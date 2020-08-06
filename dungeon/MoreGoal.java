package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MoreGoal implements Goal {
	private List<Goal> goals;
	
	@Override
	public boolean AchieveGoal(Dungeon dungeon, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addGoal(JSONObject Goal, Dungeon dungeon) {
		// TODO Auto-generated method stub
		JSONArray AllGoals = Goal.getJSONArray("subgoals");
		List<String> l = new ArrayList<>();
		l.addAll(dungeon.getGoalList().get(dungeon.getGoalList().size()-1));
		if(Goal.getString("goal").equals("AND")) {
			if(AllGoals.getJSONObject(0).getString("goal").equals("AND") || AllGoals.getJSONObject(0).getString("goal").equals("OR")) {
				addGoal(AllGoals.getJSONObject(0), dungeon);
			}else {
				dungeon.getGoalList().get(dungeon.getGoalList().size()-1).add(AllGoals.getJSONObject(0).getString("goal"));
			}
			if(AllGoals.getJSONObject(1).getString("goal").equals("AND") || AllGoals.getJSONObject(1).getString("goal").equals("OR")) {
				addGoal(AllGoals.getJSONObject(1), dungeon);
			}else {
				//System.out.println(AllGoals.getJSONObject(1).getString("goal"));
				dungeon.getGoalList().get(dungeon.getGoalList().size()-1).add(AllGoals.getJSONObject(1).getString("goal"));
			}
		}else {
			if(AllGoals.getJSONObject(0).getString("goal").equals("AND") || AllGoals.getJSONObject(0).getString("goal").equals("OR")) {
				addGoal(AllGoals.getJSONObject(0), dungeon);
			}else {
				dungeon.getGoalList().get(dungeon.getGoalList().size()-1).add(AllGoals.getJSONObject(0).getString("goal"));
			}
			dungeon.getGoalList().add(l);
			if(AllGoals.getJSONObject(1).getString("goal").equals("AND") || AllGoals.getJSONObject(1).getString("goal").equals("OR")) {
				addGoal(AllGoals.getJSONObject(1), dungeon);
			}else {
				dungeon.getGoalList().get(dungeon.getGoalList().size()-1).add(AllGoals.getJSONObject(1).getString("goal"));
			}
		}
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

}
