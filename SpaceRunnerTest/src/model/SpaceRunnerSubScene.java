package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class SpaceRunnerSubScene extends SubScene {

	private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final static String BACKGROUND_IMAGE = "model/resources/blue_panel.png";
	
	private boolean isHidden;
	
	// constructor
	public SpaceRunnerSubScene() {
		super(new AnchorPane(), 600, 400);
		prefWidth(600);
		prefHeight(400);
		isHidden = true;
		
		BackgroundImage image = new BackgroundImage(
				new Image(BACKGROUND_IMAGE, 600, 400, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		
		root2.setBackground(new Background(image));
		
		setLayoutX(1024);
		setLayoutY(180);
		
	}
	
	// it animates and sets the position of the subscene
	public void moveSubScene() {
		
		// this takes a duration and converts it to a liner transition animation
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		
		// setting this node for transition
		transition.setNode(this);
		
		if(isHidden) {
			
			// transition.setToX() always changes from the base X position 
			// here new X will be 1024 + (-676) = 348
			transition.setToX(-676);
			isHidden = false;
			
		} else {
			
			//here new X will be 1024 + 0 = 348
			transition.setToX(0);
			isHidden = true;
			
		}
		
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}
