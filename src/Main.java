public class Main {
    public static void main(String[] args) {
        // Crear la instancia del modelo
        PedidoMedicamentoModel model = new PedidoMedicamentoModel();
        
        // Crear la instancia del controlador y pasarle el modelo
        PedidoMedicamentosController controller = new PedidoMedicamentosController(model);
        
        // Crear la instancia de la GUI y pasarle el controlador
        PedidoMedicamentosGUI gui = new PedidoMedicamentosGUI(controller);
        
        // Hacer visible la interfaz gráfica
        gui.setVisible(true);
    }
}



