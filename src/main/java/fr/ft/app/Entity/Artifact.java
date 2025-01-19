package fr.ft.app;

public abstract class Artifact extends Entity {

  private int power;

  public abstract void addPower(Creature creature);

  public abstract void removePower(Creature creature);

  public int getPower() {
    return this.power;
  }

  public void move(GameMap.Direction dir, int count) {

  }
}
