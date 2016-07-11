package dictionnaires;

import java.util.ArrayList;

public abstract class TypeDictionnary {

	ArrayList <String> dictionnary;
	
	public abstract boolean isPresent(String word);
}


