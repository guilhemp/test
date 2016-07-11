import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import Donnees.Csv;
import Donnees.RegularExpression;
import Donnees.SqlDataBase;
import Donnees.Table;




public class Main {


	public static void main(String[] args) {
		
		ArrayList <Csv> files = new ArrayList<>();
		
		for (int i=1; i<7;i++){
			
			String adress = Integer.toString(i)+".csv";
			Csv csv = new Csv(adress);
			RegularExpression rE =new RegularExpression();
			Table table = csv.getTable();
			csv.action();
			ArrayList <Date> associationDate = new ArrayList <>();
			ArrayList <Time> associationHour = new ArrayList <>();
			ArrayList <Integer> duration = new ArrayList <>();
			for (String s : table.getString("Association Time")){
				Date date = new Date(0);
				date = Date.valueOf(rE.dateExtraction(s));
				Time time = new Time(0);
				time = Time.valueOf(rE.timeExtraction(s));
				associationDate.add(date);
				associationHour.add(time);
			}
			for (String s: table.getString("Session Duration")){
				duration.add(rE.durationExtraction(s));
			}
			table.add("associationDate", "date", associationDate);
			table.add("associationHour", "time", associationHour);
			table.add("duration", "int", duration);
			table.remove("Association Time");
			table.remove("Disassociation Time");
			table.remove("Session Duration");
			table.suppressingNull("Location");
			files.add(csv);
		}
		
		
		SqlDataBase db = new SqlDataBase("Concordia");
		db.dropTable("test1");
		db.createTable("test1", files.get(0).getTable(), "ID,location, associationDate, associationHour");
		
		for(Csv csv : files){
			db.importTable(csv.getTable(), "test1", "ID,location, associationDate, associationHour");
		}
		
		
		/*
		SqlDataBase db = new SqlDataBase("Concordia");
		db.resultQuery("SELECT * FROM test1 ORDER BY ID, associationDate, associationHour");
		db.getTable().print(100);
		*/
		

		
		
		
		
		
		
	}

}
