package gameSlitherlink;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Rules.Cells;
import Rules.Rules;

public class Slitherlink extends JFrame{
	private static final Calendar Calender = null;
	private Cell[][] p;
	private JButton New = new JButton("New");
	private JButton Solve = new JButton("Solve");
	private int x = 20;
	private int y =70;
	private int size = 0;
	private int[][] matrix;
	private Rules rule;
	private static int i = 1;
	private boolean check = false;
	private JLabel label = new JLabel("Slitherlink");
	private int size_cell = 40;
	private String inputFile = "InputFile.cnf";
	private String outputFile = "OutputFile.cnf";
	public Slitherlink(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		setBackground(Color.BLACK);
		setVisible(true);
		this.add(New);
		this.add(Solve);
		this.add(label);
		label.setBounds(80,180,300, 100);
		label.setFont(new java.awt.Font("Tahoma", 0, 60));
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		New.setBounds(30, 20, 70, 30);
		Solve.setBounds(120, 20, 70, 30);
		setSize(500, 500);
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
		setLocationRelativeTo(null);
	}
	
	public void showResult(Cells[][] matrix){
		size = matrix.length;
		setSize((size+2)*size_cell-20, (size+2)*size_cell+60);
		p = new Cell[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				p[i][j] = new Cell();
				p[i][j].setHide(matrix[i][j].x[1], matrix[i][j].x[2], matrix[i][j].x[3], matrix[i][j].x[0]);
				p[i][j].setIndex(matrix[i][j].value);
				this.add(p[i][j]);
				p[i][j].setVisible(true);
				p[i][j].setBounds(x+j*size_cell,y+i*size_cell, size_cell, size_cell);
			}
			
		}
		
		
	}
	
	public void restart(){
		
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				
				p[i][j].setVisible(false);
				
			}
			
		}
	}

	public void showMatrix(int[][] matrix){
		size = matrix.length;
		setSize((size+2)*size_cell-20, (size+2)*size_cell+60);
		p = new Cell[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				p[i][j] = new Cell();
				p[i][j].setHide(false, false,false, false);
				p[i][j].setIndex(matrix[i][j]);
				this.add(p[i][j]);
				p[i][j].setVisible(true);
				p[i][j].setBounds(x+j*size_cell,y+i*size_cell, size_cell, size_cell);
			}
			
		}
		
		
	}
	
	public void NewActionPerformed(java.awt.event.ActionEvent evt){
		label.setVisible(false);
		if(size>0) restart();
		ReadFile read = new ReadFile();
		this.matrix = read.setMatric("Slitherlink.txt", i);
		i++;
		this.showMatrix(matrix);
		rule = new Rules(matrix);
	}
	
	public void SolveActionPerformed(java.awt.event.ActionEvent evt){
			restart();
			long beginSolve = Calender.getInstance().getTimeInMillis();
			try{
				rule.Doc_File_Ketqua(inputFile,outputFile);
	    	}catch (Exception e){
				e.getMessage();
			}
			this.showResult(rule.cell);
			long endSolve= Calender.getInstance().getTimeInMillis();
			System.out.println("Solve Time: " + (endSolve - beginSolve)+"ms");
	}
	
	public static void main(String[] args) {
			
			EventQueue.invokeLater(new Runnable() {
				
				public void run() {
						Slitherlink frame = new Slitherlink();
						frame.setVisible(true);
				}
			});
		}

	
	
}
