package LuatSudoku;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Luat {
	
	//private String inputFile = "sudoku.cnf", outputFile = "sudoku.solution";
	private int Kichco_Sudoku;
    private int Kichco_o_Sudoku; // Kích cỡ 1 phần của sudoku (ví dụ sudoku 9x9 thì blockSize là 3)
    private int[][] Sudoku;
    private int[][][] mang_bien_dem; // Lưu trữ các biến của MiniSat
    private int bien_dem;		//Biến lưu trữ các ô
    private ArrayList<String> All_Luat = new ArrayList<String>();  //Mảng chưa tất cả luật của Sudoku
    private ArrayList<String> mang_luat = new ArrayList<String>(); // Mảng chứa các luật của 1 sudoku bình thường
    private ArrayList<String> mang_luat_dien = new ArrayList<String>(); // Mảng chứa các luật sinh ra từ các ô đã được điền
    private Time_Sudoku t = new Time_Sudoku();
    	
    private int r, c, v; // Giá trị hàng, cột, giá trị của một ô
    private StringBuilder Luat_cu = new StringBuilder();
    public Luat(int[][] Sudoku) {
    	this.Sudoku = Sudoku;
    	this.Kichco_Sudoku = Sudoku.length;
    	this.Kichco_o_Sudoku = (int) Math.sqrt(Kichco_Sudoku);

    	mang_bien_dem = new int[Kichco_Sudoku + 1][Kichco_Sudoku + 1][Kichco_Sudoku + 1];
    	bien_dem = 1;
    	for (r = 1; r < Kichco_Sudoku + 1; r++) {
    	    for (c = 1; c < Kichco_Sudoku + 1; c++) {
    		for (v = 1; v < Kichco_Sudoku + 1; v++) {
    		    mang_bien_dem[r][c][v] = bien_dem++;
    		}
    	    }
    	}
    	bien_dem--;
        }
    private void addCurrentRuleAndResetBuffer(StringBuilder Luat_cu, ArrayList<String> luat) {
    	luat.add(Luat_cu.toString());
    	Luat_cu.setLength(0);
        }
    
    //thủ tục mã hóa
    public void Ma_Hoa() throws Exception {
    	Luat1();
    	Luat2();
    	Luat3();
    	Luat4();
    	Luat5();
    	Luat6();
    	Luat7();
    	Luat8();
    	Luat9();

    	All_Luat.addAll(mang_luat);
    	All_Luat.addAll(mang_luat_dien);
        }
    //Ghi các mệnh đề thành cnf
    public void writeToFile(String fileName) throws IOException, ArrayIndexOutOfBoundsException{
    	try{
    	Ma_Hoa();
    	FileWriter fw = new FileWriter(fileName);
    	fw.write("p cnf " + bien_dem + " " + All_Luat.size() + "\n");
    	for (String rule : All_Luat) {
    	    fw.write(rule.trim() + " 0\n");
    	}
    	fw.close();
    	}catch (Exception e){
    		e.getMessage();
    	}
    	
        }
    //Luat 1 :Tạo cnf clause thể hiện mỗi ô chứa ít nhất 1 giá trị từ 1 đến size của bảng
    //x111 hợp x112 hợp x113 hợp x114......
    public void Luat1() {
    	for (r = 1; r < Kichco_Sudoku + 1; r++) {
    	    for (c = 1; c < Kichco_Sudoku + 1; c++) {
    		for (v = 1; v < Kichco_Sudoku + 1; v++) {
    			Luat_cu.append(mang_bien_dem[r][c][v] + " ");
    		}
    		addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    	    }
    	}
        }
    //Luật 2 :Tạo cnf clause thể hiện mỗi ô có nhiều nhất 1 giá trị từ 1 đến size của bảng
    //
    public void Luat2() {
    	for (r = 1; r < Kichco_Sudoku + 1; r++) {
    	    for (c = 1; c < Kichco_Sudoku + 1; c++) {
    		for (int v = 1; v < Kichco_Sudoku; v++) {
    		    for (int i = v + 1; i < Kichco_Sudoku + 1; i++) {
    		    	Luat_cu.append("-" + mang_bien_dem[r][c][v] + " " + "-" + mang_bien_dem[r][c][i]);
    			addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    		    }
    		}
    	    }
    	}
        }
    //Luật 3 :Tạo cnf clause thể hiện trong mỗi hàng mỗi số xuất hiện ít nhất 1 lần:
    public void Luat3() {
    	for (r = 1; r < Kichco_Sudoku + 1; r++) {
    	    for (v = 1; v < Kichco_Sudoku + 1; v++) {
    		for (int c = 1; c < Kichco_Sudoku + 1; c++) {
    			Luat_cu.append(mang_bien_dem[r][c][v] + " ");
    		}
    		addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    	    }
    	}
        }
    //Luật 4 : Tạo cnf clause thể hiện trong mỗi hàng mỗi số xuất hiện nhiều nhất 1 lần;
    public void Luat4() {
    	for (r = 1; r < Kichco_Sudoku + 1; r++) {
    	    for (v = 1; v < Kichco_Sudoku + 1; v++) {
    		for (c = 1; c < Kichco_Sudoku; c++) {
    		    for (int i = c + 1; i < Kichco_Sudoku + 1; i++) {
    		    	Luat_cu.append("-" + mang_bien_dem[r][c][v] + " " + "-" + mang_bien_dem[r][i][v]);
    			addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    		    }
    		}
    	    }
    	}
        }
    //Luật 5 : Tạo cnf clause thể hiện mỗi cột mỗi số xuất hiện ít nhất 1 lần
    public void Luat5() {
    	for (c = 1; c < Kichco_Sudoku + 1; c++) {
    	    for (v = 1; v < Kichco_Sudoku + 1; v++) {
    		for (r = 1; r < Kichco_Sudoku + 1; r++) {
    			Luat_cu.append(mang_bien_dem[r][c][v] + " ");
    		}
    		addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    	    }
    	}
        }
    //Luật 6 :Tạo cnf clause thể hiện mỗi cột mỗi số xuất hiện nhiều nhất 1 lần
    public void Luat6() {
    	for (c = 1; c < Kichco_Sudoku + 1; c++) {
    	    for (v = 1; v < Kichco_Sudoku + 1; v++) {
    		for (r = 1; r < Kichco_Sudoku; r++) {
    		    for (int i = r + 1; i < Kichco_Sudoku + 1; i++) {
    		    	Luat_cu.append("-" + mang_bien_dem[r][c][v] + " " + "-" + mang_bien_dem[i][c][v]);
    			addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    		    }
    		}
    	    }
    	}
        }
    //Luật 7 :Tạo cnf clause thể hiện trong mỗi ô mỗi số xuất hiện ít nhất 1 lần
    public void Luat7() {
    	for (v = 1; v < Kichco_Sudoku + 1; v++) {
    	    for (int i = 0; i < Kichco_o_Sudoku; i++) {
    		for (int j = 0; j < Kichco_o_Sudoku; j++) {
    		    for (r = 1; r < Kichco_o_Sudoku + 1; r++) {
    			for (c = 1; c < Kichco_o_Sudoku + 1; c++) {
    				Luat_cu.append(mang_bien_dem[Kichco_o_Sudoku * i + r][Kichco_o_Sudoku * j + c][v] + " ");
    			}
    		    }
    		    addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    		}
    	    }
    	}
        }
    //Luật 8 :Tạo cnf clause thể hiện trong mỗi block mỗi số xuất hiện không quá 1 lần
    public void Luat8() {
    	for (v = 1; v < Kichco_Sudoku + 1; v++) {	
    	    for (int i = 0; i < Kichco_o_Sudoku; i++) {
    		for (int j = 0; j < Kichco_o_Sudoku; j++) {
    		    for (r = 1; r < Kichco_o_Sudoku + 1; r++) {
    			for (c = 1; c < Kichco_o_Sudoku + 1; c++) {
    			    for (int k = c + 1; k < Kichco_o_Sudoku + 1; k++) {
    			    	Luat_cu.append("-" + mang_bien_dem[Kichco_o_Sudoku * i + r][Kichco_o_Sudoku * j + c][v] + " " + "-"
    					+ mang_bien_dem[Kichco_o_Sudoku * i + r][Kichco_o_Sudoku * j + k][v]);
    				addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    			    }
    			}
    		    }
    		}
    	    }
    	}

    	for (v = 1; v < Kichco_Sudoku + 1; v++) {
    	    for (int i = 0; i < Kichco_o_Sudoku; i++) {
    		for (int j = 0; j < Kichco_o_Sudoku; j++) {
    		    for (r = 1; r < Kichco_o_Sudoku + 1; r++) {
    			for (c = 1; c < Kichco_o_Sudoku + 1; c++) {
    			    for (int k = r + 1; k < Kichco_o_Sudoku + 1; k++) {
    				for (int l = 1; l < Kichco_o_Sudoku + 1; l++) {
    					Luat_cu.append("-" + mang_bien_dem[Kichco_o_Sudoku * i + r][Kichco_o_Sudoku * j + c][v] + " "
    					    + "-" + mang_bien_dem[Kichco_o_Sudoku * i + k][Kichco_o_Sudoku * j + l][v]);
    				    addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    				}
    			    }
    			}
    		    }
    		}
    	    }
    	}
        }
    //Luật 9 :Tạo cnf clause cho các ô đã được điền sẵn
    public void Luat9() throws Exception{
    	try{
    	for (r = 0; r < Kichco_Sudoku; r++) {
    	    for (c = 0; c < Kichco_Sudoku; c++) {
    		if (Sudoku[r][c] != 0) {
    			Luat_cu.append(mang_bien_dem[r + 1][c + 1][Sudoku[r][c]]);
    		    addCurrentRuleAndResetBuffer(Luat_cu, mang_luat);
    		}
    	    }
    	}
    	}catch(Exception e){
    		e.getMessage();
    	}
        }
    public int[][] Doc_File_Ketqua(String inputFile, String outputFile) throws IOException {
    	Process process = new ProcessBuilder("MiniSat_v1.14.exe", inputFile, outputFile).start();
    	InputStream is = process.getInputStream();
    	BufferedReader br = new BufferedReader(new FileReader(outputFile));
    	String line,result = "";
    	line = br.readLine();
    	if (line.equals("UNSAT")) {
    		System.out.print("UNSAT");
    	    br.close();
    	    return null;
    	    
    		    //solution = readResultFromFile(outputFile);
    		    //setChanged();
    		}
    		//return result;
    	int[][] solution = new int[Kichco_Sudoku][Kichco_Sudoku];
    	line = br.readLine();
    	String[] numbers = line.trim().split(" ");
    	for (String number : numbers) {
    	    if (number.startsWith("-") || number.equals("0"))
    		continue;
    	    int realNumber = Integer.parseInt(number) - 1;
    	    int nCells = Kichco_Sudoku * Kichco_Sudoku;
    	    int row = realNumber / nCells;
    	    int col = (realNumber % nCells) / Kichco_Sudoku;
    	    int value = (realNumber % nCells) % Kichco_Sudoku + 1;
    	    solution[row][col] = value;
    	    //System.out.print(row+" "+col+" "+value+"\n");
    	}
    	br.close();
    	return solution;
        }
}
