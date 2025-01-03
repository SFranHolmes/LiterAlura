package com.literalura.catalogodelibros.model;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/catalogo_de_libros";
    private static final String USER = "postgres";
    private static final String PASSWORD = "${DB_PASSWORD}";

    public Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    public void registrarLibro(DatosLibros libro) {
        String sql = "INSERT INTO libros (titulo, idiomas) VALUES (?, ?)";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.titulo());
            ps.setString(2, String.join(",", libro.idiomas()));
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar libro", e);
        }

        // Registrar autores relacionados
        for (DatosAutor autor : libro.autors()) {
            registrarAutor(autor);
        }
    }

    public void registrarAutor(DatosAutor autor) {
        String sql = "INSERT INTO autores (nombre, birth_year) VALUES (?, ?) ON CONFLICT (nombre) DO NOTHING";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, autor.nombre());
            ps.setString(2, autor.fechaDeNacimiento());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar autor", e);
        }
    }

    public List<String> listarLibros() {
        String sql = "SELECT titulo FROM libros";
        List<String> libros = new ArrayList<>();
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                libros.add(rs.getString("titulo"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar libros", e);
        }
        return libros;
    }

    public List<String> listarAutores() {
        String sql = "SELECT nombre FROM autores";
        List<String> autores = new ArrayList<>();
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                autores.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar autores", e);
        }
        return autores;
    }

    public List<String> buscarAutoresPorAno(String ano) {
        String sql = "SELECT nombre FROM autores WHERE birth_year = ?";
        List<String> autores = new ArrayList<>();
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ano);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    autores.add(rs.getString("nombre"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar autores por año", e);
        }
        return autores;
    }
}
