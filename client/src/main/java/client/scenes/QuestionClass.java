package client.scenes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import client.utils.ServerUtils;
import commons.Player;
import static commons.Utility.isNumeric;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
	protected double timestamp;

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

		this.timestamp = this.getProgress();

		if (this.answerButton1.equals(source)) {
			this.setStyleAnswerButton1(color);
			this.inputButton = this.answerButton1;
			this.inputText = null;
			this.setStyleAnswerButton1(color);
		} else if (this.answerButton2.equals(source)) {
			this.setStyleAnswerButton2(color);
			this.inputButton = this.answerButton2;
			this.inputText = null;
			this.setStyleAnswerButton2(color);
		} else if (this.answerButton3.equals(source)) {
			this.setStyleAnswerButton3(color);
			this.inputButton = this.answerButton3;
			this.inputText = null;
			this.setStyleAnswerButton3(color);
		} else if (this.textField.equals(source) && isNumeric(this.textField.getText())) {
			this.inputButton = null;
			this.inputText = this.textField;
		}

		this.disableButtons(true);
		if (this instanceof QuestionScreenSinglePlayerCtrl) {
			this.mainCtrl.updatePoints(this.inputButton, this.inputText, this);
		}
	}

	/**
	 * The method disables the buttons after the player answers.
	 * @param disabled Boolean value that dictates whether the buttons should be disabled.
	 */
	public void disableButtons(boolean disabled) {
		this.answerButton1.setDisable(disabled);
		this.answerButton2.setDisable(disabled);
		this.answerButton3.setDisable(disabled);
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
	 * Getter method for timestamp.
	 * @return The last time when the user clicked on an answer.
	 */
	public double getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Getter method for inputButton.
	 * @return The button that was last clicked by the player.
	 */
	public Button getInputButton() {
		return this.inputButton;
	}

	/**
	 * Getter method for inputText.
	 * @return The text that was last entered by the player.
	 */
	public TextField getInputText() {
		return this.inputText;
	}

	/**
	 * Setter method for inputText.
	 * @param text The value that is to be assigned to inputText.
	 */
	public void setInputText(TextField text) {
		this.inputText = text;
	}

	/**
	 * The method sets the CSS style of the answer Label in the estimate game mode.
	 * @param style The CSS style that is to be applied.
	 */
	public void setEstimateAnswerStyle(String style) {
		this.estimateAnswer.setStyle(style);
	}

	/**
	 * Writes a certain string to the Label estimateAnswer.
	 * @param text The text that is to be written to the label.
	 */
	public void setEstimateAnswerLabel(String text) {
		this.estimateAnswer.setText(text);
	}

	/**
	 * This method set up the question in the label.
	 * @param label The question.
	 */
	public void setUpLabel(String label) {
		this.labelQuestion.setText(label);
	}

	/**
	 * This method set up the first answer in the first button.
	 * @param label The first answer.
	 */
	public void setLabelButton1(String label) {
		this.answerButton1.setText(label);
	}

	/**
	 * This method set up the second answer in the second button.
	 * @param label The second answer.
	 */
	public void setLabelButton2(String label) {
		this.answerButton2.setText(label);
	}

	/**
	 * This method set up the third answer in the third button.
	 * @param label The third answer.
	 */
	public void setLabelButton3(String label) {
		this.answerButton3.setText(label);
	}

	/**
	 * This method set up the visibility of the first button.
	 * @param visible The visibility of a button.
	 */
	public void setVisibleButton1(boolean visible) {
		this.answerButton1.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the second button.
	 * @param visible The visibility of a button.
	 */
	public void setVisibleButton2(boolean visible) {
		this.answerButton2.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the third button.
	 * @param visible The visibility of a button.
	 */
	public void setVisibleButton3(boolean visible) {
		this.answerButton3.setVisible(visible);
	}

	/**
	 * The method sets up the visibility of the estimateAnswer label.
	 * @param visible The visibility of a button.
	 */
	public void setVisibleEstimateAnswer(boolean visible) {
		this.estimateAnswer.setVisible(visible);
	}

	/**
	 * This method set up the color for the progress bar.
	 */
	public void initialize() {
		this.progressBarTime.setStyle("-fx-accent: #00FF00");
	}

	/**
	 * Decreases the progress bar by a certain, given, amount.
	 * @param amount The amount to be decreased.
	 */
	public void decreaseProgress(double amount) {
		this.progress -= amount;
		this.progressBarTime.setProgress(this.progress);
	}

	/**
	 * This method sets up the progress.
	 * @param progress The starting number of for the progress bar (basically progress will be set
	 *                 up 1).
	 */
	public void setProgress(double progress) {
		this.progress = progress;
		this.progressBarTime.setProgress(progress);
	}

	/**
	 * Setter for the visibility of the ImageView of the Question.
	 * @param visible The visibility of the ImageView.
	 */
	public void setVisibleImageQuestion(boolean visible) {
		this.imageQuestion.setVisible(visible);
	}

	/**
	 * This method set up the visibility of the text field.
	 * @param visible The visibility of the text field.
	 */
	public void setVisibleTextField(boolean visible) {
		this.textField.setVisible(visible);
	}

	/**
	 * Sets the image for the question from a given path.
	 * @param imageArray The byte array with information about the image.
	 */
	public void setImageQuestionImageView(byte[] imageArray) throws IOException {
		this.imageQuestion.setImage(this.imageParser(imageArray));
	}

	/**
	 * Parses a byteArray to image.
	 * @param imageArray The byte array with information about the image.
	 * @return An image object for the imageViews.
	 * @throws IOException There is a problem with parsing.
	 */
	public Image imageParser(byte[] imageArray) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(imageArray);
		BufferedImage bufferedImage = ImageIO.read(bis);
		WritableImage result = new WritableImage(
			bufferedImage.getWidth(),
			bufferedImage.getHeight()
		);
		PixelWriter pw = result.getPixelWriter();
		for (int xAxis = 0; xAxis < bufferedImage.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < bufferedImage.getHeight(); yAxis++) {
				pw.setArgb(xAxis, yAxis, bufferedImage.getRGB(xAxis, yAxis));
			}
		}
		return result;
	}

	/**
	 * Method for setting visibility of the image views for the answers.
	 * @param visible The visibility of the image view.
	 * @param number The sequential number of the image view.
	 */
	public void setVisibilityImageView(boolean visible, int number) {
		if (number < 0 || number > 3)
			throw new IllegalArgumentException("Number should be between 0 and 4");
		this.getImageViewOfTheVbox(number).setVisible(visible);
	}

	/**
	 * Sets the image for the different image views based on which picture should be loaded.
	 * @param imageArray The array containing information about the picture.
	 * @param number The number of the VBox with the imageView for the picture.
	 * @throws IOException Something went wrong reading the file.
	 */
	public void setImagesInImageViewsAnswers(byte[] imageArray, int number) throws IOException {
		if (number < 0 || number > 3)
			throw new IllegalArgumentException("Number should be between 0 and 4");
		ImageView currentImageView = this.getImageViewOfTheVbox(number);
		currentImageView.setImage(this.imageParser(imageArray));
	}

	/**
	 * Gets the image view related with the number of the VBox.
	 * @param number The number of the VBox.
	 * @return Current image view desired.
	 */
	protected ImageView getImageViewOfTheVbox(int number) {
		VBox current = (VBox) this.hBoxImages.getChildren().get(number);
		return (ImageView) current.getChildren().get(0);
	}

	public void setMainCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	// abstract classes
	public abstract void showFinalScreen();
	public abstract Player getPlayer();
	public abstract Parent getScene();
	public abstract void showIntermediateScene();
}