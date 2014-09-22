package BitEncoding;

import java.util.ArrayList;

public class Bit_Encoding {
	private ArrayList<String> Rules = new ArrayList<String>();
	private StringBuilder rule = new StringBuilder();
	
	public ArrayList<String> getRules(){
		return Rules;
	}
	
	public void add_rule(StringBuilder rule, ArrayList<String> rules){
		rules.add(rule.toString());
    	rule.setLength(0);
	}
	public int[] convert(int i, int j, int x, int y, int[][][] matrix){
		int[] one = new int[y-x+1];
		int h=0;
		for(int k=x; k<=y; k++){
			one[h] = matrix[i][j][k];
			h++;
		}
		
		return one;
		
	}
	public int convert_bit_to_int(int[] c){
		int sum = 0;
		for(int i=0; i<c.length; i++){
			if(c[i]>0){
				sum+=Math.pow(2, i);
			}
		}
		return sum;
	}
	public void addBit(int in, int[] x, int[] c, int[] result){
		String a = new String();
		for(int j=0; j<result.length; j++){
			if(j==0) a = " ";
			else a = " -";
			//rule.append("-"+result[x.length]+" "+c[x.length-1]);
			//add_rule(rule,Rules);
			//rule.append(result[x.length]+" -"+c[x.length-1]);
			//add_rule(rule,Rules);
			rule.append("-"+c[0]+" "+x[0]+" -"+in+a+result[j]);
			add_rule(rule,Rules);
			rule.append("-"+x[0]+" "+c[0]+" -"+in+a+result[j]);
			add_rule(rule,Rules);
			rule.append(x[0]+" "+result[0]+" -"+in+a+result[j]);
			add_rule(rule,Rules);
			rule.append("-"+x[0]+" -"+result[0]+" -"+in+a+result[j]);
			add_rule(rule,Rules);
			
			for(int i=1; i<c.length; i++){
				rule.append("-"+c[i]+" "+x[i]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				rule.append("-"+c[i]+" "+x[i]+" "+c[i-1]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				rule.append("-"+c[i]+" "+c[i-1]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				rule.append("-"+x[i]+" -"+c[i-1]+" "+c[i]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				
				rule.append("-"+result[i]+" -"+x[i]+" -"+c[i-1]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				rule.append("-"+result[i]+" "+x[i]+" "+c[i-1]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				
				rule.append("-"+x[i]+" "+result[i]+" "+c[i-1]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
				rule.append(x[i]+" "+result[i]+" -"+c[i-1]+" -"+in+a+result[j]);
				add_rule(rule,Rules);
			}
		}
		
	}
}
