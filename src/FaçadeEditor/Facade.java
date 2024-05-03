package FaçadeEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import ToDoListOperation.BoolTache;
import ToDoListOperation.ComplexTache;
import ToDoListOperation.Priorite;
import ToDoListOperation.SimpleTache;
import ToDoListOperation.Tache;
import ToDoListOperation.TacheBuilder;
import ToDoListOperation.TacheFactory;
import ToDoListOperation.TodoListImpl;
import ToDoListOperation.concreateTacheFactoryBuilder;
import XmlExport.EnregistrerVisitor;
import XmlExport.SaveToDoList;
import XmlParser.XMLParser;

public class Facade extends JFrame implements IFacade {
    public JTable subtasksTable;
    private DefaultTableModel model, subtasksModel;
    private TodoListImpl todoList;
    private TacheFactory factory = new concreateTacheFactoryBuilder();
    
    public Facade(TodoListImpl todoList, DefaultTableModel model) {
        super("Gestionnaire de ToDoList");
      
        this.todoList=todoList;
       this.model=model;
       
    }
    
    public void createComplexTask(List<Tache> subTasks) {
        try {
        	TacheBuilder builder;
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
                Priorite subTaskPriority = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité de la sous-tâche:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.BASSE);
               ;
                int subTaskDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours) de la sous-tâche:"));
                int subTaskProgress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression de la sous-tâche:"));
                Tache newSubtask = null;
                switch (choice) {
                    case "Simple":
                        builder=factory.createSimpleTache();
                        newSubtask = builder
                        	    .setDescription(subTaskDesc)
                                .setDateEcheance(subTaskDeadline)
                                .setPriorite(subTaskPriority)
                                .setEstimatedDuration(subTaskDuration)
                                .setProgress(subTaskProgress)
                                .build();
                        break;
                    case "Booléenne":
                        boolean isCompleted = JOptionPane.showConfirmDialog(null, "La sous-tâche est-elle terminée ?", "Sous-tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        builder=factory.createTacheBoolean();
                        newSubtask =((BoolTache) builder
                        	    .setDescription(subTaskDesc)
                                .setDateEcheance(subTaskDeadline)
                                .setPriorite(subTaskPriority)
                                .setEstimatedDuration(subTaskDuration))
                                .setCompleted(isCompleted)
                                .build();
                        break;
                    case "Complexe":
                        // Créez une liste pour stocker les sous-tâches de la sous-tâche complexe
                        List<Tache> subSubTasks = new ArrayList<>();
                        // Appelez la méthode createComplexTask récursivement pour créer les sous-tâches complexes
                        createComplexTask(subSubTasks);
                        createComplexTask(subSubTasks);
                        builder= factory.createTacheComplexe();
                        newSubtask=((ComplexTache) builder   
                        		.setDescription(subTaskDesc)
                        		.setDateEcheance(subTaskDeadline)
                                .setPriorite(subTaskPriority)
                                .setEstimatedDuration(subTaskDuration)
                                .setProgress(subTaskProgress))
                                .setSubTaches(subSubTasks)
                                .build();
                    
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
    @Override 
    public void modifyTask(Tache task) {
        TacheBuilder builder;
        
        JTextField deadlineField = new JTextField(task.getDeadline().toString());
        JFrame modifyFrame = new JFrame("Modifier la tâche");
        JButton saveButton = new JButton("Enregistrer");
       
        
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
        JTextField progressField = new JTextField(Integer.toString(task.getProgress()));
       
        if (task instanceof SimpleTache) {
        	   builder = factory.createSimpleTache();
        	   modifyPanel.add(new JLabel("Échéance :"));
               modifyPanel.add(deadlineField);

              
               modifyPanel.add(new JLabel("Progression (%) :"));
               modifyPanel.add(progressField);
               
               saveButton.addActionListener(e -> {
                   // Utiliser le constructeur spécifique pour modifier les attributs de la tâche
               	 builder.setDescription(descriptionField.getText())
                    .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                    .setDateEcheance(LocalDate.parse(deadlineField.getText()))
                    .setProgress(Integer.parseInt(progressField.getText()));
                    
                   Tache modifiedTask = builder.build(); // Construire la nouvelle tâche avec les modifications
                   todoList.replaceTask(task, modifiedTask); // Remplacer l'ancienne tâche par la nouvelle dans la liste
                   // Rafraîchir l'affichage de la table principale
                   updateTableModel();
                   modifyFrame.dispose(); // Fermer la fenêtre de modification
               });

           
        } else if (task instanceof BoolTache) {
            builder = factory.createTacheBoolean();
            JCheckBox  completedCheckBox = new JCheckBox("Terminer", ((BoolTache) task).isCompleted());
            modifyPanel.add(completedCheckBox);
            modifyPanel.add(new JLabel()); // Placeholder for alignment
            saveButton.addActionListener(e -> {
                      	
   
            	  ((BoolTache) builder.setDescription(descriptionField.getText())
                       .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                       .setDateEcheance(LocalDate.parse(deadlineField.getText())))
                       .setCompleted(completedCheckBox.isSelected()); 
              
              
                Tache modifiedTask = builder.build(); // Construire la nouvelle tâche avec les modifications
                todoList.replaceTask(task, modifiedTask); // Remplacer l'ancienne tâche par la nouvelle dans la liste
                // Rafraîchir l'affichage de la table principale
                updateTableModel();
                modifyFrame.dispose(); // Fermer la fenêtre de modification
            });
            
        } else if (task instanceof ComplexTache) {
            builder = factory.createTacheComplexe(); 
                    
            saveButton.addActionListener(e -> {
                // Utiliser le constructeur spécifique pour modifier les attributs de la tâche
            	                   
             	
         	   ((ComplexTache) builder.setDescription(descriptionField.getText())
                    .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                    .setDateEcheance(((ComplexTache)task).calculateDeadline())
                    .setProgress(task.getProgress()))
                    .setSubTaches(((ComplexTache) task).getSubTaches());
         	    

             Tache modifiedTask = builder.build(); // Construire la nouvelle tâche avec les modifications
             todoList.replaceTask(task, modifiedTask); // Remplacer l'ancienne tâche par la nouvelle dans la liste
             // Rafraîchir l'affichage de la table principale
             updateTableModel();
             modifyFrame.dispose(); // Fermer la fenêtre de modification
         });
            
            
            
            
            
        } else {
            // Gérer d'autres types de tâches si  nécessaire
            return;
        }

    
        modifyPanel.add(saveButton);

        modifyFrame.add(modifyPanel);
        modifyFrame.setVisible(true);
    }

    public void modifySubtask(ComplexTache complexTask, int selectedRow) {
    	
        if (selectedRow >= 0 && selectedRow < complexTask.getSubTaches().size()) {
            Tache subtask = complexTask.getSubTaches().get(selectedRow);
            TacheBuilder builder;
                     
           
            JFrame modifyFrame = new JFrame("Modifier la sous-tâche");
            modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            modifyFrame.setSize(400, 300);
            modifyFrame.setLocationRelativeTo(this);
            JButton saveButton = new JButton("Enregistrer");
            JPanel modifyPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            modifyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JTextField deadlineField = new JTextField(subtask.getDeadline().toString());
            JTextField descriptionField = new JTextField(subtask.getDescription());
            modifyPanel.add(new JLabel("Description :"));
            modifyPanel.add(descriptionField);

            JComboBox<Priorite> prioriteComboBox = new JComboBox<>(Priorite.values());
            prioriteComboBox.setSelectedItem(subtask.getPriorite());
            modifyPanel.add(new JLabel("Priorité :"));
            modifyPanel.add(prioriteComboBox);
            JTextField progressField = new JTextField(Integer.toString(subtask.getProgress()));
           
            if (subtask instanceof SimpleTache) {
            	   builder = factory.createSimpleTache();
            	   modifyPanel.add(new JLabel("Échéance :"));
                   modifyPanel.add(deadlineField);                 
                   modifyPanel.add(new JLabel("Progression (%) :"));
                   modifyPanel.add(progressField);
                   
                   saveButton.addActionListener(e -> {
                       // Utiliser le constructeur spécifique pour modifier les attributs de la tâche
                   	 builder.setDescription(descriptionField.getText())
                        .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                        .setDateEcheance(LocalDate.parse(deadlineField.getText()))
                        .setProgress(Integer.parseInt(progressField.getText()));
                        
                     Tache modifiedSubtask = builder.build(); // Construire la nouvelle sous-tâche avec les modifications
                     complexTask.replaceSubtask(selectedRow, modifiedSubtask); // Remplacer l'ancienne sous-tâche par la nouvelle
                     // Rafraîchir l'affichage de la liste des sous-tâches
                     updateSubtasksTableModel(complexTask);
                     modifyFrame.dispose(); // Fermer la fenêtre de modification
                   });

               
            } else if (subtask instanceof BoolTache) {
                builder = factory.createTacheBoolean();
                JCheckBox  completedCheckBox = new JCheckBox("Terminer", ((BoolTache) subtask).isCompleted());
                modifyPanel.add(completedCheckBox);
                modifyPanel.add(new JLabel()); // Placeholder for alignment
                saveButton.addActionListener(e -> {
                          	
       
                	  ((BoolTache) builder.setDescription(descriptionField.getText())
                           .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                           .setDateEcheance(LocalDate.parse(deadlineField.getText())))
                           .setCompleted(completedCheckBox.isSelected()); 
                  
                  
                      Tache modifiedSubtask = builder.build(); // Construire la nouvelle sous-tâche avec les modifications
                      complexTask.replaceSubtask(selectedRow, modifiedSubtask); // Remplacer l'ancienne sous-tâche par la nouvelle
                      // Rafraîchir l'affichage de la liste des sous-tâches
                      updateSubtasksTableModel(complexTask);
                      modifyFrame.dispose(); // Fermer la fenêtre de modification
                });
                
            } else if (subtask instanceof ComplexTache) {
                builder = factory.createTacheComplexe(); 
                        
                saveButton.addActionListener(e -> {
                    // Utiliser le constructeur spécifique pour modifier les attributs de la tâche
                	                   
                 	
             	   ((ComplexTache) builder.setDescription(descriptionField.getText())
                        .setPriorite((Priorite) prioriteComboBox.getSelectedItem())
                        .setDateEcheance(((ComplexTache)subtask).calculateDeadline())
                        .setProgress(subtask.getProgress()))
                        .setSubTaches(((ComplexTache) subtask).getSubTaches());
             	    
                          
         
                   Tache modifiedSubtask = builder.build(); // Construire la nouvelle sous-tâche avec les modifications
                   complexTask.replaceSubtask(selectedRow, modifiedSubtask); // Remplacer l'ancienne sous-tâche par la nouvelle
                   // Rafraîchir l'affichage de la liste des sous-tâches
                   updateSubtasksTableModel(complexTask);
                   modifyFrame.dispose(); // Fermer la fenêtre de modification
             });
                
  
            } else {
                // Gérer d'autres types de tâches si  nécessaire
                return;
            }

        
            modifyPanel.add(saveButton);

            modifyFrame.add(modifyPanel);
            modifyFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Aucune sous-tâche sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @Override 
    public void createComplexTask() {
        try {
        	 TacheBuilder builder;
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
                    case "Simple"://(subTaskDesc, subTaskDeadline, subTaskPriority, subTaskDuration, subTaskProgress);
                        builder=factory.createSimpleTache();
                        newSubtask = builder
                        	    .setDescription(subTaskDesc)
                                .setDateEcheance(subTaskDeadline)
                                .setPriorite(subTaskPriority)
                                .setEstimatedDuration(subTaskDuration)
                                .setProgress(subTaskProgress)
                                .build();
                        break;
                    case "Booléenne":
                        boolean isCompleted = JOptionPane.showConfirmDialog(null, "La sous-tâche est-elle terminée ?", "Sous-tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        
                        builder=factory.createTacheBoolean();
                        newSubtask =((BoolTache) builder
                        	    .setDescription(subTaskDesc)
                                .setDateEcheance(subTaskDeadline)
                                .setPriorite(subTaskPriority)
                                .setEstimatedDuration(subTaskDuration))
                                .setCompleted(isCompleted)
                                .build();
                     
                        break;
                    case "Complexe":
                        // Créez une liste pour stocker les sous-tâches de la sous-tâche complexe
                        List<Tache> subSubTasks = new ArrayList<>();
                        // Appelez la méthode createComplexTask récursivement pour créer les sous-tâches complexes
                        createComplexTask(subSubTasks);
                        builder= factory.createTacheComplexe();
                        newSubtask=((ComplexTache) builder   
                        		.setDescription(subTaskDesc)
                        		.setDateEcheance(subTaskDeadline)
                                .setPriorite(subTaskPriority)
                                .setEstimatedDuration(subTaskDuration)
                                .setProgress(subTaskProgress))
                                .setSubTaches(subSubTasks)
                                .build();
                    
                        
                  break;
                }
                if (newSubtask != null) {
                    subTasks.add(newSubtask);
                }
            }
            // Créez la tâche complexe avec la liste de sous-tâches
            builder=factory.createTacheComplexe();
            ComplexTache complexTask =(ComplexTache)((ComplexTache) builder
            	       .setDescription(description)
                       .setPriorite(priorite))
                       .setSubTaches(subTasks)
                       .build();
            		
            
            todoList.addTask(complexTask);
            updateTableModel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Faites attention au remplissage.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour créer les sous-tâches complexes de manière récursive
    
    
    
 

    public void showTaskDetails(Tache task) {
    	
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

  
    @Override
    public void deleteTask(Tache task) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette tâche ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            todoList.removeTask(task);
            updateTableModel();
        }
    }
    
    
    

    public void showSubtasks(ComplexTache complexTask) {
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
    	   TacheBuilder builder;
    	String[] options = {"Simple", "Booléenne", "Complexe"};
        String choice = (String) JOptionPane.showInputDialog(null, "Choisissez le type de sous-tâche à ajouter:", "Type de sous-tâche", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        String description = JOptionPane.showInputDialog("Entrez la description de la sous-tâche:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(null, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
      
        Tache newSubtask = null;
       
		switch (choice) {
            case "Simple":
            	  int progress = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
                builder=factory.createSimpleTache();
                newSubtask = builder
                	    .setDescription(description)
                        .setDateEcheance(deadline)
                        .setPriorite(priorite)
                        .setEstimatedDuration(estimatedDuration)
                        .setProgress(progress)
                        .build();
            
                break;
            case "Booléenne":
                boolean isCompleted = JOptionPane.showConfirmDialog(null, "La tâche est-elle terminée ?", "Tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                builder=factory.createTacheBoolean();
                newSubtask =((BoolTache) builder
                	    .setDescription(description)
                        .setDateEcheance(deadline)
                        .setPriorite(priorite)
                        .setEstimatedDuration(estimatedDuration))
                        .setCompleted(isCompleted)
                        .build();
    
                break;
            case "Complexe":
            	  List<Tache> subSubTasks = new ArrayList<>();
                  // Appelez la méthode createComplexTask récursivement pour créer les sous-tâches complexes
                  createComplexTask(subSubTasks);
                  int progress1 = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
                  
                  builder= factory.createTacheComplexe();
                  newSubtask=((ComplexTache) builder   
                  		.setDescription(description)
                  		.setDateEcheance(deadline)
                          .setPriorite(priorite)
                          .setEstimatedDuration(estimatedDuration)
                          .setProgress(progress1))
                          .setSubTaches(subSubTasks)
                          .build();
              

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
    @Override
	public void createSimpleTask() {
    	try {TacheBuilder builder;
    	       String description = JOptionPane.showInputDialog("Entrez la description de la tâche simple:");
    	        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
    	        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
    	        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
    	        int progress2 = Integer.parseInt(JOptionPane.showInputDialog("Entrez le pourcentage de progression:"));
    	       
                builder = factory.createSimpleTache();
                Tache task = builder
                	    .setDescription(description)
                        .setDateEcheance(deadline)
                        .setPriorite(priorite)
                        .setEstimatedDuration(estimatedDuration)
                        .setProgress(progress2)
                        .build();

    	        todoList.addTask(task);
    	        updateTableModel();
		} catch (Exception e) {
			 JOptionPane.showMessageDialog(this, "fait attention au remplissage des donnees .", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
 
    }
    @Override
	public void createBooleanTask() {
    	try {
    	String description = JOptionPane.showInputDialog("Entrez la description de la tâche booléenne:");
        LocalDate deadline = LocalDate.parse(JOptionPane.showInputDialog("Entrez la date d'échéance (AAAA-MM-JJ):"));
        Priorite priorite = (Priorite) JOptionPane.showInputDialog(this, "Sélectionnez la priorité:", "Priorité", JOptionPane.QUESTION_MESSAGE, null, Priorite.values(), Priorite.MOYENNE);
        int estimatedDuration = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée estimée (en jours):"));
        boolean isCompleted = JOptionPane.showConfirmDialog(this, "La tâche est-elle terminée ?", "Tâche terminée", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
       
        TacheBuilder builder = factory.createTacheBoolean();
        BoolTache task =(BoolTache) ((BoolTache) builder
        	    .setDescription(description)
                .setDateEcheance(deadline)
                .setPriorite(priorite)
                .setEstimatedDuration(estimatedDuration))
                .setCompleted(isCompleted)
                .build();

        todoList.addTask(task);
        updateTableModel();
	} catch (Exception e) {
		 JOptionPane.showMessageDialog(this, "fait attention au remplissage des donnees .", "Erreur", JOptionPane.ERROR_MESSAGE);
	}

    }
    @Override
	public void importXML() {
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
@Override
    public void saveToDoList() {
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
            	SaveToDoList doList = new SaveToDoList();
            	doList.saveListTache(todoList, file.getAbsolutePath());
            
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

 

}



