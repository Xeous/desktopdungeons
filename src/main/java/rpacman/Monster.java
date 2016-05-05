package rpacman;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class Monster extends Creature{
	
	Random random = new Random();
	private static final String[] directions = { "WEST", "EAST", "NORTH", "SOUTH"};
	private boolean canSeeTheCharacter;
	private int numberOfStepsDone;
	private int monsterStep;
	public boolean shouldMove;
	
	public Monster(int n) {
		monsterStep = 4;
		
		if(1<n && n < 10){
			this.name = "Ghost";
			delay = 0;
			speed = 1;
			image = new ImageIcon("/rpacman/pics/ghost.png").getImage();
			realImage = new ImageIcon("/rpacman/pics/ghost2.png").getImage();
			dx = 0;
			dy = 0;
			direction = getRandomDirection(directions);
			direction = "WEST";
			setDxDy();
			criticalchance = 5;
			numberOfStepsDone = 0;
		}
		else {
			this.name = "Necromancer";
			delay = 0;
			speed = 0;
			criticalchance = 30;
			direction = "nothing";
			image = new ImageIcon("/rpacman/pics/necro.png").getImage();
			realImage = new ImageIcon("/rpacman/pics/necro2.png").getImage();
			dx = 0;
			dy = 0;
			canSeeTheCharacter = false;
		}

	}
	
	public String getRandomDirection(String[] dir){
		return dir[random.nextInt(dir.length)];
	}
	
	
	@Override
	void move() {

		
		if(delay>currentdelay){
			currentdelay++;
		}
		else{
			currentdelay = 0;
		
			xPos += dx;
	
			yPos += dy;
			if(isInPlace()){
				numberOfStepsDone ++;
				if(numberOfStepsDone == monsterStep){
					numberOfStepsDone = 0;
					shouldMove = true;
				}
				else{
					shouldMove = false;
				}
			}
		}


	}
	

	@Override
	public int getAttackPower() {
		int attackpower = random.nextInt(6) + random.nextInt(6) +2 + attack;
		return attackpower;
	}

	
	public String[] getDirections(){
		return directions;
	}

	
}
