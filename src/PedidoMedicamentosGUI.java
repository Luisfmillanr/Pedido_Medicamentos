import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Clase que gestiona la interfaz gráfica de usuario para realizar pedidos de medicamentos.
 * Utiliza componentes de Swing para la interacción del usuario.
 */
public class PedidoMedicamentosGUI extends JFrame {

    private JTextField nombreField;
    private JComboBox<String> tipoComboBox;
    private JTextField cantidadField;
    private JRadioButton cofarma, empsephar, cemefar;
    private JCheckBox principal, secundaria;
    private PedidoMedicamentosController controller;

    /**
     * Constructor de la GUI que recibe el controlador para manejar la lógica de negocio.
     * 
     * @param controller El controlador que gestiona la lógica entre la vista y el modelo.
     */
    public PedidoMedicamentosGUI(PedidoMedicamentosController controller) {
        this.controller = controller;
        initComponents();
    }

    // Inicializa los componentes gráficos de la interfaz
    private void initComponents() {
        setTitle("Sistema de Pedido de Medicamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new GridLayout(9, 2));

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
        JButton consultarButton = new JButton("Consultar Pedidos");

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

        consultarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarPedidos();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Añadir componentes al layout
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
        add(consultarButton);
    }

    // Realiza el pedido y lo guarda, mostrando un resumen si es válido
    private void realizarPedido() throws IOException {
        String nombre = nombreField.getText();
        String tipo = (String) tipoComboBox.getSelectedItem();
        String cantidadStr = cantidadField.getText();
        String distribuidor = cofarma.isSelected() ? "Cofarma" : empsephar.isSelected() ? "Empsephar" : cemefar.isSelected() ? "Cemefar" : null;
        String sucursales = (principal.isSelected() ? "Principal" : "") + (secundaria.isSelected() ? ", Secundaria" : "");

        if (controller.validarPedido(nombre, tipo, cantidadStr, distribuidor, sucursales)) {
            controller.guardarPedido(nombre, tipo, Integer.parseInt(cantidadStr), distribuidor, sucursales);
            mostrarResumenPedido(nombre, tipo, cantidadStr, distribuidor, sucursales);
        } else {
            JOptionPane.showMessageDialog(this, "Error en los datos del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpia el formulario de la GUI
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

    // Muestra los pedidos guardados en una ventana emergente
    private void mostrarPedidos() throws IOException {
        String[] pedidos = controller.consultarPedidos();
        JTextArea areaPedidos = new JTextArea();
        for (String pedido : pedidos) {
            areaPedidos.append(pedido + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(areaPedidos);

        JFrame frame = new JFrame("Consulta de Pedidos");
        frame.setSize(400, 300);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    // Muestra el resumen del pedido en una nueva ventana
    private void mostrarResumenPedido(String nombre, String tipo, String cantidad, String distribuidor, String sucursales) {
        JFrame resumenFrame = new JFrame("Pedido al distribuidor " + distribuidor);
        resumenFrame.setSize(400, 300);
        resumenFrame.setLayout(new GridLayout(4, 1));

        // Texto del pedido
        JLabel pedidoLabel = new JLabel(cantidad + " unidades del " + tipo + " " + nombre);
        resumenFrame.add(pedidoLabel);

        // Texto con la dirección
        String direccion = "Para la farmacia situada en ";
        if (sucursales.contains("Principal")) {
            direccion += "Calle de la Rosa n.28 ";
        }
        if (sucursales.contains("Secundaria")) {
            direccion += "y para la situada en Calle Alcazabilla n.3";
        }
        JLabel direccionLabel = new JLabel(direccion);
        resumenFrame.add(direccionLabel);

        // Botón "Enviar Pedido"
        JButton enviarButton = new JButton("Enviar Pedido");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pedido enviado");
                resumenFrame.dispose();
            }
        });
        resumenFrame.add(enviarButton);

        // Botón "Cancelar"
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumenFrame.dispose();
            }
        });
        resumenFrame.add(cancelarButton);

        // Mostrar la ventana
        resumenFrame.setVisible(true);
    }
}



