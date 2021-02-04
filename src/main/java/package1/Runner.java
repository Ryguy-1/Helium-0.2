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

    public static String guildId;
    public static InputManager manager;

    public static JFrame frame;
    public static AppPanel panel;


    public static void main(String[] args) throws LoginException, IOException {

        // RUNNER CODE
        guildId = "";
        manager = new InputManager();

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

}


