package Hundir_La_Flota;

public class Jugador {

    private String nombre;
    private Tablero tablero;
    private Barcos TipoBarco;

    public Jugador(String nombre, Tablero tablero) {
        this.nombre = nombre;
        this.tablero = tablero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Barcos getBarco() {
        return TipoBarco;
    }
    public String toString() {
        return "Jugador: " + nombre + ", Barco: " + TipoBarco + ", Ataque: " + "\nTablero:\n" + tablero;
    }
}
