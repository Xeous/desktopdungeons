package com.xeous.DesktopDungeons;

import java.util.*;

//http://www.jonathanzong.com/blog/2012/11/06/maze-generation-with-prims-algorithm

public class Prim {
	public static final int MAZE_WIDTH =20;
	public static final int MAZE_HEIGHT =20;


    public static char[][] createMaze()
    {
    	// dimensions of generated maze

 
    	// build maze and initialize with only walls
        StringBuilder s = new StringBuilder(MAZE_HEIGHT);
        for(int x=0;x< MAZE_HEIGHT;x++)
        	s.append('*');
        char[][] maze = new char[MAZE_WIDTH][MAZE_HEIGHT];
        for(int x=0;x< MAZE_WIDTH;x++) maze[x] = s.toString().toCharArray();
 
        // select random point and open as start node
        Point st = new Point((int)(Math.random()* MAZE_WIDTH),(int)(Math.random()* MAZE_HEIGHT),null);
        maze[st.r][st.c] = 'S';
 
        // iterate through direct neighbors of node
        ArrayList<Point> frontier = new ArrayList<Point>();
        for(int x=-1;x<=1;x++)
        	for(int y=-1;y<=1;y++){
        		if(x==0&&y==0||x!=0&&y!=0)
        			continue;
        		try{
        			if(maze[st.r+x][st.c+y]=='.') continue;
        		}catch(Exception e){ // ignore ArrayIndexOutOfBounds
        			continue;
        		}
        		// add eligible points to frontier
        		frontier.add(new Point(st.r+x,st.c+y,st));
        	}
 
        Point last=null;
        while(!frontier.isEmpty()){
 
        	// pick current node at random
        	Point cu = frontier.remove((int)(Math.random()*frontier.size()));
        	Point op = cu.opposite();
        	try{
        		// if both node and its opposite are walls
        		if(maze[cu.r][cu.c]=='*'){
        			if(maze[op.r][op.c]=='*'){
 
        				// open path between the nodes
        				maze[cu.r][cu.c]='.';
        				maze[op.r][op.c]='.';
 
        				// store last node in order to mark it later
        				last = op;
 
        				// iterate through direct neighbors of node, same as earlier
        				for(int x=-1;x<=1;x++)
				        	for(int y=-1;y<=1;y++){
				        		if(x==0&&y==0||x!=0&&y!=0)
				        			continue;
				        		try{
				        			if(maze[op.r+x][op.c+y]=='.') continue;
				        		}catch(Exception e){
				        			continue;
				        		}
				        		frontier.add(new Point(op.r+x,op.c+y,op));
				        	}
        			}
        		}
        	}catch(Exception e){ // ignore NullPointer and ArrayIndexOutOfBounds
        	}
 
        	// if algorithm has resolved, mark end node
        	if(frontier.isEmpty())
        		maze[last.r][last.c]='E';
        }
 
		// print final maze
		for(int i=0;i< MAZE_WIDTH;i++){
			for(int j=0;j< MAZE_HEIGHT;j++)
				System.out.print(maze[i][j]);
			System.out.println();
		}
		
		return maze;
    }
 
    static class Point{
    	Integer r;
    	Integer c;
    	Point parent;
    	public Point(int x, int y, Point p){
    		r=x;c=y;parent=p;
    	}
    	// compute opposite node given that it is in the other direction from the parent
    	public Point opposite(){
    		if(this.r.compareTo(parent.r)!=0)
    			return new Point(this.r+this.r.compareTo(parent.r),this.c,this);
    		if(this.c.compareTo(parent.c)!=0)
    			return new Point(this.r,this.c+this.c.compareTo(parent.c),this);
    		return null;
    	}
    }
}
