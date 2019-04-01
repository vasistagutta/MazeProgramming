/*
 * CSCI 502 - JAVA Section 2
 * Assignment 3 - Read, draw and solving mazes
 * Name - Srikanth Reddy Nagidi (Z1836478)
 * 		- Himamounisha Boggavarapu (Z1840235)
 * 
 *  Class - MazePanel
 */
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class MazePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	// reference to maze object
	Maze maze = null;
	// variables to track if solution attempted and solution found
	private boolean solutionAttempted = false, solutionFound = false;
	
	//method to readMaze from the file
	//it gets input file from the MazeApp class - JFileChooser
	//it sets both solution attempted and solution found to false and call repaint at the end
	public void readMaze(File inputFile) throws FileNotFoundException, NumberFormatException{
		
		solutionAttempted = false;
		solutionFound = false;
		
		maze = new Maze();
		try {
			maze.readMaze(inputFile);
			this.repaint();
		}catch (Exception ex) {
			throw ex;
		}
	}
	
	//method to call clearing maze path
	public void clearMazePath() {
		solutionAttempted = false;
		solutionFound = false;
		
		maze.clearMazePath();
		this.repaint();
	}
	//method to solve maze. 
	// it sets solution attempted to true and solution found = calling method solveMaze() from Maze
	public void solveMaze() {
		solutionAttempted = true;
		solutionFound = maze.solveMaze();
		this.repaint();
	}
	
	// paint component to drawMaze
	// it fills the panel with light Gray color
	// it calls the drawMaze method of the maze object.
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = this.getSize();
		g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, (int)d.getWidth(), (int)d.getHeight());
        if (maze != null) {
        	// it calculates the top and left corner of the maze as below
            int left =((int)d.getWidth() - maze.getNumberOfCols()*MazeSquare.squareDimension)/2;
            int top = ((int)d.getHeight() - maze.getNumberOfRows()*MazeSquare.squareDimension)/2;
            
        	maze.drawMaze(g, top, left);
        	
        	// drawing a string "solved" if solution is found
        	if  (solutionAttempted && solutionFound) {
            	g.setColor(Color.BLACK);
            	g.setFont(new Font("Arial", Font.BOLD, 20));
            	g.drawString("Solved", top+maze.getNumberOfCols()*MazeSquare.squareDimension/2,
            			left+(maze.getNumberOfRows()+3)*MazeSquare.squareDimension);
            }
        	// drawing a string "solution doesn't exists if solution found is false"
            if (solutionAttempted && !solutionFound ) {
            	g.setColor(Color.BLACK);
            	g.setFont(new Font("Arial", Font.BOLD, 20));
            	g.drawString("No solution exists for this maze", top
            			, left+(maze.getNumberOfRows()+3)*MazeSquare.squareDimension);
            }
        }
	}
}
