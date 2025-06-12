package Exceptions;

public class GameOverException extends Exception{
   String message;
   public GameOverException(String message){
      this.message=message;
   }
   public String getMessage(){
      return message;
   }
   public String toString(){
      return getClass().getName() + message;
   }
}
