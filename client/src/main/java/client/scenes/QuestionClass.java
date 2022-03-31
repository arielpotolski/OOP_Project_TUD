package client.scenes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import client.utils.ServerUtils;
import commons.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;

public abstract class QuestionClass {
	protected MainCtrl mainCtrl;
	protected ServerUtils server;
	protected double timeStamp;

	@FXML
	private HBox hBoxImages;

	@FXML
	public Label labelQuestion;

	@FXML
	protected TextField textField;

	@FXML
	protected Label estimateAnswer;

	@FXML
	protected Button answerButton1;

	@FXML
	protected Button answerButton2;

	@FXML
	protected Button answerButton3;

	@FXML
	protected ImageView imageQuestion;

	@FXML
	protected ImageView imageFirst;

	@FXML
	protected ImageView imageSecond;

	@FXML
	protected ImageView imageThird;

	@FXML
	protected ProgressBar progressBarTime;

	@FXML
	protected Circle innerCircleTutorial;

	@FXML
	protected Button inputButton;

	@FXML
	protected TextField inputText;

	protected double progress = 1;

	/**
	 * Setter method for inputButton.
	 * @param button The button that is to be assigned to inputButton.
	 */
	public void setInputButton(Button button) {
		this.inputButton = button;
	}

	/**
	 * This method shows answer when player click on one of the three buttons.
	 * @param event The player click on the button.
	 */
	public void answerReturn(ActionEvent event) throws IOException {
		String color = "-fx-background-color: #f0dca5; -fx-background-radius: 15;";
		Object source = event.getSource();
		this.mainCtrl.clearButtons(this);

		this.timeStamp = getProgress();

		if (this.answerButton1.equals(source)) {
			this.inputButton = this.answerButton1;
			this.inputText = null;
			setStyleAnswerButton1(color);
		} else if (this.answerButton2.equals(source)) {
			this.inputButton = this.answerButton2;
			this.inputText = null;
			setStyleAnswerButton2(color);
		} else if (this.answerButton3.equals(source)) {
			this.inputButton = this.answerButton3;
			this.inputText = null;
			setStyleAnswerButton3(color);
		} else if (this.textField.equals(source) && isNumeric(this.textField.getText())) {
			this.inputButton = null;
			this.inputText = this.textField;
		}

		if (this instanceof QuestionScreenSinglePlayerCtrl) {
			this.mainCtrl.updatePoints(inputButton, inputText, this);
		}
	}

	/**
	 * This method returns the progress.
	 * @return The progress.
	 */
	public double getProgress() {
		return this.progress;
	}

	/**
	 * The method sets the CSS style of the first Answer Button.
	 * @param style The CSS style that is to be applied.
	 */
	public void setStyleAnswerButton1(String style) {
		this.answerButton1.setStyle(style);
	}

	/**
	 * The method sets the CSS style of the second Answer Button.
	 * @param style The CSS style that is to be applied.
	 */
	public void setStyleAnswerButton2(String style) {
		this.answerButton2.setStyle(style);
	}

	/**
	 * The method sets the CSS style of the third Answer Button.
	 * @param style The CSS style that is to be applied.
	 */
	public void setStyleAnswerButton3(String style) {
		this.answerButton3.setStyle(style);
	}

	/**
	 * Getter method for timeStamp
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
	 * This method set up the color for the progress bar.
	 *
	 */
	public void initialize() {
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
	 * Sets the image for the question from a given path
	 * @param imageArray the byte array with information about the image
	 */
	public void setImageQuestionImageView(byte[] imageArray) throws IOException {
		imageQuestion.setImage(imageParser(imageArray));
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
	protected ImageView getImageViewOfTheVbox(int number) {
		VBox current = ((VBox) (hBoxImages.getChildren().get(number)));
		return (ImageView) current.getChildren().get(0);
	}

	public void setMainCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	/**
	 *	Tests whether or not a given string is numeric
	 *
	 * @param string the string which we test if
	 *               its numeric or not
	 * @return a boolean value telling us if the
	 * 	       string is numeric
	 */
	public static boolean isNumeric(String string) {
		if (string == null) {
			return false;
		}

		try {
			Integer number = Integer.parseInt(string);
			// Avoid checkstyle erorr for unused variable
			number = number + 1;
		} catch (NumberFormatException err) {
			return false;
		}
		return true;
	}

	// abstract classes
	public abstract void showFinalScreen();
	public abstract Player getPlayer();
	public abstract Scene getScene();
	public abstract void showIntermediateScene();
}
