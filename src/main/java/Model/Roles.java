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

/*
 *
 * @author Pril Wolf
 */
public enum Roles {

    SENTINEL(10, 3, 10, 3, 10, 3),
    PRIESTESS(10, 3, 10, 3, 10, 3),
    VALKYRIE(10, 5, 10, 5, 10, 5),
    EMPRESS(15, 2, 20, 2, 20, 2),
    PRODIGE(5, 5, 5, 5, 5, 5),
    LICH(10, 3, 10, 3, 10, 3),
    BONEWALKER(10, 3, 10, 3, 10, 3),
    ARCANIST(10, 3, 10, 3, 10, 3),
    FELL_GOD(10, 3, 10, 3, 10, 3),
    CORRUPTED(10, 3, 10, 3, 10, 3);

    public final int attack;
    public final int aGrowth;
    public final int defense;
    public final int dGrowth;
    public final int hitPoint;
    public final int hGrowth;

    private Roles(int p_attack, int p_aGrowth, int p_defense, int p_dGrowth, int p_hitPoint, int p_hGrowth) {
        attack = p_attack;
        aGrowth = p_aGrowth;
        defense = p_defense;
        dGrowth = p_dGrowth;
        hitPoint = p_hitPoint;
        hGrowth = p_hGrowth;
    }

    public static Roles[] heroes() {
        Roles[] heroes = {SENTINEL, PRIESTESS, VALKYRIE, EMPRESS, PRODIGE};
        return heroes;
    }

    public static Roles[] monster() {
        Roles[] monster = {LICH, ARCANIST, FELL_GOD, CORRUPTED};
        return monster;
    }

}
