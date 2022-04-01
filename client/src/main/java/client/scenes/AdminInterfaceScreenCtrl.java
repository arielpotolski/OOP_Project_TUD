package client.scenes;

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
		this.mainCtrl.showSplashScreen();
	}

	/**
	 * Take the user to the add activity screen.
	 */
	@FXML
	private void jumpToAddActivityScreen() {
		this.mainCtrl.showAdminAddActivityScreen();
	}

	/**
	 * Take the user to the remove activity screen.
	 */
	@FXML
	private void jumpToRemoveActivityScreen() {
		this.mainCtrl.showAdminRemoveActivityScreen();
	}

	/**
	 * Take the user to the edit activity screen.
	 */
	@FXML
	private void jumpToEditActivityScreen() {
		this.mainCtrl.showAdminEditActivityScreen();
	}
}