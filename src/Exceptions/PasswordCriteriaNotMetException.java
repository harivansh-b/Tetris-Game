package Exceptions;

public class PasswordCriteriaNotMetException extends Exception {
   String message;
   public PasswordCriteriaNotMetException(String message) {
      this.message = message;
   }
   public String getMessage(){
      return message;
   }
   public String toString(){
      return getClass().getName() + message;
   }
}