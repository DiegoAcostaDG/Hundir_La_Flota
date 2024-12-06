package Hundir_La_Flota;

import java.util.InputMismatchException;
import java.util.Scanner;

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

    public void setBarco(Hundir_La_Flota.Barcos.TipoBarco barco) {
        this.TipoBarco = TipoBarco;
    }
  
    public String toString() {
        return "Jugador: " + nombre + ", Barco: " + TipoBarco + ", Ataque: " + "\nTablero:\n" + tablero;
    }

    // Métodos adicionales según las necesidades del juego
     //Metodo de ataque
    public void realizarDisparo(Tablero tableroEnemigo, Scanner scanner) {
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
    private int leerOpcion(Scanner scanner, int maxOpcion) {
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