package fr.ft.app.Entity;

public class Weapon extends Artifact {

  public void addPower(Creature creature) {
    creature.getAttack().increase(this.getPower());
  }

  public void removePower(Creature creature) {
    creature.getAttack().increase(-this.getPower());
  }

  public Weapon(String p_name, int p_power) {
    super(p_name, p_power);
  };

}
