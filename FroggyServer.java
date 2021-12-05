import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.control.TextArea;
import javafx.application.Platform;


public class FroggyServer{
 //Objects+Variables
 private ObjectInputStream ois=null;
 private ObjectOutputStream oos=null;
 private static TextArea taLog;
 private TreeMap<String, Account> map = new TreeMap<String, Account>();
 private Account tempAccount;
 private ServerThread st = null;
 
 //socket 
 private Socket sSocket=null;
 private Socket cSocket=null;

 //final
 private final File ACCOUNT_FILE=new File("accounts.dat");

 //Constructor. 
 public void FroggyClient(TextArea ta){
  taLog=ta;

  //read the file into the TreeMap
  try{
   //if the file exists
   if(ACCOUNT_FILE.exists()){
    ois=new ObjectInputStream(new FileInputStream(ACCOUNT_FILE));
    
    //put accounts into the TreeMap until it runs out of accounts.
    while(true){
     tempAccount=(Account)ois.readObject();
     map.put(tempAccount.getName(), tempAccount);
    }//end while
   }//end if
  }catch(Exception e){return;}
  
  //displays the machine's ip address
  try{
   log("Running Server with IP address "+InetAddress.getLocalHost());
  }catch(UnknownHostException uhe){log("UnknownHostException "+uhe);
   return;
  }//end try/catch
  
  //Make it do server things
  st=new ServerThread();
  st.start();
 }//end constructor
 
 //logs to the textarea in a thread-safe way
 public static void log(String s){
  Platform.runLater(new Runnable(){
  public void run(){
   taLog.appendText(s);
  }
  });
 }///end log
 
 //saves the accounts to a binary file
 public void save(){
  try{
   //if the file doesn't exist, creates it.
   if(!ACCOUNT_FILE.exists())
    ACCOUNT_FILE.createNewFile();
   
   //writes each account to the file
   oos = new ObjectOutputStream(new FileOutputStream(ACCOUNT_FILE));
   for(Map.Entry<String, Account> entry : map.entrySet())
    oos.writeObject(entry.getValue());
   
   //closes and flushes the ObjectOutputStream
   oos.flush();
   oos.close();
  }catch(IOException ioe){return;}
 }//end save
}//end FroggyServer

//Thread to do server things
class ServerThread extends Thread{
 private ServerSocket sSocket=null;
 private ClientThread ct;
 private Socket cSocket=null;
 
 //final
 private static final int PORT=5002;

 
 //basic Constructor
 public void ServerThread(ServerSocket ss, Socket s){
  sSocket=ss;
  cSocket=s;
 }//end constructor
 
 //run method
 public void run(){
  //sets up the serversocket
  try{
   sSocket=new ServerSocket(PORT);
  }catch(IOException ioe){FroggyServer.log("IOException "+ioe);
   return;
  }//end try/catch
  
  //ongoing
  while(true){
   try{
    //The client's socket from the server's socket
    cSocket=sSocket.accept();
   }catch(IOException ioe){FroggyServer.log("IOException "+ioe);
    return;
   }//end try/catch
   
   //Creates a thread for the client
   ct=new ClientThread(cSocket);
   ct.start();
  }//end while
 }//end run
}//end ServerThread

class ClientThread extends Thread{
 private Socket cSocket=null;
 private String request="";
 private ObjectInputStream in=null;
 private ObjectOutputStream out =null;
 private boolean keepGoing=true;
 
 //constructor
 public ClientThread(Socket cs){
  cSocket=cs;
 }//end Constructor
 
 public void run(){
  //Informs the user that there's a connection
  FroggyServer.log("User Connected");
  try{
   while(keepGoing){
    //waits for inputs
    request=in.readUTF();
    
    switch(request){
     case"d":
      doDisconnect();
      break;
     case"c":
      doCreateAccount();
      break;
     case"l":
      doLogIn();
      break;
     case"sm":
      doSendMessage();
      break; 
    }//end switch
   }//end while
  }catch(IOException ioe){FroggyServer.log("IOException "+ioe);
   return;
  }//end try/catch
 }//end run
 
 public void doDisconnect(){}
 public void doCreateAccount(){}
 public void doLogIn(){}
 public void doSendMessage(){}
}//end ClientThread