import java.io.Serializable;

public class Message implements Serializable{
 private String name;
 private String contents;
 private String color;
 private static final long serialVersionUID = 01L;

 //Constructor
 public Message(String clr, String n, String cont){
  color=clr;
  name=n;
  contents=cont;
 }//end constructor
 
 //accessor and mutators
 public String getName(){return name;}
 public String getContents(){return contents;}
 public String getColor(){return color;}
 public void setName(String n){name=n;}
 public void setContents(String c){contents=c;}
 public void setColor(String c){color=c;}
 
 //temporary toString method
 public String toString(){
  return(name+": "+contents);
 }
}//end class
