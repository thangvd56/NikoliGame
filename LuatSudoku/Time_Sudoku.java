package LuatSudoku;

public class Time_Sudoku {
	String miniSatResult ="";
	public void extractInformationFromMiniSatResult(String result) {
		String[] lines = result.split("\n");
		miniSatResult = "";
		for (String s : lines) {
		    if (s.contains("Memory")) {
			miniSatResult += s + "\n";
		    }
		    if (s.contains("CPU")) {
			miniSatResult += s;
		    }
		}
		//return miniSatResult;
	    }
	public String getminiSatResult(){
		
		return miniSatResult;
	}


}
