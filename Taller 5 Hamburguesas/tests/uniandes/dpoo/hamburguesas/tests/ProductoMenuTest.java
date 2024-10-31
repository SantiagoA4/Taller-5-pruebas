package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {
    
    private ProductoMenu menu1;

    @BeforeEach
    public void setUp() {
        menu1 = new ProductoMenu("Hamburguesas", 100);
    }
    
    @Test
    @DisplayName("Nombre")
    public void miNombre() {
    	String miNombre = "Hamburguesas";
        assertEquals(miNombre, menu1.getNombre());

    }

    @Test
    @DisplayName("Prueba Factura")
    public void generarFactura() {
        String miFactura = "Hamburguesas\n            100\n";
        assertEquals(miFactura, menu1.generarTextoFactura());
    }
}


