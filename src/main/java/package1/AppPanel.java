package package1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

public class AppPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {


    private Font titleFont;
    private Font buttonFont;
    private Font smallButtonFont;

    public static final int LOGIN_STATE = 0;
    public static final int HOME_STATE = 1;

    //LOGIN_STATE
    private CustomButton startButton;

    //HOME_STATE
    private CustomButton returnToLauncher;


    public static int currentState;

    AppPanel(){
        currentState = 0;
        titleFont = new Font("Impact", Font.PLAIN, 78);
        buttonFont = new Font("Impact", Font.PLAIN, 48);
        smallButtonFont = new Font("Impact", Font.PLAIN, 25);
        startButton = new CustomButton(Runner.WIDTH*11/16, Runner.HEIGHT*1/6, 300, 100, "Start Helium", Color.white, 37, 70);
        returnToLauncher = new CustomButton(0, 0, Runner.WIDTH/6, Runner.HEIGHT/15, "Return to Launcher", Color.white, 4, 30);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }


    public void paintComponent(Graphics g){

        if (currentState == LOGIN_STATE) {
            drawLoginState(g);
        } else if (currentState == HOME_STATE) {
            drawHomeState(g);
        }

    }



    private void drawLoginState(Graphics g){
        //left side pink

        g.setColor(Color.white);
        g.fillRect(0, 0, Runner.WIDTH, Runner.HEIGHT);

        Graphics2D g2d = (Graphics2D) g.create();
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, (float) 0.9);
        g2d.setComposite(alcom);

        g2d.setColor(Color.PINK);
        g2d.fillRect(0, 0, Runner.WIDTH*10/16, Runner.HEIGHT);




        //text
        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        g.drawString("Helium Restocks", Runner.WIDTH/11, Runner.HEIGHT/5);

        //draw GIF
        //flower -> https://i.giphy.com/media/7OWdOFOo1bYyt8cZUD/source.gif
        try {
            Image icon = new ImageIcon(new URL("https://i.giphy.com/media/7OWdOFOo1bYyt8cZUD/source.gif")).getImage();

            g.drawImage(icon, Runner.WIDTH*1/20, Runner.HEIGHT/5, this);


        }catch(Exception e){
            System.out.println("Could not get gif");
        }

        //city -> https://i.pinimg.com/originals/2a/66/19/2a66194c6a16e0eed2f26f792bff7561.gif
        try {
            Image icon = new ImageIcon(new URL("https://i.pinimg.com/originals/2a/66/19/2a66194c6a16e0eed2f26f792bff7561.gif")).getImage();

            g.drawImage(icon, Runner.WIDTH*10/16, 0, this);


        }catch(Exception e){
            System.out.println("Could not get gif");
        }
        g.setFont(buttonFont);
        startButton.draw(g);

        g2d.dispose();


    }

    private void drawHomeState(Graphics g){

        Graphics2D g2d = (Graphics2D) g.create();
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, (float) 0.98);
        g2d.setComposite(alcom);

        g.setColor(Color.white);
        g.fillRect(0, 0, Runner.WIDTH, Runner.HEIGHT);



        //wave
        //https://1.bp.blogspot.com/-AA5UeZDeCXY/W-7Zr-i3GkI/AAAAAAAAa28/M-2Tm4nzX4Aw9EmzoZ6Hlgk2LWdBe_RGgCLcBGAs/s1600/cats-wallpaper-japan-art.jpg

        try {
            Image icon = new ImageIcon(new URL("https://cdn.dribbble.com/users/722246/screenshots/2105878/attachments/380895/Bays_Nights.jpg")).getImage();

            Image newImage = icon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

            g.drawImage(icon, -400, 0, this);


        }catch(Exception e){
            System.out.println("Could not get gif");
        }





        g.setColor(Color.pink);
        g.fillRect(0,0,Runner.WIDTH/8, Runner.HEIGHT);

        //draw Task bar
        g2d.setColor(Color.darkGray);
        g2d.fillRect(0,0, Runner.WIDTH, Runner.HEIGHT/15+2);

        g.setColor(Color.pink);
        g.fillRect(0,0, Runner.WIDTH, Runner.HEIGHT/15);




        g.setFont(smallButtonFont);
        returnToLauncher.draw(g);



        g2d.dispose();


    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(currentState == LOGIN_STATE) {
            if (startButton.contains(e.getX(), e.getY())) {
                System.out.println("Start button was Clicked");
                currentState = HOME_STATE;
            }
        }else if(currentState == HOME_STATE){
            if(returnToLauncher.contains(e.getX(), e.getY())){
                currentState = LOGIN_STATE;
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {


       if(currentState == LOGIN_STATE) {
           if (startButton.contains(e.getX(), e.getY())) {
               startButton.setTextColor(Color.GRAY);
           } else {
               startButton.setTextColor(Color.WHITE);
           }
       }else if(currentState == HOME_STATE){
           if (returnToLauncher.contains(e.getX(), e.getY())) {
               returnToLauncher.setTextColor(Color.GRAY);
           } else {
               returnToLauncher.setTextColor(Color.WHITE);
           }
       }

    }
}
