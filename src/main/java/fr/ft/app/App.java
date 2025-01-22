package fr.ft.app;

import fr.ft.app.Controller.GameController;
import fr.ft.app.Controller.HeroController;
import fr.ft.app.Controller.MapController;
import fr.ft.app.Model.GameModel;
import fr.ft.app.Model.Entity.Artifact.Artifact;
import fr.ft.app.Model.Entity.Artifact.Weapon;
import fr.ft.app.Model.Entity.Creature.Creature;
import fr.ft.app.Model.Entity.Creature.Role;
import fr.ft.app.Model.Map.MapModel;
import fr.ft.app.View.GameView;
import fr.ft.app.View.HeroView;
import fr.ft.app.View.MapView;

/*
 * 1 create model
 * 2 create controller
 * 3 init model via controller
 * 4 create view
 * 5 launch view loop
 */

/**
 * Hello world!
 *
 */
public class App   {


    public static void main(String[] args) throws Exception {
  
        // create model 
        Creature hero = Creature.invoke("mimou", Role.EMPRESS);
        Artifact a = new Weapon("sword",10);
        hero.setArtifact(a);
    
        MapModel map = new MapModel();
        map.setHero(hero);

        GameModel model = new GameModel();
        model.setMap(map);



        // create controller
        HeroController heroController = new HeroController(hero);
        MapController mapController = new MapController(map, 0);
        mapController.populate();

        GameController controller = new GameController();
        controller.setHero(heroController);
        controller.setMap(mapController);


        //create view
        MapView mView = new MapView(map);
        HeroView hView = new HeroView(hero);

        GameView view = new GameView();
        view.setMap(mView);
        view.setHero(hView);


        // link M C V
        controller.setView(view);
        controller.setModel(model);
        view.setController(controller);
        view.setModel(model);
        model.setView(view);
        model.setController(controller);
        model.setView(view);
        
        // start loop
        view.run();

      }

}

/*

 */