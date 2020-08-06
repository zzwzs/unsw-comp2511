package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Business extends Entity{
	private Dungeon dungeon;
	private List<Integer> keys;
	
	public Business(int x, int y) {
		super(x, y);
		keys = new ArrayList<>();
	}
	
	public void getWorld(Dungeon dungeon) {
		this.dungeon = dungeon;
		gener();
	}
	
	public void gener() {
		for(Entity entity: dungeon.getEntity()) {
			if(entity instanceof Door) {
				Door d = (Door)entity;
				keys.add(d.getID());
			}
		}
	}
	
	public Key getAKey(Dungeon dungeon) {
		int found = 0;
		Random rand = new Random();
		//System.out.println(keys);
		//System.out.println(keys.size());
		int a = rand.nextInt(keys.size());
		for(int i = 0; i < dungeon.getWidth() - 1; i++) {
			for(Entity entity:dungeon.getEntity()) {
				if(entity.getY() == dungeon.getHeight() - 1) {
					if(entity.getX() == i) {
						found = 1;
						break;
					}
				}
			}
			if(found == 0) {
				//System.out.println(1);
				Key k = new Key(i, dungeon.getHeight() - 1, keys.get(a));
				sold(keys.get(a));
				return k;
			}
			found = 0;
		}
		System.out.println("Your backpack is full");
		return null;
	}
	
	public void sold(int id) {
		keys.remove(id);
	}
	
}
