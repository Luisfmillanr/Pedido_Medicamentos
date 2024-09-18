import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class PedidoMedicamentosGUI extends JFrame {

    private JTextField nombreField;
    private JComboBox<String> tipoComboBox;
    private JTextField cantidadField;
    private JRadioButton cofarma, empsephar, cemefar;
    private JCheckBox principal, secundaria;
    private PedidoMedicamentosController controller;

    public PedidoMedicamentosGUI(PedidoMedicamentosController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        // Inicializar componentes
        setTitle("Sistema de Pedido de Medicamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new GridLayout(8, 2));

        // Campos del formulario
        nombreField = new JTextField();
        tipoComboBox = new JComboBox<>(new String[]{"Analgésico", "Analéptico", "Anestésico", "Antiácido", "Antidepresivo", "Antibiótico"});
        cantidadField = new JTextField();
        cofarma = new JRadioButton("Cofarma");
        empsephar = new JRadioButton("Empsephar");
        cemefar = new JRadioButton("Cemefar");
        ButtonGroup distribuidorGroup = new ButtonGroup();
        distribuidorGroup.add(cofarma);
        distribuidorGroup.add(empsephar);
        distribuidorGroup.add(cemefar);
        principal = new JCheckBox("Principal");
        secundaria = new JCheckBox("Secundaria");

        // Botones
        JButton confirmarButton = new JButton("Confirmar");
        JButton borrarButton = new JButton("Borrar");

        confirmarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    realizarPedido();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        // Añadir componentes
        add(new JLabel("Nombre del Medicamento:"));
        add(nombreField);
        add(new JLabel("Tipo del Medicamento:"));
        add(tipoComboBox);
        add(new JLabel("Cantidad:"));
        add(cantidadField);
        add(new JLabel("Distribuidor:"));
        add(cofarma);
        add(empsephar);
        add(cemefar);
        add(new JLabel("Sucursal:"));
        add(principal);
        add(secundaria);
        add(confirmarButton);
        add(borrarButton);
    }

    // Realizar pedido
    private void realizarPedido() throws IOException {
        String nombre = nombreField.getText();
        String tipo = (String) tipoComboBox.getSelectedItem();
        String cantidadStr = cantidadField.getText();
        String distribuidor = cofarma.isSelected() ? "Cofarma" : empsephar.isSelected() ? "Empsephar" : cemefar.isSelected() ? "Cemefar" : null;
        String sucursales = (principal.isSelected() ? "Principal" : "") + (secundaria.isSelected() ? ", Secundaria" : "");

        if (controller.validarPedido(nombre, tipo, cantidadStr, distribuidor, sucursales)) {
            controller.guardarPedido(nombre, tipo, Integer.parseInt(cantidadStr), distribuidor, sucursales);
            JOptionPane.showMessageDialog(this, "Pedido realizado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error en los datos del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpiar formulario
    private void limpiarFormulario() {
        nombreField.setText("");
        cantidadField.setText("");
        tipoComboBox.setSelectedIndex(0);
        cofarma.setSelected(false);
        empsephar.setSelected(false);
        cemefar.setSelected(false);
        principal.setSelected(false);
        secundaria.setSelected(false);
    }

    public static void main(String[] args) {
        PedidoMedicamentoModel model = new PedidoMedicamentoModel();
        PedidoMedicamentosController controller = new PedidoMedicamentosController(model);
        PedidoMedicamentosGUI gui = new PedidoMedicamentosGUI(controller);
        gui.setVisible(true);
    }
}





