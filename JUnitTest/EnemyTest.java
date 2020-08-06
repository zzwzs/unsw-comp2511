package unsw.JUnitTest;

import static org.junit.Assert.*;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;

public class EnemyTest {

	@Test
	public void test1() {
		Dungeon dungeon = new Dungeon(3,3);
		Enemy enemy = new Enemy(1,1);
		Player player = new Player(dungeon,1,0);
		dungeon.addEntity(enemy);
		dungeon.addEntity(player);
		enemy.enemyMove(1, 0, player);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				assertEquals(1, enemy.getX());
				assertEquals(0, enemy.getY());
			}
		}, 2000);
	}
	
	@Test
	public void test2() {
		Dungeon dungeon = new Dungeon(3,3);
		Enemy enemy = new Enemy(1,2);
		Wall wall = new Wall(1,1);
		Player player = new Player(dungeon,1,0);
		dungeon.addEntity(enemy);
		dungeon.addEntity(wall);
		dungeon.addEntity(player);
		dungeon.setPlayer(player);
		enemy.move(1,0,dungeon);
		assertEquals(1, enemy.getX());
		assertEquals(2, enemy.getY());
	}
}
