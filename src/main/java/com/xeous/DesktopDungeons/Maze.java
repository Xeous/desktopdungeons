package com.xeous.DesktopDungeons;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gabor on 2014.12.19..
 */
public class Maze {

    public static final int MAZE_WIDTH = Board.NUMBER_OF_CUBES;
    public static final int MAZE_HEIGHT = Board.NUMBER_OF_CUBES;
    private Board board;

    Block[][] maze;

    public Maze(Board board){
        this.board = board;
    }


    public void createMaze(){
        maze = new Block[MAZE_WIDTH][MAZE_HEIGHT];

        initWalls(maze);

        Node startNode = setStartNode(maze);

        ArrayList<Node> frontier = new ArrayList<>();

        addToFrontier(startNode, frontier);

        Node last = null;

        while(!frontier.isEmpty()){
            Node current = frontier.remove((int)(Math.random()*frontier.size()));
            Node opposite = current.opposite();

            last = openNewWall(frontier, last, current, opposite);

            findEndNode(frontier, last);

        }
    }

    private Node openNewWall(ArrayList<Node> frontier, Node last, Node current, Node opposite) {
        try{
            if(maze[current.posX][current.posY].getType().equals("*")) {
                if (maze[opposite.posX][opposite.posY].getType().equals("*")) {

                    maze[current.posX][current.posY].setType(".");


//                    System.out.println("exception1");
//                    try {
//                        System.out.println("exception3");
//                        Thread.sleep(100);
//                        board.repaint();
//                    } catch (InterruptedException e) {
//                        System.out.println("exception2");
//                        e.printStackTrace();
//                    }

                    maze[opposite.posX][opposite.posY].setType(".");

                    last = opposite;

                    addToFrontier(opposite, frontier);
                }
            }

        }catch(Exception e){
        }
        return last;
    }

    private void findEndNode(ArrayList<Node> frontier, Node last) {
        if(frontier.isEmpty()){
            maze[last.posX][last.posY].setType("E");
        }
    }

    private void addToFrontier(Node startNode, ArrayList<Node> frontier) {
        for(int x=-1;x<=1;x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0){
                    continue;
                }
                try {
                    if (maze[startNode.posX + x][startNode.posY + y].getType().equals(".")) {
                        continue;
                    }
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                frontier.add(new Node(startNode.posX + x, startNode.posY + y, startNode));
            }
        }
    }

    public void drawMaze(Graphics g){
        for(int i=0;i< MAZE_WIDTH;i++){
            for(int j=0;j< MAZE_HEIGHT;j++){
                System.out.print(maze[i][j].getType());
                maze[i][j].drawBlock(g);
            }

            System.out.println();
        }
    }

    private static Node setStartNode(Block[][] maze) {
        Node startNode = new Node((int)(Math.random()*MAZE_WIDTH), (int)(Math.random()*MAZE_HEIGHT), null);
        maze[startNode.posX][startNode.posY].setType("S");

        return startNode;
    }

    private static void initWalls(Block[][] maze) {
        for(int i=0; i<MAZE_WIDTH; i++){
            for(int j=0; j<MAZE_HEIGHT; j++){
                maze[i][j]=new Block(new Point(i,j));
                maze[i][j].setGameElement(new Wall());
                maze[i][j].setType("*");
            }
        }
    }


}
