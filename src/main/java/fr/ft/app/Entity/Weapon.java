package fr.ft.app;

public class Weapon extends Artifact {

  public void addPower(Creature creature) {
    creature.increaseAttack(this.getPower());
  }

  public void removePower(Creature creature) {
    creature.increaseAttack(-this.getPower());
  }

}
