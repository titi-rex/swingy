package fr.ft.app;

public abstract class Entity {
  protected GameMap map;
  protected String name;
  protected Coordinates coordinate;

  public String getName() {
    return this.name;
  }


}
