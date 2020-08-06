package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer; 
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private Sword sword;
    private Key key;
    private int score;
    private Bomb bomb;
    private int potion;
    private int alive;
    private int bombs;
    private boolean E;
    private boolean start;
    private boolean AchieveGoal;
    private List<Remove> removes;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        sword = null;
    	key = null;
    	bomb = null;
    	potion = 0;
    	alive = 1;
    	bombs = 0;
    	E = false;
    	start = false;
    	removes = new ArrayList<>();
    	setAchieveGoal(false);
    }

    public void moveUp() {
        if(alive == 1 && start == true) {
	    	if (getY() > 0) {
	        	int x = getX();
	            int y = getY()-1;
	            if(IsWall(x,y) == false && IsClosedDoor(x,y) == false) {
	            	if(checkPush(x, x, getY(), y)) {
	            		if(pushB(x, y)) {
	            			y().set(y);
	            		}
	            	}else {  		
	            		y().set(y);
	            	}
	            }
	            EnemyMove();
	            collision(x,y);
	            if(WasShot()) {
	            	System.out.println("killed by archer");
	            	die();
	            }
	            if(achieveGoal()) {
	            	setAchieveGoal(true);
	            	System.out.println("You win");
	            }
	        }
        }
    }

    public void moveDown() {
    	if(alive == 1 && start == true) {
	        if (getY() < dungeon.getHeight() - 1) {
	        	int x = getX();
	            int y = getY()+1;
	            if(IsWall(x,y) == false && IsClosedDoor(x,y) == false) {
	            	if(checkPush(x, x, getY(), y)) {
	            		if(pushB(x, y)) {
	            			y().set(y);
	            		}
	            	}else {		
	            		y().set(y);
	            	}
	            	
	            }
	            EnemyMove();
	            collision(x,y);
	            if(WasShot()) {
	            	System.out.println("killed by archer");
	            	die();
	            }
	            if(achieveGoal()) {
	            	setAchieveGoal(true);
	            	System.out.println("You win");
	            }
	        }
    	}
    }

    public void moveLeft() {
    	if(alive == 1 && start == true) {
	        if (getX() > 0) {
	        	int x = getX()-1;
	            int y = getY();
	            if(IsWall(x,y) == false && IsClosedDoor(x,y) == false) {
	            	if(checkPush(getX(), x, y, y)) {
	            		if(pushB(x, y)) {
	            			x().set(x);
	            		}
	            	}else {      		
	            		x().set(x);
	            	}
	            }
	            EnemyMove();
	            collision(x,y);
	            if(WasShot()) {
	            	System.out.println("killed by archer");
	            	die();
	            }
	            if(achieveGoal()) {
	            	setAchieveGoal(true);
	            	System.out.println("You win");
	            }
	        }
    	}
    }

    public void moveRight() {
    	if(alive == 1 && start == true) {
	    	if (getX() < dungeon.getWidth() - 1) {
	            int x = getX()+1;
	            int y = getY();
	            if(IsWall(x,y) == false && IsClosedDoor(x,y) == false) {
	            	if(checkPush(getX(), x, y, y)) {
	            		if(pushB(x, y)) {
	            			x().set(x);
	            		}
	            	}else {	
	            		x().set(x);
	            	}
	            }
	            EnemyMove();
	            collision(x,y);
	            if(WasShot()) {
	            	System.out.println("killed by archer");
	            	die();
	            }
	            if(achieveGoal()) {
	            	setAchieveGoal(true);
	            	System.out.println("You win");
	            }
	    	}
        }

    }
    
    public boolean IsWall(int x, int y) {
    	for(Entity entity : dungeon.getEntity()){
    		//if(entity == null) {
    		//	return false;
    		//}
    		if(x == entity.getX() && y == entity.getY()) {
    			if(entity instanceof Wall) {
    				System.out.println("can't move");
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public Boolean IsClosedDoor(int x, int y) {
      	for(Entity entity : dungeon.getEntity()){
      		//if(entity == null) {
    		//	return false;
    		//}
      		if(x == entity.getX() && y == entity.getY()) {
      			if(entity instanceof Door) {
	      			Door door = (Door) entity;
	      			if(key == null && door.getStatus() != "open") {
	      				return true;
	      			}else if(key != null && key.getID() == door.getID() && door.getStatus() == "close") {
	      				door.setStatus("open");
	      				for(Entity keys: dungeon.getEntity()) {
	      					if(keys instanceof Key) {
	      						if(keys.getY() == dungeon.getHeight() - 1) {
	      							Remove r = new Remove("key", keys.getX(), keys.getY());
	      							removes.add(r);
		      						dungeon.removeEntity(keys);
		      						break;
		      					}
	      					}
	      				}
	      				this.setKey(null);
	      				System.out.println("You open the door");
	      				return false;
	      			}else if(door.getStatus() == "open"){
	      				return false;
	      			}else {
	      				return true;
	      			}
      			}
      		}
      	}
      	return false;
    }
    
    /**
     * 
     *to check is this entity boulder or not, if it is return true else return false
     */
    
    public Boolean checkPush(int x1, int x2, int y1, int y2) {							//(x1, y1) is player before move,(x2, y2) is player after move
    	if(y1 == y2 && x2 != x1) {
    		for(Entity entity : dungeon.getEntity()){
    			if(x2 == entity.getX() && y1 == entity.getY()) {
    				if(entity instanceof Boulder) {
    					return true;
    				}
    			}
    		}
    	}else if(x1 == x2 && y1 != y2) {
    		for(Entity entity : dungeon.getEntity()){
    			if(x1 == entity.getX() && y2 == entity.getY()) {
    				if(entity instanceof Boulder) {
    					return true;
    				}
    			}
    		}
    	}else {
    		return false;
    	}
    	return false;
    }
    
    public void EnemyMove() {
    	for(Entity entity: dungeon.getEntity()) {
        	if(entity instanceof Enemy && alive == 1) {
        		Enemy E1 = (Enemy) entity;
        		if(E1.getStatus()) {
        			E1.enemyMove(getX(), getY(), this);
        			E1.update(getX(), getY());
        		}
        	}
        }
    }
    
    public Boolean pushB(int x, int y) {								//x and y (x,y) is the coordinate of the boulder
    	int x1, y1 = 0;
    	if(x - getX() == 1 && y == getY()) {
    		x1 = x + 1;
    		y1 = y;
    	}else if(getX() - x == 1 && y == getY()) {
    		x1 = x - 1;
    		y1 = y;
    	}else if(x == getX() && y - getY() == 1) {
    		x1 = x;
    		y1 = y + 1;
    	}else if(x == getX() && getY() - y == 1) {
    		x1 = x;
    		y1 = y - 1;
    	}else {
    		System.out.println("boulder is not near the player");
    		return false;
    	}
    	for(Entity entity : dungeon.getEntity()){
    		if(x1 == entity.getX() && y1 == entity.getY()) {			//x1 and y1 (x1, y1) is the coordinate of the position we want to push boulder to
        		//System.out.println(1);
    			if(entity instanceof Wall || entity instanceof Boulder) {
    				System.out.println("we can't push boulder to a Wall or another boulder");
    				return false;
    			}else if(entity instanceof Switch){
    				
    			}else {
    				return false;
    			}
    		}
    	}
    	for(Entity boulder : dungeon.getEntity()){
			if(x == boulder.getX() && y == boulder.getY()) {
				if(boulder instanceof Boulder) {
					boulder.x().set(x1);
					boulder.y().set(y1);
					//System.out.println("success");
					return true;
				}
			}
		}
    	return false;
    }
    
    public boolean achieveBoulders() {
    	for(Entity entity : dungeon.getEntity()) {
    		if(entity instanceof Switch) {
    			Switch switchs = (Switch) entity;
    			for(Entity boulder : dungeon.getEntity()) {
    				if(boulder instanceof Boulder) {
    					if(entity.getX() == boulder.getX() && entity.getY() == boulder.getY()) {
    						switchs.setAchieve(1);
    					}
    				}
    			}
    			if(switchs.getAchieve() == 0) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public boolean achieveGoal() {
    	int f = 0;
    	for(List<String> l :dungeon.getGoalList()) {
    		//System.out.println(l);
    		for(String s : l) {
	    		if(s.equals("exit")) {
	        		ExitGoal goals = new ExitGoal();
	        		if(!goals.AchieveGoal(dungeon, this)) {
	        			f = 1;
	        		}
	        	}else if(s.equals("enemies")) {
	        		EnemyGoal goals = new EnemyGoal();
	        		if(!goals.AchieveGoal(dungeon, this)) {
	        			f = 1;
	        		}
	        	}else if(s.equals("treasure")) {
	        		TreasureGoal goals = new TreasureGoal();
	        		if(!goals.AchieveGoal(dungeon, this)) {
	        			f = 1;
	        		}
	        	}else if(s.equals("boulders")) {
	        		BoulderGoal goals = new BoulderGoal();
	        		if(!goals.AchieveGoal(dungeon, this)) {
	        			f = 1;
	        		}
	        	}
	    		
    		}
    		if(f != 1) {
    			//System.out.println(l);
    			return true;
    		}else {
    			f = 0;
    		}
    	}
    	return false;
    	
    }
    
    public void die() {
    	alive = 0;
    }
    
    public boolean WasShot() {
    	for(Entity entity: dungeon.getEntity()) {
    		if(entity instanceof Archer) {
    			if((getX() <= entity.getX()+1 && getX() >= entity.getX()-1 && getY() <= entity.getY()+1 && getY() >= entity.getY()-1) && (getX() == entity.getX() || getY() == entity.getY())) {
    				if(potion == 0) {
    					return true;
    				}else {
    					Archer a = (Archer)entity;
    					a.setAlive(false);
    				}
    			}
    		}
    	}
    	return false;
    }
    
    public boolean lightBomb() {
    	if(bombs != 0) {
	    	bombs += -1;
	    	//LightBomb lightBomb = new LightBomb(0,0, dungeon);
	    	//ImageView view = new ImageView(new Image("/bomb_lit_1.png"));
	    	for(Entity entity:dungeon.getEntity()) {
	    		if(entity instanceof Bomb && entity.getY() == dungeon.getHeight() - 1) {
	    			System.out.println(1);
	    			Bomb b = (Bomb)entity;
	    			b.x().set(getX());
	    			b.y().set(getY());
	    			dungeon.removeEntity(entity);
	    			Timer CountDown = new Timer();
	    	        CountDown.schedule(new TimerTask() {
	    	            @Override
	    	            public void run() {	    	            	
	            			b.Explode();
	            			
	    	            	//dungeon.removeEntity(lightBomb);
	    	            }
	    	        }, 3000);
	    	        return true;
	    		}
	    	}
	    	return false;	
    	}else{
    		return false;
    	}
    }
    
    public void start() {
    	if(start == false) {
    		this.start = true;
    		for(Entity entity:dungeon.getEntity()) {
    			if(entity instanceof Archer) {
    				Archer a = (Archer)entity;
    				if(a.isAlive()) {
    					a.archerMove(dungeon);
    				}
    			}
    		}
    	}
    }
    
    public void collision(int x, int y) {
    	int exist = 0;
    	for(Entity entity : dungeon.getEntity()) {
    		if(entity.getX() == x && entity.getY() == y) {
	    		if(entity instanceof Sword) {
	    			Sword s = (Sword)entity;
	    			if(s.collisionSword(this)) {
	    			//dungeon.removeEntity(entity);
	    				for(int i = 0; i < dungeon.getWidth(); i++) {
	    					for(Entity entity2 : dungeon.getEntity()) {
	    						if(entity2.getX() == i && entity2.getY() == dungeon.getHeight() - 1) {
	    							exist = 1;
	    						}
	    					}
	    					if(exist == 0) {
	    						System.out.println(i);
	    						s.x().set(i);
	    	    				s.y().set(dungeon.getHeight() - 1);
	    	    				break;
	    					}else {
	    						exist = 0;
	    					}
	    				}
	    			}
	    			break;
	    		}else if(entity instanceof Bomb) {
	    			Bomb b = (Bomb)entity;
	    			if(b.collisionBomb(this)) {
	    				for(int i = 0; i < dungeon.getWidth(); i++) {
	    					for(Entity entity2 : dungeon.getEntity()) {
	    						if(entity2.getX() == i && entity2.getY() == dungeon.getHeight() - 1) {
	    							exist = 1;
	    						}
	    					}
	    					if(exist == 0) {
	    						//System.out.println(i);
	    						b.x().set(i);
	    	    				b.y().set(dungeon.getHeight() - 1);
	    	    				break;
	    					}else {
	    						exist = 0;
	    					}
	    				}
	    			}
	    			//dungeon.removeEntity(entity);
	    			break;
	    		}else if(entity instanceof Enemy) {
	    			Enemy e = (Enemy)entity;
	    			if(e.collisionEnemy(this)) {
	    				//dungeon.removeEntity(entity);
	    			}
	    			break;
	    		}else if(entity instanceof Archer) {
	    			Archer a = (Archer)entity;
	    			if(a.collisionArcher(this)) {
	    				//dungeon.removeEntity(entity);
	    			}
	    			break;
	    		}else if(entity instanceof Exit) {
	    			Exit e = (Exit)entity;
	    			e.collisionExit(this);
	    			System.out.println("Congratulations, you arrived at the exit");
	    			break;
	    		}else if(entity instanceof Key) {
	    			Key k = (Key)entity;
	    			if(k.collisionKey(this)) {
	    				for(int i = 0; i < dungeon.getWidth(); i++) {
	    					for(Entity entity2 : dungeon.getEntity()) {
	    						if(entity2.getX() == i && entity2.getY() == dungeon.getHeight() - 1) {
	    							exist = 1;
	    						}
	    					}
	    					if(exist == 0) {
	    						//System.out.println(i);
	    						k.x().set(i);
	    	    				k.y().set(dungeon.getHeight() - 1);
	    	    				break;
	    					}else {
	    						exist = 0;
	    					}
	    				}
	    			}
	    			//dungeon.removeEntity(entity);
	    			break;
	    		}else if(entity instanceof Potion) {
	    			Potion p = (Potion)entity;
	    			if(p.collisionPotion(this)) {
	    				for(int i = 0; i < dungeon.getWidth(); i++) {
	    					for(Entity entity2 : dungeon.getEntity()) {
	    						if(entity2.getX() == i && entity2.getY() == dungeon.getHeight() - 1) {
	    							exist = 1;
	    						}
	    					}
	    					if(exist == 0) {
	    						//System.out.println(i);
	    						p.x().set(i);
	    	    				p.y().set(dungeon.getHeight() - 1);
	    	    				break;
	    					}else {
	    						exist = 0;
	    					}
	    				}
	    			}
	    	    	//dungeon.removeEntity(entity);
	    			break;
	    		}else if(entity instanceof Treasure) {
	    			Treasure T = (Treasure)entity;
	    			T.collisionTreasure(this);
	    			Remove r = new Remove("treasure", T.getX(), T.getY());
	    			removes.add(r);
	    			dungeon.removeEntity(entity);
	    			break;
	    		}
    		}
    	}
    }

    public int getStatus() {
    	return potion;
    }
    
    public int getSwordS() {
    	if(sword == null) {
    		return 0;
    	}else{
    		return 1;
    	}
    }
    
    public Dungeon getD() {
    	return dungeon;
    }
    
    public Key getKey() {
    	return key;
    }
    
    public void setKey(Key key) {
    	this.key = key;
    }
    
    public int getScore() {
    	return score;
    }
    
    public Sword getSword() {
    	return sword;
    }
    
    public int getBombs() {
    	return bombs;
    }
    
    public void setBombs(int bombs) {
    	this.bombs = bombs;
    }

	public void setSword(Sword sword) {
		this.sword = sword;
	}
	
	public void setExit(boolean b) {
		E = b;
	}
	
	public boolean getExit() {
		return E;
	}
	
	public int getAlive() {
		return alive;
	}
	
	public int getPotion() {
		return potion;
	}
	
	public void setPotion(int potion) {
		this.potion = potion;
	}

	public void setScore(int i) {
		score = i;
	}

	public void drop() {
		// TODO Auto-generated method stub
		if(this.getKey() != null) {
			for(Entity entity: dungeon.getEntity()) {
				if(entity instanceof Key) {
					Key drop_key = (Key) entity;
					if(drop_key.getID() == this.getKey().getID()){
						drop_key.x().set(getX());
						drop_key.y().set(getY());
					}
				}
			}
			this.setKey(null);
		}
	}
	
	public void removeP(int x, int y) {
		for(Entity entity:dungeon.getEntity()) {
			if(entity.getX() == x && entity.getY() == y) {
				dungeon.removeEntity(entity);
				break;
			}
		}
	}
	
	public void deleteS() {
		for(Entity entity:dungeon.getEntity()) {
			if(entity.getY() == dungeon.getHeight() - 1 && entity instanceof Sword) {
				dungeon.removeEntity(entity);
				break;
			}
		}
	}
	
	public void addRemoves(String name, int x, int y) {
		Remove r = new Remove(name, x, y);
		removes.add(r);
	}
	
	public List<Remove> getRemoves(){
		return removes;
	}
	
	public void deleteRemoves(Remove r) {
		removes.remove(r);
	}

	public boolean trade() {
		for(Entity entity: dungeon.getEntity()) {
			if(entity instanceof Business && entity.getX() == getX() && entity.getY() == getY()) {
				if(score >= 3 ) {	
					if(key == null) {
						Business b = (Business)entity;
						b.getWorld(dungeon);
						key = b.getAKey(dungeon);
						if(key != null) {
							score += -3;
							dungeon.addEntity(key);
							System.out.println("get a key");
							return true;
						}else {
							System.out.println("sold out");
							return false;
						}
					}else {
						System.out.println("You already got a key");
					}
				}else {
					System.out.println("no enough gold");
					return false;
				}
			}
		}
		return true;
		
		
	}
	
	public boolean getStart() {
		return start;
	}

	public boolean isAchieveGoal() {
		return AchieveGoal;
	}

	public void setAchieveGoal(boolean achieveGoal) {
		AchieveGoal = achieveGoal;
	}

}
