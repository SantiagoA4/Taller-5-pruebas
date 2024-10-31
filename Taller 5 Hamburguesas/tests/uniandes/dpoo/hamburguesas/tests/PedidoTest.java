package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {

    private Pedido pedido;
    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido("Santiago", "Calle 100");
        producto1 = new ProductoMenu("Hamburguesa", 100);
        producto2 = new ProductoMenu("Papas Fritas", 50);

        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
    }

    @Test
    @DisplayName("Id Pedido")
    public void testGetIdPedido() {
        
        assertEquals(6, pedido.getIdPedido());
    }

    @Test
    @DisplayName("Nombre Cliente")
    public void testGetNombreCliente() {
        assertEquals("Santiago", pedido.getNombreCliente());
    }

    @Test
    @DisplayName("Agregar Producto")
    public void testAgregarProducto() {
        
    }

    @Test
    @DisplayName("Precio Neto del Pedido")
    public void testGetPrecioNetoPedido() {
        int expectedNeto = 100 + 50; 
        assertEquals(expectedNeto, pedido.getPrecioTotalPedido() - pedido.getPrecioIVAPedido());
    }

    @Test
    @DisplayName("Precio IVA del Pedido")
    public void testGetPrecioIVAPedido() {
        int expectedIVA = (int) ((100 + 50) * 0.19); 
        assertEquals(expectedIVA, pedido.getPrecioTotalPedido() - pedido.getPrecioNetoPedido());
    }

    @Test
    @DisplayName("Precio Total del Pedido")
    public void testGetPrecioTotalPedido() {
        int precioNeto = 100 + 50;
        int expectedTotal = precioNeto + (int) (precioNeto * 0.19); 
        assertEquals(expectedTotal, pedido.getPrecioTotalPedido());
    }

    @Test
    @DisplayName("Generar Texto Factura")
    public void testGenerarTextoFactura() {
        String factura = pedido.generarTextoFactura();

        assertTrue(factura.contains("Cliente: Santiago"));
        assertTrue(factura.contains("Dirección: Calle 100"));

        assertTrue(factura.contains("Hamburguesa"));
        assertTrue(factura.contains("100"));
        assertTrue(factura.contains("Papas Fritas"));
        assertTrue(factura.contains("50"));

        int precioNeto = 100 + 50;
        int expectedIVA = (int) (precioNeto * 0.19);
        int precioTotal = precioNeto + expectedIVA;

        assertTrue(factura.contains("Precio Neto:  " + precioNeto));
        assertTrue(factura.contains("IVA:          " + expectedIVA));
        assertTrue(factura.contains("Precio Total: " + precioTotal));
    }
    
    @Test
    @DisplayName("Guardar Factura")
    public void testGuardarFactura() throws FileNotFoundException, IOException {
        File archivoTemporal = File.createTempFile("facturaTest", ".txt");
        archivoTemporal.deleteOnExit(); 

        pedido.guardarFactura(archivoTemporal);

        String contenidoArchivo = new String(Files.readAllBytes(Paths.get(archivoTemporal.getPath())));

        String facturaEsperada = pedido.generarTextoFactura();

        assertEquals(facturaEsperada, contenidoArchivo);
    }
}


