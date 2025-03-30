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
package fr.ft.swingy.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Pril Wolf
 */
@Entity
public class Creature {

    @Id
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Must contain only letters")
    private String name;

    private int level = 1;
    private int xp = 0;

    @Enumerated(EnumType.STRING)
    private Roles role;
    private int attack;
    private int defense;
    private int hitPoint;

    private Artifact artifact;

    public Creature() {
    }

    public Creature(String name, Roles role, int attack, int defense, int hitPoint) {
        this.name = name;
        this.role = role;
        this.attack = attack;
        this.defense = defense;
        this.hitPoint = hitPoint;
        this.artifact = null;
    }

    static public Creature invoke(String p_name, Roles p_role) {
        return new Creature(p_name, p_role,
                p_role.attack,
                p_role.defense,
                p_role.hitPoint);
    }

    public boolean gainXp(int value) {
        xp += value;
        return checkLevel();
    }

    private boolean checkLevel() {
        int nextLevel = level * 1000 + (level - 1) * (level - 1) * 450;
        boolean leveled = false;
        if (xp >= nextLevel) {
            leveled = true;
            levelUp();
            xp -= nextLevel;
            checkLevel();
        }
        return leveled;
    }

    public void levelUp() {
        level += 1;
        attack += role.aGrowth;
        defense += role.dGrowth;
        hitPoint += role.hGrowth;
    }

    public void levelUp(int n) {
        for (int i = 0; i < n; i++) {
            this.levelUp();
        }
    }

    public void attack(Creature opponent) {
        System.err.println(this + " attack " + opponent + " with " + this.getAttack() + " attack");
        opponent.takeDamage(this.getAttack());
    }

    public void takeDamage(int value) {
        int damage = Math.max(value - this.defense, 1);
        this.hitPoint -= damage;
        System.err.println(this + " take " + damage + " damage");
    }

    public boolean isAlive() {
        return getHitPoint() >= 0;
    }

    public void applyArtifact() {
        switch (artifact.getType()) {
            case Artifact.Types.ARMOR ->
                defense += artifact.getPower();
            case Artifact.Types.HELM ->
                hitPoint += artifact.getPower();
            case Artifact.Types.WEAPON ->
                attack += artifact.getPower();
        }
    }

    public void discardArtifact() {
        switch (artifact.getType()) {
            case Artifact.Types.ARMOR ->
                defense = Math.max(0, defense - artifact.getPower());
            case Artifact.Types.HELM ->
                hitPoint = Math.max(1, hitPoint - artifact.getPower());
            case Artifact.Types.WEAPON ->
                attack = Math.max(1, attack - artifact.getPower());
        }
    }

    @Override
    public String toString() {
        return name + " the " + role.toString();
    }

    // Getter and Setter        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        if (this.artifact != null) {
            discardArtifact();
        }
        this.artifact = artifact;
        applyArtifact();
    }
}
