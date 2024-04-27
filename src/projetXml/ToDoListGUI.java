package projetXml;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import FactoryMethodParser.XMLParser;
import projetXml.*;

public class ToDoListGUI extends JFrame {
    private TodoListImpl todoList;
    private DefaultTableModel model;
    private JTable tasksTable;

    public ToDoListGUI() {
        setTitle("Gestionnaire de ToDoList");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        todoList = new TodoListImpl();

        // Création du modèle de table
        model = new DefaultTableModel(new Object[]{"#", "Description", "Type", "Échéance", "Priorité", "Progression"}, 0);
        tasksTable = new JTable(model);
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Création des boutons
        JButton createSimpleTaskButton = new JButton("Créer une tâche simple");
        JButton createBooleanTaskButton = new JButton("Créer une tâche booléenne");
        JButton createComplexTaskButton = new JButton("Créer une tâche complexe");
        JButton importXMLButton = new JButton("Importer depuis XML");
        JButton saveToXMLButton = new JButton("Enregistrer en XML");

        // Création du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(tasksTable), BorderLayout.CENTER);

        // Création du panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createSimpleTaskButton);
        buttonPanel.add(createBooleanTaskButton);
        buttonPanel.add(createComplexTaskButton);
        buttonPanel.add(importXMLButton);
        buttonPanel.add(saveToXMLButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Ajout des écouteurs d'événements
        createSimpleTaskButton.addActionListener(e -> createSimpleTask());
        createBooleanTaskButton.addActionListener(e -> createBooleanTask());
        createComplexTaskButton.addActionListener(e -> createComplexTask());
        importXMLButton.addActionListener(e -> importXML());
        saveToXMLButton.addActionListener(e -> saveToDoList());
    }

    private void createSimpleTask() {
        String description = JOptionPane.showInputDialog("Entrez la description de la tâche simple:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
        SimpleTache task = new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
        todoList.addTask(task);
        updateTableModel();
    }

    private void createBooleanTask() {
        String description = JOptionPane.showInputDialog("Entrez la description de la tâche booléenne:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
        boolean isCompleted = JOptionPane.showConfirmDialog(this, "La tâche est-elle terminée ?", "Tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        BoolTache task = new BoolTache(description, deadline, priorite, estimatedDuration, progress, isCompleted);
        todoList.addTask(task);
        updateTableModel();
    }

    private void createComplexTask() {
        String description = JOptionPane.showInputDialog("Entrez la description de la tâche complexe (max 20 caractères):");
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.BASSE);
        int numSubtasks = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nombre de sous-tâches:"));
        List<Tache> subTasks = new ArrayList<>();
        for (int i = 0; i < numSubtasks; i++) {
            String subTaskDesc = JOptionPane.showInputDialog("Entrez la description de la sous-tâche " + (i + 1) + ":");
            LocalDate subTaskDeadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
            Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.HAUTE);
            int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
            int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
            subTasks.add(new SimpleTache(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress));
        }
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
            try {
                XMLParser.parseXml(todoList, file.getAbsolutePath());
                updateTableModel();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'importation du fichier XML: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
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
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du fichier XML: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTableModel() {
        model.setRowCount(0);
        int index = 1;
        for (Tache task : todoList.getAllTasks()) {
            model.addRow(new Object[]{index, task.getDescription(), task.getClass().getSimpleName(), task.getDeadline(), task.getPriorite(), task.getProgress()});
            index++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoListGUI gui = new ToDoListGUI();
            gui.setVisible(true);
        });
    }
}