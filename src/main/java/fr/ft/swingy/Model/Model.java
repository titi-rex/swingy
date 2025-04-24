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
import fr.ft.swingy.View.View;
import java.awt.Point;
import javax.swing.ListModel;
import javax.swing.text.Document;

/**
 * Interface for Swingy Model
 *
 * @author Pril Wolf
 */
public interface Model {

    /**
     * enumeration representing possible movement for hero
     */
    public enum Direction {
        NORTH, EAST, WEST, SOUTH, CENTER
    }

    /**
     * enumeration representing possible action for hero
     */
    public enum Action {
        MOVE, FIGHT, RUN, TAKE, DISCARD
    }

    void setView(View view);

    void createNewHero(String name, Roles role) throws InvalidHeroException;

    void deleteHero(Creature hero);

    void createNewGame(Creature hero);

    void actionHero(Model.Action action, Model.Direction direction);

    void saveGame();

    boolean isFighting();

    boolean isPlaying();

    boolean hasDropped();

    boolean isEnd();

    ListModel getRolesModel();

    ListModel getCharactersListModel();

    int getSize();

    Cell getCellAt(int x, int y);

    Cell getCellAt(Point position);

    Creature getHero();

    Point getHeroCoordinate();

    Artifact getDropped();

    Document getGameLogs();

}
