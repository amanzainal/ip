package duke.command;

import duke.exception.*;
import duke.task.*;
import duke.ui.*;
import java.util.List;

public class AddCommand extends Command<List<Task>> {
    private Task task;

    public AddCommand(Task task) {
        super("add", List.of());
        this.task = task;
    }

    public List<Task> execute(List<Task> tasks) {
        tasks.add(task);
        Integer count = tasks.size();
        System.out.printf("\n        ~~~ >^o_o^< ~~~\nGot it! I've added this task:\n - %s\n\nYou have %d task(s) in the list.\n", task, count);
        return tasks;
    }
}
