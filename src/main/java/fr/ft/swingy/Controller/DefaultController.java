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

import fr.ft.swingy.Model.Creature;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.GUI.MenuBarView;
import fr.ft.swingy.View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Pril Wolf
 */
public class DefaultController implements Controller {

    private View view;
    private Model model;

    private final MenuController menuController;
    private final CreatorController creatorController;
    private final PlayController playController;

    public DefaultController() {
        this.menuController = new MenuController();
        this.creatorController = new CreatorController();
        this.playController = new PlayController();

    }

    @Override
    public void init() {
        creatorController.setMain(this);
        menuController.init();
        creatorController.init();
        playController.init();
    }

    @Override
    public void loadNewGame() {
        Creature heroSelected = (Creature) view
                .getCreatorPanel()
                .getCharacterList()
                .getSelectedValue();
        model.createNewGame(heroSelected);
        playController.setModel(model.getPlayModel());
        menuController.enabledSave(true);
        view.showPlayView();
    }

    public View getView() {
        return view;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        creatorController.setView(view.getCreatorPanel());
        playController.setView(view.getPlayPanel());
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        creatorController.setModel(model.getCreatorModel());
    }

    public CreatorController getCreatorController() {
        return creatorController;
    }

    public PlayController getPlayController() {
        return playController;
    }

    private class MenuController {

        public MenuController() {
        }

        public void init() {
            MenuBarView menuBarView = view.getMenuBarView();
            menuBarView.switchItem.addActionListener(new SwitchAction());
            menuBarView.saveItem.addActionListener(new SaveAction());
            menuBarView.exitItem.addActionListener(new ExitAction());
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
                model.getPlayModel().save();
            }
        }

        private class ExitAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("exit requested");
                view.requestClose();
            }
        }

        public void enabledSave(boolean bool) {
            view.getMenuBarView().saveItem.setEnabled(bool);
        }

    }
}
