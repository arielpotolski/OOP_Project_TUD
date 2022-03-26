package client.scenes;

import javafx.fxml.FXML;

import javax.inject.Inject;

public class AdminInterfaceScreenCtrl {
	private MainCtrl mainCtrl;

	/**
	 * The AdminInterfaceScreenCtrl constructor.
	 * @param mainCtrl The main controller.
	 */
	@Inject
	public AdminInterfaceScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

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
}