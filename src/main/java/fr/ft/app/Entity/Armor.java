package fr.ft.app.Entity;

public class Armor extends Artifact {

  public void addPower(Creature creature) {
    creature.getDefense().increase(this.getPower());
  }

  public void removePower(Creature creature) {
    creature.getDefense().increase(-this.getPower());
  }

  public Armor(String p_name, int p_power) {
    super(p_name, p_power);
  };

}
