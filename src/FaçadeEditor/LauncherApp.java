package FaçadeEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import ToDoListOperation.BoolTache;
import ToDoListOperation.ComplexTache;
import ToDoListOperation.Priorite;
import ToDoListOperation.SimpleTache;
import ToDoListOperation.Tache;
import ToDoListOperation.TodoListImpl;
import XmlParser.XMLParser;

/**
 * @author KHICHA
 * @author TIRECHE
 * Cette classe représente l'interface graphique de l'application de gestion de ToDoList.
 * Elle fournit une interface utilisateur pour créer, modifier, importer et sauvegarder des tâches.
 */
public class LauncherApp extends JFrame {

    private JButton btnCreateSimple, btnCreateBoolean, btnCreateComplex, btnImportXML, btnSave;
    IFacade facade;
    JTable table;
    DefaultTableModel model;
    public TodoListImpl todoList;

    /**
     * Constructeur par défaut de l'application de gestion de ToDoList.
     */
    public LauncherApp() {
        super("Gestionnaire de ToDoList");
        todoList = new TodoListImpl();
        createUI();
    }

    /**
     * Initialise et configure l'interface graphique de l'application.
     */
    public void createUI() {
        try {
            setLayout(new BorderLayout());
            setupMainTable();
            setupButtonsPanel();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 400);
            setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'importation du fichier XML: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configure la table principale de l'interface graphique.
     */
    public void setupMainTable() {
        model = new DefaultTableModel(new String[]{"ID", "Description", "Type", "Échéance", "Priorité", "Progression"}, 0);
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Tache selectedTask = todoList.getAllTasks().get(selectedRow);
                    ((Facade) facade).showTaskDetails(selectedTask);
                }
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        facade = new Facade(todoList,model);
    }

    /**
     * Configure le panneau de boutons de l'interface graphique.
     */
    private void setupButtonsPanel() {
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnCreateSimple = new JButton("Créer Tâche Simple");
        btnCreateBoolean = new JButton("Créer Tâche Booléenne");
        btnCreateComplex = new JButton("Créer Tâche Complexe");
        btnImportXML = new JButton("Importer XML");
        btnSave = new JButton("Sauvegarder");
        panelButtons.add(btnCreateSimple);
        panelButtons.add(btnCreateBoolean);
        panelButtons.add(btnCreateComplex);
        panelButtons.add(btnImportXML);
        panelButtons.add(btnSave);
        add(panelButtons, BorderLayout.SOUTH);
        attachEventHandlers();
    }

    /**
     * Attache les gestionnaires d'événements aux boutons de l'interface graphique.
     */
    private void attachEventHandlers() {
        btnCreateSimple.addActionListener(e -> facade.createSimpleTask());
        btnCreateBoolean.addActionListener(e -> facade.createBooleanTask());
        btnCreateComplex.addActionListener(e -> facade.createComplexTask());
        btnImportXML.addActionListener(e -> facade.importXML());
        btnSave.addActionListener(e -> facade.saveToDoList());
    }

}
