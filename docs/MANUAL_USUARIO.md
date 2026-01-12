Integrantes: Concepcion Arequipa y Josue Patiño

# **MANUAL DE USUARIO: SISTEMA DE EMISIÓN DE LICENCIAS**

## 1. Introducción

   Este sistema ha sido diseñado para automatizar el proceso de obtención de licencias de conducir, desde el registro inicial del ciudadano hasta la impresión del documento legal.

## 2. Acceso al Sistema (Inicio de Sesión)

![img_1.png](img_1.png)


   Para acceder al sistema, el usuario debe poseer el rol de Analista o Administrador. Además, es indispensable que su cuenta se encuentre en estado Activo; de lo contrario, el acceso será denegado.

Para ingresar, debe ejecutar la aplicación y seguir estos pasos:

- Ingrese su nombre de Usuario.

- Escriba su contraseña.

- Presione el botón "Ingresar".

Nota de Seguridad: El sistema se bloqueará automáticamente tras 3 intentos fallidos para proteger la integridad de la información y prevenir accesos no autorizados.

Si sus credenciales son correctas el sistema le dara un mensaje de bienvenida, y abrira el menu principal de acuerdo a su rol.

![img_2.png](img_2.png)

## 3. Rol Analista

Si el usuario tiene como rol Analista en el sistema le aparecera un panel de gestion, ya que el analista es el encargado de la operatividad diaria del sistema.

![img_3.png](img_3.png)

En el panel de gestion, el analista puede registrar solicitantes, verificar los requisitos del tramite, registrar las notas de los examenes de los solicitantes y generar la licencia del solicitante de acuerdo al estado del tramite en el que se encuentra cada solicitante. Ademas cuenta con un filtrado donde puede visualizar el estado de cada tramite o buscar a un solicitante por su cedula.

3.1. Registro de solicitantes

Instrucciones de uso:

- Presione el boton Registrar Solicitante, donde se visualizara automaticamente una ventana para el registro de nuevos solicitantes, aqui se enuentran los siguientes botones:

1. Guardar: Para guardar en la base de datos el registro del solicitante con sus datos.
2. Limpiar: Para limpiar los campos del registro.
3. Regresar: Para regresar al menu de gestion del Analista.

![img_4.png](img_4.png)

- Ingrese los datos del solicitante: 

1. Cedula de identidad: Debe tener 10 digitos numericos y no puede ser duplicada.
2. Nombre del solicitante: Campo obligatorio
3. Fecha de nacimiento: El solicitante debe ser mayor de 18 años.
4. Tipo de licencia: El solicitante tiene la opcion de solicitar una licencia tipo A,B,C o D.
5. Fecha de solicitud: Se genera automaticamente

- Presione en Guardar.

Nota: Si no se ingresa todos los campos requeridos o alguno de ellos no cumple con las reglas requeridas, se mostrara un mensaje de error de acuerdo al campo y el sistema no guardara el registro del solicitante.

![img_5.png](img_5.png)
![img_6.png](img_6.png)
![img_7.png](img_7.png)

Caso contrario se mostrara un mensaje que confirmara que el registro fue exitoso, y el solicitante tendra como estado de su tramite en pendiente.

![img_8.png](img_8.png)

3.2. Verificacion de requisitos

Para verificar los requisitos del solicitante, se debe de cumplir lo siguiente:

- El solicitante debe tener su tramite en estado pendiente.

Instrucciones:

1. Seleccione en la tabla al solicitante que tenga su estado en pendiente, se puede ayudar con la funcion de filtrado.

![img_9.png](img_9.png)

2. Se activara automaticamente el boton de Verificar Requisitos.

3. Presione el boton 

4. Se abrira una ventana para la verificacion de requisitos que debe cumplir el solicitante

![img_10.png](img_10.png)

Donde tendra los siguiente:

- Aprobar: Si todos los requisitos se cumplen, si es asi el estado del tramite pasa a EXAMENES. 
- Rechazar: Si no se cumple alguno de los requisitos, si es asi el estado del tramite se queda en PENDIENTE.
- Regresar: Para regresar al menu de gestion del Analista

5. Se debe marcar las casillas de acuerdo a los requisitos cumplidos por el solicitante:

- Certificado medico: Representa que el solicitante entrego su certificado medico actualizado.
- Pago: Representa que el solicitante ya realizó el pago en el banco o puntos autorizados por el valor de la especie de la licencia.
- Multas: Verifica que el conductor no tenga infracciones de tránsito sin cancelar (exceso de velocidad, mal estacionado, etc.)


6. Escriba las observaciones si el solicitante no cumplio alguno de los requisitos caso contrario no es necesario.

![img_12.png](img_12.png)

7. Es obligatorio que el solicitante cumpla con todos los requisitos para aprobar caso contrario no podra pasar al siguiente estado de su tramite.

![img_11.png](img_11.png)

8. El sistema no rechaza a los solicitantes que cumplen todos sus requisitos:

![img_13.png](img_13.png)

9. Si el solicitante cumplio con todos sus requisitos, el sistema generara un mensaje de exito, ademas como resultado el estado del tramite del solicitante pasara a EXAMENES.

![img_14.png](img_14.png)

3.3 Registro de las notas del examen teorico y practico del solicitante

Para registrar las notas del solicitante, se debe de cumplir lo siguiente:

- El solicitante debe tener su tramite en estado de examenes.

Instrucciones:

1. En el menu de gestion, seleccione en la tabla al solicitante que tenga su estado en examenes, se puede ayudar con la funcion de filtrado.

![img_15.png](img_15.png)

2. Se activara automaticamente el boton Registrar Examenes 

3. Presione el boton

4. Se abrira automaticamente la ventana para el registro de la nota teorica y practica del solicitante.

![img_16.png](img_16.png)

5. Aqui se visualizara lo siguiente:

- Nota teorica: Se ingresara aqui la nota del examen teorico del solicitante
- Nota practica: Se ingresara aqui la nota del examen practico del solicitante
- Boton de Guardar: Se encarga de guardar las notas ingresadas al sistema.
- Regresar: 

Reglas:
- Las notas ingresadas deben de estan en un rango de 0 a 20 de puntaje.
- Solo se puede ingresar valores numericos
- Para que un solicitante apruebe debe tener tanto en su nota teorica como practica un puntaje mayor o igual a 14.
- Caso contrario reprobara el solicitante.

6. Ingrese las nota practica y teorica que tiene el solicitante

![img_17.png](img_17.png)

7. Presione guardar

8. Se visualizara un mensaje de exito.

![img_18.png](img_18.png)

9. Presione Regresar para salir del registro de examenes.

10.  Para verificar se podra ver en la tabla que el solicitante tendra su estado APROBADO O REPROBADO segun las notas.

![img_19.png](img_19.png)


3.4. Generacion de licencia del solicitante

Para generar la licencia del solicitante, se debe de cumplir lo siguiente:

- El solicitante debe tener su tramite en estado aprobado.

Instrucciones:

1. Seleccione en la tabla al solicitante que tenga su estado en aprobado, se puede ayudar con la funcion de filtrado.

![img_20.png](img_20.png)

2. Se activara automaticamente el boton de Generar Licencia
3. Presione el boton
4. Se abrira automaticamente la ventana para la generacion de la licencia del solicitante.

![img_21.png](img_21.png)

Donde se visualiza lo siguiente:

- Una licencia de conducir del solicitante previa.
- Boton de Guardar: Su funcion es generar la licencia de conducir del solicitante y guardar el estado del tramite del solicitante a EMITIDA.
- Boton de Agregar Foto: Permite agregar la foto de perfil del solicitante para su licencia de conducir.
- Boton de Generar: Permite guardar el pdf de la licencia de perfil de manera local a la computadora del Analista.
- Regrasar: Salir de la ventana

5. Presione Guardar:

![img_22.png](img_22.png)

Se mostrara un mensaje que la licencia se emitio con exito.

6. Posteriormente, se inactivara el boton de GUARDAR debido a que el solicitante solo puede tener una licencia unica

![img_23.png](img_23.png)

7. Presione Agregar Foto
8. Elija la foto tipo carnet del solicitante para su licencia y presione Open

![img_24.png](img_24.png)
![img_25.png](img_25.png)

8. Si se desea guardar localmente desde el computador del Analista presione Generar

9. Elija la carpeta donde desea guardar y presione Save

![img_26.png](img_26.png)

![img_27.png](img_27.png)

10. Resultado de la licencia del solicitante:


![img_28.png](img_28.png)

## 4. Rol Administrador

El administrador tiene acceso a módulos exclusivos para supervisar el sistema. Para leer las instrucciones del uso de cada boton de la parte superior dirigase al Rol Analista.

![img_29.png](img_29.png)

4.1. Gestion de usuarios analistas y administradores

1. Presione el boton Gestion Usuario
2. Automaticamente abrira la ventana para la gestion de usuarios

![img_30.png](img_30.png)

Donde se visualizara:

- Una tabla de todos los usuarios registrados tanto activos como inactivos.
- Boton de Crear: Su funcion es registrar usuarios nuevos.
- Boton de Actualizar: Permite actualizar la informacion personal de los usuarios ya creados.
- Limpiar: Su funcion es limpiar los campos llenos
- Boton de recargar: Se encarga de actualizar la tabla de los usuarios.

3. Creacion de usuarios nuevos

- Complete todos los campos requeridos en la ventana: nombre, cedula, username (nombre de usuario), contraseña, rol y estado.

Reglas:

1. Todos los campos deben se completados.
2. La cedula no puede ser duplicada y debe tener 10 digitos numericos.

Recomendaciones:

3. Tome en cuenta que si el usuario esta desactivado no puede acceder a su cuenta.
4. Recuerde su contraseña ya que esta hasheada.

![img_31.png](img_31.png)

- Presione en Crear 
- Se visualizara un mensaje de exito

![img_32.png](img_32.png)

- Ademas que se actualizara la tabla y se visualizara este nuevo usuario

![img_33.png](img_33.png)

4. Actualizacion de usuarios

- En la tabla de usuarios elija el usuario a actualizar 

![img_34.png](img_34.png)

5. Posteriormente, en la seccion de informacion personal del usuario aparecera automaticamente la informacion del usuario seleccionado

![img_35.png](img_35.png)

Nota: La contraseña no aparece debido a que esta hasheada para una proteccion adecuada, puede ser cambiada o no segun lo requerido.

7. Cambiar la imformacion personal si es necesario

![img_36.png](img_36.png)

7. Presionar actualizar

8. Se visualizara automaticamente la informacion actualizada del usuario en la tabla

![img_37.png](img_37.png)

5. Funcion de filtrado de usuarios

- Ingresar la cedula del usuario a buscar:

![img_38.png](img_38.png)

- Presione Enter 

- Automaticamente se visualizara en la tabla el usuario que tenga ese numero de cedula

![img_39.png](img_39.png)

Nota: Caso contrario de que la cedula a ingresar no exista se mostrara un mensaje de aviso

![img_40.png](img_40.png)

4.2. Visualizacion del reporte de los solicitantes

- En el menu de gestion del Administrador, presione ReporteAdmin

![img_41.png](img_41.png)

- Automaticamente se desplegara una ventana

![img_42.png](img_42.png)

Donde se visualizara:

1. Un resumen visual automatico de cuántos trámites hay en cada estado: Pendientes, en examen, aprobadas, reprobadas y emitidas.

![img_45.png](img_45.png)

4. Una tabla de todos los solicitantes con su respectiva informacion

![img_44.png](img_44.png)

3. Filtrar: Su funcion es filtrar los solicitantes por fecha de creacion, estado el tramite, tipo de licencia y cedula

![img_43.png](img_43.png)

6. Exportar: Permite exportar en un archivo .cvs todos los solicitantes de la tabla.

- Para exportar todos los solicitantes con su respectiva informacion en un archivo .cvs presione Exportar

![img_46.png](img_46.png)

- Elija la carpeta a su eleccion para guardar el .cvs
- Presione Save
- Posteriormente se vizualizara un mensaje de exito
![img_47.png](img_47.png)
- Resultado del .cvs en blog de notas
![img_48.png](img_48.png)









