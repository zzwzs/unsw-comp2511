package unsw.JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;
public class BouldersTest {

	@Test
	public void test() {
		Dungeon dungeon = new Dungeon(4,4);
		Boulder boulder1 = new Boulder(dungeon,2,1);
		Boulder boulder2 = new Boulder(dungeon,1,2);
		Boulder boulder3 = new Boulder(dungeon,0,2);
		Wall wall = new Wall(1,3);
		Player player = new Player(dungeon,1,1);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		dungeon.addEntity(boulder3);
		dungeon.addEntity(wall);
		dungeon.addEntity(player);
		
		player.moveRight();
		assertEquals(3,boulder1.getX());
		assertEquals(1,boulder1.getY());
		
		player.moveLeft();
		player.moveDown();
		assertEquals(1,boulder2.getX());
		assertEquals(2,boulder2.getY());
		
		player.moveRight();
		player.moveDown();
		player.moveLeft();
		assertEquals(1,boulder2.getX());
		assertEquals(2,boulder2.getY());
	}

}

