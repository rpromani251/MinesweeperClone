This is my attempt to recreate the classic Windows XP Minesweeper, but only in Java!

---

Note: All Resources were obtained from screencaptures of existing creations of Minesweeper on the Internet.

---

AnimationPanel.java contains the code for animating each element on the panel, as well as the game logic. 
- I recognize that having all this code in one class is bad practice, so in the future I plan to fix it.
- Furthermore, the game does not detect a win condition currently, and poorly handles the flagging function, so that will also need to be fixed in the future.

---

Border.java contains a minimal amount of code, simply establishing the height and width of the border and implementing a draw function 

---

Cell.java contains all the code regarding the information of each cell's:
- Row and column
- Revealed and Flagged Status
- If it has a mine, or has been visited
- Neighboring mines
- And if it has been exploded or not!

This class also contains the necessary setters and getters for each of those instance variables, as well as a resetCell method for end of game usage.

--- 

Main.java contains the code handling:
- Game Loop
- Menu and the different level options (Beginner (9x9), Intermediate (16x16), Expert (30x16))

Currently, the game only supports gameplay for beginner mode (barely), and I will implement the other two modes in the future.
