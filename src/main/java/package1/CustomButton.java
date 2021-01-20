package package1;

import java.awt.*;

public class CustomButton {


    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Color textColor;

    CustomButton(int x, int y, int width, int height, String text, Color textColor){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColor = textColor;
    }




    public void draw(Graphics g){
        ////////////////////////Get Rid of For Finished Product
//        g.setColor(Color.GREEN);//shows hitboxes
//        g.drawRect(x, y, width, height);
        /////////////////////////////////


        g.setColor(textColor);
        g.drawString(text, x+37, y+70);

    }

    public void setTextColor(Color color){
        this.textColor = color;
    }


    public boolean contains(int mouseX, int mouseY) {
        //if mouseX is right of x of the button AND left right of button (x+width)    ((THEN SAME THING FOR Y))
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            return true;
        }
        return false;
    }





}
