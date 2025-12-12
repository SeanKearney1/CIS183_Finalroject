package com.example.dungeoncreator;

import android.content.res.Resources;
import android.media.Image;

public class DungeonData {

    private static final int dungeonSize = 32;
    private static final String[] themes = { "None","Easy","Extreme","Traps","Puzzle","Really Big","One Room","Story","Oops All Monsters!!!","Slime Only","Skeleton Only" };
    private static final String[] categories = {"Normal","Frozen","Desert","Void"};
    private static final String[] items = {"Sword","Key"};
    private static final String[] enemies = {"Slime","Skull"};
    private static final String[] objects = {"LeverOff","LeverOn","Bridge","DoorClosed","DoorOpened","FinalDoorClosed","FinalDoorOpened","SpawnerOn","SpawnerOff"};

    private static final int imageTiles[][] = {
            //Normal
            {
                R.drawable.air,
                R.drawable.stone_back,
                R.drawable.stone,
                R.drawable.water,
            },
            //Frozen
            {
                R.drawable.air,
                R.drawable.frozen_stone_back,
                R.drawable.frozen_stone,
                R.drawable.frozen_water,
            },
            //Desert
            {
                R.drawable.air,
                R.drawable.desert_stone_back,
                R.drawable.desert_stone,
                R.drawable.desert_water,
            },
            //Void
            {
                R.drawable.air,
                R.drawable.void_stone_back,
                R.drawable.void_stone,
                R.drawable.void_water,
            }
    };
    private static final int imageItems[][] = {
            //Normal
            {
                R.drawable.air,
                R.drawable.key,
                R.drawable.sword
            },
            //Frozen
            {
                R.drawable.air,
                R.drawable.frozen_key,
                R.drawable.sword
            },
            //Desert
            {
                R.drawable.air,
                R.drawable.desert_key,
                R.drawable.sword
            },
            //Void
            {
                R.drawable.air,
                R.drawable.void_key,
                R.drawable.sword
            }
    };
    private static final int imageEnemies[][] = {
            //Normal
            {
                R.drawable.air,
                R.drawable.slime,
                R.drawable.skull
            },
            //Frozen
            {
                R.drawable.air,
                R.drawable.frozen_slime,
                R.drawable.frozen_skull
            },
            //Desert
            {
                R.drawable.air,
                R.drawable.desert_slime,
                R.drawable.desert_skull
            },
            //Void
            {
                R.drawable.air,
                R.drawable.void_slime,
                R.drawable.void_skull
            }
    };
    private static final int imageObjects[][] = {
            //Normal
            {
                R.drawable.air,
                R.drawable.lever_off,
                R.drawable.lever_on,
                R.drawable.bridge,
                R.drawable.door_closed,
                R.drawable.door_open,
                R.drawable.final_door_closed,
                R.drawable.final_door_open,
                R.drawable.spawner_on,
                R.drawable.spawner_off,
                R.drawable.player
            },
            //Frozen
            {
                R.drawable.air,
                R.drawable.frozen_lever_off,
                R.drawable.frozen_lever_on,
                R.drawable.frozen_bridge,
                R.drawable.frozen_door_closed,
                R.drawable.frozen_door_open,
                R.drawable.final_frozen_door_closed,
                R.drawable.final_frozen_door_open,
                R.drawable.frozen_spawner_on,
                R.drawable.frozen_spawner_off,
                R.drawable.player
            },
            //Desert
            {
                R.drawable.air,
                R.drawable.desert_lever_off,
                R.drawable.desert_lever_on,
                R.drawable.desert_bridge,
                R.drawable.desert_door_closed,
                R.drawable.desert_door_open,
                R.drawable.final_desert_door_closed,
                R.drawable.final_desert_door_open,
                R.drawable.desert_spawner_on,
                R.drawable.desert_spawner_off,
                R.drawable.player
            },
            //Void
            {
                R.drawable.air,
                R.drawable.void_lever_off,
                R.drawable.void_lever_on,
                R.drawable.void_bridge,
                R.drawable.void_door_closed,
                R.drawable.void_door_open,
                R.drawable.final_void_door_closed,
                R.drawable.final_void_door_open,
                R.drawable.void_spawner_on,
                R.drawable.void_spawner_off,
                R.drawable.player
            }
    };




    private static Dungeon currentDungeon;

    public static String[] getThemes() { return themes; }

    public static String[] getCategories() {
        return categories;
    }
    public static String[] getItems() {
        return items;
    }
    public static String[] getEnemies() {
            return enemies;
    }
    public static String[] getObjects() {
        return objects;
    }
    public static int getDungeonSize() {
        return dungeonSize;
    }

    public static int[] getDungeonTiles(int theme) {
        return imageTiles[theme];
    }
    public static int[] getDungeonEnemies(int theme) {
        return imageEnemies[theme];
    }
    public static int[] getDungeonItems(int theme) {
        return imageItems[theme];
    }
    public static int[] getDungeonObjects(int theme) {
        return imageObjects[theme];
    }

    public static Dungeon getCurrentDungeon() {
        return currentDungeon;
    }
    public static void setCurrentDungeon(Dungeon d) {
        currentDungeon = d;
    }


}
