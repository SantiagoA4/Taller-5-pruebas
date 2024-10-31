package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;

public class ProductoAjustadoTest {
	
	private ProductoAjustado productoAjustado;
	private Ingrediente tocineta;
	private Ingrediente queso;
	private Ingrediente tomate;
	
	@BeforeEach
	public void setUp() {
		ProductoMenu hamburguesa = new ProductoMenu("Hamburguesa", 100);
        productoAjustado = new ProductoAjustado(hamburguesa);
        
		tocineta = new Ingrediente("Tocineta", 10);
		queso = new Ingrediente("Queso", 15);
		tomate = new Ingrediente("Tomate", 5);
		
		productoAjustado.agregarIngrediente(tocineta);
        productoAjustado.agregarIngrediente(queso);
        productoAjustado.eliminarIngrediente(tomate);
        
	}
	
	@Test
	@DisplayName("Nombre")
	public void getNombre() {
		String miNombre = "Hamburguesa";
		assertEquals(miNombre, productoAjustado.getNombre());
	}
	
	@Test
    @DisplayName("Agregar Ingrediente")
	public void getPrecio() {
		int miPrecio = 100 + 10 + 15;
        assertEquals(miPrecio, productoAjustado.getPrecio());
	
	}
	
	@Test
	@DisplayName("Factura")
	public void generarFactura() {
        String miFactura = 
                "    +Tocineta                10\n" +
                "    +Queso                15\n" +
                "    -Tomate\n" +
                "            125\n";
        assertEquals(miFactura, productoAjustado.generarTextoFactura());
    }
}


