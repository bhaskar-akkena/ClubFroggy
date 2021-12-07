import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import java.io.FileInputStream;
//A GUI for clients to connect with
public class ClientGUI extends Application implements EventHandler<ActionEvent>{
 //GUI elements
 private Button btnLogIn=new Button("Log In");
 private Button btnSignUp=new Button("Sign Up");
 private TextField tfUsername=new TextField();
 private TextField tfPassword=new TextField();
 private Label lblUsername=new Label("Username: ");
 private Label lblPassword=new Label("Password: ");
 private ComboBox<Label> cbColor=new ComboBox<Label>();
 private Label lblPink=new Label("Pink");
 private Label lblOrange=new Label("Orange");
 private Label lblGreen=new Label("Green");
 private Label lblRed=new Label("Red");
 private Label lblYellow=new Label("Yellow");
 private Label lblBlue=new Label("Blue");
 private TextArea taLog=new TextArea();
 private Label lblMessage=new Label("Message: ");
 private TextField tfMessage=new TextField();
 private Button btnSend=new Button("Send");
 private Button btnDisconnect=new Button("Disconnect");
 
 //Images
        Image icon = new Image(new FileInputStream("Imgs\\frog.png"));
        Image imgBlue = new Image(new FileInputStream("Imgs\\blue.png"));
        Image imgGreen = new Image(new FileInputStream("Imgs\\green.png"));
        Image imgOrange = new Image(new FileInputStream("Imgs\\orange.png"));
        Image imgPink = new Image(new FileInputStream("Imgs\\pink.png"));
        Image imgPurple = new Image(new FileInputStream("Imgs\\purple.png"));
        Image imgRed = new Image(new FileInputStream("Imgs\\red.png"));
        Image imgYellow = new Image(new FileInputStream("Imgs\\yellow.png"));
 
 //Scene design
 private HBox box1=new HBox();
 private HBox box2=new HBox();
 private HBox box3=new HBox();
 private HBox box4=new HBox();
 
 //Others
 private VBox root=new VBox();
 private Stage stage;
 private Scene scene;
 private FroggyClient fc;
 private String userName="";
 
 //launches the GUI
 public static void main(String[] args){launch();}
 
 //Starts the GUI
 @Override
 public void start(Stage s){
  //basic housekeeping
  stage=s;
  taLog.setEditable(false);
  stage.setTitle("Club Froggy!");
  
  //Image Icons
        ImageView ivIcon = new ImageView(icon);
        ivIcon.setFitHeight(20);
        ivIcon.setFitWidth(20);

        ImageView ivBlue = new ImageView(imgBlue);
        ivBlue.setFitHeight(20);
        ivBlue.setFitWidth(20);

        ImageView ivGreen = new ImageView(imgGreen);
        ivGreen.setFitHeight(20);
        ivGreen.setFitWidth(20);

        ImageView ivOrange = new ImageView(imgOrange);
        ivOrange.setFitHeight(20);
        ivOrange.setFitWidth(20);

        ImageView ivPink = new ImageView(imgPink);
        ivPink.setFitHeight(20);
        ivPink.setFitWidth(20);

        ImageView ivPurple = new ImageView(imgPurple);
        ivPurple.setFitHeight(20);
        ivPurple.setFitWidth(20);

        ImageView ivRed = new ImageView(imgRed);
        ivRed.setFitHeight(20);
        ivRed.setFitWidth(20);

        ImageView ivYellow = new ImageView(imgYellow);
        ivYellow.setFitHeight(20);
        ivYellow.setFitWidth(20);

        lblBlue.setGraphic(ivBlue);
        lblGreen.setGraphic(ivGreen);
        lblYellow.setGraphic(ivYellow);
        lblOrange.setGraphic(ivOrange);
        lblRed.setGraphic(ivRed);
        lblPink.setGraphic(ivPink);
  
  //makes the buttons do things
  btnLogIn.setOnAction(this);
  btnSignUp.setOnAction(this);
  btnSend.setOnAction(this);
  btnSend.setStyle("-fx-background-color: #32cd32; ");
  btnDisconnect.setOnAction(this);
  btnDisconnect.setStyle("-fx-background-color: #e34234; ");
          root.setStyle("-fx-background-color: linear-gradient(to bottom, #ff7f50, #6a5acd)");

    
        //Adds elements to where they get shown + cleanup
  tfMessage.setPrefWidth(466);
  cbColor.setPromptText("Choose Froggy Color");
        cbColor.getItems().addAll(lblPink, lblOrange, lblRed, lblBlue, lblGreen, lblYellow);
        box1.getChildren().addAll(lblUsername, tfUsername, btnLogIn);
        box1.setSpacing(5);
        box2.getChildren().addAll(lblPassword, tfPassword, btnSignUp);
        box2.setSpacing(5);
        box3.getChildren().addAll(lblMessage, tfMessage);
        box3.setSpacing(5);
        box4.getChildren().addAll(btnSend, btnDisconnect);
        box4.setSpacing(400);
        root.getChildren().addAll(box1, box2, cbColor, taLog, box3, box4);
        root.setSpacing(5);
        root.setPadding(new Insets(5,5,5,5));
  
  //starts the GUI
  scene=new Scene(root);
  stage.setScene(scene);
  stage.show();
  
  //Activates the code that actually does things
  fc=new FroggyClient(taLog);
 }//end start
 
 //buttons do things when clicked
 @Override
 public void handle(ActionEvent e){
  Button b=(Button)e.getSource();
  
  switch(b.getText()){
   case"Log In":
    doLogIn();
    break;
   case"Sign Up":
    doSignUp();
    break;
   case"Send":
    doSend();
    break;
   case"Disconnect":
    doDisconnect();
    break;
  }//end switch
 }//end handle
 
 public void doLogIn(){
  if(userName.equals("")){
   userName=tfUsername.getText();
   fc.logIn(tfUsername.getText(), tfPassword.getText());
  }
 }
 
 public void doSignUp(){
  if(userName.equals("")){
   userName=tfUsername.getText();
   fc.createAccount(tfUsername.getText(), tfPassword.getText(), cbColor.getValue().getText());
  }
 }
 
 public void doSend(){
  if(!userName.equals("")){
   fc.sendMessage(new Message(cbColor.getValue().getText(), userName, tfMessage.getText()));
  }
 }
 
 public void doDisconnect(){
  fc.disconnect();
 }
 
 
 
}//end ClientGUI
