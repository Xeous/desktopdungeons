package rpacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.GapContent;


public class Board extends JPanel implements ActionListener{

	private Timer gametimer;
	boolean paused;
	private static final int SIZE = 680; 
	private static final int PORTION = 17; 
	private ArrayList<Monster> monsters;
	private ArrayList<Item> items;
	public static final int timerDelay = 5;
	private static final Image wallImage = new ImageIcon("/rpacman/pics/wall.png").getImage();
	private static final Image stairsImage = new ImageIcon("/rpacman/pics/stairs.png").getImage();
	private static final Image healthImage = new ImageIcon("/rpacman/pics/health.png").getImage();
	private static final Image treasureImage = new ImageIcon("/rpacman/pics/treasure.png").getImage();
	private GameLogic logic;
	private boolean startgame;
	private boolean ingame;
	private Monster monsterToFightWith;
	private boolean gameover;
	private boolean gamepaused;
	private Point mousePos;
	private boolean infight;
	private boolean gameOver;
	private boolean gameWon;
	
	public Board() {
		
		addKeyListener(new TAdapter());
		addMouseListener(new MyMouseAdapter());
		setFocusable(true);
		setBackground(Color.black);
        gametimer = new Timer(timerDelay,this);
        gametimer.start();
        
        startNewGame();
        
    	int charclass = ((mousePos.x - 150) / 100)/2; // 0- tól 2- ig tartó számra váltja
    	logic.getNewCharacter(charclass);
    	ingame = true;
    	gamepaused = false;
    	startgame = false;
	}

	private void startNewGame() {
		gamepaused = true;
        startgame = true;
        ingame = false;
        mousePos = new Point(0,0);
        logic = new GameLogic(this);
        monsterToFightWith = null;
        gameOver = false;
        gameWon = false;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		int oneblock = (int) SIZE / PORTION;
		
		if(startgame){
			initgame(g2d);
		}
		
		if(gameOver){
			gameState(g2d, "Congratulaions! You have died.");
		}
		
		if(gameWon){
			gameState(g2d, "Congratulaions! You saved the world.");
		}
		
		if(monsterToFightWith != null){
			drawBattle(g2d);
			if(logic.character.isDead){
				gameOver = true;
			}
		}
			
		if(ingame){
			drawLines(g2d, oneblock);
			drawWalls(g2d, oneblock);
	        drawStats(g2d, logic.character, SIZE+2, 0);
	        drawMonsters(g2d);
	        g2d.drawImage(logic.character.getImage(), logic.character.getXPos(), logic.character.getYPos(), this);
			if(logic.character.isDead){
				gameOver = true;
			}
		}
		
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
		
	}
	

	private void drawBattle(Graphics2D g2d) {
		drawStats(g2d, monsterToFightWith, 0, 0);
        drawStats(g2d, logic.character, SIZE+2, 0);
		ingame= false;
		
	}

	private void drawLines(Graphics2D g2d, int oneblock) {
		g2d.setColor(Color.blue);
		for (int i = 0; i<=SIZE; i = i + oneblock){
			g2d.drawLine(i, 0, i, SIZE);
		}
		
		for (int i = 0; i<=SIZE; i = i + oneblock){
			g2d.drawLine(0, i, SIZE, i);
		}
	}
	
	
	private void drawWalls(Graphics2D g2d, int oneblock){
		int object; 
		
		for (int i = 0; i < PORTION; i++) {
			for (int j = 0; j < PORTION; j++) {
				object = logic.getObjectFromLogic(i, j);
				
				if(object == 1){ // fal
					g2d.drawImage(wallImage, i*oneblock+2, j*oneblock+1, this);
				}
				if(object == logic.treasureNumber){ //a végsõ kincs
					g2d.drawImage(treasureImage, i*oneblock+2, j*oneblock+1, this);
				}
				if(object == logic.healthNumber){ // élet
					g2d.drawImage(healthImage, i*oneblock+2, j*oneblock+1, this);
				}
				if(object == logic.stairsNumber){ // következõ pálya bejárata
					g2d.drawImage(stairsImage, i*oneblock+2, j*oneblock+1, this);
				}

			}
		}
	}
	
	
	
	
	private void drawStats(Graphics g2d, Creature creature, int x, int y){		
		Image image = creature.getRealImage();
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		g2d.setColor(Color.white);
		g2d.drawRect(x, y, width, height+1);
		g2d.drawRect(x, y, width, SIZE);
		g2d.drawImage(image, x+1, y+1, this);
		setFont(g2d);
        int addy = 40;
        
        String s1 , s2, s3, s4, s5, s6;
        if(creature.getName().equals("The hero")){
        	s1 = "Level: " + logic.character.getLevel();
        	s2 = "Health: " + logic.character.getModHealth() + "/" + logic.character.getCurrentHealth();
        	s3 = "Attack: " + logic.character.getModAttack();
        	s4 = "Critical: " + logic.character.getModCriticalchance();
        	s5 = "XP: " + logic.character.getExperience();
        	s6 = "Luck: " + logic.character.getModLuck();

        }
        else{
        	s1 = "Level: " + creature.getLevel();
        	s2 = "Health: " + creature.getModHealth() + "/" + creature.getCurrentHealth();
        	s3 = "Attack: " + creature.getModAttack();
        	s4 = "Critical: " + creature.getModCriticalchance();
        	s5 = "XP: " + creature.getExperience() ;
        	s6 = "";
        }      
        x += 20;
        g2d.drawString(s1, x, height +=40);
        g2d.drawString(s2, x, height+=addy);
        g2d.drawString(s3, x, height+=addy);
        g2d.drawString(s4, x, height+=addy);
        g2d.drawString(s5, x, height+=addy);
        g2d.drawString(s6, x, height+=addy);
	}

	private void setFont(Graphics g2d) {
		Font font  = new Font("Exocet", Font.BOLD, 20);
        g2d.setColor(Color.white);
        g2d.setFont(font);
	}
	
	
	private void initgame(Graphics g2d){
		Image image1 = new ImageIcon("/rpacman/pics/02.png").getImage();
		Image image2 = new ImageIcon("/rpacman/pics/12.png").getImage();
		Image image3 = new ImageIcon("/rpacman/pics/22.png").getImage();
		g2d.drawImage(image1, 150, 200, this);
		drawBorder(image1, 150, 200, g2d);
		g2d.drawImage(image2, 350, 200, this);
		drawBorder(image2, 350, 200, g2d);
		g2d.drawImage(image3, 550, 200, this);
		drawBorder(image3, 550, 200, g2d);
		setFont(g2d);
        g2d.drawString("Choose your destiny", 310, 100);
        g2d.drawString("Or just load a game", 310, 500);
        Rectangle r = new Rectangle(150, 200, 600, 200);
        if(r.contains(mousePos)){
        	int charclass = ((mousePos.x - 150) / 100)/2; // 0- tól 2- ig tartó számra váltja
        	logic.getNewCharacter(charclass);
        	ingame = true;
        	gamepaused = false;
        	startgame = false;
        }
        
	}
	
	private void gameState(Graphics g2d, String state){
		gamepaused = true;
		ingame = false;
		monsterToFightWith = null;
		setFont(g2d);
        g2d.drawString(state, 210, 340);		
	}
	
	
	private void drawBorder(Image image, int x, int y, Graphics g2d){
		g2d.setColor(Color.white);
		int width = image.getWidth(this);
		int height = image.getHeight(this);
		g2d.drawRect(x, y, width, height);
		
	}
	
	public void drawMonsters(Graphics g2d){
		for(Monster m : logic.monsters){
			g2d.drawImage(m.getImage(), m.getXPos(), m.getYPos(), this);
		}
		
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!gamepaused){
			logic.changeDirection();
			logic.play();
		}

		repaint();	
	}

	private void pause() {
		
		if(gamepaused == true){
			gamepaused = false;
		}
		else gamepaused = true;
	}
	
	
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
        	
        	if(ingame){
        		int key = e.getKeyCode();
        		if (key == KeyEvent.VK_A) {
        			logic.character.setKeypressed(false, 0);
        		}
        		if (key == KeyEvent.VK_D) {
        			logic.character.setKeypressed(false , 1);
        		}
        		if (key == KeyEvent.VK_W) {
        			logic.character.setKeypressed(false, 2);
        		}
        		if (key == KeyEvent.VK_S) {
        			logic.character.setKeypressed(false, 3);
        		}
        	}

        }

    	public void keyPressed(KeyEvent e) {
    		int key = e.getKeyCode();
    		
    		if (key == KeyEvent.VK_R) {
    			startNewGame();
    		}
    		
    		
    		if(ingame){
        		if (key == KeyEvent.VK_SPACE) {
        			pause();
        		}
        		if (key == KeyEvent.VK_A) {
        			logic.character.setKeypressed(true ,0 );
        		}
        		if (key == KeyEvent.VK_D) {
        			logic.character.setKeypressed(true, 1);
        		}
        		if (key == KeyEvent.VK_W) {
        			logic.character.setKeypressed(true, 2);
        		}
        		if (key == KeyEvent.VK_S) {
        			logic.character.setKeypressed(true ,3);
        		}
    		}

    	}
    }
    
    
    private class MyMouseAdapter extends MouseAdapter{
    	 public void mousePressed(MouseEvent e) {
             int x = e.getX();
             int y = e.getY();
             mousePos = new Point(x, y);
         }
    }

    public void setMonsterToFightWith(Monster m){
    	monsterToFightWith = m;
    }
    
    public void setGameWon(boolean l){
    	gameWon = l;
    }
	
}
