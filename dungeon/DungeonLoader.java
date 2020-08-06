package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        JSONObject Goal = json.getJSONObject("goal-condition");        
        String Union = Goal.getString("goal");

        List<String> l = new ArrayList<String>();
        //if(Union.equals("OR")) {
        //	
        //}else {
	        if(Union.equals("AND") || Union.equals("OR")) {
	        	dungeon.getGoalList().add(l);
	        	MoreGoal m = new MoreGoal();
	        	m.addGoal(Goal, dungeon);
	        }else if(Union.equals("exit")) {
	        	l.add("exit");
	        	loadGoal(dungeon, l);
	        }else if(Union.equals("enemies")) {
	        	l.add("enemies");
	        	loadGoal(dungeon, l);
	        }else if(Union.equals("treasure")) {
	        	l.add("treasure");
	        	loadGoal(dungeon, l);
	        }else if(Union.equals("boulders")) {
	        	l.add("boulders");
	        	loadGoal(dungeon, l);
	        }
        //}

        
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "door":
        	int id_d = json.getInt("id");
            Door door = new Door(x, y, id_d);
            onLoad(door);
            entity = door;
            break;
        case "backpack":
            Backpack backpack = new Backpack(x, y);
            onLoad(backpack);
            entity = backpack;
            break;
        case "bomb":
            Bomb bomb = new Bomb(x, y, dungeon);
            onLoad(bomb);
            entity = bomb;
            break;
        case "LightBomb":
        	LightBomb lbomb = new LightBomb(x, y, dungeon);
            onLoad(lbomb);
            entity = lbomb;
            break;
        case "boulder":
            Boulder boulders = new Boulder(dungeon, x, y);
            onLoad(boulders);
            entity = boulders;
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "enemy":
            Enemy enemy = new Enemy(x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "invincibility":
            Potion potion = new Potion(x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "key":
        	int id_k = json.getInt("id");
            Key key = new Key(x, y, id_k);
            onLoad(key);
            entity = key;
            break;
        case "business":
            Business business = new Business(x, y);
            onLoad(business);
            entity = business;
            break;
        case "dirt":
        	Dirt dirt = new Dirt(x, y);
            onLoad(dirt);
            entity = dirt;
            break;
        case "switch":
        	Switch switches = new Switch(x,y);
        	onLoad(switches);
        	entity = switches;
        	break;
        case "archer":
        	Archer archer = new Archer(x,y);
        	onLoad(archer);
        	entity = archer;
        	break;
        // TODO Handle other possible entities
        }
        dungeon.addEntity(entity);
    }
    
    public void loadGoal(Dungeon dungeon, List<String> goal) {
    	dungeon.addGoalList(goal);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

	public abstract void onLoad(Bomb bomb);

	public abstract void onLoad(Boulder boulders);

	public abstract void onLoad(Exit exit);

	public abstract void onLoad(Enemy enemy);

	public abstract void onLoad(Potion potion);

	public abstract void onLoad(Treasure treasure);

	public abstract void onLoad(Sword sword);

	public abstract void onLoad(Key keys);

	public abstract void onLoad(Door door);

	public abstract void onLoad(Dirt dirts);

	public abstract void onLoad(Switch plates);

	public abstract void onLoad(LightBomb bomb);
	
	public abstract void onLoad(Backpack backpack);

	public abstract void onLoad(Business business);

	public abstract void onLoad(Archer archer);

    // TODO Create additional abstract methods for the other entities

}
