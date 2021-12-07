import java.util.Date;
import java.io.Serializable;

public class Account implements Serializable {
   private String name;
   private String password;
   private String color;
   private static final long serialVersionUID = 01L;
 
   //standard constructor
   public Account(String n, String p, String c) {
      name=n;
      password=p;
      color=c;
   }//end date;
 
   //get and set methods
   public void setName(String n) { name=n; }
   public void setPassword(String p) { password=p; }
   public void setColor(String c) { color=c; }
   public String getName() { return name; }
   public String getPassword() { return password; }
   public String getColor() { return color; }
}//end class