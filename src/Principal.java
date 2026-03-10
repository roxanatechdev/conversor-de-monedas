import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcion = 0;
        double valorOriginal;
        ConversorDeMoneda conversorDeMoneda = new ConversorDeMoneda();
        List<String> codigosPorPais = new ArrayList<>(Arrays.asList("USD", "ARS", "BRL", "COP", "EUR"));

        mostrarTitulo("CONVERSOR DE MONEDAS", 50);
        System.out.println("                  ¡Bienvenido!                  ");
        mostrarLineaSeparadora(50, '=');

        while (opcion != 10) {
            mostrarMenuPrincipal();
            System.out.print(" ➤  Ingrese su opción: ");

            try {
                opcion = sc.nextInt();

                if (opcion >= 1 && opcion <= 8) {
                    System.out.print(" ➤  Ingrese el monto a convertir: $ ");
                    valorOriginal = sc.nextDouble();

                    if (valorOriginal > 0) {
                        System.out.print(" Procesando conversión ");
                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(200);
                                System.out.print("·");
                            } catch (InterruptedException e) {}
                        }
                        System.out.println();

                        switch (opcion) {
                            case 1 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(0), codigosPorPais.get(1), valorOriginal);
                            case 2 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(1), codigosPorPais.get(0), valorOriginal);
                            case 3 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(0), codigosPorPais.get(2), valorOriginal);
                            case 4 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(2), codigosPorPais.get(0), valorOriginal);
                            case 5 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(0), codigosPorPais.get(3), valorOriginal);
                            case 6 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(3), codigosPorPais.get(0), valorOriginal);
                            case 7 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(0), codigosPorPais.get(4), valorOriginal);
                            case 8 -> conversorDeMoneda.realizandoConversionDeMoneda(codigosPorPais.get(4), codigosPorPais.get(0), valorOriginal);
                        }
                        mostrarMensajeExito("¡Conversión realizada con éxito!");

                    } else {
                        mostrarError("El monto debe ser mayor a 0.");
                    }

                } else if (opcion == 9) {
                    mostrarHistorialElegante(conversorDeMoneda.getHistorial());
                } else if (opcion == 10) {
                    mostrarDespedida();
                } else {
                    mostrarError("Opción inválida. Por favor, intenta de nuevo.");
                }

            } catch (Exception e) {
                mostrarError("Entrada inválida. Por favor, ingresa un número.");
                sc.nextLine(); // Limpiar buffer
            }

            if (opcion != 10) {
                System.out.println();
                mostrarLineaSeparadora(50, '~');
                System.out.print(" Presione Enter para continuar...");
                sc.nextLine();
                sc.nextLine();
            }
        }
    }

    private static void mostrarTitulo(String titulo, int ancho) {
        int espacios = (ancho - titulo.length()) / 2;
        String formato = "╔" + "═".repeat(ancho) + "╗\n";
        formato += "║" + " ".repeat(espacios) + titulo + " ".repeat(ancho - titulo.length() - espacios) + "║\n";
        formato += "╚" + "═".repeat(ancho) + "╝";
        System.out.println(formato);
    }

    private static void mostrarLineaSeparadora(int longitud, char caracter) {
        System.out.println(" " + String.valueOf(caracter).repeat(longitud));
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│                    MENÚ PRINCIPAL               │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.println("│   1.  USD  →  ARS  (Dólar → Peso Argentino)     │");
        System.out.println("│   2.  ARS  →  USD  (Peso Argentino → Dólar)     │");
        System.out.println("│   3.  USD  →  BRL  (Dólar → Real Brasileño)     │");
        System.out.println("│   4.  BRL  →  USD  (Real Brasileño → Dólar)     │");
        System.out.println("│   5.  USD  →  COP  (Dólar → Peso Colombiano)    │");
        System.out.println("│   6.  COP  →  USD  (Peso Colombiano → Dólar)    │");
        System.out.println("│   7.  USD  →  EUR  (Dólar → Euro)               │");
        System.out.println("│   8.  EUR  →  USD  (Euro → Dólar)               │");
        System.out.println("│   9.  📋  Ver Historial de Conversiones         │");
        System.out.println("│  10.  🚪  Salir                                 │");
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    private static void mostrarHistorialElegante(List<HistorialConversor> historial) {
        if (historial.isEmpty()) {
            System.out.println("\n┌────────────────────────────────────┐");
            System.out.println("│      No hay conversiones aún       │");
            System.out.println("└────────────────────────────────────┘");
            return;
        }

        System.out.println("\n┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                     HISTORIAL DE CONVERSIONES                  │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  #  │     FECHA Y HORA     │              CONVERSIÓN           │");
        System.out.println("├─────┼──────────────────────┼───────────────────────────────────┤");

        int contador = 1;

        for (HistorialConversor c : historial) {
            // La fecha ya viene como String, la usamos directamente
            String fecha = c.getFecha();
            System.out.printf("│ %2d  │ %-20s │ %10.2f %-4s → %10.2f %-4s │%n",
                    contador++,
                    fecha,
                    c.getMontoOriginal(),
                    c.getMonedaOrigen(),
                    c.getMontoConvertido(),
                    c.getMonedaDestino());
        }

        System.out.println("├─────┴──────────────────────┴───────────────────────────────────┤");
        System.out.printf("│                     Total de conversiones: %3d                 │%n", historial.size());
        System.out.println("└────────────────────────────────────────────────────────────────┘");
    }

    private static void mostrarMensajeExito(String mensaje) {
        System.out.println("\n  ✅  " + mensaje);
    }

    private static void mostrarError(String mensaje) {
        System.out.println("\n  ❌  " + mensaje);
    }

    private static void mostrarDespedida() {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│                                    │");
        System.out.println("│    ¡Gracias por usar el programa!  │");
        System.out.println("│                                    │");
        System.out.println("│         ¡Hasta pronto! 👋          │");
        System.out.println("│                                    │");
        System.out.println("└────────────────────────────────────┘\n");
    }
}