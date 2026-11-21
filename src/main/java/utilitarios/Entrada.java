package utilitarios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/** Lectura segura de datos por teclado (solo para la versión de consola). */
public class Entrada {
    private static final Scanner sc = new Scanner(System.in);

    private static final DateTimeFormatter FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter HORA =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FECHA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    /** Pide un texto y lo devuelve. */
    public static String leerTexto(String msg) {
        String texto;
        do {
            System.out.print(msg);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) System.out.println("No puede estar vacio.");
        } while (texto.isEmpty());
        return texto;
    }

    /** Pide un número entero y repite hasta que sea válido. */
    public static int leerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Escriba un numero valido.");
            }
        }
    }

    /** Pide una fecha en formato dd/MM/yyyy. */
    public static LocalDate leerFecha(String msg) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(msg + "(dd/MM/aaaa): "), FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha invalida. Ejemplo: 28/10/2026");
            }
        }
    }

    /** Pide una hora en formato HH:mm. */
    public static LocalTime leerHora(String msg) {
        while (true) {
            try {
                return LocalTime.parse(leerTexto(msg + "(HH:mm): "), HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Hora invalida. Ejemplo: 09:30");
            }
        }
    }

    /** Pide fecha y hora juntas. */
    public static LocalDateTime leerFechaHora(String msg) {
        while (true) {
            try {
                return LocalDateTime.parse(leerTexto(msg + "(dd/MM/aaaa HH:mm): "), FECHA_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Ejemplo: 28/10/2026 09:30");
            }
        }
    }
}