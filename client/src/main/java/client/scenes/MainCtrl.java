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

import java.io.IOException;
import java.util.List;

import client.utils.ServerUtils;
import commons.*;

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
	public void showQuestionScreenSinglePlayer() throws IOException {
		// If the size of question set equals to zero, this method change to final screen.
		if (questions.size() == 0) {
			showSinglePlayerFinalScreen();
			return;
		}

		clearButtons();	// change back the color of the buttons
		questionScreenSinglePlayerCtrl.setInputButton(null);
		questionScreenSinglePlayerCtrl.setInputText(null);

		// Assign the player variable that got from
		// SinglePlayerPreGame to player variable in MainCtrl
		player = singlePlayerPreGameCtrl.getPlayer();

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			questionScreenSinglePlayerCtrl.decreaseProgress(0.1f);
		}));
		timeLine.setCycleCount(10);
		timeLine.setOnFinished(_e -> {
			updatePoints(questionScreenSinglePlayerCtrl.getInputButton(),
						questionScreenSinglePlayerCtrl.getInputText());
		});
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
	 * @throws IOException if there is a problem with the parsing
	 */
	public void setUpMultipleChoice(MCQuestion question) throws IOException {
		hideTextFieldAndRevealButtons();
		clearImages();

		// Set up label for the question and answers.
		String questionText = question.getActivity().getTitle();
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setImageQuestionImageView(
				question.imageInByteArrayQuestion());
		questionScreenSinglePlayerCtrl.setVisibleImageQuestion(true);
		questionScreenSinglePlayerCtrl.setLabelButton1(Long.toString(question.getAnswer1()));
		questionScreenSinglePlayerCtrl.setLabelButton2(Long.toString(question.getAnswer2()));
		questionScreenSinglePlayerCtrl.setLabelButton3(Long.toString(question.getAnswer3()));
		questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
	}

	/**
	 * This method sets up the "instead of" question
	 * @param question "instead of" question
	 */
	public void setUpInsteadQuestion(InsteadOfQuestion question) throws IOException {
		hideTextFieldAndRevealButtons();
		clearImages();

		// Set up label for the question and answers
		String questionText = question.getQuestionActivity().getTitle();
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setImageQuestionImageView(
				question.imageInByteArrayQuestion());
		questionScreenSinglePlayerCtrl.setVisibleImageQuestion(true);
		questionScreenSinglePlayerCtrl.setLabelButton1(question.getAnswer1().getTitle());
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(1),
				0);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 0);
		questionScreenSinglePlayerCtrl.setLabelButton2(question.getAnswer2().getTitle());
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(2),
				1);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 1);
		questionScreenSinglePlayerCtrl.setLabelButton3(question.getAnswer3().getTitle());
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(3),
				2);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 2);
		questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
	}

	/**
	 * This method sets up the highest consumption question
	 * @param question highest consumption question
	 */
	public void setUpHighestQuestion(HighestConsumptionQuestion question) throws IOException {
		hideTextFieldAndRevealButtons();
		clearImages();

		// Set up label for the question and answers.
		String questionText = "Which one of these activities consumes the most energy?";
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setLabelButton1(question.getActivity1Title());
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(
				question.imageInByteArrayActivity1(), 0);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 0);
		questionScreenSinglePlayerCtrl.setLabelButton2(question.getActivity2Title());
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(
				question.imageInByteArrayActivity2(), 1);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 1);
		questionScreenSinglePlayerCtrl.setLabelButton3(question.getActivity3Title());
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(
				question.imageInByteArrayActivity3(), 2);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 2);
		questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
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
	public void setUpEstimateQuestion(EstimateQuestion question) throws IOException {
		clearImages();
		String questionText = question.getActivityTitle();
		questionScreenSinglePlayerCtrl.setUpLabel(questionText);
		questionScreenSinglePlayerCtrl.setImageQuestionImageView(
				question.imageInByteArrayQuestion());
		questionScreenSinglePlayerCtrl.setVisibleImageQuestion(true);
		questionScreenSinglePlayerCtrl.setVisibleTextField(true);
		questionScreenSinglePlayerCtrl.setVisibleButton1(false);
		questionScreenSinglePlayerCtrl.setVisibleButton2(false);
		questionScreenSinglePlayerCtrl.setVisibleButton3(false);
		questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
	}

	/**
	 * Hides all the visible imageViews
	 */
	public void clearImages() {
		questionScreenSinglePlayerCtrl.setVisibleImageQuestion(false);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 0);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 1);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 2);
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
		intermediateSceneCtrl.setProgress(1f);

		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			intermediateSceneCtrl.decreaseProgress(0.25);
		}));
		timeLine.setCycleCount(4);
		timeLine.play();
		timeLine.setOnFinished(_e -> {
			questionScreenSinglePlayerCtrl.setProgress(1); // Reset the progress bar after
			try {
				showQuestionScreenSinglePlayer();     // the timeline finish its cycle.
			} catch (IOException err) {
				err.printStackTrace();
			}
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
		questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
	}


	/**
	 * This method reveals the answer after the player clicked on the button
	 * @param button the button
	 * @param textField the text field.
	 */
	public void updatePoints(Button button, TextField textField) {
		// in case the player doesn't provide an answer in time
		if(button == null && textField == null) {
			currentPoint = 0;
			questionScreenSinglePlayerCtrl.setProgress(1);

			showAnswer();

			return;
		}

		// Get the time the player used for guessing the answer
		double timePassed = questionScreenSinglePlayerCtrl.getTimeStamp();

		if (question instanceof MCQuestion) {
			System.out.println(1);
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
			System.out.println(2);

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
			System.out.println(3);

			InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;
			currentPoint = insteadQuestion.pointsEarned(1000,
					Integer.parseInt(String.valueOf(button.getId().charAt(12))), timePassed);
			player.setPoint(player.getPoint() + currentPoint);
			if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== insteadQuestion.returnEnergyConsumption(button.getText())) {
				numberOfCorrectAnswered++;
			}
		} else if (question instanceof EstimateQuestion) {
			System.out.println(4);

			EstimateQuestion estimateQuestion = (EstimateQuestion) question;
			currentPoint = estimateQuestion.pointsEarned(1000,
					Integer.parseInt(textField.getText()),timePassed);
			player.setPoint(player.getPoint() + currentPoint);
		}

		// Show the recent score.
		showAnswer();
	}

	/**
	 *  The methods resets the answer buttons to their initial state
	 */
	public void clearButtons() {
		String color = "-fx-background-color: #5b9ad5; -fx-background-radius: 15;";

		questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
		questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
		questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
	}

	private void showAnswer() {
		Button button = questionScreenSinglePlayerCtrl.getInputButton();
		TextField textField = questionScreenSinglePlayerCtrl.getInputText();

		questionScreenSinglePlayerCtrl.setProgress(1f);

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			questionScreenSinglePlayerCtrl.decreaseProgress(1/3f);
		}));
		timeLine.setCycleCount(3);
		timeLine.setOnFinished(_e -> {
			showIntermediateScene();
		});
		timeLine.play();

		if(button != null) {
			String color = "-fx-background-color: #E37474; -fx-background-radius: 15;";

			button.setStyle(color);
		}

		String color = "-fx-background-color: #4BA85D; -fx-background-radius: 15;";

		if (question instanceof MCQuestion) {
			MCQuestion multipleChoiceQuestion = (MCQuestion) question;
			long button1 = multipleChoiceQuestion.getAnswer1();
			long button2 = multipleChoiceQuestion.getAnswer2();
			long button3 = multipleChoiceQuestion.getAnswer3();


			if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== button1) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
			} else if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== button2) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
			} else if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== button3) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof HighestConsumptionQuestion) {
			HighestConsumptionQuestion highConsumptionQuestion
					= (HighestConsumptionQuestion) question;

			Activity activity1 = highConsumptionQuestion.getActivity1();
			Activity activity2 = highConsumptionQuestion.getActivity2();
			Activity activity3 = highConsumptionQuestion.getActivity3();

			if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity1.getConsumptionInWh()){
				questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
			} else if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity2.getConsumptionInWh()){
				questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
			} else if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity3.getConsumptionInWh()){
				questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
			}

		} else if (question instanceof InsteadOfQuestion) {
			InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;

			Activity answer1 = insteadQuestion.getAnswer1();
			Activity answer2 = insteadQuestion.getAnswer2();
			Activity answer3 = insteadQuestion.getAnswer3();

			if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== answer1.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
			} else if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== answer2.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
			} else if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== answer3.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof EstimateQuestion) {
			String message;

			questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(true);

			EstimateQuestion estimateQuestion = (EstimateQuestion) question;
			currentPoint = estimateQuestion.pointsEarned(1000,
					Integer.parseInt(textField.getText()),
					questionScreenSinglePlayerCtrl.getTimeStamp());

			if(currentPoint < 800) {
				if(currentPoint > 500) {
					color = "-fx-background-color: #E37474; -fx-background-radius: 15;";
					message = "Well done!";
				} else {
					color = "-fx-background-color: #DF795B; -fx-background-radius: 15;";
					message = "Oh!";
				}
			} else {
				message = "Bullseye!";
			}

			questionScreenSinglePlayerCtrl.setEstimateAnswerStyle(color);
			questionScreenSinglePlayerCtrl.setEstimateAnswerLabel(
					message +
					" The correct answer is: " +
					estimateQuestion.getActivity().getConsumptionInWh());

			textField.clear();
		}
	}
}