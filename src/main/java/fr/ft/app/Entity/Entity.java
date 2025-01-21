package fr.ft.app.Entity;

import fr.ft.app.Coordinates;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public abstract class Entity {
  protected String name;
  protected Coordinates coordinates;
  protected String symbole;

  Entity(String p_name) {
    name = p_name;
  }
}
