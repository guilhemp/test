package Donnees;

import java.util.ArrayList;


/*--------------------------------------------------DESCRIPTION-----------------------------------------------------------------------------------------------------

This Classe's instances are used to store data 








-------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


public class Column <T>{

	
	//--------------------------Attributs ------------------------------------------------
	
	
	protected String header;				//name of the field 
	protected String type;					//type of the field 
	protected ArrayList <T> data;			//data storage
	protected int rowCount;					//number of row
	
	//---------------------------Constructeur ---------------------------------------------
	
	public Column   (String header, String type, ArrayList <T> data){
		this.header=header;
		this.type=type;
		setData(data);
		rowCount=data.size();		
	}
	
	//---------------------------Accesseurs -----------------------------------------------
	
	public void setHeader (String header){
		this.header=header;
	}
	
	public String getHeader (){
		return header;
	}
	
	public void setType (String type){
		this.type=type;
	}
	
	public String getType (){
		return type;
	}
	
	public void setData (ArrayList <T> data){
		ArrayList <T> newData = new ArrayList <> ();
		for(T t : data){
			newData.add(t);
		}
		this.data=newData;
		setRowCount();
	}
	
	public void setData (int i, T data ){
		(this.data).set(i,data);
	}
	
	public ArrayList<T> getData (){
		return data;
	}
	
	public  T getData (int i){
		return data.get(i);
	}
	
	public void setRowCount (){
		rowCount=data.size();
	}
	
	public int getRowCount (){
		return rowCount;
	}
	
	//-------------------------------------Methodes-------------------------------------------
	
	public void addData ( T data ){
		this.data.add(data);
		rowCount++;
	}
	
	public void addData (int i, T data ){
		this.data.add(i, data);
		rowCount++;
	}
	
	public void removeData (int i){
		this.data.remove(i);
		rowCount--;
	}
	
	
	
	
	
	
	
}
