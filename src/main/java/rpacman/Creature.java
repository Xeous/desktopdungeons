package rpacman;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
 * @author Gabii
 *
 */
public abstract class Creature {
	protected int dx;
	protected int dy;
	protected int speed;
	protected int health;
	protected int attack;
	protected double delay;
	protected int currentdelay;
	protected String direction;
	protected Point prevPosition;
	protected int onetile;
	protected int criticalchance;
	protected int experience;
	protected Image realImage;
	protected int currentHealth;
	protected int modHealth;
	protected int modAttack;
	protected int modCriticalchance;
	protected boolean isDead;
	protected String name;
	protected Image image;
	protected int xPos;
	protected int yPos;
	protected int level;
	
	public Creature(){
	}


	public boolean isInPlace() {
		return (((xPos - 1) % ((image.getHeight(null) + 2))) == 0)
				&& (((yPos - 1) % ((image.getHeight(null) + 2))) == 0);

	}
	
	public void setDxDy() {
		
			int[] dxy = getDxDyFromDirection(direction);
			dx = dxy[0];
			dy = dxy[1];

	}

	protected int[] getDxDyFromDirection(String direction) {
		int dx;
		int dy;
		
		if (direction.equals("NORTH")) {
			dx = 0;
			dy = -speed;
		} else if (direction.equals("WEST")) {
			dx = -speed;
			dy = 0;
		} else if (direction.equals("SOUTH")) {
			dx = 0;
			dy = speed;
		} else if(direction.equals("EAST")){
			dx = speed;
			dy = 0;
		}
		else{
			dx = 0;
			dy = 0;
		}
		int[] a = {dx, dy};
		return a;
	}
	
	public Point getNextPosition(String direction){
		Point p = getPosition();
		int dxy[] = getDxDyFromDirection(direction);
		int dx = dxy[0];
		int dy = dxy[1];
		
		if(dx < 0){
			return new Point(p.x-1, p.y);
		}
		if(dx > 0){
			return new Point(p.x+1, p.y);
		}
		if(dy < 0){
			return new Point(p.x, p.y-1);
		}
		if (dy > 0){
			return new Point(p.x, p.y+1);
		}
		return p;
		
		
	}
	
	public boolean isCritical(){
		Random r = new Random();
		int i = r.nextInt(100)+1;
		if(criticalchance >= i){
			return true;
		}
		return false;
	}
	
	public abstract int getAttackPower();
	
	public Rectangle getBounds(){
		return new Rectangle(xPos, yPos, image.getHeight(null), image.getWidth(null));
	}
	
	public boolean isMoving(){
		if(dx !=0 || dy!=0){
			return true;
		}
		else return false;
	}
	
	public void setDirection(String s) {
		direction = s;
	}

	public String getDirection() {
		return direction;
	}

	public String getPreviousdirection() {
		return "valami";
	}

	public Point getPosition(){
		return new Point(xPos/40, yPos/40);
	}

	
	abstract void move();

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}


	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}


	public double getDelay() {
		return delay;
	}


	public void setDelay(double delay) {
		this.delay = delay;
	}


	public int getCurrentdelay() {
		return currentdelay;
	}


	public void setCurrentdelay(int currentdelay) {
		this.currentdelay = currentdelay;
	}
	

	public Point getPrevPosition() {
		return prevPosition;
	}
	
	
	public void setPrevPosition(Point prevPosition) {
		this.prevPosition = prevPosition;
	}
	
	public int getCriticalchance() {
		return criticalchance;
	}

	public void setCriticalchance(int criticalchance) {
		this.criticalchance = criticalchance;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public Image getRealImage() {
		return realImage;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getXPos() {
		return xPos;
	}


	public void setXPos(int xPos) {
		this.xPos = xPos;
	}


	public int getYPos() {
		return yPos;
	}


	public void setYPos(int yPos) {
		this.yPos = yPos;
	}


	public Image getImage() {
		return image;
	}
	

	public void setCurrentHealth(int health) {
		if(currentHealth - health > modHealth){
			currentHealth = modHealth;
			return;
		}
		if(currentHealth - health < 0){
			currentHealth = 0;
			isDead = true;
			return;
		}		
		currentHealth -= health;
	}
	
	public int getModHealth(){
		return modHealth;
	}
	
	public int getModAttack(){
		return modAttack;
	}
	
	public int getModCriticalchance(){
		return modCriticalchance;
	}
	
	public int getLevel(){
		return level;
	}
	
	
}
