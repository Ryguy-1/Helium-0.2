package package1;

import java.awt.*;

public class CustomButton {


    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Color textColor;
    private int offsetX;
    private int offsetY;


    CustomButton(int x, int y, int width, int height, String text, Color textColor, int offsetX, int offsetY){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColor = textColor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }




    public void draw(Graphics g){
        ////////////////////////Show Hitbox
        g.setColor(Color.GREEN);//shows hitboxes
        g.drawRect(x, y, width, height);
        /////////////////////////////////


        g.setColor(textColor);
        g.drawString(text, x+offsetX, y+offsetY);

    }

    public void fill(Graphics g){
        g.setColor(new Color(58, 126, 166));
        g.fillRect(x, y, width, height);
    }


    public void setTextColor(Color color){
        this.textColor = color;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public boolean contains(int mouseX, int mouseY) {
        //if mouseX is right of x of the button AND left right of button (x+width)    ((THEN SAME THING FOR Y))
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            return true;
        }
        return false;
    }





}
