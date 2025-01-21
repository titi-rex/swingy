package fr.ft.app.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter public abstract class Artifact extends Entity {

  protected int power;

  public abstract void addPower(Creature creature);

  public abstract void removePower(Creature creature);

  Artifact(String p_name, int p_power) {
    super(p_name);
    setPower(p_power);
  }

}
