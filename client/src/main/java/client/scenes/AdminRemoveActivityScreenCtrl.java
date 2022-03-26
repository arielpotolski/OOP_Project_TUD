package client.scenes;

import java.io.IOException;
import java.util.Optional;

import client.Main;
import client.utils.ServerUtils;
import commons.Activity;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.inject.Inject;

public class AdminRemoveActivityScreenCtrl {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

	@FXML
	private TextField activityTitle;

	@FXML
	private TextField activityImagePath;

	@FXML
	private TextField activityConsumption;

	@FXML
	private TextField activitySource;

	/**
	 * The AdminRemoveActivityScreenCtrl constructor.
	 * @param mainCtrl The main controller.
	 */
	@Inject
	public AdminRemoveActivityScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = new ServerUtils(Main.serverHost);
	}

	/**
	 * Take the user to the admin interface screen.
	 */
	@FXML
	private void jumpToAdminInterfaceScreen() {
		this.mainCtrl.showAdminInterfaceScreen();
	}

	@FXML
	private void removeActivity() {}
}