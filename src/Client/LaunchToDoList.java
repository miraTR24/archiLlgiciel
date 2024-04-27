package Client;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import FactoryMethodParser.XMLParser;
import projetXml.*;

public class LaunchToDoList extends JFrame {
    private JTable table, subtasksTable;
    private DefaultTableModel model, subtasksModel;
    private TodoListImpl todoList;
    private JButton btnCreateSimple, btnCreateBoolean, btnCreateComplex, btnImportXML, btnSave;

    public LaunchToDoList() {
        super("Gestionnaire de ToDoList");
        todoList = new TodoListImpl();
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());
        setupMainTable();
        setupButtonsPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);
    }

    private void setupMainTable() {
        model = new DefaultTableModel(new String[]{"ID", "Description", "Type", "Échéance", "Priorité", "Progression"}, 0);
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
    }

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

        subtasksModel = new DefaultTableModel(
                new Object[]{"ID", "Description", "Échéance", "Priorité", "Progression"}, 0
        );
        subtasksTable = new JTable(subtasksModel);
        subtasksTable.setFillsViewportHeight(true);
        subtasksTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = subtasksTable.getSelectedRow();
                if (selectedRow != -1) {
                    Tache selectedSubtask = complexTask.getSubTaches().get(selectedRow);
                    showTaskDetails(selectedSubtask);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addSubtaskButton = new JButton("Ajouter sous-tâche");
        JButton modifySubtaskButton = new JButton("Modifier sous-tâche");
        JButton deleteSubtaskButton = new JButton("Supprimer sous-tâche");
        buttonPanel.add(addSubtaskButton);
        buttonPanel.add(modifySubtaskButton);
        buttonPanel.add(deleteSubtaskButton);

        addSubtaskButton.addActionListener(e -> addSubtask(complexTask));
        modifySubtaskButton.addActionListener(e -> modifySubtask(complexTask, subtasksTable.getSelectedRow()));
        deleteSubtaskButton.addActionListener(e -> deleteSubtask(complexTask, subtasksTable.getSelectedRow()));

        updateSubtasksTableModel(complexTask);

        subtasksFrame.add(new JScrollPane(subtasksTable), BorderLayout.CENTER);
        subtasksFrame.add(buttonPanel, BorderLayout.SOUTH);
        subtasksFrame.setVisible(true);
    }

    private void addSubtask(ComplexTache complexTask) {
        String subTaskDesc = JOptionPane.showInputDialog("Entrez la description de la nouvelle sous-tâche:");
        LocalDate subTaskDeadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.HAUTE);
        int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
        Tache newSubtask = new SimpleTache(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress);
        complexTask.getSubTaches().add(newSubtask);
        updateSubtasksTableModel(complexTask);
    }

    private void modifySubtask(ComplexTache complexTask, int selectedRow) {
        if (selectedRow >= 0 && selectedRow < complexTask.getSubTaches().size()) {
            Tache selectedSubtask = complexTask.getSubTaches().get(selectedRow);
            String subTaskDesc = JOptionPane.showInputDialog("Entrez la nouvelle description de la sous-tâche:", selectedSubtask.getDescription());
            LocalDate subTaskDeadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la nouvelle date d'échéance (AAAA-MM-JJ):", selectedSubtask.getDeadline().toString()));
            Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la nouvelle priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), selectedSubtask.getPriorite());
            int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la nouvelle durée estimée (en jours):", String.valueOf(selectedSubtask.getEstimatedDuration())));
            int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau pourcentage de progression:", String.valueOf(selectedSubtask.getProgress())));
            selectedSubtask = new SimpleTache(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress);
            updateSubtasksTableModel(complexTask);
        } else {
            JOptionPane.showMessageDialog(this, "Aucune sous-tâche sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSubtask(ComplexTache complexTask, int selectedRow) {
        if (selectedRow >= 0 && selectedRow < complexTask.getSubTaches().size()) {
            complexTask.getSubTaches().remove(selectedRow);
            updateSubtasksTableModel(complexTask);
            updateTableModel() ;
        } else {
            JOptionPane.showMessageDialog(this, "Aucune sous-tâche sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSubtasksTableModel(ComplexTache complexTask) {
        subtasksModel.setRowCount(0);
        for (Tache subtask : complexTask.getSubTaches()) {
            subtasksModel.addRow(new Object[]{
                    subtask.getDescription(), subtask.getDeadline(), subtask.getPriorite(), subtask.getProgress()
            });
        }
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
            		LaunchToDoList gui = new LaunchToDoList();
            		gui.setVisible(true);
            		});
            		}            
            		}