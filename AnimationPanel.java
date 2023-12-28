import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AnimationPanel extends JPanel implements KeyListener, MouseListener {
    private static Timer timer;
    private JButton restartButton;
    private JLabel flagJLabel;
    private JLabel timerJLabel;

    private Cell[][] gameBoard;
    private Border border;

    private int WIDTH=165;
    private int HEIGHT=200;
    private int gameBoardWidth = 9;
    private int gameBoardHeight = 9;
    private int OFFSET=3;
    private int time = 0;
    private boolean allUnknown = true;
    private boolean hasPlayerWon = false;
    private int mines = 10;
    private int availableFlags = mines;
    private int[][] directions = {
        {-1, -1},  // top-left
        {-1,  0},  // top
        {-1,  1},  // top-right
        { 0, -1},  // left
        { 0,  1},  // right
        { 1, -1},  // bottom-left
        { 1,  0},  // bottom
        { 1,  1}   // bottom-right
    };
    

    private ImageIcon unknownTile = new ImageIcon("Resources//unknownTile.png");
    private ImageIcon knownTile = new ImageIcon("Resources//knownTile.png");
    private ImageIcon flagTile = new ImageIcon("Resources//flagTile.png");
    private ImageIcon bombTile = new ImageIcon("Resources//bombTile.png");
    private ImageIcon explodedBombTile = new ImageIcon("Resources//explodedBombTile.png");
    private ImageIcon restartButtIcon = new ImageIcon("Resources//restartButton.png");
    private ImageIcon restartButtIconExploded = new ImageIcon("Resources//restartButtonExploded.png");
    private ImageIcon restartButtIconOnClick = new ImageIcon("Resources//restartButtonOnClick.png");
    private ImageIcon restartButtIconOnWin = new ImageIcon("Resources//restartButtonOnWin.png");
    private ImageIcon tile1 = new ImageIcon("Resources//tile1.png");
    private ImageIcon tile2 = new ImageIcon("Resources//tile2.png");
    private ImageIcon tile3 = new ImageIcon("Resources//tile3.png");
    private ImageIcon tile4 = new ImageIcon("Resources//tile4.png");
    private ImageIcon tile5 = new ImageIcon("Resources//tile5.png");
    private ImageIcon tile6 = new ImageIcon("Resources//tile6.png");
    private ImageIcon tile7 = new ImageIcon("Resources//tile7.png");
    private ImageIcon tile8 = new ImageIcon("Resources//tile8.png");

    public AnimationPanel() {
        gameBoard = new Cell[gameBoardHeight][gameBoardWidth];
        border = new Border(WIDTH-OFFSET, HEIGHT-OFFSET);
        JPanel controlPanel = new JPanel();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints flagGBC = new GridBagConstraints();
        GridBagConstraints restartGBC = new GridBagConstraints();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // FlagJLabel
        flagJLabel = new JLabel(Integer.toString(availableFlags));
        flagJLabel.setForeground(Color.RED);  // Setting text color as an example
        flagJLabel.setFont(new Font("Arial", Font.BOLD, 12));
        flagJLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 30 ));

        // FlagGBC
        flagGBC.gridx = 0;  // Center it horizontally
        flagGBC.gridy = 0;  // At the top
        flagGBC.anchor = GridBagConstraints.NORTH;  // Anchor it to the top
        flagGBC.insets = new Insets(0, 0, 10, 0); // top, left, bottom, right

        controlPanel.add(flagJLabel);

        // RestartButton
        restartButton = new JButton("Restart Button", restartButtIcon);
        restartButton.setPreferredSize(new Dimension(26, 26));
        restartButton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            restartButton.setIcon(restartButtIconOnClick);
                }
            @Override
            public void mouseReleased(MouseEvent e) {
            restartButton.setIcon(restartButtIcon);
                }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
                timer.stop();
            }
        });
        controlPanel.add(restartButton);

        // Timer JLabel
        timerJLabel = new JLabel(Integer.toString(time));
        timerJLabel.setForeground(Color.RED);  // Setting text color as an example
        timerJLabel.setFont(new Font("Arial", Font.BOLD, 12));
        timerJLabel.setBorder(BorderFactory.createEmptyBorder(0, 35, 0, 0));

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time ++;
                timerJLabel.setText(Integer.toString(time));

                if (time >= 99) {
                    timerJLabel.setBorder(BorderFactory.createEmptyBorder(0, 27, 0, 0));
                }
            }
        });
        controlPanel.add(timerJLabel);


        // Set the position to the top-left corner for the control panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Span the control panel across the entire width of the grid
        gbc.gridwidth = gameBoardWidth;
        // Set the bottom padding (spacing below the control panel)
        gbc.insets = new Insets(0, 0, 0, 0);  // top, left, bottom, right
        // Set the control panel to fill the entire width of its display area
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add the control panel with the constraints
        add(controlPanel, gbc);

        // Reset gridwidth and insets before adding cells
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);


        // Restart GBC
        restartGBC.gridx = gameBoardWidth / 2;    
    
        

        // Default positioning should be to the center.
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < gameBoardHeight; i++ ) {
            for (int j = 0; j < gameBoardWidth; j++) {
                Cell cell = new Cell(i, j);
                gameBoard[i][j] = cell;

                cell.addMouseListener(this);
                cell.addActionListener(e -> cellReveal(cell));
                cell.setPreferredSize(new Dimension(16, 16));
                cell.setIcon(unknownTile);
                cell.setMargin(new Insets(0, 0, 0, 0));
                cell.setBorder(BorderFactory.createEmptyBorder());

                // Set grid position
                gbc.gridx = j + 0;
                gbc.gridy = i + 1;
                gbc.insets = new Insets(0, 0, 0, 0);
               

                add(cell, gbc); // add with constraints
            }    
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = gameBoardWidth; // span the entire width
        gbc.fill = GridBagConstraints.HORIZONTAL; // make it fill horizontally
        add(controlPanel, gbc);
        }

    private void cellReveal(Cell cell) {
        // Place cells at start of game
        if (allUnknown) {
            placeMines(mines);
            allUnknown = false;
            timer.start();
        }

        // Logging
        for (Cell[] Row : gameBoard) {
            System.out.println(Arrays.toString(Row));
        }
        System.out.println();

        chainReveal(cell.getRow(), cell.getCol());
        
        // Change the texture of the clicked cell
        cell.setIsRevealed(true);

        // Detect losing condition
        if (cell.hasMine()) {
            cell.setIcon(explodedBombTile);
            cell.toggleExploded();
            restartButton.setIcon(restartButtIconExploded);
            endGame(false);
            return;
        } 

        // Detect winning condition
        if (hasPlayerWon) {
            restartButton.setIcon(restartButtIconOnWin);
            endGame(true); 
            
            return;
        }

        
    }

    private boolean hasPlayerWon() {
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                if (!gameBoard[i][j].isRevealed() && !gameBoard[i][j].hasMine()) {
                    return false; // Found an unrevealed cell without a mine
                }
            }
        }
        System.out.println("Win conditions met");
        return true;
    }

    private void endGame(boolean won) {
        timer.stop();

        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                Cell cell = gameBoard[i][j];
                cell.removeMouseListener(this); // Prevent further interaction
                if (cell.hasMine() && !cell.isExploded()) {
                    cell.setIcon(bombTile); // Reveal all mines
                }
            }
        }

        if (won) {
            // Put in winning things
            JOptionPane.showMessageDialog(this, "You won!");
        } else {
            // Play losing sound
            JOptionPane.showMessageDialog(this, "Game over");
        }
    }

    public void placeMines(int totalMines) {
        ArrayList<Cell> cellList = new ArrayList<>();

        // Flatten the 2D array into a list
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                cellList.add(gameBoard[i][j]);
            }
        }

        // Shuffle the list
        Collections.shuffle(cellList);

        // Mark the first totalMines cells as mines
        for (int i = 0; i < totalMines; i++) {
            cellList.get(i).setHasMine(true);
        }

        cellList.clear();
    }

    private void chainReveal(int row, int col) {
        // If out of bounds or cell has been visited, return immediately
        if (!isValidPosition(row, col) || gameBoard[row][col].isVisited()) {
            return;
        }
        
        // Mark cell as visited
        gameBoard[row][col].setVisited(true);

        // Log the mine count
        int mine_count = countNeighboringMines(row, col, gameBoard);
        System.out.println(mine_count);

        // Check if win condition has been met
        hasPlayerWon = hasPlayerWon();

        switch (mine_count) {
            case 0:
                gameBoard[row][col].setIcon(knownTile);

                // Recursively reveal all neighboring cells
                for (int[] direction : directions) {
                    chainReveal(row + direction[0], col + direction[1]);
                }
                break;
            case 1:
                gameBoard[row][col].setIcon(tile1);
                break;
            case 2:
                gameBoard[row][col].setIcon(tile2);
                break;
            case 3:
                gameBoard[row][col].setIcon(tile3);
                break;
            case 4:
                gameBoard[row][col].setIcon(tile4);
                break;
            case 5:
                gameBoard[row][col].setIcon(tile5);
                break;
            case 6:
                gameBoard[row][col].setIcon(tile6);
                break;
            case 7:
                gameBoard[row][col].setIcon(tile7);
                break;
            case 8:
                gameBoard[row][col].setIcon(tile8);
                break;
        };
    }

    public int countNeighboringMines(int row, int col, Cell[][] gameBoard) {
        int count = 0;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
    
            if (isValidPosition(newRow, newCol) && gameBoard[newRow][newCol].hasMine()) {
                count++;
            }
        }
        return count;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < gameBoardHeight && col >= 0 && col < gameBoardWidth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        border.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public static void closeWriter() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    // Check if source is an instance of Cell and if the right mouse button was pressed
        if (e.getSource() instanceof Cell && e.getButton() == MouseEvent.BUTTON3) {
            Cell cell = (Cell) e.getSource();

            if (!cell.isRevealed()) {
                if (cell.isFlagged() == false && availableFlags > 0 ) {
                    availableFlags--;
                    flagJLabel.setText(Integer.toString(availableFlags));
                    cell.setIcon(flagTile);
                    cell.toggleFlag();
                } else if (cell.isFlagged() == true) {
                    availableFlags++;
                    flagJLabel.setText(Integer.toString(availableFlags));
                    cell.setIcon(unknownTile);
                    cell.toggleFlag();
                }
            }
        }
}


    @Override
    public void mouseReleased(MouseEvent e) {
               
    
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    private void restartGame() {
        // Reset the game board
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                // gameBoard[i][j].resetCell(); 
                gameBoard[i][j].setIsRevealed(false);
                gameBoard[i][j].setIsFlagged(false);
                gameBoard[i][j].setHasMine(false);
                gameBoard[i][j].setVisited(false);
                gameBoard[i][j].setNeighboringMines(0);
                gameBoard[i][j].setIcon(unknownTile);
            }
        }
        
        // Reset any game variables, counters, timers, etc.
        allUnknown = true;

        timer.stop();
        timerJLabel.setText("0");
        time = 0;

        availableFlags = mines;
        flagJLabel.setText(Integer.toString(availableFlags));
        // ... any other resets you need to do ...
    
        // Perhaps regenerate mine positions
        placeMines(mines);
        System.out.println(mines);
    
        // If you have any GUI components that show game state, reset them too.
        // e.g., flagCounterLabel.setText(String.valueOf(initialFlags));
    }

    public void setBeginner() {
        WIDTH = 165;
        HEIGHT = 200;
        gameBoardWidth = 9;
        gameBoardHeight = 9;
        mines = 10;

        gameBoard = new Cell[gameBoardHeight][gameBoardWidth];
        border = new Border(WIDTH-OFFSET, HEIGHT-OFFSET);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        restartGame();
    }

    public void setIntermediate() {
        WIDTH = 165*2;
        HEIGHT = 200*2;
        gameBoardWidth = 16;
        gameBoardHeight = 16;
        mines = 40;
        
        gameBoard = new Cell[gameBoardHeight][gameBoardWidth];
        border = new Border(WIDTH-OFFSET, HEIGHT-OFFSET);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        restartGame();
    }

    public void setExpert() {
        WIDTH = 165*4;
        HEIGHT = 200*2;
        gameBoardWidth = 30;
        gameBoardHeight = 16;
        mines = 99;

        gameBoard = new Cell[gameBoardHeight][gameBoardWidth];
        border = new Border(WIDTH-OFFSET, HEIGHT-OFFSET);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        restartGame();
    }
    
    
}
