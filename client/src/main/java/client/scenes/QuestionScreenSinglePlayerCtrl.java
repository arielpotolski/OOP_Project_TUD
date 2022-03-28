package client.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.Player;

import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionScreenSinglePlayerCtrl extends QuestionClass implements Initializable {
	/**
	 * Constructor for question screen in single player mode.
	 *
	 * @param mainCtrl the injected main controller.
	 * @param server the injected server.
	 */
	@Inject
	public QuestionScreenSinglePlayerCtrl(MainCtrl mainCtrl,ServerUtils server) {
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	/**
	 *	Initialisation method.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize();
	}

	/**
	 *  Move to the final screen.
	 */
	@Override
	public void showFinalScreen() {
		this.mainCtrl.showSinglePlayerFinalScreen();
	}

	@Override
	public Player getPlayer() {
		return (this.mainCtrl.getSinglePlayerPreGameCtrl()).getPlayer();
	}

	/**
	 * Getter method for the singleplayer screen
	 *
	 * @return the Scene assigned to singleplayer question screen.
	 */
	@Override
	public Scene getScene() {
		return this.mainCtrl.getSinglePlayerPreGameScreen();
	}

	/**
	 * This method shows the intermediate scene.
	 */
	public void showIntermediateScene() {
		IntermediateSceneCtrl intermediateSceneCtrl = this.mainCtrl.getIntermediateSceneCtrl();
		Stage primaryStage = this.mainCtrl.getPrimaryStage();

		intermediateSceneCtrl.setQuestionAnswer(this.mainCtrl.getNumberOfQuestionAnswered());
		intermediateSceneCtrl.setLabelPoint(this.mainCtrl.getPlayer().getPoint());
		intermediateSceneCtrl.setCurrentQuestionPointsEarned(this.mainCtrl.getCurrentPoint());
		primaryStage.setTitle("IntermediateScene");
		primaryStage.setScene(this.mainCtrl.getIntermediateScene());

		// This timeline will execute on another thread - run the count-down timer.
		intermediateSceneCtrl.setProgress(1f);

		Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			intermediateSceneCtrl.decreaseProgress(0.25);
		}));
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			try {
				this.mainCtrl.showQuestionScreen(true);     // the timeline finish its cycle.
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 *
	 * @param event the player click on the button.
	 */
	@Override
	public void answerReturn(ActionEvent event) {
		mainCtrl.clearButtons(this);

		super.answerReturn(event);
	}
}