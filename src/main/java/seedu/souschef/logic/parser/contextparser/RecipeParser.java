package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.logic.commands.AddFavouriteCommand;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.commands.EditCommand;
import seedu.souschef.logic.commands.FindCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.ListCommand;
import seedu.souschef.logic.commands.PlanMealCommand;
import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.parser.commandparser.AddCommandParser;
import seedu.souschef.logic.parser.commandparser.DeleteCommandParser;
import seedu.souschef.logic.parser.commandparser.EditCommandParser;
import seedu.souschef.logic.parser.commandparser.FindCommandParser;
import seedu.souschef.logic.parser.commandparser.PlanMealCommandParser;
import seedu.souschef.logic.parser.commandparser.SelectCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses user input.
 */
public class RecipeParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param recipeModel
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command<Recipe> parseCommand(Model recipeModel,
        Model mealPlannerModel, String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        final String args = arguments.trim();
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parseRecipe(recipeModel, arguments);

        case "favourite":
            return new AddFavouriteCommand<Recipe>(recipeModel, arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parseRecipe(recipeModel, arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parseRecipe(recipeModel, arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parseRecipe(recipeModel, arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand<Recipe>(recipeModel);

        case SelectCommand.COMMAND_WORD:
             return new SelectCommandParser().parseRecipe(recipeModel, arguments);

        case PlanMealCommand.COMMAND_WORD:
            return new PlanMealCommandParser().parsePlan(mealPlannerModel, recipeModel, args);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
