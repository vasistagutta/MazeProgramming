/*
 * CSCI 502 - JAVA Section 2
 * Assignment 3 - Read, draw and solving mazes
 * Name - Srikanth Reddy Nagidi (Z1836478)
 * 		- Himamounisha Boggavarapu (Z1840235)
 * 
 * Class - MazeApp
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.awt.*;

public class MazeApp extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	MazePanel mazePanel = new MazePanel(); // a reference to MazePanel Object
	
	//Buttons for the panel
	private JButton chooseFile = new JButton ("<html><font color=black>Open Maze File</font></html>");
	private JButton solveMaze = new JButton("<html><font color=black>Solve Maze</font></html>");
	private JButton clearSolution = new JButton ("<html><font color=black>Clear Solution</font></html>");
	
	//constructor for the title of the application
	private MazeApp(String title) {
		super(title);
	}
	
	// method for performing action for the above mentioned JButtons
	@Override
	public void actionPerformed(ActionEvent e) {
		//choosing the maze file from the computer directory
		//JFile chooser will help in choosing the directory
		if (e.getSource() == chooseFile) {
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int r = j.showSaveDialog(null);
			if (r==JFileChooser.APPROVE_OPTION) {
				//getting absolute path of the text file
				File selectedFile = new File (j.getSelectedFile().getAbsolutePath());
				try {
					//reading the maze file
					mazePanel.readMaze(selectedFile);
					// solve maze button is enabled after reading and painting the maze in the panel
					solveMaze.setEnabled(true);
				}catch (Exception ex){
					// to display any exceptions thrown
					JOptionPane.showMessageDialog(this, "check the input file, its not in right format", 
							" error", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		}
		//solving the maze upon clicking the solveMaze Button
		//the path if found is painted in red color
		else if (e.getSource() == solveMaze) {
			mazePanel.solveMaze();
			solveMaze.setEnabled(false);
			clearSolution.setEnabled(true);
		}
		//if clear Solution button is pressed the path painted in red color is changed back to White color
		else {
			mazePanel.clearMazePath();
			solveMaze.setEnabled(true);
			clearSolution.setEnabled(false);
		}
		
	}
	//setting up the frame
	public void createAndShowGUI() {
		initComponents(); // adding necessary components to the panel
		
		// setting the location of the panel on the screen relative to size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
	    this.setLocation(width/5, height/5); 
		
	    //action listeners for the JButtons
		chooseFile.addActionListener(this);
		solveMaze.addActionListener(this);
		clearSolution.addActionListener(this);		
		
		//Display the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
	}
	
	private void initComponents() {
		//Initializing the panel, setting border, and size of the panel
		final JPanel components = new JPanel(new BorderLayout());
		//Dimension of the panel is set based on MazeSquare size and Maximum number of rows and cols of Maze
		Dimension panelSize = new Dimension (Maze.MAX_ROWS*MazeSquare.squareDimension, 
				Maze.MAX_COLS*MazeSquare.squareDimension);
		components.setPreferredSize(panelSize);
		components.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		components.add(mazePanel, BorderLayout.CENTER); // adding the mazePanel object to the panel
		
		//a new panel for the buttons in FlowLayout
		JPanel controls = new JPanel (new FlowLayout());
		
		//adding all the three buttons to the controls panel
		controls.add(chooseFile);
		chooseFile.setEnabled(true);
		Dimension buttonSize = new Dimension(MazeSquare.squareDimension*Maze.MAX_COLS/4,
				MazeSquare.squareDimension*2);
		chooseFile.setPreferredSize(buttonSize);
		Font font = new Font("Arial", Font.BOLD, MazeSquare.squareDimension);
		chooseFile.setFont(font);
		
		controls.add(solveMaze);
		solveMaze.setEnabled(false); // initially solveMaze button is not enabled
		solveMaze.setPreferredSize(buttonSize);
		solveMaze.setFont(font);
		
		controls.add(clearSolution);
		clearSolution.setEnabled(false); // initially clearButton is not enabled
		clearSolution.setPreferredSize(buttonSize);
		clearSolution.setFont(font);
		
		components.add(controls, BorderLayout.SOUTH); // adding controls to component panel at south of the panel
		add(components, BorderLayout.CENTER); // adding components panel to the application frame
	}
	
	//main method for the application
	public static void main (String [] args) {
		MazeApp app = new MazeApp("Maze Solver");
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
                | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
		//calling the GUI application method (lambda expression)
		EventQueue.invokeLater(() -> {
			app.createAndShowGUI();
		});
		
	}
	
}
