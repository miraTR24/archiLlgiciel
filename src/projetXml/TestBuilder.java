package projetXml;

import java.time.LocalDate;

public class TestBuilder {
    public static void main(String[] args) {
        TacheFactory factory = new concreateTacheFactoryBuilder(); // Instance de l'usine
        BooleanTacheBuilder builder = new BooleanTacheBuilder(factory); // Builder pour créer la tâche
        
        Tache tache = builder
            .setDescription("Test Task")
            .setDateEcheance(LocalDate.now().plusDays(5)) // Deadline dans 5 jours
            .setPriorite(Priorite.HAUTE)
            .build(); // Construire la tâche

        System.out.println("Description: " + tache.getDescription());
        System.out.println("Deadline: " + tache.getDeadline());
        System.out.println("Priorite: " + tache.getPriorite());
        
    }
}