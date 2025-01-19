package fr.ft.app;

public abstract class Hero {

  private String name;
  private int level;
  private int xp;
  private int attack;
  private int defense;
  private int hitPoint;
  private Artifact artifact;
  private GameMap map;

  Hero(String p_name, GameMap p_map) {
    name = p_name;
    level = 1;
    map = p_map;
  }

  public abstract void fight(Vilain vilain);

  public void run(Vilain vilain) {

  }

  public void move(GameMap.Direction direction) {

  }

}
