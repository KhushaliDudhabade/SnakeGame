import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT = 400;
    int B_WIDTH = 400;
    int MAX_DOTS = 1600;
    int DOT_SIZE = 10;
    int DOTS;
    int x[] = new int[MAX_DOTS];
    int y[] = new int[MAX_DOTS];
    int apple_x;
    int apple_y;

    //images
    Image body, head, apple;
    Timer timer;
    int DELAY=200;

    boolean leftDirect=true;
    boolean rightDirect= false;
    boolean upDirect =false;
    boolean downDirect =false;
    boolean inGame=true;



    Board() {
        TAdapter tAdapter= new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImages();

    }

    public void initGame() {
        DOTS = 3;
        //initialize snake position
        x[0] = 250;
        y[0] = 250;
        for (int i = 1; i < DOTS; i++) {
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }
        //initialize apple position
        locateApple();
        timer =new Timer(DELAY, this);
        timer.start();

    }

    //load images
    public void loadImages() {
        ImageIcon bodyIcon = new ImageIcon("src/Resources/yellow.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/Resources/red.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/Resources/greenapple11.jpg");
        apple = appleIcon.getImage();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
        else{
            gameOver(g);
            timer.stop();
        }
    }

    public void locateApple(){
        apple_x=((int)(Math.random()*39))*DOT_SIZE;
        apple_y=((int)(Math.random()*39))*DOT_SIZE;
    }

    //check collision with Border and body
    public void checkCollision(){
        //collision with body
        for(int i=1;i<DOTS;i++){
            if(i>4&&x[0]==x[i]&&y[0]==y[i]){
                inGame=false;
            }
        }
        //collision with border
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>=B_WIDTH){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]>=B_HEIGHT){
            inGame=false;
        }
    }

    // Display Game over msg
    public void gameOver(Graphics g){
        String msg ="Game Over";
        int score=(DOTS-3)*100;
        String scoremsg ="Score:"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics= getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        //g.drawString(msg,x:(B_WIDTH-fontMetrics.stringWidth(msg))/2,y:B_HEIGHT/4);
        //g.drawString(scoremsg,x:(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2,y:3*(B_HEIGHT/4));

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
          if(inGame){
              checkApple();
              checkCollision();
              move();
          }
         repaint();
    }
    //make snake move
    public void move(){
        for(int i=DOTS-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];

        }
        if(leftDirect){
            x[0]-=DOT_SIZE;
        }
        if(rightDirect){
            x[0]+=DOT_SIZE;
        }
        if(upDirect){
            y[0]-=DOT_SIZE;
        }
        if(downDirect){
            y[0]+=DOT_SIZE;
        }
    }
    //make snake eat food
    public void checkApple(){
        if(apple_x==x[0]&&apple_y==y[0]){
            DOTS++;
            locateApple();
        }
    }
    //Implement Controls
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT &&!rightDirect){
                leftDirect= true;
                upDirect =false;
                downDirect=false;
            }
            if(key==KeyEvent.VK_RIGHT &&!leftDirect){
                rightDirect= true;
                upDirect =false;
                downDirect=false;
            }
            if(key==KeyEvent.VK_UP &&!downDirect){
                leftDirect= false;
                upDirect =true;
                rightDirect=false;
            }
            if(key==KeyEvent.VK_DOWN &&!upDirect){
                leftDirect= false;
                rightDirect =false;
                downDirect=true;
            }

        }
    }
}
