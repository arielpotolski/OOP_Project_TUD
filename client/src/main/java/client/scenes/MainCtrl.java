/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import java.util.List;

import client.utils.ServerUtils;
import commons.EstimateQuestion;
import commons.HighestConsumptionQuestion;
import commons.InsteadOfQuestion;
import commons.LobbyResponse;
import commons.MCQuestion;
import commons.Player;
import commons.Question;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class MainCtrl {
	private Stage primaryStage;

	private SplashCtrl splashCtrl;
	private Scene splashScreen;

	private SinglePlayerPreGameCtrl singlePlayerPreGameCtrl;
	private Scene singlePlayerPreGameScreen;

	private MultiplayerPreGameCtrl multiplayerPreGameCtrl;
	private Scene multiplayerPreGameScreen;

	private QuestionScreenSinglePlayerCtrl questionScreenSinglePlayerCtrl;
	private Scene questionScreenSinglePlayer;

	private GlobalLeaderboardScreenCtrl globalLeaderboardScreenCtrl;
	private Scene globalLeaderBoard;

	private IntermediateSceneCtrl intermediateSceneCtrl;
	private Scene intermediateScene;

	private SinglePlayerFinalScreenCtrl singlePlayerFinalSceneCtrl;
	private Scene singlePlayerFinalScene;

	private WaitingScreenCtrl waitingScreenCtrl;
	private Scene waitingScreen;

	private ServerUtils server;

	private List<Question> questions;
	private String answer;
	private Question question;

	private Timeline timeLine;
	private Player player;
	private int currentPoint;
	private int numberOfQuestionAnswered = 0;
	private int numberOfCorrectAnswered = 0;

	/**
	 * Initialize all the screens
	 *
	 * @param primaryStage the primary stage
	 * @param singlePlayer a pair of single player with parent
	 * @param multiPlayer a pair of multiplayer with parent
	 * @param splash a pair of splash screen with parent
	 * @param questionScreenSinglePlayer a pair of question screen with parent.
	 * @param globalLeaderBoard a pair of global leader board with parent
	 * @param intermediateScene a pair of intermediate screen with parent
	 * @param singlePlayerFinalScene a pair of final single player screen with parent.
	 * @param waitingScreen a pair of waiting screen with parent
	 */
	public void initialize(Stage primaryStage,
		Pair<SinglePlayerPreGameCtrl, Parent> singlePlayer,
		Pair<MultiplayerPreGameCtrl, Parent> multiPlayer,
		Pair<SplashCtrl, Parent> splash,
		Pair<QuestionScreenSinglePlayerCtrl, Parent> questionScreenSinglePlayer,
		Pair<GlobalLeaderboardScreenCtrl, Parent> globalLeaderBoard,
		Pair<IntermediateSceneCtrl, Parent> intermediateScene,
		Pair<SinglePlayerFinalScreenCtrl, Parent> singlePlayerFinalScene,
		Pair<WaitingScreenCtrl, Parent> waitingScreen
	) {
		this.primaryStage = primaryStage;

		multiplayerPreGameCtrl = multiPlayer.getKey();
		multiplayerPreGameScreen = new Scene(multiPlayer.getValue());

		singlePlayerPreGameCtrl = singlePlayer.getKey();
		singlePlayerPreGameScreen = new Scene(singlePlayer.getValue());

		splashCtrl = splash.getKey();
		splashScreen = new Scene(splash.getValue());

		questionScreenSinglePlayerCtrl = questionScreenSinglePlayer.getKey();
		this.questionScreenSinglePlayer = new Scene(questionScreenSinglePlayer.getValue());

		globalLeaderboardScreenCtrl = globalLeaderBoard.getKey();
		this.globalLeaderBoard = new Scene(globalLeaderBoard.getValue());

		intermediateSceneCtrl = intermediateScene.getKey();
		this.intermediateScene = new Scene(intermediateScene.getValue());

		singlePlayerFinalSceneCtrl = singlePlayerFinalScene.getKey();
		this.singlePlayerFinalScene = new Scene(singlePlayerFinalScene.getValue());

		waitingScreenCtrl = waitingScreen.getKey();
		this.waitingScreen = new Scene(waitingScreen.getValue());

		showSplashScreen();

		primaryStage.show();
	}

	/**
	 * This method shows up the splash screen.
	 */
	public void showSplashScreen() {
		primaryStage.setTitle("Quizzz");
		primaryStage.setScene(splashScreen);
	}

	/**
	 * This method shows up the single player pre-game screen.
	 */
	public void showSinglePlayerPreGameScreen() {
		primaryStage.setTitle("SinglePlayer");
		primaryStage.setScene(singlePlayerPreGameScreen);
	}

	/**
	 * This method shows up multiplayer pre-game screen
	 */
	public void showMultiplePlayersPreGameScreen() {
		primaryStage.setTitle("Multiplayer");
		primaryStage.setScene(multiplayerPreGameScreen);
	}

	public void showWaitingScreen(LobbyResponse firstResponse) {
		primaryStage.setTitle("Waiting Lobby");
		primaryStage.setScene(waitingScreen);
		waitingScreenCtrl.beginActiveRefresh(firstResponse);
	}

	/**
	 * This method shows up the question screen in single player mode.
	 */
	public void showQuestionScreenSinglePlayer() {
		// If the size of question set equals to zero, this method change to final screen.
		if (questions.size() == 0) {
			showSinglePlayerFinalScreen();
			return;
		}

		// Assign the player variable that got from
		// SinglePlayerPreGame to player variable in MainCtrl
		player = singlePlayerPreGameCtrl.getPlayer();

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			questionScreenSinglePlayerCtrl.decreaseProgress();
		}));
		timeLine.setCycleCount(10);
		timeLine.play();

		primaryStage.setTitle("Question");

		// Get first question of the set and delete that question.
		question = questions.get(0);
		questions.remove(0);

		numberOfQuestionAnswered++;
		if (question instanceof EstimateQuestion) {
			setUpEstimateQuestion((EstimateQuestion) question);
		} else if (question instanceof HighestConsumptionQuestion) {
			setUpHighestQuestion((HighestConsumptionQuestion) question);
		} else if (question instanceof MCQuestion) {
			setUpMultipleChoice((MCQuestion) question);
		} else if (question instanceof InsteadOfQuestion) {
			setUpInsteadQuestion((InsteadOfQuestion) question);
		}
		primaryStage.setScene(questionScreenSinglePlayer);
	}

	public void setServer(ServerUtils server) {
		this.server = server;
	}

	public ServerUtils getServer() {
		return this.server;
	}

	/**
	 * This method will get a list of questions
	 */
	public void getQuestions() {
		this.questions = server.getQuestions();
	}

	/**
	 * This method sets up the multiple choice question
	 * @param question multiple choice question
	 */
	public void setUpMultipleChoice(MCQuestion question) {
		hideTextFieldAndRevealButtons();

		// Set up label for the question and answers.
		String questionText = question.getActivity().getTitle();
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setLabelButton1(Long.toString(question.getAnswer1()));
		questionScreenSinglePlayerCtrl.setLabelButton2(Long.toString(question.getAnswer2()));
		questionScreenSinglePlayerCtrl.setLabelButton3(Long.toString(question.getAnswer3()));
	}

	/**
	 * This method sets up the "instead of" question
	 * @param question "instead of" question
	 */
	public void setUpInsteadQuestion(InsteadOfQuestion question) {
		hideTextFieldAndRevealButtons();

		// Set up label for the question and answers
		String questionText = question.getQuestionActivity().getTitle();
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setLabelButton1(question.getAnswer1().getTitle());
		questionScreenSinglePlayerCtrl.setLabelButton2(question.getAnswer2().getTitle());
		questionScreenSinglePlayerCtrl.setLabelButton3(question.getAnswer3().getTitle());
	}

	/**
	 * This method sets up the highest consumption question
	 * @param question highest consumption question
	 */
	public void setUpHighestQuestion(HighestConsumptionQuestion question) {
		hideTextFieldAndRevealButtons();

		// Set up label for the question and answers.
		String questionText = "Which one of these activities consumes the most energy?";
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setLabelButton1(question.getActivity1Title());
		questionScreenSinglePlayerCtrl.setLabelButton2(question.getActivity2Title());
		questionScreenSinglePlayerCtrl.setLabelButton3(question.getActivity3Title());
	}

	/**
	 * This method hide the text field and reveal the buttons.
	 */
	public void hideTextFieldAndRevealButtons() {
		questionScreenSinglePlayerCtrl.setVisibleButton1(true);
		questionScreenSinglePlayerCtrl.setVisibleButton2(true);
		questionScreenSinglePlayerCtrl.setVisibleButton3(true);
		questionScreenSinglePlayerCtrl.setVisibleTextField(false);
	}

	/**
	 * This method sets up the estimate question
	 * @param question the estimate question.
	 */
	public void setUpEstimateQuestion(EstimateQuestion question) {
		String questionText = question.getActivityTitle();
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setVisibleTextField(true);
		questionScreenSinglePlayerCtrl.setVisibleButton1(false);
		questionScreenSinglePlayerCtrl.setVisibleButton2(false);
		questionScreenSinglePlayerCtrl.setVisibleButton3(false);
	}

	/**
	 * This method shows the global leader board screen.
	 */
	public void showGlobalLeaderboardScreen() {
		globalLeaderboardScreenCtrl.getItems();
		primaryStage.setTitle("Leader Board");
		primaryStage.setScene(globalLeaderBoard);
	}

	/**
	 * This method shows the intermediate scene.
	 */
	public void showIntermediateScene() {

		intermediateSceneCtrl.setQuestionAnswer(numberOfQuestionAnswered);
		intermediateSceneCtrl.setLabelPoint(player.getPoint());
		intermediateSceneCtrl.setCurrentQuestionPointsEarned(currentPoint);
		primaryStage.setTitle("IntermediateScene");
		primaryStage.setScene(intermediateScene);

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			intermediateSceneCtrl.decreaseProgress();
		}));
		timeLine.setCycleCount(10);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			intermediateSceneCtrl.setProgress(1); // Reset the progress bar after
			showQuestionScreenSinglePlayer();     // the timeline finish its cycle.
		});
	}

	/**
	 * This method shows the final screen.
	 */
	public void showSinglePlayerFinalScreen() {
		singlePlayerFinalSceneCtrl.setTotalScore(player.getPoint());
		singlePlayerFinalSceneCtrl.setCorrectAnswers(numberOfCorrectAnswered);
		singlePlayerFinalSceneCtrl.setServer(server);
		singlePlayerFinalSceneCtrl.addPlayer(player);
		primaryStage.setTitle("Final Score");
		primaryStage.setScene(singlePlayerFinalScene);
	}


	/**
	 * This method reveals the answer after the player clicked on the button
	 * @param button the button
	 * @param textField the text field.
	 */
	public void showAnswer(Button button, TextField textField) {
		timeLine.stop(); // Stop the count-down timer.

		// Get the time the player used for guessing the answer
		double timePassed = questionScreenSinglePlayerCtrl.getProgress();
		if (question instanceof MCQuestion) {
			MCQuestion multipleChoiceQuestion = (MCQuestion) question;

			// The point which the player will receive after answered the question
			currentPoint = multipleChoiceQuestion.pointsEarned(1000,
					Integer.parseInt(button.getText()),timePassed);

			// Set the point for the player
			player.setPoint(player.getPoint() + currentPoint);

			// If the player clicked on the correct answer,
			// number of correct answers would be increased.
			if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== Long.parseLong(button.getText())) {
				numberOfCorrectAnswered++;
			}
		} else if (question instanceof HighestConsumptionQuestion) {
			HighestConsumptionQuestion highConsumptionQuestion
					= (HighestConsumptionQuestion) question;
			currentPoint = highConsumptionQuestion.pointsEarned(1000,
					highConsumptionQuestion.returnEnergyConsumption(button.getText()),timePassed);
			player.setPoint(player.getPoint() + currentPoint);
			if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== highConsumptionQuestion.returnEnergyConsumption(button.getText())){
				numberOfCorrectAnswered++;
			}

		} else if (question instanceof InsteadOfQuestion) {
			InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;
			currentPoint = insteadQuestion.pointsEarned(1000,
					Integer.parseInt(String.valueOf(button.getId().charAt(12))), timePassed);
			player.setPoint(player.getPoint() + currentPoint);
			if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== insteadQuestion.returnEnergyConsumption(button.getText())) {
				numberOfCorrectAnswered++;
			}
		} else if (question instanceof EstimateQuestion) {
			EstimateQuestion estimateQuestion = (EstimateQuestion) question;
			currentPoint = estimateQuestion.pointsEarned(1000,
					Integer.parseInt(textField.getText()),timePassed);
			player.setPoint(player.getPoint() + currentPoint);
			textField.clear();
		}

		// Reset the count-down timer
		questionScreenSinglePlayerCtrl.setProgress(1);

		// Show the recent score.
		showIntermediateScene();
	}
}