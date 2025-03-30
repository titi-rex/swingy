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

import fr.ft.swingy.View.GUI.Component.SwingyComboBox;
import fr.ft.swingy.View.GUI.Component.SwingyList;
import fr.ft.swingy.View.GUI.Component.SwingyButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Pril Wolf
 */
public class CreatorView extends JPanel {

    private final SwingyButton createButton;
    private final SwingyButton deleteButton;
    private final SwingyButton playButton;
    private final SwingyComboBox rolesBox;
    private JTextField nameField;
    private final SwingyList characterList;
    private final CreatureView infoStat;

    /**
     * must set model for characterList
     */
    public CreatorView() {
        setLayout(new BorderLayout());

        nameField = new JTextField(12);
        rolesBox = new SwingyComboBox();
        characterList = new SwingyList();
        createButton = new SwingyButton("Create");
        deleteButton = new SwingyButton("Delete");
        playButton = new SwingyButton("Play");
        infoStat = new CreatureView();

        add(creationForm(), BorderLayout.PAGE_START);
        add(characterList, BorderLayout.CENTER);
        add(infoStat, BorderLayout.LINE_END);
        add(buttonBox(), BorderLayout.PAGE_END);
    }

    private JPanel buttonBox() {
        JPanel buttonBox = new JPanel(new FlowLayout());
        buttonBox.add(deleteButton);
        buttonBox.add(playButton);
        return buttonBox;
    }

    private JPanel creationForm() {
        JLabel nameLabel = new JLabel("Name: ");
        JLabel classLabel = new JLabel("Class");

        JPanel creationForm = new JPanel();
        GroupLayout formLayout = new GroupLayout(creationForm);
        formLayout.setAutoCreateGaps(true);
        formLayout.setAutoCreateContainerGaps(true);
        formLayout.setHorizontalGroup(formLayout.createSequentialGroup()
                .addComponent(nameLabel)
                .addComponent(nameField)
                .addComponent(classLabel)
                .addComponent(rolesBox)
                .addComponent(createButton));
        return creationForm;
    }

    // Getter and Setter
    public SwingyButton getCreateButton() {
        return createButton;
    }

    public SwingyButton getDeleteButton() {
        return deleteButton;
    }

    public SwingyButton getPlayButton() {
        return playButton;
    }

    public SwingyComboBox getRolesBox() {
        return rolesBox;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public SwingyList getCharacterList() {
        return characterList;
    }

    public CreatureView getInfoStat() {
        return infoStat;
    }

}
