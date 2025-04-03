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

import fr.ft.swingy.Model.Entity.Cell;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.Console.ConsoleView;
import fr.ft.swingy.View.Console.ConsoleView.InputHandler;
import fr.ft.swingy.View.View;
import fr.ft.swingy.View.View.ViewName;
import java.awt.event.ActionListener;
import static fr.ft.swingy.View.Console.ConsoleView.NEWLINE;

/**
 *
 * @author Pril Wolf
 */
public class ConsoleController extends AbstractController implements InputHandler {

    public enum InputType {
        HELP,
        GUI,
        EXIT,
        CLASS,
        CREATE,
        DELETE,
        PLAY,
        NORTH,
        EAST,
        SOUTH,
        WEST,
        FIGHT,
        RUN,
        TAKE,
        DISCARD
    }

    public static final String ERROR_INPUT_UNKNOW = "Error: not a valid command: ";
    public static final String ERROR_INPUT_MISSING = "Error: missing an argument: ";

    public static final String HELP_HELP_MSG = "help: display help" + NEWLINE
            + "exit: save and exit the game" + NEWLINE
            + "gui: switch to gui";

    public static final String CREATOR_HELP_MSG = "create NAME CLASS: create a new hero" + NEWLINE
            + "class: print list of class available" + NEWLINE
            + "delete NAME: delete a hero" + NEWLINE
            + "play NAME: start a game";

    public static final String PLAY_HELP_MSG = "move your hero with: north/west/south/east" + NEWLINE
            + "choose your fate with: fight/run" + NEWLINE
            + "take the artifact: yes/no";

    public static final String MOVE_HELP_MSG = "move your hero with: north/west/south/east";
    public static final String FIGHT_HELP_MSG = "choose your fate with: fight/run";
    public static final String ARTIFACT_HELP_MSG = "take the artifact: yes/no";

    private ViewName currentScene;
    private final ConsoleView cView;

    public ConsoleController(View view, Model model) {
        super(view, model);
        this.cView = (ConsoleView) view;
    }

    @Override
    public void init() {
        cView.addInputListener(this);
        cView.addContextMessage(HELP_HELP_MSG);
        if (model.isPlaying()) {
            currentScene = ViewName.PLAY;
        } else {
            currentScene = ViewName.CREATOR;
        }
        cView.showView(currentScene);
    }

    @Override
    public void consume(String input) {
        try {
            parse(normalize(input));
        } catch (InvalidCommandException e) {
            cView.addContextMessage(ERROR_INPUT_UNKNOW + e.getMessage());
        } catch (IllegalArgumentException e) {
            cView.addContextMessage(ERROR_INPUT_UNKNOW);
        } catch (IndexOutOfBoundsException e) {
            cView.addContextMessage(ERROR_INPUT_MISSING + e.getMessage());
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

    private void parse(String input[]) {
        InputType inputType = ConsoleController.InputType.valueOf(input[0]);
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
                if (currentScene == ViewName.CREATOR) {
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
                currentScene = ViewName.PLAY;
                cView.setHeroSelected(input[1]);
                cView.showView(currentScene);
                CreatorAction action = new CreatorAction(CreatorAction.Types.PLAY);
                action.actionPerformed(null);

            }
            default ->
                throw new InvalidCommandException("unauthorised in this context");
        }
    }

    private void parsePlay(InputType inputType) {

        switch (inputType) {
            case InputType.HELP -> {
                cView.addContextMessage(PLAY_HELP_MSG);
            }
            case InputType.NORTH, InputType.EAST, InputType.SOUTH, InputType.WEST -> {
                if (model.isFighting() || model.hasDropped()) {
                    throw new InvalidCommandException("unauthorised in this context");
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
                    throw new InvalidCommandException("unauthorised in this context");
                }
            }
            case InputType.TAKE, InputType.DISCARD -> {
                if (model.hasDropped()) {
                    HeroAction action = new HeroAction(Model.Action.valueOf(inputType.name()), null);
                    action.actionPerformed(null);
                } else {
                    throw new InvalidCommandException("unauthorised in this context");
                }
            }
            default ->
                throw new InvalidCommandException("unauthorised in this context");
        }
    }

}
