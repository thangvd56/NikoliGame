package LuatSudoku;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//import ReadFile.Readfile;
//import Rule.Luat;

public class Frame extends JFrame {
	protected static final Calendar Calender = null;
	private JPanel table = new JPanel();
	private JPanel[][] tableSudoku = new JPanel[6][6];
	private int x = 0;
	private int y = 0;
	private JTextField[][] Cell = new JTextField[26][26];
	//private JTextField time = new JTextField("");
	private JButton New = new JButton("New");
	private JButton Solve = new JButton("Solve");
	private JComboBox select = new JComboBox();
	private JLabel Label = new JLabel("Select");
	private JLabel Label1 = new JLabel("SUDOKU");
	private JLabel Label2 = new JLabel("Time_Solve");
	private static int lever9=1;
	private static int lever16=1;
	private static int lever25=1;
	private int[][] matrix;
	private int[][] result;
	String inputFile = "InputFile.cnf";
	String outputFile = "OutputFile.cnf";
	private Luat rule;
	public Time_Sudoku t = new Time_Sudoku();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		select.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"...", "9x9", "16x16", "25x25"}));
		this.add(Label1);
		setBounds(200, 30, 40*9+250, 40*9+60);
		Label1.setBounds(0, 0, 40*9+20, 40*9+20);
		Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		Label1.setFont(new java.awt.Font("Tahoma", 0, 40));
		Label1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		Label1.setVisible(true);
		this.add(New);
		this.add(Solve);
		this.add(select);
		this.add(Label);
		//this.add(time);
		New.setBounds(40*9+40, 40*9-50, 80, 30);
		Solve.setBounds(40*9+130, 40*9-50, 80, 30);
		Label.setBounds(40*9+50, 100, 80, 30);
		select.setBounds(40*9+130, 100, 80, 30);
		//time.setBounds(40*9+80, 40*9-10, 80, 30);
		setLocationRelativeTo(null);
		select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });
		New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewActionPerformed(evt);
            }
        });
		Solve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolveActionPerformed(evt);
            }
        });
	}
	public void setTable(int x, int y){
		this.x=x;
		this.y=y;
	}
	public void setFrame(){
		table.removeAll();
		this.add(table);
		table.setBounds(0, 0, 40*9+20, 40*9+20);
		Label1.setVisible(false);
		table.setVisible(true);
		int mid = (int) Math.sqrt(x);
		table.setLayout(new GridLayout(mid,mid));
		for(int i=0;i<mid;i++){
			for(int j=0;j<mid;j++){
				tableSudoku[i][j] = new JPanel();
				tableSudoku[i][j].setLayout(new GridLayout(mid,mid));
				table.add(tableSudoku[i][j]);
				tableSudoku[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
				
			}
		}
		
		if(x !=25){
			setSize(40*x+250, 40*x+60);
			table.setBounds(0, 0, 40*x+20, 40*x+20);
			New.setBounds(40*x+40, 40*x-50, 80, 30);
			Solve.setBounds(40*x+130, 40*x-50, 80, 30);
			select.setBounds(40*x+130, 100, 80, 30);
			Label.setBounds(40*x+50, 100, 80, 30);
		}
		else {
			setSize(40*16+250, 40*16+60);
			table.setBounds(0, 0, 40*16+20, 40*16+20);
			New.setBounds(40*16+40, 40*16-50, 80, 30);
			Solve.setBounds(40*16+130, 40*16-50, 80, 30);
			select.setBounds(40*16+130, 100, 80, 30);
			Label.setBounds(40*16+50, 100, 80, 30);
			
		}
		table.setBorder(new EmptyBorder(5, 5, 5, 5));
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				Cell[i][j] = new JTextField();
				//Cell[i][j].setText(String.valueOf(i));
				Cell[i][j].setHorizontalAlignment(JTextField.CENTER);
				tableSudoku[i/mid][j/mid].add(Cell[i][j]);
			}
		}
		select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });
		setLocationRelativeTo(null);
	}
	public int[][] setSudoku(int[][] matrix){
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				if(matrix[i][j]!=0) Cell[i][j].setText(String.valueOf(matrix[i][j]));
				else Cell[i][j].setText("");
				Cell[i][j].setHorizontalAlignment(JTextField.CENTER);
				
			}
		}
		return matrix;
	}
	private void selectActionPerformed(java.awt.event.ActionEvent evt) {                                           
		JComboBox select = (JComboBox) evt.getSource();
        String Select = (String) select.getSelectedItem();
        if(Select.equals("9x9")==true){
        	setTable(9,9);
        	setFrame();
        }
        else if(Select.equals("16x16")==true){
        	setTable(16,16);
        	setFrame();
        	
        }
        else if (Select.equals("25x25")==true){
        	setTable(25,25);
        	setFrame();
        	
        }
    }  
	private void NewActionPerformed(java.awt.event.ActionEvent evt) {
		
        if(x==9){
        	matrix = new int[9][9];
        	Readfile read = new Readfile();
        	matrix = setSudoku(read.setMatric("sudokus.9",lever9));
        	lever9++;
        	rule = new Luat(matrix);
    		try{
    			rule.writeToFile(inputFile);
    			rule.Doc_File_Ketqua(inputFile,outputFile);
        	}catch (Exception e){
    			e.getMessage();
    		}
        }
        else if(x==16){
        	matrix = new int[16][16];
        	Readfile read = new Readfile();
        	matrix = setSudoku(read.setMatric("sudokus.16",lever16));
        	lever16++;
        	rule = new Luat(matrix);
    		try{
    			rule.writeToFile(inputFile);
    			rule.Doc_File_Ketqua(inputFile,outputFile);
        	}catch (Exception e){
    			e.getMessage();
    		}
        }
        
        else if (x==25){
        	matrix = new int[25][25];
        	Readfile read = new Readfile();
        	matrix = setSudoku(read.setMatric("sudokus.25",lever25));
        	lever25++;
        	rule = new Luat(matrix);
    		try{
    			rule.writeToFile(inputFile);
    			rule.Doc_File_Ketqua(inputFile,outputFile);
        	}catch (Exception e){
    			e.getMessage();
    		}
        }
    }  
	private void SolveActionPerformed(java.awt.event.ActionEvent evt) {
		
		try{
			long beginsolve = Calender.getInstance().getTimeInMillis();
			setSudoku(rule.Doc_File_Ketqua(inputFile,outputFile));
			long endsolve = Calender.getInstance().getTimeInMillis();
			long solve_time = endsolve - beginsolve;
			System.out.println("Solve Time: " + solve_time+"ms");
			//time.setText(String.valueOf(solve_time)+" ms");
			//System.out.print(t.getminiSatResult());
    	}catch (Exception e){
			e.getMessage();
		}
	}
}
