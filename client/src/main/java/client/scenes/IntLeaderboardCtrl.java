package client.scenes;

import java.util.Comparator;
import java.util.List;

import commons.Player;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;

public class IntLeaderboardCtrl {
	@FXML
	private ProgressBar timeUntilNextQuestion;

	@FXML
	private BarChart barChart;

	/**
	 * Displays the scores of the players in a barchart
	 * @param playersInTheGame A list with all the players in the game
	 */
	public void displayScores(List<Player> playersInTheGame) {
		playersInTheGame.sort(new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return o2.getPoint() - o1.getPoint();
			}
		});
		this.barChart.setTitle("Player Scores");
		XYChart.Series series = new XYChart.Series();
		series.setName("Score");
		for (Player player : playersInTheGame) {
			series.getData().add(new XYChart.Data(player.getNickName(), player.getPoint()));
		}
		this.barChart.getData().add(series);
	}
}
