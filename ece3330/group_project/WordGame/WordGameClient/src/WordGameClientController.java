import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;



public class WordGameClientController {

        //Sign in components
        @FXML
        private Label userNameLabel; //Label to show what the user should input in the username field

        @FXML
        private Label passwordLabel; //Label to show what the user should put in the password field
        @FXML
        private TextField userNameTextField; //textField to take in user input for the userName string

        @FXML
        private TextField passwordTextField; //textField to take in user input for the passWord string

        @FXML
        private Button signInButton; //button that is used to initiate the taking of the userName and passWord string from the fields

        @FXML
        private Button createNewButton; //button to change the signIn page to one where the user can create an account

        @FXML
        private Label signInWarningMessage; //Message to pop up if the users account is not present in the database

        private boolean guestMode; //Boolean that will activate if the user check guest mode so no data will be updated on database
        private boolean createNewAccount; //boolean to single to the database that the information coming in is for a new user

        //lobbyMenu components
        @FXML
        private Label currentPlayersLabel; //Label to show the current players in the server

        @FXML
        private Button playButton; //Button to be used by the owner of the lobby to initiate the settings and create the lobby
        @FXML
        private Button letterModeButton;

        @FXML
        private Button wordModeButton;

        @FXML
        private Button englishButton;

        @FXML
        private Button frenchButton;

        @FXML
        private Button italianButton;

        @FXML
        private Label languageLabel;

        @FXML
        private Label gameModeLabel;



        private String language; //String to pass the selected language to the game
        private String gameMode; //String to pass the selected Game mode to game

        //Game components
        Button[] buttonArray; //Array to hold the buttons that the user can click
        Button[] selectedButtonArray; //Array to hold the buttons the user has selected
        private int numButtons; //Int to hold the number of buttons the game has passed
        ArrayList<String> wordGuess; //Arraylist that hold the current word guess of the user

        private int lettersClicked; //Int to show how many letters have been selected to know


        @FXML
        private GridPane buttonGrid; //Grid pane to hold the letter buttons for the game

        @FXML
        private Label timeLabel; //Label to show the current time remaining in the game(time given by the server





        //scoreboard components
        @FXML
        private Label firstLabel; //Label to display the player in first place

        @FXML
        private Label secondLabel; //Label to display the player in second place

        @FXML
        private Label thirdLabel; //Label to display player in third place

        //FXML navigation components
        private Stage stage;
        private Scene scene;
        private Parent root;

        public int currentStage;


        @FXML // When login button is pressed on the main page
        /** Method to change the scene to the Lobby page */
        private void takeMeToLobbyPage(ActionEvent event) throws IOException {
                // Use objects because getResource could be null
                currentStage = 1; //update current stage
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("lobbyMenu.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }

        @FXML // When create new account button is pressed on the main page
        /** Method to change the scene to the Game page */
        private void takeMeToGamePage(ActionEvent event) throws IOException {
                // Use objects because getResource could be null
                currentStage = 2; //update current stage
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }

        @FXML // When create new account button is pressed on the main page
        /** Method to change the scene to the Scoreboard page */
        private void takeMeToScoreboardPage(ActionEvent event) throws IOException {
                // Use objects because getResource could be null
                currentStage = 3; //update current stage
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scoreboardScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }

        //Initialize Method
        @FXML
        /** Method to set all the variables to their default values to run the program
         * for objects this method also creates the new objects to be used
         */
        public void initialize() {
                language = "english";
                gameMode = "/settings gamemode LETTER";
                wordGuess = new ArrayList<>();
                lettersClicked = 0;
                language = "english";
                numButtons = 4;
                currentStage = 0; //Initialize the value that shows what stage the program is on
                /*
                setButtons(numButtons, testCharArray);
                resetButtons(numButtons);
                 */

        }

        //Sign in Methods
        @FXML
        /** This method is used when the user doesn't have a profile and would like to create one
         * it will set the sign-in page to be able to take in input to send to the database to create a profile
         */
        void createButtonPressed(ActionEvent event) {
                createNewAccount = true; //Boolean to tell the database to add a new profile to the database
                signInWarningMessage.setVisible(false); //Sets the warning message to false as the user is making a new account
                signInWarningMessage.setText("Account already, exists or invalid input"); //Sets the error message to a different text since it would be a different error for making a class
                //Set components to represent a new account page
                signInButton.setText("Create Account");
                createNewButton.setVisible(false);
                userNameLabel.setText("Create username");
                passwordLabel.setText("Create password");
        }

        @FXML
        /** This method allows the user to have an option of not having an account but still being able to play the game
         * This will result in the user not being able to have their score be on the scoreboard
         */
        void guestButtonPressed(ActionEvent event) throws IOException {
                guestMode = true; //Allows the game and database to know not to write or reachieved scoreboard information
                takeMeToLobbyPage(event); //Sends the user to the next FXML file
        }

        @FXML
        /** this method will retrieve the information from the text fields and send them to the database to check to see if the user exists
         * if the user does not exist the error message will appear
         * if the user does exist the next FXML file will be opened
         */
        void signInButtonPressed(ActionEvent event) throws IOException {
                //String to be passed to the database
                String userName = userNameTextField.getText();
                String passWord = passwordTextField.getText();
        }

        //Lobby Methods
        @FXML
        /** Method to be used by the owner of the lobby to initialize the settings of the game
         * this method will only be used by the owner of the lobby(The first person in the lobby) and each user after will not be able to change settings
         */
        void playButtonPressed(ActionEvent event) throws IOException, URISyntaxException {
                //Removes settings options
                playButton.setVisible(false);
                currentPlayersLabel.setVisible(true);
                gameModeLabel.setVisible(false);
                languageLabel.setVisible(false);
                englishButton.setVisible(false);
                italianButton.setVisible(false);
                frenchButton.setVisible(false);
                wordModeButton.setVisible(false);
                letterModeButton.setVisible(false);
                takeMeToGamePage(event); //Send user to the game FXML file
                loopSound("resources/WordGameV1.wav"); //Play sound for the game
        }

        @FXML
        /** set language setting to english to be passed to game */
        void englishButtonPressed(ActionEvent event) {language = "english";}

        @FXML
        /** set language setting to french to be passed to game */
        void frenchButtonPressed(ActionEvent event) {language = "french";}

        @FXML
        /** set language setting to italian to be passed to game */
        void italianButtonPressed(ActionEvent event) {language = "italian";}

        @FXML
        /** set game mode to letter mode to be passed to game */
        void letterModeButtonPressed(ActionEvent event) {gameMode = "/settings gamemode LETTER";}

        @FXML
        /** set game mode to word mode to be passed to game */
        void wordModeButtonPressed(ActionEvent event) {gameMode = "/settings gamemode WORD";}

        //Game Methods

        /** setButtons is used when the game is initialized to create all the buttons and create a corresponding grid with the given number of letters and sets the buttons to contain the given characters
         * in this class the listeners for the buttons are also present, and they will send the buttons to the corresponding place on teh GUI to show what the user guess is
         * @param n is the number of characters for the buttons
         * @param letters are the corresponding characters for the buttons
         */
        public void setButtons(int n, char[] letters){
                buttonArray = new Button[n]; //Initialize the button array
                //For loop to create the button objects and set them to their corresponding letter
                for(int i = 0; i < n; i++){
                        buttonArray[i] = new Button();
                        buttonArray[i].setText(String.valueOf(letters[i]));
                        int whichButton = i; //Int for the listener to determine what button is being changed
                        //Listeners for each of the buttons
                        buttonArray[i].setOnAction(actionEvent -> {
                                selectedButtonArray[lettersClicked].setText(buttonArray[whichButton].getText()); //Set the text of the button that is displaying the users guess
                                selectedButtonArray[lettersClicked].setVisible(true); //Sets the guess button to visible
                                buttonArray[whichButton].setVisible(false);//sets the option button to not visible to visuals the button moving
                                wordGuess.add(buttonArray[whichButton].getText()); //Adds the letter to the users guess
                                lettersClicked++; //Ticks how many letters have selected
                        });
                }
        }

        /** resetButtons is used like the setButtons method but for the reverse, this method will be used to initialize the buttons that showcase the users
         * current guess and when the buttons are clicked the letter is removed from the guess and the button reapers in the option button pool
         * @param n is the number of buttons
         */
        public void resetButtons(int n){
                selectedButtonArray = new Button[n]; //Initialize the array of buttons
                //For loop to create the button objects and set the initial state to not visible
                for(int i = 0; i < n; i++){
                        selectedButtonArray[i] = new Button();
                        selectedButtonArray[i].setVisible(false);
                        int whichButton = i; //int to show which button is to be changed in the listener
                        //Create listener for each button
                        selectedButtonArray[i].setOnAction(actionEvent -> {
                                selectedButtonArray[whichButton].setVisible(false);
                                //For loop that makes the button option visable again
                                for(int j = 0; j < n; j++){
                                        if(buttonArray[j].getText() == selectedButtonArray[whichButton].getText()){
                                                buttonArray[j].setVisible(true);
                                        }
                                }
                                wordGuess.remove(selectedButtonArray[whichButton].getText()); //removes letter from users guess
                                //For loop to reset the look of the users guess so there is no gaps in the display of the guess
                                for(int x = 0; x < n; x++){
                                        if(selectedButtonArray[x].isVisible() == false) {
                                                for (int y = x; y < n; y++) {
                                                        if (selectedButtonArray[y].isVisible() == true) {
                                                                selectedButtonArray[x].setText(selectedButtonArray[y].getText());
                                                                selectedButtonArray[x].setVisible(true);
                                                                selectedButtonArray[y].setVisible(false);
                                                                y = n;
                                                        }
                                                }
                                        }
                                }
                                lettersClicked--; //Ticks the letters selected counter down
                        });
                }
        }

        @FXML
        /** checkButtonPressed method takes the current guess of the user to send to the database to check if valid word
         * then if true is passed to the game to determine the amount of points and will then be passed back to play corresponding sound
         */
        void checkButtonPressed(ActionEvent event) {
                wordGuessToString();
        }

        /** startGame initializes the GridPane with the correct amount of columns and puts the buttons in the GridPane */
        public void startGame(){
                for(int i = 0; i < numButtons; i++){
                        buttonGrid.addColumn(i,buttonArray[i], selectedButtonArray[i]);
                }
        }

        /** wordGuessToString take the wordGuess array and turns it into string to be used in the game */
        private String wordGuessToString(){
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < wordGuess.size(); i++){
                     stringBuilder.append(wordGuess.get(i));
                }
                String str = stringBuilder.toString();
                return str;
        }

        /** playSound takes a file and plays the corresponding sound */
        public static void playSound(String filename) throws URISyntaxException {

                URL url = WordGameClientController.class.getResource(filename); //Get the relative path of the file
                File Clap = new File(url.toURI()); //use the path to create the file

                //Create a clip of the audio file and play it
                try{
                      Clip clip = AudioSystem.getClip();
                      clip.open(AudioSystem.getAudioInputStream(Clap));
                      clip.start();
                }
                catch(Exception e){
                        System.out.println("Error");
                }
        }

        /** loopSound is like playSound but instead is used for the background music, so it loops */
        public static void loopSound(String filename) throws URISyntaxException {

                URL url = WordGameClientController.class.getResource(filename); //Get the relative path of the file
                File Clap = new File(url.toURI());//use the path to create the file
                //Create a clip of the audio file and play it
                try{
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(Clap));
                        clip.loop(15);
                }
                catch(Exception e){
                        System.out.println("Error");
                }
        }
}




//Image Credits:
//Image for background by Racool_studio on Freepik