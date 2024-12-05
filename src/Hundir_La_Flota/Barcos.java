package Hundir_La_Flota;

public class Barcos {
    // Atributos de la clase Barcos
    private int tamanio;
    private int[][] coordenadas; // Modificado para ser bidimensional
    private boolean hundido;

    // Constructor de la clase Barcos
    public Barcos(int tamanio, int[][] coordenadas) {
        this.tamanio = tamanio;
        this.coordenadas = coordenadas;
        this.hundido = false;
    }

    // Getters y Setters
    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public int[][] getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(int[][] coordenadas) {
        this.coordenadas = coordenadas;
    }

    public boolean isHundido() {
        return hundido;
    }

    public void setHundido(boolean hundido) {
        this.hundido = hundido;
    }
}
