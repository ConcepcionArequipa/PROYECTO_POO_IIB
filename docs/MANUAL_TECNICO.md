 # PROYECTO POO LICENCIAS II BIMESTRE
INTEGRANTES : PATIÑO JOSUE , CONCEPCION AREQUIPA

GR2
# MANUAL TÉCNICO

Sistema de Entrega de Licencias

Este documento describe la arquitectura, estructura interna y decisiones técnicas
del sistema de entrega de licencias desarrollado en Java con Swing y base de datos remota.

## Tecnologías Utilizadas

- Lenguaje: Java 17
- Interfaz gráfica: Java Swing
- Base de datos: MySQL (alojada en Clever Cloud)
- Administración de BD: MySQL Workbench
- Acceso a datos: JDBC
- Seguridad: BCrypt para hash de contraseñas
- Control de versiones: Git / GitHub
- Exportación de reportes: CSV y PDF (iText)

- Usa esta ruta para obtener java 17 : [java 17](https://adoptium.net/es/temurin/releases?version=17)

## Arquitectura del Sistema

El sistema sigue una arquitectura en capas basada en el patrón MVC (Modelo - Vista - Controlador),
separando claramente la lógica de negocio, el acceso a datos y la interfaz gráfica.

```
Capa UI (Swing)
   ↓
Capa Service (Reglas de negocio)
   ↓
Capa DAO (Acceso a datos)
   ↓
Base de Datos Remota (MySQL - Clever Cloud)
```


## Organización del Proyecto

El proyecto está organizado en paquetes, siguiendo buenas prácticas de POO:

- model: Clases que representan entidades del sistema (POJOs).
- dao: Clases responsables del acceso a la base de datos mediante JDBC.
- service: Reglas de negocio y validaciones.
- ui: Formularios y ventanas en Java Swing.

```
src/
 ├── dao
 ├── model
 ├── service
 ├── ui
 └── pom.xml
```


## Encapsulamiento

Los atributos de las clases modelo son privados y se accede a ellos
mediante getters y setters, protegiendo la integridad de los datos.
```md
Ejemplo:
private String cedula;
public String getCedula() { return cedula; }
```
## Gestión de Dependencias y Build

El proyecto utiliza Apache Maven como herramienta de gestión de dependencias
y automatización del proceso de compilación, a través del archivo pom.xml.


El uso de Maven aporta las siguientes ventajas técnicas:

- Control de versiones de librerías.
- Reducción de errores por dependencias faltantes.
- Facilidad para desplegar el proyecto.
- Estandarización del proceso de build.

## Librerías Utilizadas

El sistema utiliza las siguientes librerías externas para garantizar el correcto funcionamiento de sus diferentes módulos:

### iText 7
- **itext-commons-7.2.5**  
  Proporciona funcionalidades comunes compartidas entre los distintos módulos de iText.

- **itext-io-7.2.5**  
  Manejo de entrada y salida de datos, especialmente para la generación de documentos PDF.

- **itext-kernel-7.2.6**  
  Núcleo principal de iText, encargado de la estructura base y creación de documentos PDF.

- **itext-layout-7.2.5**  
  Permite el diseño y la organización visual del contenido dentro de los documentos PDF.

### Seguridad
- **jbcrypt-0.4**  
  Utilizada para el hash seguro de contraseñas mediante el algoritmo BCrypt.

### Base de Datos
- **mysql-connector-j-9.5.0**  
  Conector JDBC oficial para la comunicación entre la aplicación Java y la base de datos MySQL.

### Logging
- **slf4j-api-2.0.7**  
  API de logging utilizada por iText y otras librerías para el manejo de mensajes de registro.

- **slf4j-simple-2.0.7**  
  Implementación simple de SLF4J que permite mostrar los logs directamente en consola.




## Polimorfismo

El polimorfismo se aplica principalmente en la capa de interfaz gráfica
mediante herencia y sobrescritura de métodos.

Las ventanas del sistema heredan de una clase base común (`BaseFrame`
y `BaseDialogo`), pero cada una implementa su propio comportamiento,
permitiendo que un mismo método tenga diferentes implementaciones
según la ventana que lo utilice.

```
Ejemplos de clases que aplican polimorfismo:

BaseFrame
 ├── LoginFrame
 ├── MenuAdmin
 ├── MenuAnalista
 ├── RegistrarSolicitante
 ├── RegistrarExamen
 └── GenerarLicencia

```
Esto permite reutilizar código común y adaptar el comportamiento
según el contexto de cada formulario.


## Base de Datos

El sistema utiliza una base de datos relacional MySQL alojada en la nube
mediante la plataforma Clever Cloud, lo que garantiza disponibilidad
remota y acceso seguro desde la aplicación de escritorio.

Las tablas incluyen:

   | Tablas        | 
   | ------------- |
   | usuario       | 
   | solicitante   | 
   | tramite       | 
   | examen        | 
   | requisitos    | 
   | licencia      | 

La conexión desde la aplicación Java se realiza utilizando JDBC
a través del driver oficial MySQL Connector/J.

## Seguridad

- Las contraseñas se almacenan usando hash BCrypt.
- Los usuarios inactivos no pueden iniciar sesión.
- El sistema controla roles (ADMIN / ANALISTA).

## Reglas de Negocio

- Edad mínima del solicitante: 18 años.
- Nota mínima aprobatoria: 14.
- La licencia solo se genera si el trámite está aprobado.
- Inserción de licencia y actualización de trámite se realizan
  dentro de una transacción atómica.

## Compilación y Ejecución

### Opción 1: Ejecución desde el código fuente (IDE)

1. Descargar o clonar el repositorio desde GitHub.
2. Abrir el proyecto en un IDE compatible con Java (IntelliJ IDEA o NetBeans).
3. Verificar la configuración de conexión a la base de datos.
4. Ejecutar la clase Main.java del proyecto.
5. Iniciar sesión utilizando las credenciales de prueba.
Credenciales de prueba:

Administrador
>>Usuario: `jossuer`  
>>Contraseña: `josue55`

Analista
>>Usuario: `conce`  
>>Contraseña: `conce88`

### Opción 2: Ejecución mediante archivo ejecutable (.jar)

1. Descargar el archivo app.jar desde el repositorio.
2. Asegurarse de tener Java 17 instalado en el equipo.
3. Ejecutar el archivo app.jar.
4. Iniciar sesión con las credenciales de prueba proporcionadas.










