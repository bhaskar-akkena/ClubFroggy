import java.io.*;
import java.net.*;
import javafx.scene.control.TextArea;

public class FroggyClient{
 //for use in the code
 private String address;
 private final int PORT=5001;
 private Socket soc=null;
 private ObjectOutputStream oos=null;
 private ObjectInputStream ois = null;
 private boolean hasConnected=false;
 private boolean loggedIn=false;
 private TextArea taLog;
 private Account account;
 private ClientThread ct;
 
 //for use in messages
 private String response;
 private Message message;
 
 //Makes a client with associated socket, input, and output streams
 public FroggyClient(TextArea ta){
  address="127.0.0.1";
  taLog=ta;
  hasConnected=true;
  try{
   soc=new Socket(address, PORT);
   oos=new ObjectOutputStream(soc.getOutputStream());
   ois=new ObjectInputStream(soc.getInputStream());
   ct=new ClientThread(ois);
   ct.start();
  }catch(Exception e){return;}
 }//endFroggyClient
 
 //methods to display to the textArea
 public void informUser(String s){
  taLog.appendText(s+"\n");
 }//informs user of something
 public void informUser(Message m){
  taLog.appendText(m.toString()+"\n");
 }//appends a message
 
 //disconnects from the server
 public void disconnect(){
  try{
   oos.writeUTF("d");
   oos.flush();
   soc.close();
   oos.close();
   ois.close();
   hasConnected=false;
  }catch(IOException ioe){return;}
 }//end disconnect
 
 //Method to log in a user
 public void  logIn(String username, String password){
  try{
   //sends login information to the server
   oos.writeUTF("l");
   oos.writeUTF(username);
   oos.writeUTF(password);
   oos.flush();
  }catch(IOException IOE){return ;}
 }//end Log In
 
 //method to create an account
 public void createAccount(String username, String password, String color, int age){
  try{
   //Informs the server of the account's username, password, color, and age
   oos.writeUTF("c");
   account=new Account(username, password, age, color);
   oos.writeObject(account);
   oos.flush();
  }catch(IOException ioe){return;}
 }//end createAccount
 
 //send a message
 public void sendMessage(Message m){
  try{
   oos.writeUTF("sm");
   oos.writeObject(m);
   oos.flush();
  }catch(IOException ioe){return;}
 }//end sendMessage
 
 //get methods
 public ObjectOutputStream getDos(){return oos;}
 public ObjectInputStream getDis(){return ois;}
 public Socket getSocket(){return soc;}
 public String getAddress(){return address;}
 public boolean getConnected(){return hasConnected;}
 public boolean getLoggedIn(){return loggedIn;}

 
 //set method
 public void setAddress(String a){address=a;}
 
 //ClientThread's purpose is to listen for incoming transmissions and react accordingly
 class ClientThread extends Thread{
  private ObjectInputStream ois=null;
  private boolean keepGoing=true;
  
  //Constructor
  public ClientThread(ObjectInputStream d){
   ois=d;
  }//end Constructor 
  
  public void run(){
   //Reads incoming from server, reacts appropriately
   while(keepGoing){
    try{
     response = ois.readUTF();
     
     switch(response){
      //Informs the user that they are logged in
      case"li":
       loggedIn=true;
       informUser("Logged In");
       break;
       
      //informs the user that their account has been created
      case"ac":
       loggedIn=true;
       informUser("Account Created");
       break;
      
      //informs the user that their username is invalid
      case"iu":
       informUser("Invalid Username");
       break;
      
      //informs the user that the username is taken
      case"ut":
       informUser("This Username has already been taken");
       break;
      
      //informs the user that the password is incorrect
      case"ip":
       informUser("Incorrect Password");
       break;
      
      //informs the user that there's a new message
      case"nm":
       try{
        message=(Message)ois.readObject();
        informUser(message);
        break;
       }catch(ClassNotFoundException cnfe){return;}
       
      //disconnects
      case"di":
       informUser("Disconnected by Server");
       keepGoing=false;
       ois.close();
       oos.close();
       soc.close();
     }//end switch
    }catch(IOException ioe){return;}//end try/catch   
   }//end while
  }//end run
  
 }//end ClientThread
}//end Class
