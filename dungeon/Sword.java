package unsw.dungeon;

public class Sword extends Entity{
	private int Durability;
	
	public Sword(int x, int y) {
        super(x, y);
        Durability = 5;
    }

	public void abrasion() {
		// TODO Auto-generated method stub
		Durability += -1;
	}
	
	public int getDura() {
		return Durability;
	}
	
	public boolean collisionSword(Player player) {
		if(player.getSword() == null) {
			player.setSword(this);
			
			System.out.println("get a sword");
			return true;
		}else {
			System.out.println("you can't take two or more sowrds");
			return false;
		}
    }
}
