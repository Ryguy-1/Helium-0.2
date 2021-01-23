package package1;

import net.bytebuddy.dynamic.TypeResolutionStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

public class AppPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {


    private Font titleFont;
    private Font buttonFont;
    private Font smallButtonFont;
    private Font superSmallButtonFont;

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
    private CustomButton removeMonitor;
    private CustomButton viewMonitors;
    private CustomButton removeWebhook;
    private boolean viewingMonitors;
    private boolean addingMonitors;
    private boolean removingMonitors;
    private boolean removingWebhook;
    private ArrayList<ActiveMonitor> drawnMonitors;



    public static int currentState;

    AppPanel(){
        //GENERAL
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        currentState = 0;
        titleFont = new Font("Impact", Font.PLAIN, 78);
        buttonFont = new Font("Impact", Font.PLAIN, 48);
        smallButtonFont = new Font("Impact", Font.PLAIN, 25);
        superSmallButtonFont = new Font("Impact", Font.PLAIN, 20);
        //LOGIN_STATE
        startButton = new CustomButton(Runner.WIDTH*11/16, Runner.HEIGHT*1/6, 300, 100, "Start Helium", Color.white, 37, 70);
        //!LOGIN_STATE
        returnToLauncher = new CustomButton(0, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Return to Launcher", Color.white, 55, 30);
        monitoringButton = new CustomButton(Runner.WIDTH*1/4, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Monitors", Color. white, 105, 30);
        bottingButton = new CustomButton(Runner.WIDTH*2/4, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Botting", Color. white, 111, 30);
        discordButton = new CustomButton(Runner.WIDTH*3/4, 0, Runner.WIDTH/4, Runner.HEIGHT/15, "Discord Integration", Color. white, 50, 30);
        //MONITORING_STATE
        viewMonitors = new CustomButton(0, Runner.HEIGHT*0/3+Runner.HEIGHT/15, Runner.WIDTH/12, Runner.HEIGHT/3-Runner.HEIGHT/15, "View", Color. white, 25, 100);
        addMonitor = new CustomButton(0, Runner.HEIGHT*1/3, Runner.WIDTH/12, Runner.HEIGHT/3, "Add+", Color. white, 25, 120);
        removeMonitor = new CustomButton(0, Runner.HEIGHT*2/3, Runner.WIDTH/12, Runner.HEIGHT/3, "Remove-", Color.white, 5, 100);
        removeWebhook = new CustomButton(Runner.WIDTH*3/4, Runner.HEIGHT*5/6, Runner.WIDTH/4, Runner.HEIGHT/6, "Remove Entire Webhook --", Color.white, 10, 50);
        viewingMonitors = true;
        addingMonitors = false;
        removingMonitors = false;
        removingWebhook = false;
        drawnMonitors = new ArrayList<ActiveMonitor>();


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
        g.fillRect(0,0,Runner.WIDTH/12, Runner.HEIGHT);

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

        for(ActiveMonitor active: drawnMonitors){
            active.draw(g);
        }


        if(viewingMonitors){
            viewMonitors.fill(g);
            viewMonitors.draw(g);
            addMonitor.draw(g);
            removeMonitor.draw(g);
            removeWebhook.draw(g);
        }else if(addingMonitors){
            viewMonitors.draw(g);
            addMonitor.fill(g);
            addMonitor.draw(g);
            removeMonitor.draw(g);
            removeWebhook.draw(g);
        }else if(removingMonitors){
            viewMonitors.draw(g);
            addMonitor.draw(g);
            removeMonitor.fill(g);
            removeMonitor.draw(g);
            removeWebhook.draw(g);
        }else if(removingWebhook){
            viewMonitors.draw(g);
            addMonitor.draw(g);
            removeMonitor.draw(g);
            removeWebhook.fill(g);
            removeWebhook.draw(g);
        }



        g2d.dispose();


    }


    private void updateActiveMonitors(){

        //drawnMonitors;

        drawnMonitors.clear();

        //Sidebar = Width/12
        //Topbar = Height/15

        //goes to 3 max as you can only have 3 webhooks right now...
//        for (int i = 0; i < Runner.manager.getWebhookManagerList().size(); i++){
//            drawnMonitors.add(new ActiveMonitor((Runner.WIDTH/12)+(Runner.WIDTH*11/12)*(i/3), Runner.HEIGHT*2/15, (Runner.WIDTH - Runner.WIDTH/12)*1/3-20, ((Runner.HEIGHT-Runner.HEIGHT/15)-Runner.HEIGHT*2/15), Color.white,
//                    Runner.manager.getWebhookManagerList().get(i).getwebhookURL()));
//        }
        //done this way for now. Will make expandable later. Just not working how I would like it with the other algorithm so do this to save time!
        if(Runner.manager.getWebhookManagerList().size()>=1){
                        drawnMonitors.add(new ActiveMonitor((Runner.WIDTH/12)+ 10, Runner.HEIGHT*2/15, (Runner.WIDTH - Runner.WIDTH/12)*1/3-20, ((Runner.HEIGHT-Runner.HEIGHT/15)-Runner.HEIGHT*2/15), Color.white,
                    Runner.manager.getWebhookManagerList().get(0).getwebhookURL()));
        }
        if(Runner.manager.getWebhookManagerList().size()>=2){
            drawnMonitors.add(new ActiveMonitor((Runner.WIDTH/12)+ 20 + (Runner.WIDTH - Runner.WIDTH/12)*1/3-20, Runner.HEIGHT*2/15, (Runner.WIDTH - Runner.WIDTH/12)*1/3-20, ((Runner.HEIGHT-Runner.HEIGHT/15)-Runner.HEIGHT*2/15), Color.white,
                    Runner.manager.getWebhookManagerList().get(1).getwebhookURL()));
        }
        if(Runner.manager.getWebhookManagerList().size()==3){
            drawnMonitors.add(new ActiveMonitor((Runner.WIDTH/12)+ 30 + 2*((Runner.WIDTH - Runner.WIDTH/12)*1/3-20), Runner.HEIGHT*2/15, (Runner.WIDTH - Runner.WIDTH/12)*1/3-20, ((Runner.HEIGHT-Runner.HEIGHT/15)-Runner.HEIGHT*2/15), Color.white,
                    Runner.manager.getWebhookManagerList().get(2).getwebhookURL()));
        }

    }






    private void addMonitor(){
        String webhookURL, websiteURL;
        webhookURL = ""; websiteURL = "";
        boolean isVisible = false;

        try {
            webhookURL = (String) JOptionPane.showInputDialog(null, "Webhook URL",
                   "Please Enter the Webhook URL", JOptionPane.QUESTION_MESSAGE);

            websiteURL = (String) JOptionPane.showInputDialog(null, "URL (BestBuy, Target, Amazon, or Walmart)",
                    "Please Enter the URL for the Website", JOptionPane.QUESTION_MESSAGE);



            if (JOptionPane.showConfirmDialog(null, "Do You Want the Chrome Window to Be Visible?", "Set Visibility",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                isVisible = true;
            } else {
                isVisible = false;
            }

        }catch(Exception e){}

        if(Runner.manager.hasAddedWebhook(webhookURL)){
            Runner.manager.addURLtoWebhook(webhookURL, websiteURL, isVisible);
        }else{
            Runner.manager.addWebhook(webhookURL);
            Runner.manager.addURLtoWebhook(webhookURL, websiteURL, isVisible);
        }

        updateActiveMonitors();

        viewingMonitors = true;



    }


    private void removeMonitor(){

        String webhookURL, websiteURL;
        webhookURL = ""; websiteURL = "";


        try {
            webhookURL = (String) JOptionPane.showInputDialog(null, "Webhook URL",
                    "Please Enter the Webhook URL to Remove From", JOptionPane.QUESTION_MESSAGE);

            websiteURL = (String) JOptionPane.showInputDialog(null, "URL (BestBuy, Target, Amazon, or Walmart)",
                    "Please Enter the URL for the Website to Remove", JOptionPane.QUESTION_MESSAGE);
        }catch(Exception e){}


        if(Runner.manager.hasAddedWebhook(webhookURL)){
            Runner.manager.removeURLfromWebhook(webhookURL, websiteURL);
        }else{
            System.out.println("Have not initiated Webhook Yet!");
        }

        updateActiveMonitors();

        viewingMonitors = true;

    }


    private void removeWebhook(){

        String webhookURL = "";


        try {
            webhookURL = (String) JOptionPane.showInputDialog(null, "Webhook URL",
                    "Please Enter the Webhook URL to Remove Entirely", JOptionPane.QUESTION_MESSAGE);

        }catch(Exception e){}


        if(Runner.manager.hasAddedWebhook(webhookURL)){
            Runner.manager.removeWebhook(webhookURL);
        }else{
            System.out.println("Webhook Has Not Been Initialized Yet!");
        }

        updateActiveMonitors();

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
                addingMonitors = false;
                removingMonitors = false;
                removingWebhook = false;
            }else if(addMonitor.contains(e.getX(), e.getY())){
                //add monitor
                viewingMonitors = false;
                addingMonitors = true;
                removingMonitors = false;
                removingWebhook = false;
                addMonitor();
            }else if(removeMonitor.contains(e.getX(), e.getY())){
                viewingMonitors = false;
                addingMonitors = false;
                removingMonitors = true;
                removingWebhook = false;
                removeMonitor();
            }else if(removeWebhook.contains(e.getX(), e.getY())){
                viewingMonitors = false;
                addingMonitors = false;
                removingMonitors = false;
                removingWebhook = true;
                removeWebhook();
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

           if(removeMonitor.contains(e.getX(), e.getY())){
               removeMonitor.setTextColor(Color.GRAY);
           }else{
               removeMonitor.setTextColor(Color.WHITE);
           }

           if(removeWebhook.contains(e.getX(), e.getY())){
               removeWebhook.setTextColor(Color.GRAY);
           }else{
               removeWebhook.setTextColor(Color.WHITE);
           }


       }


    }
}
