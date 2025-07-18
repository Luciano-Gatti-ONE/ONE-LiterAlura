/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aluraCourse.literAlura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Implementación de la interfaz IDataConverter que se encarga de convertir datos JSON
 * a objetos Java utilizando la biblioteca Jackson.
 *
 * Esta clase centraliza la lógica de deserialización, permitiendo transformar
 * una cadena JSON en una instancia del tipo de clase especificado.
 *
 * Métodos:
 * - <T> T obtenerDatos(String json, Class<T> clase): deserializa el JSON en una instancia de la clase indicada.
 *
 * Se utiliza ObjectMapper de Jackson como motor de conversión.
 * En caso de error en la conversión, se lanza una RuntimeException.
 *
 * Esta clase es útil para desacoplar la lógica de parsing del resto de la aplicación.
 *
 * @author Luciano E. Gatti Flekenstein
 */

@Component
public class DataConverter implements IDataConverter{
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T convertJsonToObject(String json, Class<T> clase) {
        try {
            return mapper.readValue(json.toString(), clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
