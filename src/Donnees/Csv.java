package Donnees;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Csv {

	//-------------------------------------Attributs-----------------------------------------------
	
	private String inputAdress;
	private String outputAdress;
	private Table table;
	private ArrayList <ArrayList<String>> metaData;
	ArrayList <ArrayList<ArrayList<Byte>>> tableBuffer ;
	
	
	
	//------------------------------------Constructeur---------------------------------------------
	
	public Csv (String adress){
		this.inputAdress=adress;
		this.outputAdress=adress;
		table= new Table();
		metaData= new ArrayList<>();
		tableBuffer = new ArrayList <>();
	}
	
	public Csv (String inputAdress,String outputAdress ){
		this.inputAdress=inputAdress;
		this.outputAdress=outputAdress;
		table= new Table();
		metaData= new ArrayList<>();
		tableBuffer = new ArrayList <>();
	}
	
	public Csv (String adress, Table table){
		this.inputAdress=adress;
		this.outputAdress=adress;
		this.table=table;
		this.metaData=table.getMetaData();
		tableBuffer= new ArrayList <>();
	}
	
	//-----------------------------------Accesseurs-------------------------------------------------
	
	public void setOutpuAdress (String outputAdress){
		this.outputAdress= outputAdress;
	}
	
	public String getOutputAdress (){
		return outputAdress;
	}
	
	public String getInputAdress (){
		return inputAdress;
	}
	
	public Table getTable (){
		return table;
	}
	
	public ArrayList <ArrayList<String>> getMetaData (){
		return metaData;
	}
	
	//----------------------------------Methodes-importation------------------------------------------
	
	private void lecture (){
		FileInputStream fis;
		FileChannel fc;
		
		try{
			  fis = new FileInputStream(new File(inputAdress));
			  fc= fis.getChannel();
			
			  int size = (int)fc.size();
			  ByteBuffer bBuff = ByteBuffer.allocate(size);
			  fc.read(bBuff);
			  bBuff.flip();
			  byte [] tabByte = bBuff.array();
			  

			  int counter=0;
			  ArrayList<ArrayList<Byte>> bigTempArray = new ArrayList <> ();
			  ArrayList<Byte> tempArray = new ArrayList<>();
			  while (counter<tabByte.length){
				  byte buffer = tabByte[counter];
				  if (buffer==10){
					  tempArray.remove(tempArray.size()-1);
					  bigTempArray.add(tempArray);
					  tableBuffer.add(bigTempArray);
					  bigTempArray = new ArrayList <> ();
					  tempArray = new ArrayList<>();
				  }
				  else if (buffer== 44){
					  bigTempArray.add(tempArray);
					  tempArray = new ArrayList<>();
				  }
				  else{
					  tempArray.add(buffer);
				  }
				  counter++;
			  }
			  
		      System.out.println("reading file  "+ inputAdress + " done");
		} catch (FileNotFoundException e) {
		      e.printStackTrace();
	    } catch (IOException e) {
		      e.printStackTrace();
	    }
	}
	
	private void getHeader (){
		System.out.println("");
		System.out.println("HEADERS:");
		for (ArrayList<Byte> aB : tableBuffer.get(0)){
			String buffer="";
			for (byte b : aB){
				buffer=buffer.concat(Character.toString((char)b));
			}
			ArrayList<String> newHeader=new ArrayList<>();
			newHeader.add(buffer);
			metaData.add(newHeader);
			System.out.print("-" + buffer+ "-");
		}
		System.out.println("");
		System.out.println("");	
	}
	
	private void getType (){
		System.out.println("TYPES:");
		
		int testNb = tableBuffer.size()-1 ;
		
		for (int j=0; j<(tableBuffer.get(0)).size();j++){
			String type= "int";
			for (int i=1; i<testNb; i++){
				if (typeTest((tableBuffer.get(i)).get(j)).equals("double") && type.equals("int")){
					type="double";
				}
				else if (typeTest((tableBuffer.get(i)).get(j)).equals("string") && !type.equals("string")){
					type="string";
				}
			}
			(metaData.get(j)).add(type);
			System.out.print("-" + type+ "-");
		}
		System.out.println("");
		System.out.println("");	
	}
	
	
	private String typeTest (ArrayList<Byte> tab ){
		String type = "int";
		for (byte b : tab ){
			if (b==46 && type.equals("int")){
				type="double";
			}
			else if (b<45 || b>57){
				type="string";
			}
		}
		return type;
	}
	
	private void dataImportation (){
		for (int j=0; j<metaData.size();j++){
			if ((metaData.get(j)).get(1).equals("int")){
				ArrayList <Integer> data = new ArrayList <>();
				for (int i=1; i<tableBuffer.size(); i++){
					String buffer ="";
					for (byte b : (tableBuffer.get(i)).get(j)){
						buffer=buffer.concat(Character.toString((char)b));
					}
					data.add(Integer.parseInt(buffer));
				}
				table.add((metaData.get(j)).get(0), "int", data);
			}
			else if ((metaData.get(j)).get(1).equals("double")){
				ArrayList <Double> data = new ArrayList <>();
				for (int i=1; i<tableBuffer.size(); i++){
					String buffer ="";
					for (byte b : (tableBuffer.get(i)).get(j)){
						buffer=buffer.concat(Character.toString((char)b));
					}
					data.add(Double.parseDouble(buffer));
				}
				table.add((metaData.get(j)).get(0), "double", data);
			}
			else if ((metaData.get(j)).get(1).equals("string")){
				ArrayList <String> data = new ArrayList <>();
				for (int i=1; i<tableBuffer.size(); i++){
					String buffer ="";
					for (byte b : (tableBuffer.get(i)).get(j)){
						buffer=buffer.concat(Character.toString((char)b));
					}
					data.add(buffer);
				}
				table.add((metaData.get(j)).get(0), "string", data);
			}
		}
		tableBuffer=null;
		metaData = table.getMetaData();
		
		System.out.println("importation de " + inputAdress + " effectuee");
		System.out.println("");	
	}
	
	//--------------------------------------------------Exportation-----------------------------------------------------------
	
	public void printTable (){
		table.print();
	}
	
	public void printTable (int lineNb){
		table.print(lineNb);
	}
	
	public void exportation (){
		FileOutputStream fos;
		PrintStream ps;
		
		try {
			fos = new FileOutputStream(new File(outputAdress));
			ps = new PrintStream (fos);
			
			for (int i=0;i<metaData.size()-1;i++){
				ps.print(metaData.get(i).get(0)+",");
			}
			ps.print(metaData.get(metaData.size()-1).get(0));
			ps.println("");
			
			for(int i=0; i<table.rowCount();i++){
				@SuppressWarnings("rawtypes")
				ArrayList row = table.getRow(i);
				for(int j =0; j< row.size()-1;j++ ){
					ps.print(row.get(j)+",");
				}
				ps.print(row.get(row.size()-1));
				ps.println("");
			}
			ps.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		System.out.println("ecriture de " + outputAdress + " effectuee");
		System.out.println("");	
	}
	
	public void deleteOutput (){
		File output = new File(outputAdress);
		output.delete();
	}
	
	
	//-------------------------------------------------Methodes publiques------------------------------------------------------
	
	public void action (){
		lecture();
		getHeader();
		getType();
		dataImportation();

	}
	
	
	
}
