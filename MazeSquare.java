/*
 * CSCI 502 - JAVA Section 2
 * Assignment 3 - Read, draw and solving mazes
 * Name - Srikanth Reddy Nagidi (Z1836478)
 * 		- Himamounisha Boggavarapu (Z1840235)
 * 
 * Class - MazeSquare
 */

import java.awt.*;

public class MazeSquare {
	
	// enum for the type of the maze square -  wall, space, path
	public enum SquareType{
		WALL, SPACE, PATH;
	}
	//dimension of each square is 15*15 pixels
	static final int squareDimension = 15 ;
	//row and column number Maze Square
	private int rows, columns;
	
	//type of the square - Wall, Space, Path
	private SquareType type;
	private boolean visited = false; // this variable helps in solving the maze
	
	//constructor for the MazeSquare
	MazeSquare(int rows, int columns, SquareType type){
		this.rows =rows;
		this.columns = columns;
		this.type = type;
	}
	//this method clears the path of the square
	public void clearSquare() {
		visited = false;
		if (type == SquareType.PATH) {
			type = SquareType.SPACE;
		}
	}
	// if mazeSquare is visited it sets the visited variable to true
	public void markVisited() {
		visited = true;
	}
	//returns if the mazeSquare is visited
	public boolean getVisited() {
		return visited;
	}
	// returns if the square is Wall
	public boolean isWall() {
		return (type == SquareType.WALL); 
	}
	// this method sets the square to path --> it sets type to Path
	public void setToPath() {
		type = SquareType.PATH;
	}
	
	// method to paint the square
	// startX and startY are the coordinates of top and left corner of Maze
	// color of the Square is
	//						- Black if Square is Wall
	//						- White if Square is Space
	//						- Red if Square is in Path
	// each square coordinates are startX+(columns*squareDimension), startY+(rows*squareDimension)
	public void drawSquare(Graphics g, int startX, int startY) {
		Color c ;
		if (type == SquareType.WALL) {
			c= Color.darkGray;
		}else if (type == SquareType.SPACE) {
			c = Color.WHITE;
		}else {
			c = Color.RED;
		}
		g.setColor(c);
		g.fillRect(startX+(columns*squareDimension), startY+(rows*squareDimension), squareDimension, squareDimension);
		g.setColor(Color.BLACK);
		g.drawRect(startX+(columns*squareDimension), startY+(rows*squareDimension), squareDimension, squareDimension);
	}

}
