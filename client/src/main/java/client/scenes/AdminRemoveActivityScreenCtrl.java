package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import client.utils.SoundHandler;
import commons.Activity;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javax.inject.Inject;

public class AdminRemoveActivityScreenCtrl implements Initializable {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

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
		this.server = mainCtrl.getServer();
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
		this.activityData
			.setWrapText(true);
		this.activityDropdown
			.getSelectionModel()
			.selectedItemProperty()
			.addListener((
				ObservableValue<? extends String> _observable,
				String _oldString,
				String newString
			) -> {
				Optional<Activity> maybeActivity = this.mainCtrl
					.getActivities()
					.stream()
					.filter(a -> a.getId().equals(newString))
					.findFirst();
				this.activityData.setText(
					maybeActivity.map(Activity::toString).orElse("")
				);
			});
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
		SoundHandler.clickSound();
		String id = this.activityDropdown
			.getSelectionModel()
			.getSelectedItem();
		Activity activity = this.mainCtrl
			.getActivities()
			.stream()
			.filter(a -> a.getId().equals(id))
			.findFirst()
			.orElseThrow(IndexOutOfBoundsException::new);
		this.server.removeActivity(activity);
		this.mainCtrl.refreshActivities();
	}

	/**
	 * Update the dropdown of activities.
	 * @param activities List of sorted of activities to from the dropdown from.
	 */
	protected void updateDropdown(List<Activity> activities) {
		ObservableList<String> list = this.activityDropdown.getItems();
		list.clear();
		list.addAll(activities.stream().map(Activity::getId).toList());
	}
}