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
    public static final int MONITORING_STATE = 1;

    //LOGIN_STATE
    private CustomButton startButton;

    // !LOGIN_STATE
    private CustomButton returnToLauncher;
    private CustomButton monitoringButton;
    private CustomButton bottingButton;
    private CustomButton discordButton;

    //MONITORING_STATE
    private CustomButton addMonitor;
    private CustomButton viewMonitors;
    private boolean viewingMonitors;


    public static int currentState;

    AppPanel(){
        //GENERAL
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        currentState = 0;
        titleFont = new Font("Impact", Font.PLAIN, 78);
        buttonFont = new Font("Impact", Font.PLAIN, 48);
        smallButtonFont = new Font("Impact", Font.PLAIN, 25);
        //LOGIN_STATE
        startButton = new CustomButton(Runner.WIDTH*11/16, Runner.HEIGHT*1/6, 300, 100, "Start Helium", Color.white, 37, 70);
        //!LOGIN_STATE
        returnToLauncher = new CustomButton(0, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Return to Launcher", Color.white, 55, 30);
        monitoringButton = new CustomButton(Runner.WIDTH*1/4, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Monitors", Color. white, 105, 30);
        bottingButton = new CustomButton(Runner.WIDTH*2/4, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Botting", Color. white, 111, 30);
        discordButton = new CustomButton(Runner.WIDTH*3/4, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Discord Integration", Color. white, 50, 30);
        //MONITORING_STATE
        viewMonitors = new CustomButton(0, Runner.HEIGHT*0/2+Runner.HEIGHT/15, Runner.WIDTH/16, Runner.HEIGHT/2-Runner.HEIGHT/15, "View", Color. white, 12, 150);
        addMonitor = new CustomButton(0, Runner.HEIGHT*1/2, Runner.WIDTH/16, Runner.HEIGHT/2, "Add+", Color. white, 12, 150);
        viewingMonitors = true;


    }


    public void paintComponent(Graphics g){

        if (currentState == LOGIN_STATE) {
            drawLoginState(g);
        } else if (currentState == MONITORING_STATE) {
            drawMonitoringState(g);
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

    private void drawMonitoringState(Graphics g){

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
        g.fillRect(0,0,Runner.WIDTH/16, Runner.HEIGHT);

        //draw Task bar
        g2d.setColor(Color.darkGray);
        g2d.fillRect(0,0, Runner.WIDTH, Runner.HEIGHT/15+2);

        g.setColor(Color.pink);
        g.fillRect(0,0, Runner.WIDTH, Runner.HEIGHT/15);

        g.setFont(smallButtonFont);
        returnToLauncher.draw(g);
        //
        monitoringButton.fill(g);
        monitoringButton.draw(g);
        //
        bottingButton.draw(g);
        discordButton.draw(g);



        if(viewingMonitors){
            viewMonitors.fill(g);
            viewMonitors.draw(g);
            addMonitor.draw(g);
        }else{
            viewMonitors.draw(g);
            addMonitor.fill(g);
            addMonitor.draw(g);
        }



        g2d.dispose();


    }

    private void addMonitor(){
        String webhookURL, websiteURL;
        webhookURL = ""; websiteURL = "";

        try {
            webhookURL = (String) JOptionPane.showInputDialog(null, "Webhook URL",
                   "Please Enter the Webhook URL", JOptionPane.QUESTION_MESSAGE);

            websiteURL = (String) JOptionPane.showInputDialog(null, "URL (BestBuy, Target, Amazon, or Walmart)",
                    "Please Enter the URL for the Website", JOptionPane.QUESTION_MESSAGE);
        }catch(Exception e){}

        Runner.manager.addWebhook(webhookURL);
        Runner.manager.addURLtoWebhook(webhookURL, websiteURL);




        viewingMonitors = true;


    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(currentState == LOGIN_STATE) {
            if (startButton.contains(e.getX(), e.getY())) {
                System.out.println("Start button was Clicked");
                currentState = MONITORING_STATE;
            }
        }else if(currentState != LOGIN_STATE){
            if(returnToLauncher.contains(e.getX(), e.getY())){
                currentState = LOGIN_STATE;
            }else if(monitoringButton.contains(e.getX(), e.getY())){
                //switch to monitors
            }else if(bottingButton.contains(e.getX(), e.getY())){
                //switch to botting
            }else if(discordButton.contains(e.getX(), e.getY())){
                //switch to discord integration
            }
        }

        //separate because also have to draw taskbar in top else if statement
        if(currentState == MONITORING_STATE){
            if(viewMonitors.contains(e.getX(), e.getY())){
                //viewing monitors
                viewingMonitors = true;
            }else if(addMonitor.contains(e.getX(), e.getY())){
                //add monitor
                viewingMonitors = false;
                addMonitor();
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
       }else if(currentState != LOGIN_STATE){
           //have to do separate for each so no overlap -> Weird issue but fixes it.
           if (returnToLauncher.contains(e.getX(), e.getY())) {
               returnToLauncher.setTextColor(Color.GRAY);
           } else{
               returnToLauncher.setTextColor(Color.WHITE);
           }

           if(monitoringButton.contains(e.getX(), e.getY())){
               monitoringButton.setTextColor(Color.GRAY);
           } else{
               monitoringButton.setTextColor(Color.WHITE);
           }

           if(bottingButton.contains(e.getX(), e.getY())){
               bottingButton.setTextColor(Color.GRAY);
           } else{
               bottingButton.setTextColor(Color.WHITE);
           }

           if(discordButton.contains(e.getX(), e.getY())){
               discordButton.setTextColor(Color.GRAY);
           } else{
               discordButton.setTextColor(Color.WHITE);
           }
       }


       if(currentState==MONITORING_STATE){

           if(viewMonitors.contains(e.getX(), e.getY())){
               viewMonitors.setTextColor(Color.GRAY);
           }else{
               viewMonitors.setTextColor(Color.WHITE);
           }

           if(addMonitor.contains(e.getX(), e.getY())){
               addMonitor.setTextColor(Color.GRAY);
           }else{
               addMonitor.setTextColor(Color.WHITE);
           }

       }


    }
}
