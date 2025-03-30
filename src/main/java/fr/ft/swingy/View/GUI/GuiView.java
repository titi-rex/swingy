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

import fr.ft.swingy.View.GUI.Component.SwingyComboBox;
import fr.ft.swingy.View.GUI.Component.SwingyList;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.View.ViewElement;
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
import javax.swing.ComboBoxModel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Pril Wolf
 */
public class GuiView extends JFrame implements View {

    private Model model;

    public static final int WINDOW_SIZE = 800;
    public static final int WINDOW_SIZE_MIN = 500;

    public static final String TITLE = "Swingy - The best RPG you never played";

    private final MenuBarPanel menuPanel;
    private final CreatorView creatorPanel;
    private final PlayView playPanel;
    private final JPanel cards;
    private final CardLayout cLayout;

    /*
javax.swing.UIManager$LookAndFeelInfo[Metal javax.swing.plaf.metal.MetalLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[Nimbus javax.swing.plaf.nimbus.NimbusLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[CDE/Motif com.sun.java.swing.plaf.motif.MotifLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[GTK+ com.sun.java.swing.plaf.gtk.GTKLookAndFeel]
     */
    public GuiView(Model model) throws HeadlessException {
        super(TITLE);
        this.model = model;
        initWindow();

        cLayout = new CardLayout();
        cards = new JPanel(cLayout);
        add(cards);

        menuPanel = new MenuBarPanel();
        setJMenuBar(menuPanel);

        creatorPanel = new CreatorView();
        creatorPanel.getCharacterList().setModel(model.getCharactersListModel());
        creatorPanel.getRolesBox().setModel((ComboBoxModel) model.getRolesModel());
        cards.add(creatorPanel, ViewName.CREATOR.name());

        playPanel = new PlayView();
        playPanel.setModel(model);
        cards.add(playPanel, ViewName.PLAY.name());

    }

    private void initWindow() {
        setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setMinimumSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setMaximumSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new windowHandler());

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiView.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    @Override
    public void start() {
        setVisible(true);
        showView(ViewName.CREATOR);
    }

    @Override
    public void showView(ViewName viewName) {
        cLayout.show(cards, viewName.name());
    }

    @Override
    public ChangeListener getPlayViewListener() {
        return playPanel;
    }

    @Override
    public void requestClose() {
        this.dispatchEvent(
                new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public CreatureView getInfoCreature() {
        return creatorPanel.getInfoStat();
    }

    @Override
    public SwingyComboBox getRoles() {
        return creatorPanel.getRolesBox();
    }

    @Override
    public SwingyList getCharacters() {
        return creatorPanel.getCharacterList();
    }

    @Override
    public String getNameSelected() {
        String name = creatorPanel.getNameField().getText();
        creatorPanel.getNameField().setText("");
        return name;
    }

    @Override
    public Roles getRoleSelected() {
        return (Roles) creatorPanel.getRolesBox().getSelectedItem();
    }

    @Override
    public ViewElement getCreate() {
        return creatorPanel.getCreateButton();
    }

    @Override
    public ViewElement getDelete() {
        return creatorPanel.getDeleteButton();
    }

    @Override
    public ViewElement getPlay() {
        return creatorPanel.getPlayButton();
    }

    @Override
    public ViewElement getSwitch() {
        return menuPanel.getSwitchItem();
    }

    @Override
    public ViewElement getExit() {
        return menuPanel.getExitItem();
    }

    @Override
    public ViewElement getNorth() {
        return playPanel.getCommandBar().getNorthButton();
    }

    @Override
    public ViewElement getEast() {
        return playPanel.getCommandBar().getEastButton();

    }

    @Override
    public ViewElement getSouth() {
        return playPanel.getCommandBar().getSouthButton();

    }

    @Override
    public ViewElement getWest() {
        return playPanel.getCommandBar().getWestButton();
    }

    @Override
    public ViewElement getFight() {
        return playPanel.getCommandBar().getFightButton();
    }

    @Override
    public ViewElement getRun() {
        return playPanel.getCommandBar().getRunButton();
    }

    @Override
    public ViewElement getYes() {
        return playPanel.getCommandBar().getYesButton();
    }

    @Override
    public ViewElement getNo() {
        return playPanel.getCommandBar().getNoButton();
    }

    @Override
    public ViewElement getHelp() {
        return null;
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

}
