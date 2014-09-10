package project1;

import java.util.ArrayList;
import java.util.HashMap;

public class Transformation {
    Integer numberObjectsFrom;
    Integer numberObjectsTo;
    Integer maxObjects;
    Integer minObjects;
    RavensFigure figureFrom;
    RavensFigure figureTo;
    ArrayList<ObjectTrans> AllTrans;
    ArrayList<String>flatTrans;
    
    public Transformation(RavensFigure figure1, RavensFigure figure2) 
    {
    	numberObjectsFrom = figure1.getObjects().size();
    	numberObjectsTo = figure2.getObjects().size();
    	maxObjects = (numberObjectsFrom > numberObjectsTo) ? numberObjectsFrom : numberObjectsTo;
    	minObjects = (numberObjectsFrom < numberObjectsTo) ? numberObjectsFrom : numberObjectsTo;
    	figureFrom = figure1;
    	figureTo = figure2;
    	AllTrans = new ArrayList<ObjectTrans>();
    	flatTrans = new ArrayList<String>();
    	setTrans();
    	setFlatTrans();
    	
        //goal = new ObjectTrans(A.getObjects().get(0),B.getObjects().get(0));
        
    }

    public void setFlatTrans()
    {
    for(int x = 0; x < AllTrans.size(); x++)
    	{
    		ArrayList<String> temp = AllTrans.get(x).getDiffArray();
    		for(int y=0;y<temp.size(); y++)
    		{
    			flatTrans.add(temp.get(y));
    		}
    	}
    }

    public String getFrom() 
    {
        return "0";
    }
    
    public String getTo() 
    {
    	return "0";
    }

    
    public void setTrans()
    {
    	for(int x = 0; x<maxObjects; x++)
    	
    	if (x < minObjects)
    	{
    		ObjectTrans temp = new ObjectTrans(figureFrom.getObjects().get(x),figureTo.getObjects().get(x)); 
    		AllTrans.add(temp);
    	}
    	else if(numberObjectsFrom < maxObjects)
    	{
    		RavensObject emptyObject = new RavensObject("dne");
    		ObjectTrans temp = new ObjectTrans(emptyObject,figureTo.getObjects().get(x));
    		AllTrans.add(temp);
    	}
    	else if(numberObjectsTo < maxObjects)
    	{
    		RavensObject emptyObject = new RavensObject("dne");
    		ObjectTrans temp = new ObjectTrans(figureFrom.getObjects().get(x),emptyObject);
    		AllTrans.add(temp);
    	}
    }
    
    public ArrayList<ObjectTrans> getTrans()
    {
    	return AllTrans;
    }
    
    public ArrayList<String> getFlatTrans()
    {
    	return flatTrans;
    }

    
}
