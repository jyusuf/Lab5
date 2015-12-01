package poker.app.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

//import org.apache.commons.math3.util.Combinations;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import domain.CardDomainModel;
import enums.eGame;
import enums.eRank;
import enums.eSuit;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import poker.app.MainApp;
import pokerBase.Card;
import pokerBase.Deck;
import pokerBase.GamePlay;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Hand;
import pokerBase.Player;
import pokerBase.Rule;
import pokerEnums.eDrawAction;
import pokerEnums.eGameState;
public class PokerTableController {
	
	@FXML
	private Button btnToggle;
	boolean bPlay = false;
	boolean bP1Sit = false;
	boolean bP2Sit = false;
	boolean bP3Sit = false;
	boolean bP4Sit = false;
	
	@FXML
    private void GetToggleGroup() {

    	mainApp.getToggleGroup();
    	
    }

	// Reference to the main application.
	private MainApp mainApp;
	private GamePlay gme = null;
	private int iCardDrawn = 0;
	private int iCardDrawnPlayer = 0;
	private int iCardDrawnCommon = 0;
	private int iDrawingCount = 0;
	private Player PlayerCommon = new Player("Common", 0);

	@FXML
	public AnchorPane APMainScreen;
	private ImageView imgTransCardP1 = new ImageView();
	private ImageView imgTransCardP2 = new ImageView();
	private ImageView imgTransCardP3 = new ImageView();
	private ImageView imgTransCardP4 = new ImageView();
	private ImageView imgTransCardCommon = new ImageView();
	private ImageView imgTransCard = new ImageView();

	@FXML
	public HBox HboxCommonArea;

	@FXML
	public HBox HboxCommunityCards;

	@FXML
	public HBox hBoxP1Cards;
	@FXML
	public HBox hBoxP2Cards;
	@FXML
	public HBox hBoxP3Cards;
	@FXML
	public HBox hBoxP4Cards;

	

	@FXML
	public TextField txtP1Name;
	@FXML
	public TextField txtP2Name;
	@FXML
	public TextField txtP3Name;
	@FXML
	public TextField txtP4Name;

	
	@FXML
	public Label lblP1Name;
	@FXML
	public Label lblP2Name;
	@FXML
	public Label lblP3Name;
	@FXML
	public Label lblP4Name;

	@FXML
	public ToggleButton btnP1SitLeave;
	@FXML
	public ToggleButton btnP2SitLeave;
	@FXML
	public ToggleButton btnP3SitLeave;
	@FXML
	public ToggleButton btnP4SitLeave;	

	@FXML
	public Button btnDraw;

	@FXML
	public Button btnPlay;

	public PokerTableController() {
	}

	
	@FXML
	private void initialize() {
	}

	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	@FXML
	private void handleP1SitLeave() {		
		int iPlayerPosition = 1;
		btnP1SitLeave.setDisable(true);
		bP1Sit = handleSitLeave(bP1Sit, iPlayerPosition, lblP1Name, txtP1Name, btnP1SitLeave, hBoxP1Cards);
		btnP1SitLeave.setDisable(false);
	}

	@FXML
	private void handleP2SitLeave() {		
		int iPlayerPosition = 2;
		btnP2SitLeave.setDisable(true);
		bP2Sit = handleSitLeave(bP2Sit, iPlayerPosition, lblP2Name, txtP2Name, btnP2SitLeave, hBoxP2Cards);
		btnP2SitLeave.setDisable(false);
	}
	
	@FXML
	private void handleP3SitLeave() {		
		int iPlayerPosition = 3;
		btnP3SitLeave.setDisable(true);
		bP3Sit = handleSitLeave(bP3Sit, iPlayerPosition, lblP3Name, txtP3Name, btnP3SitLeave, hBoxP3Cards);
		btnP3SitLeave.setDisable(false);
	}
	
	@FXML
	private void handleP4SitLeave() {		
		int iPlayerPosition = 4;
		btnP4SitLeave.setDisable(true);
		bP4Sit = handleSitLeave(bP4Sit, iPlayerPosition, lblP4Name, txtP4Name, btnP4SitLeave, hBoxP4Cards);
		btnP4SitLeave.setDisable(false);
	}

	private boolean handleSitLeave(boolean bSit, int iPlayerPosition, Label lblPlayer, TextField txtPlayer,
			ToggleButton btnSitLeave, HBox HBoxPlayerCards) {
		if (bSit == false) {
			Player p = new Player(txtPlayer.getText(), iPlayerPosition);
			mainApp.AddPlayerToTable(p);
			lblPlayer.setText(txtPlayer.getText());
			lblPlayer.setVisible(true);
			btnSitLeave.setText("Leave");
			txtPlayer.setVisible(false);
			bSit = true;
		} else {
			mainApp.RemovePlayerFromTable(iPlayerPosition);
			btnSitLeave.setText("Sit");
			txtPlayer.setVisible(true);
			lblPlayer.setVisible(false);
			HBoxPlayerCards.getChildren().clear();
			bSit = false;
		}

		return bSit;
	}
	
	
	
	@FXML
	private void handlePlay() {

		
		ImageView imgBottom = new ImageView(
				new Image(getClass().getResourceAsStream("/res/img/b1fh.png"), 75, 75, true, true));

		HboxCommonArea.getChildren().clear();
		HboxCommonArea.getChildren().add(imgBottom);
		HboxCommunityCards.getChildren().clear();
		
		// Clear all players hands
		hBoxP1Cards.getChildren().clear();
		hBoxP2Cards.getChildren().clear();
		hBoxP3Cards.getChildren().clear();
		hBoxP4Cards.getChildren().clear();
		
		// Get the Rule, start the Game
		Rule rle = new Rule(eGame.FiveStud);
		gme = new GamePlay(rle);

		eGameState egamestate = eGameState.StartGame;
		
		// Add the seated players to the game
		for (Player p : mainApp.GetSeatedPlayers()) {
			gme.addPlayerToGame(p);
			GamePlayPlayerHand GPPH = new GamePlayPlayerHand();
			GPPH.setGame(gme);
			GPPH.setPlayer(p);
			GPPH.setHand(new Hand());
			gme.addGamePlayPlayerHand(GPPH);
		}

		// Add a deck to the game
		gme.setGameDeck(new Deck());

		btnDraw.setVisible(true);
		iCardDrawn = 0;

		String strCard = "/res/img/b1fv.png";

		for (int i = 0; i < gme.getNbrOfCards(); i++) {
			ImageView img = new ImageView(new Image(getClass().getResourceAsStream(strCard), 75, 75, true, true));

			hBoxP1Cards.getChildren().add(img);
		}

		ImageView imgBottomCard = new ImageView(
				new Image(getClass().getResourceAsStream("/res/img/b2fh.png"), 75, 75, true, true));

		HboxCommonArea.getChildren().clear();
		HboxCommonArea.getChildren().add(imgBottomCard);

	}
	

	@FXML
	private void handleDraw() {
		iCardDrawn++;
		iDrawingCount++;
		ImageView imView = null;
		eGameState egamestate= eGameState.PlayGame;

		//  Disable the button in case of double-click
		btnDraw.setDisable(true);
		
		// Draw a card for each player seated
		for (Player p : mainApp.GetSeatedPlayers()) {
			Card c = gme.getGameDeck().drawFromDeck();

			if (p.getiPlayerPosition() == 1) {
				GamePlayPlayerHand GPPH = gme.FindPlayerGame(gme, p);
				GPPH.addCardToHand(c);

				//	This is the card that is going to be dealt to the player.
				String strCard = "/res/img/" + c.getCardImg();
				ImageView imgvCardDealt = new ImageView(new Image(getClass().getResourceAsStream(strCard), 96, 71, true, true));
				
				// imgvCardFaceDown - There's already a place holder card sitting in the player's hbox.  It's face down.  Find it
				// and then determine it's bounds and top left hand handle. 				
				ImageView imgvCardFaceDown = (ImageView) hBoxP1Cards.getChildren().get(iCardDrawn - 1);			
				Bounds bndCardDealt = imgvCardFaceDown.localToScene(imgvCardFaceDown.getBoundsInLocal());
				Point2D pntCardDealt = new Point2D(bndCardDealt.getMinX(), bndCardDealt.getMinY());
				
				//	imgvDealerDeck = the card in the common area, where dealer's card is located.  Find the boundary top left point.
				ImageView imgvDealerDeck = (ImageView) HboxCommonArea.getChildren().get(0);
				Bounds bndCardDeck = imgvDealerDeck.localToScene(imgvDealerDeck.getBoundsInLocal());
				Point2D pntCardDeck = new Point2D(bndCardDeck.getMinX(), bndCardDeck.getMinY());

				//	Add a sequential transition to the card (move, rotate)
				SequentialTransition transMoveRotCard = createTransition(pntCardDeck, pntCardDealt);

	
				//	Add a parallel transition to the card (fade in/fade out).
				final ParallelTransition transFadeCardInOut = createFadeTransition(imgvCardFaceDown, new Image(getClass().getResourceAsStream(strCard), 75, 75, true, true));

				
				transMoveRotCard.onFinishedProperty().set(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {

						//	get rid of the created card, run the fade in/fade out transition
						//	This isn't going to fire until the transMoveRotCard is complete.
						APMainScreen.getChildren().remove(imgTransCard);
						transFadeCardInOut.play();
						
						//	Enable the draw button after the animation is done.
						btnDraw.setDisable(false);
				}
				});

				transMoveRotCard.play();


				//	This is hard coded for five card stud... what to do AFTER the fifth card is dealt...  this should probably change to
				//  a switch statement (switching on game played, card #, etc).
				if (iCardDrawn == 5) {
					Hand h = GPPH.getHand();
					h = Hand.EvalHand(h);
					GPPH.setHand(h);
					System.out.println(GPPH.getHand().getHandStrength());
				}
			}
		}

		if (iCardDrawn == 5) {

			btnDraw.setVisible(false);
		}
		
		

	}

	

	private SequentialTransition createTransition(final Point2D pntStartPoint, final Point2D pntEndPoint) {


		imgTransCard = new ImageView(
				new Image(getClass().getResourceAsStream("/res/img/b2fh.png"), 75, 75, true, true));

		imgTransCard.setX(pntStartPoint.getX());
		imgTransCard.setY(pntStartPoint.getY()-30);

		APMainScreen.getChildren().add(imgTransCard);

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), imgTransCard);
		translateTransition.setFromX(0);
		translateTransition.setToX(pntEndPoint.getX() - pntStartPoint.getX());
		translateTransition.setFromY(0);
		translateTransition.setToY(pntEndPoint.getY() - pntStartPoint.getY());

		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);

		int rnd = randInt(1,6);
		
		System.out.println(rnd);
		
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), imgTransCard);
		rotateTransition.setByAngle(90F);
		rotateTransition.setCycleCount(rnd);
		rotateTransition.setAutoReverse(false);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition, rotateTransition);

		
		SequentialTransition seqTrans = new SequentialTransition();
		seqTrans.getChildren().addAll(parallelTransition);		

		return seqTrans;
	}
	
	
	private ParallelTransition createFadeTransition(final ImageView iv,  final Image img) {

		FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(.25), iv);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				iv.setImage(img);
				;
			}

		});

		FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(.25), iv);
		fadeInTransition.setFromValue(0.0);
		fadeInTransition.setToValue(1.0);


		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeOutTransition, fadeInTransition);

		return parallelTransition;
	}	
	
	
	
	private static int randInt(int min, int max) {

		return (int) (Math.random() * ( min - max )) * -1;


	}
	
}
