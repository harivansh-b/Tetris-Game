package Exceptions;

public class FileReadException extends Exception {
   String message;
   public FileReadException(String message, Throwable cause) {
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