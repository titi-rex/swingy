package fr.ft.app.View;

import fr.ft.app.Model.Entity.Creature.Creature;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class HeroView {
    private Creature model;

    public HeroView(Creature p_hero) {
        model = p_hero;
    }

    public String console() {
        if (model.getEncounter() != null)
            return HeroView.encounter(model.getEncounter());
        else
            return null;
    }

    public static String encounter(Creature creature){
        return creature.getName() + " the " + creature.getRole().name()
        + "\nThe monster is near.. And want to kill you." 
        + "\nStats : " 
        + creature.getAttack().getValue() 
        + "/" + creature.getDefense().getValue() 
        + "/" + creature.getHitPoint().getValue();
    }

    public static String stats(Creature creature) {
        return "Stats" 
        + "\n " + creature.getAttack().getValue() 
        + "\n " + creature.getDefense().getValue() 
        + "\n " + creature.getHitPoint().getValue();
    }

    public static void show(Creature creature) {
        System.out.print(creature.getName() + " is a " + creature.getRole().name());
        if (creature.getArtifact() != null)
            System.out.println(" with a " + creature.getArtifact().getName());
        else
            System.out.println();
    }

}
