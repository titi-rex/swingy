package fr.ft.app;

public class Armor extends Artifact {

  public void addPower(Creature creature) {
    creature.increaseDefense(this.getPower());
  }

  public void removePower(Creature creature) {
    creature.increaseDefense(-this.getPower());
  }

}
