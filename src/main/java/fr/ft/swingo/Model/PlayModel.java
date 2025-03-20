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
package fr.ft.swingo.Model;

import java.awt.Point;
import javax.swing.event.ChangeListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Main Model for the swingy game
 *
 * @author Pril Wolf
 */
public class PlayModel implements AutoCloseable {

    /**
     * enum representing possible movement for hero
     */
    public enum Direction {
        NORTH, EAST, WEST, SOUTH
    };

    private Cell[][] cells = {
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(Cell.Type.HERO, null), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},};
    private int size = 5;
    private boolean running;
    private ChangeListener view;
    private Point heroCoordinate;
    SessionFactory sessionFactory;
    Session gameSession;
    Creature hero;

    /**
     * after creation you nust set the view via
     * {@link fr.ft.swingo.Model.PlayModel#setView}
     */
    public PlayModel(Creature newHero) {
        this.running = false;

        sessionFactory = new Configuration().configure().buildSessionFactory();
        gameSession = sessionFactory.openSession();
        hero = gameSession.get(Creature.class, newHero.getName());
        if (hero == null) {
            throw new AssertionError("not handled");
        }
        generateWorld();
        running = true;
    }

    private void generateWorld() {
        size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        System.out.println("size: " + size);
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
        heroCoordinate = new Point(size / 2, size / 2);
    }

    public void save() {
        if (running == true) {
            gameSession.flush();
        }
    }

    @Override
    public void close() {
        if (running == true) {
            gameSession.close();
            sessionFactory.close();
        }
    }

    /**
     * function to moddify hero position check if victory condition happen after
     * move and call ChangeListener registered at
     * {@link fr.ft.swingo.Model.PlayModel#view}
     *
     * @param dir where the hero should move
     */
    public void moveHero(Direction dir) {
        System.out.println("hero pos: " + heroCoordinate);
        if (isRunning() == true) {
            System.out.println("move to: " + dir.name());

            switch (dir) {
                case Direction.NORTH -> {
                    heroCoordinate.y--;
                    checkEnd();
                }
                case Direction.EAST -> {
                    heroCoordinate.x--;
                    checkEnd();
                }
                case Direction.SOUTH -> {
                    heroCoordinate.y++;
                    checkEnd();
                }
                case Direction.WEST -> {
                    heroCoordinate.x++;
                    checkEnd();
                }
                default ->
                    throw new AssertionError();
            }
        } else {
            System.out.println("cant move, game not running");
        }
    }

    private void checkEnd() {
        System.out.println("hero pos: " + heroCoordinate);
        if (heroCoordinate.x == 0
                || heroCoordinate.y == 0
                || heroCoordinate.x == size - 1
                || heroCoordinate.y == size - 1) {
            endGame();
        }
        stateChanged();
    }

    private void endGame() {
        running = false;
    }

    private void stateChanged() {
        view.stateChanged(null);
    }

    /**
     * return cell at position y (vertical) x (horizontal)
     *
     * @param y
     * @param x
     * @return cell
     * @throws IndexOutOfBoundsException
     */
    public Cell getCellAt(int y, int x) {
        return cells[y][x];
    }

    /**
     *
     * @return size of the map
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return array of cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     *
     * @param cells
     */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     *
     * @return
     */
    public ChangeListener getView() {
        return view;
    }

    /**
     *
     * @param view
     */
    public void setView(ChangeListener view) {
        this.view = view;
    }

    /**
     *
     * @return
     */
    public Point getHeroCoordinate() {
        return heroCoordinate;
    }

    /**
     *
     * @param heroCoordinate
     */
    public void setHeroCoordinate(Point heroCoordinate) {
        this.heroCoordinate = heroCoordinate;
    }

    /**
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     *
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

}
