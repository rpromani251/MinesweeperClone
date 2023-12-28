import javax.swing.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the JFrame
                JFrame frame = new JFrame("Minesweeper");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                AnimationPanel animationPanel = new AnimationPanel();
                frame.add(animationPanel);

                // Add a window listener to close the FileWriter when the application is closed
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        AnimationPanel.closeWriter();
                        System.exit(0);
                    }
                });

                // Set the icon for the JFrame
                ImageIcon icon = new ImageIcon("Resources//minesweeper16icon.png");
                frame.setIconImage(icon.getImage());

                // Create the menu bar
                JMenuBar menuBar = new JMenuBar();

                // Create a menu
                JMenu gameMenu = new JMenu("Game");
                JMenu helpMenu = new JMenu("Help");

                menuBar.add(gameMenu);
                menuBar.add(helpMenu);

                // Add items to the menu
                JMenuItem newGameItem = new JMenuItem("New Game");
                newGameItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("New game started!");
                    }
                });
                gameMenu.add(newGameItem);

                gameMenu.addSeparator();

                JMenuItem beginnerItem = new JMenuItem("Beginner (9x9)");
                beginnerItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Beginner");
                        animationPanel.setBeginner();
                    }
                });
                gameMenu.add(beginnerItem);

                JMenuItem intermediateItem = new JMenuItem("Intermediate (16x16)");
                intermediateItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Intermediate");
                        animationPanel.setIntermediate();
                    }
                });
                gameMenu.add(intermediateItem);

                JMenuItem expertItem = new JMenuItem("Expert (30x16)");
                expertItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Expert");
                        animationPanel.setExpert();
                    }
                });
                gameMenu.add(expertItem);

                // Set the menu bar for the frame
                frame.setJMenuBar(menuBar);

                // Make frame visible
                frame.pack();
                frame.setVisible(true);
                frame.setResizable(false);

                
            }
        });
    }
    
}
