package fr.ft.app.Controller;

import fr.ft.app.Model.Entity.Creature.Creature;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class HeroController {
    private Creature model;

    public HeroController(Creature p_hero) {
        model = p_hero;
    }

    public boolean hasEncounter() {
        return (model.getEncounter() != null);
    }

    public boolean fight() {
        Creature opponent = model.getEncounter();

        int damageInflicted = model.getAttack().getValue() - opponent.getDefense().getValue();
        int damageTaken = opponent.getAttack().getValue() - model.getDefense().getValue();

        model.getHitPoint().increase(-damageTaken);
        opponent.getHitPoint().increase(-damageInflicted);


        // notify view to fight result
        return (damageInflicted > damageTaken);
    }

    public boolean runAway() {
        return true;
    }

    public void endFight() {

    }


}
// notify view of
/*
run 
fight
xp gain
artifact

 */