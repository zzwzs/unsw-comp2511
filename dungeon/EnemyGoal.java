package unsw.dungeon;

import org.json.JSONObject;

public class EnemyGoal implements Goal{

	@Override
	public boolean AchieveGoal(Dungeon dungeon, Player player) {
		for(Entity entity : dungeon.getEntity()) {
			if(entity instanceof Enemy) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void addGoal(JSONObject goal, Dungeon dungeon) {
		// TODO Auto-generated method stub
		
	}
	
}
