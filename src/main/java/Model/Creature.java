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
package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 * @author Pril Wolf
 */
@Entity
public class Creature {

    @Id
    @NotNull
    @NotEmpty
    private String name;
//  protected Coordinates coordinates;

    private int level = 1;
    private int xp = 0;

    @Enumerated(EnumType.STRING)
    private Roles role;
    private int attack;
    private int defense;
    private int hitPoint;
//  private Artifact artifact;

    @Transient
    private static Validator validator;

    public Creature() {
    }

    public Creature(String name, Roles role, int attack, int defense, int hitPoint) {
        this.name = name;
        this.role = role;
        this.attack = attack;
        this.defense = defense;
        this.hitPoint = hitPoint;
    }

    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public boolean validate() {
        Set<ConstraintViolation<Creature>> constraintViolations
                = validator.validate(this);
        return constraintViolations.isEmpty();
    }

    public void gainXp(int value) {
        xp += value;
        checkLevel();
    }

    private void checkLevel() {
        int nextLevel = level * 1000 + (level - 1) * (level - 1) * 450;
        if (xp >= nextLevel) {
            levelUp();
            xp -= nextLevel;
            checkLevel();
        }
    }

    public void levelUp() {
        level += 1;
//        attack.growth();
//        defense.growth();
//        hitPoint.growth();

    }

    static public Creature invoke(String p_name, Roles p_role) {
        return new Creature(p_name, p_role,
                p_role.attack,
                p_role.defense,
                p_role.hitPoint);
    }

    @Override
    public String toString() {
        return "l" + level + " : " + name + " the " + role.name();
    }

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
}
