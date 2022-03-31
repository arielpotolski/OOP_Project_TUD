                                       package client.scenes;
                                                  
                                        import java.net.URL;
                                     import java.util.HashMap;
                                       import java.util.List;
                                       import java.util.Map;
                                  import java.util.ResourceBundle;
                                                  
                                  import com.google.inject.Inject;
                                      import javafx.fxml.FXML;
                                 import javafx.fxml.Initializable;
                                import javafx.scene.chart.BarChart;
                                 import javafx.scene.chart.XYChart;
                              import javafx.scene.control.ProgressBar;
                                                  
                     public class IntLeaderboardCtrl implements Initializable {
                                     private MainCtrl mainCtrl;
                             private HashMap<String, Integer> players;
                                                  
                                               @FXML
                             private ProgressBar timeUntilNextQuestion;
                                                  
                                       double progress = 1f;
                                                  
                                                /**
              * Decreases the progess in the progress bar by a certain, given, amount.
                     * @param amount The amount of progress that the bar loses.
                                                 */
                           public void decreaseProgress(double amount) {
                                      this.progress -= amount;
                       this.timeUntilNextQuestion.setProgress(this.progress);
                                                 }
                                               @FXML
                                     private BarChart barChart;
                                                  
                                              @Inject
                           public IntLeaderboardCtrl(MainCtrl mainCtrl) {
                                     this.mainCtrl = mainCtrl;
                                                 }
                                                  
                     public void setPlayers(HashMap<String, Integer> players) {
                                      this.players = players;
                                                 }
                                                  
                                                /**
                         * Displays the scores of the players in a barchart
                                                 */
                                   public void displayScores() {
                  List<Map.Entry<String, Integer>> playersInTheGame = this.players
                                            .entrySet()
                                             .stream()
                               .sorted(Map.Entry.comparingByValue())
                                             .toList();
                              this.barChart.setTitle("Player Scores");
                                 if (playersInTheGame.size() < 6) {
                     this.barChart.setMaxWidth(playersInTheGame.size() * 100);
                                                 }
                    for (Map.Entry<String, Integer> player : playersInTheGame) {
                           XYChart.Series series = new XYChart.Series();
                   series.getData().add(new XYChart.Data("", player.getValue()));
                                  series.setName(player.getKey());
                                this.barChart.getData().add(series);
                                                 }
                                                 }
                                                  
                                             @Override
                  public void initialize(URL location, ResourceBundle resources) {
                    this.timeUntilNextQuestion.setStyle("-fx-accent: #00FF00");
                                                 }
                                                  
                                                /**
                                 * Setter method for the progress.
                            * @param amount The amount set to progress.
                                                 */
                              public void setProgress(double amount) {
                                      this.progress = amount;
                       this.timeUntilNextQuestion.setProgress(this.progress);
                                                 }
                                                  }