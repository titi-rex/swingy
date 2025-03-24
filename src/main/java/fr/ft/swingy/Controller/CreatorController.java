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
package fr.ft.swingy.Controller;

import fr.ft.swingy.Model.CreatorModel;
import fr.ft.swingy.Model.Creature;
import fr.ft.swingy.Model.Roles;
import fr.ft.swingy.View.GUI.CreatorView;
import fr.ft.swingy.View.GUI.GuiView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Pril Wolf
 */
public class CreatorController {

    private Controller main;
    private CreatorView view;
    private CreatorModel model;

    public CreatorController() {
    }

    public void init() {

        view.getCreateButton().addActionListener(new CreateAction());
        view.getDeleteButton().addActionListener(new DeleteAction());
        view.getPlayButton().addActionListener(new PlayAction());
        view.getRolesBox().addActionListener(new BoxSelectAction());
        view.getCharacterList().addListSelectionListener(new ListSelectAction());
    }

    private class CreateAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameField().getText();
            Roles role = (Roles) view.getRolesBox().getSelectedItem();
            try {
                model.create(name, role);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private class DeleteAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = characterSelected();
            if (idx < 0) {
                return;
            }
            System.out.println("delete requested at " + idx);
            model.delete(idx);
        }
    }

    private class PlayAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = characterSelected();
            if (idx < 0) {
                return;
            }
            System.out.println("play requested");
            main.loadNewGame();
        }
    }

    private class BoxSelectAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Roles role = (Roles) view.getRolesBox().getSelectedItem();
            view.getInfoStat().update(role);
        }
    }

    private class ListSelectAction implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList list = (JList) e.getSource();
            Creature crt = (Creature) list.getSelectedValue();
            if (crt != null) {
                view.getInfoStat().update(crt);
            }
        }
    }

    private int characterSelected() {
        return view.getCharacterList().getSelectedIndex();
    }

    public Controller getMain() {
        return main;
    }

    public void setMain(Controller main) {
        this.main = main;
    }

    public CreatorView getView() {
        return view;
    }

    public void setView(CreatorView view) {
        this.view = view;
    }

    public CreatorModel getModel() {
        return model;
    }

    public void setModel(CreatorModel model) {
        this.model = model;
    }

}
