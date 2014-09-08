package project1;

import java.util.ArrayList;
import java.util.HashMap;

public class Transformation {
    //transformation
		//nameto
		//namefrom
		//Objects
			//attributes
		//Objectfrom
		
	
	public RavensFigure figureFrom;
    public RavensFigure figureTo;
    
    
    //public HashMap<String, ArrayList<String>> attributeDifferences;
    // Differences has a structure that looks like this:
    // (string Object name, hashmap(attribute name, Array(what the attribute was, what the attribute became)))
    public HashMap<String, HashMap<String, ArrayList<String>>> Differences;
    //public HashMap<String, attributeDifferences>;
    
    

    public void figureTransformation(RavensFigure figure1, RavensFigure figure2) 
    {
        this.figureFrom=figure1;
        this.figureTo=figure2;
        
		for(int x = 0; x < this.figureFrom.getObjects().size(); x = x+1)
		{
			 //setDifferences(this.figureFrom.getObjects())
		}
        
        
    }


    public String getName() 
    {
        return "0";
    }
    

    public String getValue() 
    {
        return "0";
    }
    
    public void setDifferences(RavensObject object1, RavensObject object2)
    {
    	//The two objects names should be equal. (X compared to X, etc)
    	//if (this.objectFrom.getName().equals(this.objectTo.getName()))
    	//{
    	//	String name = objectFigure.getObjects().g//get the attributes and put them in the the difference map.

    		
    	//}
    	
    	
    }
}
