package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class QuestionScreenSinglePlayerCtrl implements Initializable {
	private MainCtrl mainCtrl;
	private ServerUtils server;

	@FXML
	private TextField textField;

	@FXML
	private Button answerButton1;

	@FXML
	private Button answerButton2;

	@FXML
	private Button answerButton3;

	@FXML
	public Label labelQuestion;

	@FXML
	private ImageView imageQuestion;

	@FXML
	private ImageView imageFirst;

	@FXML
	private ImageView imageSecond;

	@FXML
	private ProgressBar progressBarTime;

	double progress = 1;

	/**
	 * Constructor for question screen in single player mode.
	 *
	 * @param mainCtrl the injected main controller.
	 * @param server the injected server.
	 */
	@Inject
	public QuestionScreenSinglePlayerCtrl(MainCtrl mainCtrl,ServerUtils server) {
		this.mainCtrl = mainCtrl;
		this.server = server;
	}

	/**
	 * This method set up the question in the label
	 *
	 * @param label the question.
	 */
	public void setUpLabel(String label) {
		labelQuestion.setText(label);
	}

	/**
	 * This method set up the first answer in the first button
	 *
	 * @param label the first answer.
	 */
	public void setLabelButton1(String label) {
		answerButton1.setText(label);
	}

	/**
	 * This method set up the second answer in the second button
	 *
	 * @param label the second answer.
	 */
	public void setLabelButton2(String label) {
		answerButton2.setText(label);
	}

	/**
	 * This method set up the third answer in the third button
	 *
	 * @param label the third answer.
	 */
	public void setLabelButton3(String label) {
		answerButton3.setText(label);
	}

	/**
	 * This method set up the visibility of the first button.
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleButton1(boolean visible) {
		answerButton1.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the second button.
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleButton2(boolean visible) {
		answerButton2.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the third button.
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleButton3(boolean visible) {
		answerButton3.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the text field.
	 *
	 * @param visible the visibility of the text field.
	 */
	public void setVisibleTextField(boolean visible) {
		textField.setVisible(visible);
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 *
	 * @param event the player click on the button.
	 */
	public void answerReturn(ActionEvent event) {
		if (event.getSource() == answerButton1) {
			mainCtrl.showAnswer(answerButton1,null);
		} else if (event.getSource() == answerButton2) {
			mainCtrl.showAnswer(answerButton2,null);
		} else if (event.getSource() == answerButton3) {
			mainCtrl.showAnswer(answerButton3,null);
		} else if (event.getSource() == textField) {
			mainCtrl.showAnswer(null,textField);
		}
	}

	/**
	 * This method set up the color for the progress bar.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		progressBarTime.setStyle("-fx-accent: #00FF00");
	}

	/**
	 * This method decrease the progress
	 */
	public void decreaseProgress() {
		progress -= 0.1;
		progressBarTime.setProgress(progress);
	}

	/**
	 * This method returns the progress
	 *
	 * @return the progress.
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * This method set up the progress
	 *
	 * @param progress the starting number of for the progress bar
	 *                    (basically progress will be set up 1).
	 */
	public void setProgress(double progress) {
		this.progress = progress;
		progressBarTime.setProgress(progress);
	}
}
