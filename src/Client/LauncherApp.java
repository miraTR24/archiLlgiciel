
package Client;

import projetXml.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import FactoryMethodParser.XMLParser;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import projetXml.Tache;

public class LauncherApp extends JFrame {
    private JTable table;
    private TodoListImpl todoList;
    private JButton btnCreateSimple, btnCreateBoolean, btnCreateComplex, btnImportXML, btnSave;
    private DefaultTableModel model;

    public LauncherApp() {
        super("Gestionnaire de ToDoList");
        todoList = new TodoListImpl();
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Description", "Type", "Échéance", "Priorité", "Progression"});
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Tache selectedTask = todoList.getAllTasks().get(selectedRow);
                    showTaskDetails(selectedTask);
                }
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);

        attachEventHandlers();
    }

    private void attachEventHandlers() {
        btnCreateSimple.addActionListener(e -> createSimpleTask());
        btnCreateBoolean.addActionListener(e -> createBooleanTask());
        btnCreateComplex.addActionListener(e -> createComplexTask());
        btnImportXML.addActionListener(e -> importXML());
        btnSave.addActionListener(e -> saveToDoList());
    }

    private void showTaskDetails(Tache task) {
        JFrame detailsFrame = new JFrame("Détails de la tâche");
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setSize(400, 300);
        detailsFrame.setLocationRelativeTo(this);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        detailsPanel.add(new JLabel("Description :"));
        detailsPanel.add(new JLabel(task.getDescription()));
        detailsPanel.add(new JLabel("Type :"));
        detailsPanel.add(new JLabel(task.getClass().getSimpleName()));
        detailsPanel.add(new JLabel("Échéance :"));
        detailsPanel.add(new JLabel(task.getDeadline().toString()));
        detailsPanel.add(new JLabel("Priorité :"));
        detailsPanel.add(new JLabel(task.getPriorite().toString()));
        detailsPanel.add(new JLabel("Progression :"));
        detailsPanel.add(new JLabel(task.getProgress() + "%"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton modifyButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        modifyButton.addActionListener(e -> modifyTask(task));
        deleteButton.addActionListener(e -> deleteTask(task));

        if (task instanceof ComplexTache) {
            JButton viewSubtasksButton = new JButton("Voir les sous-tâches");
            viewSubtasksButton.addActionListener(e -> showSubtasks((ComplexTache) task));
            buttonPanel.add(viewSubtasksButton);
        }

        detailsFrame.add(detailsPanel, BorderLayout.CENTER);
        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        detailsFrame.setVisible(true);
    }

    private void modifyTask(Tache task) {
        // Ouvrir une nouvelle fenêtre pour modifier les détails de la tâche
        // et mettre à jour la tâche dans la TodoList
        updateTableModel();
    }

    private void deleteTask(Tache task) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette tâche ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            todoList.removeTask(task);
            updateTableModel();
        }
    }

    private void showSubtasks(ComplexTache complexTask) {
        JFrame subtasksFrame = new JFrame("Sous-tâches");
        subtasksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subtasksFrame.setSize(600, 400);
        subtasksFrame.setLocationRelativeTo(this);

        JTable subtasksTable = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Description", "Échéance", "Priorité", "Progression"}, 0
        ));
        subtasksTable.setFillsViewportHeight(true);

        for (Tache subtask : complexTask.getSubTaches()) {
            ((DefaultTableModel) subtasksTable.getModel()).addRow(new Object[]{
                subtask.getDescription(), subtask.getDeadline(), subtask.getPriorite(), subtask.getProgress()
            });
        }

        subtasksFrame.add(new JScrollPane(subtasksTable), BorderLayout.CENTER);
        subtasksFrame.setVisible(true);
    }

    private void createSimpleTask() {
        String description = JOptionPane.showInputDialog("Entrez la description de la tâche simple:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité",
                JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));

        SimpleTache task = new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
        todoList.addTask(task);
        updateTableModel();
    }

    private void createBooleanTask() {
        String description = JOptionPane.showInputDialog("Entrez la description de la tâche booléenne:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité",
                JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
        boolean isCompleted = JOptionPane.showConfirmDialog(this, "La tâche est-elle terminée ?", "Tâche terminée",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        BoolTache task = new BoolTache(description, deadline, priorite, estimatedDuration, progress, isCompleted);
        todoList.addTask(task);
        updateTableModel();
    }

    private void createComplexTask() {
        String description = JOptionPane.showInputDialog("Entrez la description de la tâche complexe (max 20 caractères):");
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité",
                JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.BASSE);
        

     


        int numSubtasks = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nombre de sous-tâches:"));
        for (int i = 0; i < numSubtasks; i++) {
            String subTaskDesc = JOptionPane.showInputDialog("Entrez la description de la sous-tâche " + (i + 1) + ":");
            LocalDate subTaskDeadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
            Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité",
                    JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.HAUTE);
            int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
            int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));

            List<Tache> subTasks = new ArrayList<>();
            subTasks.add(new SimpleTache(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress));
            subTasks.add(new SimpleTache("Servir le dîner", LocalDate.now().plusDays(3), Priorite.HAUTE, 1, 10));
            Tache complexTask = new ComplexTache(description, priorite, subTasks);
        }
        List<Tache> subTasks = new ArrayList<>();
        subTasks.add(new SimpleTache("Servir le dîner", LocalDate.now().plusDays(3), Priorite.HAUTE, 1, 10));

        Tache complexTask = new ComplexTache(description, priorite, subTasks);
        todoList.addTask(complexTask);
        updateTableModel();
    }

    private void importXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importer une ToDoList depuis un fichier XML");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers XML", "xml"));

        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XMLParser.parseXml(todoList, file.getAbsolutePath());
			updateTableModel();
        }
    }

    private void saveToDoList() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer la ToDoList dans un fichier XML");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers XML", "xml"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".xml")) {
                file = new File(file.getAbsolutePath() + ".xml");
            }
            try {
                EnregistrerVisitor visitor = new EnregistrerVisitor();
                todoList.acceptVistor(visitor, file.getAbsolutePath());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du fichier XML: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void updateTableModel() {
        model.setRowCount(0);
        int index = 1;
        for (Tache task : todoList.getAllTasks()) {
            model.addRow(new Object[]{index, task.getDescription(), task.getClass().getSimpleName(),
                    task.getDeadline(), task.getPriorite(), task.getProgress()});
            index++;
        }
    }

    public static void main(String[] args) {
        new LauncherApp();
    }
}