package fr.ft.app.Model;

import fr.ft.app.Controller.GameController;
import fr.ft.app.Model.Map.MapModel;
import fr.ft.app.View.GameView;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class GameModel {
    private GameView view;
    private GameController controller;

    // private Creature hero;
    private MapModel map;

    public GameModel() {
        
    }
}
