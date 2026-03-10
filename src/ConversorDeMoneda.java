
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversorDeMoneda {
    private List<HistorialConversor> historial;

    public ConversorDeMoneda() {
        historial = cargarHistorial();
    }

    public void realizandoConversionDeMoneda(String monedaOrigen, String monedaDestino, double monto) {
        try {
            // Crear la URI para hacer la solicitud al API de tasas de cambio
            URI direccion = URI.create("https://v6.exchangerate-api.com/v6/b863bf382329f37ba59315b7/pair/" + monedaOrigen + "/" + monedaDestino + "/" + monto);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(direccion)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonReader reader = new JsonReader(new StringReader(response.body()));
            reader.setLenient(true);

            // Parsear la respuesta JSON utilizando JsonParser
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            double valorConvertido = jsonObject.get("conversion_result").getAsDouble();

            // Obtener la fecha y hora actual para registrar la conversión
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            // Crear un nuevo objeto Conversión y agregarlo al historial
            historial.add(new HistorialConversor(monedaOrigen, monedaDestino, monto, valorConvertido, fecha));
            guardarHistorial();

            //mostrar la conversion de moneda
            System.out.println("El valor " + monto + " [" + monedaOrigen + "] corresponde al valor final de =>>> " + valorConvertido + " [" + monedaDestino + "]");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void guardarHistorial() {
        // permite escribir en el archivo, si el archivo no existe, se creará, si ya existe, se sobrescribira.
        try (Writer writer = new FileWriter("historial.json")) {
            // Convertir la lista de conversiones a formato JSON con pretty printing y escribirla en el archivo
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(historial, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<HistorialConversor> cargarHistorial() {
        try {
            // Comprobar si el archivo de historial existe
            Path path = Paths.get("historial.json");
            // Leer el archivo JSON y convertirlo a una lista de objetos HistorialConversor
            if (Files.exists(path)) {
                BufferedReader reader = new BufferedReader(new FileReader("historial.json"));
                Gson gson = new Gson();
                return gson.fromJson(reader, new TypeToken<List<HistorialConversor>>() {
                }.getType());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<HistorialConversor> getHistorial() {
        return historial;
    }

    // Método adicional para mostrar el historial formateado en consola
    public void mostrarHistorialFormateado() {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            String historialJson = gson.toJson(historial);
            System.out.println("=== HISTORIAL DE CONVERSIONES ===");
            System.out.println(historialJson);
        } catch (Exception e) {
            System.out.println("Error al mostrar el historial: " + e.getMessage());
        }
    }

}
