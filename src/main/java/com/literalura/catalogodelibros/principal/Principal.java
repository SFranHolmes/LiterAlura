package com.literalura.catalogodelibros.principal;

import com.literalura.catalogodelibros.client.ConsumoApi;
import com.literalura.catalogodelibros.model.*;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvertirDatos convertir = new ConvertirDatos();
    private final Scanner sc = new Scanner(System.in);
    private final DatabaseManager dbManager = new DatabaseManager();

    public void mostrarElMenu() {
        System.out.println("""
                    Seleccione una opción:
                    1. Buscar libro por título.
                    2. Buscar autor por nombre.
                    3. Buscar autores por año.
                    4. Listar libros registrados.
                    5. Buscar autores registrados.
                    6. Top 10 libros más descargados.
                    0. Salir.
                    """);

        int opcion = sc.nextInt();
        sc.nextLine(); // Consumir el salto de línea
        switch (opcion) {
            case 1 -> buscarLibroPorTitulo();
            case 2 -> buscarAutorPorNombre();
            case 3 -> buscarAutoresPorAno();
            case 4 -> listarLibrosRregistrados();
            case 5 -> listarAutoresRegistrados();
            case 6 -> mostrarTop10Libros();
            case 0 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro que desea buscar:");
        var tituloLibro = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = convertir.obtenerDatos(json, Datos.class);

        datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst()
                .ifPresentOrElse(
                        libro -> {
                            System.out.println("Libro Encontrado:");
                            System.out.println(libro);
                        },
                        () -> System.out.println("Libro no encontrado")
                );
    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar:");
        var nombreAutor = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreAutor.replace(" ", "+"));
        var datosBusqueda = convertir.obtenerDatos(json, Datos.class);

        // Buscar autores que coincidan con el nombre ingresado
        datosBusqueda.resultados().stream()
                .flatMap(libro -> libro.autors().stream())
                .filter(autor -> autor.nombre().toUpperCase().contains(nombreAutor.toUpperCase()))
                .findFirst()
                .ifPresentOrElse(
                        autor -> {
                            System.out.println("Autor encontrado:");
                            System.out.println(autor);
                        },
                        () -> System.out.println("Autor no encontrado")
                );
    }

    private void buscarAutoresPorAno() {
        System.out.println("Ingrese el año de nacimiento del autor:");
        String ano = sc.nextLine();
        System.out.println("Consultando la API...");

        // Realizar la consulta a la API
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + ano);
        var datosBusqueda = convertir.obtenerDatos(json, Datos.class);

        // Mostrar resultados para depuración
        System.out.println("Respuesta de la API (depuración):");
        datosBusqueda.resultados().forEach(libro -> {
            System.out.println("Título: " + libro.titulo());
            libro.autors().forEach(autor ->
                    System.out.println("Autor: " + autor.nombre() + ", Año de nacimiento: " + autor.fechaDeNacimiento())
            );
        });

        // Filtrar autores cuyo año de nacimiento coincida con el ingresado
        List<DatosAutor> autores = datosBusqueda.resultados().stream()
                .flatMap(libro -> libro.autors().stream())
                .filter(autor -> autor.fechaDeNacimiento() != null && autor.fechaDeNacimiento().equals(ano))
                .toList();

        // Mostrar autores encontrados
        if (!autores.isEmpty()) {
            System.out.println("Autores encontrados:");
            autores.forEach(autor ->
                    System.out.println("Nombre: " + autor.nombre() + ", Año de nacimiento: " + autor.fechaDeNacimiento()));
        } else {
            System.out.println("No se encontraron autores para ese año.");
        }
    }

    private void listarLibrosRregistrados(){
        List<String> libros = dbManager.listarLibros();
        if (!libros.isEmpty()) {
            System.out.println("Libros registrados:");
            libros.forEach(System.out::println);
        } else {
            System.out.println("No hay libros registrados.");
        }

    }

    private void listarAutoresRegistrados(){
        List<String> autores = dbManager.listarAutores();
        if (!autores.isEmpty()) {
            System.out.println("Autores registrados:");
            autores.forEach(System.out::println);
        } else {
            System.out.println("No hay autores registrados.");
        }
    }

    private void mostrarTop10Libros() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = convertir.obtenerDatos(json, Datos.class);

        System.out.println("Top 10 libros más descargados:");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }
}
