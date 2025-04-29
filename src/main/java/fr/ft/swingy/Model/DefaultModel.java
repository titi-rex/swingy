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

import fr.ft.swingy.Model.Entity.Cell;
import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.Model.Entity.Artifact;
import static fr.ft.swingy.App.ERROR_ENUM_SWITCH;
import fr.ft.swingy.View.View;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.awt.Point;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListModel;
import javax.swing.text.Document;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * General Model Class for Swingy, use a  in {@link ft.ft.swingy.Model.CreatorModel} and a
 * {@link ft.ft.swingy.Model.PlayModel} internally
 *
 * @author Pril Wolf
 */
public class DefaultModel implements Model, AutoCloseable {

    private View view;

    private final SessionFactory sessionFactory;
    private static Validator validator;

    private final ComboBoxModel rolesBoxModel;

    private final CreatorModel creatorModel;
    private PlayModel playModel;

    /**
     *
     */
    public DefaultModel() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        sessionFactory = new Configuration().configure().buildSessionFactory();
        creatorModel = new CreatorModel(sessionFactory, validator);
        playModel = new PlayModel(sessionFactory);
        rolesBoxModel = new DefaultComboBoxModel(Roles.heroes());
    }

    /**
     * Create a new Hero and store it inside the database
     *
     * @param name
     * @param role
     * @throws InvalidHeroException if hero not valid
     */
    @Override
    public void createNewHero(String name, Roles role) throws InvalidHeroException {
        creatorModel.create(name, role);

    }

    /**
     *
     * @param hero
     */
    @Override
    public void deleteHero(Creature hero) {
        try {
            creatorModel.delete(hero);
        } catch (Exception e) {
            view.error("Error: " + e.getMessage());
        }
    }

    /**
     * Create a new map and start a new game with
     *
     * @param hero Hero to play with
     */
    @Override
    public void createNewGame(Creature hero) {
        playModel.invokeHero(hero);
        playModel.generateWorld();
        playModel.startGame();
        playModel.getView().stateChanged(null);
    }

    /**
     * Function to change Hero coordinates or to resolve fight/artifact actions
     *
     * @param action
     * @param direction
     */
    @Override
    public void actionHero(Model.Action action, Model.Direction direction) {
        switch (action) {
            case Model.Action.MOVE ->
                playModel.moveHero(direction);
            case Model.Action.FIGHT ->
                playModel.resolveFight();
            case Model.Action.RUN ->
                playModel.resolveRun();
            case Model.Action.TAKE ->
                playModel.takeDropped();
            case Model.Action.DISCARD ->
                playModel.discardDropped();
            default ->
                throw new UnsupportedOperationException(ERROR_ENUM_SWITCH);
        }
    }

    /**
     * Save Hero status to database
     */
    @Override
    public void saveGame() {
        if (playModel != null) {
            playModel.save();
        }
    }

    /**
     * Close the sessionFactory
     */
    @Override
    public void close() {
        sessionFactory.close();
        playModel = null;
    }

    // Getter/Setter
    @Override
    public boolean isFighting() {
        return playModel.getCellAt(playModel.getHeroCoordinate()).getType() == Cell.Type.ENNEMY;
    }

    @Override
    public boolean isPlaying() {
        return playModel.isRunning();
    }

    @Override
    public boolean hasDropped() {
        return playModel.getDropped() != null;
    }

    @Override
    public boolean isEnd() {
        return playModel.isEnd();
    }

    @Override
    public ListModel getRolesModel() {
        return rolesBoxModel;
    }

    @Override
    public ListModel getCharactersListModel() {
        return creatorModel.getCharacters();
    }

    @Override
    public void setView(View view) {
        this.view = view;
        playModel.setView(view.getPlaySceneListener());
    }

    @Override
    public int getSize() {
        return playModel.getSize();
    }

    @Override
    public Cell getCellAt(int y, int x) {
        return playModel.getCellAt(y, x);
    }

    @Override
    public Cell getCellAt(Point position) {
        return playModel.getCellAt(position);
    }

    @Override
    public Creature getHero() {
        return playModel.getHero();
    }

    @Override
    public Point getHeroCoordinate() {
        return playModel.getHeroCoordinate();
    }

    @Override
    public Artifact getDropped() {
        return playModel.getDropped();
    }

    @Override
    public Document getGameLogs() {
        return playModel.getGameLogs();
    }

}
