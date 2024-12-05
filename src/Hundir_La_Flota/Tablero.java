package Hundir_La_Flota;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private char[][] tablero;
    private List<Barcos> barcos;

    public Tablero() {
        tablero = new char[10][10];
        barcos = new ArrayList<>();
        // Llenar el tablero con espacios vacíos
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = ' ';
            }
        }
    }

    // Método para agregar un barco al tablero
    public boolean agregarBarco(Barcos barco) {
        int[][] coords = barco.getCoordenadas();
        for (int[] coord : coords) {
            int fila = coord[0];
            int col = coord[1];
            if (fila < 0 || fila >= 10 || col < 0 || col >= 10 || tablero[fila][col] != ' ') {
                return false; // Coordenada inválida o lugar ya ocupado
            }
        }
        for (int[] coord : coords) {
            tablero[coord[0]][coord[1]] = 'B'; // Marcar el barco en el tablero
        }
        barcos.add(barco);
        return true;
    }

    // Método para colocar un barco en una posición inicial y dirección
    public boolean colocarBarco(Barcos barco, int filaInicial, int colInicial, char direccion) {
        int tamanio = barco.getTamanio();
        int[][] coords = new int[tamanio][2];

        for (int i = 0; i < tamanio; i++) {
            if (direccion == 'H') { // Horizontal
                if (colInicial + i >= 10) return false; // Fuera del tablero
                coords[i][0] = filaInicial;
                coords[i][1] = colInicial + i;
            } else if (direccion == 'V') { // Vertical
                if (filaInicial + i >= 10) return false; // Fuera del tablero
                coords[i][0] = filaInicial + i;
                coords[i][1] = colInicial;
            } else {
                return false; // Dirección inválida
            }

            // Verificar si las coordenadas están libres
            if (tablero[coords[i][0]][coords[i][1]] != ' ') {
                return false; // Lugar ya ocupado
            }
        }

        // Establecer las coordenadas del barco y agregar al tablero
        barco.setCoordenadas(coords);
        return agregarBarco(barco);
    }

    // Método para mostrar el tablero
    public void mostrarTablero() {
        System.out.println("    0   1   2   3   4   5   6   7   8   9  ");
        System.out.println("  +---+---+---+---+---+---+---+---+---+---+");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + tablero[i][j] + " |");
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+---+---+");
        }
    }

    // Main para probar
    public static void main(String[] args) {
        Tablero tablero = new Tablero();

        // Crear un barco y colocarlo en el tablero
        Barcos barco1 = new Barcos(3, null);
        if (tablero.colocarBarco(barco1, 1, 1, 'H')) {
            System.out.println("Barco colocado correctamente.");
        } else {
            System.out.println("Error al colocar el barco.");
        }

        tablero.mostrarTablero();
    }
}