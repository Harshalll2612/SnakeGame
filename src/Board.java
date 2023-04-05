import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int max_dots = 1600,dot_size = 10;
    int height = 400, width = 400;
    int x[] = new int[max_dots];
    int y[] = new int[max_dots];
    int snake_size, apple_x,apple_y;
    Image head,body,apple;
    Timer timer;
    int delay = 200;
    boolean inGame = true;

    boolean left = true, right = false, up = false, down = false;
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.BLACK);
        initialize();
        LoadImages();
    }

    // Initializing the starting positions of snake and apple
    public void initialize(){
        snake_size = 3;
        x[0] = 50;
        y[0] = 50;
        for(int i=0;i<snake_size;i++){
            x[i] = x[0] + dot_size*i;
            y[i] = y[0];
        }
        locateApple();
        timer = new Timer(delay,this);
        timer.start();
    }
    // loading the images in objects created of Image class
    public void LoadImages(){
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }
    // draw images on particular locations
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawImages(g);
    }
    public void drawImages(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<snake_size;i++){
                if(i==0) g.drawImage(head,x[0],y[0],this);
                else g.drawImage(body,x[i],y[i],this);
            }
        }else{
            gameOver(g);
            timer.stop();
        }
    }
    // Randomizing the apple locations
    public void locateApple(){
        apple_x = ((int)(Math.random()*39))*dot_size;
        apple_y = ((int)(Math.random()*39))*dot_size;
    }
    // check if snake ate apple
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            snake_size++;
            locateApple();
        }
    }
    // check collision
    public void collisionCheck(){
        for(int i=4;i<snake_size;i++){
            if(x[0]==x[i] && y[0]==y[i]){
                inGame = false;
            }
        }
        if(x[0]<0 || x[0]>=width || y[0]<0 || y[0]>=height){
            inGame = false;
        }
    }

    // Game over
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (snake_size - 3)*100;
        String score_msg = "Score:" + Integer.toString(score);
        Font big = new Font("Helvita",Font.BOLD,50);
        FontMetrics fontMetrics_b = getFontMetrics(big);
        Font small = new Font("Helvita",Font.BOLD,25);
        FontMetrics fontMetrics_s = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(big);
        g.drawString(msg,(width - fontMetrics_b.stringWidth(msg))/2,height/4);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(score_msg,(width - fontMetrics_s.stringWidth(score_msg))/2,(3*height)/4);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            collisionCheck();
            checkApple();
            move();
        }
        repaint();
    }
    //snake movement
    public void move(){
        for(int i= snake_size-1;i>=1;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0]-= dot_size;
        }else if(right){
            x[0]+= dot_size;
        }else if(up){
            y[0]-= dot_size;
        }else{
            y[0]+= dot_size;
        }
    }

    // Implementing controls
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                right = false;
            }
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
        }
    }
}
