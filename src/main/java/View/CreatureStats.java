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
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pril Wolf
 */
public class CreatureStats extends JPanel {

    public static final int P_WIDTH = 130;
    public static final int P_HEIGHT = 300;

    private JLabel roleLabel;
    private JLabel attackValue;
    private JLabel defenseValue;
    private JLabel hitPointValue;

    public CreatureStats() {
        super();
        
        Dimension d = new Dimension(P_WIDTH, P_HEIGHT);
        setSize(d);
        setPreferredSize(d);
        this.setBorder(BorderFactory.createTitledBorder("Info"));
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        
        JLabel attack = new JLabel("Attack: ");
        JLabel defense = new JLabel("Defense: ");
        JLabel hitPoint = new JLabel("Hit Point: ");
        roleLabel = new JLabel("No Selection");
        attackValue = new JLabel("");
        defenseValue = new JLabel("");
        hitPointValue = new JLabel("");

        add(statBox(roleLabel, null));
        add(statBox(attack, attackValue));
        add(statBox(defense, defenseValue));
        add(statBox(hitPoint, hitPointValue));
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

    public JLabel getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(JLabel roleLabel) {
        this.roleLabel = roleLabel;
    }

    public JLabel getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(JLabel attackValue) {
        this.attackValue = attackValue;
    }

    public JLabel getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(JLabel defenseValue) {
        this.defenseValue = defenseValue;
    }

    public JLabel getHitPointValue() {
        return hitPointValue;
    }

    public void setHitPointValue(JLabel hitPointValue) {
        this.hitPointValue = hitPointValue;
    }
}
