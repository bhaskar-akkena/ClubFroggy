import java.util.Date;
import java.io.Serializable;

public class Account implements Serializable{
 private String name;
 private String password;
 private Date bannedUntil=null;
 private boolean isBanned=false;
 private String color;
 private int age;
 private static final long serialVersionUID = 01L;
 
 //standard constructor
 public Account(String n, String p, int a, String c){
  name=n;
  password=p;
  color=c;
  age=a;
 }//end date;
 
 //get and set methods
 public void setName(String n){name=n;}
 public void setPassword(String p){password=p;}
 public void setIsBanned(boolean b){isBanned=b;}
 public void setBannedUntil(Date b){bannedUntil=b;}
 public void setColor(String c){color=c;}
 public void setAge(int a){age=a;}
 public String getName(){return name;}
 public String getPassword(){return password;}
 public boolean getIsBanned(){return isBanned;}
 public Date getBannedUntil(){return bannedUntil;}
 public String getColor(){return color;}
 public int getAge(){return age;}
 
 //bans the user for a number of days
 public void ban(int days){
  //sets banned to true and gets current date
  isBanned=true;
  bannedUntil=new Date();
  bannedUntil.setDate((bannedUntil.getDate()+days));
 }//end ban
 
 //Checks whether a user is banned. If not, changes the boolean
 //Returns true if user is banned, false if they aren't
 public String checkBanned(){
  if(!isBanned)
   return "Not Banned";
  Date now=new Date();
  if(bannedUntil.before(now)){
   isBanned=false;
   return "Not Banned";
  }
  else{
   int hoursFormat=bannedUntil.getHours();
   String ampm;
   if(hoursFormat<12)
    ampm="am";
   else
    ampm="pm";
    
   if(hoursFormat==0 || hoursFormat==23)
    hoursFormat=12;
   if(hoursFormat>12)
    hoursFormat-=12;
   
   return ("User is Banned Until "+(bannedUntil.getMonth()+1)+"/"+bannedUntil.getDate()+"/"+bannedUntil.getYear()+"At "+
   hoursFormat+":"+bannedUntil.getMinutes()+ampm);
  }
 }//end checkBanned
}//end class