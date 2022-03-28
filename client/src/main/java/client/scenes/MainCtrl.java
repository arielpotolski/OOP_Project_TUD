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
import commons.Activity;
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

	MultiplayerQuestionScreenCtrl multiplayerQuestionScreenCtrl;
	private Scene multiplayerQuestionScreen;

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

	private IntermediateLeaderboardCtrl intermediateLeaderboardCtrl;
	private Scene intermediateLeaderboardScene;

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
	 * @param questionScreenMultiPlayer a pair of question screen for multiplayer with parent
	 * @param intermediateLeaderboard a pair of intermediate leaderboard
	 *                                screen for multiplayer with parent
	 */
	public void initialize(Stage primaryStage,
		Pair<SinglePlayerPreGameCtrl, Parent> singlePlayer,
		Pair<MultiplayerPreGameCtrl, Parent> multiPlayer,
		Pair<SplashCtrl, Parent> splash,
		Pair<QuestionScreenSinglePlayerCtrl, Parent> questionScreenSinglePlayer,
		Pair<GlobalLeaderboardScreenCtrl, Parent> globalLeaderBoard,
		Pair<IntermediateSceneCtrl, Parent> intermediateScene,
		Pair<SinglePlayerFinalScreenCtrl, Parent> singlePlayerFinalScene,
		Pair<WaitingScreenCtrl, Parent> waitingScreen,
		Pair<MultiplayerQuestionScreenCtrl, Parent> questionScreenMultiPlayer,
		Pair<IntermediateLeaderboardCtrl, Parent> intermediateLeaderboard
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

		multiplayerQuestionScreenCtrl = questionScreenMultiPlayer.getKey();
		multiplayerQuestionScreen = new Scene(questionScreenMultiPlayer.getValue());
		multiplayerQuestionScreenCtrl.setMainCtrl(this);

		globalLeaderboardScreenCtrl = globalLeaderBoard.getKey();
		this.globalLeaderBoard = new Scene(globalLeaderBoard.getValue());

		intermediateSceneCtrl = intermediateScene.getKey();
		this.intermediateScene = new Scene(intermediateScene.getValue());

		singlePlayerFinalSceneCtrl = singlePlayerFinalScene.getKey();
		this.singlePlayerFinalScene = new Scene(singlePlayerFinalScene.getValue());

		waitingScreenCtrl = waitingScreen.getKey();
		this.waitingScreen = new Scene(waitingScreen.getValue());

		this.intermediateLeaderboardCtrl = intermediateLeaderboard.getKey();
		this.intermediateLeaderboardScene = new Scene(intermediateLeaderboard.getValue());

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
	 *
	 * @param isSingleplayer whether the current game mode is single player
	 *                       or not
	 */
	public void showQuestionScreen(boolean isSingleplayer) throws IOException {
		QuestionClass screenCtrl;

		if (isSingleplayer) {
			screenCtrl = questionScreenSinglePlayerCtrl;
		} else {
			screenCtrl = multiplayerQuestionScreenCtrl;
		}

		// If the size of question set equals to zero, this method change to final screen.
		if (questions.size() == 0) {
			screenCtrl.showFinalScreen();
			return;
		}

		clearButtons(screenCtrl);	// change back the color of the buttons
		screenCtrl.setInputButton(null);
		screenCtrl.setInputText(null);

		// Assign the player variable that got from
		// SinglePlayerPreGame to player variable in MainCtrl
		player = screenCtrl.getPlayer();
		screenCtrl.setProgress(1f);

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			screenCtrl.decreaseProgress(0.1f);
		}));
		timeLine.setCycleCount(10);
		timeLine.setOnFinished(_e -> {
			updatePoints(screenCtrl.getInputButton(),
						screenCtrl.getInputText(),
						screenCtrl);
		});
		timeLine.play();

		primaryStage.setTitle("Question");

		// Get first question of the set and delete that question.
		question = questions.get(0);
		questions.remove(0);

		numberOfQuestionAnswered++;
		if (question instanceof EstimateQuestion) {
			setUpEstimateQuestion((EstimateQuestion) question, screenCtrl);
		} else if (question instanceof HighestConsumptionQuestion) {
			setUpHighestQuestion((HighestConsumptionQuestion) question, screenCtrl);
		} else if (question instanceof MCQuestion) {
			setUpMultipleChoice((MCQuestion) question, screenCtrl);
		} else if (question instanceof InsteadOfQuestion) {
			setUpInsteadQuestion((InsteadOfQuestion) question, screenCtrl);
		}

		primaryStage.setScene(screenCtrl.getScene());
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
	 * @param screenCtrl The screen controller which handles the task.
	 * @throws IOException if there is a problem with the parsing
	 */
	public void setUpMultipleChoice(
			MCQuestion question,
			QuestionClass screenCtrl
	) throws IOException {
		hideTextFieldAndRevealButtons(screenCtrl);


		// Set up label for the question and answers.
		String questionText = question.getActivity().getTitle();
		screenCtrl.setUpLabel(questionText);
		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			clearImages();

			screenCtrl.setImageQuestionImageView(
					question.imageInByteArrayQuestion());
			screenCtrl.setVisibleImageQuestion(true);
		}
		screenCtrl.setLabelButton1(Long.toString(question.getAnswer1()));
		screenCtrl.setLabelButton2(Long.toString(question.getAnswer2()));
		screenCtrl.setLabelButton3(Long.toString(question.getAnswer3()));
		screenCtrl.setVisibleEstimateAnswer(false);
		screenCtrl.setVisibleTextField(false);
	}

	/**
	 * This method sets up the "instead of" question
	 * @param question "instead of" question
	 * @param screenCtrl The screen controller which handles the task.
	 * @throws IOException if there is a problem with the parsing
	 */
	public void setUpInsteadQuestion(
			InsteadOfQuestion question,
			QuestionClass screenCtrl
	) throws IOException {
		hideTextFieldAndRevealButtons(screenCtrl);
		// Set up label for the question and answers
		String questionText = question.getQuestionActivity().getTitle();
		screenCtrl.setUpLabel(questionText);
		screenCtrl.setLabelButton1(question.getAnswer1().getTitle());
		screenCtrl.setLabelButton2(question.getAnswer2().getTitle());
		screenCtrl.setLabelButton3(question.getAnswer3().getTitle());
		screenCtrl.setVisibleEstimateAnswer(false);

		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			clearImages();

			screenCtrl.setImageQuestionImageView(
					question.imageInByteArrayQuestion());
			screenCtrl.setVisibleImageQuestion(true);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(1),
					0);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(2),
					1);
			screenCtrl.setVisibilityImageView(true, 1);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(3),
					2);
			screenCtrl.setVisibilityImageView(true, 2);
			screenCtrl.setVisibilityImageView(true, 0);
			screenCtrl.setVisibleTextField(false);
		}
	}

	/**
	 * This method sets up the highest consumption question
	 * @param question highest consumption question
	 * @param screenCtrl The screen controller which handles the task.
	 * @throws IOException if there is a problem with the parsing
	 */
	public void setUpHighestQuestion(
			HighestConsumptionQuestion question,
			QuestionClass screenCtrl
		) throws IOException {
		hideTextFieldAndRevealButtons(screenCtrl);

		// Set up label for the question and answers.
		String questionText = "Which one of these activities consumes the most energy?";
		screenCtrl.setUpLabel(questionText);
		screenCtrl.setLabelButton1(question.getActivity1Title());
		screenCtrl.setLabelButton2(question.getActivity2Title());
		screenCtrl.setLabelButton3(question.getActivity3Title());
		screenCtrl.setVisibleEstimateAnswer(false);

		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			clearImages();

			screenCtrl.setImagesInImageViewsAnswers(
					question.imageInByteArrayActivity3(), 2);
			screenCtrl.setVisibilityImageView(true, 2);
			screenCtrl.setImagesInImageViewsAnswers(
					question.imageInByteArrayActivity2(), 1);
			screenCtrl.setVisibilityImageView(true, 1);
			screenCtrl.setImagesInImageViewsAnswers(
					question.imageInByteArrayActivity1(), 0);
			screenCtrl.setVisibilityImageView(true, 0);
			screenCtrl.setVisibleTextField(false);
		}
	}

	/**
	 * This method hide the text field and reveal the buttons.
	 *
	 * @param screenCtrl controller which dictates whether the singleplayer
	 *            	     or the multiplayer screen changes
	 */
	public void hideTextFieldAndRevealButtons(QuestionClass screenCtrl) {
		screenCtrl.setVisibleButton1(true);
		screenCtrl.setVisibleButton2(true);
		screenCtrl.setVisibleButton3(true);
		screenCtrl.setVisibleTextField(false);
	}

	/**
	 * This method sets up the estimate question
	 * @param question the estimate question.
	 * @param screenCtrl The screen controller which handles the task.
	 */
	public void setUpEstimateQuestion(
			EstimateQuestion question,
			QuestionClass screenCtrl
	) throws IOException {
		String questionText = question.getActivityTitle();
		screenCtrl.setUpLabel(questionText);

		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			clearImages();
			screenCtrl.setImageQuestionImageView(
					question.imageInByteArrayQuestion());
			screenCtrl.setVisibleImageQuestion(true);
		}
		screenCtrl.setVisibleTextField(true);
		screenCtrl.setVisibleButton1(false);
		screenCtrl.setVisibleButton2(false);
		screenCtrl.setVisibleButton3(false);
		screenCtrl.setVisibleEstimateAnswer(false);
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
	 * This method shows the final screen for singleplayer.
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
	 * This method shows the final screen for multiplayer.
	 */
	public void showMultiPlayerFinalScreen() {
		//TODO show final screen for multiplayer
	}

	/**
	 * This method reveals the answer after the player clicked on the button
	 * @param button the button
	 * @param textField the text field.
	 * @param screenCtrl screen controller which can be either for singleplayer or
	 *                   for multiplayer
	 */
	public void updatePoints(Button button, TextField textField, QuestionClass screenCtrl) {
		// in case the player doesn't provide an answer in time
		if(button == null && textField == null) {
			currentPoint = 0;
			screenCtrl.setProgress(1);

			showAnswer(screenCtrl);

			return;
		}

		// Get the time the player used for guessing the answer
		double timePassed = screenCtrl.getTimeStamp();

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
			int buttonId = 0;

			Activity activity1 = highConsumptionQuestion.getActivity1();
			Activity activity2 = highConsumptionQuestion.getActivity2();
			Activity activity3 = highConsumptionQuestion.getActivity3();

			if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity1.getConsumptionInWh()) {
				buttonId = 1;
			} else if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity2.getConsumptionInWh()) {
				buttonId = 2;
			} else if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity3.getConsumptionInWh()) {
				buttonId = 3;
			}
			currentPoint = highConsumptionQuestion.pointsEarned(1000,
					buttonId,timePassed);

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
		}

		// Show the recent score.
		showAnswer(screenCtrl);
	}

	/**
	 *  The methods resets the answer buttons to their initial state
	 *
	 * @param screenCtrl screen controller which can be either for singleplayer or
	 * 	 *                   for multiplayer
	 */
	public void clearButtons(QuestionClass screenCtrl) {
		String color = "-fx-background-color: #5b9ad5; -fx-background-radius: 15;";

		screenCtrl.setStyleAnswerButton1(color);
		screenCtrl.setStyleAnswerButton2(color);
		screenCtrl.setStyleAnswerButton3(color);
	}

	/**
	 * 	Method that shows the correct answer after the timer is finished
	 * 	The correct answer will be colored with green
	 * 	If the player choses the wrong answer, his choice will be colored with red
	 * @param screenCtrl screen controller which can be either for singleplayer or
	 *                   for multiplayer
	 */
	private void showAnswer(QuestionClass screenCtrl) {
		Button button = screenCtrl.getInputButton();
		TextField textField = screenCtrl.getInputText();

		screenCtrl.setProgress(1f);

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			screenCtrl.decreaseProgress(1/3f);
		}));
		timeLine.setCycleCount(3);
		timeLine.setOnFinished(_e -> {
			screenCtrl.showIntermediateScene();
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
				screenCtrl.setStyleAnswerButton1(color);
			} else if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== button2) {
				screenCtrl.setStyleAnswerButton2(color);
			} else if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== button3) {
				screenCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof HighestConsumptionQuestion) {
			HighestConsumptionQuestion highConsumptionQuestion
					= (HighestConsumptionQuestion) question;

			Activity activity1 = highConsumptionQuestion.getActivity1();
			Activity activity2 = highConsumptionQuestion.getActivity2();
			Activity activity3 = highConsumptionQuestion.getActivity3();

			if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity1.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton1(color);
			} else if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity2.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton2(color);
			} else if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== activity3.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof InsteadOfQuestion) {
			InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;

			Activity answer1 = insteadQuestion.getAnswer1();
			Activity answer2 = insteadQuestion.getAnswer2();
			Activity answer3 = insteadQuestion.getAnswer3();

			if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== answer1.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton1(color);
			} else if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== answer2.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton2(color);
			} else if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== answer3.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof EstimateQuestion) {
			String message;

			screenCtrl.setVisibleEstimateAnswer(true);

			EstimateQuestion estimateQuestion = (EstimateQuestion) question;
			if(textField != null) {
				currentPoint = estimateQuestion.pointsEarned(1000,
						Integer.parseInt(textField.getText()),
						screenCtrl.getTimeStamp());
			} else {
				currentPoint = 0;
			}

			if(currentPoint < 500) {
				if(currentPoint > 300) {
					color = "-fx-background-color: #f0de8d; -fx-background-radius: 15;";
					message = "Well done!";
				} else {
					color = "-fx-background-color: #E37474; -fx-background-radius: 15;";
					message = "Oh!";
				}
			} else {
				message = "Bullseye!";
			}

			screenCtrl.setEstimateAnswerStyle(color);
			screenCtrl.setEstimateAnswerLabel(
					message +
					" The correct answer is: " +
					String.valueOf(estimateQuestion.getActivity().getConsumptionInWh()));

			if(textField != null)
				textField.clear();
		}
	}

	/**
	 *  Getter method for the singleplayer pre game controller controller
	 *
	 * @return The singleplayer pre game controller
	 */
	public SinglePlayerPreGameCtrl getSinglePlayerPreGameCtrl() {
		return singlePlayerPreGameCtrl;
	}

	/**
	 *  Getter method for the singleplayer pre game controller screen
	 *
	 * @return The singleplayer pre game screen
	 */
	public Scene getSinglePlayerPreGameScreen() {
		return questionScreenSinglePlayer;
	}

	/**
	 * 	Getter method for multiplayer pre game controller.
	 *
	 * @return multiplayer pre game controller.
	 */
	public MultiplayerPreGameCtrl getMultiplayerPreGameCtrl() {
		return multiplayerPreGameCtrl;
	}

	/**
	 * 	Getter method for the multiplayer question screen.
	 *
	 * @return the multiplayer question screen.
	 */
	public Scene getMultiplayerQuestionScreen() {
		return multiplayerQuestionScreen;
	}

	/**
	 * 	Getter method for intermediate screen controller
	 *
	 * @return intermediate screen controller
	 */
	public IntermediateSceneCtrl getIntermediateSceneCtrl() {
		return intermediateSceneCtrl;
	}

	/**
	 *	Getter method for the number of questions answerd this far
	 *
	 * @return the number of questions answerd this far
	 */
	public int getNumberOfQuestionAnswered() {
		return numberOfQuestionAnswered;
	}

	/**
	 *	Getter method for the player
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 *	Getter method for the number of points
	 *
	 * @return the number of points received at the last
	 * 		   question answered
	 */
	public int getCurrentPoint() {
		return currentPoint;
	}

	/**
	 *	Getter method for the primary stage
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * 	Getter method for the intermediate scene
	 *
	 * @return the intermediate scene
	 */
	public Scene getIntermediateScene() {
		return intermediateScene;
	}

	/**
	 * 	Getter method for the intermediate leaderboard scene
	 *
	 * @return the intermediate leaderboard scene
	 */
	public Scene getIntermediateLeaderboardScene() {
		return intermediateLeaderboardScene;
	}

	/**
	 * 	Getter method for the intermediate leaderboard controller
	 *
	 * @return the intermediate leaderboard controller
	 */
	public IntermediateLeaderboardCtrl getIntermediateLeaderboardCtrl() {
		return intermediateLeaderboardCtrl;
	}
}