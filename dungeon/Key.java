package unsw.dungeon;

public class Key extends Entity{
	private int id;
	private boolean canUse;
	
	public Key(int x, int y, int id) {
        super(x, y);
        this.id = id;
        setCanUse(true);
    }
	
	public int getID() {
		return id;
	}
	
    public boolean collisionKey(Player player) {
    	if(player.getKey() == null) {
			player.setKey(this);
			System.out.println("get a key");
			return true;
		}else {
			System.out.println("you can't take two or more keys");
			return false;
		}
    }

	public boolean isCanUse() {
		return canUse;
	}

	public void setCanUse(boolean canUse) {
		this.canUse = canUse;
	}
    
}
