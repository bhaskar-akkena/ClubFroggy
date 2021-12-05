import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends Application {
    final TextArea jtextFilDiscu = new TextArea();
    final TextArea jtextListUsers = new TextArea();
    final TextField jtextInputChat = new TextField();
    private String oldMsg = "";
    private Thread read;
    private String serverName;
    private int PORT;
    private String name;
    BufferedReader input;
    PrintWriter output;
    Socket server;

    HBox hbox = new HBox();
    VBox vbox =  new VBox();
    GridPane gridPane = new GridPane();

    Button jsbtn = new Button("Send");
    Button jsbtndeco = new Button("Disconnect");

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FroggyChat");

        //jtextFilDiscu.setStyle("-fx-background-image: url(https://previews.123rf.com/images/tigatelu/tigatelu1303/tigatelu130300034/18586394-funny-frog-cartoon.jpg); -fx-background-repeat: stretch;-fx-background-position: center center;-fx-content-display: url(https://previews.123rf.com/images/tigatelu/tigatelu1303/tigatelu130300034/18586394-funny-frog-cartoon.jpg)");



        hbox.getChildren().addAll(jtextFilDiscu,jtextListUsers);
        hbox.setPrefHeight(500);
        hbox.setPrefWidth(700);

        jtextFilDiscu.setPrefSize(600,500);
        jtextListUsers.setPrefSize(200,500);
        jtextInputChat.setPrefSize(800,100);

        jtextFilDiscu.setPadding(new Insets(2,2,2,2));
        jtextListUsers.setPadding(new Insets(2,2,2,2));
        jtextInputChat.setPadding(new Insets(10,10,10,10));

        jsbtndeco.setStyle("-fx-background-color: #e34234; ");
        jsbtn.setStyle("-fx-background-color: #32cd32; ");










        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0,0,10,0));

        vbox.setPrefHeight(600);
        vbox.setPrefWidth(500);
        vbox.setPadding(new Insets(10,10,10,10));





        gridPane.add(jsbtn, 1, 0);
        gridPane.add(jsbtndeco, 0,0);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(635);

        vbox.getChildren().addAll(hbox,jtextInputChat,gridPane);

        Scene scene = new Scene(vbox,800,600);

        jtextFilDiscu.getStylesheets().add(this.getClass() .getResource("console.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        jtextInputChat.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            //Send message when Enter button is clicked
            public void handle(KeyEvent e) {
                if(e.getCode()== KeyCode.ENTER){
                    sendMessage();
                }
                //Get last message
                if(e.getCode()==KeyCode.UP){
                    String currentMessage = jtextInputChat.getText().trim();
                    jtextInputChat.setText(oldMsg);
                    oldMsg=currentMessage;
                }
                if(e.getCode()==KeyCode.DOWN){
                    String currentMessage = jtextInputChat.getText().trim();
                    jtextInputChat.setText(oldMsg);
                    oldMsg=currentMessage;
                }
            }
        });

        //Click on send button
        jsbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                sendMessage();
            }
        });


    }

    private void sendMessage() {
    }

    public static void main(String[] args) {
        launch();



    }
}
