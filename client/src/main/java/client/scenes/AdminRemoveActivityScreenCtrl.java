package client.scenes;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import client.Main;
import client.utils.ServerUtils;
import commons.Activity;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javax.inject.Inject;

public class AdminRemoveActivityScreenCtrl implements Initializable {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;
	private List<Activity> activities;

	@FXML
	private TextArea activityData;

	@FXML
	private ChoiceBox<String> activityDropdown;

	/**
	 * The AdminRemoveActivityScreenCtrl constructor.
	 * @param mainCtrl The main controller.
	 */
	@Inject
	public AdminRemoveActivityScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = new ServerUtils(Main.serverHost);
		this.refreshActivities();
	}

	/**
	 * Initialize the dropdown box of activities and handle user selection events.  The dropdown
	 * menu is populated with the contents of this list and the default selected item is get to the
	 * first element of the list.  The text area is then made to display the contents of the
	 * selected activity, if there is no activity then a dummy empty one is created.  Finally, an
	 * event listener is setup to update the contents of the text area every time a new activity is
	 * selected.  If somehow the user manages to select an activity that doesn't exist an
	 * IndexOutOfBoundsException exception is thrown.
	 * @param _location Unused.
	 * @param _resources Unused.
	 */
	@Override
	public void initialize(URL _location, ResourceBundle _resources) {
		this.activityDropdown
			.getItems()
			.addAll(
				this.activities
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
			.setText(
				this.activities
					.stream()
					.findFirst()
					.orElse(new Activity())
					.toString()
			);
		this.activityDropdown
			.getSelectionModel()
			.selectedItemProperty()
			.addListener((
				ObservableValue<? extends String> _observable,
				String _oldString,
				String newString
			) -> this.activityData
				.setText(
					this.activities
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

	/**
	 * Remove the currently selected activity from the database.  This method first attempts to find
	 * an activity with the matching ID in the activities list.  If there is no such activity then
	 * an IndexOutOfBoundsException is thrown, although this should in theory never happen unless
	 * there are no activities left to remove.  The activity is then removed from the server and the
	 * activity list is refreshed. Finally, we set the new selected activity to the first in the
	 * list.  We don't need to update the text area as this is handled by the event listener.
	 */
	@FXML
	private void removeActivity() {
		String id = this.activityDropdown
			.getSelectionModel()
			.getSelectedItem();
		Activity activity = this.activities
			.stream()
			.filter(a -> a.getId().equals(id))
			.findFirst()
			.orElseThrow(IndexOutOfBoundsException::new);
		this.server
			.removeActivity(activity);
		this.refreshActivities();
		this.activityDropdown
			.getItems()
			.remove(activity.getId());
		this.activityDropdown
			.getSelectionModel()
			.selectFirst();
	}

	/**
	 * Refresh the list of activities.
	 */
	protected void refreshActivities() {
		this.activities = this.server
			.getActivities()
			.stream()
			.sorted(Comparator.comparing(Activity::getId))
			.toList();
	}
}