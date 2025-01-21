package fr.ft.app;

import fr.ft.app.Entity.Creature;

public interface IGameMap {

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    };

    int minSize = 9;

    void moveHero(Direction dir);

    void populate(Creature hero);
    void addEntityAt(Creature creature, Coordinates position);


    Creature[][] getViewData();

}
    // Entity getEntityAt(Entity entity, Coordinates position);

    // boolean addEntityAt(Entity entity, Coordinates position);

    // void moveEntity(Entity entity, Direction dir);
    // void moveEntity(Entity entity, Coordinates position);
