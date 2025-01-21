package fr.ft.app;

import fr.ft.app.Entity.Artifact;
import fr.ft.app.Entity.Weapon;
import fr.ft.app.View.CreatureView;
import fr.ft.app.Entity.Creature;
import fr.ft.app.Entity.Role;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    Creature c2 = Creature.invoke("mimou", Role.EMPRESS);
    Artifact a = new Weapon("sword",10);

    c2.setArtifact(a);
    CreatureView.show(c2);

    GameMap map = new GameMap(0);
    map.populate(c2);
    map.see();
  }
}

/**
 * import javax.swing.*;
 *     // Creating instance of JFrame // Creating instance of JFrame
    JFrame frame = new JFrame();

    // Creating instance of JButton
    JButton button = new JButton(" GFG WebSite Click");

    // x axis, y axis, width, height
    button.setBounds(150, 200, 220, 50);

    // adding button in JFrame
    frame.add(button);

    // 400 width and 500 height
    frame.setSize(500, 600);

    // using no layout managers
    frame.setLayout(null);

    // making the frame visible
    frame.setVisible(true);
 */