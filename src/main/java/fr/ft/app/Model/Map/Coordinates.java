package fr.ft.app.Model.Map;

public class Coordinates {
  public int x;
  public int y;


  public void increase(int value) {
    y += value;
    x += value;
  }

  public Coordinates(int p_x, int p_y) {
    x = p_x;
    y = p_y;
  }
}
