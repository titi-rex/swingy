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

import fr.ft.swingy.Controller.Controller;
import fr.ft.swingy.Model.Model;
import fr.ft.swingy.Model.PlayModel;
import fr.ft.swingy.View.GUI.CreatorView;
import fr.ft.swingy.View.GUI.PlayView;
import fr.ft.swingy.View.GUI.MenuBarView;

/**
 *
 * @author Pril Wolf
 */
public interface View {

    void setModel(Model model);

    void setController(Controller controller);

    void start();

    void showPlayView();

    void showCreatorView();

    void requestClose();
    
    CreatorView getCreatorPanel();

    MenuBarView getMenuBarView();

    PlayView getPlayPanel();

}
