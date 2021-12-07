import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.control.TextArea;
import javafx.application.Platform;


public class FroggyServer{
 //Objects+Variables
 private ObjectInputStream ois=null;
 private ObjectOutputStream oos=null;
 private TextArea taLog;
 private TreeMap<String, Account> map = new TreeMap<String, Account>();
 private Account tempAccount;
 private ServerThread st = null;
 
 //accessor for the ServerThread
 public ServerThread getServerThread(){return st;}

 //final
 private final File ACCOUNT_FILE=new File("accounts.dat");

 //Constructor. 
 public FroggyServer(TextArea ta){
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
  st=new ServerThread(this);
  st.start();
 }//end constructor
 
 //logs to the textarea in a thread-safe way
 public void log(String s){
  Platform.runLater(new Runnable(){
  public void run(){
   taLog.appendText(s);
  }
  });
 }///end log
 
 //saves the accounts to a binary file
 public void save(){
  if(map.size()>0){
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
  }//end if
 }//end save
 
 //logs in a user
 public String doLogIn(String userName, String password){
  //synchronized on the hashmap
  synchronized(map){
   //if the username is in the map
   if(map.containsKey(userName)){
    //if the passwords match
    if(password.equals(map.get(userName).getPassword()))
     return "li";
    else
     return "ip";
   }//end if
   else
    return "iu";
  }//end synchronized
 }//end doLogIn
 
 //method to create an account
 public String doCreateAccount(Account a){
  //synchronized on the map
  synchronized(map){
   //if the username already exists
   if(map.containsKey(a.getName()))
    return "ut";
   //otherwise, creates the account
   else{
    map.put(a.getName(), a);
    return "ac";
   }
  }//end synchronized
 }//end doCreateAccount
}//end FroggyServer

//Thread to do server things
class ServerThread extends Thread{
 private ServerSocket sSocket=null;
 private ArrayList<ClientThread> ctList=new ArrayList<ClientThread>();
 private ClientThread ct;
 private Socket cSocket=null;
 private FroggyServer base;
 private boolean keepGoing=true;
 
 //final
 private static final int PORT=5002;

 //basic Constructor
 public ServerThread(FroggyServer fs){
  base=fs;
 }//end constructor
 
 //run method
 public void run(){
  //sets up the serversocket
  try{
   sSocket=new ServerSocket(PORT);
  }catch(IOException ioe){base.log("IOException "+ioe);
   return;
  }//end try/catch
  
  //ongoing
  while(true){
   try{
    //The client's socket from the server's socket
    cSocket=sSocket.accept();
   }catch(IOException ioe){base.log("IOException "+ioe);
    return;
   }//end try/catch
   
   //Creates a thread for the client, starts it, and stores it in an ArrayList
   ct=new ClientThread(cSocket, base, this);
   ct.start();
   synchronized(ctList){
    ctList.add(ct);
   }//end synchronized
  }//end while
 }//end run
 
 //disconnects all clients
 public void doServerDisconnect(){
  synchronized(ctList){ 
   ctList.forEach((c) -> c.doServerDisconnect());
  }//end synchronized
 }//end doServerDisconnect()
 
 //sends a message to all clients
 public void sendMessage(Message m){
  synchronized(ctList){
   ctList.forEach((c) -> c.doSendMessage(m));
  }//end synchronized
 }//ed sendMessage
}//end ServerThread

class ClientThread extends Thread{
 private Socket cSocket=null;
 private String request="";
 private String userName="";
 private String password="";
 private ObjectInputStream in=null;
 private ObjectOutputStream out =null;
 private boolean keepGoing=true;
 private boolean loggedIn=false;
 private FroggyServer base;
 private Account account;
 private ServerThread server;
 private Message mes;
 
 //constructor
 public ClientThread(Socket cs, FroggyServer fs, ServerThread st){
  cSocket=cs;
  base=fs;
  server=st;
 }//end Constructor
 
 public void run(){
  //Informs the user that there's a connection
  base.log("User Connected");
  try{
   //input and output streams
   in=new ObjectInputStream(cSocket.getInputStream());
   out=new ObjectOutputStream(cSocket.getOutputStream());
   
   while(keepGoing){
    //waits for inputs
    request=in.readUTF();
    
    switch(request){
     case"d":
      doClientDisconnect();
      break;
     case"c":
      doCreateAccount();
      break;
     case"l":
      doLogIn();
      break;
     case"sm":
      try{
       mes=(Message)in.readObject();
      }catch(ClassNotFoundException cnfe){return;}
      server.sendMessage(mes);
      break; 
    }//end switch
   }//end while
  }catch(IOException ioe){base.log("IOException "+ioe);
   return;
  }//end try/catch
 }//end run
 
 //method to allow user to disconnect
 public void doClientDisconnect(){
  try{
   in.close();
   out.close();
   cSocket.close();
   keepGoing=false;
  }catch(IOException ioe){base.log("IOException "+ioe);
   return;
  }//end try/catch
 }//end doDisconnect 
 
 public void doServerDisconnect(){
  try{
   out.writeUTF("di");
   out.flush();
   out.close();
   in.close();
   cSocket.close();
   keepGoing=false;
  }catch(IOException ioe){base.log("IOException "+ioe);
   return;
  }//end try/catch
 }//end doServerDisconnect
 
 //method to create an account
 public void doCreateAccount(){
  try{
   try{
    //gets the account, and checks if it's valid
    account=(Account)in.readObject();
    request=base.doCreateAccount(account);
   }catch(ClassNotFoundException cnfe){base.log("ClassNotFoundException "+cnfe);
    return;
   }//end try/catch
   
   //informs the client of the result
   switch(request){
    case "ac":
     loggedIn=true;
     out.writeUTF("ac");
     out.flush();
     break;
    case "ut":
     out.writeUTF("ut");
     out.flush();
     break;
   }//end switch
  }catch(IOException ioe){base.log("IOException "+ioe);
   return;
  }//end try/catch
 }//end doCreateAccount
 
 //method to log in a user
 public void doLogIn(){
  try{
   //reads the username and password
   userName=in.readUTF();
   password=in.readUTF();
 
   //Calls a method that's in a better place
   request=base.doLogIn(userName, password);
   
   //synchronized on the outputstream
   synchronized(out){
    
    //switch on request
    switch(request){
     //informs client that they're logged in
     case "li":
      loggedIn=true;
      out.writeUTF("li");
      out.flush();
      break;
     //informs client that their password is wrong
     case "ip":
      out.writeUTF("ip");
      out.flush();
      break;
     //informs user that their username is wrong
     case "iu":
      out.writeUTF("iu");
      out.flush();
      break;
    }//end switch
   }//end synchronized
  }catch(IOException ioe){base.log("IOException "+ioe);
   return;
  }//end try/catch
 }//end doLogIn
 
 //method to send a message
 public void doSendMessage(Message m){
  if(loggedIn){ 
   try{
    out.writeUTF("nm");
    out.writeObject(m);
    out.flush();
   }catch(IOException ioe){base.log("IOException "+ioe);
    return;
   }//end try/catch
  }//end if
 }//end doSendMesssage
}//end ClientThread
