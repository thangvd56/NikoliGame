package LuatSudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Readfile {
	private String readUsingBufferedReader(String fileName, int i) throws IOException {
	        File file = new File(fileName);
	        FileReader fr = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);
	        int j = 0;
	        String line;
	        while ((line = br.readLine()) != null) {
	        	j++;
	            if(j==i){
	            	break;
	            }
	        }
	        br.close();
	        fr.close();
	        return line;
	}
	
	public int[][] setMatric(String fileName, int id){
		try{
			String line = readUsingBufferedReader(fileName,id);
			String[] array = line.split(" ");
			int size = (int)Math.sqrt(array.length);
			int[][] matrix = new int[size][size];
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					matrix[i][j] = Integer.parseInt(array[i*size+j]);
				}
			}
			return matrix;
		}catch(Exception e){	
			return null;
		}
		
	}
}
