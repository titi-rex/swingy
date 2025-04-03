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

import fr.ft.swingy.App;
import static fr.ft.swingy.App.ERROR_ENUM_SWITCH;
import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.Model.InvalidHeroException;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Pril Wolf
 */
public abstract class AbstractController implements Controller {

    protected final View view;
    protected final Model model;

    public AbstractController(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public abstract void init();

    // Meta Actions
    protected class MetaAction implements ActionListener {

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
                        view.requestClose();
                        App.switchView();
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
    protected class CreatorAction implements ActionListener {

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
                            System.err.println("Error: " + ex.getMessage());
                        }
                    };
                }
                case Types.DELETE -> {
                    return () -> {
                        if (view.isHeroSelected()) {
                            model.deleteHero((Creature) view.getHeroSelected());
                        } else {
                            throw new UnsupportedOperationException("implmentd error user");
                        }
                    };
                }
                case Types.PLAY -> {
                    return () -> {
                        if (view.isHeroSelected()) {
                            model.createNewGame((Creature) view.getHeroSelected());
                            view.showView(View.ViewName.PLAY);
                        } else {
                            throw new UnsupportedOperationException("implmentd error user");
                        }
                    };
                }
                case Types.SELECT_HERO -> {
                    return () -> {
                        view.updateInfoCreature((Creature) view.getHeroSelected());
                    };
                }
                case Types.SELECT_ROLE -> {
                    return () -> {
                        view.updateInfoCreature(view.getRoleSelected());
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
    protected class HeroAction implements ActionListener {

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
