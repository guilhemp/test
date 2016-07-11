package Donnees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlDataBase {
	
    protected String url;
    protected String user;
    protected String password;
    protected Connection conn;	
	protected Table table;
	protected static Dictionary dictionary;
	
	public SqlDataBase (String adresse){
		
		try{
        Class.forName("org.postgresql.Driver");
		url = "jdbc:postgresql://localhost:5433/";
		url = url.concat(adresse);
		user = "postgres";
		password = "postgres";
	    conn = DriverManager.getConnection(url, user, password);
	    System.out.println("effective connection to " + adresse + " dataBase \n");
		}catch (Exception e) {
		      e.printStackTrace();		
		}
		table = new Table();
		dictionary = new Dictionary();
	}
	
	public void resultQuery (Table inputTable, String query){
		
		ArrayList <ArrayList<String>> metaData = new ArrayList<>();
		ArrayList <ArrayList <Object>> data = new ArrayList <>();
		inputTable.clear();
		
		try{
	      Statement  state= conn.createStatement();
	      ResultSet result = state.executeQuery(query);
	      ResultSetMetaData resultMeta = result.getMetaData();
		
	      for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
	    	ArrayList <String> temp = new ArrayList <String>();
			temp.add(resultMeta.getColumnName(i));
			temp.add(dictionary.sqlToTable(resultMeta.getColumnTypeName(i)));
			metaData.add(temp);
		  }    
	      
	      for (ArrayList <String > a : metaData){
	    	  System.out.println("\t"+ a.get(0)+"\t ("+a.get(1)+")");
	      }
	      
	      for (@SuppressWarnings("unused") ArrayList <String> a : metaData){
	    	  ArrayList <Object> temp = new ArrayList <>();
	    	  data.add(temp);
	      }
	      
	      while (result.next()){
	    	  for (int i=1; i<= resultMeta.getColumnCount();i++){
	    		  (data.get(i-1)).add(result.getObject(i));
	    	  }
	      }
	      
	      for (int j=0; j<metaData.size();j++){
	    	  inputTable.add(metaData.get(j).get(0), metaData.get(j).get(1), data.get(j));
	      }
	      
	      System.out.println("");
	      System.out.println("importation completed \n");

	      
	      
	      result.close();
	      state.close();
	      
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void resultQuery (String query){
		resultQuery(table, query);
	}
	
	public void updateQuery (String query){
		try{
		      Statement  state= conn.createStatement();
		      state.executeUpdate(query);
		      System.out.println(query+"\n");
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private boolean tablePresence (String tableName){
		boolean result= false;
		Table tableInventory = new Table ();
		resultQuery(tableInventory,"SELECT tablename FROM pg_tables");
		for(String s : tableInventory.getString("tablename")){
			if (s.equals(tableName)){
				result=true;
			}
		}
		return result;
	}
	
	public void createTable (String tableName, Table table, String primaryKey){
		if (tablePresence(tableName)==false){
			String query ="CREATE TABLE ";
			query=query.concat(tableName + " (");

			
			for (ArrayList <String> a : table.getMetaData()){
				query=query.concat(a.get(0) + " " + dictionary.tableToSql(a.get(1))+ " ,");
			}
			
			query= query.concat("PRIMARY KEY ("+ primaryKey+ "))" );
			updateQuery(query);
		}
		else{
			System.out.println("table "+tableName+" already exists \n");
		}
	}
	
	public void createTable (String tableName){
		createTable(tableName, table, table.getMetaData().get(0).get(0));
	}
	
	public void dropTable (String tableName){
		String query = "DROP TABLE IF EXISTS " + tableName;
		updateQuery(query);
	}
	
	public Table getTable (){
		return table;
	}
	
	public void importTable (Table inputTable, String destinationTable, String primaryKey){
		Csv tempCsv = new Csv (destinationTable+"_temp.csv", inputTable);
		tempCsv.exportation();
		dropTable(destinationTable + "_temp");
		updateQuery("CREATE TABLE " + destinationTable + "_temp AS SELECT * FROM "+ destinationTable+ " WHERE 1=0");
		updateQuery("COPY "+destinationTable +"_temp FROM '/home/guilhem/workspace/Donnees/" +destinationTable+ "_temp.csv' DELIMITER ',' CSV HEADER");
		updateQuery("INSERT INTO " + destinationTable+ " SELECT DISTINCT ON ("+primaryKey +") *  FROM "+ destinationTable +"_temp");
		dropTable(destinationTable + "_temp");
		tempCsv.deleteOutput();
	}

}
