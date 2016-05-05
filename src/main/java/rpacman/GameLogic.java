package rpacman;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
	private int[][] positions;
	private int charx;
	private int chary;
	FileHandler fh = new FileHandler();
	private Board board;
	private int numberOfHealth;
	protected List<Monster> monsters;
	protected List<Item> items;
	protected Character character;
	private Random random = new Random();
	public static final int treasureNumber = 20;
	public static final int characterNumber = 19;
	public static final int stairsNumber = 18;
	public static final int healthNumber = 17;
	private static final int seeLimit = 4;
	public static final int monsterStep = 4;
	
	public GameLogic(Board board) {
		this.board = board;
		
		List<String> l = getLogic(1);
		monsters = new ArrayList<Monster>();
		items = new ArrayList<Item>();
		setPositions(l);
	}

	
	//betölti a fájlból a pályát egy listába
	private List<String> getLogic(int i) {
		List<String> l = null;
		try {
			l = fh.returnFile("level"+i+".txt");
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return l;
	}
	
	//a listából átírja az értékeket egy mátrixba
	private void setPositions(List<String> l) {
		int size = l.size();
		positions = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			String[] s = l.get(i).split(" ");
			for (int j = 0; j < size; j++) {
				
				int placenumber = Integer.parseInt(s[j]);
				positions[j][i] = placenumber;
				if(Integer.parseInt(s[j]) == characterNumber){
					
					charx = j;
					chary = i;
				}
				if(placenumber >=2 && placenumber <= 16){
					Monster m = new Monster(placenumber);
					m.setXPos(j*40+1);
					m.setYPos(i*40 +1);
					monsters.add(m);
				}

			}
		}
	}
	
	//Megnézi hogy a karakter belement e valamilyen szörnybe, és ha igen akkor visszaadja a szörnyet.
	public Monster collide(){

		for(Monster monster: monsters){
			
			Rectangle r1 = monster.getBounds();
			Rectangle r2 = character.getBounds();
			if(r2.intersects(r1)){
				return monster;
			}
			
		}
		
		return null;
		
	}
	
	//megnézi egy lény következõ lépésének eredményét az iránya alapján
	public int outcomeOfNextStep(Creature creature, String direction) {

		Point nextp = creature.getNextPosition(direction);
		if(nextp.x < positions.length && nextp.x >=0 && 
				nextp.y < positions.length && nextp.y >=0){ // ha nem megyünk le a pályáról
	
			return positions[nextp.x][nextp.y]; // akkor visszaadjukhogy mi van ott
		}
		else return -1;
	}

	public void play(){
		moveMonsters();
		moveCharacter();
	}
	

	public void moveMonsters(){
		for(Monster monster : monsters){
	        monster.move();
	        boolean canSee = canSee(character, monster);
	        int outcome = outcomeOfNextStep(monster, monster.getDirection());
	        
	        if(!canSee){
		        if(monster.isInPlace()){
		        	if(outcome != 1 && outcome  != -1){
		        		monster.setDxDy();
		        	}
		        	
		        	ArrayList<String> moves = canMove(monster);
		        	if(outcome == 1 || outcome == -1 ){		        		
		        		monster.setDirection(moves.get(random.nextInt(moves.size())));
		        		monster.setDxDy();

		        	}

				}
	        }
	        else{
	        	monster.dx = 0;
	        	monster.dy = 0;
	        }

		}
	}
	
	public int moveCharacter(){
		
		int outcome = outcomeOfNextStep(character, character.getDirection());
		
		Monster monster = collide();
		
		if((monster != null)){
			//board.setMonsterToFightWith(monster);
			monsters.remove(monster);
		}
		
		if(character.isInPlace()){
			if(getPlaceNumberFromPosition(character) == stairsNumber){
				goNextLevel();
			}
			
			if(getPlaceNumberFromPosition(character) == healthNumber){
				Point p = character.getPosition();
				character.setCurrentHealth(30);
				positions[p.x][p.y] = 0;
			}
			
			if(getPlaceNumberFromPosition(character) == treasureNumber){
				board.setGameWon(true);
			}
		}
		
		if(character.isInPlace()){
			if(outcome != 1 && outcome  != -1){
				character.setDxDy();

			}
			
		}
		
		character.move();
		return 0;
	}


	

	private void goNextLevel() {
		character.increaseOnLevel();
		monsters = new ArrayList<Monster>();
		items = new ArrayList<Item>();
		List<String> l = getLogic(character.getOnlevel());
		setPositions(l);
		
	}
	
	//milyen irányokba tud mozogni egy szörny
	public ArrayList<String> canMove(Monster monster){

		ArrayList<String> l = new ArrayList<String>();
		String[] directions = monster.getDirections(); 

		for (int i = 0; i < directions.length; i++) {

			if(outcomeOfNextStep(monster, directions[i])!= 1 && outcomeOfNextStep(monster, directions[i])!= -1){
				l.add(directions[i]);
			}
		}
		
		if(l.size()>1){
			int chance = random.nextInt(100) + 1;
			if(chance >= 0){
				System.out.println(monster.getDirection());
				System.out.println(monster.getPreviousdirection());
				l.remove(monster.getPreviousdirection());
			}
		}
		
		return l;
		
	}
	
	
	public int getObjectFromLogic(int x, int y){
		return positions[x][y];
	}
	
	public void getNewCharacter(int characterclass){
		character =  new Character(characterclass, charx*40+1, chary*40+1);
	}
	
	public int getPlaceNumberFromPosition(Creature creature){
		Point p = creature.getPosition();
		return positions[p.x][p.y];
	}

	
	public void changeDirection(){
		boolean[] pressed = character.getKeypressed();
		if(pressed[0] == true){
			character.setDirection("WEST");
			character.addPressedDirection("WEST");
		}
		else if(pressed[1] == true){
			character.setDirection("EAST");
			character.addPressedDirection("EAST");
		}
		else if(pressed[2] == true){
			character.setDirection("NORTH");
			character.addPressedDirection("NORTH");
		}
		else if(pressed[3] == true){
			character.setDirection("SOUTH");
			character.addPressedDirection("SOUTH");
		}
	}
	
	
	private boolean canSee(Creature c1, Creature c2){
		Point p1 = c1.getPosition();
		Point p2 = c2.getPosition();
		
		int range = Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
		if(range < seeLimit){
			return true;
		}
		else {
			return false;
		}
		
	}

}
