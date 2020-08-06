package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    
    private Timeline timeline;
    
    private Stage stage;
    
    private DungeonApplication application;
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Stage stage, DungeonApplication application) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.stage = stage;
        this.application = application;
    }

    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities) {
        	//System.out.println(entity.getX());
            squares.getChildren().add(entity);
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
        	player.moveUp();
        	removeEnemy();
            removeBoulder();
            remove();
            openDoor();
            break;
        case DOWN:
            player.moveDown();
            removeEnemy();
            removeBoulder();
            remove();
            openDoor();
            break;
        case LEFT:
        	player.moveLeft();
            removeEnemy();
            removeBoulder();
            remove();
            openDoor();
            break;
        case RIGHT:
            player.moveRight();
            removeEnemy();
            removeBoulder();
            remove();
            openDoor();
            break;
        case D:
        	player.drop();
        	remove();
        	break;
        case SPACE:
        	if(player.lightBomb()) {
	        	remove();
	        	removeBoulder();
	        	bombLighting();
        	}
        	break;
        case T:
        	if(player.trade()) {
        		setKeyImage();
        	}
        	break;
        case S:
        	player.start();
        	checkDie();
        	checkGoal();
        	break;
        default:
            break;
        }
    }
    
    
    
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
    
	private void setKeyImage() {
		for(Entity entity:dungeon.getEntity()) {
			if(entity.getY() == dungeon.getHeight()-1) {
				if(entity instanceof Key) {
					ImageView view = new ImageView(new Image("/key.png"));
	        		System.out.println(entity.getX());
		    		view.setId("key");
		            view.setX(entity.getX());
		            view.setY(entity.getY());
		            trackPosition(entity, view);
		            squares.getChildren().add(view);
				}
    		}
		}
		
	}
	
	public void openDoor() {
		for(Entity entity:dungeon.getEntity()) {
			if(entity instanceof Door) {
				Door d = (Door)entity;
				if(d.getStatus() =="open") {
					for(Node node: squares.getChildren()) {
						ImageView view = (ImageView)node;
						if(view.getId() != null) {
							if(view.getId().contentEquals("door") && GridPane.getColumnIndex(view) == d.getX() && GridPane.getRowIndex(view) == d.getY()) {
								view.setImage(new Image("/open_door.png"));
							}
						}
					}
				}
			}
		}
	}
	
	@FXML
    public boolean removeEnemy(){
    	for(Node node: squares.getChildren()) {
    		ImageView view = (ImageView)node;
    		if(view.getId() != null) {
    			for(Entity entity: dungeon.getEntity()) {
		    		if(entity instanceof Enemy && view.getId().contentEquals("enemy") && GridPane.getColumnIndex(view) == entity.getX() && GridPane.getRowIndex(view) == entity.getY()) {
		    			Enemy e = (Enemy) entity;
		    			//System.out.println(e.getStatus());
		    			if(!e.getStatus()) {
		    				dungeon.removeEntity(entity);
		    				squares.getChildren().remove(view);
		    				return true;
		    			}
		    		}
	    		}
	    	}
    	}
    	return false;
    }
    
    public boolean removeBoulder(){
    	for(Node node: squares.getChildren()) {
    		ImageView view = (ImageView)node;
    		if(view.getId() != null) {
    			for(Entity entity: dungeon.getEntity()) {
		    		if(entity instanceof Boulder && view.getId().contentEquals("boulder") && GridPane.getColumnIndex(view) == entity.getX() && GridPane.getRowIndex(view) == entity.getY()) {
		    			Boulder b = (Boulder) entity;
		    			if(b.isDestroyed()) {
		    				//System.out.println(1);
		    				dungeon.removeEntity(entity);
		    				squares.getChildren().remove(view);
		    				return true;
		    			}
		    		}
	    		}
	    	}
    	}
    	return false;
    }
    
    public boolean remove() {
    	for(Node node: squares.getChildren()) {
    		ImageView view = (ImageView)node;
    		for(Remove remove: player.getRemoves()) {
    			if(view.getId() != null) {
	    			if(GridPane.getColumnIndex(view) == remove.getX() && GridPane.getRowIndex(view) == remove.getY() && view.getId().contentEquals(remove.getName())) {
	    				squares.getChildren().remove(view);
	    				player.deleteRemoves(remove);
	    				return true;
	    			}
    			}
    		}
    	}
    	return false;
    }
    
    public void bombLighting() {
    	for (Node node: squares.getChildren()) {
    		ImageView view = (ImageView) node;
    		if (view.getId() != null) {
        		if (view.getId().contentEquals("bomb") && GridPane.getColumnIndex(view) == player.getX() && GridPane.getRowIndex(view) == player.getY()) {
        			view.setImage(new Image("/bomb_lit_1.png"));
        			Exploding(view);
        		}
    		}
    	}
    }

    public void Exploding(ImageView view) {
    	Timeline timeline1 = new Timeline();
    	Timeline timeline2 = new Timeline();
    	Timeline timeline3 = new Timeline();
    	Timeline timeline4 = new Timeline();
    	Duration t1 = Duration.millis(1000);
    	Duration t2 = Duration.millis(2000);
    	Duration t3 = Duration.millis(2500);
		EventHandler<ActionEvent> handler1 = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			view.setImage(new Image("/bomb_lit_2.png"));
    		}
    	};
    	
    	EventHandler<ActionEvent> handler2 = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			view.setImage(new Image("/bomb_lit_3.png"));
    		}
    	};
    	
    	EventHandler<ActionEvent> handler3 = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			removeEnemy();
    			removeBoulder();
    			view.setImage(new Image("/bomb_lit_4.png"));
    		}
    	};
    	
    	EventHandler<ActionEvent> handler4 = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			squares.getChildren().remove(view);
    		}
    	};
    	
    	KeyFrame f1 = new KeyFrame(t1, handler1);
    	KeyFrame f2 = new KeyFrame(t1, handler2);
    	KeyFrame f3 = new KeyFrame(t1, handler3);
    	KeyFrame f4 = new KeyFrame(t1, handler4);
    	timeline1.getKeyFrames().add(f1);
    	timeline2.getKeyFrames().add(f2);
    	timeline3.getKeyFrames().add(f3);
    	timeline4.getKeyFrames().add(f4);
    	timeline1.play();
    	timeline2.setDelay(t1);
    	timeline2.play();
    	timeline3.setDelay(t2);
    	timeline3.play();
    	timeline4.setDelay(t3);
    	timeline4.play();
    }

    public boolean checkDie() {
    	Timeline timelinec = new Timeline();
    	timelinec.setCycleCount(Timeline.INDEFINITE);
    	Duration check = Duration.millis(200);
    	
    	EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if(player.getAlive() == 1) {
    				checks();
    				removeArcher();
    			}else {
    				Dead();
    				timelinec.stop();
    			}
    		}
    	};
    	
    	KeyFrame f1 = new KeyFrame(check, handler);
    	timelinec.getKeyFrames().add(f1);
    	timelinec.play();
    	return true;
    }
    
    public void checks() {
    	for(Entity entity:dungeon.getEntity()) {
    		if(entity instanceof Archer) {
    			Archer a = (Archer)entity;
    			if((player.getX() >= a.getX()-1 && player.getX() <= a.getX()+1 && player.getY() >= a.getY()-1 && player.getY() <= a.getY()+1) && (a.getX() == player.getX() || a.getY() == player.getY())) {
    				if(player.getStatus() == 0) {
    					player.die();
    					System.out.println("Killed by archer");
    				}else {
    					a.setAlive(false);
    				}
    			}
    		}
    	}
    }
    
    public void removeArcher() {
    	for(Entity entity:dungeon.getEntity()) {
    		if(entity instanceof Archer) {
    			Archer a = (Archer)entity;
    			if(!a.isAlive()) {
    				for(Node node:squares.getChildren()) {
    					ImageView view = (ImageView)node;
    					if(view.getId() != null) {
    						if(view.getId().contentEquals("archer") && GridPane.getColumnIndex(view) == a.getX() && GridPane.getRowIndex(view) == a.getY()) {
    							squares.getChildren().remove(view);
    							break;
    						}
    					}
    				}
    				dungeon.removeEntity(a);
    				break;
    			}
    		}
    	}
    }
    
    public void Dead() {
    	Stage dialogStage = new Stage();
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	Button b1 = new Button("Death is not the end");
    	Button b2 = new Button("Fall into death");
    	b1.addEventHandler(MouseEvent.MOUSE_CLICKED, 
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		            dialogStage.close();
    		            stage.close();
    		            Stage primaryStage = new Stage();
    		            try {
							application.start(primaryStage);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    		        }
    		});
    	b2.addEventHandler(MouseEvent.MOUSE_CLICKED, 
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	stage.close();
    		        	dialogStage.close();
    		        	System.exit(0);
    		        }
    	});

    	
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(new Text("Game Over!"),b1,b2);
    	

    	vbox.setAlignment(Pos.CENTER);
    	vbox.setPadding(new Insets(60));
    	dialogStage.setScene(new Scene(vbox));
    	dialogStage.show();
    }
    
    public void achieveGoal() {
    	Stage dialogStage = new Stage();
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	Button b1 = new Button("Rebirth");
    	b1.addEventHandler(MouseEvent.MOUSE_CLICKED, 
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		            dialogStage.close();
    		            stage.close();
    		            Stage primaryStage = new Stage();
    		            try {
							application.start(primaryStage);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    		        }
    		});
    	Button b2 = new Button("Success");
    	b2.addEventHandler(MouseEvent.MOUSE_CLICKED, 
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	stage.close();
    		        	dialogStage.close();
    		        	System.exit(0);
    		        }
    		});
    	
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(new Text("Achieve Goal"),b1,b2);
    	

    	vbox.setPadding(new Insets(60));
    	vbox.setAlignment(Pos.CENTER);
    	dialogStage.setScene(new Scene(vbox));
    	dialogStage.show();
    }
    
    public void checkGoal() {
    	Timeline timelineG = new Timeline();
    	Duration time = Duration.millis(200);
    	timelineG.setCycleCount(timeline.INDEFINITE);
    	
    	EventHandler <ActionEvent> handle = new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if(player.isAchieveGoal()) {
		    		achieveGoal();
		    		timelineG.stop();
		    	}
			}    		
    	};
    	
    	KeyFrame f = new KeyFrame(time, handle);
    	timelineG.getKeyFrames().add(f);
    	timelineG.play();
    }
    
    public void checkPlayerAlive() {
    	if(player.getAlive() == 0) {
    		Dead();
    	}
    }
    
}

