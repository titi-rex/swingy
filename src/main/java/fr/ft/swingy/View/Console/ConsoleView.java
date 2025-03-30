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

import fr.ft.swingy.Controller.ConsoleAdapter;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.View.GUI.CreatureView;
import fr.ft.swingy.View.GUI.Component.SwingyComboBox;
import fr.ft.swingy.View.GUI.Component.SwingyList;
import fr.ft.swingy.View.View;
import fr.ft.swingy.View.ViewElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Pril Wolf
 */
public class ConsoleView implements View {

    private Model model;

    private boolean running;

    public enum Scenes {
        CREATOR, PLAY
    };

    private Scenes currentScene;

    private final ConsoleCommand helpCommand;
    private final ConsoleCommand switchCommand;
    private final ConsoleCommand exitCommand;
    private final ConsoleCommand createCommand;
    private final ConsoleCommand deleteCommand;
    private final ConsoleCommand playCommand;
    private final ConsoleCommand northCommand;
    private final ConsoleCommand eastCommand;
    private final ConsoleCommand southCommand;
    private final ConsoleCommand westCommand;
    private final ConsoleCommand fightCommand;
    private final ConsoleCommand runCommand;
    private final ConsoleCommand yesCommand;
    private final ConsoleCommand noCommand;
    
    private final PlayView playView;
    private final ConsoleAdapter adapter;
    
    private SwingyComboBox rolesBox;
    private SwingyList charactersList;
    

    public ConsoleView(Model model) {
        currentScene = Scenes.CREATOR;
        adapter = new ConsoleAdapter(this);

        helpCommand = new ConsoleCommand();
        switchCommand = new ConsoleCommand();
        exitCommand = new ConsoleCommand();

        createCommand = new ConsoleCommand();
        deleteCommand = new ConsoleCommand();
        playCommand = new ConsoleCommand();

        northCommand = new ConsoleCommand();
        eastCommand = new ConsoleCommand();
        southCommand = new ConsoleCommand();
        westCommand = new ConsoleCommand();
        fightCommand = new ConsoleCommand();
        runCommand = new ConsoleCommand();
        yesCommand = new ConsoleCommand();
        noCommand = new ConsoleCommand();

        playView = new PlayView(model);
    }

    @Override
    public void start() {
        running = true;
        loop();
    }

    private void loop() {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (running) {
                switch (currentScene) {
                    case Scenes.CREATOR:
                        showView(View.ViewName.CREATOR);
                        break;
                    case Scenes.PLAY:
                        showView(View.ViewName.PLAY);
                        break;
                    default:
                        throw new UnsupportedOperationException("enum switch not fully handled");
                }
                System.out.print("> ");
                String input = consoleReader.readLine();
                System.out.print(" got: " + input);
                adapter.parse(currentScene, input.trim().toUpperCase());
            }

        } catch (IOException | UnsupportedOperationException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void showView(ViewName viewName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void requestClose() {
        running = false;
    }

    @Override
    public ChangeListener getPlayViewListener() {
        return playView;
    }


    @Override
    public SwingyComboBox getRoles() {
        return rolesBox;
    }

    @Override
    public SwingyList getCharacters() {
        return charactersList;
    }

    @Override
    public String getNameSelected() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Roles getRoleSelected() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CreatureView getInfoCreature() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ViewElement getHelp() {
        return helpCommand;
    }

    @Override
    public ViewElement getSwitch() {
        return switchCommand;
    }

    @Override
    public ViewElement getExit() {
        return exitCommand;
    }

    @Override
    public ViewElement getCreate() {
        return createCommand;
    }

    @Override
    public ViewElement getDelete() {
        return deleteCommand;
    }

    @Override
    public ViewElement getPlay() {
        return playCommand;
    }

    @Override
    public ViewElement getNorth() {
        return northCommand;
    }

    @Override
    public ViewElement getEast() {
        return eastCommand;
    }

    @Override
    public ViewElement getSouth() {
        return southCommand;
    }

    @Override
    public ViewElement getWest() {
        return westCommand;
    }

    @Override
    public ViewElement getFight() {
        return fightCommand;
    }

    @Override
    public ViewElement getRun() {
        return runCommand;
    }

    @Override
    public ViewElement getYes() {
        return yesCommand;
    }

    @Override
    public ViewElement getNo() {
        return noCommand;
    }

}
