package unsw.dungeon;

import org.json.JSONObject;

public class TreasureGoal implements Goal{
	private int treasures;
	
	@Override
	public boolean AchieveGoal(Dungeon dungeon, Player player) {
		treasures = 0;
		for(Entity entity : dungeon.getEntity()) {
			if(entity instanceof Treasure) {
				treasures++;
			}
		}
		if(treasures == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void addGoal(JSONObject goal, Dungeon dungeon) {
		// TODO Auto-generated method stub
		
	}
	
}
