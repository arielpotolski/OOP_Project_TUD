package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javax.inject.Inject;

public class AdminRemoveActivityScreenCtrl {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

	@FXML
	private TextArea activityData;

	@FXML
	private ChoiceBox<String> activityDropdown;

	/**
	 * The AdminRemoveActivityScreenCtrl constructor.  It initializes the main controller and server
	 * but also initializes the values of the activity dropdown list.
	 * @param mainCtrl The main controller.
	 */
	@Inject
	public AdminRemoveActivityScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = new ServerUtils(Main.serverHost);
		ObservableList<String> xs = FXCollections.observableList(
			this.server
				.getActivities()
				.stream()
				.map(Activity::getId)
				.sorted()
				.toList()
		);
		this.activityDropdown = new ChoiceBox<>(xs);
		if (!xs.isEmpty()) {
			this.activityDropdown.setValue(xs.get(0));
		}
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