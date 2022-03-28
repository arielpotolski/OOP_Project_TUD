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
package client;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.GlobalLeaderboardScreenCtrl;
import client.scenes.IntermediateLeaderboardCtrl;
import client.scenes.IntermediateSceneCtrl;
import client.scenes.MainCtrl;
import client.scenes.MultiplayerPreGameCtrl;
import client.scenes.MultiplayerQuestionScreenCtrl;
import client.scenes.QuestionScreenSinglePlayerCtrl;
import client.scenes.SinglePlayerFinalScreenCtrl;
import client.scenes.SinglePlayerPreGameCtrl;
import client.scenes.SplashCtrl;
import client.scenes.WaitingScreenCtrl;

import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import static com.google.inject.Guice.createInjector;

public class Main extends Application {
	private static final Injector INJECTOR = createInjector(new MyModule());
	private static final MyFXML FXML = new MyFXML(INJECTOR);

	public static void main(String[] args) throws URISyntaxException, IOException {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		var singlePlayer = FXML.load(SinglePlayerPreGameCtrl.class,"client","scenes",
				"SinglePlayerPreGame.fxml");
		var multiPlayer = FXML.load(MultiplayerPreGameCtrl.class,"client","scenes",
				"MultiplayerPreGame.fxml");
		var splashScreen = FXML.load(SplashCtrl.class,"client","scenes",
				"SplashScreen.fxml");
		var singlePlayerQuestion = FXML.load(QuestionScreenSinglePlayerCtrl.class,"client","scenes",
				"QuestionScreenSinglePlayer.fxml");
		var globalLeaderBoard = FXML.load(GlobalLeaderboardScreenCtrl.class,"client","scenes",
				"GlobalLeaderBoardScreen.fxml");
		var intermediateScene = FXML.load(IntermediateSceneCtrl.class,"client","scenes",
				"IntermediateScreen.fxml");
		var singlePlayerFinalScene = FXML.load(SinglePlayerFinalScreenCtrl.class,"client","scenes",
				"SinglePlayerFinalScreen.fxml");
		var waitingScreen = FXML.load(WaitingScreenCtrl.class,
				"client", "scenes", "WaitingScreen.fxml");
		var questionScreenMultiplayer = FXML.load(MultiplayerQuestionScreenCtrl.class,
				"client", "scenes", "MultiplayerQuestionScreen.fxml");
		var intermediateLeaderboard = FXML.load(IntermediateLeaderboardCtrl.class,
				"client", "scenes", "IntermediateLeaderboard.fxml");
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
			questionScreenMultiplayer,
			intermediateLeaderboard
		);
	}
}
	
