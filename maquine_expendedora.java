import java.util.HashMap;
import java.util.Map;

class Producto {
    private String nombre;
    private double precio;
    private boolean donaFopre;
    private int unidadesVendidas;

    public Producto(String nombre, double precio, boolean donaFopre) {
        this.nombre = nombre;
        this.precio = precio;
        this.donaFopre = donaFopre;
        this.unidadesVendidas = 0;
    }

    public void vender() {
        this.unidadesVendidas++;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDonaFopre() {
        return donaFopre;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }
}


/*La clase ProductoFactory implementa el patrón Factory Method. El método obtenerProducto() 
actúa como un método de fábrica que crea y devuelve instancias de la clase Producto*/

class ProductoFactory {
    private static Map<String, Producto> productos = new HashMap<>();

    public static Producto obtenerProducto(String nombre, double precio, boolean donaFopre) {
        if (!productos.containsKey(nombre)) {
            productos.put(nombre, new Producto(nombre, precio, donaFopre));
        }
        return productos.get(nombre);
    }
}

/* La clase MaquinaExpendedora implementa el patrón Singleton. Se asegura de que solo haya una instancia de 
la clase en toda la aplicación al utilizar un método estático getInstance() que devuelve la única instancia disponible*/

class MaquinaExpendedora {
    private static MaquinaExpendedora instance = null;
    private Map<String, Producto> productos;
    private double credito;

    private MaquinaExpendedora() {
        this.productos = new HashMap<>();
        this.credito = 0;
    }

    public static MaquinaExpendedora getInstance() {
        if (instance == null) {
            instance = new MaquinaExpendedora();
        }
        return instance;
    }

    public void agregarProducto(Producto producto) {
        productos.put(producto.getNombre(), producto);
    }

    public void agregarCredito(double monto) {
        this.credito += monto;
    }

    public boolean comprarProducto(String nombreProducto) {
        Producto producto = productos.get(nombreProducto);
        if (producto != null && credito >= producto.getPrecio()) {
            producto.vender();
            this.credito -= producto.getPrecio();
            return true;
        } else {
            return false;
        }
    }

    public int calcularTotalUnidades() {
        int total = 0;
        for (Producto producto : productos.values()) {
            total += producto.getUnidadesVendidas();
        }
        return total;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Producto producto : productos.values()) {
            total += producto.getPrecio() * producto.getUnidadesVendidas();
        }
        return total;
    }

    public double calcularPorcentajeDisponibilidad() {
        int capacidadMaxima = 100 * productos.size();
        return ((double) calcularTotalUnidades() / capacidadMaxima) * 100;
    }

    public Producto consultarProductoMasComprado() {
        Producto masComprado = null;
        int maxUnidades = 0;
        for (Producto producto : productos.values()) {
            if (producto.getUnidadesVendidas() > maxUnidades) {
                maxUnidades = producto.getUnidadesVendidas();
                masComprado = producto;
            }
        }
        return masComprado;
    }

    public double calcularDonacionTotalFopre() {
        double totalDonacion = 0;
        for (Producto producto : productos.values()) {
            if (producto.isDonaFopre()) {
                totalDonacion += producto.getPrecio() * producto.getUnidadesVendidas() * 0.06;
            }
        }
        return totalDonacion;
    }

    public Map<String, Double> calcularDonacionFoprePorTipo() {
        Map<String, Double> donacionPorTipo = new HashMap<>();
        for (Producto producto : productos.values()) {
            if (producto.isDonaFopre()) {
                double donacionProducto = producto.getPrecio() * producto.getUnidadesVendidas() * 0.06;
                donacionPorTipo.put(producto.getNombre(), donacionPorTipo.getOrDefault(producto.getNombre(), 0.0) + donacionProducto);
            }
        }
        return donacionPorTipo;
    }

    public Map<String, Integer> unidadesCompradasPorTipo() {
        Map<String, Integer> unidadesPorTipo = new HashMap<>();
        for (Producto producto : productos.values()) {
            unidadesPorTipo.put(producto.getNombre(), producto.getUnidadesVendidas());
        }
        return unidadesPorTipo;
    }

    public void terminarCompra() {
        this.credito = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        MaquinaExpendedora maquina = MaquinaExpendedora.getInstance();

        Producto producto1 = ProductoFactory.obtenerProducto("Galletas", 2.5, true);
        Producto producto2 = ProductoFactory.obtenerProducto("Refresco", 1.75, false);
        Producto producto3 = ProductoFactory.obtenerProducto("Chocolate", 1.0, true);

        maquina.agregarProducto(producto1);
        maquina.agregarProducto(producto2);
        maquina.agregarProducto(producto3);

        maquina.agregarCredito(5);
        maquina.comprarProducto("Galletas");
        maquina.comprarProducto("Chocolate");
        maquina.comprarProducto("Chocolate");

        // Imprimir estadísticas
     System.out.println("Unidades totales compradas: " + maquina.calcularTotalUnidades());
System.out.println("Valor total de las compras: " + maquina.calcularValorTotal());
System.out.println("Porcentaje de disponibilidad: " + maquina.calcularPorcentajeDisponibilidad());
System.out.println("Producto más comprado: " + maquina.consultarProductoMasComprado().getNombre());
System.out.println("Donación total al FOPRE: " + maquina.calcularDonacionTotalFopre());
System.out.println("Donación al FOPRE por tipo de producto: " + maquina.calcularDonacionFoprePorTipo());
        
    }


}
