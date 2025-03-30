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

import static fr.ft.swingy.App.ERROR_ENUM_SWITCH;
import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.InvalidHeroException;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Pril Wolf
 */
public class DefaultController implements Controller {

    private final View view;
    private final Model model;

    public DefaultController(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void init() {
        view.getSwitch().addActionListener(new MetaAction(MetaAction.Types.SWITCH));
        view.getExit().addActionListener(new MetaAction(MetaAction.Types.EXIT));
        view.getCreate().addActionListener(new CreatorAction(CreatorAction.Types.CREATE));
        view.getDelete().addActionListener(new CreatorAction(CreatorAction.Types.DELETE));
        view.getPlay().addActionListener(new CreatorAction(CreatorAction.Types.PLAY));
        view.getCharacters().addActionListener(new CreatorAction(CreatorAction.Types.SELECT_HERO));
        view.getRoles().addActionListener(new CreatorAction(CreatorAction.Types.SELECT_ROLE));
        view.getNorth().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.NORTH));
        view.getEast().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.EAST));
        view.getSouth().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.SOUTH));
        view.getWest().addActionListener(new HeroAction(Model.Action.MOVE, Model.Direction.WEST));
        view.getFight().addActionListener(new HeroAction(Model.Action.FIGHT, null));
        view.getRun().addActionListener(new HeroAction(Model.Action.RUN, null));
        view.getYes().addActionListener(new HeroAction(Model.Action.TAKE, null));
        view.getNo().addActionListener(new HeroAction(Model.Action.DISCARD, null));
    }

    // Meta Actions
    private class MetaAction implements ActionListener {

        public enum Types {
            HELP, SWITCH, EXIT
        };

        private interface ActionPtr {

            void action();
        }

        private final Types actionType;
        private final ActionPtr actionPtr;

        public MetaAction(Types actionType) {
            this.actionType = actionType;
            this.actionPtr = assignAction();
        }

        private ActionPtr assignAction() {
            switch (actionType) {
                case Types.HELP -> {
                    return () -> {

                    };
                }
                case Types.SWITCH -> {
                    return () -> {
                        System.out.println("switch to cli requested");
                    };
                }
                case Types.EXIT -> {
                    return () -> {
                        model.saveGame();
                        view.requestClose();
                    };
                }
                default ->
                    throw new UnsupportedOperationException(ERROR_ENUM_SWITCH);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionPtr.action();
        }
    }

    // Creation Actions
    private class CreatorAction implements ActionListener {

        public enum Types {
            CREATE, DELETE, PLAY, SELECT_ROLE, SELECT_HERO
        };

        private interface ActionPtr {

            void action();
        }

        private final Types actionType;
        private final ActionPtr actionPtr;

        public CreatorAction(Types actionType) {
            this.actionType = actionType;
            this.actionPtr = assignAction();
        }

        private ActionPtr assignAction() {
            switch (actionType) {
                case Types.CREATE -> {
                    return () -> {
                        String name = view.getNameSelected();
                        Roles role = (Roles) view.getRoleSelected();
                        try {
                            model.createNewHero(name, role);
                        } catch (InvalidHeroException ex) {
                            System.err.println(ex.getMessage());
                        }
                    };
                }
                case Types.DELETE -> {
                    return () -> {
                        if (view.getCharacters().isSelectionEmpty()) {
                            throw new UnsupportedOperationException("implmentd error user");
                        } else {
                            model.deleteHero((Creature) view.getCharacters().getSelectedValue());
                        }
                    };
                }
                case Types.PLAY -> {
                    return () -> {
                        if (view.getCharacters().isSelectionEmpty()) {
                            throw new UnsupportedOperationException("implmentd error user");
                        } else {
                            model.createNewGame((Creature) view.getCharacters().getSelectedValue());
                            view.showView(View.ViewName.PLAY);
                        }
                    };
                }
                case Types.SELECT_HERO -> {
                    return () -> {
                        view.getInfoCreature().update((Creature) view.getCharacters().getSelectedValue());
                    };
                }
                case Types.SELECT_ROLE -> {
                    return () -> {
                        view.getInfoCreature().update(view.getRoleSelected());
                    };
                }
                default ->
                    throw new UnsupportedOperationException(ERROR_ENUM_SWITCH);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionPtr.action();
        }
    }

    // In Game Actions
    private class HeroAction implements ActionListener {

        private interface ActionPtr {

            void action();
        }

        private final Model.Action actionType;
        private final Model.Direction direction;
        private final ActionPtr actionPtr;

        public HeroAction(Model.Action actionType, Model.Direction direction) {
            this.actionType = actionType;
            this.direction = direction;
            this.actionPtr = assignAction();
        }

        private ActionPtr assignAction() {
            switch (actionType) {
                case Model.Action.MOVE -> {
                    return () -> model.actionHero(actionType, direction);
                }
                case Model.Action.FIGHT, Model.Action.RUN, Model.Action.TAKE, Model.Action.DISCARD -> {
                    return () -> model.actionHero(actionType, null);
                }
                default ->
                    throw new UnsupportedOperationException(ERROR_ENUM_SWITCH);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionPtr.action();
        }
    }

}
