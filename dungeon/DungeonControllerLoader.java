package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image bomb_lit_1;			//exploding
    private Image bomb_lit_2;
    private Image bomb_lit_3;
    private Image bomb_lit_4;			//explosion
    private Image bomb_unlit;			//entity bomb
    private Image boulder;
    private Image exitImage;			//exit(not door)
    private Image gold_pileImage;		//gold
    private Image greatswordImage;		//sword
    private Image brilliant_blueImage, bubbly;	//blue potion, green potion
    private Image closed_door, open_door; //open and close door
    private Image deep_elf_master_archer, hound, gnome; //enemy
    private Image dirt;					  //floor
    private Image key;
    private Image plate;
    private Image backpackImage;


    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        wallImage = new Image("/brick_brown_0.png");
        bomb_lit_1 = new Image("/bomb_lit_1.png");
        bomb_lit_2 = new Image("/bomb_lit_2.png");
        bomb_lit_3 = new Image("/bomb_lit_3.png");
        bomb_lit_4 = new Image("/bomb_lit_4.png");
        bomb_unlit = new Image("/bomb_unlit.png");
        boulder = new Image("/boulder.png");
        exitImage = new Image("/exit.png");
        gold_pileImage = new Image("/gold_pile.png");
        greatswordImage = new Image("/greatsword_1_new.png");
        brilliant_blueImage = new Image("/brilliant_blue_new.png");
        bubbly = new Image("/bubbly.png");
        closed_door = new Image("/closed_door.png");
        open_door = new Image("/open_door.png");
        deep_elf_master_archer = new Image("/deep_elf_master_archer.png");
        hound = new Image("/hound.png");
        gnome = new Image("/gnome.png");
        dirt = new Image("/dirt_0_new.png");
        key = new Image("/key.png");
        plate = new Image("/pressure_plate.png");
        backpackImage = new Image("/backpack.png");
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        view.setId("player");
        view.setX(player.getX());
        view.setY(player.getY());
        addEntity(player, view);
    }
    
    @Override
    public void onLoad(Business business) {
        ImageView view = new ImageView(gnome);
        view.setId("business");
        view.setX(business.getX());
        view.setY(business.getY());
        addEntity(business, view);
    }
    
    @Override
    public void onLoad(Switch plates) {
        ImageView view = new ImageView(plate);
        view.setX(plates.getX());
    	view.setY(plates.getY());
    	view.setId("pressure_plate");
        addEntity(plates, view);
    }
    
    @Override
    public void onLoad(Dirt dirts) {
        ImageView view = new ImageView(dirt);
        addEntity(dirts, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        view.setId("wall");
        view.setX(wall.getX());
        view.setY(wall.getY());
        addEntity(wall, view);
    }
    
    @Override
    public void onLoad(Bomb bomb) {								//think how to change bomb to another type
        ImageView view = new ImageView(bomb_unlit);
        view.setX(bomb.getX());
    	view.setY(bomb.getY());
    	view.setId("bomb");
        addEntity(bomb, view);
    }
    
    @Override
    public void onLoad(LightBomb bomb) {								//think how to change bomb to another type
        ImageView view = new ImageView(bomb_lit_1);
        view.setX(bomb.getX());
        view.setY(bomb.getY());
        view.setId("LightBomb");
        addEntity(bomb, view);
    }
    
    @Override
    public void onLoad(Boulder boulders) {
        ImageView view = new ImageView(boulder);
        view.setX(boulders.getX());
    	view.setY(boulders.getY());
    	view.setId("boulder");
        addEntity(boulders, view);
    }
    
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        view.setId("exit");
        view.setX(exit.getX());
        view.setX(exit.getY());
        addEntity(exit, view);
    }
    
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(hound);
        view.setX(enemy.getX());
    	view.setY(enemy.getY());
    	view.setId("enemy");
        addEntity(enemy, view);
    }
    
    @Override
    public void onLoad(Archer archer) {
        ImageView view = new ImageView(deep_elf_master_archer);
        view.setX(archer.getX());
    	view.setY(archer.getY());
    	view.setId("archer");
        addEntity(archer, view);
    }
    
    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(brilliant_blueImage);
        view.setId("potion");
        view.setX(potion.getX());
        view.setY(potion.getY());
        addEntity(potion, view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(gold_pileImage);
        view.setId("treasure");
        view.setX(treasure.getX());
        view.setY(treasure.getY());
        addEntity(treasure, view);
    }
    
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(greatswordImage);
        view.setX(sword.getX());
        view.setY(sword.getY());
        view.setId("sword");
        addEntity(sword, view);
    }
    
    @Override
    public void onLoad(Key keys) {
        ImageView view = new ImageView(key);
        view.setX(keys.getX());
    	view.setY(keys.getY());
    	view.setId("key");
        addEntity(keys, view);
    }
    
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(closed_door);
        view.setX(door.getX());
    	view.setY(door.getY());
    	view.setId("door");
        addEntity(door, view);
    }
    
	@Override
	public void onLoad(Backpack backpack) {
		// TODO Auto-generated method stub
		ImageView view = new ImageView(backpackImage);
        view.setX(backpack.getX());
    	view.setY(backpack.getY());
    	view.setId("backpack");
        addEntity(backpack, view);
	}

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */

    public DungeonController loadController(Stage stage, DungeonApplication dungeonApplication) throws FileNotFoundException {
        return new DungeonController(load(), entities,stage,dungeonApplication);
    }


}
