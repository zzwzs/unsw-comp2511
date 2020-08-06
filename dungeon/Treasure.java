package unsw.dungeon;

public class Treasure extends Entity{
	public Treasure(int x, int y) {
        super(x, y);
    }
	
    public boolean collisionTreasure(Player player) {
    	player.setScore(player.getScore()+5);
		System.out.println("get treasure");
		return true;
    }
}
