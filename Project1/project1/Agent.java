package project1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    HashMap<String,String> transformHash = new HashMap<>();
    HashMap<String,String> attributesHash = new HashMap<>();
    HashMap<String,Double> transformPointsHash = new HashMap<>();
    
    public Agent() {
     //*** This holds the points of the transformation. 
    	transformPointsHash.put("scaled", 100.0);
        transformPointsHash.put("angle", 100.0);
        transformPointsHash.put("fill", 100.0);
        transformPointsHash.put("location", 100.0);
     //*** This holds all of the possibilities of the transformation between the figures
        transformHash.put("location","above,inside,under,below,left,right,left-of,right-of");
        transformHash.put("fill","fill");
        transformHash.put("scaled","size");
        transformHash.put("angle","angle");
     //*** this hold the actual attributes of the figures
        attributesHash.put("fill","fill");
        

    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public String Solve(RavensProblem problem) 
    {
        String answer="0";
        System.out.println("-------------------------------------Problem:" + problem.getName());
     //*** First load up all the Ravens figures
        RavensFigure FigA = problem.getFigures().get("A");
        RavensFigure FigB = problem.getFigures().get("B");
        RavensFigure FigC = problem.getFigures().get("C");
        RavensFigure Fig1 = problem.getFigures().get("1");
        RavensFigure Fig2 = problem.getFigures().get("2");
        RavensFigure Fig3 = problem.getFigures().get("3");
        RavensFigure Fig4 = problem.getFigures().get("4");
        RavensFigure Fig5 = problem.getFigures().get("5");
        RavensFigure Fig6 = problem.getFigures().get("6");

//used for debugging
String debugName = problem.getName();
if (debugName.equals("2x1 Basic Problem 14"))
		{
		
    //*** Using the Ravens figures above, build transformation matrices using the compare routine

        HashMap<String, String> compAB = CompareAndCorrelate(FigA, FigB);
        HashMap<String, String> compC1 = CompareAndCorrelate(FigC, Fig1);
        HashMap<String, String> compC2 = CompareAndCorrelate(FigC, Fig2);
        HashMap<String, String> compC3 = CompareAndCorrelate(FigC, Fig3);
        HashMap<String, String> compC4 = CompareAndCorrelate(FigC, Fig4);
        HashMap<String, String> compC5 = CompareAndCorrelate(FigC, Fig5);
        HashMap<String, String> compC6 = CompareAndCorrelate(FigC, Fig6);


    //*** reset all the scores to zero
        Double[] score = {0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        
    //*** score each of the comparison matrices and place them in the scores array
        score[1] = Score(compAB, compC1, "1");
        score[2] = Score(compAB, compC2, "2");
        score[3] = Score(compAB, compC3, "3");
        score[4] = Score(compAB, compC4, "4");
        score[5] = Score(compAB, compC5, "5");
        score[6] = Score(compAB, compC6, "6");
 
    //*** print out the scores for each comparison 
        for(int x=0; x < score.length; x++)
        {
            System.out.println(x + " score:" + score[x]);
      
		}
        
    //*** compare all the scores to each other and select the maximum
        double maxscore = score[0];
        int iteration = 0;
        for ( int i = 1; i < score.length; i++) 
        {
            if ( score[i] > maxscore) 
            {
              maxscore = score[i];
              iteration = i;
            }
        }
		

    //*** the max score is the answer send it out.
        answer = String.valueOf(iteration);
        System.out.println("Answer: " + answer);
        String correct = problem.checkAnswer(answer);
        System.out.println("correct answer "+ correct);
}//used for debug purposes: with if (debugName.equals("")) from above
        return String.valueOf(answer);
    //*** End of Main Loop-----End of Main Loop-----End of Main Loop-----End of Main Loop-----End of Main Loop-----End of Main Loop
		
    }
    
    //*** Used to score the transformations maps
    private double Score(HashMap<String, String> reference, HashMap<String, String> guess, String number)
    {
        double score = 0;
        HashSet<String> CShapes = new HashSet<>();
        int CShapesQty = 0;
        int figureChange = Integer.valueOf(reference.get("figure_change"));

        Set<String> Cattributes = reference.keySet();
        for(String key : Cattributes) //loop through all of the attributes in the "C" space. (we'll generalize "A" to "C")
        {
            String objectKey; 
            if(key.split("\\.")[0].equals("A")) //replace the A with C to make this a more general routine
            	objectKey = "C";
            else
            	objectKey = number;
            
            String exactKey = key.replaceFirst("\\w\\.", "");//remove figure number to look at the attribute and object.
            
            String CKey; 
            if (exactKey.contains("."))
            	CKey = objectKey + "." + exactKey; //deal with the specific attribute
            else 
            	CKey = exactKey; //only in the case of figure_change
            
            String val1 = reference.get(key);
            String val2;
            if (guess.containsKey(CKey))
            	val2 = guess.get(CKey);
            else val2 = null;
            
            //System.out.println(key + " : " + val1 + " - " + CKey + " : " + val2);
            if(!key.contains("shape"))
            {
            	if (val1.equals(val2))
            	{
            		score = score + 1; //increase the score by one if the values are the same from A to C and B to 1
            		System.out.println(key + " : " + val1 + " - " + CKey + " : " + val2);
            		System.out.println("score added. Score = " + score);
            		System.out.println();
            	}
            	
            } 
            else 
            {
                if (CKey.contains("C"))
                {
                    CShapes.add(val2); //keep a running tab on which shapes go together.
                    CShapesQty = CShapesQty + 1;
                }
            }
            if (key.contains("angle"))
            {
            	if (!val1.equals(val2))
            	{
            		//make rotations less than 180 degrees.
            		Integer valone = Integer.parseInt(val1);
            		Integer valtwo = Integer.parseInt(val2);
            		if (valone > 180) valone = valone - 360;
            		if (valtwo > 180) valtwo = valtwo - 360;
            		if (Math.abs(valone)==Math.abs(valtwo)) 
            			{
            			score = score + 1.0;
            			System.out.println("score added rotation > 180"); 
            			}
            	}
            }
        }
        
        //*** tie breakers
        int shapesInAnswer=0;
        int expectedCountOfNewObjects = CShapesQty - Math.abs(figureChange);
        System.out.println("Expected Objects: " + expectedCountOfNewObjects);
        for(String shape:CShapes)
        {
            shapesInAnswer=0;
            for(String key : guess.keySet())
            {
                //Most objects from C should be in answer
                if(key.contains("shape") && key.contains(number) && guess.get(key).equals(shape))
                {
                    score=score+1;
                    System.out.println("score added shapes same. Score = " + score);
                    System.out.println();
                }
                
                if(key.contains("shape") && key.contains(number))
                    shapesInAnswer++;
            }
        }
        //Amount of objects in answer should match quantity of objects in C minus deleted objects 
        //(Comes into play for problem 05)
        if(shapesInAnswer == expectedCountOfNewObjects)
        {
            score = score + 1;
            System.out.println("score added number of shapes the same. Score = " + score);
            System.out.println();
        }
        
        System.out.println("score "+score);
        System.out.println();
        System.out.println();
        
        return score;
    
    }
    
    
    //*** Compare the two figures, correlate them, and then create a transformation Hashmap 
    private HashMap<String,String> CompareAndCorrelate(RavensFigure figure1, RavensFigure figure2)
    {	
        HashMap<String,String> returnHash = new HashMap<>();
        HashMap<String,String> fig1Hash = new HashMap<>();
        HashMap<String,String> fig2Hash = new HashMap<>();
        HashMap<String,String> workingHash = new HashMap<>();
        
        ExtractAttributes(figure1, fig1Hash);
        ExtractAttributes(figure2, fig2Hash);
        
        int countChange = figure2.getObjects().size() - figure1.getObjects().size();
        
        for(Entry<String,String> entry : fig1Hash.entrySet())
        {
        	workingHash.put(figure1.getName()+"."+entry.getKey(), entry.getValue());
        }
        for(Entry<String,String> entry : fig2Hash.entrySet())
        {
        	workingHash.put(figure2.getName()+"."+entry.getKey(), entry.getValue());
        }
        
        //Now do the correlation
        Set<String> working = workingHash.keySet();
        for(String key : working)
        {
        	if (key.contains("Y"))
        	{	//If the Y figure exists, we know we have more than one figure, therefore correlate
        		System.out.print("correlate");
        		System.out.println();
        		
        		//match shape C.Z.shape -> 1.Z.shape
        		//match shape C.X.shape -> 1.X.shape
        		//match shape C.Y.shape -> 1.Y.shape
        		//match shape C.Z.size -> 1.Z.size
        		//match shape C.X.size -> 1.X.size
        		//match shape C.Y.size -> 1.Y.size
        		
        		//does size exist?
        		//two sizes that are the same? if so... forget it.
        		//match size C.Z.size -> whatever has the same size -> rename value for Z or X or Y-> call it the same as C.(this value, XYZ)
        		//repeat for other two.
        	}
        }
        	

        
        returnHash = workingHash;
        //inform that the figure has changed
        returnHash.put("figure_change", ""+ countChange);

        return returnHash;
    }

    private void ExtractAttributes(RavensFigure figure, HashMap<String, String> ret) {
        for(RavensObject obj:figure.getObjects())
        {
            String objName = obj.getName();
            for(RavensAttribute att:obj.getAttributes())
            {
                ret.put(obj.getName()+"."+att.getName(), att.getValue());
            }
        }
    }
    
    private HashSet<RavensAttribute> FindDifference(RavensObject A, RavensObject B)
    {
        HashSet<RavensAttribute> ret = new HashSet<RavensAttribute>();
        for(RavensAttribute ravensAttributeA : A.getAttributes())
        {
            for(RavensAttribute ravensAttributeB : B.getAttributes())
            {
                if (ravensAttributeA.getName() == null ? ravensAttributeB.getName() == null : ravensAttributeA.getName().equals(ravensAttributeB.getName()))
                    if(ravensAttributeA.getValue() == null ? ravensAttributeB.getValue() != null : !ravensAttributeA.getValue().equals(ravensAttributeB.getValue()))
                        ret.add(new RavensAttribute(ravensAttributeA.getName(), ravensAttributeB.getValue()));
            }
                
        }
        
        return ret;
    }
}