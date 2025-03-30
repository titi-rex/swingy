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

import fr.ft.swingy.Model.Cell;
import fr.ft.swingy.Model.PlayModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Pril Wolf
 */
public class PlayView extends JPanel implements ChangeListener {

    public static final int MIN_RENDER_SIZE = 3;
    public static final int MAX_RENDER_SIZE = 15;
    private static final int RENDER_BOUND = MAX_RENDER_SIZE / 2;

    private final URL HERO_TILE = getClass().getResource("/images/hero.png");
    private final URL EMPTY_TILE = getClass().getResource("/images/empty.png");
    private final URL ENNEMY_TILE = getClass().getResource("/images/ennemy.png");

    private PlayModel model;
    private JPanel map;
    private final JTextArea logArea;

    private final CreatureView heroStats;
    private final CreatureView ennemyStats;
    private final CommandBar commandBar;

    /**
     * MUST set model after constructor
     */
    public PlayView() {
        super();
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout());
        JLabel logLabel = new JLabel("Game Logs:");
        logArea = new JTextArea(6, 100);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(Color.white);
        logArea.setSize(200, 50);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        header.add(logLabel);
        header.add(scrollPane);

        heroStats = new CreatureView();
        ennemyStats = new CreatureView();
        commandBar = new CommandBar();

        add(header, BorderLayout.PAGE_START);
        add(heroStats, BorderLayout.LINE_START);
        add(ennemyStats, BorderLayout.LINE_END);
        add(commandBar, BorderLayout.PAGE_END);
    }

    /**
     *
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        logArea.setCaretPosition(logArea.getDocument().getLength());
        clearMap();
        renderMap();
        renderCommandBar();
        renderHeroStats();
        renderEnnemyStats();
        this.revalidate();
    }

    private Point getRenderPosition(int pos, int size) {
        int start = Integer.max(pos - RENDER_BOUND, 0);
        int end = Integer.min(pos + RENDER_BOUND + 1, size);

        if (pos - RENDER_BOUND < 0) {
//            end += Math.abs(pos - RENDER_BOUND);
            end = Math.min(size, end + Math.abs(pos - RENDER_BOUND));
        } else if (pos + RENDER_BOUND > size) {
            start = Math.max(0, start + (size - (pos + RENDER_BOUND)));

        }
        return new Point(start, end);
    }

    public void renderMap() {
        Point yRenderInterval = getRenderPosition(
                model.getHeroCoordinate().y,
                model.getSize()
        );
        System.out.println("render interval Y: " + yRenderInterval);

        Point xRenderInterval = getRenderPosition(
                model.getHeroCoordinate().x,
                model.getSize()
        );
        System.out.println("render interval X: " + xRenderInterval);

        map = renderLoop(yRenderInterval, xRenderInterval);
        add(map, BorderLayout.CENTER);
    }

    private JPanel renderLoop(Point yRenderInterval, Point xRenderInterval) {
        JPanel mapPanel = new JPanel();
        BoxLayout cLayout = new BoxLayout(mapPanel, BoxLayout.PAGE_AXIS);
        mapPanel.setLayout(cLayout);

        for (int i = yRenderInterval.x; i < yRenderInterval.y; i++) {
            JPanel line = new JPanel();
            BoxLayout lLayout = new BoxLayout(line, BoxLayout.LINE_AXIS);
            line.setLayout(lLayout);
            for (int j = xRenderInterval.x; j < xRenderInterval.y; j++) {
                if (model.getHeroCoordinate().equals(new Point(j, i))) {
                    line.add(new JLabel(new ImageIcon(HERO_TILE)));
                    System.err.println("hero found at: " + new Point(j, i));
                } else {
                    switch (model.getCellAt(i, j).getType()) {
                        default:
                        case Cell.Type.EMPTY:
                            line.add(new JLabel(new ImageIcon(EMPTY_TILE)));
                            break;
                        case Cell.Type.ENNEMY:
                            line.add(new JLabel(new ImageIcon(ENNEMY_TILE)));
                            break;
                    }
                }
            }
            mapPanel.add(line);
        }
        return mapPanel;
    }

    public void clearMap() {
        if (map != null) {
            remove(map);
        }
    }

    public void renderCommandBar() {
        Point heroPos = model.getHeroCoordinate();
        if (model.getCellAt(heroPos.y, heroPos.x).getType() == Cell.Type.ENNEMY) {
            commandBar.showFightCommand();
        } else if (model.getDropped() == null) {
            commandBar.showMoveCommand();
        } else {
            commandBar.showArtifactCommand();
        }
    }

    public void renderHeroStats() {
        heroStats.update(model.getHero());
    }

    public void renderEnnemyStats() {
        Point hero = model.getHeroCoordinate();
        if (model.getCellAt(hero.y, hero.x).getType() == Cell.Type.ENNEMY) {
            ennemyStats.update(model.getCellAt(hero.y, hero.x).getCreature());
        } else {
            ennemyStats.clear();
        }
    }


    /**
     * class holding possible command and button for user
     */
    public class CommandBar extends JPanel {

        public static final String MOVE_COMMAND = "move";
        public static final String FIGHT_COMMAND = "fight";
        public static final String ARTIFACT_COMMAND = "artifact";

        private final JButton northButton;
        private final JButton eastButton;
        private final JButton southButton;
        private final JButton westButton;
        private final JButton fightButton;
        private final JButton runButton;
        private final JButton yesButton;
        private final JButton noButton;

        private final CardLayout layout;

        /**
         *
         */
        public CommandBar() {
            super();
            layout = new CardLayout();
            setLayout(layout);

            JPanel movePanel = new JPanel(new FlowLayout());
            northButton = new JButton("North");
            eastButton = new JButton("East");
            southButton = new JButton("South");
            westButton = new JButton("West");
            movePanel.add(northButton);
            movePanel.add(eastButton);
            movePanel.add(southButton);
            movePanel.add(westButton);
            this.add(movePanel, MOVE_COMMAND);

            JPanel fightPanel = new JPanel(new FlowLayout());
            fightButton = new JButton("Fight");
            runButton = new JButton("Run");
            fightPanel.add(fightButton);
            fightPanel.add(runButton);
            this.add(fightPanel, FIGHT_COMMAND);

            JPanel artifactPanel = new JPanel(new FlowLayout());
            yesButton = new JButton("Take Artifact");
            noButton = new JButton("Leave");
            artifactPanel.add(yesButton);
            artifactPanel.add(noButton);
            this.add(artifactPanel, ARTIFACT_COMMAND);
        }

        public void showMoveCommand() {
            layout.show(this, MOVE_COMMAND);
        }

        public void showFightCommand() {
            layout.show(this, FIGHT_COMMAND);
        }

        public void showArtifactCommand() {
            layout.show(this, ARTIFACT_COMMAND);
        }

        //    Getter and Setter
        public JButton getNorthButton() {
            return northButton;
        }

        public JButton getEastButton() {
            return eastButton;
        }

        public JButton getWestButton() {
            return westButton;
        }

        public JButton getSouthButton() {
            return southButton;
        }

        public JButton getFightButton() {
            return fightButton;
        }

        public JButton getRunButton() {
            return runButton;
        }

        public JButton getYesButton() {
            return yesButton;
        }

        public JButton getNoButton() {
            return noButton;
        }

    }

    //    Getter and Setter
    public CommandBar getCommandBar() {
        return commandBar;
    }

    public PlayModel getModel() {
        return model;
    }

    public void setModel(PlayModel model) {
        this.model = model;
        logArea.setDocument(model.getGameLogs());

    }

    public JPanel getMap() {
        return map;
    }

    public void setMap(JPanel map) {
        this.map = map;
    }

    public CreatureView getHeroStats() {
        return heroStats;
    }

    public CreatureView getEnnemyStats() {
        return ennemyStats;
    }
}
