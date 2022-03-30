package client.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import client.Main;
import client.utils.ServerUtils;
import commons.Activity;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Polygon;
import javax.inject.Inject;


public class AdminEditActivityScreenCtrl implements Initializable {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;
	private List<Activity> activities;

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
	public AdminEditActivityScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = new ServerUtils(Main.serverHost);
		this.refreshActivities();
	}

	/**
	 * Take the user to the adminInterfaceScreen.
	 */
	@FXML
	public void jumpToAdminInterfaceScreen() {
		this.mainCtrl.showAdminInterfaceScreen();
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
	 * This method is run everytime the user clicks on the apply button on the edit activity scene.
	 * It takes the text present in the this.activityData box, read it, and use a PutMapping to
	 * apply the changes to the database. If the id is not changed, the addActivity endpoint will
	 * overwrite the activity with that id, applying the changes. Therefore, it is not necessary
	 * to remove the activity with the old information for the database.
	 * @return An optional which contains the activity with the updated info on success,
	 * or nothing on error.
	 */
	@FXML
	private Optional<Activity> applyChanges() {
		/*
		 * Once the player clicks the apply button, we read the text in the activityData
		 * textBox. We then, need to separate that String into the Activity components (id,
		 * title, imagePath, source). The consumption is handled afterwards;
		 */
		long consumption;
		String activityString = this.activityData.getText();
		String[] activityInfo = activityString.split("\n");
		String id = activityInfo[0].split(": ")[1];
		String title = activityInfo[1].split(": ")[1];
		String imagePath = activityInfo[3].split(": ")[1];
		String source = activityInfo[4].split(": ")[1];

		/* Try to get the consumption as a long instead of a string.  If this fails (because the
		 * input was invalid or something like that) then we cannot proceed and return an empty
		 * optional.
		 */
		try {
			consumption = Long.parseLong(activityInfo[2].split(": ")[1]);
		} catch (NumberFormatException err) {
			return Optional.empty();
		}

		/* Create a new activity with the provided information and check to see if the activity is
		 * valid.  If the activity is valid then we tell the server to add the activity and return
		 * the activity wrapped in an optional.  If an IOException occurred in the process or if the
		 * activity was invalid we return an empty optional.
		 */
		try {
			Activity a = new Activity(id, title, consumption, imagePath, source);
			if (a.isValid()) {
				this.refreshActivities();
				this.activityDropdown
					.getSelectionModel()
					.selectFirst();
				return Optional.of(this.server.addActivity(a));
			}
		} catch (IOException err) {
			err.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * Refresh the list of activities.
	 */
	private void refreshActivities() {
		this.activities = this.server
			.getActivities()
			.stream()
			.sorted(Comparator.comparing(Activity::getId))
			.toList();
	}
}
