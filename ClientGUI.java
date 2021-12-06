import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class ClientGui extends Application {

    Scene Scene1, Scene2;

    final TextArea jtintro = new TextArea();
    final TextField jtfName = new TextField(this.name);
    final TextField jtfport = new TextField(Integer.toString(this.PORT));
    final TextField jtfAddr = new TextField(this.serverName);
    final Button jcbtn = new Button("Connect");


    final TextArea jtextFilDiscu = new TextArea();
    final TextArea jtextListUsers = new TextArea();
    final TextField jtextInputChat = new TextField();

    BufferedReader input;
    PrintWriter output;

    Socket server;

    Menu menu = new Menu("Select your Froggy");
    MenuBar menuBar = new MenuBar();
    MenuItem menuItem1 = new MenuItem("Blue");
    MenuItem menuItem2 = new MenuItem("Green");
    MenuItem menuItem3 = new MenuItem("Orange");
    MenuItem menuItem4 = new MenuItem("Pink");
    MenuItem menuItem5 = new MenuItem("Purple");
    MenuItem menuItem6 = new MenuItem("Red");
    MenuItem menuItem7 = new MenuItem("Yellow");

    FlowPane flowPane = new FlowPane();
    VBox vbox1 = new VBox();
    VBox vbox2 = new VBox();
    HBox hbox1 = new HBox();
    GridPane gridPane = new GridPane();

    Button jsbtn = new Button("Send");
    Button jsbtndeco = new Button("Disconnect");

    private Thread read;

    private String oldMsg = "";
    private String serverName;
    private int PORT;
    private String name;

    public static void main(String[] args) {
        launch();


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("FroggyChat");

        //Scene 1

        Image icon = new Image(new FileInputStream("Imgs\\frog.png"));
        Image imgBlue = new Image(new FileInputStream("Imgs\\blue.png"));
        Image imgGreen = new Image(new FileInputStream("Imgs\\green.png"));
        Image imgOrange = new Image(new FileInputStream("Imgs\\orange.png"));
        Image imgPink = new Image(new FileInputStream("Imgs\\pink.png"));
        Image imgPurple = new Image(new FileInputStream("Imgs\\purple.png"));
        Image imgRed = new Image(new FileInputStream("Imgs\\red.png"));
        Image imgYellow = new Image(new FileInputStream("Imgs\\yellow.png"));

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

        jcbtn.setOnAction(actionEvent -> {
            try{
                name = jtfName.getText();
                String port = jtfport.getText();
                serverName = jtfAddr.getText();
                PORT = Integer.parseInt(port);

                jtintro.setText("Connecting to " + serverName + " on port " + PORT + "as user" + name);
                server = new Socket(serverName, PORT);

                input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                output = new PrintWriter(server.getOutputStream(), true);
                output.println(name);

                read = new Read();


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Couldn't connect"+e.getMessage());
            }

            primaryStage.setScene(Scene2);
        });
        flowPane.setHgap(23);
        flowPane.getChildren().addAll(jtfName,jtfAddr,jtfport,jcbtn);


        vbox1.getChildren().addAll(menuBar,jtintro, flowPane);
        vbox1.setSpacing(10);
        vbox1.setPadding(new Insets(10, 10, 10, 10));

        jtintro.setText("Hello welcome to the FroggyChat Login");
        jtintro.setEditable(false);
        jtfName.setText("Name");
        jtfAddr.setText("Localhost");
        jtfport.setText("8008");

        Scene1 = new Scene(vbox1, 600, 225);
//      Scene1.setFill(Color.web("#81c483"));


        //Scene 2

        hbox1.getChildren().addAll(jtextFilDiscu, jtextListUsers);
        hbox1.setPrefHeight(500);
        hbox1.setPrefWidth(700);

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

        hbox1.setSpacing(5);
        hbox1.setPadding(new Insets(0, 0, 10, 0));

        vbox2.setPrefHeight(600);
        vbox2.setPrefWidth(500);
        vbox2.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(jsbtn, 1, 0);
        gridPane.add(jsbtndeco, 0, 0);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(635);

        vbox2.getChildren().addAll(hbox1, jtextInputChat, gridPane);

        jsbtndeco.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Disconnect");
            alert.setHeaderText("Do you wish to Exit?");
            alert.setResizable(false);
            alert.setContentText("Terminate Chat");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);
//             read.interrupt();
//             output.close();
            if (button == ButtonType.OK) {
                primaryStage.close();
                System.out.println("Thank you for using ClubFroggy, Bye...");
            } else {
                System.out.println("Canceled");
            }
        });

        Scene2 = new Scene(vbox2, 800, 600);


        primaryStage.setScene(Scene1);
        primaryStage.show();

        //Send message when Enter button is clicked
        jtextInputChat.setOnKeyPressed(e -> {
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
        });

        //Click on send button
        jsbtn.setOnAction(ae -> sendMessage());


    }


    public void sendMessage() {
    }

    static class Read extends Thread{

    }
}
