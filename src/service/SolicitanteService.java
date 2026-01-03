package service;

import dao.SolicitanteDao;
import dao.TramiteDao;
import model.Solicitante;
import java.time.LocalDate;
import java.time.Period;

public class SolicitanteService {
    public void registrarSolicitante(Solicitante solicitante) throws Exception {
        //Validaciones de negocio

        //Validar si el solicitante es nulo

        if(solicitante == null) {
            throw new Exception("Solicitante inválido");
        }

        //Llamar a los metodos
        validarCedula(solicitante.getCedula());
        validarEdad(solicitante.getFechaNacimiento());

        if(solicitante.getNombre() == null || solicitante.getNombre().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        //Llamar  al Dao

        SolicitanteDao dao = new SolicitanteDao();

        if (dao.existeCedula(solicitante.getCedula())){
            throw new Exception("La cedula ya esta registrada");
        }

        // Insertar solicitante y obtener la ID

        int idSolicitante= dao.insertarYRetornarId(solicitante);

        if(idSolicitante <= 0) {
           throw new Exception("No se pudo registrar el solicitante");
        }

        //Crear tramite inicial

        // NOTA: se requiere que el DAO retorne el ID del solicitante

        TramiteDao tramiteDao = new TramiteDao();

       boolean ok= tramiteDao.crear(idSolicitante); // estado= pendiente
        if(!ok) {
            throw new Exception("No se pudo crear el tramite inicial");
        }
    }

    //Metodo para validar la edad del solicitante
    private void validarEdad(LocalDate fechaNacimiento) throws Exception {
        if(fechaNacimiento == null) {
            throw new Exception("La fecha de nacimiento es obligatoria");
        }
        int edad= Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if(edad < 18) {
            throw new Exception("El solicitante deber ser mayor de 18 años");
        }
    }

    //Metodo para validar la cedula del solicitante
    private void validarCedula(String cedula) throws Exception {
        if(cedula == null || cedula.trim().isEmpty()) {
            throw new Exception("El cédula es obligatoria");
        }
        if(!cedula.matches("\\d{10}")) {
            throw new Exception("La cédula debe tener 10 digitos numéricos");
        }
    }
}
