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
package fr.ft.swingy;

import fr.ft.swingy.Controller.ConsoleController;
import fr.ft.swingy.Controller.Controller;
import fr.ft.swingy.Controller.GuiController;
import fr.ft.swingy.Model.DefaultModel;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.Console.ConsoleView;
import fr.ft.swingy.View.GUI.GuiView;
import javax.swing.JFrame;
import fr.ft.swingy.View.View;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pril Wolf
 */
public class App {

    public static final String ERROR_ENUM_SWITCH = "fatal: missing enum handling in switch";
    public static final String ERROR_USAGE = "Usage: java -jar swingy.jar gui/consol";
    public static final String ERROR_SWITCH = "Error: trying to switch view without an App instance";

    private static App INSTANCE;

    private final Model model;
    private View view;
    private Controller controller;
    private boolean gui;

    public App(boolean gui) {
        this.gui = gui;
        INSTANCE = this;
        JFrame.setDefaultLookAndFeelDecorated(true);
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        model = new DefaultModel();
        if (gui) {
            initGui();
        } else {
            initConsole();
        }
    }

    public static void switchView() {
        if (INSTANCE != null) {
            if (INSTANCE.gui) {
                INSTANCE.gui = false;
                INSTANCE.initConsole();
            } else {
                INSTANCE.gui = true;
                INSTANCE.initGui();
            }
            INSTANCE.start();
        } else {
            System.err.println();
        }
    }

    private void initGui() {
        view = new GuiView(model);
        controller = new GuiController(view, model);
        model.setView(view);
        controller.init();
    }

    private void initConsole() {
        view = new ConsoleView(model);
        controller = new ConsoleController(view, model);
        model.setView(view);
        controller.init();
    }

    public void start() {
        view.start();
    }

    public static void main(String[] args) {

        try {
            App app;
            switch (args[0]) {
                case "gui" ->
                    app = new App(true);
                case "console" ->
                    app = new App(false);
                default ->
                    throw new NullPointerException();
            }
            app.start();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.err.println(ERROR_USAGE);

        } catch (RuntimeException e) {
            System.err.println("runtime error: " + e.getMessage());
        }
    }
}
