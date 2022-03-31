                                       package client.scenes;
                                                  
                                    import java.io.IOException;
                                     import java.util.Optional;
                                                  
                                  import client.utils.ServerUtils;
                                      import commons.Activity;
                                                  
                                      import javafx.fxml.FXML;
                               import javafx.scene.control.TextField;
                                    import javax.inject.Inject;
                                                  
                             public class AdminAddActivityScreenCtrl {
                                  private final MainCtrl mainCtrl;
                                 private final ServerUtils server;
                                                  
                                               @FXML
                                  private TextField activityTitle;
                                                  
                                               @FXML
                                private TextField activityImagePath;
                                                  
                                               @FXML
                               private TextField activityConsumption;
                                                  
                                               @FXML
                                 private TextField activitySource;
                                                  
                                                /**
                           * The AdminAddActivityScreenCtrl constructor.
                               * @param mainCtrl The main controller.
                                                 */
                                              @Inject
                       public AdminAddActivityScreenCtrl(MainCtrl mainCtrl) {
                                     this.mainCtrl = mainCtrl;
                                this.server = mainCtrl.getServer();
                                                 }
                                                  
                                                /**
                           * Take the user to the admin interface screen.
                                                 */
                                               @FXML
                            private void jumpToAdminInterfaceScreen() {
                             this.mainCtrl.showAdminInterfaceScreen();
                                                 }
                                                  
                                                /**
    * Add the activity whose parameters are denoted by the filled out text fields to the servers
                                        * activity database.
   * @return An optional which contains the newly added Activity on success or nothing on error.
                                                 */
                                               @FXML
                             private Optional<Activity> addActivity() {
    /* Get all the contents of the UI text fields to create the activity.  We also need to make
      * an activity ID.  The IDs are prefixed with the string "38-" to denote our group number
     * since that appears to be the convention, and then we append a normalized activity title
       * to it.  To normalize the title, we convert all whitespace to hyphens ('-'), turn all
                          * characters lowercase, and remove all symbols.
                                                 */
                                         long consumption;
                            String title = this.activityTitle.getText();
                        String imagePath = this.activityImagePath.getText();
                           String source = this.activitySource.getText();
                                     String id = "38-" + title
                                              .chars()
              .filter(c -> Character.isWhitespace(c) || Character.isLetterOrDigit(c))
               .map(c -> Character.isWhitespace(c) ? '-' : Character.toLowerCase(c))
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                            .toString();
                                                  
      /* Try to get the consumption as a long instead of a string.  If this fails (because the
       * input was invalid or something like that) then we cannot proceed and return an empty
                                            * optional.
                                                 */
                                               try {
                 consumption = Long.parseLong(this.activityConsumption.getText());
                               } catch (NumberFormatException err) {
                                      return Optional.empty();
                                                 }
                                                  
     /* Create a new activity with the provided information and check to see if the activity is
     * valid.  If the activity is valid then we tell the server to add the activity and return
    * the activity wrapped in an optional.  If an IOException occurred in the process or if the
                        * activity was invalid we return an empty optional.
                                                 */
                                               try {
               Activity a = new Activity(id, title, consumption, imagePath, source);
                                         if (a.isValid()) {
                                    this.activityTitle.clear();
                                 this.activityConsumption.clear();
                                  this.activityImagePath.clear();
                                    this.activitySource.clear();
                          return Optional.of(this.server.addActivity(a));
                                                 }
                                    } catch (IOException err) {
                                       err.printStackTrace();
                                                 }
                                      return Optional.empty();
                                                 }
                                                  }