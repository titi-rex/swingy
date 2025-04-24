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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI Creation scene for {@link fr.ft.swingy.View.View.ViewName.CREATOR}
 *
 * @author Pril Wolf
 */
public class CreatorScene extends JPanel {

    private final JButton createButton;
    private final JButton deleteButton;
    private final JButton playButton;
    private final JComboBox rolesBox;
    private final JList characterList;
    private final CreatureDataPanel infoStat;
    private JTextField nameField;

    /**
     * Create a CreatorScene who display a list of created hero, a creation box
     * for new hero, current selection stats and button for deleting or playing.
     * Must set model for characterList
     */
    public CreatorScene() {
        setLayout(new BorderLayout());

        nameField = new JTextField(12);
        rolesBox = new JComboBox();
        characterList = new JList();
        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        playButton = new JButton("Play");
        infoStat = new CreatureDataPanel();

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
    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JComboBox getRolesBox() {
        return rolesBox;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JList getCharacterList() {
        return characterList;
    }

    public CreatureDataPanel getInfoStat() {
        return infoStat;
    }

}
