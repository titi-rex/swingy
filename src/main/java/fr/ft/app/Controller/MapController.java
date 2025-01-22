package fr.ft.app.Controller;

import java.util.Random;

import fr.ft.app.Model.Entity.Creature.Creature;
import fr.ft.app.Model.Entity.Creature.Role;
import fr.ft.app.Model.Map.Coordinates;
import fr.ft.app.Model.Map.MapModel;
import fr.ft.app.Model.Map.Direction;

public class MapController {

    private MapModel model;
    private Random rnd;

    public MapController(MapModel p_model, long seed) {
        rnd = new Random(seed);
        model = p_model;
        model.setSeed(seed);
    }

    public void populate() {
    model.setSize((model.getHero().getLevel() - 1) * 5 + 10 - (model.getHero().getLevel() % 2));
    Creature [][] layout = new Creature[model.getSize()][model.getSize()];

    for (int y = 0; y < layout.length; y++) {
      for (int x = 0; x < layout[y].length; x++) {
        layout[x][y] = createVilain();
      }
    }
    layout[model.getSize()/2][model.getSize()/2] = null;
    model.setLayout(layout);
    model.getHero().setCoordinates(new Coordinates(model.getSize()/2, model.getSize()/2));
  };


  private Creature createVilain() {
    int idx = rnd.nextInt(Role.values().length);
    if (idx < Role.LICH.ordinal())
      return null;
    else
      return Creature.invoke("Vilain", Role.get(idx));
  }

  public void moveHero(Direction dir) {
    Coordinates heroPos = model.getHero().getCoordinates();
    switch (dir) {
      default:
      case NORTH:
        heroPos.y--;
        break;
      case EAST:
        heroPos.x--;
        break;
      case SOUTH:
        heroPos.y++;
        break;
      case WEST:
        heroPos.x++;
        break;
    }

    Creature encounter = model.getLayout()[heroPos.x][heroPos.y];
    if (encounter != null) {
      model.getHero().setEncounter(encounter);
    } else {
      model.getHero().setEncounter(null);
    }
  }
}
