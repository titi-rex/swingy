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
package fr.ft.swingo.View;

import fr.ft.swingo.Model.Cell;
import fr.ft.swingo.Model.PlayModel;
import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Pril Wolf
 */
public class PlayView extends JPanel implements ChangeListener {

    public static final int MAX_RENDER_SIZE = 3;
    private final URL HERO_TILE = getClass().getResource("/images/hero.png");
    private final URL EMPTY_TILE = getClass().getResource("/images/empty.png");
    private final URL ENNEMY_TILE = getClass().getResource("/images/ennemy.png");
    private PlayModel model;

    public PlayView(PlayModel model) {
        super();
        this.model = model;
        setLayout(new BorderLayout());
        this.model.setView(this);
        renderMap();
    }

    private void renderMap() {
        JPanel map = new JPanel();
        BoxLayout cLayout = new BoxLayout(map, BoxLayout.PAGE_AXIS);
        map.setLayout(cLayout);
        int start = 0;
        int end = model.getSize();
        for (int i = start; i < end; i++) {
            JPanel line = new JPanel();
            BoxLayout lLayout = new BoxLayout(line, BoxLayout.LINE_AXIS);
            line.setLayout(lLayout);
            for (int j = start; j < end; j++) {
                switch (model.getCellAt(i, j).getType()) {
                    case Cell.Type.EMPTY:
                        line.add(new JLabel(new ImageIcon(EMPTY_TILE)));
                        break;
                    case Cell.Type.HERO:
                        line.add(new JLabel(new ImageIcon(HERO_TILE)));
                        break;
                    case Cell.Type.ENNEMY:
                        line.add(new JLabel(new ImageIcon(ENNEMY_TILE)));
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            map.add(line);
        }
        add(map, BorderLayout.CENTER);
    }

    public PlayModel getModel() {
        return model;
    }

    public void setModel(PlayModel model) {
        this.model = model;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        renderMap();
    }

}
