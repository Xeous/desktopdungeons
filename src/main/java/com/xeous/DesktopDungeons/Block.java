package com.xeous.DesktopDungeons;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * Created by gabor on 2014.12.18..
 */

@Getter
@Setter
public class Block {
    private String type; //start end wall monster player
    private GameElement gameElement;
    private Point position;
    private Color color;
    private Image image;
    private int size;

    public Block(Point position){
        this.position = position;
        type = "empty";
        size = Board.CUBE_PIXELS;
        gameElement=null;
        color = Color.WHITE;
    }


    public void drawBlock(Graphics g){
        if(type.equals("S")){
            color = Color.BLUE;
        }
        else if (type.equals("E")){
            color = Color.RED;
        }
        else if (type.equals(".")){
            color = Color.BLACK;
        }
        else{
            color = Color.WHITE;
        }

        g.setColor(color);
        g.fillRect(position.x*size, position.y*size, size,size);
    }


}
