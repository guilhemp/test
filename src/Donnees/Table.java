package Donnees;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ListIterator;



/*--------------------------------------------------DESCRIPTION-----------------------------------------------------------------------------------------------------

This Classe's instances are used to store data 








-------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

public class Table {


	//--------------------------------Attributs----------------------------------------
	
	
	
	private ArrayList <ArrayList <String>> metaData; // informations about field: name, type 
	private ArrayList <Column<Integer>> tableInt;    // Contain the integer type fields 
	private ArrayList <Column<Double>> tableDouble;  // Contain the double type fields 
	private ArrayList <Column<String>> tableString;  // Contain the text type fields
	private ArrayList <Column<Date>> tableDate;		 // Contain the Date type fields
	private ArrayList < Column<Time>> tableTime;	 // Contain the Time type fields
	private int rowCount;							 // Number of row
	private int columnCount;						 // Number of fields 
	
	
	//--------------------------------Constructeur---------------------------------------
	
	
	public Table (){
		metaData = new ArrayList  <>();
		tableInt = new ArrayList <>();
		tableDouble= new ArrayList <>();
		tableString = new ArrayList <>();
		tableDate = new ArrayList <>();
		tableTime = new ArrayList <>();
		
		rowCount=0;
		columnCount=0;
	}
	
	
	//---------------------------------Methodes-MetaDonnees-------------------------------
	
	private void addField (String name, String type){
		ArrayList <String> newHeader = new ArrayList <String>();
		newHeader.add(name);
		newHeader.add(type);
		metaData.add(newHeader);
		columnCount++;
	}
	
	private void removeField (String name){
		metaData.remove(getHeaderFieldColumn(name));
		columnCount--;
	}
	
	private <T> int getFieldColumn (ArrayList<Column<T>> table , String name){
		ListIterator <Column<T>> columnIterator = table.listIterator();
		int columnNumber=0;
		while(columnIterator.hasNext()){
			if ( (( columnIterator.next().getHeader()).equals(name))){
				columnNumber=columnIterator.nextIndex()-1;
			}
		}
		return columnNumber;
	}
	
	private  int getHeaderFieldColumn ( String name){
		ListIterator <ArrayList<String>> columnIterator = metaData.listIterator();
		int columnNumber=0;
		while(columnIterator.hasNext()){
			if ( (( columnIterator.next().get(0)).equals(name))){
				columnNumber=columnIterator.nextIndex()-1;
			}
		}
		return columnNumber;
	}
	
	
	//--------------------------------Methodes-tables---------------------------------------
	
	//---Columns--------------------------------------
	
	private <T> void addColumn (ArrayList <Column<T>> table,String name, String type, ArrayList <T> data){
		Column <T> newColumn = new Column <T> (name, type, data);
		table.add(newColumn);
		rowCount=data.size();
	}
	
	private <T> void removeColumn (ArrayList <Column <T>> table, String name){
		table.remove(getFieldColumn(table, name));
	}
	
	private <T> ArrayList<T> getColumn (ArrayList <Column<T>> table, String name ){
		return (table.get(getFieldColumn(table, name))).getData();
	}
	
	private <T> void setColumn (ArrayList <Column<T>> table, String name, ArrayList <T> data){
		(table.get(getFieldColumn(table, name))).setData(data);
	}
	
	//-----Values----------------------------------------------
	
	private <T> void addValue (ArrayList <Column <T>> table, String name,int i, T value  ){
		(table.get(getFieldColumn(table, name))).addData(i, value);
	}
	
	private <T> void removeValue (ArrayList <Column <T>> table, String name,int i){
		(table.get(getFieldColumn(table, name))).removeData(i);
	}

	private <T> T getValue (ArrayList <Column<T>> table, String name, int i){
		return (table.get(getFieldColumn(table, name))).getData(i);
	}
	
	private <T> void setValue (ArrayList <Column <T>> table, String name,int i, T value  ){
		(table.get(getFieldColumn(table, name))).setData(i, value);
	}
	
	//-------Row------------------------------------------------
	
	public void addRow (@SuppressWarnings("rawtypes") ArrayList row){
		if (row.size()==columnCount){
			for (int i=0; i<metaData.size();i++){
				String field = (metaData.get(i)).get(0);
				String type = (metaData.get(i)).get(1);
				if(type.equals("int")){
					addInt(field ,rowCount, (int)row.get(i));
				}
				else if(type.equals("double")){
					addDouble(field,rowCount, (double)row.get(i));
				}
				else if(type.equals("string")){
					addString(field,rowCount, (String)row.get(i));
				}
				else if(type.equals("date")){
					addDate(field,rowCount, (Date)row.get(i));
				}
				else if(type.equals("time")){
					addTime(field,rowCount, (Time)row.get(i));
				}
			}
			rowCount++;
		}
	}
	
	
	
	
	//---------------------------------------Methodes-publiques----------------------------------------
	
	//-------------------------------ADD------------------------------------
	
	@SuppressWarnings({ "unchecked" })
	public void add (String name, String type,  ArrayList<?> data ){
		if (type.equals("int")){
			addColumn(tableInt, name, type, (ArrayList<Integer>)data);
			addField(name, type);
		}
		else if (type.equals("double")){
			addColumn(tableDouble, name, type, (ArrayList<Double>)data);
			addField(name, type);
		}
		else if (type.equals("string")){
			addColumn(tableString, name, type, (ArrayList<String>)data);
			addField(name, type);
		}
		else if (type.equals("date")){
			addColumn(tableDate, name, type, (ArrayList<Date>)data);
			addField(name, type);
		}
		else if (type.equals("time")){
			addColumn(tableTime, name, type, (ArrayList<Time>)data);
			addField(name, type);
		}
		else{
			System.out.println("field error while adding column");
		}
	}
	
	
	
	private void addInt(String name, int i, Integer value ){
		addValue(tableInt, name, i, value);
	}	
	
	
	private void addDouble(String name, int i, Double value ){
		addValue(tableDouble, name, i, value);
	}
	
	
	private void addString(String name, int i, String value ){
		addValue(tableString, name, i, value);
	}
	
	private void addDate(String name, int i, Date value ){
		addValue(tableDate, name, i, value);
	}
	
	private void addTime(String name, int i, Time value ){
		addValue(tableTime, name, i, value);
	}
	
	
	//----------------------------Remove-----------------------------------------
	
	public void remove (String name){
		String type = (metaData.get(getHeaderFieldColumn(name))).get(1);
		if (type.equals("int")){
			removeColumn(tableInt, name);
			removeField(name);
		}
		else if(type.equals("double")){
			removeColumn(tableDouble, name);
			removeField(name);
		}
		else if (type.equals("string")){
			removeColumn(tableString, name);
			removeField(name);
		}
		else if (type.equals("date")){
			removeColumn(tableDate, name);
			removeField(name);
		}
		else if (type.equals("time")){
			removeColumn(tableTime, name);
			removeField(name);
		}
		else{
			System.out.println("field error while removing column");
		}
	}
	
	public void remove ( int i){
		if (i<rowCount){
			for (int j=0;i<columnCount;j++){
				String name = (metaData.get(j)).get(0);	
				String type = (metaData.get(j)).get(1);
				if (type.equals("int")){
					removeValue(tableInt, name,i);
				}
				else if(type.equals("double")){
					removeValue(tableDouble, name, i);
				}
				else if (type.equals("string")){
					removeValue(tableString, name, i);
				}
				else if (type.equals("date")){
					removeValue(tableDate, name, i);
				}
				else if (type.equals("time")){
					removeValue(tableTime, name, i);
				}
				else{
					System.out.println("field error while removing value");
				}	
			}
			rowCount--;
		}
		else{
			System.out.println("row index error while removing value");
		}
	}
	
	public void clear (){
		metaData = new ArrayList  < ArrayList<String>>();
		tableInt = new ArrayList <Column<Integer>>();
		tableDouble= new ArrayList <Column <Double>>();
		tableString = new ArrayList <Column <String>>();
		tableDate = new ArrayList <Column <Date>>();
		tableTime = new ArrayList <Column <Time>>();
		
		rowCount=0;
		columnCount=0;
	}
	
	//------------------------------GET---------------------------------------------
	
	public ArrayList<Integer> getInt(String name){
		return getColumn(tableInt, name);
	}
	
	public int getInt(String name, int i){
		return getValue(tableInt, name, i);
	}
	
	public ArrayList<Double> getDouble(String name){
		return getColumn(tableDouble, name);
	}
	
	public double getDouble(String name, int i){
		return getValue(tableDouble, name, i);
	}	
	
	public ArrayList<String> getString(String name){
		return getColumn(tableString, name);
	}	
	
	public String getString(String name, int i){
		return getValue(tableString, name, i);
	}	
	
	public ArrayList<Date> getDate(String name){
		return getColumn(tableDate, name);
	}	
	
	public Date getDate(String name, int i){
		return getValue(tableDate, name, i);
	}	
	
	public ArrayList<Time> getTime(String name){
		return getColumn(tableTime, name);
	}	
	
	public Time getTime(String name, int i){
		return getValue(tableTime, name, i);
	}	
	

	public ArrayList<String>  getRow (int row){
		ArrayList <String> result = new ArrayList <>();
		int actualRow =row;
		if (row >=rowCount){
			actualRow=rowCount-1; 
		}
			for (ArrayList<String> aS : metaData){
				if (aS.get(1).equals("int")){
					result.add(Integer.toString(getInt(aS.get(0),actualRow)));					
				}
				else if (aS.get(1).equals("double")){
					result.add(Double.toString(getDouble(aS.get(0),actualRow)));					
				}
				else if (aS.get(1).equals("string")){
					result.add(getString(aS.get(0),actualRow));					
				}
				else if (aS.get(1).equals("date")){
					result.add(getDate(aS.get(0),actualRow).toString());					
				}
				else if (aS.get(1).equals("time")){
					result.add(getTime(aS.get(0),actualRow).toString());					
				}
			}
		return result;
	}
	
	public int  columnCount (){
		return columnCount;
	}
	
	public int rowCount (){
		return rowCount;
	}
	
	public ArrayList <ArrayList <String>> getMetaData (){
		return metaData;
	}
	
	//------------------------------SET-----------------------------------------------------
	
	public void setInt(String name, ArrayList<Integer> data ){
		setColumn(tableInt, name, data);
	}
	
	public void setInt(String name, int i, Integer value ){
		setValue(tableInt, name, i, value);
	}	
	
	public void setDouble(String name, ArrayList<Double> data ){
		setColumn(tableDouble, name, data);
	}
	
	public void setDouble(String name,  int i, Double value ){
		setValue(tableDouble, name, i, value);
	}
	
	public void setString(String name, ArrayList<String> data ){
		setColumn(tableString, name, data);
	}	
	
	public void setString(String name, int i, String value ){
		setValue(tableString, name, i, value);
	}
	
	public void setDAte(String name, ArrayList<Date> data ){
		setColumn(tableDate, name, data);
	}	
	
	public void setDAte(String name, int i, Date value ){
		setValue(tableDate, name, i, value);
	}
	
	public void setTime(String name, ArrayList<Time> data ){
		setColumn(tableTime, name, data);
	}	
	
	public void setTime(String name, int i, Time value ){
		setValue(tableTime, name, i, value);
	}
	//--------------------------------Modification-------------------------------------------------------

	public void suppressingNull (String field){
		int counter =0;
		ListIterator<String> stringIterator = getString(field).listIterator();
		while(stringIterator.hasNext()){
			String temp = stringIterator.next();
			if (temp==null | temp.length()==0){
				stringIterator.set("unknown");
				counter++;
			}
		}
		System.out.println(counter + " elements modified \n");
	}
	
	
	//--------------------------------Print-------------------------------------------------------
	
	public void print (){
		print(rowCount);
	}
	
	public void print (int lineNb){
		for (ArrayList<String> aS : metaData){
			System.out.print("\t" +aS.get(0)+"\t");
		}
		System.out.println("");	
		for (ArrayList<String> aS : metaData){
			System.out.print("\t("+aS.get(1)+")\t");
		}
		System.out.println("\n");
		
		int counter = Math.min(lineNb , rowCount);
		for (int i=0; i<counter; i++){
			for (ArrayList<String> aS : metaData){
				if (aS.get(1).equals("int")){
					System.out.print("\t" +getInt(aS.get(0),i)+"\t");					
				}
				else if (aS.get(1).equals("double")){
					System.out.print("\t" +getDouble(aS.get(0),i)+"\t");					
				}
				else if (aS.get(1).equals("string")){
					System.out.print("\t" +getString(aS.get(0),i)+"\t");					
				}
				else if (aS.get(1).equals("date")){
					System.out.print("\t" +getDate(aS.get(0),i)+"\t");					
				}
				else if (aS.get(1).equals("time")){
					System.out.print("\t" +getTime(aS.get(0),i)+"\t");					
				}
			}
			System.out.println("");
		}
	}
	
	
	
	
	

}
