package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.Player;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;

public class GlobalLeaderboardScreenCtrl implements Initializable {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

	@FXML
	private Polygon backArrow;

	@FXML
	private AnchorPane leaderboard;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ListView<Player> listView;

	/**
	 * Constructor for global leader board controllers.
	 * @param mainCtrl The injected main controller.
	 */
	@Inject
	public GlobalLeaderboardScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = mainCtrl.getServer();
	}

	/**
	 * This method updates the list view by getting all the list of player in database.
	 * @param _location Unused.
	 * @param _resources Unused.
	 */
	@Override
	public void initialize(URL _location, ResourceBundle _resources) {
		this.scrollPane.prefWidthProperty().bind(this.listView.widthProperty());
		this.scrollPane.prefHeightProperty().bind(this.listView.heightProperty());
		this.scrollPane.setContent(this.listView);
		this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	}

	/**
	 * This method helps for updating the list view after a new player finished his/her game.
	 */
	public void getItems() {
		this.listView.getItems().addAll(this.server.getPlayers());
	}

	/**
	 * Jump to the splash screen.
	 */
	public void jumpToSplashScreen() {
		this.mainCtrl.showSplashScreen();
	}
}