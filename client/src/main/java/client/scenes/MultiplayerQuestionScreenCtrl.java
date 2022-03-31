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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MultiplayerQuestionScreenCtrl extends QuestionClass  implements Initializable {
	private MainCtrl mainCtrl;
	private ServerUtils server;

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
	private TextField textFieldChat;


	@FXML
	private VBox vBox;

	private Player player;

	private int gameId;

	@Inject
	public MultiplayerQuestionScreenCtrl(MainCtrl mainCtrl, ServerUtils server) {
		this.server = server;
		this.mainCtrl = mainCtrl;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void sendMessage(){
		String message = textFieldChat.getText();
		server.send(this.createWebSocketURL(gameId),
				new MessageModel(message, player.getNickName()));
	}

	public void sendEmoji(ActionEvent event) {
		Node node = (Node) event.getSource();
		String emoji = (String) node.getUserData();
		server.send(this.createWebSocketURL(gameId),
				new MessageModel(emoji, player.getNickName()));
	}

	/**
	 * This method will insert the message into the chat box after the client
	 * sends the emojis to other clients.
	 * @param message
	 */
	public void updateMessage(String message) {

		if ("CRY".equals(message)) {
			updateImage("/emojis/CRY.png");
			return;
		} else if ("WOW".equals(message)) {
			updateImage("/emojis/WOW.png");
			return;
		} else if ("ANGRY".equals(message)) {
			updateImage("/emojis/ANGRY.png");
			return;
		} else if ("VICTORY".equals(message)) {
			updateImage("/emojis/VICTORY.png");
			return;
		}

		HBox hBox = new HBox();

		Text text = new Text(message);

		TextFlow textFlow = new TextFlow(text);

		textFlow.setStyle("-fx-color: rgb(239,242,255);"
				+ "-fx-background-color: rgb(15,125,242);"
				+ "-fx-background-radius: 20px");

		text.setFill(Color.color(0.934, 0.945, 0.996));

		hBox.getChildren().add(textFlow);

		textField.clear();

		Platform.runLater(() -> {
			vBox.getChildren().add(hBox);
		});
	}

	/**
	 * This method will insert the emojis into the chat box after the client
	 * sends the emojis to other clients.
	 * @param url the path of the image
	 */
	public void updateImage(String url) {
		Image image = new Image(url, 20, 20, false, true);
		ImageView imageView = new ImageView(image);
		HBox hBox = new HBox();
		TextFlow textFlow = new TextFlow(imageView);
		textFlow.setStyle("-fx-color: rgb(239,242,255)" +
				";-fx-background-color: rgb(15,125,242)" +
				";-fx-background-radius: 20px");
		hBox.getChildren().add(textFlow);
		textField.clear();
		Platform.runLater(() -> {
			vBox.getChildren().add(hBox);
		});
	}

	public void setServer(ServerUtils server) {
		this.server = server;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image angryImage = new Image("emojis/ANGRY.png",
				20,
				20,
				false,
				true);
		ImageView angry = new ImageView(angryImage);
		Image cryImage = new Image("emojis/CRY.png",
				20,
				20,
				false,
				true);
		ImageView cry = new ImageView(cryImage);
		Image victoryImage = new Image("emojis/VICTORY.png",
				20,
				20,
				false,
				true);
		ImageView victory = new ImageView(victoryImage);
		Image wowImage = new Image("emojis/WOW.png",
				20,
				20,
				false,
				true);
		ImageView wow = new ImageView(wowImage);
		firstEmoji.setGraphic(angry);
		secondEmoji.setGraphic(victory);
		thirdEmoji.setGraphic(cry);
		fourthEmoji.setGraphic(wow);
	}

	public void decreaseOtherPlayersTime() throws IOException {
		server.getConnection().send(new JokerMessage(JokerType.DECREASE));
	}

	/**
	 * This method shows the intermediate scene.
	 */
	@Override
	public void showIntermediateScene() {
		IntLeaderboardCtrl intLeaderboardCtrl =
				this.mainCtrl.getIntermediateLeaderboardCtrl();
		Stage primaryStage = this.mainCtrl.getPrimaryStage();

		intLeaderboardCtrl.setProgress(1f);
		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			intLeaderboardCtrl.decreaseProgress(0.25);
		}));

		primaryStage.setTitle("IntermediateScene");
		primaryStage.setScene(this.mainCtrl.getIntermediateLeaderboardScreen());

		// This timeline will execute on another thread - run the count-down timer.
		intLeaderboardCtrl.setProgress(1f);
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			try {
				this.mainCtrl.showQuestionScreen(false); // the timeline finish its cycle.
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
	public void answerReturn(ActionEvent event) {
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
	 * Setter for the gameId
	 * @param gameId the gameId
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public double getProgress() {
		return this.progress;
	}
}
