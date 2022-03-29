package client.scenes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

public class QuestionScreenSinglePlayerCtrl implements Initializable {
	private MainCtrl mainCtrl;
	private ServerUtils server;
	private double timeStamp;

	@FXML
	private TextField textField;

	@FXML
	private TextField inputText;

	@FXML
	private Button inputButton;

	@FXML
	private Button answerButton1;

	@FXML
	private Button answerButton2;

	@FXML
	private Button answerButton3;

	@FXML
	private Label estimateAnswer;

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

	@FXML
	private HBox hBoxImages;

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
	 * 	The method sets up the visibility of the estimateAnswer Label
	 *
	 * @param visible the visibility of a button
	 */
	public void setVisibleEstimateAnswer(boolean visible) {
		this.estimateAnswer.setVisible(visible);
	}

	/**
	 * Parses a byteArray to image
	 * @param imageArray the byte array with information about the image
	 * @return an image object for the imageViews
	 * @throws IOException exception if there is a problem with the parsing
	 */
	public Image imageParser(byte[] imageArray) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(imageArray);
		BufferedImage bufferedImage = ImageIO.read(bis);
		WritableImage result = new WritableImage(bufferedImage.getWidth(),
				bufferedImage.getHeight());
		PixelWriter pw = result.getPixelWriter();
		for (int xAxis = 0; xAxis < bufferedImage.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < bufferedImage.getHeight(); yAxis++) {
				pw.setArgb(xAxis, yAxis, bufferedImage.getRGB(xAxis, yAxis));
			}
		}
		return result;
	}

	/**
	 * Sets the image for the question from a given path
	 * @param imageArray the byte array with information about the image
	 */
	public void setImageQuestionImageView(byte[] imageArray) throws IOException {
		imageQuestion.setImage(imageParser(imageArray));
	}

	/**
	 * Sets the image for the different image views based on which picture should be loaded
	 * @param imageArray the array containing information about the picture
	 * @param number the number of the VBox with the imageView for the picture
	 * @throws IOException if something is wrong with reading the file
	 */
	public void setImagesInImageViewsAnswers(byte[] imageArray, int number) throws IOException {
		if (number < 0 || number > 3) throw new IllegalArgumentException("Number should be " +
				"between 0 and 4");
		ImageView currentImageView = getImageViewOfTheVbox(number);
		currentImageView.setImage(imageParser(imageArray));
	}

	/**
	 * Gets the image view related with the number of the VBox
	 * @param number the number of the VBox
	 * @return current image view desired
	 */
	private ImageView getImageViewOfTheVbox(int number) {
		VBox current = ((VBox) (hBoxImages.getChildren().get(number)));
		return (ImageView) current.getChildren().get(0);
	}

	/**
	 * Method for setting visibility of the image views for the answers
	 * @param visible the visibility of the image view
	 * @param number the sequential number of the image view
	 */
	public void setVisibilityImageView(boolean visible, int number) {
		if (number < 0 || number > 3) throw new IllegalArgumentException("Number should be " +
				"between 0 and 4");
		getImageViewOfTheVbox(number).setVisible(visible);
	}

	/**
	 * Setter for the visibility of the ImageView of the Question
	 * @param visible the visibility of the ImageView
	 */
	public void setVisibleImageQuestion(boolean visible) {
		this.imageQuestion.setVisible(visible);
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
		timeStamp = getProgress();

		mainCtrl.clearButtons();

		if (answerButton1.equals(source)) {
			inputButton = answerButton1;
			inputText = null;
		} else if (answerButton2.equals(source)) {
			inputButton = answerButton2;
			inputText = null;
		} else if (answerButton3.equals(source)) {
			inputButton = answerButton3;
			inputText = null;
		} else if (textField.equals(source)) {
			inputButton = null;
			inputText = textField;
		}

		this.mainCtrl.updatePoints(inputButton, inputText);
	}

	/**
	 * This method set up the color for the progress bar.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.progressBarTime.setStyle("-fx-accent: #00FF00");
	}

	/**
	 * 	Decreases the progress bar by a certain, given, amount
	 *
	 * @param amount the amount to be decreased
	 */
	public void decreaseProgress(double amount) {
		this.progress -= amount;
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

	/**
	 *  The method sets the CSS style of the first Answer Button
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setStyleAnswerButton1(String style) {
		answerButton1.setStyle(style);
	}

	/**
	 *  The method sets the CSS style of the second Answer Button
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setStyleAnswerButton2(String style) {
		answerButton2.setStyle(style);
	}

	/**
	 *  The method sets the CSS style of the third Answer Button
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setStyleAnswerButton3(String style) {
		answerButton3.setStyle(style);
	}

	/**
	 * 	Getter method for timeStamp
	 *
	 * @return The last time when the user clicked on an answer
	 */
	public double getTimeStamp() {
		return timeStamp;
	}

	/**
	 * 	Getter method for inputButton
	 *
	 * @return the button that was last clicked by the Player
	 */
	public Button getInputButton() {
		return inputButton;
	}

	/**
	 * 	Getter method for inputText
	 *
	 * @return the text that was last entered by the player
	 */
	public TextField getInputText() {
		return inputText;
	}

	/**
	 * 	Setter method for inputText
	 *
	 * @param text The value that is to be assigned to inputText
	 */
	public void setInputText(TextField text) {
		inputText = text;
	}

	/**
	 * 	Setter method for inputButton
	 *
	 * @param button The button that is to be assigned to inputButton
	 */
	public void setInputButton(Button button) {
		inputButton = button;
	}

	/**
	 * 	The method sets the CSS style of the answer Label in the estimate game mode
	 *
	 * @param style the CSS style that is to be applied
	 */
	public void setEstimateAnswerStyle(String style) {
		estimateAnswer.setStyle(style);
	}

	/**
	 * 	Writes a certain string to the Label estimateAnswer
	 *
	 * @param text The text that is to be written to the label
	 */
	public void setEstimateAnswerLabel(String text) {
		estimateAnswer.setText(text);
	}
}