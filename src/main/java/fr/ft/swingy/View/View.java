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
package fr.ft.swingy.View;

import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Entity.Roles;
import javax.swing.event.ChangeListener;

/**
 * Interface for Swingy View
 *
 * @author Pril Wolf
 */
public interface View {

    /**
     * Type of scene view has to present
     */
    enum SceneName {
        CREATOR, PLAY
    };

    /**
     * Start the view loop
     */
    void start();

    /**
     * Switch to specific view type
     *
     * @param viewName
     */
    void showView(SceneName viewName);

    /**
     * Display an error message
     *
     * @param message
     */
    void error(String message);

    /**
     * Ask the view to close
     */
    void requestClose();

    /**
     * Return a ChangeListner for the in
     * {@link ft.ft.swingy.View.View.ViewName.PLAY} view, used by model to
     * notify update
     *
     * @return
     */
    ChangeListener getPlaySceneListener();

    /**
     * Update a creature info display
     *
     * @param creature
     */
    void updateInfoCreature(Creature creature);

    /**
     * Update a creature info display
     *
     * @param role
     */
    void updateInfoCreature(Roles role);

    /**
     * Return the name of the selected hero in
     * {@link ft.ft.swingy.View.View.ViewName.CREATOR}
     *
     * @return
     */
    String getNameSelected();

    /**
     * Return the role selected in
     * {@link ft.ft.swingy.View.View.ViewName.CREATOR}
     *
     * @return
     */
    Roles getRoleSelected();

    /**
     * Return the selected hero in
     * {@link ft.ft.swingy.View.View.ViewName.CREATOR}
     *
     * @return
     */
    Creature getHeroSelected();

    /**
     * Indicate if a hero is selected in
     * {@link ft.ft.swingy.View.View.ViewName.CREATOR}
     *
     * @return
     */
    boolean isHeroSelected();
}
