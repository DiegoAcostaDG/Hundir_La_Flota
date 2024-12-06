package Hundir_La_Flota;

public class Barcos {
    public enum TipoBarco {
        PORTAAVIONES(4), ACORAZADO(3), DESTRUCTOR(2), SUBMARINO(2);

        private final int tamanio;

        TipoBarco(int tamanio) {
            this.tamanio = tamanio;
        }

        public int getTamanio() {
            return tamanio;
        }
    }

    private TipoBarco tipo;

    public Barcos(TipoBarco tipo) {
        this.tipo = tipo;
    }

    public TipoBarco getTipo() {
        return tipo;
    }

    public int getTamanio() {
        return tipo.getTamanio();
    }
}


