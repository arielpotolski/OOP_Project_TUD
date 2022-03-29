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
import java.util.Random;

import client.utils.ServerUtils;
import commons.Activity;
import commons.Connection;
import commons.EstimateQuestion;
import commons.HighestConsumptionQuestion;
import commons.InsteadOfQuestion;
import commons.LobbyResponse;
import commons.MCQuestion;
import commons.MessageModel;
import commons.Player;
import commons.Question;
import commons.messages.ErrorMessage;
import commons.messages.LeaderboardMessage;
import commons.messages.Message;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private TopPlayersLeaderboardCtrl topPlayersLeaderboardCtrl;
	private Scene topPlayersLeaderboard;

	private MultiplayerQuestionScreenCtrl multiplayerQuestionScreenCtrl;
	private Scene multiPlayerQuestionScreen;

	private IntLeaderboardCtrl intLeaderboardCtrl;
	private Scene intermediateLeaderboardScreen;

	private ServerUtils server;

	private List<Question> questions;
	private String answer;
	private Question question;

	private Timeline timeLine;
	private Player player;
	private String nickname;
	private int currentPoint;
	private int numberOfQuestionAnswered = 0;
	private int numberOfCorrectAnswered = 0;

	private long seed = 0;

	private Logger logger;

	public MainCtrl() {
		seed = new Random().nextInt();
	}

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
	 * @param topPlayersLeaderboard a pair of top players leaderboard scene with parent.
	 * @param multiPlayerQuestion a pair of multiplayer
	 * @param intLeaderboard a pair of intermediate leaderboard
	 *          question screen with parent
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
		Pair<MultiplayerQuestionScreenCtrl, Parent> multiPlayerQuestion,
		Pair<IntLeaderboardCtrl, Parent> intLeaderboard,
		Pair<TopPlayersLeaderboardCtrl, Parent> topPlayersLeaderboard
	) {
		this.logger = LoggerFactory.getLogger(MainCtrl.class);;

		this.primaryStage = primaryStage;

		this.multiplayerPreGameCtrl = multiPlayer.getKey();
		this.multiplayerPreGameScreen = new Scene(multiPlayer.getValue());

		this.singlePlayerPreGameCtrl = singlePlayer.getKey();
		this.singlePlayerPreGameScreen = new Scene(singlePlayer.getValue());

		this.splashCtrl = splash.getKey();
		this.splashScreen = new Scene(splash.getValue());

		this.questionScreenSinglePlayerCtrl = questionScreenSinglePlayer.getKey();
		this.questionScreenSinglePlayer = new Scene(questionScreenSinglePlayer.getValue());

		this.globalLeaderboardScreenCtrl = globalLeaderBoard.getKey();
		this.globalLeaderBoard = new Scene(globalLeaderBoard.getValue());

		this.intermediateSceneCtrl = intermediateScene.getKey();
		this.intermediateScene = new Scene(intermediateScene.getValue());

		this.singlePlayerFinalSceneCtrl = singlePlayerFinalScene.getKey();
		this.singlePlayerFinalScene = new Scene(singlePlayerFinalScene.getValue());

		this.waitingScreenCtrl = waitingScreen.getKey();
		this.waitingScreen = new Scene(waitingScreen.getValue());

		this.topPlayersLeaderboardCtrl = topPlayersLeaderboard.getKey();
		this.topPlayersLeaderboard = new Scene(topPlayersLeaderboard.getValue());

		this.multiplayerQuestionScreenCtrl = multiPlayerQuestion.getKey();
		this.multiPlayerQuestionScreen = new Scene(multiPlayerQuestion.getValue());

		this.intLeaderboardCtrl = intLeaderboard.getKey();
		this.intermediateLeaderboardScreen = new Scene(intLeaderboard.getValue());

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

	/**
	 * 	Method that shows the question screen for the multiplayer game mode
	 *
	 * @throws IOException
	 */
	public void showMultiplayerQuestionScreen() throws IOException{
		// Switch to final screen if the aren't any questions
		if (questions.size() == 0) {
			showMultiPlayerFinalScreen();
			return;
		}

		primaryStage.setTitle("Question");
		primaryStage.setScene(multiPlayerQuestionScreen);

		question = questions.get(0); // get the element at the top
		questions.remove(0); // pop the element at the top

		// This timeline will execute on another thread - run the count-down timer.
		timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
			multiplayerQuestionScreenCtrl.decreaseProgress();
		}));
		timeLine.setCycleCount(10);
		timeLine.play();

		numberOfQuestionAnswered++;

		if (question instanceof EstimateQuestion) {
			setUpEstimateQuestionMultiplayer((EstimateQuestion) question);
		} else if (question instanceof HighestConsumptionQuestion) {
			setUpHighestQuestionMultiplayer((HighestConsumptionQuestion) question);
		} else if (question instanceof MCQuestion) {
			setUpMultipleChoiceMultiplayer((MCQuestion) question);
		} else if (question instanceof InsteadOfQuestion) {
			setUpInsteadQuestionMultiplayer((InsteadOfQuestion) question);
		}
		primaryStage.setScene(multiPlayerQuestionScreen);
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

	/**
	 *  Method that switches to multiplayer final screen
	 */
	public void showMultiPlayerFinalScreen() {
		//TODO implement this
	}

	public void setServer(ServerUtils server) {
		this.server = server;
	}

	public ServerUtils getServer() {
		return this.server;
	}

	/**
	 * Set up a thread which listens to the connection from the server for messages
	 */
	public void startMessageReceiverThread() {
		Thread thread = new Thread(() -> {
			Connection conn = this.server.getConnection();
			while (true) {
				try {
					Message message = conn.receive();
					switch (message.getType()) {
					case LEADERBOARD:
						this.intLeaderboardCtrl.setPlayers(
								((LeaderboardMessage) message).getPlayers());
						break;
					case JOIN:
					case ERROR:
						this.logger.error("Received error message: " +
								((ErrorMessage) message).getError());
						break;
						// TODO EndGame Message to stop this thread
					}
				} catch (Exception err) {
					err.printStackTrace();
					return;
				}
			}
		});
		thread.start();
	}

	/**
	 * This method will get a list of questions
	 */
	public void getQuestions() {
		this.questions = server.getQuestions(this.seed);
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
	 * This method sets up the multiple choice question for the multiplayer game mode
	 * @param question multiple choice question
	 * @throws IOException if there is a problem with the parsing
	 */
	public void setUpMultipleChoiceMultiplayer(MCQuestion question) throws IOException {
		//TODO implement this
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
		questionScreenSinglePlayerCtrl.setLabelButton1(question.answerString(1));
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(1),
				0);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 0);
		questionScreenSinglePlayerCtrl.setLabelButton2(question.answerString(2));
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(2),
				1);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 1);
		questionScreenSinglePlayerCtrl.setLabelButton3(question.answerString(3));
		questionScreenSinglePlayerCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(3),
				2);
		questionScreenSinglePlayerCtrl.setVisibilityImageView(true, 2);
		questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
	}

	/**
	 * This method sets up the "instead of" question for the multiplayer game mode
	 * @param question "instead of" question
	 */
	public void setUpInsteadQuestionMultiplayer(InsteadOfQuestion question) throws IOException {
		//TODO implement this
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
	 * This method sets up the highest consumption question for the multiplayer game mode
	 * @param question highest consumption question
	 */
	public void setUpHighestQuestionMultiplayer(HighestConsumptionQuestion question)
			throws IOException {
		//TODO implement this
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
	 * This method sets up the estimate question for the multiplayer game mode
	 * @param question the estimate question.
	 */
	public void setUpEstimateQuestionMultiplayer(EstimateQuestion question) throws IOException {

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
		if (button == null && textField == null) {
			currentPoint = 0;
			questionScreenSinglePlayerCtrl.setProgress(1);

			showAnswer();

			return;
		}

		// Get the time the player used for guessing the answer
		double timePassed = questionScreenSinglePlayerCtrl.getTimeStamp();

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
					buttonId, timePassed);

			player.setPoint(player.getPoint() + currentPoint);

			if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== highConsumptionQuestion.returnEnergyConsumption(button.getText())) {
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
					Integer.parseInt(textField.getText()), timePassed);
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
		timeLine.stop();

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
			long correctAnswer = multipleChoiceQuestion.getActivity().getConsumptionInWh();

			if (correctAnswer == button1) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
			} else if (correctAnswer == button2) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
			} else if (correctAnswer == button3) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof HighestConsumptionQuestion) {
			HighestConsumptionQuestion highConsumptionQuestion
					= (HighestConsumptionQuestion) question;

			Activity activity1 = highConsumptionQuestion.getActivity1();
			Activity activity2 = highConsumptionQuestion.getActivity2();
			Activity activity3 = highConsumptionQuestion.getActivity3();
			long correctAnswer = highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh();

			if (correctAnswer == activity1.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
			} else if (correctAnswer == activity2.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
			} else if (correctAnswer == activity3.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof InsteadOfQuestion) {
			InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;

			Activity answer1 = insteadQuestion.getAnswer1();
			Activity answer2 = insteadQuestion.getAnswer2();
			Activity answer3 = insteadQuestion.getAnswer3();
			long correctAnswer = insteadQuestion.correctAnswer().getConsumptionInWh();

			if (correctAnswer == answer1.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton1(color);
			} else if (correctAnswer == answer2.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton2(color);
			} else if (correctAnswer == answer3.getConsumptionInWh()) {
				questionScreenSinglePlayerCtrl.setStyleAnswerButton3(color);
			}
		} else if (question instanceof EstimateQuestion) {
			String message;

			questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(true);

			EstimateQuestion estimateQuestion = (EstimateQuestion) question;
			if(textField != null) {
				currentPoint = estimateQuestion.pointsEarned(1000,
						Integer.parseInt(textField.getText()),
						questionScreenSinglePlayerCtrl.getTimeStamp());
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

			questionScreenSinglePlayerCtrl.setEstimateAnswerStyle(color);
			questionScreenSinglePlayerCtrl.setEstimateAnswerLabel(
					message +
					" The correct answer is: " +
					String.valueOf(estimateQuestion.getActivity().getConsumptionInWh()));

			if(textField != null)
				textField.clear();
		}
	}

	/**
	 * 	Setter method for seed
	 *
	 * @param seed The seed that is set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * Shows the final leaderboard scene of the multiplayer game mode.
	 * This method needs to be changed in the future to allow displaying the names
	 * of the top 3 players.
	 */
	public void showTopPlayersLeaderboard() {
		primaryStage.setTitle("Final Leaderboard");
		primaryStage.setScene(this.topPlayersLeaderboard);
	}

	/**
	 * setter for the player nickname.
	 * @param nickName the nickname selected by the player.
	 */
	public void setNickname(String nickName) {
		this.nickname = nickName;
	}

	/**
	 * getter for the player's nickname
	 * @return the player's nickname as a String.
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * this method calls the method joinLobby from the multiplayerPreGameCtrl class,
	 * which handles the player joining a lobby feature.
	 */
	public void joinLobby() {
		this.multiplayerPreGameCtrl.joinLobby();
	}

	public void showMultiPlayerQuestionScreen() {
		player = multiplayerPreGameCtrl.getPlayer();
		multiplayerQuestionScreenCtrl.setPlayer(player);
		multiplayerQuestionScreenCtrl.setServer(server);

		primaryStage.setTitle("MultiPlayerQuestion");
		primaryStage.setScene(multiPlayerQuestionScreen);
	}

	/**
	 * Changes the scene to intermediate leaderboard
	 * @throws IOException if something goes wrong with the socket
	 * @throws ClassNotFoundException if Class is not found
	 */
	public void changeToLeaderboard() throws IOException, ClassNotFoundException {
		this.primaryStage.setScene(this.intermediateLeaderboardScreen);
		this.primaryStage.setTitle("Leaderboard");
		this.intLeaderboardCtrl.displayScores();
	}

	public void setPortInMultiplayerQuestionScreen(int port) {
		multiplayerQuestionScreenCtrl.setPort(port);
	}
}