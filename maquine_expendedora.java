import java.util.ArrayList;
import java.util.List;

// Paso 1: Interfaz Manejador
interface Manejador {
    void procesarSolicitud(Transaccion transaccion);
    void establecerSiguienteManejador(Manejador siguienteManejador);
}

// Paso 2: Enumeración TipoTransaccion
enum TipoTransaccion {
    RETIRO,
    DEPOSITO,
    CONSULTA_SALDO
}

// Paso 3: Implementaciones concretas de Manejador
class ManejadorRetiro implements Manejador {
    private Manejador siguienteManejador;

    @Override
    public void procesarSolicitud(Transaccion transaccion) {
        if (transaccion.getTipo() == TipoTransaccion.RETIRO) {
            System.out.println("Procesando retiro...");
            // Lógica de procesamiento de retiro
        } else if (siguienteManejador != null) {
            siguienteManejador.procesarSolicitud(transaccion);
        }
    }

    @Override
    public void establecerSiguienteManejador(Manejador siguienteManejador) {
        this.siguienteManejador = siguienteManejador;
    }
}

class ManejadorDeposito implements Manejador {
    private Manejador siguienteManejador;

    @Override
    public void procesarSolicitud(Transaccion transaccion) {
        if (transaccion.getTipo() == TipoTransaccion.DEPOSITO) {
            System.out.println("Procesando depósito...");
            // Lógica de procesamiento de depósito
        } else if (siguienteManejador != null) {
            siguienteManejador.procesarSolicitud(transaccion);
        }
    }

    @Override
    public void establecerSiguienteManejador(Manejador siguienteManejador) {
        this.siguienteManejador = siguienteManejador;
    }
}

class ManejadorConsultaSaldo implements Manejador {
    private Manejador siguienteManejador;

    @Override
    public void procesarSolicitud(Transaccion transaccion) {
        if (transaccion.getTipo() == TipoTransaccion.CONSULTA_SALDO) {
            System.out.println("Procesando consulta de saldo...");
            // Lógica de consulta de saldo
        } else if (siguienteManejador != null) {
            siguienteManejador.procesarSolicitud(transaccion);
        }
    }

    @Override
    public void establecerSiguienteManejador(Manejador siguienteManejador) {
        this.siguienteManejador = siguienteManejador;
    }
}

// Paso 4: Clase ServicioCAJERO
class ServicioCAJERO {
    private Manejador manejadorInicio;

    public ServicioCAJERO() {
        // Crear instancias de los diferentes manejadores concretos
        ManejadorRetiro manejadorRetiro = new ManejadorRetiro();
        ManejadorDeposito manejadorDeposito = new ManejadorDeposito();
        ManejadorConsultaSaldo manejadorConsultaSaldo = new ManejadorConsultaSaldo();

        // Establecer la cadena de responsabilidad
        manejadorRetiro.establecerSiguienteManejador(manejadorDeposito);
        manejadorDeposito.establecerSiguienteManejador(manejadorConsultaSaldo);

        this.manejadorInicio = manejadorRetiro;
    }

    public void procesarTransaccion(Transaccion transaccion) {
        manejadorInicio.procesarSolicitud(transaccion);
    }
}

// Paso 5: Clase Transaccion
class Transaccion {
    private TipoTransaccion tipo;

    public Transaccion(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }
}

// Paso 6: Clase Main para probar la implementación
public class Main {
    public static void main(String[] args) {
        // Paso 6: Probar la implementación
        ServicioCAJERO servicioCAJERO = new ServicioCAJERO();
        
        // Crear y procesar diferentes tipos de transacciones
        Transaccion transaccion1 = new Transaccion(TipoTransaccion.RETIRO);
        Transaccion transaccion2 = new Transaccion(TipoTransaccion.DEPOSITO);
        Transaccion transaccion3 = new Transaccion(TipoTransaccion.CONSULTA_SALDO);

        servicioCAJERO.procesarTransaccion(transaccion1);
        servicioCAJERO.procesarTransaccion(transaccion2);
        servicioCAJERO.procesarTransaccion(transaccion3);
    }
}
