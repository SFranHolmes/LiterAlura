package com.literalura.catalogodelibros.model;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
