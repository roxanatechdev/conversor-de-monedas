# Conversor de Monedas

¡Bienvenido al Conversor de Monedas! Este programa permite convertir diferentes monedas utilizando la API de tasas de cambio en tiempo real. Ahora también incluye un historial de conversiones realizadas para que puedas llevar un seguimiento de todas tus transacciones.

## Características

- **Conversión de Monedas**: Convierte entre varias monedas, incluyendo Dólar (USD), Peso Argentino (ARS), Real Brasileño (BRL) y Peso Colombiano (COP).
- **Historial de Conversiones**: Mantiene un registro de todas las conversiones realizadas, almacenadas en un archivo JSON (`historial.json`).
- **Interfaz de Usuario Interactiva**: Interactúa fácilmente con el programa a través de un menú de opciones.
- **Soporte extendido para monedas**: Capacidad para aumentar la cantidad de monedas disponibles para elegir.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **API de ExchangeRate**: Para obtener las tasas de cambio en tiempo real.
- **Gson**: Para la manipulación y el parsing de datos JSON.

## Cómo Usar

1. **Ejecuta el programa**: Al iniciar, se presentará un menú con opciones de conversión.
2. **Selecciona una opción**: Ingresa el número correspondiente a la conversión que deseas realizar.
3. **Ingresa el valor a convertir**: Proporciona el monto que quieres convertir.
4. **Consulta el historial**: Las conversiones realizadas se guardarán en el archivo `historial.json`, que podrás revisar en cualquier momento.

## Implementación del Historial

La función `guardarHistorial` se encarga de guardar cada conversión realizada en un archivo JSON. Utiliza `FileWriter` para crear o actualizar el archivo `historial.json`, y `Gson` para serializar el historial de conversiones en formato JSON.

### Ejemplo de Código

```java
public void guardarHistorial(String monedaOrigen, String monedaDestino, double monto, double resultado) {
    // Crea un objeto de conversión con los detalles
    Conversion conversion = new Conversion(monedaOrigen, monedaDestino, monto, resultado);
    
    // Carga el historial existente
    List<Conversion> historial = cargarHistorial();
    
    // Agrega la nueva conversión al historial
    historial.add(conversion);
    
    // Guarda el historial actualizado en el archivo JSON
    try (Writer writer = new FileWriter("historial.json")) {
        gson.toJson(historial, writer);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

## Ejemplo de Uso
![Interfaz del Conversor de Monedas](https://user-images.githubusercontent.com/screenshots/Captura.png) 

