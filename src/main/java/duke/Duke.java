package duke;

import java.io.FileNotFoundException;
import java.io.IOException;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Main;
import duke.ui.Ui;

/**
 * The main class representing the Duke chatbot application.
 *
 * <p>The {@code Duke} class serves as the entry point for the Duke chatbot application.
 * It handles user input, parsing, command execution, and storage operations.</p>
 */
public class Duke {
    private final Storage storage;
    private TaskList tasks;
    private Parser parser;
    private Ui ui;

    /**
     * Constructs a Duke object
     */
    public Duke() {
        this.tasks = new TaskList();
        this.storage = new Storage();
        this.ui = new Ui();
        this.parser = new Parser(ui);

        try {
            this.tasks = this.storage.load();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the response from Duke
     *
     * @param input User input
     * @return Response from Duke
     */
    public String getResponse(String input) {
        try {
            ui.clear();

            // Parse the input
            Command command = Parser.parseInput(input);

            // Execute the command
            command.execute(this.tasks, this.ui);
            storage.save(tasks);

            if (command instanceof ByeCommand) {
                Main.getStage().close();
            }

            // For debugging purposes.
            System.out.printf("\n------------\nInput: %s\nOutput: %s\n-----------", input, this.ui.getResponse());
            return this.ui.getResponse();

        } catch (IOException e) {
            this.ui.appendResponse(e.getMessage());
            return this.ui.getResponse();
        }
    }
}





