package project1;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectTrans {
    String name1; //name of the first figure x,y,z
    String name2; //name of the second figure x,y,z, etc
    ArrayList<RavensAttribute> attributes1;  //list of the attributes for first figure
    ArrayList<RavensAttribute> attributes2;  //list of the attributes for second figure
    ArrayList<String> differences; //actual differences array?
    //differences array looks something like this:
    //<same,same,attribute1,attribute2,same,same,dne,dne>
    

    public ObjectTrans(RavensObject object1, RavensObject object2) 
    {
        name1 = object1.getName();
        name2 = object2.getName();
        attributes1 = object1.getAttributes();
        attributes2 = object2.getAttributes();
        differences = new ArrayList<>();
        setDiffArray();
    }

    public void setDiffArray()
    {
    	for(int x = 0; x < attributes1.size(); x++)
    	{
    		if (!name1.equals("dne") && !name2.equals("dne"))
    		{
	    		if (attributes1.get(x).getName().equals(attributes2.get(x).getName()) == true )
	    		{
	    			if(attributes1.get(x).getValue().equals(attributes2.get(x).getValue()) == true)
	    			{
	    				differences.add("Same");
	    				differences.add("Same");
	    			}
	    			else
	    			{
	    	    		differences.add(attributes1.get(x).getValue());
	    	    		differences.add(attributes2.get(x).getValue());
	    			}
	    		}
	    		else if(name1.equals("dne"))
	    		{
	    			differences.add("dne");
	    			differences.add(attributes2.get(x).getValue());
	    		}
	    		else if(name2.equals("dne"))
	    		{
	    			differences.add(attributes1.get(x).getValue());
	    			differences.add("dne");
	    		}
    		}
    	}
    }
    
    public String getFrom() 
    {
        return name1;
    }
    
    public String getTo() 
    {
        return name1;
    }

    public ArrayList<RavensAttribute> getAttributeFrom() 
    {
        return attributes1;
    }
    
    public ArrayList<RavensAttribute> getAttributeTo() 
    {
        return attributes2;
    }
    public ArrayList<String> getDiffArray()
    {
    	return differences;
    }

}
