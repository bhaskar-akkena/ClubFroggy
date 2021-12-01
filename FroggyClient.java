import java.io.*;
import java.net.*;

public class FroggyClient{
 //for use in the code
 private String address;
 private final int PORT=5001;
 private Socket soc=null;
 private DataOutputStream dos=null;
 private DataInputStream dis = null;
 private boolean hasConnected=false;
 private boolean loggedIn=false;
 
 //for use in messages
 private String response;
 private Message message;
 
 //Makes a client with associated socket, input, and output streams
 public FroggyClient(String a){
  address=a;
  hasConnected=true;
  try{
   soc=new Socket(address, PORT);
   dos=new DataOutputStream(soc.getOutputStream());
   dis=new DataInputStream(soc.getInputStream());
  }catch(Exception e){return;}
 }//endFroggyClient
 
 //disconnects from the server
 public void disconnect(){
  try{
   dos.writeUTF("d");
   dos.flush();
   soc.close();
   dos.close();
   dis.close();
   hasConnected=false;
  }catch(IOException ioe){return;}
 }//end disconnect
 
 //Method to log in a user
 public void  logIn(String username, String password){
  try{
   //sends login information to the server
   dos.writeUTF("l");
   dos.writeUTF(username);
   dos.writeUTF(password);
   dos.flush();
  }catch(IOException IOE){return ;}
 }//end Log In
 
 //method to create an account
 public void createAccount(String username, String password){
  try{
   //Informs the server of the account's username and password
   dos.writeUTF("c");
   dos.writeUTF(username);
   dos.writeUTF(password);
   dos.flush();
  }catch(IOException ioe){return;}
 }//end createAccount
 
 //send a message
 public void sendMessage(Message m){
  try{
   dos.writeUTF("sm");
   dos.writeObject(m)
   dos.flush();
  }catch(IOException ioe){return;}
 }//end sendMessage
 
 //get methods
 public DataOutputStream getDos(){return dos;}
 public DataInputStream getDis(){return dis;}
 public Socket getSocket(){return soc;}
 public String getAddress(){return address;}
 public boolean getConnected(){return hasConnected;}
 public boolean getLoggedIn(){return loggedIn;}

 
 //set method
 public void setAddress(String a){address=a;}
 
 //ClientThread's purpose is to listen for incoming transmissions and react accordingly
 class ClientThread extends Thread{
  DataInputStream dis=null;
  
  //Constructor
  public ClientThread(DataInputStream d){
   dis=d;
  }//end Constructor 
  
  public void run(){
   //Reads incoming from server, reacts appropriately
   while(true){
    try{
     response = dis.readUTF();
     
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
      case"ut"
       informUser("This Username has already been taken");
       break;
      
      //informs the user that the password is incorrect
      case"ip":
       informUser("Incorrect Password");
       break;
      
      //informs the user that there's a new message
      case"nm":
       message=dis.readObject();
       informUser(message);
       break;
     }//end switch
    }catch(IOException ioe){return;}//end try/catch   
   }//end while
  }//end run
  
 }//end ClientThread
}//end Class