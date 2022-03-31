                                                 /*
                          * Copyright 2021 Delft University of Technology
                                                 *
                 * Licensed under the Apache License, Version 2.0 (the "License");
                 * you may not use this file except in compliance with the License.
                             * You may obtain a copy of the License at
                                                 *
                          *    http://www.apache.org/licenses/LICENSE-2.0
                                                 *
               * Unless required by applicable law or agreed to in writing, software
                * distributed under the License is distributed on an "AS IS" BASIS,
             * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
               * See the License for the specific language governing permissions and
                                  * limitations under the License.
                                                 */
                                                  
                                       package client.scenes;
                                                  
                                    import java.io.IOException;
                                    import java.util.Comparator;
                                       import java.util.List;
                                     import java.util.Optional;
                                      import java.util.Random;
                                                  
                                        import client.Main;
                                  import client.utils.ServerUtils;
                                      import commons.Activity;
                                     import commons.Connection;
                                  import commons.EstimateQuestion;
                             import commons.HighestConsumptionQuestion;
                                 import commons.InsteadOfQuestion;
                                   import commons.LobbyResponse;
                                     import commons.MCQuestion;
                                    import commons.MessageModel;
                                       import commons.Player;
                                      import commons.Question;
                               import commons.messages.ErrorMessage;
                               import commons.messages.JokerMessage;
                                 import commons.messages.JokerType;
                               import commons.messages.KillerMessage;
                            import commons.messages.LeaderboardMessage;
                                  import commons.messages.Message;
                                                  
                                 import javafx.animation.KeyFrame;
                                 import javafx.animation.Timeline;
                                 import javafx.event.EventHandler;
                                    import javafx.scene.Parent;
                                     import javafx.scene.Scene;
                                 import javafx.scene.control.Alert;
                                import javafx.scene.control.Button;
                              import javafx.scene.control.ButtonType;
                               import javafx.scene.control.TextField;
                                   import javafx.stage.Modality;
                                     import javafx.stage.Stage;
                                  import javafx.stage.WindowEvent;
                                    import javafx.util.Duration;
                                      import javafx.util.Pair;
                                      import org.slf4j.Logger;
                                  import org.slf4j.LoggerFactory;
                                                  
                                      public class MainCtrl {
                                    private Stage primaryStage;
                                                  
                                   private SplashCtrl splashCtrl;
                                    private Scene splashScreen;
                                                  
                      private SinglePlayerPreGameCtrl singlePlayerPreGameCtrl;
                              private Scene singlePlayerPreGameScreen;
                                                  
                       private MultiplayerPreGameCtrl multiplayerPreGameCtrl;
                              private Scene multiplayerPreGameScreen;
                                                  
               private QuestionScreenSinglePlayerCtrl questionScreenSinglePlayerCtrl;
                             private Scene questionScreenSinglePlayer;
                                                  
                  private GlobalLeaderboardScreenCtrl globalLeaderboardScreenCtrl;
                                  private Scene globalLeaderBoard;
                                                  
                        private IntermediateSceneCtrl intermediateSceneCtrl;
                                  private Scene intermediateScene;
                                                  
                  private SinglePlayerFinalScreenCtrl singlePlayerFinalSceneCtrl;
                               private Scene singlePlayerFinalScene;
                                                  
                            private WaitingScreenCtrl waitingScreenCtrl;
                                    private Scene waitingScreen;
                                                  
                     private AdminInterfaceScreenCtrl adminInterfaceScreenCtrl;
                                private Scene adminInterfaceScreen;
                                                  
                   private AdminAddActivityScreenCtrl adminAddActivityScreenCtrl;
                               private Scene adminAddActivityScreen;
                                                  
                private AdminRemoveActivityScreenCtrl adminRemoveActivityScreenCtrl;
                              private Scene adminRemoveActivityScreen;
                                                  
                  private AdminEditActivityScreenCtrl adminEditActivityScreenCtrl;
                               private Scene adminEditActivityScreen;
                                                  
                    private TopPlayersLeaderboardCtrl topPlayersLeaderboardCtrl;
                                private Scene topPlayersLeaderboard;
                                                  
                private MultiplayerQuestionScreenCtrl multiplayerQuestionScreenCtrl;
                              private Scene multiPlayerQuestionScreen;
                                                  
                           private IntLeaderboardCtrl intLeaderboardCtrl;
                            private Scene intermediateLeaderboardScreen;
                                                  
                                    private ServerUtils server;
                                                  
                                 private List<Question> questions;
                                     private Question question;
                                                  
                                     private Timeline timeLine;
                                       private Player player;
                                      private String nickname;
                                     private int currentPoint;
                             private int numberOfQuestionAnswered = 0;
                              private int numberOfCorrectAnswered = 0;
                                                  
                                 private List<Activity> activities;
                                                  
                                       private long seed = 0;
                                                  
                    private EventHandler<WindowEvent> confirmCloseEventHandler;
                                                  
                                       private Logger logger;
                                                  
                   private static final double JOKER_DECREASE_TIME_PERCENT = 0.5;
                                                  
                                        public MainCtrl() {
                                this.seed = new Random().nextInt();
                          this.server = new ServerUtils(Main.serverHost);
                                                 }
                                                  
                                                /**
                                    * Initialize all the screens
                                                 *
                              * @param primaryStage the primary stage
                     * @param singlePlayer a pair of single player with parent
                       * @param multiPlayer a pair of multiplayer with parent
                        * @param splash a pair of splash screen with parent
             * @param questionScreenSinglePlayer a pair of question screen with parent.
                * @param globalLeaderBoard a pair of global leader board with parent
                * @param intermediateScene a pair of intermediate screen with parent
         * @param singlePlayerFinalScene a pair of final single player screen with parent.
                    * @param waitingScreen a pair of waiting screen with parent
            * @param adminInterfaceScreen A pair of admin interface screen with parent.
          * @param adminAddActivityScreen A pair of admin add activity screen with parent.
       * @param adminRemoveActivityScreen A pair of admin remove activity screen with parent.
         * @param adminEditActivityScreen A pair of admin edit activity screen with parent.
        * @param topPlayersLeaderboard a pair of top players leaderboard scene with parent.
                         * @param multiPlayerQuestion a pair of multiplayer
                     * @param intLeaderboard a pair of intermediate leaderboard
                               *          question screen with parent
                                                 */
                             public void initialize(Stage primaryStage,
                        Pair<SinglePlayerPreGameCtrl, Parent> singlePlayer,
                         Pair<MultiplayerPreGameCtrl, Parent> multiPlayer,
                                  Pair<SplashCtrl, Parent> splash,
              Pair<QuestionScreenSinglePlayerCtrl, Parent> questionScreenSinglePlayer,
                    Pair<GlobalLeaderboardScreenCtrl, Parent> globalLeaderBoard,
                       Pair<IntermediateSceneCtrl, Parent> intermediateScene,
                 Pair<SinglePlayerFinalScreenCtrl, Parent> singlePlayerFinalScene,
                           Pair<WaitingScreenCtrl, Parent> waitingScreen,
                    Pair<AdminInterfaceScreenCtrl, Parent> adminInterfaceScreen,
                  Pair<AdminAddActivityScreenCtrl, Parent> adminAddActivityScreen,
               Pair<AdminRemoveActivityScreenCtrl, Parent> adminRemoveActivityScreen,
                 Pair<AdminEditActivityScreenCtrl, Parent> adminEditActivityScreen,
                  Pair<MultiplayerQuestionScreenCtrl, Parent> multiPlayerQuestion,
                          Pair<IntLeaderboardCtrl, Parent> intLeaderboard,
                   Pair<TopPlayersLeaderboardCtrl, Parent> topPlayersLeaderboard
                                                ) {
                       this.logger = LoggerFactory.getLogger(MainCtrl.class);
                                                  
                                 this.primaryStage = primaryStage;
                                                  
                        this.multiplayerPreGameCtrl = multiPlayer.getKey();
                 this.multiplayerPreGameScreen = new Scene(multiPlayer.getValue());
                                                  
                       this.singlePlayerPreGameCtrl = singlePlayer.getKey();
                this.singlePlayerPreGameScreen = new Scene(singlePlayer.getValue());
                                                  
                                 this.splashCtrl = splash.getKey();
                         this.splashScreen = new Scene(splash.getValue());
                                                  
             this.questionScreenSinglePlayerCtrl = questionScreenSinglePlayer.getKey();
        this.questionScreenSinglePlayer = new Scene(questionScreenSinglePlayer.getValue());
                                                  
                   this.globalLeaderboardScreenCtrl = globalLeaderBoard.getKey();
                 this.globalLeaderBoard = new Scene(globalLeaderBoard.getValue());
                                                  
                      this.intermediateSceneCtrl = intermediateScene.getKey();
                 this.intermediateScene = new Scene(intermediateScene.getValue());
                                                  
                 this.singlePlayerFinalSceneCtrl = singlePlayerFinalScene.getKey();
            this.singlePlayerFinalScene = new Scene(singlePlayerFinalScene.getValue());
                                                  
                          this.waitingScreenCtrl = waitingScreen.getKey();
                     this.waitingScreen = new Scene(waitingScreen.getValue());
                                                  
                   this.adminInterfaceScreenCtrl = adminInterfaceScreen.getKey();
              this.adminInterfaceScreen = new Scene(adminInterfaceScreen.getValue());
                                                  
                 this.adminAddActivityScreenCtrl = adminAddActivityScreen.getKey();
            this.adminAddActivityScreen = new Scene(adminAddActivityScreen.getValue());
                                                  
              this.adminRemoveActivityScreenCtrl = adminRemoveActivityScreen.getKey();
         this.adminRemoveActivityScreen = new Scene(adminRemoveActivityScreen.getValue());
                                                  
                this.adminEditActivityScreenCtrl = adminEditActivityScreen.getKey();
           this.adminEditActivityScreen = new Scene(adminEditActivityScreen.getValue());
                                                  
                  this.topPlayersLeaderboardCtrl = topPlayersLeaderboard.getKey();
             this.topPlayersLeaderboard = new Scene(topPlayersLeaderboard.getValue());
                                                  
                 this.multiplayerQuestionScreenCtrl = multiPlayerQuestion.getKey();
            this.multiPlayerQuestionScreen = new Scene(multiPlayerQuestion.getValue());
                                                  
                         this.intLeaderboardCtrl = intLeaderboard.getKey();
             this.intermediateLeaderboardScreen = new Scene(intLeaderboard.getValue());
                                                  
                                    setEventHandlerForClosure();
                                        closeConfirmation();
                                                  
                          multiplayerQuestionScreenCtrl.setMainCtrl(this);
                                                  
                                        showSplashScreen();
                                        primaryStage.show();
                                                 }
                                                  
                                                /**
                                    * Sets the event for closure
                                                 */
                                 private void closeConfirmation() {
                            this.primaryStage.setOnCloseRequest(event ->
                                    this.primaryStage.fireEvent(
                                          new WindowEvent(
                                         this.primaryStage,
                                  WindowEvent.WINDOW_CLOSE_REQUEST
                                                 )
                                                 )
                                                 );
                                                 }
                                                  
                                                /**
              * Setter for the event handler when the user requests to exit the screen
                                                 */
                             private void setEventHandlerForClosure() {
                             this.confirmCloseEventHandler = event -> {
                                Alert closeConfirmation = new Alert(
                                   Alert.AlertType.CONFIRMATION,
                                  "Are you sure you want to exit?"
                                                 );
            Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                                           ButtonType.OK
                                                 );
                                     exitButton.setText("Yes");
                          closeConfirmation.setHeaderText("Confirm Exit");
                    closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                          closeConfirmation.initOwner(this.primaryStage);
                                                  
               Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
                         if (!ButtonType.OK.equals(closeResponse.get())) {
                                          event.consume();
                                  } else if (this.server != null){
                                               try {
                       this.server.getConnection().send(new KillerMessage());
                                    } catch (IOException err) {
                                       err.printStackTrace();
                                                 }
                                                 }
                                                 };
                                                 }
                                                /**
                             * This method shows up the splash screen.
                                                 */
                                  public void showSplashScreen() {
                                  primaryStage.setTitle("Quizzz");
                                primaryStage.setScene(splashScreen);
                this.primaryStage.setOnCloseRequest(this.confirmCloseEventHandler);
                                                 }
                                                  
                                                /**
                     * This method shows up the single player pre-game screen.
                                                 */
                           public void showSinglePlayerPreGameScreen() {
                               primaryStage.setTitle("SinglePlayer");
                         primaryStage.setScene(singlePlayerPreGameScreen);
                                                 }
                                                  
                                                /**
                         * This method shows up multiplayer pre-game screen
                                                 */
                          public void showMultiplePlayersPreGameScreen() {
                               primaryStage.setTitle("Multiplayer");
                          primaryStage.setScene(multiplayerPreGameScreen);
                                                 }
                                                  
                    public void showWaitingScreen(LobbyResponse firstResponse) {
                              primaryStage.setTitle("Waiting Lobby");
                               primaryStage.setScene(waitingScreen);
                        waitingScreenCtrl.beginActiveRefresh(firstResponse);
                                                 }
                                                  
                                                /**
                 * This method shows up the question screen in single player mode.
           * @param isSingleplayer Whether the current game mode is single player or not.
                                                 */
            public void showQuestionScreen(boolean isSingleplayer) throws IOException {
                                     QuestionClass screenCtrl;
                                                  
                                       if (isSingleplayer) {
                            screenCtrl = questionScreenSinglePlayerCtrl;
                                              } else {
                            screenCtrl = multiplayerQuestionScreenCtrl;
                                                  
        server.registerForMessages("/message/receive", MessageModel.class, messageModel -> {
              multiplayerQuestionScreenCtrl.updateMessage(messageModel.getMessage());
                                                });
                                                 }
                      // Enable all buttons to make sure the player can answer
                                 screenCtrl.disableButtons(false);
                                                  
         // If the size of question set equals to zero, this method change to final screen.
                                    if (questions.size() == 0) {
                                   screenCtrl.showFinalScreen();
                                              return;
                                                 }
                                                  
                                                                                                             clearButtons(screenCtrl);	// change back the color of the buttons
                                  screenCtrl.setInputButton(null);
                                   screenCtrl.setInputText(null);
                                                  
                            // Assign the player variable that got from
                       // SinglePlayerPreGame to player variable in MainCtrl
                                  player = screenCtrl.getPlayer();
                                    screenCtrl.setProgress(1f);
                                                  
            // This timeline will execute on another thread - run the count-down timer.
                 timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e -> {
                                 screenCtrl.decreaseProgress(0.1f);
                                                }));
                                   timeLine.setOnFinished(_e -> {
                             updatePoints(screenCtrl.getInputButton(),
                                     screenCtrl.getInputText(),
                                            screenCtrl);
                                                });
                                    timeLine.setCycleCount(10);
                                          timeLine.play();
                                                  
                                 primaryStage.setTitle("Question");
                                                  
                     // Get first question of the set and delete that question.
                                    question = questions.get(0);
                                        questions.remove(0);
                                                  
                                    numberOfQuestionAnswered++;
                            if (question instanceof EstimateQuestion) {
                  setUpEstimateQuestion((EstimateQuestion) question, screenCtrl);
                    } else if (question instanceof HighestConsumptionQuestion) {
              setUpHighestQuestion((HighestConsumptionQuestion) question, screenCtrl);
                            } else if (question instanceof MCQuestion) {
                      setUpMultipleChoice((MCQuestion) question, screenCtrl);
                        } else if (question instanceof InsteadOfQuestion) {
                  setUpInsteadQuestion((InsteadOfQuestion) question, screenCtrl);
                                                 }
                                                  
                           primaryStage.setScene(screenCtrl.getScene());
                                                 }
                                                  
                                  public ServerUtils getServer() {
                                        return this.server;
                                                 }
                                                  
                                                /**
           * Set up a thread which listens to the connection from the server for messages
                                                 */
                             public void startMessageReceiverThread() {
                                 Thread thread = new Thread(() -> {
                           Connection conn = this.server.getConnection();
                                           while (true) {
                                               try {
                                 Message message = conn.receive();
                                    switch (message.getType()) {
                                         case LEADERBOARD:
                                this.intLeaderboardCtrl.setPlayers(
                           ((LeaderboardMessage) message).getPlayers());
                                               break;
                                            case JOKER:
                        JokerMessage jokerMessage = (JokerMessage) message;
                      if (jokerMessage.getJokerType() == JokerType.DECREASE) {
                                   multiplayerQuestionScreenCtrl.
                 decreaseProgress(this.multiplayerQuestionScreenCtrl.getProgress()
                                  * JOKER_DECREASE_TIME_PERCENT);
                                                 }
                                               break;
                                             case JOIN:
                                            case ERROR:
                           this.logger.error("Received error message: " +
                               ((ErrorMessage) message).getError());
                                               break;
                                            case KILLER:
                                              return;
                                                 }
                                     } catch (Exception err) {
                                       err.printStackTrace();
                                              return;
                                                 }
                                                 }
                                                });
                                          thread.start();
                                                 }
                                                  
                                                /**
                             * This method will get a list of questions
                                                 */
                                    public void getQuestions() {
                          this.questions = server.getQuestions(this.seed);
                                                 }
                                                  
                                                /**
                         * This method sets up the multiple choice question
                             * @param question multiple choice question
                 * @param screenCtrl The screen controller which handles the task.
                    * @throws IOException if there is a problem with the parsing
                                                 */
                                  public void setUpMultipleChoice(
                                        MCQuestion question,
                                      QuestionClass screenCtrl
                                       ) throws IOException {
                             hideTextFieldAndRevealButtons(screenCtrl);
                                                  
                                                  
                           // Set up label for the question and answers.
                      String questionText = question.getActivity().getTitle();
                                screenCtrl.setUpLabel(questionText);
                    if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
                                           clearImages();
             screenCtrl.setImageQuestionImageView(question.imageInByteArrayQuestion());
                             screenCtrl.setVisibleImageQuestion(true);
                                                 }
                 screenCtrl.setLabelButton1(Long.toString(question.getAnswer1()));
                 screenCtrl.setLabelButton2(Long.toString(question.getAnswer2()));
                 screenCtrl.setLabelButton3(Long.toString(question.getAnswer3()));
                            screenCtrl.setVisibleEstimateAnswer(false);
                               screenCtrl.setVisibleTextField(false);
                                                 }
                                                  
                                                /**
                          * This method sets up the "instead of" question
                              * @param question "instead of" question
                 * @param screenCtrl The screen controller which handles the task.
                    * @throws IOException if there is a problem with the parsing
                                                 */
                                 public void setUpInsteadQuestion(
                                    InsteadOfQuestion question,
                                      QuestionClass screenCtrl
                                       ) throws IOException {
                             hideTextFieldAndRevealButtons(screenCtrl);
                            // Set up label for the question and answers
                  String questionText = question.getQuestionActivity().getTitle();
                                screenCtrl.setUpLabel(questionText);
                       screenCtrl.setLabelButton1(question.answerString(1));
                       screenCtrl.setLabelButton2(question.answerString(2));
                       screenCtrl.setLabelButton3(question.answerString(3));
                            screenCtrl.setVisibleEstimateAnswer(false);
                                                  
                    if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
                                           clearImages();
                                                  
             screenCtrl.setImageQuestionImageView(question.imageInByteArrayQuestion());
                             screenCtrl.setVisibleImageQuestion(true);
             screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(1), 0);
             screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(2), 1);
                            screenCtrl.setVisibilityImageView(true, 1);
             screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArray(3), 2);
                            screenCtrl.setVisibilityImageView(true, 2);
                            screenCtrl.setVisibilityImageView(true, 0);
                               screenCtrl.setVisibleTextField(false);
                                                 }
                                                 }
                                                  
                                                /**
                      * This method sets up the highest consumption question.
                          * @param question Highest consumption question.
                 * @param screenCtrl The screen controller which handles the task.
                   * @throws IOException If there is a problem with the parsing.
                                                 */
                                 public void setUpHighestQuestion(
                                HighestConsumptionQuestion question,
                                      QuestionClass screenCtrl
                                       ) throws IOException {
                             hideTextFieldAndRevealButtons(screenCtrl);
                                                  
                           // Set up label for the question and answers.
          String questionText = "Which one of these activities consumes the most energy?";
                                screenCtrl.setUpLabel(questionText);
                     screenCtrl.setLabelButton1(question.getActivity1Title());
                     screenCtrl.setLabelButton2(question.getActivity2Title());
                     screenCtrl.setLabelButton3(question.getActivity3Title());
                            screenCtrl.setVisibleEstimateAnswer(false);
                                                  
                    if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
                                           clearImages();
                                                  
         screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArrayActivity3(), 2);
                            screenCtrl.setVisibilityImageView(true, 2);
         screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArrayActivity2(), 1);
                            screenCtrl.setVisibilityImageView(true, 1);
         screenCtrl.setImagesInImageViewsAnswers(question.imageInByteArrayActivity1(), 0);
                            screenCtrl.setVisibilityImageView(true, 0);
                               screenCtrl.setVisibleTextField(false);
                                                 }
                                                 }
                                                  
                                                /**
                     * This method hide the text field and reveal the buttons.
     * @param screenCtrl Controller which dictates whether the singleplayer or the multiplayer
                                *                   screen changes.
                                                 */
               public void hideTextFieldAndRevealButtons(QuestionClass screenCtrl) {
                                screenCtrl.setVisibleButton1(true);
                                screenCtrl.setVisibleButton2(true);
                                screenCtrl.setVisibleButton3(true);
                               screenCtrl.setVisibleTextField(false);
                                                 }
                                                  
                                                /**
                            * This method sets up the estimate question.
                              * @param question The estimate question.
                 * @param screenCtrl The screen controller which handles the task.
                                                 */
                                 public void setUpEstimateQuestion(
                                     EstimateQuestion question,
                                      QuestionClass screenCtrl
                                       ) throws IOException {
                         String questionText = question.getActivityTitle();
                                screenCtrl.setUpLabel(questionText);
                                                  
                    if (screenCtrl instanceof QuestionScreenSinglePlayerCtrl) {
                                           clearImages();
             screenCtrl.setImageQuestionImageView(question.imageInByteArrayQuestion());
                             screenCtrl.setVisibleImageQuestion(true);
                                                 }
                               screenCtrl.setVisibleTextField(true);
                                screenCtrl.setVisibleButton1(false);
                                screenCtrl.setVisibleButton2(false);
                                screenCtrl.setVisibleButton3(false);
                            screenCtrl.setVisibleEstimateAnswer(false);
                                                 }
                                                  
                                                /**
                                 * Hides all the visible imageViews
                                                 */
                                    public void clearImages() {
                   questionScreenSinglePlayerCtrl.setVisibleImageQuestion(false);
                  questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 0);
                  questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 1);
                  questionScreenSinglePlayerCtrl.setVisibilityImageView(false, 2);
                                                 }
                                                  
                                                /**
                           * Take the user to the admin interface screen.
                                                 */
                              public void showAdminInterfaceScreen() {
                           this.primaryStage.setTitle("Admin Interface");
                       this.primaryStage.setScene(this.adminInterfaceScreen);
                                                 }
                                                  
                                                /**
                         * Take the user to the admin add activity screen.
                                                 */
                             public void showAdminAddActivityScreen() {
                                     this.refreshActivities();
                            this.primaryStage.setTitle("Add Activity");
                      this.primaryStage.setScene(this.adminAddActivityScreen);
                                                 }
                                                  
                                                /**
                        * Take the user to the admin remove activity screen.
                                                 */
                           public void showAdminRemoveActivityScreen() {
                                     this.refreshActivities();
                           this.primaryStage.setTitle("Remove Activity");
                    this.primaryStage.setScene(this.adminRemoveActivityScreen);
                                                 }
                                                  
                                                /**
                        * Take the user to the admin remove activity screen.
                                                 */
                            public void showAdminEditActivityScreen() {
                                     this.refreshActivities();
                            this.primaryStage.setTitle("Edit Activity");
                     this.primaryStage.setScene(this.adminEditActivityScreen);
                                                 }
                                                  
                              public List<Activity> getActivities() {
                                      return this.activities;
                                                 }
                                                  
                                                /**
    * Calls the refresh activities method from the AdminEditActivitiesCtrl class, which refresh
       * the activities shown in the admin interface screens according to the current status
                                         * of the database.
                                                 */
                                 public void refreshActivities() {
                                   this.activities = this.server
                                          .getActivities()
                                             .stream()
                           .sorted(Comparator.comparing(Activity::getId))
                                             .toList();
                this.adminRemoveActivityScreenCtrl.updateDropdown(this.activities);
                 this.adminEditActivityScreenCtrl.updateDropdown(this.activities);
                                                 }
                                                  
                                                /**
                        * This method shows the global leader board screen.
                                                 */
                            public void showGlobalLeaderboardScreen() {
                              globalLeaderboardScreenCtrl.getItems();
                               primaryStage.setTitle("Leader Board");
                             primaryStage.setScene(globalLeaderBoard);
                                                 }
                                                  
                                                /**
                               * This method shows the final screen.
                                                 */
                            public void showSinglePlayerFinalScreen() {
                    singlePlayerFinalSceneCtrl.setTotalScore(player.getPoint());
               singlePlayerFinalSceneCtrl.setCorrectAnswers(numberOfCorrectAnswered);
                           singlePlayerFinalSceneCtrl.addPlayer(player);
                               primaryStage.setTitle("Final Score");
                           primaryStage.setScene(singlePlayerFinalScene);
                  questionScreenSinglePlayerCtrl.setVisibleEstimateAnswer(false);
                                                 }
                                                  
                                                /**
                       * This method shows the final screen for multiplayer.
                                                 */
                             public void showMultiPlayerFinalScreen() {
                             // TODO show final screen for multiplayer
                                                 }
                                                  
                                                /**
              * This method reveals the answer after the player clicked on the button
                                     * @param button the button
                                 * @param textField the text field.
           * @param screenCtrl screen controller which can be either for singleplayer or
                                *                   for multiplayer
                                                 */
      public void updatePoints(Button button, TextField textField, QuestionClass screenCtrl) {
                                          timeLine.stop();
                                                  
                      // in case the player doesn't provide an answer in time
                             if (button == null && textField == null) {
                                       this.currentPoint = 0;
                                     screenCtrl.setProgress(1);
                                    this.showAnswer(screenCtrl);
                                              return;
                                                 }
                                                  
                      // Get the time the player used for guessing the answer
                           double timePassed = screenCtrl.getTimeStamp();
                                                  
                             if (this.question instanceof MCQuestion) {
                  MCQuestion multipleChoiceQuestion = (MCQuestion) this.question;
                                                  
               // The point which the player will receive after answered the question
                        currentPoint = multipleChoiceQuestion.pointsEarned(
                                               1000,
                                Integer.parseInt(button.getText()),
                                             timePassed
                                                 );
                                                  
                                  // Set the point for the player
                         player.setPoint(player.getPoint() + currentPoint);
                                                  
                          // If the player clicked on the correct answer,
                          // number of correct answers would be increased.
                   if (multipleChoiceQuestion.getActivity().getConsumptionInWh()
                               == Long.parseLong(button.getText())) {
                                     numberOfCorrectAnswered++;
                                                 }
                    } else if (question instanceof HighestConsumptionQuestion) {
                         HighestConsumptionQuestion highConsumptionQuestion
                              = (HighestConsumptionQuestion) question;
              int buttonId = button.getId().charAt(button.getId().length() - 1) - '0';
                                                  
          currentPoint = highConsumptionQuestion.pointsEarned(1000, buttonId, timePassed);
                                                  
                         player.setPoint(player.getPoint() + currentPoint);
                                                  
                if (highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh()
              == highConsumptionQuestion.returnEnergyConsumption(button.getText())) {
                                     numberOfCorrectAnswered++;
                                                 }
                                                  
                        } else if (question instanceof InsteadOfQuestion) {
                 InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;
                         currentPoint = insteadQuestion.pointsEarned(1000,
             Integer.parseInt(String.valueOf(button.getId().charAt(12))), timePassed);
                         player.setPoint(player.getPoint() + currentPoint);
                      if (insteadQuestion.correctAnswer().getConsumptionInWh()
                  == insteadQuestion.returnEnergyConsumption(button.getText())) {
                                     numberOfCorrectAnswered++;
                                                 }
                         } else if (question instanceof EstimateQuestion) {
                  EstimateQuestion estimateQuestion = (EstimateQuestion) question;
                         currentPoint = estimateQuestion.pointsEarned(1000,
                        Integer.parseInt(textField.getText()), timePassed);
                         player.setPoint(player.getPoint() + currentPoint);
                                                 }
                                                  
                                     // Show the recent score.
                                      showAnswer(screenCtrl);
                                                 }
                                                  
                                                /**
                  * The methods resets the answer buttons to their initial state.
   * @param screenCtrl Screen controller which can be either for singleplayer or for multiplayer.
                                                 */
                        public void clearButtons(QuestionClass screenCtrl) {
            String color = "-fx-background-color: #5b9ad5; -fx-background-radius: 15;";
                                                  
                              screenCtrl.setStyleAnswerButton1(color);
                              screenCtrl.setStyleAnswerButton2(color);
                              screenCtrl.setStyleAnswerButton3(color);
                                                 }
                                                  
                                                /**
  * Method that shows the correct answer after the timer is finished.  The correct answer will be
   * colored with green.  If the player choses the wrong answer, his choice will be colored with
                                               * red.
   * @param screenCtrl Screen controller which can be either for singleplayer or for multiplayer.
                                                 */
                        private void showAnswer(QuestionClass screenCtrl) {
                            Button button = screenCtrl.getInputButton();
                          TextField textField = screenCtrl.getInputText();
                                                  
                                          timeLine.stop();
                                                  
                                    screenCtrl.setProgress(1f);
                                                  
            // This timeline will execute on another thread - run the count-down timer.
                  timeLine = new Timeline(new KeyFrame(Duration.seconds(1), _e ->
                                screenCtrl.decreaseProgress(1 / 3f)
                                                ));
                                     timeLine.setCycleCount(3);
                 timeLine.setOnFinished(_e -> screenCtrl.showIntermediateScene());
                                          timeLine.play();
                                                  
                                       if (button != null) {
            String color = "-fx-background-color: #E37474; -fx-background-radius: 15;";
                                      button.setStyle(color);
                                                 }
                                                  
            String color = "-fx-background-color: #4BA85D; -fx-background-radius: 15;";
                                                  
                               if (question instanceof MCQuestion) {
                     MCQuestion multipleChoiceQuestion = (MCQuestion) question;
                        long button1 = multipleChoiceQuestion.getAnswer1();
                        long button2 = multipleChoiceQuestion.getAnswer2();
                        long button3 = multipleChoiceQuestion.getAnswer3();
          long correctAnswer = multipleChoiceQuestion.getActivity().getConsumptionInWh();
                                                  
                                  if (correctAnswer == button1) {
                              screenCtrl.setStyleAnswerButton1(color);
                               } else if (correctAnswer == button2) {
                              screenCtrl.setStyleAnswerButton2(color);
                               } else if (correctAnswer == button3) {
                              screenCtrl.setStyleAnswerButton3(color);
                                                 }
                    } else if (question instanceof HighestConsumptionQuestion) {
                         HighestConsumptionQuestion highConsumptionQuestion
                              = (HighestConsumptionQuestion) question;
                                                  
                    Activity activity1 = highConsumptionQuestion.getActivity1();
                    Activity activity2 = highConsumptionQuestion.getActivity2();
                    Activity activity3 = highConsumptionQuestion.getActivity3();
       long correctAnswer = highConsumptionQuestion.getCorrectAnswer().getConsumptionInWh();
                                                  
                       if (correctAnswer == activity1.getConsumptionInWh()) {
                              screenCtrl.setStyleAnswerButton1(color);
                   } else if (correctAnswer == activity2.getConsumptionInWh()) {
                              screenCtrl.setStyleAnswerButton2(color);
                   } else if (correctAnswer == activity3.getConsumptionInWh()) {
                              screenCtrl.setStyleAnswerButton3(color);
                                                 }
                        } else if (question instanceof InsteadOfQuestion) {
                 InsteadOfQuestion insteadQuestion = (InsteadOfQuestion) question;
                                                  
                          Activity answer1 = insteadQuestion.getAnswer1();
                          Activity answer2 = insteadQuestion.getAnswer2();
                          Activity answer3 = insteadQuestion.getAnswer3();
             long correctAnswer = insteadQuestion.correctAnswer().getConsumptionInWh();
                                                  
                        if (correctAnswer == answer1.getConsumptionInWh()) {
                              screenCtrl.setStyleAnswerButton1(color);
                    } else if (correctAnswer == answer2.getConsumptionInWh()) {
                              screenCtrl.setStyleAnswerButton2(color);
                    } else if (correctAnswer == answer3.getConsumptionInWh()) {
                              screenCtrl.setStyleAnswerButton3(color);
                                                 }
                         } else if (question instanceof EstimateQuestion) {
                                          String message;
                                                  
                             screenCtrl.setVisibleEstimateAnswer(true);
                                                  
                  EstimateQuestion estimateQuestion = (EstimateQuestion) question;
                                      if (textField != null) {
                         currentPoint = estimateQuestion.pointsEarned(1000,
                               Integer.parseInt(textField.getText()),
                                    screenCtrl.getTimeStamp());
                                              } else {
                                         currentPoint = 0;
                                                 }
                                                  
                                     if (currentPoint < 800) {
                                     if (currentPoint > 300) {
                color = "-fx-background-color: #f0de8d; -fx-background-radius: 15;";
                                       message = "Not bad!";
                                              } else {
                color = "-fx-background-color: #E37474; -fx-background-radius: 15;";
                                          message = "Oh!";
                                                 }
                                              } else {
                                      message = "Well done!";
                                                 }
                                                  
                             screenCtrl.setEstimateAnswerStyle(color);
                                 screenCtrl.setEstimateAnswerLabel(
                                              message
                                    + " The correct answer is: "
                       + estimateQuestion.getActivity().getConsumptionInWh()
                                                 );
                                                  
                                      if (textField != null) {
                                         textField.clear();
                                                 }
                                                 }
                                                 }
                                                  
                                                /**
                * Getter method for the singleplayer pre game controller controller.
                          * @return The singleplayer pre game controller.
                                                 */
                   public SinglePlayerPreGameCtrl getSinglePlayerPreGameCtrl() {
                                  return singlePlayerPreGameCtrl;
                                                 }
                                                  
                                                /**
                  * Getter method for the singleplayer pre game controller screen.
                            * @return The singleplayer pre game screen.
                                                 */
                           public Scene getSinglePlayerPreGameScreen() {
                                 return questionScreenSinglePlayer;
                                                 }
                                                  
                                                /**
                        * Getter method for multiplayer pre game controller.
                             * @return Multiplayer pre game controller.
                                                 */
                    public MultiplayerPreGameCtrl getMultiplayerPreGameCtrl() {
                                   return multiplayerPreGameCtrl;
                                                 }
                                                  
                                                /**
                        * Getter method for the multiplayer question screen.
                             * @return The multiplayer question screen.
                                                 */
                           public Scene getMultiplayerQuestionScreen() {
                                 return multiPlayerQuestionScreen;
                                                 }
                                                  
                                                /**
                        * Getter method for intermediate screen controller.
                             * @return Intermediate screen controller.
                                                 */
                     public IntermediateSceneCtrl getIntermediateSceneCtrl() {
                                   return intermediateSceneCtrl;
                                                 }
                                                  
                                                /**
                   * Getter method for the number of questions answerd this far.
                        * @return The number of questions answerd this far.
                                                 */
                             public int getNumberOfQuestionAnswered() {
                                  return numberOfQuestionAnswered;
                                                 }
                                                  
                                                /**
                                  * Getter method for the player.
                                       * @return The player.
                                                 */
                                    public Player getPlayer() {
                                           return player;
                                                 }
                                                  
                                                /**
                             * Getter method for the number of points.
               * @return The number of points received at the last question answered.
                                                 */
                                   public int getCurrentPoint() {
                                        return currentPoint;
                                                 }
                                                  
                                                /**
                               * Getter method for the primary stage.
                                    * @return The primary stage.
                                                 */
                                  public Stage getPrimaryStage() {
                                        return primaryStage;
                                                 }
                                                  
                                                /**
                            * Getter method for the intermediate scene.
                                 * @return The intermediate scene.
                                                 */
                               public Scene getIntermediateScene() {
                                     return intermediateScene;
                                                 }
                                                  
                                                /**
                      * Getter method for the intermediate leaderboard scene.
                           * @return The intermediate leaderboard scene.
                                                 */
                         public Scene getIntermediateLeaderboardScreen() {
                               return intermediateLeaderboardScreen;
                                                 }
                                                  
                                                /**
                    * Getter method for the intermediate leaderboard controller.
                         * @return The intermediate leaderboard controller.
                                                 */
                    public IntLeaderboardCtrl getIntermediateLeaderboardCtrl() {
                                     return intLeaderboardCtrl;
                                                 }
                                                  
                                                /**
                                                * 	Setter method for seed
                                                 *
                                 * @param seed The seed that is set
                                                 */
                                  public void setSeed(long seed) {
                                         this.seed = seed;
                                                 }
                                                  
                                                /**
                 * Shows the final leaderboard scene of the multiplayer game mode.
           * This method needs to be changed in the future to allow displaying the names
                                      * of the top 3 players.
                                                 */
                             public void showTopPlayersLeaderboard() {
                            primaryStage.setTitle("Final Leaderboard");
                         primaryStage.setScene(this.topPlayersLeaderboard);
                                                 }
                                                  
                                                /**
                                 * setter for the player nickname.
                       * @param nickName the nickname selected by the player.
                                                 */
                             public void setNickname(String nickName) {
                                     this.nickname = nickName;
                                                 }
                                                  
                                                /**
                                 * getter for the player's nickname
                            * @return the player's nickname as a String.
                                                 */
                                   public String getNickname() {
                                       return this.nickname;
                                                 }
                                                  
                                                /**
          * this method calls the method joinLobby from the multiplayerPreGameCtrl class,
                        * which handles the player joining a lobby feature.
                                                 */
                                     public void joinLobby() {
                              this.multiplayerPreGameCtrl.joinLobby();
                                                 }
                                                  
                                                /**
                          * Changes the scene to intermediate leaderboard
                                                 *
                   * @throws IOException if something goes wrong with the socket
                       * @throws ClassNotFoundException if Class is not found
                                                 */
           public void changeToLeaderboard() throws IOException, ClassNotFoundException {
                  this.primaryStage.setScene(this.intermediateLeaderboardScreen);
                             this.primaryStage.setTitle("Leaderboard");
                              this.intLeaderboardCtrl.displayScores();
                                                 }
                                                  
                                                /**
                      * Getter method for the multiplayer questions controller
                                                 *
                             * @return multiplayer questions controller
                                                 */
             public MultiplayerQuestionScreenCtrl getMultiplayerQuestionScreenCtrl() {
                               return multiplayerQuestionScreenCtrl;
                                                 }
                                                  
                     public void renderTheMessageInTheChatBox(String message) {
                       multiplayerQuestionScreenCtrl.updateMessage(message);
                                                 }
                                                  
                   public void setGameIdInMultiplayerQuestionScreen(int gameId) {
                          multiplayerQuestionScreenCtrl.setGameId(gameId);
                                                 }
                                                  }