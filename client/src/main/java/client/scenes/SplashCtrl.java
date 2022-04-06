package client.scenes;

import client.utils.SoundHandler;

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
		SoundHandler.clickSound();
		this.mainCtrl.showSinglePlayerPreGameScreen();
	}

	public void changeToMultiplayer() {
		SoundHandler.clickSound();
		this.mainCtrl.showMultiplePlayersPreGameScreen();
	}

	public void changeToLeaderboard() {
		SoundHandler.clickSound();
		this.mainCtrl.showGlobalLeaderboardScreen();
	}

	public void changeToAdminInterface() {
		SoundHandler.clickSound();
		this.mainCtrl.showAdminInterfaceScreen();
	}

	public void showHelp() {
		// TODO
		throw new NotImplementedException();
	}
}