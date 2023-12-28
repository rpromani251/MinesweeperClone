import java.awt.Dimension;

import javax.swing.JButton;

public class Cell extends JButton {
    private boolean isRevealed; 
    private boolean isFlagged;
    private boolean hasMine;
    private int neighboringMines;
    private int row;
    private int col;
    private boolean visited;
    private boolean isExploded;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        isRevealed = false;
        isFlagged = false;
        hasMine = false;
        visited = false;
        neighboringMines = 0;
        isExploded = false;

    }

    public void toggleExploded() {
        isExploded = !isExploded;
    }

    public void setIsRevealed(boolean bool) {
        isRevealed = bool;
    }

    public void setIsFlagged(boolean bool) {
        isFlagged = bool;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public void setHasMine(boolean bool) {
        hasMine = bool;
    }

    public void setNeighboringMines(int count) {
        neighboringMines = count;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isRevealed() {
        return isRevealed;
    }
    
    public boolean isFlagged() {
        return isFlagged;
    }
    
    public boolean hasMine() {
        return hasMine;
    }
        
    public int getNeighboringMines() {
        return neighboringMines;
    }

    // Getter methods for row and col
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isExploded() {
        return isExploded;
    }
        

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(16, 16);
    }

    @Override
    public String toString() {
        if (hasMine) {
            return "1";
        } else {
            return "0";
        }
    }

    public void resetCell() {
        setIsRevealed(false);
        isFlagged = false;
        setHasMine(false);
        setVisited(false);
        setNeighboringMines(0);
    }


    

}