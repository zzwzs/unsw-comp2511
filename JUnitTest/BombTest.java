package unsw.JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import unsw.dungeon.Bomb;
import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.LightBomb;
import unsw.dungeon.Player;

public class BombTest {

	@Test
	public void test() {
		Dungeon dungeon = new Dungeon(4,4);
		
		Boulder boulder1 = new Boulder(dungeon,2,2);
		Boulder boulder2 = new Boulder(dungeon,1,2);
		Enemy enemy1 = new Enemy(2,1);
		Enemy enemy2 = new Enemy(2,0);
		Enemy enemy3 = new Enemy(1,0);
		Player player  = new Player(dungeon,1,1);
		LightBomb bomb = new LightBomb(2,1,dungeon);
		
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(enemy3);
		dungeon.addEntity(player);
		dungeon.addEntity(bomb);
		bomb.Explode();
		assertFalse(dungeon.getEntity().contains(boulder1));
		assertTrue(dungeon.getEntity().contains(boulder2));
		assertFalse(player.getStatus() == 1);
		assertFalse(dungeon.getEntity().contains(enemy1));
		assertFalse(dungeon.getEntity().contains(enemy2));
		assertTrue(dungeon.getEntity().contains(enemy3));
		
	}

}
