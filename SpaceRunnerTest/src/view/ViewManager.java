package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
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
	private SpaceRunnerSubScene playSubScene, scoreSubScene, helpSubScene, creditsSubscene, sceneToHide;
	
	List<ShipPicker> shipList;
	private SHIP chosenShip;
	
	public ViewManager() {
		
		// Creating window using Stage Class
		mainStage = new Stage();
		
		// Setting layout of Scene
		mainPane = new AnchorPane();
		
		// Creating a Scene for the Stage
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		
		// Setting the scene
		mainStage.setScene(mainScene);
		
		// creating elements of the main menu
	
		createBackground(); 
		createLogo();
		createButtons();
		// sub-scenes for each button
		createSubScene(); 
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
	
	// hides previous sub scene and shows current sub scene
	// it animates the process using SubScene.moveSubScene()
	private void showSubScene(SpaceRunnerSubScene subscene) {
		if(sceneToHide != null) sceneToHide.moveSubScene();
		subscene.moveSubScene();
		sceneToHide = subscene;
	}
	
	private void addMenuButton(String buttonTitle) {
		
		SpaceRunnerButton button = new SpaceRunnerButton(buttonTitle);
		
		button.setLayoutX(MENU_BUTTON_START_X);
		
		// menuButtons.size() = number of button added to the scene
		// by multiplying number of buttons by 100 we get the Y coordinate of the new buttons 
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
		
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		
		button.setOnAction(new EventHandler < ActionEvent >() {

			@Override
			public void handle(ActionEvent event) {
				// adding different sub-scenes accordingly
				if(buttonTitle == "PLAY") showSubScene(playSubScene);
				else if(buttonTitle == "SCORES") showSubScene(scoreSubScene);
				else if(buttonTitle == "HELP") showSubScene(helpSubScene);
				else if(buttonTitle == "CREDITS") showSubScene(creditsSubscene);
				else if(buttonTitle == "EXIT") mainStage.close();
			}});
	}
	
	private void createBackground() {
		
		Image backgroundImage = new Image("view/resources/purpleBackground.png", 256, 256, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		
		// Setting the background for the Main Scene through AnchorPane Layout 
		mainPane.setBackground(new Background(background));
		
	}
	
	private void createLogo() {
		
		// The ImageView is a Node used for painting images loaded with Image class.
		ImageView logo = new ImageView("view/resources/logo.png");
		logo.setLayoutX(400);
		logo.setLayoutY(50);
		
		// If the mouse is hovered over the logo
		// Here Event Handler is passed as an anonymous class
		// So that we can override the handle() method
		logo.setOnMouseEntered(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		
		// If the mouse is exited hovering over the logo
		logo.setOnMouseExited(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
		});
		
		// Adding the ImageView Node to the scene through AnchorPane
		mainPane.getChildren().add(logo);		
		
	}
	
	private  void createSubScene() {
		
		scoreSubScene = new SpaceRunnerSubScene();
		helpSubScene = new SpaceRunnerSubScene();
		creditsSubscene = new SpaceRunnerSubScene();
		
		mainPane.getChildren().add(scoreSubScene);
		mainPane.getChildren().add(helpSubScene);
		mainPane.getChildren().add(creditsSubscene);
		
		
		createShipChooserSubScene();
	}
	
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton button = new SpaceRunnerButton("START");
		button.setLayoutX(350);
		button.setLayoutY(300);
		
		button.setOnAction(new EventHandler <ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				if(chosenShip != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, chosenShip);
				}
			}
			
		});
		
		return button;
	}
	
	private void createShipChooserSubScene() {
		playSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(playSubScene);
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		
		playSubScene.getPane().getChildren().add(chooseShipLabel);
		playSubScene.getPane().getChildren().add(createShipsToChoose());
		playSubScene.getPane().getChildren().add(createButtonToStart());
	}
	
	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipList = new ArrayList<>();
		for(SHIP ship: SHIP.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			shipList.add(shipToPick);
			box.getChildren().add(shipToPick);
			
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for(ShipPicker ship: shipList) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					chosenShip = shipToPick.getShip();
				}
				
			});
			//System.out.println(ship.urlShip);
		}
		box.setLayoutX(300 - (118 * 2));
		box.setLayoutY(100);
		
		return box;
	}
}
