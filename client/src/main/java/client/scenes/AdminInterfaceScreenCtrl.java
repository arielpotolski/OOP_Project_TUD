package client.scenes;

import client.utils.SoundHandler;

import javafx.fxml.FXML;
import javax.inject.Inject;

public record AdminInterfaceScreenCtrl(MainCtrl mainCtrl) {
	@Inject
	public AdminInterfaceScreenCtrl {}

	/**
	 * Take the user to the splash screen.
	 */
	@FXML
	private void jumpToSplashScreen() {
		SoundHandler.clickSound();
		this.mainCtrl.showSplashScreen();
	}

	/**
	 * Take the user to the add activity screen.
	 */
	@FXML
	private void jumpToAddActivityScreen() {
		SoundHandler.clickSound();
		this.mainCtrl.showAdminAddActivityScreen();
	}

	/**
	 * Take the user to the remove activity screen.
	 */
	@FXML
	private void jumpToRemoveActivityScreen() {
		SoundHandler.clickSound();
		this.mainCtrl.showAdminRemoveActivityScreen();
	}

	/**
	 * Take the user to the edit activity screen.
	 */
	@FXML
	private void jumpToEditActivityScreen() {
		SoundHandler.clickSound();
		this.mainCtrl.showAdminEditActivityScreen();
	}
}