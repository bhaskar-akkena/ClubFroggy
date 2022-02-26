import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

//GUI for the server
public class ServerGUI extends Application implements EventHandler<WindowEvent> {
   private TextArea taLog=new TextArea();
   private Stage stage;
   private Scene scene;
   private FroggyServer fs;
 
   public static void main(String[] args) { launch(); }
 
   //Starts the GUI
   @Override
   public void start(Stage s) {
      //sets up and displays a GUI
      stage=s;
      stage.setTitle("Club Froggy Server");
      taLog.setEditable(false);
      scene=new Scene(taLog);
      stage.setScene(scene);
      stage.show();
      fs=new FroggyServer(taLog);
  
      //makes closing the GUI close the rest of the program gracefully
      stage.setOnCloseRequest(this);
   }//end start
 
   //when it closes, saves the userlist and disconnects clients
   @Override
   public void handle(WindowEvent e) {
      fs.save();
      fs.getServerThread().doServerDisconnect();
      System.exit(0);
   }//end handle
}//end ServerGUI