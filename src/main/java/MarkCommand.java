import java.util.List;

class MarkCommand extends Command<List<Task>> {
    public MarkCommand(List<String> arguments) {
        super("mark", arguments);
    }

    @Override
    List<Task> execute(List<Task> tasks) {
        // Assuming arguments is a list of one string
        if (!arguments.isEmpty()) {
            try {
                int index = Integer.parseInt(arguments.get(0)) - 1;
                if (index >= 0 && index < tasks.size()) {
                    Task task = tasks.get(index);
                    if (!task.isDone()) {
                        task.markAsDone();
                        System.out.printf("\n(^-^)~~   Marked task %d as done: %s\n", index + 1, task.getDescription());
                    } else {
                        System.out.printf("\n(^-^)~~   Task %d is already done\n", index + 1);
                    }
                } else {
                    System.out.printf("\n(^-^)~~   Task index %s is out of range\n", arguments.get(0));
                }
            } catch (NumberFormatException e) {
                System.out.printf("\n(^-^)~~   Invalid task index: %s\n", arguments.get(0));
            }
        } else {
            System.out.println("\n(^-^)~~   No task index provided.");
        }

        return tasks;
    }
}