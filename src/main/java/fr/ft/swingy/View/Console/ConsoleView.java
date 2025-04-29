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
package fr.ft.swingy.View.Console;

import static fr.ft.swingy.App.ERROR_ENUM_SWITCH;
import fr.ft.swingy.Controller.InvalidCommandException;
import fr.ft.swingy.Model.Entity.Cell;
import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.View.View;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Console View Class for Swingy, display game and take user input from a
 * console
 *
 * @author Pril Wolf
 */
public final class ConsoleView implements View, ChangeListener {

    /**
     * Input handler are in charge to parse and react to user input
     */
    public interface InputHandler {

        void consume(String input);
    }

    /**
     * Private interface for holding current printer
     */
    private interface Printer {

        void print();
    }

    private final Model model;

    private final BufferedReader consoleReader;
    private final String rolesList;
    private boolean running;
    private InputHandler inputHandler;
    private Printer printer;
    private String contextMessage;

    private Creature heroSelected;
    private String nameSelected;
    private Roles roleSelected;

    public static final String NEWLINE = "\n";

    /**
     *
     * @param model
     */
    public ConsoleView(Model model) {
        this.model = model;
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        printer = this::printCreator;
        contextMessage = "";
        System.out.println(NEWLINE + "Welcome to Swingy!" + NEWLINE);
        rolesList = storeRoleList();
    }

    /**
     * Start view loop
     */
    @Override
    public void start() {
        running = true;
        loop();
    }

    /**
     * Display error message in the contextMessage area
     *
     * @param message
     */
    @Override
    public void error(String message) {
        addContextMessage(message);
    }

    private void loop() {
        try {
            while (running) {
                printer.print();
                consumeContextMessage();
                printPrompt();
                inputHandler.consume(consoleReader.readLine());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Change current view
     *
     * @param viewName
     */
    @Override
    public void showView(SceneName viewName) {
        switch (viewName) {
            case SceneName.CREATOR ->
                printer = this::printCreator;
            case SceneName.PLAY ->
                printer = this::printPlay;
            default ->
                throw new AssertionError(ERROR_ENUM_SWITCH);
        }

    }

    private void printPrompt() {
        System.out.print("> ");
    }

    private void consumeContextMessage() {
        if (contextMessage != null && contextMessage.isEmpty() == false) {
            System.out.println(contextMessage);
        }
        contextMessage = "";
    }

    /**
     * Add a contextual message to display in the contextMessage area, message
     * are displayed in order
     *
     * @param contextMessage
     */
    public void addContextMessage(String contextMessage) {
        this.contextMessage += contextMessage + NEWLINE;
    }

    private void printCreator() {
        String tmp = "Existing Hero:" + NEWLINE;

        int size = model.getCharactersListModel().getSize();
        for (int i = 0; i < size; i++) {
            Creature hero = (Creature) model.getCharactersListModel().getElementAt(i);
            tmp += hero.toString() + " lvl " + hero.getLevel() + "\t|\t(" + hero.getAttack() + "/" + hero.getDefense() + "/" + hero.getHitPoint() + ")";
            if (hero.getArtifact() != null) {
                tmp += " equipped with " + hero.getArtifact().toString() + NEWLINE;
            } else {
                tmp += NEWLINE;
            }
        }
        System.out.println(tmp);
    }

    private String storeRoleList() {
        String tmp = "Availables classes:" + NEWLINE;

        int size = model.getRolesModel().getSize();
        for (int i = 0; i < size; i++) {
            Roles role = (Roles) model.getRolesModel().getElementAt(i);
            tmp += role.toString() + "\t|\tattack: " + role.attack + ", defense:  " + role.defense + ", hp: " + role.hitPoint + NEWLINE;
        }
        return tmp;
    }

    /**
     *
     * @return
     */
    public String getRolesList() {
        return rolesList;
    }

    private void printPlay() {
        printCreature("HERO\t", model.getHero());
        printMap();
        if (model.isFighting()) {
            printCreature("ENNEMY\t", model.getCellAt(model.getHeroCoordinate()).getCreature());
        }
        printGameLogs();
    }

    private void printMap() {
        System.out.println("MAP");
        int size = model.getSize();
        String tmp = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (model.getHeroCoordinate().equals(new Point(j, i))) {
                    tmp += " H ";
                } else if (model.getCellAt(new Point(j, i)).getType() == Cell.Type.EMPTY) {
                    tmp += " . ";
                } else {
                    tmp += " E ";
                }
            }
            tmp += NEWLINE;
        }
        System.out.println(tmp);
    }

    private void printCreature(String prefix, Creature creature) {
        String tmp = "";
        if (prefix != null) {
            tmp = prefix;
        }
        tmp += creature.toString() + " lvl " + creature.getLevel() + "\t|\t(" + creature.getAttack() + "/" + creature.getDefense() + "/" + creature.getHitPoint() + ")";
        if (creature.getArtifact() != null) {
            tmp += " equipped with " + creature.getArtifact().toString() + NEWLINE;
        }
        System.out.println(tmp);
    }

    private void printGameLogs() {
        System.out.println("LOGS");
        try {
            Document doc = model.getGameLogs();
            String lines[] = doc.getText(0, doc.getLength()).split("\n");
            int start = Math.max(lines.length - 6, 0);
            for (int i = start; i < lines.length; i++) {
                System.out.println(lines[i]);
            }
        } catch (BadLocationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Stop the view loop
     */
    @Override
    public void requestClose() {
        running = false;
    }

    public void setHeroSelected(String name) {
        int size = model.getCharactersListModel().getSize();
        for (int i = 0; i < size; i++) {
            Creature hero = (Creature) model.getCharactersListModel().getElementAt(i);
            if (hero.getName().equals(name)) {
                this.heroSelected = hero;
                break;
            }
        }
        if (heroSelected == null) {
            throw new InvalidCommandException("unknow hero");
        }
    }

    /**
     *
     * @param l
     */
    public void addInputListener(InputHandler l) {
        this.inputHandler = l;
    }

    /**
     * Actually does nothing because reprint is handled by the loop
     *
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {

    }

    @Override
    public Creature getHeroSelected() {
        Creature tmp = heroSelected;
        heroSelected = null;
        return tmp;
    }

    @Override
    public boolean isHeroSelected() {
        return heroSelected != null;
    }

    @Override
    public ChangeListener getPlaySceneListener() {
        return this;
    }

    @Override
    public String getNameSelected() {
        String tmp = nameSelected;
        nameSelected = null;
        return tmp;
    }

    @Override
    public Roles getRoleSelected() {
        Roles tmp = roleSelected;
        roleSelected = null;
        return tmp;
    }

    public void setNameSelected(String nameSelected) {
        this.nameSelected = nameSelected;
    }

    public void setRoleSelected(Roles roleSelected) {
        this.roleSelected = roleSelected;
    }

    @Override
    public void updateInfoCreature(Creature creature) {
    }

    @Override
    public void updateInfoCreature(Roles role) {
    }

}
