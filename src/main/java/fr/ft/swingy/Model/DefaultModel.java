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
package fr.ft.swingy.Model;

import fr.ft.swingy.Controller.Controller;
import fr.ft.swingy.View.View;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Pril Wolf
 */
public class DefaultModel implements Model, AutoCloseable {

    private View view;
    private Controller controller;

    private SessionFactory sessionFactory;

    private final CreatorModel creatorModel;
    private final ComboBoxModel rolesBoxModel;
    private DefaultListModel charactersListModel;

    private PlayModel playModel;

    public DefaultModel() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        creatorModel = new CreatorModel();
        rolesBoxModel = new DefaultComboBoxModel(Roles.heroes());

        List<Creature> data = sessionFactory.fromSession(session -> {
            return session
                    .createSelectionQuery("from Creature", Creature.class)
                    .getResultList();
        });

        charactersListModel = new DefaultListModel();
        charactersListModel.addAll(data);

        Creature.setUpValidator();
    }

    @Override
    public void createNewGame(Creature hero) {
        playModel = new PlayModel(hero);
        playModel.setView(view.getPlayPanel());
        view.getPlayPanel().setModel(getPlayModel());
        view.getPlayPanel().stateChanged(null);
    }

    @Override
    public void saveGame() {
        if (playModel != null) {
            playModel.save();
            System.out.println("game saved");
        }
    }

    @Override
    public void closeGame() {
        if (playModel != null) {
            playModel.close();
            playModel = null;
            System.out.println("game closed");
        }
    }

    @Override
    public void close() {
        sessionFactory.close();
    }

    @Override
    public void setView(View view) {
        this.view = view;
        charactersListModel.addListDataListener(view.getCreatorPanel());
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public PlayModel getPlayModel() {
        return playModel;
    }

    public void setPlayModel(PlayModel playModel) {
        this.playModel = playModel;
    }

    @Override
    public CreatorModel getCreatorModel() {
        return creatorModel;
    }

    public ComboBoxModel getRolesBoxModel() {
        return rolesBoxModel;
    }

    public DefaultListModel getCharactersListModel() {
        return charactersListModel;
    }

}
