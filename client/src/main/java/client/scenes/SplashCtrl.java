package client.scenes;


import com.google.inject.Inject;


public class SplashCtrl{

	private MainCtrl mainCtrl;

	@Inject
	/**
	 * Constructor for splash controller.
	 *
	 * @param mainCtrl the injected main controller.
	 */
	public SplashCtrl(MainCtrl mainCtrl){
		this.mainCtrl = mainCtrl;
	}

	/**
	 * the method change the splash screen to single player pre-game
	 * screen when you click on single player button.
	 */
	public void changeToSinglePlayer() {
		mainCtrl.showSinglePlayerPreGameScreen();
	}

	/**
	 * the method change the splash screen to multiplayer pre-game
	 * screen when you click on single player button.
	 */
	public void changeToMultiplayer() {
		mainCtrl.showMultiplePlayersPreGameScreen();
	}

	/**
	 * the method change the splash screen to
	 * global leader board screen when you click on single player button.
	 */
	public void changeToLeaderboard() {
		mainCtrl.showGlobalLeaderboardScreen();
	}

	public void showHelp() {
	}

}
