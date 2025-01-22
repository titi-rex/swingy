package fr.ft.app.Model.Entity.Artifact;

import fr.ft.app.Model.Entity.Creature.Creature;

public class Helm extends Artifact {

  public void addPower(Creature creature) {
    creature.getHitPoint().increase(this.getPower());
  }

  public void removePower(Creature creature) {
    creature.getHitPoint().increase(-this.getPower());
  }

  public Helm(String p_name, int p_power) {
    super(p_name, p_power);
  };

}
