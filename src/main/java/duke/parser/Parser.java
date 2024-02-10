package duke.parser;

import java.util.List;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.HelpCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.UnknownCommand;
import duke.command.UnmarkCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.ToDo;


public class Parser {
    public static Command parseInput(String userInput) throws DukeException {
        try {
            String[] parts = userInput.split(" ", 2);
            String command = parts[0].toLowerCase();
            List<String> arguments = parts.length > 1 ? List.of(parts[1].split("\\s+")) : List.of();

            String taskDescription = extractTaskDescription(arguments);
            String deadline = "Error: No deadline set.";
            String start = "Error: No start time set.";
            String end = "Error: No end time set.";

            // Get Deadline
            if (arguments.contains("/by")) {
                int index = arguments.indexOf("/by");
                if (index < arguments.size() - 1) {
                    deadline = String.join(" ", arguments.subList(index + 1, arguments.size()));
                } else {
                    throw new DukeException("Error: Missing deadline after /by. Correct format: /by <deadline>");
                }
            } else if (command.equals("deadline")) {
                throw new DukeException("Error: No description provided for the deadline command.\nCorrect format: deadline <description> /by <deadline>\n");
            }

            // Get From / To values
            if (arguments.contains("/from") && arguments.contains("/to")) {
                int fromIndex = arguments.indexOf("/from");
                int toIndex = arguments.indexOf("/to");
                if (fromIndex < toIndex && toIndex < arguments.size() - 1) {
                    start = String.join(" ", arguments.subList(fromIndex + 1, toIndex));
                    end = String.join(" ", arguments.subList(toIndex + 1, arguments.size()));
                } else {
                    throw new DukeException("Error: Missing /from or /to after event command. Correct format: event <description> /from <start> /to <end>");
                }
            } else if (arguments.contains("/from") || arguments.contains("/to")) {
                throw new DukeException("Error: Both /from and /to are required for the event command. Correct format: /from <start> /to <end>");
            }

            switch (command) {
                case "help":
                    return new HelpCommand();
                case "list":
                    return new ListCommand();
                case "mark":
                    return new MarkCommand(arguments);
                case "bye":
                    return new ByeCommand();
                case "exit":
                    return new ByeCommand();
                case "unmark":
                    return new UnmarkCommand(arguments);
                case "delete":
                    return new DeleteCommand(arguments);
                case "todo":
                    if (taskDescription.isEmpty()) {
                        throw new DukeException("Error: Todo description cannot be empty.");
                    }
                    return new AddCommand(new ToDo(taskDescription));
                case "deadline":
                    if (taskDescription.isEmpty()) {
                        throw new DukeException("Error: Deadline description cannot be empty.");
                    }
                    return new AddCommand(new Deadline(taskDescription, deadline));
                case "event":
                    if (taskDescription.isEmpty()) {
                        throw new DukeException("Error: Event description cannot be empty.");
                    } else if (start.equals("Error: No start time set.") || end.equals("Error: No end time set.")) {
                        throw new DukeException("Error: Both start and end times are required for the event command. Correct format: /from <start> /to <end>");
                    }
                    return new AddCommand(new Event(taskDescription, start, end));

                default:
                    throw new DukeException("Sorry.. I don't know what that means! If you want to leave, just say 'bye'! :(");
            }
        } catch (DukeException e) {
            System.out.printf("\n%s", e.getMessage());
            return new UnknownCommand();
        }
    }

    private static String extractTaskDescription(List<String> arguments) {
        int keywordIndex = findKeywordIndex(arguments, "/by", "/from", "/to");
        if (keywordIndex != -1) {
            return String.join(" ", arguments.subList(0, keywordIndex));
        }
        return String.join(" ", arguments);
    }

    private static int findKeywordIndex(List<String> arguments, String... keywords) {
        for (String keyword : keywords) {
            if (arguments.contains(keyword)) {
                return arguments.indexOf(keyword);
            }
        }
        return -1;
    }
}
