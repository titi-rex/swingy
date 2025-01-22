package fr.ft.app.View;

import fr.ft.app.Model.Entity.Creature.Creature;
import fr.ft.app.Model.Map.Coordinates;
import fr.ft.app.Model.Map.MapModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class MapView {

    private MapModel model;

    public MapView(MapModel p_model) {
        model = p_model;
    }

    public String console() {
        Creature[][] layout = model.getLayout();

        String txtMap = "";
        Coordinates heroPos = model.getHero().getCoordinates();
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[y].length; x++) {
                if (heroPos.x == x && heroPos.y == y)
                    txtMap += String.format(" %.1s ", "H");
                else if (layout[x][y] != null)
                    txtMap += String.format(" %.1s ", layout[x][y].getRole().name());
                else
                    txtMap += String.format(" %s ", " ");
            }
            txtMap += "\n";
        }
        return txtMap;
    }
}
