package Exceptions;

public class UserAlreadyExistsException extends Exception {
   String message;
   public UserAlreadyExistsException(String message) {
      this.message = message;
   }
   public String getMessage(){
      return message;
   }
   public String toString(){
      return getClass().getName() + message;
   }
}