package Hundir_La_Flota;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Hundir_La_Flota.Barcos.TipoBarco;

public class HundirLaFlota {
	
  //Método q  ue muestra el título del juego
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
    //Main del juego 
    public static void main(String[] args) {  
    	//Inicializar el título , el tablero del jugador y el pirata con su propio tablero.
        mostrarTitulo();
        Scanner scanner = new Scanner(System.in);

        // Solicitar el nombre del jugador 
        System.out.print("\nIntroduce tu nombre: "); 
        String nombreJugador = scanner.nextLine();
        Tablero tableroJugador = new Tablero();
        Jugador jugador = new Jugador(nombreJugador, tableroJugador);
        Pirata pirata = new Pirata(new Tablero()); 

        // Menú para introducir barcos
        introducirBarcosMenu(tableroJugador, scanner);

        // Iniciar la partida
        iniciarPartida(jugador, pirata, scanner);

        scanner.close();
    }

    private static void introducirBarcosMenu(Tablero tablero, Scanner scanner) {
        List<TipoBarco> barcosDisponibles = new ArrayList<>();
        for (TipoBarco tipo : TipoBarco.values()) {
            barcosDisponibles.add(tipo);
        }

        boolean todosBarcosColocados = false;

        while (!todosBarcosColocados) {
            System.out.println("\nMenú de Introducción de Barcos:");
            System.out.println("1. Introducir un barco");
            System.out.println("2. Ver tablero con los barcos colocados");

            int opcion = leerOpcion(scanner, 2);
            switch (opcion) {
                case 1:
                    introducirBarco(tablero, scanner, barcosDisponibles);
                    if (barcosDisponibles.isEmpty()) {
                        todosBarcosColocados = true;
                    }
                    break;
                case 2:
                    tablero.mostrarTablero();
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void introducirBarco(Tablero tablero, Scanner scanner, List<TipoBarco> barcosDisponibles) {
        if (barcosDisponibles.isEmpty()) {
            System.out.println("¡Ya has colocado todos los barcos!");
            return;
        }

        System.out.println("Selecciona el tipo de barco para introducir:");
        for (int i = 0; i < barcosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + barcosDisponibles.get(i).name());
        }

        int seleccion = leerOpcion(scanner, barcosDisponibles.size());
        TipoBarco tipoSeleccionado = barcosDisponibles.get(seleccion - 1);

        boolean colocado = false;
        while (!colocado) {
            try {
                System.out.println("Introduce los datos para colocar un " + tipoSeleccionado.name() + " de tamaño " + tipoSeleccionado.getTamanio() + ":");
                System.out.print("Fila inicial: ");
                int filaInicial = scanner.nextInt();
                System.out.print("Columna inicial: ");
                int colInicial = scanner.nextInt();
                System.out.print("Dirección (H para horizontal, V para vertical): ");
                char direccion = Character.toUpperCase(scanner.next().charAt(0));
                Barcos barco = new Barcos(tipoSeleccionado);
                if (tablero.colocarBarco(barco, filaInicial, colInicial, direccion)) {
                    System.out.println(tipoSeleccionado.name() + " colocado correctamente.");
                    barcosDisponibles.remove(tipoSeleccionado); // Elimina el barco colocado de la lista
                    colocado = true;
                } else {
                    System.out.println("Error al colocar el " + tipoSeleccionado.name() + ". Selecciona otra ubicación.");
                    return; // Volver al menú principal si no se puede colocar el barco
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Inténtalo de nuevo.");
                scanner.next(); // Limpiar el escáner
                return; // Volver al menú principal en caso de entrada no válida
            }
        }
    }

    private static void iniciarPartida(Jugador tableroJugador, Pirata pirata, Scanner scanner) {
        int turno = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {
            System.out.println("\nRonda: " + turno);
            System.out.println("1. Ver mis disparos y mis barcos");
            System.out.println("2. Ver tablero de ataque");
            System.out.println("3. Realizar un disparo");

            int opcion = leerOpcion(scanner, 3);
            switch (opcion) {
                case 1:
                    mostrarInformacionDisparos(tableroJugador);
                    break;
                case 2:
                    mostrarTableroAtaque(pirata.getTablero());
                    break;
                case 3:
                    realizarDisparo(pirata.getTablero(), scanner);
                    // Ataque del pirata
                    pirata.disparar(tableroJugador);
                    if (tableroJugador.comprobarDerrota()) {
                        System.out.println("¡Has perdido! Todos tus barcos han sido hundidos.");
                        juegoActivo = false;
                    }
                    if (pirata.getTablero().comprobarDerrota()) {
                        System.out.println("¡Has ganado! Todos los barcos enemigos han sido hundidos.");
                        juegoActivo = false;
                    }
                    turno++;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void mostrarInformacionDisparos(Tablero tableroJugador) {
        System.out.println("Coordenadas de tus disparos fallidos:");
        tableroJugador.mostrarDisparos(); // Método actualizado en la clase Tablero
        System.out.println("Coordenadas de tus disparos acertados:");
        tableroJugador.mostrarDisparos();
        tableroJugador.mostrarTablero();
    }

    private static void mostrarTableroAtaque(Tablero tableroPirata) {
        System.out.println("Tablero de ataque:");
        // Lógica para mostrar el tablero de ataque con X, @ y ~
        System.out.println("    0   1   2   3   4  ");
        System.out.println("  +---+---+---+---+---+");
        for (int i = 0; i < 5; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 5; j++) {
                char c = tableroPirata.getEstadoCasilla(i, j);
                System.out.print(" " + (c == 'X' ? 'X' : c == '@' ? '@' : c == 'A' ? '~' : ' ') + " |");
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+");
        }
    }
    //Metodo de ataque
    private static void realizarDisparo(Tablero tableroEnemigo, Scanner scanner) {
        try {
            System.out.println("Introduce las coordenadas para atacar:");
            System.out.print("Fila: ");
            int filaAtaque = scanner.nextInt();
            System.out.print("Columna: ");
            int colAtaque = scanner.nextInt();
            boolean exito = tableroEnemigo.atacar(filaAtaque, colAtaque);
            if (exito) {
                System.out.println("¡Acertaste!");
            } else {
                System.out.println("Fallaste.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Inténtalo de nuevo.");
            scanner.next(); // Limpiar el escáner
        }
    }

    // Método para leer opciones del usuario, manejando excepciones
    private static int leerOpcion(Scanner scanner, int maxOpcion) {
        int opcion = -1;
        while (opcion < 1 || opcion > maxOpcion) {
            try {
                System.out.print("Elige una opción (1-" + maxOpcion + "): ");
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > maxOpcion) {
                    System.out.println("Opción fuera de rango. Inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Inténtalo de nuevo.");
                scanner.next(); // Limpiar el escáner
            }
        }
        return opcion;
    }
}

/* package Hundir_La_Flota;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Hundir_La_Flota.Barcos.TipoBarco;

public class HundirLaFlota {
    
    // Método que muestra el título del juego
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

    // Main del juego 
    public static void main(String[] args) {  
        // Inicializar el título, el tablero del jugador y el pirata con su propio tablero.
        mostrarTitulo();
        Scanner scanner = new Scanner(System.in);
        Tablero tableroJugador = new Tablero();
        Jugador jugador = new Jugador("Jugador", tableroJugador);
        Pirata pirata = new Pirata(new Tablero());

        // Menú para introducir barcos
        introducirBarcosMenu(jugador.getTablero(), scanner);

        // Iniciar la partida
        iniciarPartida(jugador, pirata, scanner);

        scanner.close();
    }

    private static void introducirBarcosMenu(Tablero tablero, Scanner scanner) {
        List<TipoBarco> barcosDisponibles = new ArrayList<>();
        for (TipoBarco tipo : TipoBarco.values()) {
            barcosDisponibles.add(tipo);
        }

        boolean todosBarcosColocados = false;

        while (!todosBarcosColocados) {
            System.out.println("\nMenú de Introducción de Barcos:");
            System.out.println("1. Introducir un barco");
            System.out.println("2. Ver tablero con los barcos colocados");

            int opcion = leerOpcion(scanner, 2);
            switch (opcion) {
                case 1:
                    introducirBarco(tablero, scanner, barcosDisponibles);
                    if (barcosDisponibles.isEmpty()) {
                        todosBarcosColocados = true;
                    }
                    break;
                case 2:
                    tablero.mostrarTablero();
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void introducirBarco(Tablero tablero, Scanner scanner, List<TipoBarco> barcosDisponibles) {
        if (barcosDisponibles.isEmpty()) {
            System.out.println("¡Ya has colocado todos los barcos!");
            return;
        }

        System.out.println("Selecciona el tipo de barco para introducir:");
        for (int i = 0; i < barcosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + barcosDisponibles.get(i).name());
        }

        int seleccion = leerOpcion(scanner, barcosDisponibles.size());
        TipoBarco tipoSeleccionado = barcosDisponibles.get(seleccion - 1);

        boolean colocado = false;
        while (!colocado) {
            try {
                System.out.println("Introduce los datos para colocar un " + tipoSeleccionado.name() + " de tamaño " + tipoSeleccionado.getTamanio() + ":");
                System.out.print("Fila inicial: ");
                int filaInicial = scanner.nextInt();
                System.out.print("Columna inicial: ");
                int colInicial = scanner.nextInt();
                System.out.print("Dirección (H para horizontal, V para vertical): ");
                char direccion = Character.toUpperCase(scanner.next().charAt(0));
                Barcos barco = new Barcos(tipoSeleccionado);
                if (tablero.colocarBarco(barco, filaInicial, colInicial, direccion)) {
                    System.out.println(tipoSeleccionado.name() + " colocado correctamente.");
                    barcosDisponibles.remove(tipoSeleccionado); // Elimina el barco colocado de la lista
                    colocado = true;
                } else {
                    System.out.println("Error al colocar el " + tipoSeleccionado.name() + ". Selecciona otra ubicación.");
                    return; // Volver al menú principal si no se puede colocar el barco
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Inténtalo de nuevo.");
                scanner.next(); // Limpiar el escáner
                return; // Volver al menú principal en caso de entrada no válida
            }
        }
    }

    private static void iniciarPartida(Jugador jugador, Pirata pirata, Scanner scanner) {
        int turno = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {
            System.out.println("\nRonda: " + turno);
            System.out.println("1. Ver mis disparos y mis barcos");
            System.out.println("2. Ver tablero de ataque");
            System.out.println("3. Realizar un disparo");

            int opcion = leerOpcion(scanner, 3);
            switch (opcion) {
                case 1:
                    mostrarInformacionDisparos(jugador.getTablero());
                    break;
                case 2:
                    mostrarTableroAtaque(pirata.getTablero());
                    break;
                case 3:
                    realizarDisparo(jugador, pirata.getTablero(), scanner);
                    // Ataque del pirata
                    pirata.disparar(jugador.getTablero());
                    if (jugador.getTablero().comprobarDerrota()) {
                        System.out.println("¡Has perdido! Todos tus barcos han sido hundidos.");
                        juegoActivo = false;
                    }
                    if (pirata.getTablero().comprobarDerrota()) {
                        System.out.println("¡Has ganado! Todos los barcos enemigos han sido hundidos.");
                        juegoActivo = false;
                    }
                    turno++;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void mostrarInformacionDisparos(Tablero tableroJugador) {
        System.out.println("Coordenadas de tus disparos fallidos:");
        tableroJugador.mostrarDisparos(); // Método actualizado en la clase Tablero
        System.out.println("Coordenadas de tus disparos acertados:");
        tableroJugador.mostrarDisparos();
        tableroJugador.mostrarTablero();
    }

    private static void mostrarTableroAtaque(Tablero tableroPirata) {
        System.out.println("Tablero de ataque:");
        // Lógica para mostrar el tablero de ataque con X, @ y ~
        System.out.println("    0   1   2   3   4  ");
        System.out.println("  +---+---+---+---+---+");
        for (int i = 0; i < 5; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 5; j++) {
                char c = tableroPirata.getEstadoCasilla(i, j);
                System.out.print(" " + (c == 'X' ? 'X' : c == '@' ? '@' : c == 'A' ? '~' : ' ') + " |");
            }
            System.out.println();
*/





   