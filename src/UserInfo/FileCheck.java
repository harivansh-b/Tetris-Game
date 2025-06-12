package UserInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Exceptions.*;

public class FileCheck {
    
   public static int isUser(String name, String pass) {
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner scan = new Scanner(f);
            
         while (scan.hasNextLine()) {
            String[] tempstrArr = (scan.nextLine()).split(" ");
               if (tempstrArr[0].equalsIgnoreCase(name)) {
                  if (tempstrArr[1].equals(pass)) {
                     return 2;  // User found and password matches
                  }
                    return 1;  // User found, password doesn't match
               }
            }
            scan.close();
            return 0;  // User not found
         } 
         catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
         }
      return -1;
   }

   public static boolean isPresent(String username) throws FileReadException {
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner read = new Scanner(f);
         while (read.hasNextLine()) {
            String temp = read.nextLine().substring(0, username.length());
               if (temp.equalsIgnoreCase(username))
                  return true;
         }
         read.close();
         return false;
        } 
        catch (IOException e) {
         throw new FileReadException("Error reading file to check if user exists", e);
      }
   }

   public static boolean passCriteria(String pass) {
      boolean hasUpper = false, hasLower = false, hasSpecial = false, hasDigit = false;
      if (pass.length() >= 8 && pass.length() <= 16) {
         for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) {
               hasUpper = true;
            } 
            else if (Character.isLowerCase(c)) {
               hasLower = true;
            } 
            else if (Character.isDigit(c)) {
               hasDigit = true;
            } 
            else {
               int ascii = (int) c;
               if (33 <= ascii && ascii <= 64) {
                  hasSpecial = true;
               }
               }
            }
      }
      return hasLower && hasSpecial && hasUpper && hasDigit;
   }

   public static String signUp(String username, String pass) {
      try {
         if (isPresent(username)) {
            throw new UserAlreadyExistsException("Username already exists");
         }
         if (!passCriteria(pass)) {
            throw new PasswordCriteriaNotMetException("Password criteria not met");
         }
           
         File f = new File("src/UserInfo/userdata.txt");
         FileWriter fw = new FileWriter(f, true);
         fw.write(username + " " + pass + " -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 \n");
         fw.close();
         return "Sign up successful";
      } 
      catch (FileReadException | UserAlreadyExistsException | PasswordCriteriaNotMetException e) {
         System.err.println(e.getMessage());
         return e.getMessage();
      } 
      catch (IOException e) {
         System.err.println("Error writing to user file: " + e.getMessage());
         return "Error during sign-up";
      }
   }

   public static void addScore(String username, int value, int difficulty) {
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner read = new Scanner(f);
         ArrayList<String> list = new ArrayList<>();
    
         while (read.hasNextLine()) {
            String line = read.nextLine();
            if (line.startsWith(username + " ")) {
               String[] lineArr = line.split(" ");
               int[] scores = new int[13];
               for (int i = 2; i < 15; i++) {
                     scores[i - 2] = Integer.parseInt(lineArr[i]);
               }
               switch (difficulty) {
                  case 1 -> {
                     if (scores[0] < value) scores[0] = value;
                  }
                  case 2 -> {
                     if (scores[1] < value) scores[1] = value;
                  }
                  case 3 -> {
                     if (scores[2] < value) scores[2] = value;
                  }
               }
               for (int i = 12; i > 4; i -= 2) {
                  scores[i] = scores[i - 2];
                  scores[i - 1] = scores[i - 3];
               }
               scores[4] = value;
               scores[3] = difficulty; 
               for (int i = 2; i < 15; i++) {
                  lineArr[i] = Integer.toString(scores[i - 2]);
               }
                  line = String.join(" ", lineArr);
               }
               list.add(line);
            }
            FileWriter fw = new FileWriter(f);
            for (String s : list) {
                fw.write(s + "\n");
            }
            fw.close();
      } 
      catch (IOException e) {
          System.err.println("Error updating scores: " + e.getMessage());
      }
   }

   public static void easyBoard(String[] names, int[] scores) {
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner read = new Scanner(f);
         while (read.hasNextLine()) {
            String[] arr = read.nextLine().split(" ");
            String name = arr[0];
            int highscore = Integer.parseInt(arr[2]);
            for (int i = 0; i < 5; i++) {
               if (scores[i] < highscore) {
                  for (int j = 4; j > i; j--) {
                     scores[j] = scores[j - 1];
                     names[j] = names[j - 1];
                  }
                  scores[i] = highscore;
                  names[i] = name;
                  break;
                  }
               }
           }
            read.close();
         }
         catch (IOException e) {
            System.err.println("Error reading easy board data: " + e.getMessage());
      }
   }

   public static void mediumBoard(String[] names, int[] scores) {
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner read = new Scanner(f);
  
         while (read.hasNextLine()) {
            String[] arr = read.nextLine().split(" ");
            String name = arr[0];
            int highscore = Integer.parseInt(arr[3]);  // assuming arr[3] contains the medium level high score
  
            for (int i = 0; i < 5; i++) {
               if (scores[i] < highscore) {
                  for (int j = 4; j > i; j--) {
                     scores[j] = scores[j - 1];
                     names[j] = names[j - 1];
                  }
                  scores[i] = highscore;
                  names[i] = name;
                  break;
               }
            }
         }
         read.close();
   } 
   catch (IOException e) {
      System.err.println("Error reading medium board data: " + e.getMessage());
   } 
   catch (NumberFormatException e) {
      System.err.println("Error parsing high score for medium board: " + e.getMessage());
   }
  }
  
   public static void hardBoard(String[] names, int[] scores) {
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner read = new Scanner(f);
  
         while (read.hasNextLine()) {
            String[] arr = read.nextLine().split(" ");
            String name = arr[0];
            int highscore = Integer.parseInt(arr[4]);  // assuming arr[4] contains the hard level high score
  
            for (int i = 0; i < 5; i++) {
               if (scores[i] < highscore) {
                  for (int j = 4; j > i; j--) {
                     scores[j] = scores[j - 1];
                     names[j] = names[j - 1];
                  }
                  scores[i] = highscore;
                  names[i] = name;
                  break;
               }
            }
         }
         read.close();
      } 
      catch (IOException e) {
         System.err.println("Error reading hard board data: " + e.getMessage());
      } 
      catch (NumberFormatException e) {
         System.err.println("Error parsing high score for hard board: " + e.getMessage());
      }
   }
  

   public static HashMap<Integer, Integer> highScore(String username) {
      HashMap<Integer, Integer> h = new HashMap<>();
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner scan = new Scanner(f);
         while (scan.hasNextLine()) {
            String[] tempstrArr = (scan.nextLine()).split(" ");
            if (tempstrArr[0].equalsIgnoreCase(username)) {
               for (int i = 2; i < 5; i += 2) {
                  h.put(i - 1, Integer.parseInt(tempstrArr[i]));
                  }
               }
         }
         scan.close();   
      }
      catch (IOException e) {
         System.err.println("Error reading high scores: " + e.getMessage());
      }
      return h;
   }
    
   public static ArrayList<String> gameHistory(String username) {
      ArrayList<String> ret = new ArrayList<>();
      try {
         File f = new File("src/UserInfo/userdata.txt");
         Scanner scan = new Scanner(f);
         while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.startsWith(username)) {
               String[] arr = line.split(" ");
               for (int i = 5; i < arr.length; i += 2) {
                  String score = arr[i + 1];
                  int difficulty = Integer.parseInt(arr[i]);
                  String difficultyStr = switch (difficulty) {
                     case 1 -> "Easy";
                     case 2 -> "Medium";
                     case 3 -> "Hard";
                     default -> "Unknown";
                  };
                  ret.add(difficultyStr + " - " + score);
               }
            }
         }
         scan.close();
      } 
      catch (IOException e) {
         System.err.println("Error retrieving game history: " + e.getMessage());
      }
      return ret;
   }
}

