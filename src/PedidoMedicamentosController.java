import java.io.IOException;
import java.util.List;

/**
 * Controlador que gestiona la lógica entre el modelo y la vista.
 * Se encarga de validar los datos del pedido y delegar la lógica de guardar y cargar pedidos.
 */
public class PedidoMedicamentosController {

    private PedidoMedicamentoModel model;

    /**
     * Constructor que recibe el modelo para manejar los pedidos.
     * 
     * @param model El modelo de datos que gestiona el almacenamiento de pedidos.
     */
    public PedidoMedicamentosController(PedidoMedicamentoModel model) {
        this.model = model;
    }

    /**
     * Valida los datos del pedido antes de guardarlo.
     * 
     * @param nombre El nombre del medicamento.
     * @param tipo El tipo del medicamento (analgésico, antibiótico, etc.).
     * @param cantidadStr La cantidad solicitada del medicamento (como cadena).
     * @param distribuidor El distribuidor farmacéutico seleccionado.
     * @param sucursales Las sucursales de destino (principal, secundaria).
     * @return true si los datos son válidos, false de lo contrario.
     */
    public boolean validarPedido(String nombre, String tipo, String cantidadStr, String distribuidor, String sucursales) {
        if (nombre.isEmpty() || !nombre.matches("[a-zA-Z0-9 ]+")) {
            return false; // Nombre no válido
        }
        if (tipo == null || tipo.isEmpty()) {
            return false; // Tipo de medicamento no seleccionado
        }
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                return false; // Cantidad no válida
            }
        } catch (NumberFormatException e) {
            return false; // Cantidad no es un número
        }
        if (distribuidor == null || distribuidor.isEmpty()) {
            return false; // Distribuidor no seleccionado
        }
        if (sucursales == null || sucursales.isEmpty()) {
            return false; // Sucursal no seleccionada
        }
        return true;
    }

    /**
     * Guarda un pedido de medicamento en el archivo de texto.
     * 
     * @param nombre El nombre del medicamento.
     * @param tipo El tipo del medicamento (analgésico, antibiótico, etc.).
     * @param cantidad La cantidad solicitada del medicamento.
     * @param distribuidor El distribuidor farmacéutico seleccionado.
     * @param sucursales Las sucursales de destino (principal, secundaria).
     * @throws IOException Si ocurre un error de E/S al guardar el pedido.
     */
    public void guardarPedido(String nombre, String tipo, int cantidad, String distribuidor, String sucursales) throws IOException {
        model.guardarPedido(nombre, tipo, cantidad, distribuidor, sucursales);
    }

    /**
     * Consulta y devuelve una lista de pedidos guardados.
     * 
     * @return Un array de pedidos en formato de cadena.
     * @throws IOException Si ocurre un error de E/S al cargar los pedidos.
     */
    public String[] consultarPedidos() throws IOException {
        List<String> pedidos = model.cargarPedidos();
        return pedidos.toArray(new String[0]);
    }
}

