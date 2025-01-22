package fr.ft.app.View;

import java.util.Scanner;

import fr.ft.app.Controller.GameController;
import fr.ft.app.Model.GameModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class GameView {

    private GameController controller;
    private GameModel model;

    private MapView map;
    private HeroView hero;
    private Scanner scan;
    private boolean running = false;

    public GameView () {
        scan = new Scanner(System.in);
    }

    public GameView(GameModel p_model) {
        scan = new Scanner(System.in);
        map = new MapView(p_model.getMap());
    }


    public void run() {
        running = true;

        do {
            // call encounter view of hero view
            String output = hero.console();

            if (output == null)
                output = map.console();

            System.out.println(output);

            System.out.print("> ");


            String input = scan.nextLine();
            controller.input(input);

        } while (running);
    }

}


/*
 * 
 * | HISTORY |    |MAP MAP MAP MAP MAP MAP|    |STATS|
 * | HISTORY |    |MAP MAP MAP MAP MAP MAP|    |STATS|
 * | HISTORY |    |MAP MAP MAP MAP MAP MAP|    |STATS|
 * | HISTORY |    |MAP MAP MAP MAP MAP MAP|    |STATS|
 * | HISTORY |    |MAP MAP MAP MAP MAP MAP|    |STATS|
 * | HISTORY |    |MAP MAP MAP MAP MAP MAP|    |STATS|
 * 
 * | WHATYOUCANDO WHATYOUCANDO WHATYOUCANDO WHATYOUCANDO |
 * | ENTERCOMMAND  ENTERCOMMAND |
 * 
 */
