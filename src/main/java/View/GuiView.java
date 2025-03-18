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

import Controller.CreatorController;
import Controller.MenuController;
import Controller.PlayController;
import Model.CreatorModel;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Pril Wolf
 */
public class GuiView extends JFrame {

    public static final int WINDOW_SIZE = 500;
    public static final String TITLE = "Swingy - The best RPG you never played";
    private final MenuView menuView;
    private final CreatorView creatorPanel;
    private final PlayView playPanel;

    public GuiView() throws HeadlessException {
        super(TITLE);
        Dimension size = new Dimension(WINDOW_SIZE, WINDOW_SIZE);
        setSize(size);
        setMinimumSize(size);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiView frame = (GuiView) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Windows closed");
            }
        });

//        create and init menubar
        menuView = new MenuView();
        MenuController menuController = new MenuController(this, menuView);
        menuController.init();
        setJMenuBar(menuView);

//        create play view
        playPanel = new PlayView();
        PlayController playController = new PlayController(this, playPanel);
        playController.init();
        add(playPanel);

//        create creatorview
        CreatorModel creatorModel = new CreatorModel();
        creatorPanel = new CreatorView(creatorModel.roles, creatorModel.characters);
        CreatorController creatorController = new CreatorController(this, creatorPanel, creatorModel);
        creatorController.init();
        add(creatorPanel);

        pack();
    }

    public void showCreatorView() {
        playPanel.setVisible(false);
        creatorPanel.setVisible(true);
    }

    public void showPlayView() {
        creatorPanel.setVisible(false);
        playPanel.setVisible(true);
    }

    private void loadGame() {
        
    }
}
