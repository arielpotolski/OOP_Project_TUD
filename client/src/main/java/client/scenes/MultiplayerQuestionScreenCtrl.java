package client.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.MessageModel;
import commons.Player;
import commons.messages.JokerMessage;
import commons.messages.JokerType;

import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MultiplayerQuestionScreenCtrl extends QuestionClass  implements Initializable {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

	@FXML
	public Pane reactionPane;

	@FXML
	Button firstEmoji;

	@FXML
	Button secondEmoji;

	@FXML
	Button thirdEmoji;

	@FXML
	Button fourthEmoji;

	@FXML
	Button sendButton;

	@FXML
	Pane doublePointsPane;

	@FXML
	private TextField textFieldChat;

	@FXML
	private VBox vBox;

	private Player player;

	private int gameId;

	@Inject
	public MultiplayerQuestionScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = mainCtrl.getServer();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void sendMessage() {
		String message = this.textFieldChat.getText();
		this.server.send(
			this.createWebSocketURL(this.gameId),
			new MessageModel(message, this.player.getNickName())
		);
	}

	public void sendEmoji(ActionEvent event) {
		Node node = (Node) event.getSource();
		String emoji = (String) node.getUserData();
		this.server.send(
			this.createWebSocketURL(this.gameId),
			new MessageModel(emoji, this.player.getNickName())
		);
	}

	/**
	 * This method will insert the message into the chat box after the client
	 * sends the emojis to other clients.
	 * @param message The message to send.
	 */
	public void updateMessage(String message) {
		switch (message) {
		case "CRY":
		case "WOW":
		case "ANGRY":
		case "VICTORY":
			this.updateImage("/emojis/" + message + ".png");
			return;
		}

		HBox hBox = new HBox();
		Text text = new Text(message);
		TextFlow textFlow = new TextFlow(text);
		textFlow.setStyle(
			"-fx-color: rgb(239,242,255);"
				+ "-fx-background-color: rgb(15,125,242);"
				+ "-fx-background-radius: 20px"
		);

		text.setFill(Color.color(0.934, 0.945, 0.996));
		hBox.getChildren().add(textFlow);
		this.textField.clear();
		Platform.runLater(() -> this.vBox.getChildren().add(hBox));
	}

	/**
	 * This method will insert the emojis into the chat box after the client
	 * sends the emojis to other clients.
	 * @param url The path of the image.
	 */
	public void updateImage(String url) {
		Image image = new Image(url, 20, 20, false, true);
		ImageView imageView = new ImageView(image);
		HBox hBox = new HBox();
		TextFlow textFlow = new TextFlow(imageView);
		textFlow.setStyle(
			"-fx-color: rgb(239,242,255)"
			+ ";-fx-background-color: rgb(15,125,242)"
			+ ";-fx-background-radius: 20px"
		);
		hBox.getChildren().add(textFlow);
		this.textField.clear();
		Platform.runLater(() -> this.vBox.getChildren().add(hBox));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image angryImage = new Image("emojis/ANGRY.png", 20, 20, false, true);
		ImageView angry = new ImageView(angryImage);
		Image cryImage = new Image("emojis/CRY.png", 20, 20, false, true);
		ImageView cry = new ImageView(cryImage);
		Image victoryImage = new Image("emojis/VICTORY.png", 20, 20, false, true);
		ImageView victory = new ImageView(victoryImage);
		Image wowImage = new Image("emojis/WOW.png", 20, 20, false, true);
		ImageView wow = new ImageView(wowImage);
		this.firstEmoji.setGraphic(angry);
		this.secondEmoji.setGraphic(victory);
		this.thirdEmoji.setGraphic(cry);
		this.fourthEmoji.setGraphic(wow);
	}

	public void decreaseOtherPlayersTime() throws IOException {
		this.server.getConnection().send(new JokerMessage(JokerType.DECREASE));
	}

	/**
	 * This method shows the intermediate scene.
	 */
	@Override
	public void showIntermediateScene() {
		IntLeaderboardCtrl intLeaderboardCtrl = this.mainCtrl.getIntermediateLeaderboardCtrl();
		Stage primaryStage = this.mainCtrl.getPrimaryStage();

		intLeaderboardCtrl.setProgress(1f);
		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e ->
			intLeaderboardCtrl.decreaseProgress(0.25)
		));

		intLeaderboardCtrl.displayScores();

		primaryStage.setTitle("Intermediate Scene");
		primaryStage.setScene(this.mainCtrl.getIntermediateLeaderboardScreen());

		// This timeline will execute on another thread - run the count-down timer.
		intLeaderboardCtrl.setProgress(1f);
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			try {
				this.mainCtrl.showQuestionScreen(false); // The timeline finish its cycle.
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 * @param event The player click on the button.
	 */
	@Override
	public void answerReturn(ActionEvent event) throws IOException {
		super.answerReturn(event);
	}

	@Override
	public Player getPlayer() {
		return this.mainCtrl.getMultiplayerPreGameCtrl().getPlayer();
	}

	@Override
	public Scene getScene() {
		return this.mainCtrl.getMultiplayerQuestionScreen();
	}

	@Override
	public void showFinalScreen() {
		this.mainCtrl.showMultiPlayerFinalScreen();
	}

	private String createWebSocketURL(Integer gameId) {
		return "/app/chat/" + gameId.toString();
	}

	/**
	 * Setter for the gameId.
	 * @param gameId The gameId.
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public double getProgress() {
		return this.progress;
	}

	/**
	 * Hides the jokerPane.
	 * @param jokerPane The joker pane to be hidden.
	 */
	public void hideJoker(Pane jokerPane) {
		jokerPane.setVisible(false);
	}

	/**
	 * Uses the joker.
	 */
	public void useDoublePoints() {
		this.hideJoker(this.doublePointsPane);
		this.mainCtrl.setDoublePointsUsed(1);
	}

	/**
	 * Sets up all the joker panes.
	 */
	public void setAllJokersUp() {
		// TODO for all other jokers set everything visible
		this.doublePointsPane.setVisible(true);
		this.mainCtrl.setDoublePointsUsed(0);
	}
}