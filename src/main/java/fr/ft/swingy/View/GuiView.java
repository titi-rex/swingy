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
package fr.ft.swingy.View;

import fr.ft.swingy.Controller.CreatorController;
import fr.ft.swingy.Controller.MenuController;
import fr.ft.swingy.Controller.PlayController;
import fr.ft.swingy.Model.CreatorModel;
import fr.ft.swingy.Model.Creature;
import fr.ft.swingy.Model.PlayModel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Pril Wolf
 */
public class GuiView extends JFrame {

    public static final int WINDOW_SIZE = 800;
    public static final int WINDOW_SIZE_MIN = 500;

    public static final String TITLE = "Swingy - The best RPG you never played";
    public static final String CREATE_VIEW_NAME = "create";
    public static final String PLAY_VIEW_NAME = "play";

    private final MenuView menuView;
    private final MenuController menuController;
    private final CreatorView creatorPanel;
    private final CreatorModel creatorModel;
    private final PlayView playPanel;
    private PlayModel playModel;
    private final PlayController playController;
    private final JPanel cards;
    private final CardLayout cLayout;

    public GuiView() throws HeadlessException {
        super(TITLE);
        setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setMinimumSize(new Dimension(WINDOW_SIZE_MIN, WINDOW_SIZE_MIN));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        cLayout = new CardLayout();
        cards = new JPanel(cLayout);
        add(cards);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(GuiView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        menuController = new MenuController(this, menuView);
        menuController.init();
        setJMenuBar(menuView);

//        create creatorview
        creatorModel = new CreatorModel();
        creatorPanel = new CreatorView(creatorModel.roles, creatorModel.characters);
        CreatorController creatorController = new CreatorController(this, creatorPanel, creatorModel);
        creatorController.init();
        cards.add(creatorPanel, CREATE_VIEW_NAME);

//        create play view
        playPanel = new PlayView();
        playController = new PlayController(this, playPanel);
        playController.init();
        cards.add(playPanel, PLAY_VIEW_NAME);

    }

    public void showCreatorView() {
        menuController.unsetModel();
        creatorModel.refresh();
        cLayout.show(cards, CREATE_VIEW_NAME);
    }

    public void showPlayView(Creature newHero) {
        loadGame(newHero);
        cLayout.show(cards, PLAY_VIEW_NAME);
        playPanel.stateChanged(null);

    }

    private void loadGame(Creature newHero) {
        playModel = new PlayModel(newHero);
        playModel.setView(playPanel);
        playPanel.setModel(playModel);
        playPanel.clearMap();
        menuController.setModel(playModel);
        playController.setModel(playModel);

    }

}


        /*
javax.swing.UIManager$LookAndFeelInfo[Metal javax.swing.plaf.metal.MetalLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[Nimbus javax.swing.plaf.nimbus.NimbusLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[CDE/Motif com.sun.java.swing.plaf.motif.MotifLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[GTK+ com.sun.java.swing.plaf.gtk.GTKLookAndFeel]
         */
