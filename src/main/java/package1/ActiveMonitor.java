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
    private CustomButton getWebhook;
    private CustomButton target;
    private int numTarget;
    private CustomButton bestBuy;
    private int numBestBuy;
    private CustomButton walmart;
    private int numWalmart;
    private CustomButton amazon;
    private int numAmazon;
    private Font buttonFont;
    private Font smallerFont;



    ActiveMonitor(int x, int y, int width, int height, Color textColor, String webhookURL){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textColor = textColor;
        this.webhookURL = webhookURL;
        getWebhook = new CustomButton(x+10, y+10, width-20, height/6, "Get Webhook URL", Color.white, 45, 60);
        //
        target = new CustomButton(x+10, y+height-height*4/8-48, width-20, height/8, "Target: ", Color.white, 30, 48);
        bestBuy = new CustomButton(x+10, y+height-height*3/8-36, width-20, height/8, "BestBuy: ", Color.white, 30, 48);
        walmart = new CustomButton(x+10, y+height-height*2/8-24, width-20, height/8, "Walmart: ", Color.white, 30, 48);
        amazon = new CustomButton(x+10, y+height-height*1/8-12, width-20, height/8, "Amazon: ", Color.white, 30, 48);

        buttonFont = new Font("Impact", Font.PLAIN, 35);
        smallerFont = new Font("Impact", Font.PLAIN, 25);
        URLs = new ArrayList<String>();

        numTarget = 0;
        numBestBuy = 0;
        numWalmart = 0;
        numAmazon = 0;

    }




    public void draw(Graphics g){
        //update arraylist with urls from the Webhook

        for( WebHookSeleniumManager w: Runner.manager.getWebhookManagerList()){
            //iterates over all the webhookseleniummaangers
            if(w.getwebhookURL().equals(webhookURL)){
                URLs.clear();
                for (int i = 0; i < w.getMonitoringLinks().size(); i++) {
                    URLs.add(w.getMonitoringLinks().get(i));
                    //set numbers of each link
                    if(w.getMonitoringLinks().get(i).contains("amazon")){
                        numAmazon++;
                    }else if(w.getMonitoringLinks().get(i).contains("bestbuy")) {
                        numBestBuy++;
                    }else if(w.getMonitoringLinks().get(i).contains("target")){
                        numTarget++;
                    }else if(w.getMonitoringLinks().get(i).contains("walmart")){
                        numWalmart++;
                    }
                }
            }
        }
        target.setText("Target: "+numTarget);
        bestBuy.setText("BestBuy: "+numBestBuy);
        walmart.setText("Walmart: "+numWalmart);
        amazon.setText("Amazon: "+numAmazon);





        ////////////////////////Show Hitbox
        g.setColor(Color.GREEN);//shows hitboxes
        g.drawRect(x, y, width, height);
        /////////////////////////////////

        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.pink);
        g.fillRect(x+5, y+5, width-10, height-10);

        g.setFont(buttonFont);
        getWebhook.draw(g);
        g.setFont(smallerFont);

        target.draw(g);
        numTarget=0;
        bestBuy.draw(g);
        numBestBuy=0;
        walmart.draw(g);
        numWalmart=0;
        amazon.draw(g);
        numAmazon=0;


        //draw URLs Below:






//        g.setColor(textColor);
//        g.drawString(webhookURL, x, y);
//        g.drawString("Number of URLs: "+ URLs.size(), x, 50);

    }

    public CustomButton getWebhookButton(){
        return this.getWebhook;
    }

    public String getWebhookURL(){
        return this.webhookURL;
    }

    public boolean contains(int mouseX, int mouseY) {
        //if mouseX is right of x of the button AND left right of button (x+width)    ((THEN SAME THING FOR Y))
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            return true;
        }
        return false;
    }




}
