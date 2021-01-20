package package1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

public class AppPanel extends JPanel implements ActionListener{


    Font titleFont;

    final int LOGIN_STATE = 0;
    final int HOME_STATE = 1;

    int currentState;

    AppPanel(){
        currentState = 0;
        titleFont = new Font("Impact", Font.PLAIN, 78);


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
        Graphics2D g2d = (Graphics2D) g.create();
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, (float) 0.09);
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


        g2d.dispose();



    }

    private void drawHomeState(Graphics g){

    }




    @Override
    public void actionPerformed(ActionEvent e) {



    }



}
