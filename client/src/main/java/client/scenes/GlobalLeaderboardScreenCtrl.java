package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import client.utils.SoundHandler;
import commons.Player;
import commons.PlayerLeaderboard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GlobalLeaderboardScreenCtrl implements Initializable {
	private final MainCtrl mainCtrl;
	private final ServerUtils server;

	@FXML
	private TableView<PlayerLeaderboard> leaderboard;

	@FXML
	private TableColumn<PlayerLeaderboard, Integer> position;

	@FXML
	private TableColumn<PlayerLeaderboard, String> name;

	@FXML
	private TableColumn<PlayerLeaderboard,  Integer> points;

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
		this.position.setCellValueFactory(
				new PropertyValueFactory<PlayerLeaderboard, Integer>("position")
		);
		this.name.setCellValueFactory(
				new PropertyValueFactory<PlayerLeaderboard, String>("name")
		);
		this.points.setCellValueFactory(
				new PropertyValueFactory<PlayerLeaderboard, Integer>("points")
		);

		this.leaderboard.getItems().setAll(this.getItems());
	}

	/**
	 * This method helps for updating the list view after a new player finished his/her game.
	 *
	 * @return a list with all the players
	 */
	public List<PlayerLeaderboard> getItems() {
		List<PlayerLeaderboard> listOfLeaderboard = new ArrayList<PlayerLeaderboard>();
		List<Player> listOfPlayers = this.server
				.getPlayers()
				.stream()
				.sorted((_u, _v) -> Integer.compare(_v.getPoints(), _u.getPoints()))
				.toList();
		for (int i = 0;i < listOfPlayers.size(); ++i) {
			Player player = listOfPlayers.get(i);
			listOfLeaderboard.add(new PlayerLeaderboard(
					i + 1,
					player.getNickname(),
					player.getPoints()
			));
		}

		return listOfLeaderboard;
	}

	/**
	 * Jump to the splash screen.
	 */
	public void jumpToSplashScreen() {
		SoundHandler.clickSound();
		this.mainCtrl.showSplashScreen();
	}
}