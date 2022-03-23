package client.scenes;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.MessageModel;
import commons.Player;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MultiplayerQuestionScreenCtrl implements Initializable {

	private MainCtrl mainCtrl;
	private ServerUtils server;

	@FXML
	private ProgressBar progressBarTime;

	@FXML
	private Button answerButton1;

	@FXML
	private Button answerButton2;

	@FXML
	private Button answerButton3;

	@FXML
	private Ellipse lessTimeJoker;

	@FXML
	private Ellipse doublePointsJoker;

	@FXML
	private Ellipse eliminateAnswerJoker;

	@FXML
	private ImageView imageQuestion;

	@FXML
	private ImageView imageFirst;

	@FXML
	private ImageView imageSecond;

	@FXML
	private ImageView imageThird;

	@FXML
	private Circle innerCircleEmoticon;

	@FXML
	private Circle innerCircleTutorial;

	@FXML
	private Pane reactionPane;

	@FXML
	ImageView firstEmoji;

	@FXML
	ImageView secondEmoji;

	@FXML
	ImageView thirdEmoji;

	@FXML
	ImageView fourthEmoji;

	@FXML
	Button sendButton;

	@FXML
	private TextField textField;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox vBox;

	private Player player;

	@Inject
	public MultiplayerQuestionScreenCtrl(MainCtrl mainCtrl, ServerUtils server){
		this.server = server;
		this.mainCtrl = mainCtrl;
	}

	public void setPlayer(Player player){
		this.player = player;
	}

	public void sendMessage(){
		String message = textField.getText();
		server.send("/app/chat",new MessageModel(message, player.getNickName()));
	}

	public void sendFirstEmoji() {
		server.send("/app/chat",new MessageModel("cry", player.getNickName()));
	}

	public void sendSecondEmoji() {
		server.send("/app/chat",new MessageModel("wow", player.getNickName()));
	}

	public void sendThirdEmoji() {
		server.send("/app/chat",new MessageModel("angry", player.getNickName()));
	}

	public void sendFourthEmoji() {
		server.send("/app/chat",new MessageModel("victory", player.getNickName()));
	}

	public void updateMessage(String message) {

		if (Objects.equals(message, "cry")){
			updateImage("/emojis/1f62d.png");
			return;
		} else if (Objects.equals(message, "wow")) {
			updateImage("/emojis/1f631.png");
			return;
		} else if (Objects.equals(message, "angry")) {
			updateImage("/emojis/1f621.png");
			return;
		} else if (Objects.equals(message, "victory")) {
			updateImage("/emojis/1f596-1f3fb.png");
			return;
		}
		HBox hBox = new HBox();
		Text text = new Text(message);
		TextFlow textFlow = new TextFlow(text);
		textFlow.setStyle("-fx-color: rgb(239,242,255)" +
				";-fx-background-color: rgb(15,125,242)" +
				";-fx-background-radius: 20px");
		text.setFill(Color.color(0.934,0.945,0.996));
		hBox.getChildren().add(textFlow);
		textField.clear();
		Platform.runLater(() -> {
			vBox.getChildren().add(hBox);
		});
	}

	public void updateImage(String url){
		Image image = new Image(url);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		server.registerForMessages("/topic/receive", MessageModel.class, messageModel -> {
			updateMessage(messageModel.getMessage());
		});
	}
	
}