package Hundir_La_Flota;

public class Tablero {

    private char[][] tablero;
    private boolean[][] disparosRealizados;
    private final int TAMANIO_TABLERO = 5;

    // Constructor: Inicializa el tablero y los disparos realizados
    public Tablero() {
        tablero = new char[TAMANIO_TABLERO][TAMANIO_TABLERO];
        disparosRealizados = new boolean[TAMANIO_TABLERO][TAMANIO_TABLERO];

        // Inicializamos el tablero con agua ('~')
        for (int i = 0; i < TAMANIO_TABLERO; i++) {
            for (int j = 0; j < TAMANIO_TABLERO; j++) {
                tablero[i][j] = '~'; // Agua
                disparosRealizados[i][j] = false; // No se ha realizado disparo
            }
        }
    }

    // -------------------------------------------------------------------
    // Método para mostrar el tablero del jugador
    public void mostrarTablero() {
        System.out.println("    0   1   2   3   4");
        System.out.println("  +---+---+---+---+---+");
        for (int i = 0; i < TAMANIO_TABLERO; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < TAMANIO_TABLERO; j++) {
                System.out.print(" " + tablero[i][j] + " |");
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+");
        }
    }

    // -------------------------------------------------------------------
    // Método para obtener el estado de una casilla específica
    public char getEstadoCasilla(int fila, int columna) {
        // Verificamos que las coordenadas sean válidas
        if (fila >= 0 && fila < TAMANIO_TABLERO && columna >= 0 && columna < TAMANIO_TABLERO) {
            return tablero[fila][columna]; // Devuelve el estado de la casilla en el tablero
        }
        return '~'; // Si las coordenadas están fuera del rango, devolvemos un valor por defecto (agua)
    }

    // -------------------------------------------------------------------
    // Método para colocar un barco en el tablero
    public boolean colocarBarco(Barcos barco, int fila, int columna, char direccion) {
        int longitudBarco = barco.getTamanio(); // Longitud del barco

        if (direccion == 'H') { // Colocación horizontal
            if (columna + longitudBarco > TAMANIO_TABLERO) {
                return false; // El barco no cabe
            }
            for (int i = columna; i < columna + longitudBarco; i++) {
                if (tablero[fila][i] != '~') {
                    return false; // Ya hay un barco en esa casilla
                }
            }
            // Colocamos el barco
            for (int i = columna; i < columna + longitudBarco; i++) {
                tablero[fila][i] = 'B'; // 'B' representa el barco
            }
            return true;

        } else if (direccion == 'V') { // Colocación vertical
            if (fila + longitudBarco > TAMANIO_TABLERO) {
                return false; // El barco no cabe
            }
            for (int i = fila; i < fila + longitudBarco; i++) {
                if (tablero[i][columna] != '~') {
                    return false; // Ya hay un barco en esa casilla
                }
            }
            // Colocamos el barco
            for (int i = fila; i < fila + longitudBarco; i++) {
                tablero[i][columna] = 'B'; // 'B' representa el barco
            }
            return true;
        }

        return false;
    }

    // -------------------------------------------------------------------
    // Método para realizar un disparo
    public boolean atacar(int fila, int columna) {
        if (fila < 0 || fila >= TAMANIO_TABLERO || columna < 0 || columna >= TAMANIO_TABLERO) {
            System.out.println("Coordenadas fuera de los límites del tablero.");
            return false;
        }

        if (disparosRealizados[fila][columna]) {
            System.out.println("Ya has disparado a esta casilla.");
            return false;
        }

        disparosRealizados[fila][columna] = true;

        // Si el disparo da en un barco
        if (tablero[fila][columna] == 'B') {
            tablero[fila][columna] = '@'; // '@' representa un disparo acertado sobre el barco
            System.out.println("¡Acertaste! Has golpeado un barco.");
            return true;
        } else {
            tablero[fila][columna] = '~'; // '~' representa un disparo fallido (agua)
            System.out.println("Fallaste, es agua.");
            return false;
        }
    }

    // -------------------------------------------------------------------
    // Método para verificar si el jugador ha ganado (si todos los barcos han sido hundidos)
    public boolean comprobarDerrota() {
        for (int i = 0; i < TAMANIO_TABLERO; i++) {
            for (int j = 0; j < TAMANIO_TABLERO; j++) {
                if (tablero[i][j] == 'B') {
                    return false; // Si aún hay un barco en el tablero, no ha sido derrotado
                }
            }
        }
        return true; // Si no hay barcos en el tablero, el jugador ha ganado
    }

    // -------------------------------------------------------------------
    // Método para mostrar los disparos realizados (con coordenadas)
    public void mostrarDisparos() {
        System.out.println("Disparos realizados:");
        for (int i = 0; i < TAMANIO_TABLERO; i++) {
            for (int j = 0; j < TAMANIO_TABLERO; j++) {
                if (disparosRealizados[i][j]) {
                    System.out.print("(" + i + ", " + j + ") ");
                }
            }
        }
        System.out.println();
    }

    // -------------------------------------------------------------------
    // Método para mostrar el tablero con los disparos y barcos
    public void mostrarTableroConDisparos() {
        System.out.println("    0   1   2   3   4");
        System.out.println("  +---+---+---+---+---+");
        for (int i = 0; i < TAMANIO_TABLERO; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < TAMANIO_TABLERO; j++) {
                char c = tablero[i][j];
                if (disparosRealizados[i][j]) {
                    System.out.print(" " + (c == '@' ? '@' : '~') + " |");
                } else {
                    System.out.print(" " + c + " |");
                }
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+");
        }
    }

    // -------------------------------------------------------------------
    // Método para mostrar el estado del tablero actual
    public void mostrarEstadoTablero() {
        System.out.println("Estado actual del tablero:");
        mostrarTableroConDisparos(); // Muestra el tablero con los disparos
    }
}
