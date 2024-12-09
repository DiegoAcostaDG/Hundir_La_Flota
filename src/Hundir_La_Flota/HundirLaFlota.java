package Hundir_La_Flota;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HundirLaFlota {
    private Tablero jugadorTablero; // Tablero del jugador, donde colocará sus barcos
    private Tablero pirataTablero; // Tablero del enemigo, donde el jugador atacará
    private String nombreJugador; // Nombre del jugador
    private static final Barcos.TipoBarco[] TIPOS_BARCOS = {
        Barcos.TipoBarco.PORTAAVIONES, 
        Barcos.TipoBarco.ACORAZADO, 
        Barcos.TipoBarco.DESTRUCTOR, 
        Barcos.TipoBarco.SUBMARINO
    };
    private int rondas; // Contador de rondas, incrementado cada vez que el jugador dispara

    // Constructor del juego, inicializa los tableros y el nombre del jugador
    public HundirLaFlota(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.jugadorTablero = new Tablero(); // Inicializa el tablero del jugador
        this.pirataTablero = new Tablero(); // Inicializa el tablero del enemigo
        this.rondas = 0; // Inicializa el contador de rondas
    }

    // Método para mostrar el título del juego con gráficos
    private static void mostrarTitulo() {
        System.out.println("*******************************************");
        System.out.println("*            Hundir La Flota              *");
        System.out.println("*                                         *");
        System.out.println("*             Diego y Lucia               *");
        System.out.println("*******************************************\n");
        System.out.println("───║─▄──▄──▄──▄──║────\r\n" 
                + "───║─▓──▓──▓──▓──║────\r\n"
                + "───░░░░░░░░░░░░░─║────\r\n"
                + "▀███████████████████──\r\n"
                + "░██████████████████▀░░");
    }

    // Método para mostrar las instrucciones del juego al jugador
    public void mostrarInstrucciones() {
        System.out.println("\n¡Bienvenido a Hundir la Flota!");
        System.out.println("Instrucciones:");
        System.out.println("1. El juego se juega en un tablero de 5x5.");
        System.out.println("2. El jugador debe colocar sus barcos en el tablero.");
        System.out.println("   - Los barcos se colocan de manera horizontal o vertical.");
        System.out.println("   - Los barcos ocupan varias casillas dependiendo de su tamaño.");
        System.out.println("3. Después de colocar todos los barcos, el jugador puede disparar.");
        System.out.println("   - El jugador debe elegir las coordenadas del disparo.");
        System.out.println("   - Si el disparo impacta un barco enemigo, se marca como un éxito.");
        System.out.println("   - Si el disparo falla, se marca como 'agua'.");
        System.out.println("4. El objetivo es hundir todos los barcos enemigos.");
        System.out.println();
    }

    // Método para iniciar el juego, que organiza las fases del juego
    public void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);
        mostrarInstrucciones(); // Muestra las instrucciones al inicio

        // Fase de colocación de barcos
        System.out.println("\n--- Fase de colocación de barcos ---");
        for (int i = 0; i < TIPOS_BARCOS.length; i++) {
            boolean colocado = false;
            while (!colocado) {
                System.out.println("\nColocando barco: " + TIPOS_BARCOS[i] + " (tamaño: " + TIPOS_BARCOS[i].getTamanio() + ")");
                jugadorTablero.mostrarTablero(); // Mostrar tablero actual con barcos colocados
                int fila = pedirEntero(scanner, "Introduce la fila inicial (0-4): ", 0, 4); // Solicita fila
                int columna = pedirEntero(scanner, "Introduce la columna inicial (0-4): ", 0, 4); // Solicita columna
                char direccion = pedirDireccion(scanner); // Solicita dirección del barco

                // Crear el barco y intentar colocarlo en el tablero
                Barcos barco = new Barcos(TIPOS_BARCOS[i]);
                colocado = jugadorTablero.colocarBarco(barco, fila, columna, direccion);

                // Verificar si el barco se ha colocado correctamente
                if (colocado) {
                    System.out.println("Barco colocado correctamente.");
                } else {
                    System.out.println("No se pudo colocar el barco. Verifica las coordenadas e intenta de nuevo.");
                }
            }
        }
        // Menú principal del juego después de la colocación de los barcos
        boolean jugando = true;
        while (jugando) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("Ronda: " + rondas); // Muestra el número de la ronda actual
            System.out.println("1. Ver mi tablero");
            System.out.println("2. Tablero de ataque");
            System.out.println("3. Disparar");
            System.out.println("4. Salir");
            int opcion = pedirEntero(scanner, "Selecciona una opción: ", 1, 4);

            // Lógica del menú de opciones
            switch (opcion) {
                case 1:
                    // Muestra el tablero del jugador
                    System.out.println("\nTu tablero:");
                    jugadorTablero.mostrarTableroConDisparosJugador(); // Muestra el tablero con barcos y disparos
                    break;
                case 2:
                    // Muestra el tablero de ataque del enemigo
                    System.out.println("\nTablero de ataque:");
                    pirataTablero.mostrarTableroDeAtaque(); // Muestra solo el tablero de ataques sin los barcos enemigos
                    break;
                case 3:
                    // Lógica para disparar
                    System.out.println("\nIntroduce las coordenadas para disparar (fila y columna): ");
                    int filaDisparo = pedirEntero(scanner, "Fila: ", 0, 4);
                    int columnaDisparo = pedirEntero(scanner, "Columna: ", 0, 4);

                    // Verificar el disparo y actualizar tableros
                    boolean exito = pirataTablero.recibirDisparo(filaDisparo, columnaDisparo);
                    rondas++; // Incrementar ronda cada vez que se dispara

                    // Mensaje de éxito o fallo con el nombre del jugador
                    if (exito) {
                        System.out.println(nombreJugador + " ha acertado el disparo. ¡Impacto!");
                    } else {
                        System.out.println(nombreJugador + " ha fallado el disparo. Agua...");
                    }

                    // El pirata dispara aleatoriamente
                    System.out.println("\nEl Pirata está disparando aleatoriamente...");
                    Pirata.disparar(jugadorTablero);  // Llama al método del pirata para disparar aleatoriamente
                    break;

                case 4:
                    // Salir del juego
                    System.out.println("¡Gracias por jugar, " + nombreJugador + "! Hasta la próxima.");
                    jugando = false;
                    break;
                default:
                    // Opción no válida
                    System.out.println("Opción no válida. Por favor, elige una opción del menú.");
            }
        }
        // Cierra el escáner al finalizar el juego
        scanner.close();
    }
    // Método auxiliar para pedir un número entero con un rango específico y manejar excepciones
    private int pedirEntero(Scanner scanner, String mensaje, int min, int max) {
        int numero = -1;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = scanner.nextInt();
                if (numero < min || numero > max) {
                    throw new IllegalArgumentException("El número debe estar entre " + min + " y " + max + ".");
                }
                valido = true;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Entrada inválida. " + e.getMessage());
                scanner.nextLine(); // Limpiar el buffer
            }
        }
        return numero;
    }
    // Método auxiliar para pedir la dirección de un barco (horizontal o vertical)
    private char pedirDireccion(Scanner scanner) {
        char direccion = ' ';
        boolean valido = false;
        while (!valido) {
            System.out.print("Introduce la dirección (H para horizontal, V para vertical): ");
            String input = scanner.next().toUpperCase();
            if (input.equals("H") || input.equals("V")) {
                direccion = input.charAt(0);
                valido = true;
            } else {
                System.out.println("Dirección inválida. Por favor, introduce 'H' o 'V'.");
            }
        }
        return direccion;
    }
    // Método principal donde se inicializa y ejecuta el juego
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce tu nombre: ");
        String nombreJugador = scanner.nextLine(); // Solicita el nombre del jugador
        mostrarTitulo(); // Muestra el título del juego
        HundirLaFlota juego = new HundirLaFlota(nombreJugador); // Crea una instancia del juego
        juego.iniciarJuego(); // Inicia el juego
        scanner.close(); // Cierra el scanner al final
    }
}
