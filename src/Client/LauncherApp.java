package Client;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import FactoryMethodParser.XMLParser;
import projetXml.BoolTache;
import projetXml.BooleanTacheBuilder;
import projetXml.ComplexTache;
import projetXml.ComplexTacheBuilder;
import projetXml.EnregistrerVisitor;
import projetXml.Priorite;
import projetXml.SimpleTache;
import projetXml.SimpleTacheBuilder;
import projetXml.Tache;
import projetXml.TacheBuilder;
import projetXml.TacheFactory;
import projetXml.TodoListImpl;
import projetXml.concreateTacheFactoryBuilder;

public class LauncherApp extends JFrame {
    private JTable table, subtasksTable;
    private DefaultTableModel model, subtasksModel;
    private TodoListImpl todoList;
    private JButton btnCreateSimple, btnCreateBoolean, btnCreateComplex, btnImportXML, btnSave;
    private TacheFactory tacheFactory = new concreateTacheFactoryBuilder();

    public LauncherApp() {
        super("Gestionnaire de ToDoList");
        todoList = new TodoListImpl();
        createUI();
    }

    private void createUI() {
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
    
    private void modifyTask(Tache task) {
        TacheBuilder builder;
        if (task instanceof SimpleTache) {
            builder = new SimpleTacheBuilder(tacheFactory);
        } else if (task instanceof BoolTache) {
            builder = new BooleanTacheBuilder(tacheFactory);
        } else if (task instanceof ComplexTache) {
            builder = new ComplexTacheBuilder(tacheFactory);
        } else {
            // Gérer d'autres types de tâches si  nécessaire
            return;
        }

        JFrame modifyFrame = new JFrame("Modifier la tâche");
        modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modifyFrame.setSize(400, 300);
        modifyFrame.setLocationRelativeTo(this);

        JPanel modifyPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        modifyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField descriptionField = new JTextField(task.getDescription());
        modifyPanel.add(new JLabel("Description :"));
        modifyPanel.add(descriptionField);

        JComboBox<Priorite> prioriteComboBox = new JComboBox<>(Priorite.values());
        prioriteComboBox.setSelectedItem(task.getPriorite());
        modifyPanel.add(new JLabel("Priorité :"));
        modifyPanel.add(prioriteComboBox);

        JTextField deadlineField = new JTextField(task.getDeadline().toString());
        modifyPanel.add(new JLabel("Échéance :"));
        modifyPanel.add(deadlineField);

        JTextField progressField = new JTextField(Integer.toString(task.getProgress()));
        modifyPanel.add(new JLabel("Progression (%) :"));
        modifyPanel.add(progressField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            // Utiliser le constructeur spécifique pour modifier les attributs de la tâche
            builder.setDescription(descriptionField.getText())
                   .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                   .setDateEcheance(LocalDate.parse(deadlineField.getText()))
                   .setProgress(Integer.parseInt(progressField.getText()));
            if (task instanceof ComplexTache) {
                // Modifier les attributs spécifiques à ComplexTache si nécessaire
                // Exemple : builder.setEstimatedDuration(estimatedDuration);
            }
            Tache modifiedTask = builder.build(); // Construire la nouvelle tâche avec les modifications
            todoList.replaceTask(task, modifiedTask); // Remplacer l'ancienne tâche par la nouvelle dans la liste
            // Rafraîchir l'affichage de la table principale
            updateTableModel();
            modifyFrame.dispose(); // Fermer la fenêtre de modification
        });
        modifyPanel.add(saveButton);

        modifyFrame.add(modifyPanel);
        modifyFrame.setVisible(true);
    }

    private void modifySubtask(ComplexTache complexTask, int selectedRow) {
    	
        if (selectedRow >= 0 && selectedRow < complexTask.getSubTaches().size()) {
            Tache subtask = complexTask.getSubTaches().get(selectedRow);
            TacheBuilder builder;
            
            
            
            if (subtask instanceof SimpleTache) {
                builder = new SimpleTacheBuilder(tacheFactory);
            } else if (subtask instanceof BoolTache) {
                builder = new BooleanTacheBuilder(tacheFactory);
            } else if (subtask instanceof ComplexTache) {
                builder = new ComplexTacheBuilder(tacheFactory);
                
            } else {
                // Gérer d'autres types de sous-tâches si nécessaire
                return;
            }

            JFrame modifyFrame = new JFrame("Modifier la sous-tâche");
            modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            modifyFrame.setSize(400, 300);
            modifyFrame.setLocationRelativeTo(this);

            JPanel modifyPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            modifyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JTextField descriptionField = new JTextField(subtask.getDescription());
            modifyPanel.add(new JLabel("Description :"));
            modifyPanel.add(descriptionField);

            JComboBox<Priorite> prioriteComboBox = new JComboBox<>(Priorite.values());
            prioriteComboBox.setSelectedItem(subtask.getPriorite());
            modifyPanel.add(new JLabel("Priorité :"));
            modifyPanel.add(prioriteComboBox);

            JTextField deadlineField = new JTextField(subtask.getDeadline().toString());
            modifyPanel.add(new JLabel("Échéance :"));
            modifyPanel.add(deadlineField);

            JTextField progressField = new JTextField(Integer.toString(subtask.getProgress()));
            modifyPanel.add(new JLabel("Progression (%) :"));
            modifyPanel.add(progressField);
                    

            JButton saveButton = new JButton("Enregistrer");
            saveButton.addActionListener(e -> {
                // Utiliser le constructeur spécifique pour modifier les attributs de la sous-tâche
                builder.setDescription(descriptionField.getText())
                       .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                       .setDateEcheance(LocalDate.parse(deadlineField.getText()))
                       .setProgress(Integer.parseInt(progressField.getText()));
                if (subtask instanceof ComplexTache) {
                    builder.setEstimatedDuration(((ComplexTache) subtask).getEstimatedDuration());
                  
                }     
                Tache modifiedSubtask = builder.build(); // Construire la nouvelle sous-tâche avec les modifications
                complexTask.replaceSubtask(selectedRow, modifiedSubtask); // Remplacer l'ancienne sous-tâche par la nouvelle
                // Rafraîchir l'affichage de la liste des sous-tâches
                updateSubtasksTableModel(complexTask);
                modifyFrame.dispose(); // Fermer la fenêtre de modification
            });
            
            modifyPanel.add(saveButton);

            modifyFrame.add(modifyPanel);
            modifyFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Aucune sous-tâche sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
    private void createComplexTask() {
        try {
            String description = JOptionPane.showInputDialog("Entrez la description de la tâche complexe (max 20 caractères):");
            if (description == null || description.length() > 20) {
                JOptionPane.showMessageDialog(this, "La description de la tâche complexe doit faire 20 caractères maximum.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.BASSE);
            int numSubtasks = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nombre de sous-tâches:"));
            List<Tache> subTasks = new ArrayList<>();
            for (int i = 0; i < numSubtasks; i++) {
                String[] options = {"Simple", "Booléenne", "Complexe"};
                String choice = (String) JOptionPane.showInputDialog(null, "Choisissez le type de sous-tâche " + (i + 1) + ":", "Type de sous-tâche", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                String subTaskDesc = JOptionPane.showInputDialog("Entrez la description de la sous-tâche " + (i + 1) + " (max 20 caractères):");
                if (subTaskDesc == null || subTaskDesc.length() > 20) {
                    JOptionPane.showMessageDialog(this, "La description de la sous-tâche doit faire 20 caractères maximum.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate subTaskDeadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
                Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité de la sous-tâche:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.HAUTE);
                int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours) de la sous-tâche:"));
                int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression de la sous-tâche:"));
                Tache newSubtask = null;
                switch (choice) {
                    case "Simple":
                        newSubtask = tacheFactory.createSimpleTache(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress);
                        break;
                    case "Booléenne":
                        boolean isCompleted = JOptionPane.showConfirmDialog(null, "La sous-tâche est-elle terminée ?", "Sous-tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        newSubtask = tacheFactory.createTacheBoolean(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress, isCompleted);
                        break;
                    case "Complexe":
                        // Créez une liste pour stocker les sous-tâches de la sous-tâche complexe
                        List<Tache> subSubTasks = new ArrayList<>();
                        // Appelez la méthode createComplexTask récursivement pour créer les sous-tâches complexes
                        createComplexTask(subSubTasks);
                        newSubtask = tacheFactory.createTacheComplexe(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress, subSubTasks);
                        break;
                }
                if (newSubtask != null) {
                    subTasks.add(newSubtask);
                }
            }
            // Créez la tâche complexe avec la liste de sous-tâches
            ComplexTache complexTask = new ComplexTache(description, priorite, subTasks);
            todoList.addTask(complexTask);
            updateTableModel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Faites attention au remplissage.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour créer les sous-tâches complexes de manière récursive
    
    
    
    private void createComplexTask(List<Tache> subTasks) {
        try {
            String description = JOptionPane.showInputDialog("Entrez la description de la tâche complexe (max 20 caractères):");
            if (description == null || description.length() > 20) {
                JOptionPane.showMessageDialog(this, "La description de la tâche complexe doit faire 20 caractères maximum.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.BASSE);
            int numSubtasks = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nombre de sous-tâches:"));
            for (int i = 0; i < numSubtasks; i++) {
                String[] options = {"Simple", "Booléenne", "Complexe"};
                String choice = (String) JOptionPane.showInputDialog(null, "Choisissez le type de sous-tâche " + (i + 1) + ":", "Type de sous-tâche", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                String subTaskDesc = JOptionPane.showInputDialog("Entrez la description de la sous-tâche " + (i + 1) + " (max 20 caractères):");
                if (subTaskDesc == null || subTaskDesc.length() > 20) {
                    JOptionPane.showMessageDialog(this, "La description de la sous-tâche doit faire 20 caractères maximum.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate subTaskDeadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
                Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité de la sous-tâche:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.HAUTE);
                int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours) de la sous-tâche:"));
                int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression de la sous-tâche:"));
                Tache newSubtask = null;
                switch (choice) {
                    case "Simple":
                        newSubtask = tacheFactory.createSimpleTache(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress);
                        break;
                    case "Booléenne":
                        boolean isCompleted = JOptionPane.showConfirmDialog(null, "La sous-tâche est-elle terminée ?", "Sous-tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        newSubtask = tacheFactory.createTacheBoolean(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress, isCompleted);
                        break;
                    case "Complexe":
                        // Créez une liste pour stocker les sous-tâches de la sous-tâche complexe
                        List<Tache> subSubTasks = new ArrayList<>();
                        // Appelez la méthode createComplexTask récursivement pour créer les sous-tâches complexes
                        createComplexTask(subSubTasks);
                        newSubtask = tacheFactory.createTacheComplexe(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress, subSubTasks);
                        break;
                }
                if (newSubtask != null) {
                    subTasks.add(newSubtask);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Faites attention au remplissage.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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
            new Object[]{"ID", "Description", "Type", "Échéance", "Priorité", "Progression"}, 0
        );
        subtasksTable = new JTable(subtasksModel);
        subtasksTable.setFillsViewportHeight(true);
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

        // Mise à jour du modèle de la table des sous-tâches
        updateSubtasksTableModel(complexTask);

        subtasksFrame.add(new JScrollPane(subtasksTable), BorderLayout.CENTER);
        subtasksFrame.add(buttonPanel, BorderLayout.SOUTH);
        subtasksFrame.setVisible(true);
    }

    private void addSubtask(ComplexTache complexTask) {
       try {
		

    	String[] options = {"Simple", "Booléenne", "Complexe"};
        String choice = (String) JOptionPane.showInputDialog(null, "Choisissez le type de sous-tâche à ajouter:", "Type de sous-tâche", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        String description = JOptionPane.showInputDialog("Entrez la description de la sous-tâche:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(null, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
        Tache newSubtask = null;
        switch (choice) {
            case "Simple":
                newSubtask = tacheFactory.createSimpleTache(description, deadline, priorite, estimatedDuration, progress);
                break;
            case "Booléenne":
                boolean isCompleted = JOptionPane.showConfirmDialog(null, "La tâche est-elle terminée ?", "Tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                newSubtask = tacheFactory.createTacheBoolean(description, deadline, priorite, estimatedDuration, progress, isCompleted);
                break;
            case "Complexe":
            	  List<Tache> subSubTasks = new ArrayList<>();
                  // Appelez la méthode createComplexTask récursivement pour créer les sous-tâches complexes
                  createComplexTask(subSubTasks);
                  newSubtask = tacheFactory.createTacheComplexe(description, deadline, priorite, estimatedDuration, progress, subSubTasks);

                break;
        }
        if (newSubtask != null || choice != null || description != null || deadline != null) {
            complexTask.getSubTaches().add(newSubtask);
            updateSubtasksTableModel(complexTask);
        }	} catch (Exception e) {
        	 JOptionPane.showMessageDialog(this, "Fait attention au remplissage", "Erreur", JOptionPane.ERROR_MESSAGE);
        	 }
    			}


    private void deleteSubtask(ComplexTache complexTask, int selectedRow) {
        if (selectedRow >= 0 && selectedRow < complexTask.getSubTaches().size()) {
            complexTask.getSubTaches().remove(selectedRow);
            updateSubtasksTableModel(complexTask);
        } else {
            JOptionPane.showMessageDialog(this, "Aucune sous-tâche sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSubtasksTableModel(ComplexTache complexTask) {
    	  model.setRowCount(0);
          int index = 1;
        subtasksModel.setRowCount(0);
        for (Tache subtask : complexTask.getSubTaches()) {
            subtasksModel.addRow(new Object[]{index, subtask.getDescription(), subtask.getClass().getSimpleName(), subtask.getDeadline(), subtask.getPriorite(), subtask.getProgress()});
            index++;
        }
        updateTableModel();
    }

    private void createSimpleTask() {
    	
    	try {
    	       String description = JOptionPane.showInputDialog("Entrez la description de la tâche simple:");
    	        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
    	        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
    	        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
    	        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
    	        SimpleTache task = new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
    	        todoList.addTask(task);
    	        updateTableModel();
		} catch (Exception e) {
			 JOptionPane.showMessageDialog(this, "fait attention au remplissage des donnees .", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
 
    }

    private void createBooleanTask() {
    	try {
    	String description = JOptionPane.showInputDialog("Entrez la description de la tâche booléenne:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
        boolean isCompleted = JOptionPane.showConfirmDialog(this, "La tâche est-elle terminée ?", "Tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        BoolTache task = new BoolTache(description, deadline, priorite, estimatedDuration, progress, isCompleted);
        todoList.addTask(task);
        updateTableModel();
	} catch (Exception e) {
		 JOptionPane.showMessageDialog(this, "fait attention au remplissage des donnees .", "Erreur", JOptionPane.ERROR_MESSAGE);
	}

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
                LauncherApp app = new LauncherApp();
            });
		

    }
    }