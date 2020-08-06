/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<List<String>> goal;
    private List<String> oneofgoal;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goal = new ArrayList<>();
        this.oneofgoal = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public List<Entity> getEntity() {
    	return entities;
    }
    
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    }
    
    public List<List<String>> getGoalList(){
    	return goal;
    }
    
    public void addGoalList(List<String> goals) {
    	this.goal.add(goals);
    }

	public List<String> getOneofgoal() {
		return oneofgoal;
	}

	public void addOneofgoal(String oneofgoal) {
		this.oneofgoal.add(oneofgoal);
	}
}
