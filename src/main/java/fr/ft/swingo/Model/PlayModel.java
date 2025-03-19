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

/**
 *
 * @author Pril Wolf
 */
public class PlayModel {

    private Cell[][] cells = {
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(Cell.Type.HERO, null), new Cell()},
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},
        {new Cell(), new Cell(), new Cell(), new Cell(), new Cell()},};
    private int size = 5;
    private ChangeListener view;
    private Point heroCoordinate;

    public PlayModel() {
        heroCoordinate = new Point(2, 2);
    }

    private void stateChanged() {
        view.stateChanged(null);
    }

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

}
