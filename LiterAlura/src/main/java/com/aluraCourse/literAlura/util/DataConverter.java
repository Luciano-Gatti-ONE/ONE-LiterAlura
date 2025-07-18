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
// Objeto encargado de mapear JSON a objetos Java.
private ObjectMapper mapper = new ObjectMapper();

    /**
    * Convierte una cadena JSON en una instancia de la clase especificada.
    *
    * @param json  la cadena JSON que representa el objeto a convertir.
    * @param clase la clase a la que se desea mapear el JSON.
    * @param <T>   el tipo de objeto que se desea obtener tras la conversión.
    * @return una instancia del tipo T con los datos mapeados desde el JSON.
    * @throws RuntimeException si ocurre un error al procesar el JSON (por ejemplo, si está mal formado o no es compatible con la clase dada).
    */
    @Override
    public <T> T convertJsonToObject(String json, Class<T> clase) {
        try {
            return mapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
