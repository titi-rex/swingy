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

import fr.ft.swingy.Model.Entity.Artifact;
import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Entity.Roles;
import fr.ft.swingy.View.ViewElement;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pril Wolf
 */
public class CreatureView extends JPanel implements ViewElement {

    public static final int P_WIDTH = 130;
    public static final int P_HEIGHT = 300;

    private final JLabel nameValue;
    private final JLabel levelValue;
    private final JLabel roleValue;
    private final JLabel attackValue;
    private final JLabel defenseValue;
    private final JLabel hitPointValue;
    private final JLabel artifactValue;

    public CreatureView() {
        super();

        Dimension d = new Dimension(P_WIDTH, P_HEIGHT);
        setSize(d);
        setPreferredSize(d);
        this.setBorder(BorderFactory.createTitledBorder("Info"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);

        JLabel role = new JLabel("Class: ");
        JLabel level = new JLabel("Level: ");

        JLabel attack = new JLabel("Attack: ");
        JLabel defense = new JLabel("Defense: ");
        JLabel hitPoint = new JLabel("Hit Point: ");
        JLabel artifact = new JLabel("Artifact: ");

        nameValue = new JLabel("No Selection");
        levelValue = new JLabel("");
        roleValue = new JLabel("No Selection");
        attackValue = new JLabel("");
        defenseValue = new JLabel("");
        hitPointValue = new JLabel("");
        artifactValue = new JLabel("");

        add(statBox(nameValue, null));
        add(statBox(role, roleValue));
        add(statBox(level, levelValue));
        add(statBox(attack, attackValue));
        add(statBox(defense, defenseValue));
        add(statBox(hitPoint, hitPointValue));
        add(statBox(artifact, artifactValue));

    }

    public void update(Creature creature) {
        if (creature == null) {
            clear();
        } else {
            nameValue.setText(creature.getName());
            roleValue.setText(creature.getRole().toString());
            levelValue.setText(String.valueOf(creature.getLevel()));
            attackValue.setText(String.valueOf(creature.getAttack()));
            defenseValue.setText(String.valueOf(creature.getDefense()));
            hitPointValue.setText(String.valueOf(creature.getHitPoint()));

            Artifact artifact = creature.getArtifact();
            if (artifact != null) {
                artifactValue.setText(artifact.toString());
            } else {
                artifactValue.setText("");
            }
        }
    }

    public void update(Roles role) {
        if (role == null) {
            clear();
        } else {
            nameValue.setText("");
            roleValue.setText(role.toString());
            levelValue.setText("1");
            attackValue.setText(String.valueOf(role.attack));
            defenseValue.setText(String.valueOf(role.defense));
            hitPointValue.setText(String.valueOf(role.hitPoint));
            artifactValue.setText("");
        }
    }

    public void clear() {
        nameValue.setText("");
        roleValue.setText("");
        levelValue.setText("");
        attackValue.setText("");
        defenseValue.setText("");
        hitPointValue.setText("");
        artifactValue.setText("");
    }

    private JPanel statBox(JLabel name, JLabel value) {
        JPanel box = new JPanel(new FlowLayout(FlowLayout.CENTER));
        box.setBorder(BorderFactory.createLineBorder(Color.black));
        box.add(name);
        if (value != null) {
            box.add(value);
        }
        return box;
    }

    //    Getter and Setter
    public JLabel getNameValue() {
        return nameValue;
    }

    public JLabel getRoleValue() {
        return roleValue;
    }

    public JLabel getAttackValue() {
        return attackValue;
    }

    public JLabel getDefenseValue() {
        return defenseValue;
    }

    public JLabel getHitPointValue() {
        return hitPointValue;
    }

    public JLabel getLevelValue() {
        return levelValue;
    }

    public JLabel getArtifactValue() {
        return artifactValue;
    }

    @Override
    public void addActionListener(ActionListener l) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
