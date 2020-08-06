package unsw.JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import unsw.dungeon.Bomb;
import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;
import unsw.dungeon.Treasure;
import unsw.dungeon.Wall;
import unsw.dungeon.Key;

public class PlayerTest {
	@Test
	public void test() {
		Dungeon dungeon = new Dungeon(6,5);
		Player player = new Player(dungeon,3,2);
		Potion potion = new Potion(1,2);
		Boulder boulder = new Boulder(dungeon,2,1);
		Exit exit = new Exit(2,4);
		Wall wall = new Wall(3,1);
		Bomb bomb = new Bomb(4,1,dungeon);
		Treasure treasure = new Treasure(4,3);
		Key key = new Key(5,2,0);
		Door door = new Door(3,4,0);
		
		dungeon.addEntity(player);
		dungeon.addEntity(potion);
		dungeon.addEntity(boulder);
		dungeon.addEntity(exit);
		dungeon.addEntity(wall);
		dungeon.addEntity(key);
		dungeon.addEntity(bomb);
		dungeon.addEntity(treasure);
		dungeon.addEntity(door);
		
		player.moveUp();
		assertEquals(3,player.getX());
		assertEquals(2,player.getY());
		
		player.moveLeft();
		player.moveLeft();
		assertEquals(1,player.getStatus());
		
		player.moveRight();
		player.moveUp();
		assertEquals(2,boulder.getX());
		assertEquals(0,boulder.getY());
		
		player.moveDown();
		player.moveDown();
		player.moveDown();
		assertTrue(player.getExit());
		
		player.moveRight();
		assertEquals(2,player.getX());
		assertEquals(4,player.getY());
		
		player.moveUp();
		player.moveRight();
		player.moveRight();
		assertEquals(1,player.getScore());
		
		player.moveUp();
		player.moveUp();
		assertEquals(1,player.getBombs());
		
		player.moveDown();
		player.moveRight();
		assertEquals(0,player.getKey().getID());
		
		player.moveDown();
		player.moveDown();
		player.moveLeft();
		player.moveLeft();
		assertEquals(3,player.getX());
		assertEquals(4,player.getY());
		assertEquals(null,player.getKey());
		assertEquals("open",door.getStatus());
	}
}
