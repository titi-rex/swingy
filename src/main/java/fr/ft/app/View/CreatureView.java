package fr.ft.app.View;

import fr.ft.app.Entity.Creature;

public class CreatureView {

    public static void show(Creature creature) {
        System.out.print(creature.getName() + " is a " + creature.getRole().name());
        if (creature.getArtifact() != null)
            System.out.println(" with a " + creature.getArtifact().getName());
        else
            System.out.println();
    }

}
