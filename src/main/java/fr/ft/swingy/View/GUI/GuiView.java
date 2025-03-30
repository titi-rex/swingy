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
package fr.ft.swingy.View.GUI;

import fr.ft.swingy.Controller.Controller;
import fr.ft.swingy.Controller.DefaultController;
import fr.ft.swingy.Controller.CreatorController;
import fr.ft.swingy.Controller.MenuController;
import fr.ft.swingy.Controller.PlayController;
import fr.ft.swingy.Model.CreatorModel;
import fr.ft.swingy.Model.Creature;
import fr.ft.swingy.Model.DefaultModel;
import fr.ft.swingy.Model.Model;
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
import fr.ft.swingy.View.View;

/**
 *
 * @author Pril Wolf
 */
public class GuiView extends JFrame implements View {

    private Model model;
    private Controller controller;

    public static final int WINDOW_SIZE = 800;
    public static final int WINDOW_SIZE_MIN = 500;

    public static final String TITLE = "Swingy - The best RPG you never played";
    public static final String CREATE_VIEW_NAME = "create";
    public static final String PLAY_VIEW_NAME = "play";

    private final MenuBarView menuView;
    private final CreatorView creatorPanel;
    private final PlayView playPanel;
    private final JPanel cards;
    private final CardLayout cLayout;

    public GuiView() throws HeadlessException {
        super(TITLE);
        setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setMinimumSize(new Dimension(WINDOW_SIZE_MIN, WINDOW_SIZE_MIN));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(GuiView.class.getName()).log(Level.SEVERE, null, ex);
        }
        addWindowListener(new windowHandler());

        cLayout = new CardLayout();
        cards = new JPanel(cLayout);
        add(cards);
        
        menuView = new MenuBarView();
        setJMenuBar(menuView);
        
        creatorPanel = new CreatorView();
        cards.add(creatorPanel, CREATE_VIEW_NAME);

        playPanel = new PlayView();
        cards.add(playPanel, PLAY_VIEW_NAME);
    }

    @Override
    public void start() {
        setVisible(true);
        showCreatorView();
    }

    @Override
    public void showCreatorView() {
//        menuController.unsetModel();
//        creatorModel.refresh();
        cLayout.show(cards, CREATE_VIEW_NAME);
    }

    @Override
    public void showPlayView() {
        playPanel.stateChanged(null); // -> should be called by model
        cLayout.show(cards, PLAY_VIEW_NAME);
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        creatorPanel.getCharacterList().setModel(model.getCreatorModel().characters);
        creatorPanel.getRolesBox().setModel(model.getCreatorModel().roles);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public MenuBarView getMenuBarView() {
        return menuView;
    }

    @Override
    public void requestClose() {
        this.dispatchEvent(
                        new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private class windowHandler extends WindowAdapter {

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

        @Override
        public void windowClosed(WindowEvent e) {
            System.out.println("Windows closed");
        }
    }

    @Override
    public CreatorView getCreatorPanel() {
        return creatorPanel;
    }

    @Override
    public PlayView getPlayPanel() {
        return playPanel;
    }

}


/*
javax.swing.UIManager$LookAndFeelInfo[Metal javax.swing.plaf.metal.MetalLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[Nimbus javax.swing.plaf.nimbus.NimbusLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[CDE/Motif com.sun.java.swing.plaf.motif.MotifLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[GTK+ com.sun.java.swing.plaf.gtk.GTKLookAndFeel]
 */
