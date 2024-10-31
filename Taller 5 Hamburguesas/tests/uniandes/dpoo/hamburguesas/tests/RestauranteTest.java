package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.*;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante();
    }

    @Test
    @DisplayName("Iniciar Pedido Exitoso")
    public void testIniciarPedidoExitoso() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Carlos", "Calle 50");
        Pedido pedidoEnCurso = restaurante.getPedidoEnCurso();
        assertNotNull(pedidoEnCurso);
        assertEquals("Carlos", pedidoEnCurso.getNombreCliente());
    }

    @Test
    @DisplayName("Error Al Iniciar Pedido Existente")
    public void testIniciarPedidoYaExistente() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Carlos", "Calle 50");
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Ana", "Calle 10");
        });
    }

    @Test
    @DisplayName("Cerrar Pedido Sin Pedido En Curso")
    public void testCerrarPedidoSinPedidoEnCurso() {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }

    @Test
    @DisplayName("Cerrar y Guardar Pedido Exitosamente")
    public void testCerrarYGuardarPedidoExitoso() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
        restaurante.iniciarPedido("Carlos", "Calle 50");
        File facturaFile = new File("./facturas/factura_1.txt");
        facturaFile.deleteOnExit();

        restaurante.cerrarYGuardarPedido();
        
        assertNull(restaurante.getPedidoEnCurso());
        assertTrue(facturaFile.exists());
    }

    @Test
    @DisplayName("Cargar Ingredientes Exitosamente")
    public void testCargarIngredientes() throws IOException, HamburguesaException {
        File ingredientesFile = createTempFileWithContent("Tomate;500\nLechuga;300\n");
        restaurante.cargarInformacionRestaurante(ingredientesFile, new File("menu.txt"), new File("combos.txt"));
        ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();

        assertEquals(2, ingredientes.size());
        assertEquals("Tomate", ingredientes.get(0).getNombre());
        assertEquals(500, ingredientes.get(0).getCostoAdicional());
    }

    @Test
    @DisplayName("Cargar Ingrediente Repetido")
    public void testCargarIngredienteRepetido() throws IOException {
        File ingredientesFile = createTempFileWithContent("Tomate;500\nTomate;300\n");
        assertThrows(IngredienteRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(ingredientesFile, new File("menu.txt"), new File("combos.txt"));
        });
    }

    @Test
    @DisplayName("Cargar Menu Exitosamente")
    public void testCargarMenu() throws IOException, HamburguesaException {
        File menuFile = createTempFileWithContent("Hamburguesa;1000\nPapas Fritas;500\n");
        restaurante.cargarInformacionRestaurante(new File("ingredientes.txt"), menuFile, new File("combos.txt"));
        ArrayList<ProductoMenu> menuBase = restaurante.getMenuBase();

        assertEquals(2, menuBase.size());
        assertEquals("Hamburguesa", menuBase.get(0).getNombre());
        assertEquals(1000, menuBase.get(0).getPrecio());
    }

    @Test
    @DisplayName("Cargar Producto Repetido en Menu")
    public void testCargarProductoRepetidoEnMenu() throws IOException {
        File menuFile = createTempFileWithContent("Hamburguesa;1000\nHamburguesa;800\n");
        assertThrows(ProductoRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(new File("ingredientes.txt"), menuFile, new File("combos.txt"));
        });
    }

    @Test
    @DisplayName("Cargar Combo Exitosamente")
    public void testCargarCombo() throws IOException, HamburguesaException {
        File ingredientesFile = createTempFileWithContent("Tomate;500\nLechuga;300\n");
        File menuFile = createTempFileWithContent("Hamburguesa;1000\nPapas Fritas;500\n");
        File combosFile = createTempFileWithContent("Combo 1;10%;Hamburguesa;Papas Fritas\n");

        restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, combosFile);
        ArrayList<Combo> combos = restaurante.getMenuCombos();

        assertEquals(1, combos.size());
        assertEquals("Combo 1", combos.get(0).getNombre());
        assertEquals(0.1, combos.get(0).getDescuento(), 0.01);
    }

    @Test
    @DisplayName("Cargar Combo con Producto Faltante")
    public void testCargarComboConProductoFaltante() throws IOException {
        File menuFile = createTempFileWithContent("Hamburguesa;1000\n");
        File combosFile = createTempFileWithContent("Combo 1;10%;Hamburguesa;Papas Fritas\n");

        assertThrows(ProductoFaltanteException.class, () -> {
            restaurante.cargarInformacionRestaurante(new File("ingredientes.txt"), menuFile, combosFile);
        });
    }

    private File createTempFileWithContent(String content) throws IOException {
        File tempFile = File.createTempFile("testFile", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }
}

