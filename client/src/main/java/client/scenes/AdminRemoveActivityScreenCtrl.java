package client.scenes;

import java.util.Comparator;
import java.util.List;

import client.Main;
import client.utils.ServerUtils;
import commons.Activity;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminRemoveActivityScreenCtrl implements Initializable {
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
	}

	/**
	 * Initialize the dropdown box of activities and handle user selection events.  The first thing
	 * this method does is retrieve a list of activities from the server and sort them based on
	 * their IDs.  The dropdown menu is then populated with the contents of this list and the
	 * default selected item is get to the first element of the list.  The text area is then made to
	 * display the contents of the selected activity, if there is no activity then a dummy empty one
	 * is created.  Finally an event listener is setup to update the contents of the text area every
	 * time a new activity is selected.  If somehow the user manages to select an activity that
	 * doesn't exist an IndexOutOfBoundsException exception is thrown.
	 * @param _location Unused.
	 * @param _resources Unused.
	 */
	@Override
	public void initialize(URL _location, ResourceBundle _resources) {
		List<Activity> activities = this.server
			.getActivities()
			.stream()
			.sorted(Comparator.comparing(Activity::getId))
			.toList();
		this.activityDropdown
			.getItems()
			.addAll(
				activities
					.stream()
					.map(Activity::getId)
					.toList()
			);
		this.activityDropdown
			.getSelectionModel()
			.selectFirst();
		this.activityData
			.setWrapText(true);
		this.activityData
			.appendText(
				activities
					.stream()
					.findFirst()
					.orElse(new Activity())
					.toString()
			);
		this.activityDropdown
			.getSelectionModel()
			.selectedItemProperty()
			.addListener((
				ObservableValue<? extends String> observable,
				String oldString,
				String newString
			) -> this.activityData
				.setText(
					activities
						.stream()
						.filter(a -> a.getId().equals(newString))
						.findFirst()
						.orElseThrow(IndexOutOfBoundsException::new)
						.toString()
				)
			);
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