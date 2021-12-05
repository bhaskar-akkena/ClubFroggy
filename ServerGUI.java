import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

//GUI for the server
public class ServerGUI extends Application{

 private TextArea taLog=new TextArea();
 private Stage stage;
 private Scene scene;
 
 public static void main(String[] args){launch();}
 
 //Starts the GUI
 @Override
 public void start(Stage s){
  //sets up and displays a GUI
  stage=s;
  stage.setTitle("Club Froggy Server");
  taLog.setEditable(false);
  scene=new Scene(taLog);
  stage.setScene(scene);
  stage.show();
 }//end start
}//end ServerGUI
