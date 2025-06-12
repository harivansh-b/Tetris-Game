package Tetris;

import Exceptions.GameOverException;

public class GameThread extends Thread {
   private GameArea gameArea;
   private int score;
   private int level = 1;
   private int scorePerLevel;
   private int speed;
   private int speedPerLevel;
   private boolean gameOver = false;  // Track if the game is over
   private boolean isPaused = false;

   public GameThread(GameArea gameArea) {
      this.gameArea = gameArea;
      gameArea.updateScore(0);
      gameArea.updateLevel(1);
   }

   public void setSpeed(int speed) {
      this.speed = speed;
   }

   public void setScorePerLevel(int scorePerLevel) {
      this.scorePerLevel = scorePerLevel;
   }

   public void setSpeedPerLevel(int speedPerLevel) {
      this.speedPerLevel = speedPerLevel;
   }

   public int getScore(){
      return score;
   }

   public boolean getPaused() {
      return isPaused;
   }

   public synchronized void pauseGame() {
      isPaused = true; }

   public synchronized void resumeGame() {
      isPaused = false; 
      notify(); // Notify the thread to resume if it was waiting
   }

   @Override
   public void run() {
      while (!gameOver) {
         boolean blockStopped = false;

         // Check if the game is paused and wait until it's resumed
         synchronized (this) {
               while (isPaused) {
                  try {
                     wait(); // Wait until resumeGame() is called
                  } catch (InterruptedException e) {
                     return; // Exit the thread if interrupted
                  }
               }
         }

         // Spawn a new block at the start of each cycle
         gameArea.spawnBlock();
         blockStopped = false;

         // Main loop to handle block movement while game is running and not paused
         while (!blockStopped && !gameOver) {
               synchronized (this) {
                  if (isPaused) {
                     // Pause the loop and wait until resumeGame() is called
                     try {
                           wait();
                     } catch (InterruptedException e) {
                           return; // Exit the thread if interrupted
                     }
                     continue; // Continue the loop after resuming
                  }
               }

               // Move the block down and check if it has stopped moving
               if (!gameArea.moveBlockDown()) {
                  blockStopped = true;
               } else {
                  try {
                     Thread.sleep(speed);
                  } catch (InterruptedException e) {
                     return; // Exit thread on interruption
                  }
               }
         }

         if (blockStopped) {
               gameArea.moveBlockToBackground();
               score += gameArea.clearLines() * 100;
               gameArea.updateScore(score);

               int lvl = score / scorePerLevel + 1;
               if (lvl > level) {
                  level = lvl;
                  gameArea.updateLevel(level);
                  speed -= speedPerLevel;
               }
         }

         try{
            // Check if the game is over
            if (gameArea.isBlockOutOfBounds()) {
                  throw new GameOverException("Game over! Try new Game!!");
                  
            }
         }
         catch(GameOverException g){
            gameOver = true;
            System.out.println(g);
         }
      }
   }


   public boolean isGameOver() {
      return gameOver;
   }
}
