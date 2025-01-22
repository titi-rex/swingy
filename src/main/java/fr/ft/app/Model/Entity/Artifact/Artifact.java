package fr.ft.app.Model.Entity.Artifact;

import fr.ft.app.Model.Entity.Entity;
import fr.ft.app.Model.Entity.Creature.Creature;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public abstract class Artifact extends Entity {

  protected int power;

  public abstract void addPower(Creature creature);

  public abstract void removePower(Creature creature);

  public Artifact(String p_name, int p_power) {
    super(p_name);
    setPower(p_power);
  }

}
