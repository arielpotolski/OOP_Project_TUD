package client.scenes;

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
	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}
}
