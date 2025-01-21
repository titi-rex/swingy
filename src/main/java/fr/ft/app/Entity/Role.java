package fr.ft.app.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Role {

    SENTINEL(10,3,10,3,10,3),
    PRIESTESS(10,3,10,3,10,3),
    VALKYRIE(10,5,10,5,10,5),
    EMPRESS(15,2,20,2,20,2),
    PRODIGE(5,5,5,5,5,5),
    LICH(10,3,10,3,10,3),
    BONEWALKER(10,3,10,3,10,3),
    ARCANIST(10,3,10,3,10,3),
    FELL_GOD(10,3,10,3,10,3),
    CORRUPTED(10,3,10,3,10,3);

    private static final List<Role> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    public final int attack;
    public final int aGrowth;
    public final int defense;
    public final int dGrowth;
    public final int hitPoint;
    public final int hGrowth;

    private Role(int p_attack, int p_aGrowth, int p_defense, int p_dGrowth, int p_hitPoint, int p_hGrowth) {
        attack = p_attack;
        aGrowth = p_aGrowth;
        defense = p_defense;
        dGrowth = p_dGrowth;
        hitPoint = p_hitPoint;
        hGrowth = p_hGrowth;
    }

    public static Role get(int i) {
        return VALUES.get(i);
    }

}
