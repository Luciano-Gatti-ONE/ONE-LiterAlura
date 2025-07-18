package com.aluraCourse.literAlura.util;

/**
* Interfaz que define un contrato genérico para la conversión de datos JSON
* en objetos Java, utilizando deserialización.
*
* Métodos:
* - <T> T obtenerDatos(String json, Class<T> clase): deserializa una cadena JSON en una instancia
*   del tipo especificado por la clase.
* 
* @author Luciano E. Gatti Flekenstein
*/
public interface IDataConverter {
    <T> T convertJsonToObject(String json, Class<T> clase);
}
