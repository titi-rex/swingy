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

import fr.ft.swingy.Controller.Controller;
import fr.ft.swingy.Controller.DefaultController;
import fr.ft.swingy.Model.DefaultModel;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.Console.ConsoleView;
import fr.ft.swingy.View.GUI.GuiView;
import javax.swing.JFrame;
import fr.ft.swingy.View.View;
import java.util.logging.Level;

/**
 *
 * @author Pril Wolf
 */
public class App {

    public static final String ERROR_ENUM_SWITCH = "fatal: missing enum handling in switch";
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar swingy.jar gui/consol");
            return;
        }
        JFrame.setDefaultLookAndFeelDecorated(true);

        try {

            View view;
            Model model = new DefaultModel();
            if ("gui".equals(args[0])) {
                view = new GuiView(model);
            } else {
                view = new ConsoleView(model);
                java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
            }

            model.setView(view);
            
            Controller controller = new DefaultController(view, model);
            controller.init();

            view.start();
            System.out.println("main end");

        } catch (RuntimeException e) {
            System.err.println("runtime error: " + e.getLocalizedMessage());
        }
    }
}
