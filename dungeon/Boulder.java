package unsw.dungeon;

public class Boulder extends Entity{
	private Dungeon dungeon;
	private boolean Destroyed;
	
	public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        setDestroyed(false);
    }

	public boolean isDestroyed() {
		return Destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		Destroyed = destroyed;
	}
	
	
	
}
