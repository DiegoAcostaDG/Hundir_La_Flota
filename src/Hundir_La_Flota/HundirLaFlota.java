package Hundir_La_Flota;
import java.util.Scanner;

public class HundirLaFlota {

    // Main para probar
	public static void main(String[] args) {
		// Main para probar
	        // Imprimir el título en la consola
	        System.out.println("*******************************************");
	        System.out.println("*            Hundir La Flota              *");
	        System.out.println("*                                         *");
	        System.out.println("*             Diego y Lucia               *");
	        System.out.println("*******************************************\n");

	        // Código del juego Hundir la Flota
	        Scanner scanner = new Scanner(System.in);
	        Tablero tablero = new Tablero();
	        System.out.println("\nEste es el tablero de juego: \n");
	        tablero.mostrarTablero();
	        System.out.println("Introduce los datos para colocar el barco:");
	        System.out.print("Fila inicial: ");
	        int filaInicial = scanner.nextInt();
	        System.out.print("Columna inicial: ");
	        int colInicial = scanner.nextInt();
	        System.out.print("Dirección (H para horizontal, V para vertical): ");
	        char direccion = scanner.next().charAt(0);

	        // Crear un barco y colocarlo en el tablero
	        Barcos barco1 = new Barcos(3, null);
	        if (tablero.colocarBarco(barco1, filaInicial, colInicial, direccion)) {
	            System.out.println("Barco colocado correctamente.");
	        } else {
	            System.out.println("Error al colocar el barco.");
	        }
	        //CODIGO PARA INCLUIR BARCO 2 
	        System.out.println("Introduce los datos para colocar el segundo barco");
	        System.out.print("Fila inicial: ");
	        int filaInicial2 = scanner.nextInt();
	        System.out.print("Columna inicial: ");
	        int colInicial2 = scanner.nextInt();
	        System.out.print("Dirección (H para horizontal, V para vertical): ");
	        char direccion2 = scanner.next().charAt(0);
	        
	        Barcos barco2= new Barcos (4, null);

		        if (tablero.colocarBarco(barco2, filaInicial2, colInicial2, direccion2)) {
		            System.out.println("Barco colocado correctamente.");

		        } else {
		            System.out.println("Error al colocar el barco.");
		        }
	        	
	        
	        tablero.mostrarTablero();
	        scanner.close();
	    }
}

