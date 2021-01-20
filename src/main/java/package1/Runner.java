package package1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;
import javax.swing.*;

//creates new GeneralInputManager.
public class Runner{

    // test github2

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public static ArrayList<String> guildIds;
    public static ArrayList<InputManager> managers;

    public static JFrame frame;
    public static AppPanel panel;
//    public static JButton b1;
//    public static JCheckBox box;
//    public static JButton startStopDiscord;
    //public static JButton startStopScraping;

//    public static JTextField key = new JTextField("Discord API Key Here");

    GeneralInputManager generalManager;

    public static void main(String[] args) throws LoginException, IOException {

        // RUNNER CODE
        guildIds = new ArrayList<String>();
        managers = new ArrayList<InputManager>();

        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");

        Runner r = new Runner();
        r.setup();

    }


    private void setup(){
        frame = new JFrame("Helium Restocks");
        panel = new AppPanel();
        frame.add(panel);


        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

    }






//    private void setup() {
//        //check box
//        box = new JCheckBox("Show Chrome Windows");
//        box.addActionListener(this);
//        box.setVisible(false);
//
//        //quit button
//        b1 = new JButton("Quit Helium Restocks");
//        b1.addActionListener(this);
//        b1.setBorderPainted(false);
//        b1.setFocusPainted(false);
//        b1.setContentAreaFilled(true);
//        b1.setBackground(new Color(136, 175, 156));
//        b1.setForeground(new Color (255,255,255));
//
//        //discord start
//        startStopDiscord = new JButton("Start Discord");
//        startStopDiscord.setBorderPainted(false);
//        startStopDiscord.setFocusPainted(false);
//        startStopDiscord.setContentAreaFilled(true);
//        startStopDiscord.setBackground(new Color(136, 175, 156));
//        startStopDiscord.setForeground(new Color (255,255,255));
//        startStopDiscord.addActionListener(this);
//
//        //JPanel
//        panel = new JPanel();
//        panel.add(b1);
//        panel.add(key);
//        panel.add(box);
//        panel.add(startStopDiscord);
//        panel.setBackground(new Color(125, 120, 124));
//
//        //jFrame
//        frame = new JFrame("Helium Restocks");
//        frame.setBounds(200, 200, WIDTH, HEIGHT);
//        //centers title text
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(panel);
//
//    }

//    @Override
//    public void actionPerformed(ActionEvent e){
//        // TODO Auto-generated method stub
//
//        if (e.getSource() == b1) {
//            System.clearProperty("http.proxyHost");
//            System.exit(0);
//        }else if(e.getSource() == startStopDiscord){
//            if(startStopDiscord.getText().equals("Start Discord")){
//                try {
//                    startStopDiscord.setText("Discord Bot Running...");
//                    generalManager = new GeneralInputManager(key.getText());
//                    //can only scrape after discord has been enabled
//                    box.setVisible(true);
//                    key.setVisible(false);
//                }catch(Exception except){
//                    startStopDiscord.setText("Start Discord");
//                }
//            }
//        }
//    }

}


