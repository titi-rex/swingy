package fr.ft.app.Controller;

import fr.ft.app.Model.Entity.Creature.Creature;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class HeroController {
    private Creature model;

    public boolean isEncounter() {
        return (model.getEncounter() == null);
    }
}
