package rpacman;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Character extends Creature {
	

	Image charImage;

	private int limit = 40;
	private int currlimit = 0;
	private String[] pressedDirections;
	private int currentdirection;
	private boolean[] keypressed;
	private int characterclass;
	private int modSpeed;
	private int luck;
	private int modLuck;
	private int experiencelimit;
	private ArrayList<Item> backpack;
	private int onlevel;
	private Random random;


	public Character(int characterclass, int xPos, int yPos){

		this.characterclass = characterclass;
		random = new Random();
		name = "The hero";
		this.xPos = xPos;
		this.yPos = yPos;
		image = new ImageIcon("/rpacman/pics/"+ characterclass+".png").getImage();
		realImage = new ImageIcon("/rpacman/pics/" + characterclass+"2"+".png").getImage();
		isDead = false;
		init();
		speed = 2;
		level = 1;
		delay = 1;
		onlevel = 1;
		direction = "nothing";	
		dx = 0;
		dy = 0;
		currentdirection = 0;
		pressedDirections = new String[3];
		setPressedToNothing();
		keypressed = new boolean[4];

	}
	
	//beállítja a karakter értékeit, a kasztja alapján
	public void init(){;
		health = random.nextInt(6) + random.nextInt(6) +14;
		if(characterclass == 0) {
			health += 8;
		}
		currentHealth = health;
		attack = random.nextInt(6) +7;
		if(characterclass == 2){
			attack += 3;
		}
	
		luck = random.nextInt(6)+1;
		if(characterclass == 1){
			luck += 3;
			attack += 1;
			criticalchance = 20;
		}
		setMod();
	}
	
	public void setMod(){
		modAttack += attack;
		modCriticalchance += criticalchance;
		modHealth += health;
		modLuck += luck;
	}
	
	@Override
	public int getAttackPower() {
		
		return 0;
	}
	
	@Override
	public void move() {

		
		if(delay >currentdelay){
			currentdelay++;
		}
		else{
			currentdelay = 0;
			
			
			if(dx != 0 || dy !=0){
				if(currlimit == limit){
					currlimit = 0;
					dx = 0;
					dy = 0;
					
					if(notKeyPressed()){
						direction = "nothing";
					}
					else{
						direction = changeDirection();
					}
					
					setPressedToNothing();

				}
				
				xPos += dx;
				currlimit += Math.abs(dx);
				
				yPos += dy;
				currlimit += Math.abs(dy);
			}
		}

		

	}
	

	
	public void setPressedToNothing(){
		for (int i = 0; i < pressedDirections.length; i++) {
			pressedDirections[i] = "nothing";
		}
	}
	
	public void addPressedDirection(String direction){
		
		
		if(currentdirection == pressedDirections.length-1){
			currentdirection = 0;
		}
		else{
			currentdirection++;
		}
		
		
		pressedDirections[currentdirection] = direction;
	}


	
	private String changeDirection(){
		
		
		if(!pressedDirections[currentdirection].equals("nothing")){

			return pressedDirections[currentdirection];
			
		}
		
		return "nothing";
		
		
	}

	public String[] getPressedDirections() {
		return pressedDirections;
	}

	public int getCurrentdirection() {
		return currentdirection;
	}
	
	
	public boolean notKeyPressed(){
		boolean l=true;
		for (int i = 0; i < keypressed.length; i++) {
			if(keypressed[i] == true){
				l = false;
			}
		}
		return l;
	}
	
	public boolean[] getKeypressed() {
		return keypressed;
	}

	public void setKeypressed(boolean keypressed, int i) {
		this.keypressed[i] = keypressed;
	}
	

	public int getModLuck() {
		return modLuck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}


	public int getOnlevel() {
		return onlevel;
	}

	public void increaseOnLevel() {
		onlevel++;
	}	

}
