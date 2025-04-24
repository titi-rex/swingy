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
import fr.ft.swingy.Model.Model.Direction;
import java.awt.Point;
import java.util.Random;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.hibernate.SessionFactory;

/**
 * Model used for game status (map, enemy, hero position)
 *
 * @author Pril Wolf
 */
public final class PlayModel {

    private final static String NEW_LINE = "\n";

    private Cell[][] cells;
    private int size;
    private int level;
    private boolean running;
    private ChangeListener view;
    private final SessionFactory sessionFactory;
    private final Random randGen;
    private Creature hero;
    private Point heroCoordinate;
    private Artifact dropped;
    private Direction heroDirectionFrom;
    private Document gameLogs;
    private boolean end;

    /**
     * after creation you must set the view via
     * {@link fr.ft.swingy.Model.PlayModel#setView}
     *
     * @param sessionFactory
     */
    public PlayModel(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.running = false;
        this.randGen = new Random();
        this.heroDirectionFrom = Direction.CENTER;
        this.dropped = null;
        this.gameLogs = new PlainDocument();
        this.end = false;
    }

    /**
     * Retrieve the selected Hero from database
     *
     * @param newHero
     */
    public void invokeHero(Creature newHero) {
        hero = sessionFactory.fromSession(session -> {
            return session
                    .get(Creature.class, newHero.getName());
        });

        if (hero == null) {
            throw new RuntimeException("fatal: can't get hero from DB");
        }
        this.level = hero.getLevel();
    }

    /**
     * Generate a new world base on the hero level
     */
    public void generateWorld() {
        addLog("Generating World...");
        if (hero == null) {
            throw new RuntimeException("cant generate world without a hero!");
        }
        size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i > 0 && i < size - 1
                        && j > 0 && j < size - 1
                        && randGen.nextInt(100) < 33) {
                    cells[i][j] = new Cell(Cell.Type.ENNEMY, invokeEnnemyRandom());
                } else {
                    cells[i][j] = new Cell();
                }
            }
        }
        cells[size / 2][size / 2] = new Cell();
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

    /**
     * Start the game
     */
    public void startGame() {
        running = true;
        addLog("Game Start!");
    }

    /**
     * Save hero status to database
     */
    public void save() {
        if (sessionFactory != null && hero != null) {
            addLog("Game Saved!");
            sessionFactory.inTransaction(session -> {
                session.merge(hero);
            });
        }
    }

    /**
     * Function to modify hero position. Check if fight or victory condition
     * happen after
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
                    heroCoordinate.x++;
                    checkEnd();
                }
                case Direction.SOUTH -> {
                    heroDirectionFrom = Direction.NORTH;
                    heroCoordinate.y++;
                    checkEnd();
                }
                case Direction.WEST -> {
                    heroDirectionFrom = Direction.EAST;
                    heroCoordinate.x--;
                    checkEnd();
                }
                case Direction.CENTER -> {

                }
                default ->
                    throw new UnsupportedOperationException(ERROR_ENUM_SWITCH);
            }
        }
    }

    /**
     * try to run away from enemy
     */
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

    /**
     * Start a fight with the creature present at hero position.
     */
    public void resolveFight() {
        Creature opponent = getCellAt(heroCoordinate).getCreature();
        if (opponent == null) {
            throw new RuntimeException("fatal: try to resolve fight but no opponnent");
        }
        addLog("You fight");
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
            getCellAt(heroCoordinate).setType(Cell.Type.EMPTY);
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

    /**
     * Take the dropped artifact, removing the old one
     */
    public void takeDropped() {
        hero.setArtifact(dropped);
        dropped = null;
        checkEnd();
    }

    /**
     * Don't take the dropped artifact
     */
    public void discardDropped() {
        dropped = null;
        checkEnd();
    }

    /**
     * call ChangeListener registered at
     * {@link fr.ft.swingy.Model.PlayModel#view}
     */
    private void checkEnd() {
        if (heroCoordinate.x == 0
                || heroCoordinate.y == 0
                || heroCoordinate.x == size - 1
                || heroCoordinate.y == size - 1
                || hero.getHitPoint() <= 0) {
            running = false;
            end = true;
            addLog("Congratulation! You reached the end of the maze!");
        } else if (getCellAt(heroCoordinate).getType() == Cell.Type.ENNEMY) {
            addLog(getCellAt(heroCoordinate).getCreature().getName()
                    + " approach you with murderous intent");
        }
        view.stateChanged(null);
    }

    /**
     * Add a line to the game logs, insert new line automatically
     *
     * @param msg
     */
    public void addLog(String msg) {
        try {
            gameLogs.insertString(gameLogs.getLength(), "> " + msg + NEW_LINE, null);
        } catch (BadLocationException e) {
            System.err.println("can't write game log: " + e.getLocalizedMessage());
        }
    }

//    Getter and Setter
    public Cell getCellAt(int y, int x) {
        return cells[y][x];
    }

    public Cell getCellAt(Point pos) {
        return cells[pos.y][pos.x];
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
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

    public boolean isRunning() {
        return running;
    }

    public boolean isEnd() {
        return end;
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

}
