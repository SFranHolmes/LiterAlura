# Catálogo de Libros  

Este proyecto es una aplicación basada en Java que permite interactuar con una API de libros para buscar, listar y consultar datos sobre autores y libros. La aplicación incluye un menú de interacción que permite a los usuarios realizar diversas operaciones relacionadas con los datos obtenidos de la API.  

## Tabla de Contenidos  
- [Descripción](#descripción)  
- [Características](#características)  
- [Menú de Interacción](#menú-de-interacción)  
- [Estructura del Proyecto](#estructura-del-proyecto)  
- [Requisitos Previos](#requisitos-previos)  
- [Instalación](#instalación)  
- [Uso](#uso)  
- [Contribuciones](#contribuciones)  
- [Licencia](#licencia)  

## Descripción  
El **Catálogo de Libros** es una herramienta que consume datos de una API pública y los presenta en un formato interactivo en consola. Los usuarios pueden buscar libros por título, autores por nombre o año de nacimiento, listar los registros disponibles y consultar los libros más descargados.  

## Características  
- Consumo de una API pública para obtener datos en tiempo real.  
- Búsqueda de libros por título.  
- Búsqueda de autores por nombre o año de nacimiento.  
- Listado de libros y autores disponibles.  
- Consulta del Top 10 de libros más descargados.  
- Implementación de manejo de errores y validaciones.  

## Menú de Interacción  
El sistema ofrece las siguientes opciones a través de un menú interactivo:  

1. **Buscar libro por título:** Permite buscar libros ingresando su título.  
2. **Buscar autor por nombre:** Realiza una búsqueda de autores mediante su nombre.  
3. **Buscar autores por año:** Filtra los autores por su año de nacimiento.  
4. **Listar libros registrados:** Muestra todos los libros obtenidos de la API.  
5. **Listar autores registrados:** Presenta una lista de autores disponibles en la base de datos.  
6. **Top 10 libros más descargados:** Consulta los libros más descargados según la API.  

Los usuarios pueden ingresar el número correspondiente a cada opción para realizar la acción deseada.  

## Estructura del Proyecto  
El proyecto está estructurado en varias clases principales:  
- **Datos:** Representa la estructura principal de los datos retornados por la API.  
- **DatosLibros:** Contiene información detallada sobre los libros, como título, autores e idiomas.  
- **DatosAutor:** Representa la información de los autores, como nombre y año de nacimiento.  
- **Principal:** Gestiona la interacción con el usuario a través del menú.  
- **DatabaseManager:** (Opcional) Maneja la integración con una base de datos para registros locales.  

## Requisitos Previos  
- Java 11 o superior.  
- Conexión a internet para consumir la API.  
- PostgreSQL (si decides usar la base de datos).  

## Instalación  
1. Clona este repositorio:  
   ```bash  
   https://github.com/SFranHolmes/LiterAlura.git 
