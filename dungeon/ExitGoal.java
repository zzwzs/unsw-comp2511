package unsw.dungeon;

import org.json.JSONObject;

public class ExitGoal implements Goal{

	@Override
	public boolean AchieveGoal(Dungeon dungeon, Player player) {
		if(player.getExit()) {
			return true;
		}
		return false;
	}

	@Override
	public void addGoal(JSONObject goal, Dungeon dungeon) {
		// TODO Auto-generated method stub
		
	}

}
