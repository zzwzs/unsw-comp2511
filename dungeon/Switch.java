package unsw.dungeon;

public class Switch extends Entity{
	private int achieve;
	
	public Switch(int x, int y) {
        super(x, y);
        achieve = 0;
    }
	
	public int getAchieve() {
		return achieve;
	}
	
	public void setAchieve(int test) {
		achieve = test;
	}
}
