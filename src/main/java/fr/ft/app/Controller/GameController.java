package fr.ft.app.Controller;

import fr.ft.app.Model.GameModel;
import fr.ft.app.Model.Map.Direction;
import fr.ft.app.View.GameView;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class GameController {
    private GameView view;
    private GameModel model;

    private HeroController hero;
    private MapController map;

    public GameController() {

    }

    public GameController(GameView p_view) {
        view = p_view;
    }

    /**
     * Input entrypoint, separate meta move and encounter command and call revelant handler
     * @param input raw inut from view 
     */
    public void input(String input) {
        if (input.startsWith("."))
            meta(input);
        else if (hero.isEncounter())
            encounter(input);
        else
            movement(input);

    }

    private void meta(String input) {
        switch (input) {
            default:
                break;
            case ".exit":
                stop();
 
        }
    }

    private void movement(String input) {
        switch (input) {
            case "north":
                map.moveHero(Direction.NORTH);
                break;
            case "east":
                map.moveHero(Direction.EAST);
                break;
            case "south":
                map.moveHero(Direction.SOUTH);
                break;
            case "west":
                map.moveHero(Direction.WEST);
                break; 
        }
    }

    private void encounter(String input) {

    }

    private void stop() {
        view.setRunning(false);
    }

}

// mode : - move -> N/E/S/W
//        - encounter -> fight/run
// meta : .exit .gui .stats .help  

//  meta -> gamecontroller
// encounter -> hero controller 
// move -> map 