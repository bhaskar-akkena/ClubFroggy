import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends Application {
    final TextArea jtextFilDiscu = new TextArea();
    final TextArea jtextListUsers = new TextArea();
    final TextField jtextInputChat = new TextField();
    BufferedReader input;
    PrintWriter output;
    Socket server;
    Menu menu = new Menu("Froggy");
    MenuBar menuBar = new MenuBar();
    MenuItem menuItem1 = new MenuItem("Blue");
    MenuItem menuItem2 = new MenuItem("Green");
    MenuItem menuItem3 = new MenuItem("Orange");
    MenuItem menuItem4 = new MenuItem("Pink");
    MenuItem menuItem5 = new MenuItem("Purple");
    MenuItem menuItem6 = new MenuItem("Red");
    MenuItem menuItem7 = new MenuItem("Yellow");
    HBox hbox = new HBox();
    VBox vbox = new VBox();
    GridPane gridPane = new GridPane();
    Button jsbtn = new Button("Send");
    Button jsbtndeco = new Button("Disconnect");
    private String oldMsg = "";
    private Thread read;
    private String serverName;
    private int PORT;
    private String name;

    public static void main(String[] args) {
        launch();


    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FroggyChat");

        Image icon = new Image(new FileInputStream("Imgs//frog.png"));
        Image imgBlue = new Image(new FileInputStream("Imgs//blue.png"));
        Image imgGreen = new Image(new FileInputStream("Imgs//green.png"));
        Image imgOrange = new Image(new FileInputStream("Imgs//orange.png"));
        Image imgPink = new Image(new FileInputStream("Imgs//pink.png"));
        Image imgPurple = new Image(new FileInputStream("Imgs//purple.png"));
        Image imgRed = new Image(new FileInputStream("Imgs//red.png"));
        Image imgYellow = new Image(new FileInputStream("Imgs//yellow.png"));

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


        menu.setGraphic(ivIcon);
        menuItem1.setGraphic(ivBlue);
        menuItem2.setGraphic(ivGreen);
        menuItem3.setGraphic(ivOrange);
        menuItem4.setGraphic(ivPink);
        menuItem5.setGraphic(ivPurple);
        menuItem6.setGraphic(ivRed);
        menuItem7.setGraphic(ivYellow);

        menuBar.getMenus().add(menu);

        menu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7);

        hbox.getChildren().addAll(jtextFilDiscu, jtextListUsers);
        hbox.setPrefHeight(500);
        hbox.setPrefWidth(700);

        jtextFilDiscu.setPrefSize(600, 500);
        jtextListUsers.setPrefSize(200, 500);
        jtextInputChat.setPrefSize(800, 100);

        jtextFilDiscu.setPadding(new Insets(2, 2, 2, 2));
        jtextListUsers.setPadding(new Insets(2, 2, 2, 2));
        jtextInputChat.setPadding(new Insets(10, 10, 10, 10));

        jtextFilDiscu.setEditable(false);
        jtextListUsers.setEditable(false);

        jsbtndeco.setStyle("-fx-background-color: #e34234; ");
        jsbtn.setStyle("-fx-background-color: #32cd32; ");

        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0, 0, 10, 0));

        vbox.setPrefHeight(600);
        vbox.setPrefWidth(500);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(jsbtn, 1, 0);
        gridPane.add(jsbtndeco, 0, 0);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(635);

        vbox.getChildren().addAll(menuBar, hbox, jtextInputChat, gridPane);

        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        stage.show();

        jtextInputChat.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            //Send message when Enter button is clicked
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    sendMessage();
                }
                //Get last message
                if (e.getCode() == KeyCode.UP) {
                    String currentMessage = jtextInputChat.getText().trim();
                    jtextInputChat.setText(oldMsg);
                    oldMsg = currentMessage;
                }
                if (e.getCode() == KeyCode.DOWN) {
                    String currentMessage = jtextInputChat.getText().trim();
                    jtextInputChat.setText(oldMsg);
                    oldMsg = currentMessage;
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
}
