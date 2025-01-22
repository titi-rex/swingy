package fr.ft.app.Model.Entity.Creature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor public class Statistic {
    protected int value;
    protected float growthRate;

    public void growth() {
        value += growthRate;
    }

    public void increase(int p_value) {
        setValue(value + p_value);
    } 

    public Statistic clone() {
        return new Statistic(value, growthRate);
    }

}
