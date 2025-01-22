package fr.ft.app.Model.Map;

import fr.ft.app.Model.Entity.Creature.Creature;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class MapModel {

  private long seed;
  private int size;
  private Creature hero;
  private Creature[][] layout;


}
