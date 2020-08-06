package unsw.dungeon;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Archer extends Entity{
	private boolean alive;
	private int status;
	
	public Archer(int x, int y) {
		super(x, y);
		setAlive(true);
		status = 0;
	}

	public boolean collisionArcher(Player player) {
		if(alive == false) {
			return true;
		}
		if(player.getStatus() == 1) {
			setAlive(false);
			return true;
		}
		return false;
	}
	
	public void archerMove(Dungeon dungeon) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(alive == false) {
					cancel();
				}
				Move(dungeon);
			}
		}, 2000, 2000);
	}
	
	public void Move(Dungeon dungeon) {
		if(alive = true) {
			if(status == 0) {
				for(Entity entity:dungeon.getEntity()) {
					if(entity.getX() == getX()+1 && entity.getY() == getY()) {
						status += 1;
						break;
					}
				}
				this.x().set(getX()+1);
				status += 1;
			}else if(status == 1) {
				for(Entity entity:dungeon.getEntity()) {
					if(entity.getX() == getX()+1 && entity.getY() == getY()) {
						status += 1;
						break;
					}
				}
				this.y().set(getY()+1);
				status += 1;
			}else if(status == 2) {
				for(Entity entity:dungeon.getEntity()) {
					if(entity.getX() == getX()+1 && entity.getY() == getY()) {
						status += 1;
						break;
					}
				}
				this.x().set(getX()-1);
				status +=1;
			}else if(status == 3) {
				for(Entity entity:dungeon.getEntity()) {
					if(entity.getX() == getX()+1 && entity.getY() == getY()) {
						status = 0;
						break;
					}
				}
				this.y().set(getY()-1);
				status = 0;
			}
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
