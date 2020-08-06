package unsw.dungeon;

import java.util.Timer; 
import java.util.TimerTask;

public class Enemy extends Entity{
	public int playerx;
	public int playery;
	private boolean alive;
	public Enemy(int x, int y) {
		super(x, y);
		this.playerx = 0;
		this.playery = 0;
		alive = true;
	}

	public void enemyMove(int x2, int y2, Player player) {
		if(alive = true) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					int x1 = player.getX();
					int y1 = player.getY();
					//System.out.println(x1);
					if(x1!= x2 || y1!= y2 || alive == false) {
						cancel();
					}
					move(x2, y2, player.getD());
					if(getX() == x1 && getY() == y1) {
						if(player.getSwordS() == 1 || player.getStatus() == 1) {
							//player.getSword().abrasion();
							//System.out.println("enemy has been killed");
							//alive = false;
						}else {
							player.die();
							System.out.println("game over");
							cancel();
						}
					}
				}
			}, 2000, 2000);
	
		}
	}
	
	public void update(int x, int y) {
		this.playerx = x;
		this.playery = y;
	}
	
	public boolean move(int px, int py, Dungeon dungeon) {
		if(alive = true) {
			if(dungeon.getPlayer().getStatus() == 0) {	
				if(py > getY()) {
					if(!Obstruct(getX(), getY()+1, dungeon)) {
						y().set(getY()+1);
					}
				}else if(py < getY()) {
					if(!Obstruct(getX(), getY()-1, dungeon)) {
						y().set(getY()-1);
					}
				}else {
					if(px > getX()) {
						if(!Obstruct(getX()+1, getY(), dungeon)) {
							x().set(getX()+1);
						}
					}else if(px < getX()){
						if(!Obstruct(getX()-1, getY(), dungeon)) {
							x().set(getX()-1);
						}
					}else {
						
					}
				}
			}else {
				if(py > getY()) {
					if(!Obstruct(getX(), getY()-1, dungeon)) {
						y().set(getY()-1);
					}
				}else if(py < getY()) {
					if(!Obstruct(getX(), getY()+1, dungeon)) {
						y().set(getY()+1);
					}
				}else {
					if(px > getX()) {
						if(!Obstruct(getX()-1, getY(), dungeon)) {
							x().set(getX()-1);
						}
					}else if(px < getX()){
						if(!Obstruct(getX()+1, getY(), dungeon)) {
							x().set(getX()+1);
						}
					}else {
						
					}
				}
			}
		}
		return true;
	}
	
	public boolean Obstruct(int x, int y, Dungeon dungeon) {
    	for(Entity entity : dungeon.getEntity()){
    		if(x == entity.getX() && y == entity.getY()) {
    			if(entity instanceof Wall || entity instanceof Boulder || entity instanceof Door || entity instanceof Enemy) {
    				//System.out.println("can't move");
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
	public boolean collisionEnemy(Player player) {
		if(alive = true) {
			if(player.getSword() != null || player.getStatus() == 1) {
					System.out.println("An enemy has been killed");
					alive = false;
					if (player.getSword() != null && player.getStatus() == 0) {
						player.getSword().abrasion();
		  				if(player.getSword().getDura() <= 0) {
		  					System.out.println("Your sword can't be used anymore");
		  					player.addRemoves("sword", player.getSword().getX(), player.getSword().getY());
		  					player.deleteS();
		  					player.setSword(null);
		  				}
					}
					return true;
				}else {
					player.die();
					System.out.println("Game over, killed by enemy");
					return false;
				}
		}else {
			return true;
		}
    }
	
	public boolean getStatus() {
		return alive;
	}
	
	public void setStatus() {
		alive = false;
	}
}