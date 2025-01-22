package fr.ft.app.Model.Entity;

import fr.ft.app.Model.Map.Coordinates;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public abstract class Entity {
  protected String name;
  protected Coordinates coordinates;
  protected String symbole;

  public Entity(String p_name) {
    name = p_name;
  }
}
