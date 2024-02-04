package duke.command;

import duke.exception.*;
import duke.task.*;
import duke.ui.*;
import java.util.List;

public class HelpCommand extends Command<List<Task>> {
    public HelpCommand() {
        super("help", List.of());
    }

    @Override
    public List<Task> execute(List<Task> tasks) {
        System.out.printf("\nHere's all the things I can do for you! ~(^o.o^)\n");
        System.out.println("1. todo <description> - Add a reminder to do something!");
        System.out.println("2. deadline <description> /by <deadline> - Add a reminder.. with a deadline!");
        System.out.println("3. event <description> /from <start> /to <end> - Add an event to your calendar");
        System.out.println("4. list - List all your tasks and events");
        System.out.println("5. mark <index> - Mark a task as done");
        System.out.println("6. unmark <index> - Mark a task as undone");
        System.out.println("7. delete <index> - Delete a task. Warning! I have bad memory. (I can't restore deleted tasks)");
        System.out.println("8. help - Show this list of commands");
        System.out.println("9. bye - Leave :(");
        return tasks;
    }
}
