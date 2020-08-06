package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

public class Potion extends Entity{
	public Potion(int x, int y) {
        super(x, y);
    }
	
    public boolean collisionPotion(Player player) {
		System.out.println("Invincibility");
		player.setPotion(1);
		Timer CountDown = new Timer();
		CountDown.schedule(new TimerTask() {
			@Override
			public void run() {
				player.setPotion(0);
				System.out.println("Not invisiable");
				player.addRemoves("potion", getX(),getY());
				player.removeP(getX(),getY());
			}
		}, 5000);
		return true;

    }
}
