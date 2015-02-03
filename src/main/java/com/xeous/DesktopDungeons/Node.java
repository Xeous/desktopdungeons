package com.xeous.DesktopDungeons;

/**
 * Created by gabor on 2014.12.23..
 */
public class Node{
    Integer posX;
    Integer posY;

    Node parent;

    public Node(int x, int y, Node node){
        posX=x;
        posY = y;
        parent = node;
    }

    public Node opposite(){
        if(this.posX.compareTo(parent.posX)!=0){
            return new Node(this.posX+this.posX.compareTo(parent.posX),this.posY,this);
        }
        if(this.posY.compareTo(parent.posY)!=0){
            return new Node(this.posX,this.posY+this.posY.compareTo(parent.posY),this);
        }

        return null;
    }
}