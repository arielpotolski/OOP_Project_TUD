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
	 *
	 * @param mainCtrl the injected main controller.
	 */
	@Inject
	public GlobalLeaderboardScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
		this.server = new ServerUtils("http://localhost:8080/");
	}


	/**
	 * This method updates the list view by getting all the list of player in database.
	 *
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scrollPane.prefWidthProperty().bind(listView.widthProperty());
		scrollPane.prefHeightProperty().bind(listView.heightProperty());
		scrollPane.setContent(listView);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	}

	/**
	 * This method helps for updating the list view after a new player finished his/her game.
	 */
	public void getItems() {
		listView.getItems().addAll(server.getPlayers());
	}

	public void jumpToSplashScreen() {
		mainCtrl.showSplashScreen();
	}
}