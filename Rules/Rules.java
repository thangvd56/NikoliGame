package Rules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import BitEncoding.Bit_Encoding;

public class Rules{
	private Bit_Encoding bit = new Bit_Encoding();
	private int dem = 0;
	private int[][] Matrix;
	private int i,j,k;
	private int size;
	private int value;
	private int[][][] Line_Matrix;
	private int[][][] result;
	private ArrayList<String> Rules = new ArrayList<String>();
	private StringBuilder rule = new StringBuilder();
	private static int id = 0;
	public Cells[][] cell;
	public Rules(){
		Matrix = null;
		Line_Matrix = null;
		i=j=k=0;
	}
	public int[][] getMatrix(){
		return Matrix;
	}
	public Rules(int[][] matrix){
		this.Matrix = matrix;
		size = Matrix.length+1;
		value = 1;
		Line_Matrix = new int[size+1][size+1][25];
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++)
				for(k=1;k<=8;k++){
					
					Line_Matrix[i][j][k] = value;
					value++;
					if(i==1){
						Line_Matrix[i][j][2] = 0;
						Line_Matrix[i][j][6] = 0;
					}
					if(i==size){
						Line_Matrix[i][j][4] = 0;
						Line_Matrix[i][j][8] = 0;
					}
					if(j==1){
						Line_Matrix[i][j][1] = 0;
						Line_Matrix[i][j][5] = 0;
					}
					if(j==size){
						Line_Matrix[i][j][3] = 0;
						Line_Matrix[i][j][7] = 0;
					}
				}
		
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++){
				for(k=9;k<=24;k++){
					Line_Matrix[i][j][k] = value;
					value++;
				}
			}
		value--;  
	}
	
	public void add_rule(StringBuilder rule, ArrayList<String> rules){
		rules.add(rule.toString());
    	rule.setLength(0);
    	id++;
	}

	public void more_one_line_in(){//có nhiều nhất một đường vào
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++)
				for(k=1;k<=3;k++){
					for(int h = k+1;h<=4;h++){
						if(Line_Matrix[i][j][k]>0 && Line_Matrix[i][j][h]>0){
							rule.append("-"+Line_Matrix[i][j][k]+" -"+Line_Matrix[i][j][h]);
							add_rule(rule,Rules);
						}
					}
				}
	}
	
	public void more_one_line_out(){//có nhiều nhất một đường ra.
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++)
				for(k=5;k<=7;k++){
					for(int h = k+1;h<=8;h++){
						if(Line_Matrix[i][j][k]>0 && Line_Matrix[i][j][h]>0){
							rule.append("-"+Line_Matrix[i][j][k]+" -"+Line_Matrix[i][j][h]);
							add_rule(rule,Rules);
						}
					}
				}
	}
	
	public void out_vectex_is_in_vectex(){//vào của đường này là ra của đường trước đó.
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++){
				if(j>1){
					rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j-1][7]);
					add_rule(rule,Rules);
					rule.append(Line_Matrix[i][j][1]+" -"+Line_Matrix[i][j-1][7]);
					add_rule(rule,Rules);
				}
				if(i>1){
					rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i-1][j][8]);
					add_rule(rule,Rules);
					rule.append(Line_Matrix[i][j][2]+" -"+Line_Matrix[i-1][j][8]);
					add_rule(rule,Rules);
				}
				if(j<size){
					rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j+1][5]);
					add_rule(rule,Rules);
					rule.append(Line_Matrix[i][j][3]+" -"+Line_Matrix[i][j+1][5]);
					add_rule(rule,Rules);
				}
				if(i<size){
					rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i+1][j][6]);
					add_rule(rule,Rules);
					rule.append(Line_Matrix[i][j][4]+" -"+Line_Matrix[i+1][j][6]);
					add_rule(rule,Rules);
				}
			}
	}
	
	public void in_or_out(){//nếu đường này là vào thì không thể là đường ra của nó.
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++)
				for(k=1;k<=4;k++){
					if(Line_Matrix[i][j][k]>0){
						rule.append("-"+Line_Matrix[i][j][k]+" -"+Line_Matrix[i][j][k+4]);
						add_rule(rule,Rules);
					}
				}
	}
	
	public void line(){// vao 1 phia ph ra o cac phia con lai
		
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++){
				if(i==1&&j==1){
					rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i][j][7]);
					add_rule(rule,Rules);
				}
				else if(i==1&&j==size){
					rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i][j][5]);
					add_rule(rule,Rules);
				}
				else if(i==size&&j==1){
					rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i][j][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j][6]);
					add_rule(rule,Rules);
				}
				else if(i==size&&j==size){
					rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j][6]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i][j][5]);
					add_rule(rule,Rules);
				}
				else{
					if(Line_Matrix[i][j][1]==0){
						
						rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i][j][7]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j][6]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i][j][6]+" "+Line_Matrix[i][j][7]);
						add_rule(rule,Rules);
						
					}
					else if(Line_Matrix[i][j][2]==0){
						rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j][7]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][7]);
						add_rule(rule,Rules);
					}
					else if(Line_Matrix[i][j][3]==0){
						rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j][6]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][6]);
						add_rule(rule,Rules);
					}
					else if(Line_Matrix[i][j][4]==0){
						rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j][7]+" "+Line_Matrix[i][j][6]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][7]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][6]);
						add_rule(rule,Rules);
					}
					else{
						rule.append("-"+Line_Matrix[i][j][1]+" "+Line_Matrix[i][j][6]+" "+Line_Matrix[i][j][7]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][2]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][7]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][3]+" "+Line_Matrix[i][j][6]+" "+Line_Matrix[i][j][5]+" "+Line_Matrix[i][j][8]);
						add_rule(rule,Rules);
						rule.append("-"+Line_Matrix[i][j][4]+" "+Line_Matrix[i][j][6]+" "+Line_Matrix[i][j][7]+" "+Line_Matrix[i][j][5]);
						add_rule(rule,Rules);
					}
				}
			}
				
	}
	
	public void edges(){
		int[] x = new int[9];
		
		for(int i=0;i<size-1;i++){
			for(int j=0;j<size-1;j++){
				if(Matrix[i][j]==0){
					
					rule.append("-"+Line_Matrix[i+2][j+1][2]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][1]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][4]);
					add_rule(rule,Rules);
					
					rule.append("-"+Line_Matrix[i+2][j+1][6]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					
				}
				else if(Matrix[i][j]==1){
					rule.append(Line_Matrix[i+2][j+1][2]+" "+Line_Matrix[i+2][j+1][3]+" "+Line_Matrix[i+1][j+2][1]+" "+Line_Matrix[i+1][j+2][4]+" "
					+Line_Matrix[i+2][j+1][6]+" "+Line_Matrix[i+2][j+1][7]+" "+Line_Matrix[i+1][j+2][5]+" "+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+2][j+1][3]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+1][j+2][1]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+1][j+2][4]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]+" -"+Line_Matrix[i+1][j+2][1]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]+" -"+Line_Matrix[i+1][j+2][4]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][1]+" -"+Line_Matrix[i+1][j+2][1]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+2][j+1][6]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+2][j+1][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][2]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]+" -"+Line_Matrix[i+2][j+1][6]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]+" -"+Line_Matrix[i+2][j+1][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]+" -"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][3]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][1]+" -"+Line_Matrix[i+2][j+1][6]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][1]+" -"+Line_Matrix[i+2][j+1][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][1]+" -"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][1]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][4]+" -"+Line_Matrix[i+2][j+1][6]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][4]+" -"+Line_Matrix[i+2][j+1][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][4]+" -"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][4]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][6]+" -"+Line_Matrix[i+2][j+1][7]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][6]+" -"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][6]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][7]+" -"+Line_Matrix[i+1][j+2][5]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+2][j+1][7]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
					rule.append("-"+Line_Matrix[i+1][j+2][5]+" -"+Line_Matrix[i+1][j+2][8]);
					add_rule(rule,Rules);
				}
				else if(Matrix[i][j]==3){
					x[1] = Line_Matrix[i+2][j+1][2];
					x[2] = Line_Matrix[i+2][j+1][3];
					x[3] = Line_Matrix[i+1][j+2][1];
					x[4] = Line_Matrix[i+1][j+2][4];
					x[5] = Line_Matrix[i+2][j+1][6];
					x[6] = Line_Matrix[i+2][j+1][7];
					x[7] = Line_Matrix[i+1][j+2][5];
					x[8] = Line_Matrix[i+1][j+2][8];
					rule.append("-"+x[1]+" -"+x[2]+" -"+x[3]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[6]+" -"+x[3]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[5]+" -"+x[2]+" -"+x[3]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[5]+" -"+x[6]+" -"+x[3]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[1]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[1]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[5]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[5]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[1]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[1]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[5]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[5]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[1]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[1]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[5]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[5]+" -"+x[6]);
					add_rule(rule,Rules);
					
					rule.append(x[4]+" "+x[8]+" "+x[3]+" "+x[7]);
					add_rule(rule,Rules);
					rule.append(x[4]+" "+x[8]+" "+x[1]+" "+x[5]);
					add_rule(rule,Rules);
					rule.append(x[4]+" "+x[8]+" "+x[2]+" "+x[6]);
					add_rule(rule,Rules);
					rule.append(x[3]+" "+x[7]+" "+x[1]+" "+x[5]);
					add_rule(rule,Rules);
					rule.append(x[3]+" "+x[7]+" "+x[2]+" "+x[6]);
					add_rule(rule,Rules);
					rule.append(x[1]+" "+x[5]+" "+x[2]+" "+x[6]);
					add_rule(rule,Rules);
					
				}
				else if(Matrix[i][j] == 2){
					x[1] = Line_Matrix[i+2][j+1][2];
					x[2] = Line_Matrix[i+2][j+1][3];
					x[3] = Line_Matrix[i+1][j+2][1];
					x[4] = Line_Matrix[i+1][j+2][4];
					x[5] = Line_Matrix[i+2][j+1][6];
					x[6] = Line_Matrix[i+2][j+1][7];
					x[7] = Line_Matrix[i+1][j+2][5];
					x[8] = Line_Matrix[i+1][j+2][8];
					rule.append(x[1]+" "+x[5]+" "+x[2]+" "+x[6]+" "+x[3]+" "+x[7]);
					add_rule(rule,Rules);
					rule.append(x[1]+" "+x[5]+" "+x[2]+" "+x[6]+" "+x[4]+" "+x[8]);
					add_rule(rule,Rules);
					rule.append(x[1]+" "+x[5]+" "+x[3]+" "+x[7]+" "+x[4]+" "+x[8]);
					add_rule(rule,Rules);
					rule.append(x[2]+" "+x[6]+" "+x[3]+" "+x[7]+" "+x[4]+" "+x[8]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[3]+" -"+x[1]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[3]+" -"+x[5]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[1]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[5]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[1]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[5]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[3]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[3]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[4]+" -"+x[7]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[3]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[8]+" -"+x[7]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[3]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[7]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[3]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[7]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[5]+" -"+x[3]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[5]+" -"+x[7]+" -"+x[2]);
					add_rule(rule,Rules);
					rule.append("-"+x[5]+" -"+x[3]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[5]+" -"+x[7]+" -"+x[6]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[2]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[2]+" -"+x[8]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[6]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[1]+" -"+x[6]+" -"+x[8]);
					add_rule(rule,Rules);
					rule.append("-"+x[2]+" -"+x[5]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[2]+" -"+x[5]+" -"+x[8]);
					add_rule(rule,Rules);
					rule.append("-"+x[6]+" -"+x[5]+" -"+x[4]);
					add_rule(rule,Rules);
					rule.append("-"+x[6]+" -"+x[5]+" -"+x[8]);
					add_rule(rule,Rules);
				}
				else if(Matrix[i][j] == 4){
					x[1] = Line_Matrix[i+2][j+1][2];
					x[2] = Line_Matrix[i+2][j+1][3];
					x[3] = Line_Matrix[i+1][j+2][1];
					x[4] = Line_Matrix[i+1][j+2][4];
					x[5] = Line_Matrix[i+2][j+1][6];
					x[6] = Line_Matrix[i+2][j+1][7];
					x[7] = Line_Matrix[i+1][j+2][5];
					x[8] = Line_Matrix[i+1][j+2][8];
					rule.append(x[5]+" "+x[1]);
					add_rule(rule,Rules);
					rule.append(x[5]+" "+x[7]);
					add_rule(rule,Rules);
					rule.append(x[5]+" "+x[4]);
					add_rule(rule,Rules);
					rule.append(x[5]+" "+x[6]);
					add_rule(rule,Rules);
					rule.append(x[3]+" "+x[1]);
					add_rule(rule,Rules);
					rule.append(x[3]+" "+x[7]);
					add_rule(rule,Rules);
					rule.append(x[3]+" "+x[4]);
					add_rule(rule,Rules);
					rule.append(x[3]+" "+x[6]);
					add_rule(rule,Rules);
					rule.append(x[8]+" "+x[1]);
					add_rule(rule,Rules);
					rule.append(x[8]+" "+x[7]);
					add_rule(rule,Rules);
					rule.append(x[8]+" "+x[4]);
					add_rule(rule,Rules);
					rule.append(x[8]+" "+x[6]);
					add_rule(rule,Rules);
					rule.append(x[2]+" "+x[1]);
					add_rule(rule,Rules);
					rule.append(x[2]+" "+x[7]);
					add_rule(rule,Rules);
					rule.append(x[2]+" "+x[4]);
					add_rule(rule,Rules);
					rule.append(x[2]+" "+x[6]);
					add_rule(rule,Rules);
				}
				
			}
		}
	}
	
	public void oneValue(){
		for(int i=1; i<=size; i++){
			for(int j=1; j<=size; j++){
				for(int k=1; k<=size; k++){
					for(int h=1; h<=size; h++){
						if(k==i&&h==j){
				
						}
						else{
							rule.append("-"+Line_Matrix[i][j][9]+" "+Line_Matrix[i][j][10]+" "+Line_Matrix[i][j][11]+" "+Line_Matrix[i][j][12]
									   +" "+Line_Matrix[i][j][13]+" "+Line_Matrix[i][j][14]+" "+Line_Matrix[i][j][15]+" "+Line_Matrix[i][j][16]
									  +" -"+Line_Matrix[k][h][9]+" "+Line_Matrix[k][h][10]+" "+Line_Matrix[k][h][11]+" "+Line_Matrix[k][h][12]
									   +" "+Line_Matrix[k][h][13]+" "+Line_Matrix[k][h][14]+" "+Line_Matrix[k][h][15]+" "+Line_Matrix[k][h][16]);
							add_rule(rule,Rules);
						}
						
					}
				}
			}
		}
		
	}
	
	public void increasingLabel(){
		for(i=1;i<=size;i++)
			for(j=1;j<=size;j++){
				if(Line_Matrix[i][j][1]>0){
					bit.addBit(Line_Matrix[i][j][1], bit.convert(i,j-1,9,16,Line_Matrix),bit.convert(i,j,17,24,Line_Matrix),  bit.convert(i,j,9,16,Line_Matrix));
				}
				if(Line_Matrix[i][j][2]>0){
					bit.addBit(Line_Matrix[i][j][2], bit.convert(i-1,j,9,16,Line_Matrix),bit.convert(i,j,17,24,Line_Matrix),  bit.convert(i,j,9,16,Line_Matrix));
				}
				if(Line_Matrix[i][j][3]>0){
					bit.addBit(Line_Matrix[i][j][3], bit.convert(i,j+1,9,16,Line_Matrix),bit.convert(i,j,17,24,Line_Matrix),  bit.convert(i,j,9,16,Line_Matrix));
				}
				if(Line_Matrix[i][j][4]>0){
					bit.addBit(Line_Matrix[i][j][4], bit.convert(i+1,j,9,16,Line_Matrix),bit.convert(i,j,17,24,Line_Matrix),  bit.convert(i,j,9,16,Line_Matrix));
				}
			
			}
		Rules.addAll(bit.getRules());
	}
	
	public void writeToFile(String inputFile) throws IOException, ArrayIndexOutOfBoundsException{
    	try{
	    	createRules();
	    	FileWriter fw = new FileWriter(inputFile);
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	bw.write("p cnf " + value + " " + Rules.size() + "\n");
	    	for (String rule : Rules) {
	    	    bw.write(rule.trim() + " 0\n");
	    	}
	    	bw.flush();
	    	bw.close();
	    	fw.close();
	    	}catch (Exception e){
	    		e.getMessage();
	    	}
    		
    	
        }
	
	public void createRules(){
		more_one_line_in();
		more_one_line_out();
		out_vectex_is_in_vectex();
		in_or_out();
		line();
		edges();
		increasingLabel();
		oneValue();
		
	}
	
	 private String solve(String inputFile, String outputFile) throws IOException {
			//ruleEncoder.writeToFile(inputFile);
		    writeToFile(inputFile);
			Process process = new ProcessBuilder("MiniSat_v1.14.exe", inputFile, outputFile).start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String result = "", line;

			while ((line = br.readLine()) != null) {
			    result += line + "\n";
			}
			return result;
		}
	
	public void Doc_File_Ketqua(String inputFile, String outputFile) throws IOException {
		solve(inputFile,outputFile);
    	String line = new String();
    	BufferedReader br = new BufferedReader(new FileReader(outputFile));
    	line = br.readLine();
    	if (line.equals("UNSAT")) {
    		System.out.print("UNSAT");
    	    br.close();
    	}
    	line = br.readLine();
    	br.close();
    	String[] numbers = line.trim().split(" ");
    	result = new int[size+1][size+1][9];
    	int h = 0;
    	for(i=1;i<=size;i++)
			for(j=1;j<=size;j++)
				for(k=1;k<=8;k++){
					if(i*j*k>=numbers.length) result[i][j][k] = 0;
					else{
						result[i][j][k] = Integer.parseInt(numbers[h]);
						h++;
					}
					
				}
    	int[] tiep = new int[8];
    	for(i=1;i<=size;i++){
			for(j=1;j<=size;j++){
				for(k=9;k<=24;k++){
					if(k<=16) tiep[k-9] = Integer.parseInt(numbers[h]);
					
					h++;
				}
				System.out.print(bit.convert_bit_to_int(tiep)+"  ");
			}
			System.out.println();
    	}
    	cell = new Cells[size-1][size-1];
    	for(i=2;i<=size;i++){
			for(j=1;j<size;j++){
				cell[i-2][j-1] = new Cells();
				if(result[i][j][2]>0||result[i][j][6]>0){
					cell[i-2][j-1].x[0] = true;
				}
				if(result[i][j][3]>0||result[i][j][7]>0){
					cell[i-2][j-1].x[3] = true;
				}
				if(result[i-1][j+1][1]>0||result[i-1][j+1][5]>0){
					cell[i-2][j-1].x[1] = true;
				}
				if(result[i-1][j+1][4]>0||result[i-1][j+1][8]>0){
					cell[i-2][j-1].x[2] = true;
				}
				cell[i-2][j-1].value = Matrix[i-2][j-1];
			}
			
		}
    	
    	
    }
		
}