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

import java.awt.Point;
import java.util.Random;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Main Model for the swingy game
 *
 * @author Pril Wolf
 */
public final class PlayModel implements AutoCloseable {

    /**
     * enum representing possible movement for hero
     */
    public enum Direction {
        NORTH, EAST, WEST, SOUTH, CENTER
    };

    private final static String NEW_LINE = "\n";

    private Cell[][] cells;
    private int size;
    private int level;
    private boolean running;
    private ChangeListener view;
    private Point heroCoordinate;
    private final SessionFactory sessionFactory;
    private final Creature hero;
    private final Random randGen;
    private Artifact dropped;
    private Direction heroDirectionFrom;
    private Document gameLogs;

    /**
     * after creation you nust set the view via
     * {@link fr.ft.swingo.Model.PlayModel#setView}
     *
     * @param sessionFactory
     * @param newHero
     */
    public PlayModel(SessionFactory sessionFactory, Creature newHero) {
        this.sessionFactory = sessionFactory;
        this.running = false;
        this.randGen = new Random();
        this.heroDirectionFrom = Direction.CENTER;
        this.dropped = null;
        this.gameLogs = new PlainDocument();

        hero = sessionFactory.fromSession(session -> {
            return session
                    .get(Creature.class, newHero.getName());
        });

        if (hero == null) {
            throw new RuntimeException("fatal: can't get hero from DB");
        }
        addLog("Generating World...");
        this.level = hero.getLevel();
        generateWorld();
        running = true;
        addLog("Game Start!");
    }

    public void addLog(String msg) {
        try {
            gameLogs.insertString(gameLogs.getLength(), "> " + msg + NEW_LINE, null);
        } catch (BadLocationException e) {
            System.err.println("can't write game log: " + e.getLocalizedMessage());
        }
    }

    private void generateWorld() {
        size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (randGen.nextInt(100) < 33) {
                    cells[i][j] = new Cell(Cell.Type.ENNEMY, invokeEnnemyRandom());
                } else {
                    cells[i][j] = new Cell();
                }
            }
        }
        cells[size/2][size/2] = new Cell();
        heroCoordinate = new Point(size / 2, size / 2);
    }

    private Creature invokeEnnemyRandom() {
        Roles[] monsters = Roles.monster();
        int idx = randGen.nextInt(monsters.length);

        Creature creature = Creature.invoke("Monster", monsters[idx]);
        creature.levelUp(randGen.nextInt(level + 1));
        if (randGen.nextInt(100) < 33) {
            creature.setArtifact(createArtifactRandom(creature.getLevel()));
        }

        return creature;
    }

    private Artifact createArtifactRandom(int power) {
        int i = randGen.nextInt(100);
        Artifact artifact = new Artifact(Artifact.Types.HELM);

        if (i < 33) {
            artifact.setType(Artifact.Types.ARMOR);
        } else if (i > 66) {
            artifact.setType(Artifact.Types.WEAPON);
        }
        artifact.setPower(Artifact.BASE_POWER + randGen.nextInt(power));

        return artifact;
    }

    public void save() {
        if (running == true) {
            addLog("Game Saved!");
            sessionFactory.inTransaction(session -> {
                session.merge(hero);
            });
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
        if (isRunning() == true) {
            addLog("Move to " + dir.toString());
            switch (dir) {
                case Direction.NORTH -> {
                    heroDirectionFrom = Direction.SOUTH;
                    heroCoordinate.y--;
                    checkEnd();
                }
                case Direction.EAST -> {
                    heroDirectionFrom = Direction.WEST;
                    heroCoordinate.x--;
                    checkEnd();
                }
                case Direction.SOUTH -> {
                    heroDirectionFrom = Direction.NORTH;
                    heroCoordinate.y++;
                    checkEnd();
                }
                case Direction.WEST -> {
                    heroDirectionFrom = Direction.EAST;
                    heroCoordinate.x++;
                    checkEnd();
                }
                case Direction.CENTER -> {

                }
                default ->
                    throw new RuntimeException("Direction enum not fully handled");
            }
        } else {
            System.out.println("cant move, game not running");
        }
    }

    public void resolveRun() {
        addLog("Trying to run away..!");
        if (randGen.nextInt(100) < 50) {
            addLog("You don't have to face your destiny for now.");
            moveHero(heroDirectionFrom);
        } else {
            addLog("Can't escape this monster!");
            resolveFight();
        }
    }

    public void resolveFight() {
        Creature opponent = getCellAt(heroCoordinate.y, heroCoordinate.x).getCreature();
        addLog(opponent.getName() + " approach you with murderous intent");

        while (hero.isAlive() && opponent.isAlive()) {
            hero.attack(opponent);
            opponent.attack(hero);
        }
        if (hero.isAlive()) {
            addLog("You came back victorious from this fight, but at what cost?");
            int xp = 1000 * opponent.getLevel();
            addLog("You gain " + xp + " xp.");
            if (hero.gainXp(xp) == true) {
                addLog("You gain leveled up!");
            }
            drop(opponent);
            getCellAt(heroCoordinate.y, heroCoordinate.x).setType(Cell.Type.EMPTY);
        } else {
            addLog("Sadly your journey stop here. Maybe you do better the next time.");
        }
        checkEnd();
    }

    private void drop(Creature creature) {
        if (creature.getArtifact() != null && randGen.nextInt(100) < 40) {
            dropped = creature.getArtifact();
            addLog(creature + " dropped something.. It's a " + dropped.toString() + " to your taking!");
        } else {
            dropped = null;
        }
    }

    public void takeDropped() {
        hero.setArtifact(dropped);
        dropped = null;
        checkEnd();
    }

    public void discardDropped() {
        dropped = null;
        checkEnd();
    }

    private void checkEnd() {
        if (heroCoordinate.x == 0
                || heroCoordinate.y == 0
                || heroCoordinate.x == size - 1
                || heroCoordinate.y == size - 1
                || hero.getHitPoint() <= 0) {
            running = false;
            addLog("Congratulation! You reached the end of the maze!");
        }
        view.stateChanged(null);
    }

    @Override
    public void close() {
        if (running == true) {
            sessionFactory.close();
        }
    }

    //    Getter and Setter
    public Cell getCellAt(int y, int x) {
        return cells[y][x];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public ChangeListener getView() {
        return view;
    }

    public void setView(ChangeListener view) {
        this.view = view;
    }

    public Point getHeroCoordinate() {
        return heroCoordinate;
    }

    public void setHeroCoordinate(Point heroCoordinate) {
        this.heroCoordinate = heroCoordinate;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Creature getHero() {
        return hero;
    }

    public Artifact getDropped() {
        return dropped;
    }

    public Document getGameLogs() {
        return gameLogs;
    }

    public void setGameLogs(Document gameLogs) {
        this.gameLogs = gameLogs;
    }

}
