package fr.ft.app;

public class Helm extends Artifact {

  public void addPower(Creature creature) {
    creature.increaseHitPoint(this.getPower());
  }

  public void removePower(Creature creature) {
    creature.increaseHitPoint(-this.getPower());
  }

}
