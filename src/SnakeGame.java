import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        setLocation(600,200);
        pack();
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();
    }
}