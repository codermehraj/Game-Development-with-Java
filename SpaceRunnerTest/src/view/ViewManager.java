package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

public class ViewManager {
	
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;
	
	List < SpaceRunnerButton > menuButtons;
	
	public ViewManager() {
		
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		
		
		createSubScene();
		createButtons();
		createBackground();
		createLogo();		
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void createButtons() {
		menuButtons = new ArrayList < SpaceRunnerButton >();
		addMenuButton("PLAY");
		addMenuButton("SCORES");
		addMenuButton("HELP");
		addMenuButton("CREDITS");
		addMenuButton("EXIT");
		
	}
	
	private void addMenuButton(String buttonTitle) {
		SpaceRunnerButton button = new SpaceRunnerButton(buttonTitle);
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createBackground() {
		
		Image backgroundImage = new Image("view/resources/purpleBackground.png", 256, 256, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
		
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/logo.png");
		logo.setLayoutX(700);
		logo.setLayoutY(50);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
		});
		
		mainPane.getChildren().add(logo);		
		
	}
	
	private  void createSubScene() {
		SpaceRunnerSubScene subscene = new SpaceRunnerSubScene();
		subscene.setLayoutX(200);
		subscene.setLayoutY(100);
		mainPane.getChildren().add(subscene);
	}
}
