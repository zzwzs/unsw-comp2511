package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{
	private Dungeon dungeon;
	private List<Entity> l;
	
	public Bomb(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.dungeon = dungeon;
        l = new ArrayList<>();
    }

	public void Explode() {
		// TODO Auto-generated method stub
		//ArrayList<Entity> delete = new ArrayList<>();

		int x = getX();
		int y = getY();
      	for(Entity entity : dungeon.getEntity()){
      		if((x >= entity.getX()-1 && x <= entity.getX()+1 && y >= entity.getY()-1 && y <= entity.getY()+1) && (entity.getX() == x || entity.getY() == y)) {
      			if(entity instanceof Boulder || entity instanceof Enemy || entity instanceof Player || entity instanceof Archer) {
	      			if(entity instanceof Player) {
	      				Player player = (Player)entity;
	      				player.die();
	      				System.out.println("Game over, killed by bomb");
	      				l.add(entity);
	      			}else if(entity instanceof Enemy) {
	      				//entity.x().set(0);
	      				//entity.y().set(0);
	      				Enemy e = (Enemy)entity;
	      				e.setStatus();
	      				System.out.println("Kill enemy by bomb");
	      				l.add(entity);
	      			}else if(entity instanceof Archer) {
	      				Archer a = (Archer)entity;
	      				a.setAlive(false);
	      				System.out.println("Kill archer by bomb");
      				}else {
	      				Boulder boulder = (Boulder)entity;
	      				boulder.setDestroyed(true);
	      				System.out.println("Destroy boulder by bomb");
	      				l.add(entity);
	      			}
	      			
      			}
      		}
      	}
	}
	public boolean collisionBomb(Player player) {
		player.setBombs(player.getBombs()+1);

		System.out.println("get a bomb");
		return true;
    }
}
