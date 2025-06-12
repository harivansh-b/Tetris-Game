package TetrisBlocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Color;

interface TetriminoActions {
    void moveDown();
    void moveLeft();
    void moveRight();
    int[][] getShape();
    int getX();
    int getY();
    Color getColor();
    int getWidth();
    int getHeight();
    int getBottomEdge();
    int getLeftEdge();
    int getRightEdge();
}

public class Tetrimonoes implements TetriminoActions{
    private int[][] shape;
    private int[][][] shapes; // Store all rotations
    private Color color;
    private Color lastColor = null;
    private int x, y;
    private int currentRotation = 0;
    private List<Color> availableColors = Arrays.asList(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.ORANGE,
        Color.MAGENTA,
        Color.CYAN,
        Color.PINK,
        Color.LIGHT_GRAY,
        Color.DARK_GRAY,
        new Color(255, 165, 0),   // Orange
        new Color(128, 0, 128),   // Purple
        new Color(255, 105, 180), // Hot Pink
        new Color(0, 128, 128),   // Teal
        new Color(173, 216, 230), // Light Blue
        new Color(255, 215, 0),   // Gold
        new Color(50, 205, 50),   // Lime Green
        new Color(255, 99, 71),   // Tomato
        new Color(138, 43, 226),  // Blue Violet
        new Color(240, 230, 140)  // Khaki
    );

    
  

    public Tetrimonoes(int[][] shape) {
        this.shape = shape;
        initShapes(); // Initialize all possible rotations
    }

    // Initialize all rotations of the shape
    public void initShapes() {
        shapes = new int[4][][]; // Four rotations: 0°, 90°, 180°, 270°
        shapes[0] = shape; // Original shape

        // Generate rotated shapes
        for (int i = 1; i < 4; i++) {
            shapes[i] = rotate(shapes[i - 1]); // Rotate the previous shape by 90 degrees
        }
    }

    // Generate a rotated version of the given shape by 90 degrees clockwise
    private int[][] rotate(int[][] shape) {
        int height = shape.length;
        int width = shape[0].length;
        int[][] rotated = new int[width][height];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                rotated[col][height - 1 - row] = shape[row][col];
            }
        }
        return rotated;
    }

    // Randomly spawn the block with a random rotation
    public void spawn(int gridWidth) {
        Random r = new Random();
        y = -getHeight(); // Start above the grid
        
        // Set a random rotation (0° to 270°)
        int randomRotation = r.nextInt(4); // Random value between 0 and 3
        setShape(shapes[randomRotation]);
        setCurrentRotation(randomRotation);
        
        x = r.nextInt(gridWidth - getWidth()); // Random x position within the grid
    
        // Select a new color that is different from the last color
        selectNewColor();
    }
    
    public void selectNewColor() {
        Random r = new Random();
        Collections.shuffle(availableColors, r);  // Randomly shuffle colors
        Color newColor = availableColors.get(0);  // Pick the first color
        
        // If it's the same as the last color, pick the next one
        if (newColor.equals(lastColor)) {
            newColor = availableColors.get(4);
        }

        color = newColor;
        lastColor = color;
    }
    
    public int getCurrentRotation() {
      return currentRotation;
    }
   
   public void setCurrentRotation(int rotation) {
        currentRotation = rotation;
   }
   public int[][][] getShapes() {
      return shapes;
  }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
      this.x = x;
    }

    public void setY(int y){
      this.y = y;
    }
    public void moveDown() {
        y++;
    }

    public void moveRight() {
        x++;
    }

    public void moveLeft() {
        x--;
    }

    public int getBottomEdge() {
        return y + getHeight();
    }

    public int getLeftEdge() {
        return x;
    }

    public int getRightEdge() {
        return x + getWidth();
    }
}
