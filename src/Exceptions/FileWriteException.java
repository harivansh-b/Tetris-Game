package Exceptions;

public class FileWriteException extends Exception {
   String message;
   public FileWriteException(String message, Throwable cause) {
      super(cause);
      this.message = message;
      
   }
   public String getMessage(){
      return message;
   }
   public String toString(){
      return getClass().getName() + message;
   }
}