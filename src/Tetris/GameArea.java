package Tetris;

import Audios.AudioPlayer;
import Game.App;
import TetrisBlocks.*;
import UserInfo.FileCheck;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.io.*;

interface GameInterface{
    void startGame(GameArea gameArea);
    void stopGame();
    void play();
    void pause();
    void refresh(GameArea gameArea);

}

interface GameControls {
    void spawnBlock();
    void dropBlock();
    void moveBlockLeft();
    void moveBlockRight();
    void rotateBlock();
    void setGameOver();
    void initializeBackground();
}

public class GameArea extends JPanel implements GameInterface, GameControls{
    // Define the number of rows and columns in the Tetris grid
    private int gridRows = 18;
    private int gridCols = 10;
    private int cellSize; // The size of each cell in the grid

    private Color[][] background; 
    // A 2D array to store the color of each cell in the grid
    private Tetrimonoes block; // The current block being controlled by the player
    private Tetrimonoes[] blocks; // The 7 blocks
    private Tetrimonoes nextBlock = null; // To show the next spawning block
    
    public JLabel scoreLabel = new JLabel("<html><font color='white'><b>SCORE: 0</b></font></html>");
    public JLabel levelLabel = new JLabel("<html><font color='white'><b>LEVEL : 1</b></font></html>");

    public NextBlockPanel nextBlockPanel= new NextBlockPanel();

    private Random r = new Random();

    private GameThread gameThread;

    private boolean isGameOver = false; // Track game-over state
    public boolean started = false;

    private AudioPlayer audioPlayer =  new AudioPlayer();
    private Font Copperplatefont;

    // Constructor for the game area panel
    public GameArea() {
        Copperplatefont = new Font("Copperplate",Font.PLAIN,15);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 15f );
            
            // Optional: Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Copperplatefont);
                        
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        // Set the size and location of the game area on the window
        this.setBounds(50, 150, 250, 450);
        this.setOpaque(false); // Set panel to be transparent
        this.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 5)); // Add a border to the panel
        
        // Initialize the background grid with white cells
        background = new Color[gridRows][gridCols];
        initializeBackground(); // Set all background cells to white
        
        // Initialize keyboard controls for the game
        initControls();

        scoreLabel.setFont(Copperplatefont);
        levelLabel.setFont(Copperplatefont);

        blocks = new Tetrimonoes[]{
            new IShape(),
            new OShape(),
            new JShape(),
            new LShape(),
            new SShape(),
            new TShape(),
            new ZShape()
        };

    }


    public void setGameOver() {
        isGameOver = true;
        
        if(App.getGuestorlogin()==1){
            int score=gameThread.getScore();
            FileCheck.addScore(App.getUsername(),score,App.getDifficulty());
        }
        repaint(); 
        audioPlayer.playGameOver();
    }
    
    // Method to initialize keyboard controls for the game
    private void initControls() {
        InputMap im = this.getInputMap(WHEN_IN_FOCUSED_WINDOW); // Get the panel's input map
        ActionMap am = this.getActionMap(); // Get the panel's action map
    
        // Map arrow keys to actions (move block right, left, down, and rotate)
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
    
        // Define actions for each key press
        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameThread.getPaused())
                    moveBlockRight(); // Move the block right
            }
        });
    
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameThread.getPaused())
                    moveBlockLeft(); // Move the block left
            }
        });
    
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameThread.getPaused())
                    dropBlock(); // Drop the block down quickly
            }
        });
    
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameThread.getPaused())
                    rotateBlock(); // Rotate the block
            }
        });
    }
    
    // Method to initialize the background grid to white (empty cells)
    public void initializeBackground() {
        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridCols; c++) {
                background[r][c] = Color.WHITE; // Set each cell in the grid to white
            }
        }
    }

    public void NextBlock() {
        // Check if nextBlock is null, and if so, initialize it
        do {
            nextBlock = blocks[r.nextInt(blocks.length)];
        } while (block != null && nextBlock.getClass().equals(block.getClass()));
        // Update the next block panel with the second next block
        nextBlock.spawn(gridCols);
        nextBlockPanel.setBlock(nextBlock);
    }
    
    // Method to spawn a new block on the grid
    public void spawnBlock() {
        if(block == null){
            block = blocks[r.nextInt(blocks.length)];
            block.spawn(gridCols);
        }
        else{
            block = nextBlock;           // Set the current block to the next block
        }
        NextBlock();  // Call NextBlock to update nextBlock 
        
    }
    
    // Method to move the current block to the background once it reaches the bottom
    public void moveBlockToBackground() {
        int[][] shape = block.getShape(); // Get the shape of the current block
        Color color = block.getColor(); // Get the color of the block

        int xPos = block.getX(); // Get the X position of the block
        int yPos = block.getY(); // Get the Y position of the block

        // Copy the block's shape into the background grid
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if(yPos + row < 0){
                    continue;
                }
                if (shape[row][col] == 1) {
                    background[yPos + row][xPos + col] = color; // Fill background with the block's color
                }
            }
        }
    }

    // Method to check if the block can move down further (checks bottom edge)
    private boolean checkBottom() {
              
        // Check if the block is at the bottom of the grid
        if (block != null && block.getBottomEdge() >= gridRows) {

            return false; // Block has hit the bottom boundary
        }
        
        int[][] shape = block.getShape(); // Get the block's shape
        int h = block.getHeight(); // Get the block's height
        int w = block.getWidth(); // Get the block's width

        // Check for collisions with blocks in the background
        for (int c = 0; c < w; c++) {
            for (int r = h - 1; r >= 0; r--) {
                if (shape[r][c] != 0) { // Non-empty cell in the block
                    int x = c + block.getX(); // X position in the grid
                    int y = r + block.getY() + 1; // Check one row below the current block
    
                    // If the block is at the bottom row or there's a collision with the background
                    if (y >= gridRows || (y > 0 && background[y][x] != Color.WHITE)) {
                        return false; // Block cannot move down further
                    }
    
                    break; // Break after the first non-empty cell in this column
                }
            }
        }
    
        return true; // Block can move down
    }
    
    public boolean isBlockOutOfBounds(){
        if(block.getY() <= 0){
            setGameOver();
            moveBlockToBackground();
            block = null;
            return true;
        }
        return false;
    }

    // Move the block down by one unit
    public boolean moveBlockDown() {
        
        if (checkBottom()) { // Check if block can move down
            block.moveDown(); // Move the block down
            repaint(); // Redraw the game area
            return true;
        } 
        else {
            repaint();
            return false;
        }
    }

    // Check if the block can move left (checks left edge)
    private boolean checkLeft() {
        if (block.getLeftEdge() <= 0) {
            return false; // Block has hit the left boundary
        }

        int[][] shape = block.getShape(); // Get the block's shape
        int h = block.getHeight(); // Get the block's height
        int w = block.getWidth(); // Get the block's width

        // Check for collisions with blocks in the background
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (shape[r][c] != 0) {
                    int x = c + block.getX() - 1; // Check one column to the left
                    int y = r + block.getY(); // Y position in the grid
                    if (y < 0) {
                        break;
                    }
                    if (background[y][x] != Color.WHITE) {
                        return false; // Block cannot move left further
                    }
                    break;
                }
            }
        }

        return true; // Block can move left
    }

    // Move the block to the left
    public void moveBlockLeft() {
        if (block == null){
            return ;
        }
        if (checkLeft()) { // Check left boundary
            block.moveLeft(); // Move the block left
            repaint(); // Redraw the game area
        }
    }

    // Check if the block can move right (checks right edge)
    private boolean checkRight() {
        if (block.getRightEdge() >= gridCols) {
            return false; // Block has hit the right boundary
        }
        
        int[][] shape = block.getShape(); // Get the block's shape
        int h = block.getHeight(); // Get the block's height
        int w = block.getWidth(); // Get the block's width

        // Check for collisions with blocks in the background
        for (int r = 0; r < h; r++) {
            for (int c = w - 1; c >= 0; c--) {
                if (shape[r][c] != 0) {
                    int x = c + block.getX() + 1; // Check one column to the right
                    int y = r + block.getY(); // Y position in the grid
                    if (y < 0) {
                        break;
                    }
                    if (background[y][x] != Color.WHITE) {
                        return false; // Block cannot move right further
                    }
                    break;
                }
            }
        }

        return true; // Block can move right
    }

    // Move the block to the right
    public void moveBlockRight() {
        if (block == null){
            return ;
        }
        if (checkRight()) { // Check right boundary
            block.moveRight(); // Move the block right
            repaint(); // Redraw the game area
        }
    }

    // Drop the block quickly to the bottom
    public void dropBlock() {
        if (block == null){
            return ;
        }
        while (checkBottom()) { // Keep moving the block down
            block.moveDown(); // Move the block down
            repaint(); // Redraw the game area
        }
    }

    // Check if the rotated block is within bounds and does not collide with existing blocks
    private boolean canRotate(int[][] rotatedShape) {
        int xPos = block.getX();
        int yPos = block.getY();
    
        // Check if the rotated block fits within the grid and does not overlap with the background
        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] == 1) {
                    // Check for out-of-bounds (left, right, bottom)
                    if (xPos + col < 0 || xPos + col >= gridCols || yPos + row >= gridRows || yPos + row < 0) {
                        return false; // Block would be out of bounds
                    }
                    // Check for collision with background blocks
                    if (background[yPos + row][xPos + col] != Color.WHITE) {
                        return false; // Block would collide with existing blocks
                    }
                }
            }
        }
        return true; // Rotation is valid
    }

    public void rotateBlock() {
        if (block == null) {
            return;
        }

        if (block instanceof IShape) {
            // Check the current width to determine the alignment
            if (block.getWidth() == 1) { // Vertical to Horizontal
                block.setX(Math.max(0, block.getX() - 1)); // Ensure X is within bounds
                block.setY(Math.min(gridRows - block.getHeight(), block.getY() + 2)); // Adjust within bottom boundary
            } else { // Horizontal to Vertical
                block.setX(Math.min(gridCols - block.getWidth(), block.getX() + 1)); // Adjust within right boundary
                block.setY(Math.max(0, block.getY() - 2)); // Ensure Y is not negative
            }
        }
        
        // Get the next rotation index
        int nextRotationIndex = (block.getCurrentRotation() + 1) % 4; // Cycle between 0 to 3
    
        // Get the rotated block from the shapes array
        int[][] rotatedBlock = block.getShapes()[nextRotationIndex];
        
        // Check if the rotated block is within bounds and does not collide
        if (canRotate(rotatedBlock)) {
            block.setShape(rotatedBlock);  // Apply the rotation if valid
            block.setCurrentRotation(nextRotationIndex); // Update the current rotation index
            repaint();
        }
    }
    
    
    public int clearLines() {
        boolean lineFilled;

        int linesCleared = 0;
        
        // Iterate over each row starting from the bottom
        for (int r = gridRows - 1; r >= 0; r--) {
            lineFilled = true;
            
            // Check if the current row is completely filled
            for (int c = 0; c < gridCols; c++) {
                if (background[r][c] == Color.WHITE) {
                    lineFilled = false; // If any cell is empty, the line is not filled
                    break;
                }
            }
            
            // If the line is filled, clear it and move all lines above it down
            if (lineFilled) {
                for (int row = r; row > 0; row--) {
                    for (int col = 0; col < gridCols; col++) {
                        background[row][col] = background[row - 1][col]; // Move the above row down
                    }
                }
                
                // Clear the top row
                for (int col = 0; col < gridCols; col++) {
                    background[0][col] = Color.WHITE;
                }
                
                linesCleared++;
                r++; // Recheck the current row after shifting
                repaint(); // Redraw the game area
            }
        }

        if(linesCleared > 0){
            audioPlayer.playClearLine();
        }
        return linesCleared;
    }
    

    private void drawShape(Graphics g) {
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (block.getShape()[row][col] == 1) {
                    int x = (block.getX() + col) * cellSize;
                    int y = (block.getY() + row) * cellSize;
    
                    g.setColor(block.getColor());
                    g.fillRect(x, y, cellSize, cellSize); // Adjust arc width and height as needed
    
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, cellSize, cellSize); // Adjust arc width and height for border
                }
            }
        }
    }
    
    private void drawBackground(Graphics g) {
        // Draw the entire grid background
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, gridCols * cellSize, gridRows * cellSize, 10, 10); // Rounded corners for the grid
    
        // Draw the blocks that have been placed in the grid
        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridCols; c++) {
                Color color = background[r][c];
                int x = c * cellSize;
                int y = r * cellSize;
    
                // Draw colored cells with a border
                if (color != Color.BLACK) {
                    g.setColor(color);
                    g.fillRect(x, y, cellSize, cellSize); // Adjust arc width and height
    
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, cellSize, cellSize); // Adjust arc width and height for border
                }
            }
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        cellSize = this.getWidth() / gridCols;
        if(block == null){
            drawBackground(g);
            // Draw "Game Over" in red if the game is over
            if (isGameOver) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Set a bold font for "GAME OVER"
                g2d.setFont(new Font("Arial", Font.BOLD, 40)); // Choose a gaming-style font
                String gameOverMessage = "GAME OVER";
            
                // Draw a semi-transparent overlay
                g2d.setColor(new Color(0, 0, 0, 150)); // Black with some transparency
                g2d.fillRect(0, 0, getWidth(), getHeight());
            
                // Draw shadow for "GAME OVER"
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString(gameOverMessage, (getWidth() - g2d.getFontMetrics().stringWidth(gameOverMessage)) / 2 + 3, getHeight() / 2 + 3);
                
                // Draw main text in bright red
                g2d.setColor(Color.RED);
                g2d.drawString(gameOverMessage, (getWidth() - g2d.getFontMetrics().stringWidth(gameOverMessage)) / 2, getHeight() / 2);
            }
            return;
        }

        drawBackground(g);
        drawShape(g); 
        
    }

    public void reset(){
        block = null;
        initializeBackground();
    }
    public void startGame(GameArea gameArea){
        gameThread = new GameThread(gameArea);
        gameArea.setDifficulty();
        spawnBlock();
        gameThread.start();
        initializeBackground();
        
    }

    public void stopGame(){
        gameThread.interrupt();
    }
    public void play(){
        if(gameThread.getPaused())
            gameThread.resumeGame();
    }

    public void pause(){
        if(!gameThread.getPaused())
            gameThread.pauseGame();
    }
    
    public void refresh(GameArea gameArea){
        gameThread.interrupt();
        startGame(gameArea);
    }
    // Method to update the score label
    public void updateScore(int score) {
        scoreLabel.setText("<html><font color='white'><b>SCORE: " + score + "</b></font></html>");
        scoreLabel.setFont(Copperplatefont);
    }

    // Method to update the level label
    public void updateLevel(int level) {
        levelLabel.setText("<html><font color='white'><b>LEVEL: " + level + "</b></font></html>");
        levelLabel.setFont(Copperplatefont);
    }

    public int getPlayerScore(){
        return gameThread.getScore();
    }

    public void setDifficulty() {
        int speed = 0;
        int speedPerLevel = 0;
        int scorePerLevel = 0;
        switch (App.getDifficulty()) {
            case 1:
                speed = 1100;
                speedPerLevel = 50;
                scorePerLevel = 100;
                break;
            case 2:
                speed = 700;
                speedPerLevel = 75;
                scorePerLevel = 200;
                break;
            case 3:
                speed = 350;
                speedPerLevel = 100;
                scorePerLevel = 300;
                break;
            default:
                System.out.println("Invalid difficulty level. Using default settings.");
                break;
        }
    
        gameThread.setSpeed(speed);
        gameThread.setSpeedPerLevel(speedPerLevel);
        gameThread.setScorePerLevel(scorePerLevel);
    }
        
}
