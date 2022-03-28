package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Polygon;
import javax.inject.Inject;

public class AdminEditActivitySceneCtrl {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

	@FXML
	private TextArea activityData;

	@FXML
	private ChoiceBox<String> activityDropdown;

	@FXML
	private Polygon backArrow;

	@FXML
	private Button applyChangesButton;

	/**
	 * The AdminEditActivitySceneCtrl constructor.  It initializes the main controller and server
	 * but also initializes the values of the activity dropdown list.
	 * @param mainCtrl The main controller.
	 */
	@Inject
	public AdminEditActivitySceneCtrl(MainCtrl mainCtrl) {
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

	@FXML
	public void jumpToAdminInterfaceScreen() {
		this.mainCtrl.showAdminInterfaceScreen();
	}

	public void printActivityInfo() {

	}

	@FXML
	public void applyChanges() { }
}
