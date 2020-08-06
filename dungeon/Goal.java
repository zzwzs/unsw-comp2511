package unsw.dungeon;

import org.json.JSONObject;

public interface Goal {
	public boolean AchieveGoal(Dungeon dungeon, Player player);
	public void addGoal(JSONObject goal, Dungeon dungeon);
}
