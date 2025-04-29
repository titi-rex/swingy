/*
 * The MIT License
 *
 * Copyright 2025 Pril Wolf.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.ft.swingy.Controller;

import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.Console.ConsoleView;
import fr.ft.swingy.View.Console.ConsoleView.InputHandler;
import fr.ft.swingy.View.View;
import fr.ft.swingy.View.View.SceneName;
import java.awt.event.ActionListener;
import static fr.ft.swingy.View.Console.ConsoleView.NEWLINE;

/**
 * Controller implementation for {@link fr.ft.swingy.View.Console.ConsoleView}
 *
 * @author Pril Wolf
 */
public class ConsoleController extends AbstractController implements InputHandler {

    /**
     * Input value
     */
    public enum InputType {
        HELP,
        GUI,
        EXIT,
        CLASS,
        CREATE,
        DELETE,
        PLAY,
        NORTH, N,
        EAST, E,
        SOUTH, S,
        WEST, W,
        FIGHT, F,
        RUN, R,
        TAKE, YES,
        DISCARD, NO,
    }

    /**
     * Error message for unknown input
     */
    public static final String ERROR_INPUT_UNKNOWN = "Error: not a valid command";

    /**
     * Prefix error message for unknown input
     */
    public static final String ERROR_INPUT_UNKNOWN_PRE = "Error: not a valid command: ";

    /**
     * Error message for invalid number of parameter
     */
    public static final String ERROR_NUMBER_PARAMTER = "missing parameter";

    /**
     * Error message for contextually unauthorized command
     */
    public static final String ERROR_UNAUTHORIZED = "unauthorised in this context";

    /**
     * Error message for missing input
     */
    public static final String ERROR_INPUT_MISSING = "Error: missing an argument: ";
    /**
     * General Help message
     */
    public static final String HELP_HELP_MSG = "help: display help" + NEWLINE
            + "exit: save and exit the game" + NEWLINE
            + "gui: switch to gui";

    /**
     * Contextual help message for creator scene
     */
    public static final String CREATOR_HELP_MSG = "create NAME CLASS: create a new hero" + NEWLINE
            + "class: print list of class available" + NEWLINE
            + "delete NAME: delete a hero" + NEWLINE
            + "play NAME: start a game";
    /**
     * Contextual help message for play scene
     */
    public static final String PLAY_HELP_MSG = "north/west/south/east: move your hero" + NEWLINE
            + "fight/run: against a monster choose your fate" + NEWLINE
            + "take/discard: if dropped, take the artifact";

    private SceneName currentScene;
    private final ConsoleView cView;

    /**
     *
     * @param view
     * @param model
     */
    public ConsoleController(View view, Model model) {
        super(view, model);
        this.cView = (ConsoleView) view;
    }

    /**
     * Link the view component to controller actions and set the current scene
     */
    @Override
    public void init() {
        cView.addInputListener(this);
        cView.addContextMessage(HELP_HELP_MSG);
        if (model.isPlaying() || model.isEnd()) {
            currentScene = SceneName.PLAY;
            cView.addContextMessage(PLAY_HELP_MSG);
        } else {
            currentScene = SceneName.CREATOR;
            cView.addContextMessage(CREATOR_HELP_MSG);
        }
        cView.showView(currentScene);
    }

    /**
     * Parse input and perform action
     *
     * @param input raw user input from view
     */
    @Override
    public void consume(String input) {
        try {
            parse(normalize(input));
        } catch (InvalidCommandException e) {
            cView.addContextMessage(ERROR_INPUT_UNKNOWN_PRE + e.getMessage());
        } catch (IllegalArgumentException e) {
            cView.addContextMessage(ERROR_INPUT_UNKNOWN);
        } catch (IndexOutOfBoundsException e) {
            cView.addContextMessage(ERROR_INPUT_MISSING + ERROR_NUMBER_PARAMTER);
        }
    }

    private String[] normalize(String input) {
        String[] clean = input.trim().split(" ");
        for (int i = 0; i < clean.length; i++) {
            if (i != 1) {
                clean[i] = clean[i].toUpperCase();
            }
        }
        return clean;
    }

    private InputType shortToLong(InputType shortInput) {
        return switch (shortInput) {
            case InputType.N ->
                InputType.NORTH;
            case InputType.E ->
                InputType.EAST;
            case InputType.S ->
                InputType.SOUTH;
            case InputType.W ->
                InputType.WEST;
            case InputType.YES ->
                InputType.TAKE;
            case InputType.NO ->
                InputType.DISCARD;
            case InputType.F ->
                InputType.FIGHT;
            case InputType.R ->
                InputType.RUN;
            default ->
                shortInput;
        };
    }

    /**
     * Parse Meta command
     *
     * @param input
     */
    private void parse(String input[]) {
        InputType inputType = ConsoleController.InputType.valueOf(input[0]);
        inputType = shortToLong(inputType);
        ActionListener action;
        switch (inputType) {
            case InputType.EXIT:
                action = new MetaAction(MetaAction.Types.EXIT);
                action.actionPerformed(null);
                break;
            case InputType.GUI:
                action = new MetaAction(MetaAction.Types.SWITCH);
                action.actionPerformed(null);
                break;
            case InputType.HELP:
                cView.addContextMessage(HELP_HELP_MSG);
            default:

                if (currentScene == SceneName.CREATOR) {
                    parseCreator(inputType, input);
                } else {
                    parsePlay(inputType);
                }
        }
    }

    private void parseCreator(InputType inputType, String[] input) {
        switch (inputType) {
            case InputType.HELP -> {
                cView.addContextMessage(CREATOR_HELP_MSG);
            }
            case InputType.CLASS -> {
                cView.addContextMessage(cView.getRolesList());
            }
            case InputType.CREATE -> {
                cView.setNameSelected(input[1]);
                cView.setRoleSelected(Roles.valueOf(input[2]));
                CreatorAction action = new CreatorAction(CreatorAction.Types.CREATE);
                action.actionPerformed(null);
            }
            case InputType.DELETE -> {
                cView.setHeroSelected(input[1]);
                CreatorAction action = new CreatorAction(CreatorAction.Types.DELETE);
                action.actionPerformed(null);
            }
            case InputType.PLAY -> {
                cView.setHeroSelected(input[1]);
                CreatorAction action = new CreatorAction(CreatorAction.Types.PLAY);
                action.actionPerformed(null);
                currentScene = SceneName.PLAY;
                cView.showView(currentScene);
                cView.addContextMessage(PLAY_HELP_MSG);
            }
            default ->
                throw new InvalidCommandException(ERROR_UNAUTHORIZED);
        }
    }

    private void parsePlay(InputType inputType) {

        switch (inputType) {
            case InputType.HELP -> {
                cView.addContextMessage(PLAY_HELP_MSG);
            }
            case InputType.NORTH, InputType.EAST, InputType.SOUTH, InputType.WEST -> {
                if (model.isFighting() || model.hasDropped() || model.isEnd()) {
                    throw new InvalidCommandException(ERROR_UNAUTHORIZED);
                } else {
                    HeroAction action = new HeroAction(Model.Action.MOVE, Model.Direction.valueOf(inputType.name()));
                    action.actionPerformed(null);

                }
            }
            case InputType.FIGHT, InputType.RUN -> {
                if (model.isFighting()) {
                    HeroAction action = new HeroAction(Model.Action.valueOf(inputType.name()), null);
                    action.actionPerformed(null);
                } else {
                    throw new InvalidCommandException(ERROR_UNAUTHORIZED);
                }
            }
            case InputType.TAKE, InputType.DISCARD -> {
                if (model.hasDropped()) {
                    HeroAction action = new HeroAction(Model.Action.valueOf(inputType.name()), null);
                    action.actionPerformed(null);
                } else {
                    throw new InvalidCommandException(ERROR_UNAUTHORIZED);
                }
            }
            default ->
                throw new InvalidCommandException(ERROR_UNAUTHORIZED);
        }
    }

}
