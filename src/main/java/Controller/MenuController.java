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
package Controller;

import View.GuiView;
import View.MenuView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Pril Wolf
 */
public class MenuController {

    private final GuiView window;
    private final MenuView view;

    public MenuController(GuiView window, MenuView view) {
        this.window = window;
        this.view = view;
    }

    public void init() {
        view.switchItem.addActionListener(new SwitchAction());
        view.saveItem.addActionListener(new SaveAction());
        view.selectItem.addActionListener(new SelectAction());
        view.exitItem.addActionListener(new ExitAction());
    }

    private class SwitchAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("switch to cli requested");
        }
    }

    private class SaveAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("save requested");
        }
    }

    private class SelectAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            window.showCreatorView();
            System.out.println("switch to selector requested");
        }
    }

    private class ExitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("exit requested");
            window.dispatchEvent(
                    new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }
}
