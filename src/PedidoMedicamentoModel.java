import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoMedicamentoModel {

    private static final String ARCHIVO_PEDIDOS = "pedidos.txt";

    // Guardar pedido en archivo de texto
    public void guardarPedido(String nombre, String tipo, int cantidad, String distribuidor, String sucursales) throws IOException {
        FileWriter fw = new FileWriter(ARCHIVO_PEDIDOS, true); // true para añadir al final del archivo
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(nombre + "|" + tipo + "|" + cantidad + "|" + distribuidor + "|" + sucursales);
        bw.newLine();
        bw.close();
    }

    // Cargar pedidos desde archivo de texto
    public List<String> cargarPedidos() throws IOException {
        List<String> pedidos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PEDIDOS));
        String linea;
        while ((linea = br.readLine()) != null) {
            pedidos.add(linea);
        }
        br.close();
        return pedidos;
    }
}


