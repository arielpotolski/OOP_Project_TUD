package client.scenes;

import client.utils.ServerUtils;
import commons.Player;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SinglePlayerPreGameCtrl{
	public ServerUtils server;
	private MainCtrl mainCtrl;
	private String url;
	private String nickName;
	private Player player;

	@FXML
	public TextField serverURL;

	@FXML
	private TextField nickname;

	@FXML
	private Button startButton;

	@Inject
	/**
	 * Constructor for single player pre-game controller.
	 *
	 * @param mainCtrl the injected main controller.
	 * @param server the injected server.
	 */
	public SinglePlayerPreGameCtrl(MainCtrl mainCtrl, ServerUtils server){
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	/**
	 * the method change the single player pre-game screen to question screen.
	 */
	public void changeToQuestionScreen(){
		url = serverURL.getText();
		nickName = nickname.getText();
		player = new Player(nickName,0);
		server = new ServerUtils(url);
		mainCtrl.setServer(server);
		mainCtrl.setUpQuestions();
		mainCtrl.showQuestionScreenSinglePlayer();
	}

	/**
	 * the method return a player
	 *
	 * @return a player
	 */
	public Player getPlayer(){
		return player;
	}
}

