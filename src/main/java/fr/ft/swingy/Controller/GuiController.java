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
package fr.ft.swingy.Controller;

import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.GUI.GuiView;
import fr.ft.swingy.View.View;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Pril Wolf
 */
public class GuiController extends AbstractController {

    private final GuiView gView;

    public GuiController(View view, Model model) {
        super(view, model);
        gView = (GuiView) view;
    }

    @Override
    public void init() {
        gView.getSwitch().addActionListener(new MetaAction(MetaAction.Types.SWITCH));
        gView.getExit().addActionListener(new MetaAction(MetaAction.Types.EXIT));
        gView.getCreate().addActionListener(new CreatorAction(CreatorAction.Types.CREATE));
        gView.getDelete().addActionListener(new CreatorAction(CreatorAction.Types.DELETE));
        gView.getPlay().addActionListener(new CreatorAction(CreatorAction.Types.PLAY));
        gView.getCharacters().addListSelectionListener(new ListAction());
        gView.getRoles().addActionListener(new CreatorAction(CreatorAction.Types.SELECT_ROLE));
        gView.getNorth().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.NORTH));
        gView.getEast().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.EAST));
        gView.getSouth().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.SOUTH));
        gView.getWest().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.WEST));
        gView.getFight().addActionListener(new HeroAction(Model.Action.FIGHT, null));
        gView.getRun().addActionListener(new HeroAction(Model.Action.RUN, null));
        gView.getYes().addActionListener(new HeroAction(Model.Action.TAKE, null));
        gView.getNo().addActionListener(new HeroAction(Model.Action.DISCARD, null));
        if (model.isPlaying()) {
            gView.showView(View.ViewName.PLAY);
            gView.getPlayViewListener().stateChanged(null);
        } else {
            gView.showView(View.ViewName.CREATOR);
        }
    }

    private class ListAction implements ListSelectionListener {

        private final ActionListener listener;

        public ListAction() {
            listener = new CreatorAction(CreatorAction.Types.SELECT_HERO);
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            listener.actionPerformed(null);
        }

    }

}
