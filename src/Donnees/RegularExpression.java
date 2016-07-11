package Donnees;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {

	public String dateExtraction (String input){
		String output="2015-02-";
		Pattern pDate = Pattern.compile(" [0-9]{2} ");
		Matcher mDate = pDate.matcher(input);
		
		while (mDate.find()){
			output = output.concat(input.substring(mDate.start()+1, mDate.end()-1));
		}
		
		return output;
	}
	
	public String timeExtraction (String input){
		String output="";
		
		Pattern pTime = Pattern.compile("([0-1][0-9]|2[0-3])(:[0-5][0-9])+");
		Matcher mTime = pTime.matcher(input);
		while (mTime.find()){
			output=input.substring(mTime.start(), mTime.end());
		}
		return output;
	}
	
	public int durationExtraction (String input){
		int output=0;
		ArrayList <Integer> durations = new ArrayList <> ();
		Pattern pDuration = Pattern.compile("[0-9]+");
		Matcher mDuration = pDuration.matcher(input);
		
		while (mDuration.find()){
			durations.add(0,Integer.parseInt(input.substring(mDuration.start(), mDuration.end())));
		}
		
		for (int k=0;k<durations.size();k++){
			if (k<3){
				output += durations.get(k)*Math.pow(60, k);
			}
			else {
				output += durations.get(k)*3600*24;
			}
		}
		
		return output;
	}
	
	
}
