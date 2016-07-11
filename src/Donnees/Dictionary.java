package Donnees;

import java.util.ArrayList;

public class Dictionary {
	
	ArrayList <ArrayList <String>> sql;
	
	public Dictionary (){
		sql = new ArrayList <>();
		ArrayList <String> temp= new ArrayList <>();
		temp.add("int");
		temp.add("int4");
		sql.add(temp);
		temp= new ArrayList <>();
		temp.add("string");
		temp.add("text");
		sql.add(temp);
		temp= new ArrayList <>();
		temp.add("double");
		temp.add("float8");
		sql.add(temp);
		temp= new ArrayList <>();
		temp.add("date");
		temp.add("date");
		sql.add(temp);
		temp= new ArrayList <>();
		temp.add("time");
		temp.add("time");
		sql.add(temp);
	}
	
	public String tableToSql (String type){
		String result ="text";
		for (ArrayList <String> a : sql){
			if (a.get(0).equals(type)){
				result=a.get(1);
			}
		}
		return result;
	}

	public String sqlToTable (String type){
		String result ="string";
		for (ArrayList <String> a : sql){
			if (a.get(1).equals(type)){
				result=a.get(0);
			}
		}
		return result;
	}
	
	
}
