package projetXml;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoListFrameTest {
    public static void main(String[] args) {
    	TestBuilder frame = new TestBuilder();

        // Add test tasks
        List<Tache> tasks = new ArrayList<>();
        tasks.add(new SimpleTache("Task 1", LocalDate.now(), Priorite.MOYENNE, 1, 50));
        tasks.add(new SimpleTache("Task 2", LocalDate.now().plusDays(1), Priorite.BASSE, 2, 20));
        tasks.add(new BoolTache("Task 3", LocalDate.now().plusDays(2), Priorite.MOYENNE, 2, 10, false));
        tasks.add(new ComplexTache("Task 4", Priorite.HAUTE, tasks));

        frame.updateTaskList(tasks);
    }
}