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
package View;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Pril Wolf
 */
public class MenuView extends JMenuBar {

    public JMenuItem switchItem;
    public JMenuItem saveItem;
    public JMenuItem selectItem;
    public JMenuItem exitItem;

    public MenuView() {
        switchItem = new JMenuItem("Switch");
        switchItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));

        saveItem = new JMenuItem("Save");
        saveItem.setEnabled(false);
        saveItem.setMnemonic(KeyEvent.VK_S);

        selectItem = new JMenuItem("Selector");
        exitItem = new JMenuItem("Exit");

        JMenu menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_M);

        menu.add(switchItem);
        menu.add(saveItem);
        menu.add(selectItem);
        menu.add(exitItem);

        add(menu);
    }
}
