import java.io.IOException;

public class PedidoMedicamentosController {

    private PedidoMedicamentoModel model;

    public PedidoMedicamentosController(PedidoMedicamentoModel model) {
        this.model = model;
    }

    // Validar los datos del formulario
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

    // Guardar el pedido
    public void guardarPedido(String nombre, String tipo, int cantidad, String distribuidor, String sucursales) throws IOException {
        model.guardarPedido(nombre, tipo, cantidad, distribuidor, sucursales);
    }

    // Consultar pedidos
    public String[] consultarPedidos() throws IOException {
        return model.cargarPedidos().toArray(new String[0]);
    }
}


