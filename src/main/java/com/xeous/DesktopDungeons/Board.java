package com.xeous.DesktopDungeons;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

@Getter
@Setter
public class Board extends JPanel {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int NUMBER_OF_CUBES = 100;
	public static final int CUBE_PIXELS = HEIGHT/ NUMBER_OF_CUBES;
	private static final long serialVersionUID = 1L;
	private ArrayList<Wall> walls;
    private Player player;
	private Maze maze;
    private boolean creating;
    private boolean playing;
    private Block blockToDraw = null;

	public Board() {
        creating = false;
        playing = false;

        setLocation(200,0);
		setSize(WIDTH, HEIGHT);
        addKeyListener(new KeyHandler());
        setFocusable(true);
        setBackground(Color.black);
        initMap();

        walls = new ArrayList<>();

        repaint();
	}

	public void initMap(){
        maze = new Maze(this);
        maze.createMaze();

        creating = true;
	}


    private void drawInitLines(Graphics g, int currentDrawPoint) {
        g.setColor(Color.red);
        for (int i = 0; i <= NUMBER_OF_CUBES; i++) {
            g.drawLine(currentDrawPoint, 0, currentDrawPoint, HEIGHT);
            g.drawLine(0, currentDrawPoint, HEIGHT, currentDrawPoint);
            currentDrawPoint += CUBE_PIXELS;
        }
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("ok");

        if(blockToDraw != null){

        }

        if(creating){
            creating = false;

            g.fillRect(600,0,200,600);
            maze.drawMaze(g);



        }


    }

    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }


}
