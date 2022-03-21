package client.scenes;

import java.io.IOException;
import java.util.Optional;

import client.utils.ServerUtils;
import commons.LobbyResponse;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WaitingScreenCtrl {
	private MainCtrl mainCtrl;

	@FXML
	private AnchorPane peopleInTheLeaderBoardPane;

	@FXML
	private Button startButton;

	@FXML
	private Label peopleInTheRoomLabel;

	@Inject
	public WaitingScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}
}
