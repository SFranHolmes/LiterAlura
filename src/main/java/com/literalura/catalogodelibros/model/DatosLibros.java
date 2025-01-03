package com.literalura.catalogodelibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autors,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDeDescargas
) {
}
