package client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import client.scenes.AdminAddActivityScreenCtrl;
import client.scenes.AdminEditActivityScreenCtrl;
import client.scenes.AdminInterfaceScreenCtrl;
import client.scenes.AdminRemoveActivityScreenCtrl;
import client.scenes.GlobalLeaderboardScreenCtrl;
import client.scenes.IntLeaderboardCtrl;
import client.scenes.IntermediateSceneCtrl;
import client.scenes.MainCtrl;
import client.scenes.MultiplayerPreGameCtrl;
import client.scenes.MultiplayerQuestionScreenCtrl;
import client.scenes.QuestionScreenSinglePlayerCtrl;
import client.scenes.SinglePlayerFinalScreenCtrl;
import client.scenes.SinglePlayerPreGameCtrl;
import client.scenes.SplashCtrl;
import client.scenes.TopPlayersLeaderboardCtrl;
import client.scenes.WaitingScreenCtrl;
import static commons.Utility.nullOrEmpty;

import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import static com.google.inject.Guice.createInjector;

public class Main extends Application {
	private static final Injector INJECTOR = createInjector(new Module());
	private static final FXML FXML = new FXML(INJECTOR);

	public static String serverHost = "localhost";
	private static final String CONFIG_FILE = "quizzzz.config";

	public static void main(String[] args) throws URISyntaxException, IOException {
		try {
			String contents = Files.readString(Path.of(CONFIG_FILE));
			if (!nullOrEmpty(contents)) {
				// Editing a static is bad practice,
				// but this is the only place where it changes.
				// This is the simplest way to pass the URL to the rest of the program.
				serverHost = contents;
				System.out.println("loaded config file");
			}
		} catch (IOException _err) {}

		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		var singlePlayer = FXML.load(SinglePlayerPreGameCtrl.class, "SinglePlayerPreGame.fxml");
		var multiPlayer = FXML.load(MultiplayerPreGameCtrl.class, "MultiplayerPreGame.fxml");
		var splashScreen = FXML.load(SplashCtrl.class, "SplashScreen.fxml");
		var singlePlayerQuestion = FXML.load(
			QuestionScreenSinglePlayerCtrl.class,
			"QuestionScreenSinglePlayer.fxml"
		);
		var globalLeaderBoard = FXML.load(
			GlobalLeaderboardScreenCtrl.class,
			"GlobalLeaderBoardScreen.fxml"
		);
		var intermediateScene = FXML.load(IntermediateSceneCtrl.class, "IntermediateScreen.fxml");
		var singlePlayerFinalScene = FXML.load(
			SinglePlayerFinalScreenCtrl.class,
			"SinglePlayerFinalScreen.fxml"
		);
		var waitingScreen = FXML.load(WaitingScreenCtrl.class, "WaitingScreen.fxml");
		var adminInterfaceScreen = FXML.load(
			AdminInterfaceScreenCtrl.class,
			"AdminInterfaceScreen.fxml"
		);
		var adminAddActivityScreen = FXML.load(
			AdminAddActivityScreenCtrl.class,
			"AdminAddActivityScreen.fxml"
		);
		var adminRemoveActivityScreen = FXML.load(
			AdminRemoveActivityScreenCtrl.class,
			"AdminRemoveActivityScreen.fxml"
		);
		var adminEditActivityScreen = FXML.load(
			AdminEditActivityScreenCtrl.class,
			"AdminEditActivityScreen.fxml"
		);
		var topPlayersLeaderboard = FXML.load(
			TopPlayersLeaderboardCtrl.class,
			"TopPlayersLeaderboard.fxml"
		);
		var multiPlayerQuestion = FXML.load(
			MultiplayerQuestionScreenCtrl.class,
			"MultiplayerQuestionScreen.fxml"
		);
		var intermediateLeaderboard = FXML.load(
			IntLeaderboardCtrl.class,
			"IntermediateLeaderboard.fxml"
		);
		var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
		mainCtrl.initialize(
			primaryStage,
			singlePlayer,
			multiPlayer,
			splashScreen,
			singlePlayerQuestion,
			globalLeaderBoard,
			intermediateScene,
			singlePlayerFinalScene,
			waitingScreen,
			adminInterfaceScreen,
			adminAddActivityScreen,
			adminRemoveActivityScreen,
			adminEditActivityScreen,
			multiPlayerQuestion,
			intermediateLeaderboard,
			topPlayersLeaderboard
		);
	}
}