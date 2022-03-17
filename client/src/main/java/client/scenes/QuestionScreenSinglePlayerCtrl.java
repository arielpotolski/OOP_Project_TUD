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
import javafx.scene.image.Image;
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
	private ImageView imageThird;

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
		this.labelQuestion.setText(label);
	}

	/**
	 * This method set up the first answer in the first button
	 *
	 * @param label the first answer.
	 */
	public void setLabelButton1(String label) {
		this.answerButton1.setText(label);
	}

	/**
	 * This method set up the second answer in the second button
	 *
	 * @param label the second answer.
	 */
	public void setLabelButton2(String label) {
		this.answerButton2.setText(label);
	}

	/**
	 * This method set up the third answer in the third button
	 *
	 * @param label the third answer.
	 */
	public void setLabelButton3(String label) {
		this.answerButton3.setText(label);
	}

	/**
	 * This method set up the visibility of the first button.
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleButton1(boolean visible) {
		this.answerButton1.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the second button.
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleButton2(boolean visible) {
		this.answerButton2.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the third button.
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleButton3(boolean visible) {
		this.answerButton3.setVisible(visible);
	}

	/**
	 * Sets the image for the question from a given path
	 * @param path the path to the image
	 */
	public void setImageQuestionPath(String path) {
		imageQuestion.setImage(new Image(path));
	}

	/**
	 * Sets the image for the first of the sequence of images
	 * @param path the path to the image
	 */
	public void setImageFirstPath(String path) {
		imageFirst.setImage(new Image(path));
	}

	/**
	 * Sets the image for the second of the sequence of images
	 * @param path the path to the image
	 */
	public void setImageSecondPath(String path) {
		imageSecond.setImage(new Image(path));
	}

	/**
	 * Sets the image for the third of the sequence of images
	 * @param path the path to the image
	 */
	public void setImageThirdPath(String path) {
		imageThird.setImage(new Image(path));
	}

	/**
	 * Setter for the visibility of the ImageView of the Question
	 * @param visible the visibility of the ImageView
	 */
	public void setVisibleImageQuestion(boolean visible) {
		this.imageQuestion.setVisible(visible);
	}

	/**
	 * Setter for the visibility of the ImageView of the first one in the sequence
	 * @param visible the visibility of the ImageView
	 */
	public void setVisibleImageFirst(boolean visible) {
		this.imageFirst.setVisible(visible);
	}

	/**
	 * Setter for the visibility of the ImageView of the second one in the sequence
	 * @param visible the visibility of the ImageView
	 */
	public void setVisibleImageSecond(boolean visible) {
		this.imageSecond.setVisible(visible);
	}

	/**
	 * Setter for the visibility of the ImageView of the third on in the sequence
	 * @param visible the visibility of the image view
	 */
	public void setVisibleImageThird(boolean visible) {
		this.imageThird.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the text field.
	 *
	 * @param visible the visibility of the text field.
	 */
	public void setVisibleTextField(boolean visible) {
		this.textField.setVisible(visible);
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 *
	 * @param event the player click on the button.
	 */
	public void answerReturn(ActionEvent event) {
		Object source = event.getSource();
		if (answerButton1.equals(source)) {
			mainCtrl.showAnswer(answerButton1, null);
		} else if (answerButton2.equals(source)) {
			mainCtrl.showAnswer(answerButton2, null);
		} else if (answerButton3.equals(source)) {
			mainCtrl.showAnswer(answerButton3, null);
		} else if (textField.equals(source)) {
			mainCtrl.showAnswer(null, textField);
		}
	}

	/**
	 * This method set up the color for the progress bar.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.progressBarTime.setStyle("-fx-accent: #00FF00");
	}

	/**
	 * This method decrease the progress
	 */
	public void decreaseProgress() {
		this.progress -= 0.1;
		this.progressBarTime.setProgress(this.progress);
	}

	/**
	 * This method returns the progress
	 *
	 * @return the progress.
	 */
	public double getProgress() {
		return this.progress;
	}

	/**
	 * This method set up the progress
	 *
	 * @param progress the starting number of for the progress bar
	 *                    (basically progress will be set up 1).
	 */
	public void setProgress(double progress) {
		this.progress = progress;
		this.progressBarTime.setProgress(progress);
	}
}