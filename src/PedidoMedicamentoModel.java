import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona los pedidos de medicamentos.
 * Se encarga de guardar y cargar pedidos desde un archivo de texto.
 */
public class PedidoMedicamentoModel {

    private static final String ARCHIVO_PEDIDOS = "pedidos.txt";
    private static final String SEPARADOR_CAMPOS = " | ";

    /**
     * Guarda un pedido de medicamento en un archivo de texto.
     * El archivo se crea si no existe, y los pedidos se añaden al final del archivo.
     *
     * @param nombre El nombre del medicamento.
     * @param tipo El tipo del medicamento (analgésico, antibiótico, etc.).
     * @param cantidad La cantidad solicitada del medicamento.
     * @param distribuidor El distribuidor farmacéutico seleccionado.
     * @param sucursales Las sucursales de destino (principal, secundaria).
     * @throws IOException Si ocurre un error de E/S al guardar el pedido.
     */
    public void guardarPedido(String nombre, String tipo, int cantidad, String distribuidor, String sucursales) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PEDIDOS, true))) {
            bw.write(nombre + SEPARADOR_CAMPOS + tipo + SEPARADOR_CAMPOS + cantidad + SEPARADOR_CAMPOS + distribuidor + SEPARADOR_CAMPOS + sucursales);
            bw.newLine();
        }
    }

    /**
     * Carga todos los pedidos de medicamentos desde el archivo de texto.
     * Cada línea en el archivo representa un pedido, con los campos separados por el carácter "|".
     *
     * @return Una lista de cadenas, donde cada cadena representa un pedido en formato original.
     * @throws IOException Si ocurre un error de E/S al leer los pedidos.
     */
    public List<String> cargarPedidos() throws IOException {
        List<String> pedidos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PEDIDOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                pedidos.add(linea); // Añadir cada línea (pedido) a la lista
            }
        }
        return pedidos;
    }
}



