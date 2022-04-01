package client.scenes;

import com.google.inject.Inject;
import org.apache.commons.lang3.NotImplementedException;

public record SplashCtrl(MainCtrl mainCtrl) {
	/**
	 * Constructor for splash controller.
	 * @param mainCtrl the injected main controller.
	 */
	@Inject
	public SplashCtrl {}

	public void changeToSinglePlayer() {
		this.mainCtrl.showSinglePlayerPreGameScreen();
	}

	public void changeToMultiplayer() {
		this.mainCtrl.showMultiplePlayersPreGameScreen();
	}

	public void changeToLeaderboard() {
		this.mainCtrl.showGlobalLeaderboardScreen();
	}

	public void changeToAdminInterface() {
		this.mainCtrl.showAdminInterfaceScreen();
	}

	public void showHelp() {
		// TODO
		throw new NotImplementedException();
	}
}