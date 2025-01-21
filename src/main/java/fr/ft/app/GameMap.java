package fr.ft.app;

import java.util.Random;

import fr.ft.app.Entity.Creature;
import fr.ft.app.Entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class GameMap implements IGameMap {

  private int size = minSize;
  private Creature hero;
  private Random rnd;
  private long seed;
  private Creature[][] layout;

  public GameMap(long seed) {
    rnd = new Random(seed);
  }

  public void populate(Creature p_hero) {
    hero = p_hero;
    size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
    layout = new Creature[size][size];

    for (int i = 0; i < layout.length; i++) {
      for (int j = 0; j < layout[i].length; j++) {
        int idx = rnd.nextInt(Role.values().length);
        if (idx < Role.LICH.ordinal())
          layout[i][j] = null;
        else
          layout[i][j] = Creature.invoke("Vilain", Role.get(idx));
      }
    }
    layout[size/2][size/2] = null;
  };
  
  public void see() {
    for (Creature[] entities : layout) {
      for (Creature entity : entities) {
        if (entity == null)
          System.out.print(String.format("%-12s ", " empty"));
        else
          System.out.print(String.format("%-12s ", entity.getName()));
      }
      System.out.println();
    }
  } 

  public void moveHero(Direction dir) {
    switch (dir) {
      default:
      case NORTH:
        hero.getCoordinates().y--;
        break;
      case EAST:
      hero.getCoordinates().x--;
        break;
        case SOUTH:
        hero.getCoordinates().y++;
        break;
      case WEST:
        hero.getCoordinates().x++;
        break;
    }
  }

  public void addEntityAt(Creature creature, Coordinates position) {
    layout[position.x][position.y] = creature;
  }

  public Creature[][] getViewData() {

      return layout;
  };

}
