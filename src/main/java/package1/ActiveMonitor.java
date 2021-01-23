package package1;

import java.awt.*;
import java.util.ArrayList;

public class ActiveMonitor {
//Represents one Webhook

    private int x;
    private int y;
    private int width;
    private int height;
    private String webhookURL;
    private Color textColor;
    private ArrayList<String> URLs;


    ActiveMonitor(int x, int y, int width, int height, Color textColor, String webhookURL){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textColor = textColor;
        this.webhookURL = webhookURL;
        URLs = new ArrayList<String>();
    }




    public void draw(Graphics g){
        //update arraylist with urls from the Webhook
        for( WebHookSeleniumManager w: Runner.manager.getWebhookManagerList()){
            //iterates over all the webhookseleniummaangers
            if(w.getwebhookURL().equals(webhookURL)){
                URLs.clear();
                for (int i = 0; i < w.getMonitoringLinks().size(); i++) {
                    URLs.add(w.getMonitoringLinks().get(i));
                }
            }
        }

        ////////////////////////Show Hitbox
        g.setColor(Color.GREEN);//shows hitboxes
        g.drawRect(x, y, width, height);
        /////////////////////////////////

        g.setColor(textColor);
        g.drawString(webhookURL, x, y);
        g.drawString("Number of URLs: "+ URLs.size(), x, 50);



    }




    public boolean contains(int mouseX, int mouseY) {
        //if mouseX is right of x of the button AND left right of button (x+width)    ((THEN SAME THING FOR Y))
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            return true;
        }
        return false;
    }




}
