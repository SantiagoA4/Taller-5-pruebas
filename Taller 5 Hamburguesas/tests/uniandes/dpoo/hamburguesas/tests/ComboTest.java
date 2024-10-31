package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {

    private Combo combo;
    private ProductoMenu menu1;
    private ProductoMenu menu2;
    private ArrayList<ProductoMenu> itemsCombo;
    private double descuento;

    @BeforeEach
    public void setUp() {
        menu1 = new ProductoMenu("Hamburguesa", 100);
        menu2 = new ProductoMenu("Papas Fritas", 50);
        itemsCombo = new ArrayList<>();
        itemsCombo.add(menu1);
        itemsCombo.add(menu2);
        descuento = 0.5;

        combo = new Combo("Combo Hamburguesa", descuento, itemsCombo);
    }

    @Test
    @DisplayName("Nombre")
    public void testGetNombre() {
        assertEquals("Combo Hamburguesa", combo.getNombre());
    }

    @Test
    @DisplayName("Precio")
    public void testGetPrecio() {
        int miPrecio = (int) (150 * (1 - descuento));
        assertEquals(miPrecio, combo.getPrecio());
    }

    @Test
    @DisplayName("Factura")
    public void testGenerarFactura() {
        String facturaEsperada = "Combo Combo Hamburguesa\n" +
                " Descuento: 0.5\n" +
                "            75\n";
        assertEquals(facturaEsperada, combo.generarTextoFactura());
    }
    
    @Test
    @DisplayName("Descuento")
    public void testGetDescuento() {
        assertEquals(descuento, combo.getDescuento());
    }
    
   
}



