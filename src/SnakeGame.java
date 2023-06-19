// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import javax.swing.*;
public class SnakeGame extends JFrame {
    Board board;          //https://drive.google.com/drive/folders/1Trh8cnly_tpDuRsKwlz3zEcqPEwd2GPQ?usp=share_link
    SnakeGame(){
        board = new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();
    }
}