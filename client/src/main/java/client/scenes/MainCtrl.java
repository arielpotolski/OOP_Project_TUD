package client.scenes;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import client.Main;
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
import commons.messages.JokerMessage;
import commons.messages.JokerType;
import commons.messages.KillerMessage;
import commons.messages.LeaderboardMessage;
import commons.messages.Message;
import commons.messages.PointMessage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.Pair;
import org.apache.commons.lang3.NotImplementedException;
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
	private Scene globalLeaderboard;

	private IntermediateSceneCtrl intermediateSceneCtrl;
	private Scene intermediateScene;

	private SinglePlayerFinalScreenCtrl singlePlayerFinalSceneCtrl;
	private Scene singlePlayerFinalScene;

	private WaitingScreenCtrl waitingScreenCtrl;
	private Scene waitingScreen;

	private AdminInterfaceScreenCtrl adminInterfaceScreenCtrl;
	private Scene adminInterfaceScreen;

	private AdminAddActivityScreenCtrl adminAddActivityScreenCtrl;
	private Scene adminAddActivityScreen;

	private AdminRemoveActivityScreenCtrl adminRemoveActivityScreenCtrl;
	private Scene adminRemoveActivityScreen;

	private AdminEditActivityScreenCtrl adminEditActivityScreenCtrl;
	private Scene adminEditActivityScreen;

	private TopPlayersLeaderboardCtrl topPlayersLeaderboardCtrl;
	private Scene topPlayersLeaderboard;

	private MultiplayerQuestionScreenCtrl multiplayerQuestionScreenCtrl;
	private Scene multiPlayerQuestionScreen;

	private IntLeaderboardCtrl intLeaderboardCtrl;
	private Scene intermediateLeaderboardScreen;

	private final ServerUtils server;

	private List<Question> questions;
	private Question question;

	private Timeline timeline;
	private Player player;
	private String nickname;
	private int currentPoints;
	private int numberOfQuestionsAnswered = 0;
	private int numberOfCorrectAnswers = 0;

	private List<Activity> activities;

	private long seed = 0;

	private EventHandler<WindowEvent> confirmCloseEventHandler;

	private Logger logger;

	private static final double JOKER_DECREASE_TIME_PERCENT = 0.5;

	/**
	 * This counts the amount of times the joker is used and will only give
	 * double points if it is used only once
	 *
	 * It should have three states in total
	 * 0 - not used
	 * 1 - used but not in styling
	 * 2 - absolutely used
	 */
	private int doublePointsUsed = 0;

	public MainCtrl() {
		this.seed = new Random().nextInt();
		this.server = new ServerUtils(Main.serverHost);
	}

	/**
	 * Initialize all the screens
	 * @param primaryStage The primary stage.
	 * @param singlePlayer A pair of single player with parent.
	 * @param multiPlayer A pair of multiplayer with parent.
	 * @param splash A pair of splash screen with parent.
	 * @param questionScreenSinglePlayer A pair of question screen with parent.
	 * @param globalLeaderBoard A pair of global leader board with parent.
	 * @param intermediateScene A pair of intermediate screen with parent.
	 * @param singlePlayerFinalScene A pair of final single player screen with parent.
	 * @param waitingScreen A pair of waiting screen with parent.
	 * @param adminInterfaceScreen A pair of admin interface screen with parent.
	 * @param adminAddActivityScreen A pair of admin add activity screen with parent.
	 * @param adminRemoveActivityScreen A pair of admin remove activity screen with parent.
	 * @param adminEditActivityScreen A pair of admin edit activity screen with parent.
	 * @param topPlayersLeaderboard A pair of top players leaderboard scene with parent.
	 * @param multiPlayerQuestion A pair of multiplayer.
	 * @param intLeaderboard A pair of intermediate leaderboard question screen with parent.
	 */
	public void initialize(
		Stage primaryStage,
		Pair<SinglePlayerPreGameCtrl, Parent> singlePlayer,
		Pair<MultiplayerPreGameCtrl, Parent> multiPlayer,
		Pair<SplashCtrl, Parent> splash,
		Pair<QuestionScreenSinglePlayerCtrl, Parent> questionScreenSinglePlayer,
		Pair<GlobalLeaderboardScreenCtrl, Parent> globalLeaderBoard,
		Pair<IntermediateSceneCtrl, Parent> intermediateScene,
		Pair<SinglePlayerFinalScreenCtrl, Parent> singlePlayerFinalScene,
		Pair<WaitingScreenCtrl, Parent> waitingScreen,
		Pair<AdminInterfaceScreenCtrl, Parent> adminInterfaceScreen,
		Pair<AdminAddActivityScreenCtrl, Parent> adminAddActivityScreen,
		Pair<AdminRemoveActivityScreenCtrl, Parent> adminRemoveActivityScreen,
		Pair<AdminEditActivityScreenCtrl, Parent> adminEditActivityScreen,
		Pair<MultiplayerQuestionScreenCtrl, Parent> multiPlayerQuestion,
		Pair<IntLeaderboardCtrl, Parent> intLeaderboard,
		Pair<TopPlayersLeaderboardCtrl, Parent> topPlayersLeaderboard
	) {
		this.logger = LoggerFactory.getLogger(MainCtrl.class);

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
		this.globalLeaderboard = new Scene(globalLeaderBoard.getValue());

		this.intermediateSceneCtrl = intermediateScene.getKey();
		this.intermediateScene = new Scene(intermediateScene.getValue());

		this.singlePlayerFinalSceneCtrl = singlePlayerFinalScene.getKey();
		this.singlePlayerFinalScene = new Scene(singlePlayerFinalScene.getValue());

		this.waitingScreenCtrl = waitingScreen.getKey();
		this.waitingScreen = new Scene(waitingScreen.getValue());

		this.adminInterfaceScreenCtrl = adminInterfaceScreen.getKey();
		this.adminInterfaceScreen = new Scene(adminInterfaceScreen.getValue());

		this.adminAddActivityScreenCtrl = adminAddActivityScreen.getKey();
		this.adminAddActivityScreen = new Scene(adminAddActivityScreen.getValue());

		this.adminRemoveActivityScreenCtrl = adminRemoveActivityScreen.getKey();
		this.adminRemoveActivityScreen = new Scene(adminRemoveActivityScreen.getValue());

		this.adminEditActivityScreenCtrl = adminEditActivityScreen.getKey();
		this.adminEditActivityScreen = new Scene(adminEditActivityScreen.getValue());

		this.topPlayersLeaderboardCtrl = topPlayersLeaderboard.getKey();
		this.topPlayersLeaderboard = new Scene(topPlayersLeaderboard.getValue());

		this.multiplayerQuestionScreenCtrl = multiPlayerQuestion.getKey();
		this.multiPlayerQuestionScreen = new Scene(multiPlayerQuestion.getValue());

		this.intLeaderboardCtrl = intLeaderboard.getKey();
		this.intermediateLeaderboardScreen = new Scene(intLeaderboard.getValue());

		this.setEventHandlerForClosure();
		this.closeConfirmation();

		this.multiplayerQuestionScreenCtrl.setMainCtrl(this);

		this.showSplashScreen();
		this.primaryStage.show();
	}

	/**
	 * Sets the event for closure.
	 */
	private void closeConfirmation() {
		this.primaryStage.setOnCloseRequest(event ->
			this.primaryStage.fireEvent(
				new WindowEvent(
					this.primaryStage,
					WindowEvent.WINDOW_CLOSE_REQUEST
				)
			)
		);
	}

	/**
	 * Setter for the event handler when the user requests to exit the screen.
	 */
	private void setEventHandlerForClosure() {
		this.confirmCloseEventHandler = event -> {
			Alert closeConfirmation = new Alert(
				Alert.AlertType.CONFIRMATION,
				"Are you sure you want to exit?"
			);
			Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
				ButtonType.OK
			);
			exitButton.setText("Yes");
			closeConfirmation.setHeaderText("Confirm Exit");
			closeConfirmation.initModality(Modality.APPLICATION_MODAL);
			closeConfirmation.initOwner(this.primaryStage);

			Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
			if (!ButtonType.OK.equals(closeResponse.get())) {
				event.consume();
			} else if (this.server != null && this.server.getConnection() != null){
				try {
					this.server.getConnection().send(new KillerMessage());
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
		};
	}

	/**
	 * This method shows up the splash screen.
	 */
	public void showSplashScreen() {
		this.primaryStage.setTitle("Quizzzz");
		this.primaryStage.setScene(this.splashScreen);
		this.primaryStage.setOnCloseRequest(this.confirmCloseEventHandler);
	}

	/**
	 * This method shows up the single player pre-game screen.
	 */
	public void showSinglePlayerPreGameScreen() {
		this.primaryStage.setTitle("Singleplayer");
		this.primaryStage.setScene(this.singlePlayerPreGameScreen);
	}

	/**
	 * This method shows up multiplayer pre-game screen.
	 */
	public void showMultiplePlayersPreGameScreen() {
		this.primaryStage.setTitle("Multiplayer");
		this.primaryStage.setScene(this.multiplayerPreGameScreen);
	}

	/**
	 * Show the waiting screen.
	 * @param firstResponse The lobby response.
	 */
	public void showWaitingScreen(LobbyResponse firstResponse) {
		this.primaryStage.setTitle("Waiting Lobby");
		this.primaryStage.setScene(this.waitingScreen);
		this.waitingScreenCtrl.beginActiveRefresh();
	}

	/**
	 * This method shows up the question screen in single player mode.
	 * @param isSingleplayer Whether the current game mode is single player or not.
	 */
	public void showQuestionScreen(boolean isSingleplayer) throws IOException {
		QuestionClass screenCtrl;

		if (isSingleplayer) {
			screenCtrl = this.questionScreenSinglePlayerCtrl;
		} else {
			screenCtrl = this.multiplayerQuestionScreenCtrl;
			this.server.registerForMessages("/message/receive", MessageModel.class, messageModel ->
				this.multiplayerQuestionScreenCtrl.updateMessage(messageModel.getMessage())
			);
		}

		// Enable all buttons to make sure the player can answer
		screenCtrl.disableButtons(false);

		// If the size of question set equals to zero, this method change to final screen.
		if (this.questions.isEmpty()) {
			screenCtrl.showFinalScreen();
			return;
		}

		this.clearButtons(screenCtrl);	// change back the color of the buttons
		screenCtrl.setInputButton(null);
		screenCtrl.setInputText(null);

		// Assign the player variable that got from
		// SinglePlayerPreGame to player variable in MainCtrl
		this.player = screenCtrl.getPlayer();
		screenCtrl.setProgress(1f);

		// This timeline will execute on another thread - run the count-down timer.
		this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), _e ->
			screenCtrl.decreaseProgress(0.1f)
		));
		this.timeline.setOnFinished(_e -> {
			try {
				this.updatePoints(
					screenCtrl.getInputButton(),
					screenCtrl.getInputText(),
					screenCtrl
				);
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
		this.timeline.setCycleCount(10);
		this.timeline.setOnFinished(_e -> {
			try {
				this.updatePoints(
					screenCtrl.getInputButton(),
					screenCtrl.getInputText(),
					screenCtrl
				);
			} catch (IOException err) {
				err.printStackTrace();
			}
		});
		this.timeline.play();
		this.primaryStage.setTitle("Question");

		// Get first question of the set and delete that question.
		this.question = this.questions.get(0);
		this.questions.remove(0);

		this.numberOfQuestionsAnswered++;
		if (this.question instanceof EstimateQuestion) {
			this.setUpEstimateQuestion((EstimateQuestion) this.question, screenCtrl);
		} else if (this.question instanceof HighestConsumptionQuestion) {
			this.setUpHighestQuestion((HighestConsumptionQuestion) this.question, screenCtrl);
		} else if (this.question instanceof MCQuestion) {
			this.setUpMultipleChoice((MCQuestion) this.question, screenCtrl);
		} else if (this.question instanceof InsteadOfQuestion) {
			this.setUpInsteadQuestion((InsteadOfQuestion) this.question, screenCtrl);
		}

		this.primaryStage.setScene(screenCtrl.getScene());
	}

	public ServerUtils getServer() {
		return this.server;
	}

	/**
	 * Set up a thread which listens to the connection from the server for messages.
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
							((LeaderboardMessage) message).getPlayers()
						);
						break;
					case JOKER:
						JokerMessage jokerMessage = (JokerMessage) message;
						if (jokerMessage.getJokerType() == JokerType.DECREASE) {
							this.multiplayerQuestionScreenCtrl.decreaseProgress(
								this.multiplayerQuestionScreenCtrl.getProgress()
									* JOKER_DECREASE_TIME_PERCENT
							);
						}
						break;
					case JOIN:
					case ERROR:
						this.logger.error(
							"Received error message: "
								+ ((ErrorMessage) message).getError()
						);
						break;
					case KILLER:
						return;
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
	 * This method will get a list of questions.
	 */
	public void getQuestions() {
		this.questions = this.server.getQuestions(this.seed);
	}

	/**
	 * This method sets up the multiple choice question.
	 * @param question Multiple choice question.
	 * @param screenCtrl The screen controller which handles the task.
	 * @throws IOException There was a problem with parsing.
	 */
	public void setUpMultipleChoice(
		MCQuestion question,
		QuestionClass screenCtrl
	) throws IOException {
		this.hideTextFieldAndRevealButtons(screenCtrl);

		// Set up label for the question and answers.
		String questionText = question.getActivity().getTitle();
		screenCtrl.setUpLabel(questionText);
		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			this.clearImages();
			screenCtrl.setImageQuestionImageView(question.imageInByteArrayQuestion());
			screenCtrl.setVisibleImageQuestion(true);
		}
		screenCtrl.setLabelButton1(Long.toString(question.getAnswer1()));
		screenCtrl.setLabelButton2(Long.toString(question.getAnswer2()));
		screenCtrl.setLabelButton3(Long.toString(question.getAnswer3()));
		screenCtrl.setVisibleEstimateAnswer(false);
		screenCtrl.setVisibleTextField(false);
	}

	/**
	 * This method sets up the "instead of" question.
	 * @param question "instead of" question.
	 * @param screenCtrl The screen controller which handles the task.
	 * @throws IOException There was a problem with parsing.
	 */
	public void setUpInsteadQuestion(
		InsteadOfQuestion question,
		QuestionClass screenCtrl
	) throws IOException {
		this.hideTextFieldAndRevealButtons(screenCtrl);
		// Set up label for the question and answers
		String questionText = question.getQuestionActivity().getTitle();
		screenCtrl.setUpLabel(questionText);
		screenCtrl.setLabelButton1(question.answerString(1));
		screenCtrl.setLabelButton2(question.answerString(2));
		screenCtrl.setLabelButton3(question.answerString(3));
		screenCtrl.setVisibleEstimateAnswer(false);

		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			this.clearImages();
			screenCtrl.setImageQuestionImageView(question.imageInByteArrayQuestion());
			screenCtrl.setVisibleImageQuestion(true);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(1), 0);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(2), 1);
			screenCtrl.setVisibilityImageView(true, 1);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(3), 2);
			screenCtrl.setVisibilityImageView(true, 2);
			screenCtrl.setVisibilityImageView(true, 0);
			screenCtrl.setVisibleTextField(false);
		}
	}

	/**
	 * This method sets up the highest consumption question.
	 * @param question Highest consumption question.
	 * @param screenCtrl The screen controller which handles the task.
	 * @throws IOException There was a problem with parsing.
	 */
	public void setUpHighestQuestion(
		HighestConsumptionQuestion question,
		QuestionClass screenCtrl
	) throws IOException {
		this.hideTextFieldAndRevealButtons(screenCtrl);

		// Set up label for the question and answers.
		String questionText = "Which one of these activities consumes the most energy?";
		screenCtrl.setUpLabel(questionText);
		screenCtrl.setLabelButton1(question.getActivity1Title());
		screenCtrl.setLabelButton2(question.getActivity2Title());
		screenCtrl.setLabelButton3(question.getActivity3Title());
		screenCtrl.setVisibleEstimateAnswer(false);

		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			this.clearImages();
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArrayActivity3(), 2);
			screenCtrl.setVisibilityImageView(true, 2);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArrayActivity2(), 1);
			screenCtrl.setVisibilityImageView(true, 1);
			screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArrayActivity1(), 0);
			screenCtrl.setVisibilityImageView(true, 0);
			screenCtrl.setVisibleTextField(false);
		}
	}

	/**
	 * This method hide the text field and reveal the buttons.
	 * @param screenCtrl Controller which dictates whether the singleplayer or the multiplayer
	 *                   screen changes.
	 */
	public void hideTextFieldAndRevealButtons(QuestionClass screenCtrl) {
		screenCtrl.setVisibleButton1(true);
		screenCtrl.setVisibleButton2(true);
		screenCtrl.setVisibleButton3(true);
		screenCtrl.setVisibleTextField(false);
	}

	/**
	 * This method sets up the estimate question.
	 * @param question The estimate question.
	 * @param screenCtrl The screen controller which handles the task.
	 */
	public void setUpEstimateQuestion(
		EstimateQuestion question,
		QuestionClass screenCtrl
	) throws IOException {
		String questionText = question.getActivityTitle();
		screenCtrl.setUpLabel(questionText);

		if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
			this.clearImages();
			screenCtrl.setImageQuestionImageView(question.imageInByteArrayQuestion());
			screenCtrl.setVisibleImageQuestion(true);
		}
		screenCtrl.setVisibleTextField(true);
		screenCtrl.setVisibleButton1(false);
		screenCtrl.setVisibleButton2(false);
		screenCtrl.setVisibleButton3(false);
		screenCtrl.setVisibleEstimateAnswer(false);
	}

	/**
	 * Hides all the visible imageViews.
	 */
	public void clearImages() {
		this.questionScreenSinglePlayerCtrl.setVisibleImageQuestion(false);
		this.questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 0);
		this.questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 1);
		this.questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 2);
	}

	/**
	 * Take the user to the admin interface screen.
	 */
	public void showAdminInterfaceScreen() {
		this.primaryStage.setTitle("Admin Interface");
		this.primaryStage.setScene(this.adminInterfaceScreen);
	}

	/**
	 * Take the user to the admin add activity screen.
	 */
	public void showAdminAddActivityScreen() {
		this.refreshActivities();
		this.primaryStage.setTitle("Add Activity");
		this.primaryStage.setScene(this.adminAddActivityScreen);
	}

	/**
	 * Take the user to the admin remove activity screen.
	 */
	public void showAdminRemoveActivityScreen() {
		this.refreshActivities();
		this.primaryStage.setTitle("Remove Activity");
		this.primaryStage.setScene(this.adminRemoveActivityScreen);
	}

	/**
	 * Take the user to the admin remove activity screen.
	 */
	public void showAdminEditActivityScreen() {
		this.refreshActivities();
		this.primaryStage.setTitle("Edit Activity");
		this.primaryStage.setScene(this.adminEditActivityScreen);
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

	/**
	 * Calls the refresh activities method from the AdminEditActivitiesCtrl class, which refresh
	 * the activities shown in the admin interface screens according to the current status
	 * of the database.
	 */
	public void refreshActivities() {
		this.activities = this.server
			.getActivities()
			.stream()
			.sorted(Comparator.comparing(Activity::getId))
			.toList();
		this.adminRemoveActivityScreenCtrl.updateDropdown(this.activities);
		this.adminEditActivityScreenCtrl.updateDropdown(this.activities);
	}

	/**
	 * This method shows the global leader board screen.
	 */
	public void showGlobalLeaderboardScreen() {
		this.globalLeaderboardScreenCtrl.getItems();
		this.primaryStage.setTitle("Leaderboard");
		this.primaryStage.setScene(this.globalLeaderboard);
	}

	/**
	 * This method shows the final screen.
	 */
	public void showSinglePlayerFinalScreen() {
		this.singlePlayerFinalSceneCtrl.setTotalScore(this.player.getPoint());
		this.singlePlayerFinalSceneCtrl.setCorrectAnswers(this.numberOfCorrectAnswers);
		this.singlePlayerFinalSceneCtrl.addPlayer(this.player);
		this.primaryStage.setTitle("Final Score");
		this.primaryStage.setScene(this.singlePlayerFinalScene);
		this.questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
	}

	/**
	 * This method shows the final screen for multiplayer.
	 */
	public void showMultiPlayerFinalScreen() {
		// TODO show final screen for multiplayer
		throw new NotImplementedException();
	}

	/**
	 * This method reveals the answer after the player clicked on the button.
	 * @param button The button.
	 * @param textField The text field.
	 * @param screenCtrl Screen controller which can be either for singleplayer or
	 *                   for multiplayer.
	 */
	public void updatePoints(Button button, TextField textField, QuestionClass screenCtrl)
		throws IOException {
		this.timeline.stop();

		// In case the player doesn't provide an answer in time
		if (button == null && textField == null) {
			this.currentPoints = 0;
			screenCtrl.setProgress(1);
			this.showAnswer(screenCtrl);
			return;
		}

		// Get the time the player used for guessing the answer
		double timePassed = screenCtrl.getTimestamp();

		if (this.question instanceof MCQuestion multipleChoiceQuestion) {
			// The point which the player will receive after answered the question
			this.currentPoints = multipleChoiceQuestion.pointsEarned(
				1000,
				Integer.parseInt(button.getText()),
				timePassed,
				this.doublePointsUsed == 1
			);

			// Set the point for the player
			this.player.setPoint(this.player.getPoint() + this.currentPoints);

			// If the player clicked on the correct answer,
			// number of correct answers would be increased.
			if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
					== Long.parseLong(button.getText())) {
				this.numberOfCorrectAnswers++;
			}
		} else if (this.question instanceof HighestConsumptionQuestion highConsumptionQuestion) {
			int buttonId = button.getId().charAt(button.getId().length() - 1) - '0';
			currentPoint = highConsumptionQuestion.pointsEarned(
				1000,
				buttonId,
				timePassed,
				this.doublePointsUsed == 1
			);

			player.setPoint(player.getPoint() + currentPoint);

			if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
					== highConsumptionQuestion.returnEnergyConsumption(button.getText())) {
				this.numberOfCorrectAnswers++;
			}
		} else if (this.question instanceof InsteadOfQuestion insteadQuestion) {
			this.currentPoints = insteadQuestion.pointsEarned(
				1000,
				Integer.parseInt(String.valueOf(button.getId().charAt(12))),
				timePassed,
				this.doublePointsUsed == 1
			);
			this.player.setPoint(this.player.getPoint() + this.currentPoints);
			if (insteadQuestion.correctAnswer().getConsumptionInWh()
					== insteadQuestion.returnEnergyConsumption(button.getText())) {
				this.numberOfCorrectAnswers++;
			}
		} else if (this.question instanceof EstimateQuestion estimateQuestion) {
			currentPoint = estimateQuestion.pointsEarned(
				1000,
				Integer.parseInt(textField.getText()),
				timePassed,
				this.doublePointsUsed == 1
			);
			this.player.setPoint(this.player.getPoint() + this.currentPoint);
		}
		if (this.doublePointsUsed == 1) {
			this.doublePointsUsed++;
		}
		this.server.getConnection().send(new PointMessage(this.nickname, this.player.getPoint()));
		this.showAnswer(screenCtrl);
	}

	/**
	 * The methods resets the answer buttons to their initial state.
	 * @param screenCtrl Screen controller which can be either for singleplayer or for multiplayer.
	 */
	public void clearButtons(QuestionClass screenCtrl) {
		String color = "-fx-background-color: #5b9ad5; -fx-background-radius: 15;";

		screenCtrl.setStyleAnswerButton1(color);
		screenCtrl.setStyleAnswerButton2(color);
		screenCtrl.setStyleAnswerButton3(color);
	}

	/**
	 * Method that shows the correct answer after the timer is finished.  The correct answer will be
	 * colored with green.  If the player choses the wrong answer, his choice will be colored with
	 * red.
	 * @param screenCtrl Screen controller which can be either for singleplayer or for multiplayer.
	 */
	private void showAnswer(QuestionClass screenCtrl) {
		Button button = screenCtrl.getInputButton();
		TextField textField = screenCtrl.getInputText();

		this.timeline.stop();

		screenCtrl.setProgress(1f);

		// This timeline will execute on another thread - run the count-down timer.
		this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), _e ->
			screenCtrl.decreaseProgress(1 / 3f)
		));
		this.timeline.setCycleCount(3);
		this.timeline.setOnFinished(_e -> screenCtrl.showIntermediateScene());
		this.timeline.play();

		if (button != null) {
			String color = "-fx-background-color: #E37474; -fx-background-radius: 15;";
			button.setStyle(color);
		}

		String color = "-fx-background-color: #4BA85D; -fx-background-radius: 15;";

		if (this.question instanceof MCQuestion multipleChoiceQuestion) {
			long button1 = multipleChoiceQuestion.getAnswer1();
			long button2 = multipleChoiceQuestion.getAnswer2();
			long button3 = multipleChoiceQuestion.getAnswer3();
			long correctAnswer = multipleChoiceQuestion.getActivity().getConsumptionInWh();

			if (correctAnswer == button1) {
				screenCtrl.setStyleAnswerButton1(color);
			} else if (correctAnswer == button2) {
				screenCtrl.setStyleAnswerButton2(color);
			} else if (correctAnswer == button3) {
				screenCtrl.setStyleAnswerButton3(color);
			}
		} else if (this.question instanceof HighestConsumptionQuestion highConsumptionQuestion) {
			Activity activity1 = highConsumptionQuestion.getActivity1();
			Activity activity2 = highConsumptionQuestion.getActivity2();
			Activity activity3 = highConsumptionQuestion.getActivity3();
			long correctAnswer = highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh();

			if (correctAnswer == activity1.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton1(color);
			} else if (correctAnswer == activity2.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton2(color);
			} else if (correctAnswer == activity3.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton3(color);
			}
		} else if (this.question instanceof InsteadOfQuestion insteadQuestion) {
			Activity answer1 = insteadQuestion.getAnswer1();
			Activity answer2 = insteadQuestion.getAnswer2();
			Activity answer3 = insteadQuestion.getAnswer3();
			long correctAnswer = insteadQuestion.correctAnswer().getConsumptionInWh();

			if (correctAnswer == answer1.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton1(color);
			} else if (correctAnswer == answer2.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton2(color);
			} else if (correctAnswer == answer3.getConsumptionInWh()) {
				screenCtrl.setStyleAnswerButton3(color);
			}
		} else if (this.question instanceof EstimateQuestion estimateQuestion) {
			String message;
			screenCtrl.setVisibleEstimateAnswer(true);

			if (textField != null && !textField.getText().equals("")) {
				this.currentPoints = estimateQuestion.pointsEarned(
					1000,
					Integer.parseInt(textField.getText()),
					screenCtrl.getTimestamp(),
					this.doublePointsUsed == 2
				);
			} else {
				this.currentPoints = 0;
			}

			int stylingPoints = this.doublePointsUsed == 1
				? this.currentPoint / 2
				: this.currentPoint;
			if (this.doublePointsUsed == 2) {
				this.doublePointsUsed++;
			}

			if (stylingPoints < 800) {
				if (stylingPoints > 300) {
					color = "-fx-background-color: #f0de8d; -fx-background-radius: 15;";
					message = "Not bad!";
				} else {
					color = "-fx-background-color: #E37474; -fx-background-radius: 15;";
					message = "Oh!";
				}
			} else {
				message = "Well done!";
			}
			// This here is necessary in case when the joker has been used on non-Estimate question
			// Then on the next styling for Estimate Question it will colour it incorrectly
			if (this.doublePointsUsed == 1) this.doublePointsUsed = 2;

			screenCtrl.setEstimateAnswerStyle(color);
			screenCtrl.setEstimateAnswerLabel(
				message
				+ " The correct answer is: "
				+ estimateQuestion.getActivity().getConsumptionInWh()
			);

			if (textField != null) {
				textField.clear();
			}
		}
	}

	/**
	 * Sets up the jokers in the beginning of the multiplayer game
	 */
	public void setUpJokers() {
		this.multiplayerQuestionScreenCtrl.setAllJokersUp();
	}

	/**
	 * Getter method for the singleplayer pre game controller controller.
	 * @return The singleplayer pre game controller.
	 */
	public SinglePlayerPreGameCtrl getSinglePlayerPreGameCtrl() {
		return this.singlePlayerPreGameCtrl;
	}

	/**
	 * Getter method for the singleplayer pre game controller screen.
	 * @return The singleplayer pre game screen.
	 */
	public Scene getSinglePlayerPreGameScreen() {
		return this.questionScreenSinglePlayer;
	}

	/**
	 * Getter method for multiplayer pre game controller.
	 * @return Multiplayer pre game controller.
	 */
	public MultiplayerPreGameCtrl getMultiplayerPreGameCtrl() {
		return this.multiplayerPreGameCtrl;
	}

	/**
	 * Getter method for the multiplayer question screen.
	 * @return The multiplayer question screen.
	 */
	public Scene getMultiplayerQuestionScreen() {
		return this.multiPlayerQuestionScreen;
	}

	/**
	 * Getter method for intermediate screen controller.
	 * @return Intermediate screen controller.
	 */
	public IntermediateSceneCtrl getIntermediateSceneCtrl() {
		return this.intermediateSceneCtrl;
	}

	/**
	 * Getter method for the number of questions answered this far.
	 * @return The number of questions answered this far.
	 */
	public int getNumberOfQuestionsAnswered() {
		return this.numberOfQuestionsAnswered;
	}

	/**
	 * Getter method for the player.
	 * @return The player.
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Getter method for the number of points.
	 * @return The number of points received at the last question answered.
	 */
	public int getCurrentPoints() {
		return this.currentPoints;
	}

	/**
	 * Getter method for the primary stage.
	 * @return The primary stage.
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * Getter method for the intermediate scene.
	 * @return The intermediate scene.
	 */
	public Scene getIntermediateScene() {
		return this.intermediateScene;
	}

	/**
	 * Getter method for the intermediate leaderboard scene.
	 * @return The intermediate leaderboard scene.
	 */
	public Scene getIntermediateLeaderboardScreen() {
		return this.intermediateLeaderboardScreen;
	}

	/**
	 * Getter method for the intermediate leaderboard controller.
	 * @return The intermediate leaderboard controller.
	 */
	public IntLeaderboardCtrl getIntermediateLeaderboardCtrl() {
		return this.intLeaderboardCtrl;
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
		this.primaryStage.setTitle("Final Leaderboard");
		this.primaryStage.setScene(this.topPlayersLeaderboard);
	}

	/**
	 * Setter for the player nickname.
	 * @param nickname The nickname selected by the player.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Getter for the player's nickname.
	 * @return The player's nickname as a String.
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * Setter for whether double points should be given.
	 * @param doublePointsUsed The new double points.
	 */
	public void setDoublePointsUsed(int doublePointsUsed) {
		this.doublePointsUsed = doublePointsUsed;
	}

	/**
	 * This method calls the method joinLobby from the multiplayerPreGameCtrl class, which handles
	 * the player joining a lobby feature.
	 */
	public void joinLobby() {
		this.multiplayerPreGameCtrl.joinLobby();
	}

	/**
	 * Changes the scene to intermediate leaderboard.
	 */
	public void changeToLeaderboard() {
		this.primaryStage.setScene(this.intermediateLeaderboardScreen);
		this.primaryStage.setTitle("Leaderboard");
		this.intLeaderboardCtrl.displayScores();
	}

	/**
	 * Getter method for the multiplayer questions controller.
	 * @return Multiplayer questions controller.
	 */
	public MultiplayerQuestionScreenCtrl getMultiplayerQuestionScreenCtrl() {
		return this.multiplayerQuestionScreenCtrl;
	}

	public void renderTheMessageInTheChatBox(String message) {
		this.multiplayerQuestionScreenCtrl.updateMessage(message);
	}

	public void setGameIdInMultiplayerQuestionScreen(int gameId) {
		this.multiplayerQuestionScreenCtrl.setGameId(gameId);
	}
}