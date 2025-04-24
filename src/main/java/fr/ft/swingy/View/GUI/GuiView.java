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

import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.Model.Entity.Roles;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import fr.ft.swingy.View.View;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeListener;

/**
 * GUI View Class for Swingy, display game and take user input from a GUI.
 *
 * @author Pril Wolf
 */
public class GuiView extends JFrame implements View {

    public static final int WINDOW_SIZE = 800;
    public static final int WINDOW_SIZE_MIN = 500;

    public static final String TITLE = "Swingy - The best RPG you never played";

    private final MenuBarPanel menuPanel;
    private final CreatorScene creatorPanel;
    private final PlayScene playPanel;
    private final JPanel cards;
    private final CardLayout cLayout;

    /**
     * Create a GuiView. By default the Close Operation is DO_NOTHING
     *
     * @param model
     * @throws HeadlessException if no graphic environment
     */
    public GuiView(Model model) throws HeadlessException {
        super(TITLE);
        initWindow();

        cLayout = new CardLayout();
        cards = new JPanel(cLayout);
        add(cards);

        menuPanel = new MenuBarPanel();
        setJMenuBar(menuPanel);

        creatorPanel = new CreatorScene();
        creatorPanel.getCharacterList().setModel(model.getCharactersListModel());
        creatorPanel.getRolesBox().setModel((ComboBoxModel) model.getRolesModel());
        cards.add(creatorPanel, ViewName.CREATOR.name());

        playPanel = new PlayScene();
        playPanel.setModel(model);
        cards.add(playPanel, ViewName.PLAY.name());
    }

    private void initWindow() {
        setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setMinimumSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setMaximumSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GuiView.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /**
     * Set Frame visible
     */
    @Override
    public void start() {
        setVisible(true);
    }

    /**
     * Display error message in popup message
     *
     * @param message
     */
    @Override
    public void error(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Change current view
     *
     * @param viewName
     */
    @Override
    public void showView(ViewName viewName) {
        cLayout.show(cards, viewName.name());
    }

    @Override
    public ChangeListener getPlayViewListener() {
        return playPanel;
    }

    /**
     * Send a window close event
     */
    @Override
    public void requestClose() {
        this.dispatchEvent(
                new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Update a {@link fr.ft.swingy.View.GUI.CreatureDataPanel}
     *
     * @param creature
     */
    @Override
    public void updateInfoCreature(Creature creature) {
        creatorPanel.getInfoStat().update(creature);
    }

    /**
     * Update a {@link fr.ft.swingy.View.GUI.CreatureDataPanel}
     *
     * @param role
     */
    @Override
    public void updateInfoCreature(Roles role) {
        creatorPanel.getInfoStat().update(role);
    }

    public JComboBox getRoles() {
        return creatorPanel.getRolesBox();
    }

    public JList getCharacters() {
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
    public Creature getHeroSelected() {
        return (Creature) creatorPanel.getCharacterList().getSelectedValue();
    }

    @Override
    public boolean isHeroSelected() {
        return creatorPanel.getCharacterList().isSelectionEmpty() == false;

    }

    public JButton getCreate() {
        return creatorPanel.getCreateButton();
    }

    public JButton getDelete() {
        return creatorPanel.getDeleteButton();
    }

    public JButton getPlay() {
        return creatorPanel.getPlayButton();
    }

    public JMenuItem getSwitch() {
        return menuPanel.getSwitchItem();
    }

    public JMenuItem getExit() {
        return menuPanel.getExitItem();
    }

    public JButton getNorth() {
        return playPanel.getCommandBar().getNorthButton();
    }

    public JButton getEast() {
        return playPanel.getCommandBar().getEastButton();

    }

    public JButton getSouth() {
        return playPanel.getCommandBar().getSouthButton();

    }

    public JButton getWest() {
        return playPanel.getCommandBar().getWestButton();
    }

    public JButton getFight() {
        return playPanel.getCommandBar().getFightButton();
    }

    public JButton getRun() {
        return playPanel.getCommandBar().getRunButton();
    }

    public JButton getYes() {
        return playPanel.getCommandBar().getYesButton();
    }

    public JButton getNo() {
        return playPanel.getCommandBar().getNoButton();
    }

}
