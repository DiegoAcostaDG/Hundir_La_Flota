package Hundir_La_Flota;

public class Pirata {

    private Tablero tablero;

    // Constructor de Pirata
    public Pirata(Tablero tablero) {
        this.tablero = tablero;
    }

    // Método para acceder al tablero del pirata
    public Tablero getTablero() {
        return this.tablero;
    }
 // Método para disparar al tablero del enemigo
    public static void disparar(Tablero tableroEnemigo) {
        int filaAleatoria = (int) (Math.random() * 5);  // Genera una fila aleatoria entre 0 y 4
        int colAleatoria = (int) (Math.random() * 5);  // Genera una columna aleatoria entre 0 y 4
        System.out.println("Pirata disparando a (" + filaAleatoria + ", " + colAleatoria + ")...");
        tableroEnemigo.atacar(filaAleatoria, colAleatoria);  // Realiza el disparo al tablero enemigo
    }

}



