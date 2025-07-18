package com.aluraCourse.literAlura.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;

/**
* Cliente HTTP que se encarga de realizar solicitudes GET a URLs externas,
* como por ejemplo a la API de Project Gutenberg, y retornar el contenido
* de la respuesta en formato JSON como una cadena de texto.
*
* Esta clase utiliza la clase HttpClient incorporada en Java (desde Java 11)
* para construir y enviar la solicitud, y manejar la respuesta.
*
* Métodos:
* - obtenerDatos(String url): realiza una solicitud HTTP GET a la URL indicada
*   y devuelve el cuerpo de la respuesta como un String.
*
* En caso de errores de conexión o interrupciones, lanza una RuntimeException.
*
* Esta clase es útil para desacoplar la lógica de acceso a servicios externos
* del resto de la aplicación, y puede ser utilizada junto con un DataConverter
* para transformar la respuesta en objetos Java.
*
* @author Luciano E. Gatti Flekenstein
*/

@Component
public class GutenbergClient{
    /**
    * Realiza una solicitud HTTP GET a la URL especificada y devuelve la respuesta como una cadena JSON.
    *
    * Utiliza {@link java.net.http.HttpClient} para realizar la conexión. En caso de errores de entrada/salida
    * o interrupciones, lanza una {@link RuntimeException}.
    *
    * @param url la URL a la que se realizará la solicitud.
    * @return una cadena JSON con la respuesta del servidor.
    */
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}
