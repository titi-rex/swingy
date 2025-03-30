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

import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.View.Console.ConsoleView;
import fr.ft.swingy.View.View;

/**
 *
 * @author Pril Wolf
 */
public class ConsoleAdapter {

    public enum Input {
        HELP,
        GUI,
        EXIT,
        CREATE,
        DELETE,
        PLAY,
        NORTH,
        EAST,
        SOUTH,
        WEST,
        FIGHT,
        RUN,
        YES,
        NO
    }

    private final ConsoleView view;

    public ConsoleAdapter(ConsoleView view) {
        this.view = view;
    }

    public void parse(ConsoleView.Scenes currentScene, String input) {
        ConsoleAdapter.Input inputEnum;
        try {
            inputEnum = ConsoleAdapter.Input.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.err.println("bad user input!");
            return;
        }

        switch (inputEnum) {
            case ConsoleAdapter.Input.HELP -> {
                view.getHelp().triggerAction(null);
            }
            case ConsoleAdapter.Input.EXIT -> {
                view.getExit().triggerAction(null);
            }
            case ConsoleAdapter.Input.GUI -> {
                view.getSwitch().triggerAction(null);
            }
            // parse value and put them 
            case ConsoleAdapter.Input.CREATE -> {
                view.getNameSelected();
                view.getRoleSelected();
                view.getCreate().triggerAction(null);
            }
            // parse value and put them 
            case ConsoleAdapter.Input.DELETE -> {
                view.getCharacters().getSelectedValue();
                view.getCreate().triggerAction(null);
            }
            // parse value and put them 
            case ConsoleAdapter.Input.PLAY -> {
                view.getCharacters().getSelectedValue();
                view.getCreate().triggerAction(null);
            }
            default ->
                throw new UnsupportedOperationException("enum switch not fully handled");
        }

    }

}
