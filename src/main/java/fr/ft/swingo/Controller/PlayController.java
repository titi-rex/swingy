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
package fr.ft.swingo.Controller;

import fr.ft.swingo.Model.PlayModel;
import fr.ft.swingo.View.GuiView;
import fr.ft.swingo.View.PlayView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Pril Wolf
 */
public class PlayController {

    private final GuiView window;
    private final PlayView view;
    private PlayModel model;

    public PlayController(GuiView window, PlayView view) {
        this.window = window;
        this.view = view;

    }

    public void init() {
        view.getCommandBar().getNorthButton().addActionListener(new NorthAction());
        view.getCommandBar().getEastButton().addActionListener(new EastAction());
        view.getCommandBar().getSouthButton().addActionListener(new SouthAction());
        view.getCommandBar().getWestButton().addActionListener(new WestAction());
        view.getCommandBar().getFightButton().addActionListener(new FightAction());
        view.getCommandBar().getRunButton().addActionListener(new RunAction());

    }

    private class NorthAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.moveHero(PlayModel.Direction.NORTH);
        }
    }

    private class EastAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.moveHero(PlayModel.Direction.EAST);
        }
    }

    private class SouthAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.moveHero(PlayModel.Direction.SOUTH);
        }
    }

    private class WestAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.moveHero(PlayModel.Direction.WEST);
        }
    }

    private class FightAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.resolveFight();

        }
    }

    private class RunAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.resolveRun();
        }
    }

    public PlayModel getModel() {
        return model;
    }

    public void setModel(PlayModel model) {
        this.model = model;
    }

}
