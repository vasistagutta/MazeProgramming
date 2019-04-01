/*
 * CSCI 502 - JAVA Section 2
 * Assignment 3 - Read, draw and solving mazes
 * Name - Srikanth Reddy Nagidi (Z1836478)
 * 		- Himamounisha Boggavarapu (Z1840235)
 * 
 * Class - Maze
 */

import java.io.*;
import java.util.Scanner;

import java.awt.*;

public class Maze {
	// maximum rows and columns of the maze used in calculating the panel size of the application
	static int MAX_ROWS = 50;
	static int MAX_COLS = 50;
	// start row and column, end row and column for the path in Maze
	int startRow, endRow;
	int startCol, endCol;
	
	MazeSquare [][] objects; // 2D array to store MazeSquares
	
	// returns number of rows in maze
	public int getNumberOfRows() {
		return objects.length;
	}
	//returns number of columns in maze
	public int getNumberOfCols() {
		return objects[0].length;
	}
	
	// reading file and creating MazeSquare objects in storing in 2D array created above
	// file has number of Rows and columns of the Maze in first and second row of the maze
	// the file also gives the starting square and ending square of the Maze
	public void readMaze(File inputFile) throws FileNotFoundException, NumberFormatException {
		Scanner readFile = new Scanner (inputFile);
		int numberOfRows = Integer.parseInt(readFile.nextLine());
		int numberOfCols = Integer.parseInt(readFile.nextLine());
		
		objects = new MazeSquare [numberOfRows][numberOfCols];
		for (int i=0; i<numberOfRows; i++) {
			String line = readFile.nextLine();
			char [] eachRow = line.toCharArray();
			for  (int j=0; j<numberOfCols; j++) {
				MazeSquare.SquareType type =null;
				if (eachRow[j] == '#') {
					type = MazeSquare.SquareType.WALL;
				}
				else if (eachRow[j]=='.') {
					type = MazeSquare.SquareType.SPACE;
				}
				else if (eachRow[j] == 's'){
					startRow = i;
					startCol = j;
					type = MazeSquare.SquareType.SPACE;
				}
				else if (eachRow[j]=='e') {
					endRow = i;
					endCol = j;
					type = MazeSquare.SquareType.SPACE;
				}else {
					readFile.close();
					throw new NumberFormatException("File is in wrong fromat");
				}
				objects[i][j] = new MazeSquare (i, j,type);
			}
		}
		readFile.close();
	}
	// method to clear path of the maze
	// it calls clearSquare Method of each object of MazeSquare in 2D array
	public void clearMazePath() {
		for (MazeSquare[] i : objects)
			for (MazeSquare j : i) j.clearSquare();
	}
	
	// method to draw Maze
	// this method calls drawSquare method for each object in MazeSquare 2D array
	public void drawMaze (Graphics g, int startX, int startY) {
		for (MazeSquare[] i : objects)
			for (MazeSquare j : i) j.drawSquare(g, startX, startY);
	}
	// method to solve the maze
	// this method calls private method solveMaze(startRow, startCol) mentioned bellow
	public boolean solveMaze() {
		return solveMaze(startRow, startCol);
	}
	
	// private method to solve the maze
	// it starts at MazeSquare[startRow][startCol] moves north, south, east and west directions until
	//												finds the ending MazeSquare
	// it uses backtracking methodology to solve the maze
	// if there is path from starting MazeSquare and ending MazeSquare it turns all the mazeSquare in
	// 									the path to red in color using .setToPath() method
	// if path is found it returns true else false
	// 
	private boolean solveMaze(int row, int column) {
		// first base case for this recursive method
		// it stops and returns true if it reaches ending MazeSquare
		if (row == endRow && column == endCol) {
			objects[row][column].setToPath();
			objects[startRow][startCol].setToPath();
			return true;  
		}
		// Base case:-  returns false if the square is visited or if its a wall
		if (objects[row][column].isWall() || objects[row][column].getVisited()) {
			return false;
		}
		objects[row][column].markVisited(); // marking the current mazesquare to visited
		// logic to move south
		if (row != 0 && row<this.getNumberOfRows()-1) {
			if (this.solveMaze(row-1, column)) {
				objects[row-1][column].setToPath();
				return true;
			}
		}
		// logic to move north
		if (row != this.getNumberOfRows()-1 && row<this.getNumberOfRows()-1) {
			if (this.solveMaze(row+1, column)) {
				objects[row+1][column].setToPath();
				return true;
			}
		}
		// logic to move east
		if (column != 0 && column<this.getNumberOfCols()-1) {
			if (this.solveMaze(row, column-1)) {
				objects[row][column-1].setToPath();
				return true;
			}
		}
		//logic to move west
		if (column != this.getNumberOfCols()-1 && column <this.getNumberOfCols()-1) {
			if (this.solveMaze(row, column+1)) {
				objects[row][column+1].setToPath();
				return true;
			}
		}
		return false;
	}
}
