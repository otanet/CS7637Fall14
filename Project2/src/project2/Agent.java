package project2;

//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
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
    HashMap<String,String> transformations = new HashMap<>();
    HashMap<String,String> attributes = new HashMap<>();
    HashMap<String,Double> transformationScore = new HashMap<>();
    String problemName;
    Integer iFormat;
    
    public Agent() {
        transformations.put("scaled","size");
        transformations.put("angle","angle");
        transformations.put("fill","fill");
        transformations.put("location","above,inside,under,below,left,right,left-of,right-of");
        
        attributes.put("fill","fill");
        
        transformationScore.put("scaled", 1.0);
        transformationScore.put("angle", 1.0);
        transformationScore.put("fill", 1.0);
        transformationScore.put("location", 1.0);
        iFormat = 16;
        
        
        
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
    public String Solve(RavensProblem problem) {
        String answer="0";
        println();
        println("---------------PROBLEM:" + problem.getName() +"----------------");
        println();
        //This just gets all of the figures from the problems
        RavensFigure figA = problem.getFigures().get("A");
        RavensFigure figB = problem.getFigures().get("B");
        RavensFigure figC = problem.getFigures().get("C");
        RavensFigure fig1 = problem.getFigures().get("1");
        RavensFigure fig2 = problem.getFigures().get("2");
        RavensFigure fig3 = problem.getFigures().get("3");
        RavensFigure fig4 = problem.getFigures().get("4");
        RavensFigure fig5 = problem.getFigures().get("5");
        RavensFigure fig6 = problem.getFigures().get("6");
        
        problemName = problem.getName();
        
//******************************DEBUG*********************************
        String debugProblem = "2x2 Basic Problem 05";
        if (problem.getName().equals(debugProblem)){
//******************************DEBUG*********************************
        //-- Stage 1
        //verifyCorrelation returns HashMap<String,String> of ????????????
        HashMap<String,String> ObjectMapping = VerifyCorrelation(figA,figB);
        HashMap<String, String> ab = BuildComparisonSheet(figA, figB, new HashMap<String,String>());
        HashMap<String, String> c1 = BuildComparisonSheet(figC, fig1, ObjectMapping);
        HashMap<String, String> c2 = BuildComparisonSheet(figC, fig2, ObjectMapping);
        HashMap<String, String> c3 = BuildComparisonSheet(figC, fig3, ObjectMapping);
        HashMap<String, String> c4 = BuildComparisonSheet(figC, fig4, ObjectMapping);
        HashMap<String, String> c5 = BuildComparisonSheet(figC, fig5, ObjectMapping);
        HashMap<String, String> c6 = BuildComparisonSheet(figC, fig6, ObjectMapping);

        int[] score = {0,0,0,0,0,0,0};
        int[] score_2 = {0,0,0,0,0,0,0};
        int[] finalscore = {0,0,0,0,0,0,0};
        
        //--Stage 2 & 3
        score[1]+=ScoreFactSheets(ab, c1, "1");
        score[2]+=ScoreFactSheets(ab, c2, "2");
        score[3]+=ScoreFactSheets(ab, c3, "3");
        score[4]+=ScoreFactSheets(ab, c4, "4");
        score[5]+=ScoreFactSheets(ab, c5, "5");
        score[6]+=ScoreFactSheets(ab, c6, "6");
        
        if (problem.getProblemType().equals("2x2"))
        {
        ObjectMapping = VerifyCorrelation(figA,figC);
        HashMap<String, String> ab_2 = BuildComparisonSheet(figA, figC, new HashMap<String,String>());
        HashMap<String, String> b1_2 = BuildComparisonSheet(figB, fig1, ObjectMapping);
        HashMap<String, String> b2_2 = BuildComparisonSheet(figB, fig2, ObjectMapping);
        HashMap<String, String> b3_2 = BuildComparisonSheet(figB, fig3, ObjectMapping);
        HashMap<String, String> b4_2 = BuildComparisonSheet(figB, fig4, ObjectMapping);
        HashMap<String, String> b5_2 = BuildComparisonSheet(figB, fig5, ObjectMapping);
        HashMap<String, String> b6_2 = BuildComparisonSheet(figB, fig6, ObjectMapping);

        
        
        //--Stage 2 & 3
        score_2[1]+=ScoreFactSheets(ab_2, b1_2, "1");
        score_2[2]+=ScoreFactSheets(ab_2, b2_2, "2");
        score_2[3]+=ScoreFactSheets(ab_2, b3_2, "3");
        score_2[4]+=ScoreFactSheets(ab_2, b4_2, "4");
        score_2[5]+=ScoreFactSheets(ab_2, b5_2, "5");
        score_2[6]+=ScoreFactSheets(ab_2, b6_2, "6");
            
        }
        finalscore[1]= score[1] + score_2[1] ;
        finalscore[2]= score[2] + score_2[2];
        finalscore[3]= score[3] + score_2[3];
        finalscore[4]= score[4] + score_2[4];
        finalscore[5]= score[5] + score_2[5];
        finalscore[6]= score[6] + score_2[6];
        
        //--Stage 4
        for(int i=0;i<score.length;i++){
            println(i+"=>"+score[i]);
        }
        println();
        
        for(int i=0;i<score_2.length;i++){
            println(i+"=>"+score_2[i]);
        }
        
        println();
        
        for(int i=0;i<finalscore.length;i++){
            println(i+"=>"+finalscore[i]);
        }
        
        
        
        int max = finalscore[0];
        int maxI = 0;
        for ( int i = 1; i < finalscore.length; i++) {
            if ( finalscore[i] > max) {
              max = finalscore[i];
              maxI = i;
            }
        }
        int correct=0;
        int wrong=0;
        answer = String.valueOf(maxI);
        println("Answer: "+answer);
//******************************DEBUG*********************************
        }//For debugging purposes
//******************************DEBUG*********************************
        String correctAnswer = problem.checkAnswer(answer);
        System.out.println("The correct answer is: "+ correctAnswer);
        System.out.println("Robbie guessed: "+ answer);
        return String.valueOf(answer);

    }
    
    private  void println()
    {
        println("");
    }
    private void println(String text)
    {
        System.out.println(text);
    }
    
    //--Used to score the transformation knowledge sheets
    private double ScoreFactSheets(HashMap<String, String> fromTrans, HashMap<String, String> toTrans, String num)
    {
        int iformatting = iFormat;
    	String debug = "Verbose";
    	
        double score = 0;
        HashSet<String> ShapesInC = new HashSet<>();
        int shapesInCQty = 0;
        int countChanged = Integer.valueOf(fromTrans.containsKey("count_changed")?fromTrans.get("count_changed"):"0");
        
        for(String key : fromTrans.keySet())
        {
            
            //match keys:values from both transformation sheets without matching shape
            
            //String t[] = key.split("\\.");
            String objKey = (key.split("\\.")[0].equals("A"))?"C":num;
            String exactKey = key.replaceFirst("\\w\\.", "");
            String cKey = exactKey.contains(".")?objKey+"."+exactKey:exactKey;
            String val1 = fromTrans.get(key);
            String val2 = (toTrans.containsKey(cKey))?toTrans.get(cKey):null;
            println(key+" : "+val1+" - "+cKey+" : "+val2);
            iformatting--;
            if(!key.contains("shape"))
            {
                if (val1.equals(val2))
                {
                    score++;
                    if(debug.equals("Verbose"))System.out.println("*****Score increased******");
                    iformatting--;
                }
                
                
            }
            else 
            {
                if (cKey.contains("C"))
                {
                    ShapesInC.add(val2);
                    shapesInCQty++;
                }
            }
        }
        
        //--tie breakers  --------------------
        int shapesInAnswer=0;
        int expectedCountOfNewObjects = 0;
        if(fromTrans.containsKey("tf-shpe_added"))
            expectedCountOfNewObjects = shapesInCQty + Math.abs(countChanged);
        else if (fromTrans.containsKey("tf-shpe_deleted"))
            expectedCountOfNewObjects = shapesInCQty - Math.abs(countChanged);
       
        System.out.println("Expected Objects: "+expectedCountOfNewObjects);
        for(String shape:ShapesInC)
        {
            shapesInAnswer=0;
            for(String key : toTrans.keySet())
            {
                //Most objects from C should be in answer except if shape changed from A -> B
                if(key.contains("shape") && key.contains(num) && toTrans.get(key).equals(shape) && !fromTrans.containsKey("tf-shpe_changed") )
                {
                    score++;
                    if(debug.equals("Verbose"))System.out.println("***** shapes the same Score increased******");
                    iformatting--;
                }
                
                
                if(key.contains("shape") && key.contains(num))
                    shapesInAnswer++;
            }
        }
        //for 2x2 matrices, check for symmetry of rotations if all 4, extra point.
        if(fromTrans.containsKey("tf-angle")&& toTrans.containsKey("tf-angle"))
        { 
            //get all of the angles of rotation
            Integer[] angles = {360,360,360,360,360,360,360,360,360,360,360,360,360,360,360,360};
            int i = 0;
            //do if for each object, first figure out how many object there are total.
            Integer x = 0;
            Set<String> fromTranskeySet = fromTrans.keySet();
            String findstring = "."+x.toString()+".";
            while (fromTranskeySet.contains(findstring))
            {
                x++;
            }
            int numObjects = x;
                for (Entry<String,String> entry : fromTrans.entrySet())
                {
                    if (entry.getKey().endsWith(".angle"))
                            {
                               String key = entry.getKey();
                               angles[i] = Integer.parseInt(fromTrans.get(key));
                               i++;
                            }
                }
                for (Entry<String,String> entry : toTrans.entrySet())
                {
                    if (entry.getKey().endsWith(".angle"))
                            {
                               String key = entry.getKey();
                               String sAngle = toTrans.get(key);
                               angles[i] = Integer.parseInt(sAngle);
                               i++;
                            }
                }
            Arrays.sort(angles, 0, 4);
            if (((angles[0]+90) == angles[1]) && ((angles[1]+90) == angles[2]) && ((angles[2]+90) == angles[3]))
            {
                score++;
                if(debug.equals("Verbose"))System.out.println("*****rotation sym Score increased******");
                iformatting--;
            }
                
            
                
        }
    
        //Amount of objects in answer should match qty objects in C less deleted objects
        if(shapesInAnswer == expectedCountOfNewObjects)
        {
            score++;
            if(debug.equals("Verbose"))System.out.println("*****shapesInAnswer = expected Score increased******");
            iformatting--;
        }
        while (iformatting >0)
        {
        System.out.println();
        iformatting--;
        }
        
        return score;
    
    }
    
    //--Print transformation sheet
    private void PrintSheet(HashMap<String, String> sheet)
    {
        for (Entry<String, String> entry : sheet.entrySet()) 
            System.out.println(entry.getKey() + ":" + entry.getValue());
    }
    
    private void AreAllShapesSame(HashMap<String, String> ret1, HashMap<String, String> ret2, HashMap<String, String> ret) {
        boolean shapeSame = true;
        String shape = "";
        String shapeRet1 = "";
        String shapeRet2 = "";
        
        for(String entry : ret1.keySet())
        {
            if (entry.toLowerCase().contains("shape"))
            {
                if (shapeRet1.equals("") && shapeSame){
                    shapeRet1 = ret1.get(entry);
                } else if (!shapeRet1.equals(ret1.get(entry))){
                    shapeSame = false;
                }
            }
        }
        ret.put("tf-same_shpe_fig1", String.valueOf(shapeSame));
        shapeSame = true;
        for(String entry : ret2.keySet())
        {
            if (entry.toLowerCase().contains("shape"))
            {
                if (shapeRet2.equals("") && shapeSame){
                    shapeRet2 = ret2.get(entry);
                } else if (!shapeRet1.equals(ret2.get(entry))){
                    shapeSame = false;
                }
            }
        }
        ret.put("tf-same_shpe_fig2", String.valueOf(shapeSame));
    }
    
    private void DidShapeChange(HashMap<String,String> ret1, HashMap<String,String> ret2, HashMap<String,String> ret)
    {
        boolean shapeChanged = false;
        int cnt = 0;
        for(String entry : ret1.keySet())
        {
            if (entry.toLowerCase().contains("shape"))
            {
                //We just want the key attribute
                String shapeRet1 = ret1.get(entry);
                String shapeRet2 = (ret1.containsKey(entry)) ?ret2.get(entry):"";
                if (shapeRet2 != null)
                {
                    if(!shapeRet1.equals(shapeRet2))
                    {
                        shapeChanged = true;
                        cnt++;
                    }
                }
            }
        }
        
        if(shapeChanged)
            ret.put("tf-shpe_changed", String.valueOf(cnt));
    }
    
        private void WasShapeFilled(HashMap<String,String> ret1, HashMap<String,String> ret2, HashMap<String,String> ret)
    {
        boolean fillChanged = false;
        int cnt = 0;
        for(String entry : ret1.keySet())
        {
            if (entry.toLowerCase().contains("fill"))
            {
                String shapeRet1 = ret1.get(entry);
                String shapeRet2 = (ret1.containsKey(entry)) ?ret2.get(entry):"";
                if(!shapeRet1.equals(shapeRet2)){
                    fillChanged = true;
                    cnt++;
                }
            }
        }
        
        if(fillChanged)
            ret.put("tf-fill_changed", String.valueOf(cnt));
    }
        
    private void WasShapeScaled(HashMap<String,String> ret1, HashMap<String,String> ret2, HashMap<String,String> ret)
    {
        boolean scaleChanged = false;
        int cnt = 0;
        Set test = ret1.keySet();
        for(String entry : ret1.keySet())
        {
            if (entry.toLowerCase().contains("size"))
            {
                String shapeRet1 = ret1.get(entry);
                String shapeRet2 = (ret1.containsKey(entry)) ?ret2.get(entry):"";
                if(!shapeRet1.equals(shapeRet2)){
                    scaleChanged = true;
                    cnt++;
                }
            }
        }
        
        if(scaleChanged)
            ret.put("tf-scale_changed", String.valueOf(cnt));
    }
    
    //--Build transformation sheets
    private HashMap<String,String> BuildComparisonSheet(RavensFigure figure1, RavensFigure figure2, HashMap<String,String> ObjectMapping)
    {
        int iformatting = iFormat;
        HashMap<String,String> ret = new HashMap<>();
        HashMap<String,String> ret1 = new HashMap<>();
        HashMap<String,String> ret2 = new HashMap<>();
        
        HashMap<String,String> realObjectMappingFig1 = new HashMap<>();
        if(!ObjectMapping.isEmpty() ){
            for(Entry<String,String> entry : ObjectMapping.entrySet()){
                String size = entry.getValue();
                for(RavensObject obj:figure1.getObjects())
                {
                    for(RavensAttribute att :  obj.getAttributes()){
                        if(att.getName().toLowerCase().contains("size") && att.getValue().equals(size)){
                            realObjectMappingFig1.put(obj.getName(), entry.getKey().split("\\.")[0]);
                        }
                    }

                }
            }
        }
        HashMap<String,String> realObjectMappingFig2 = new HashMap<>();
        if(!ObjectMapping.isEmpty() ){
            for(Entry<String,String> entry : ObjectMapping.entrySet()){
                String size = entry.getValue();
                for(RavensObject obj:figure2.getObjects())
                {
                    for(RavensAttribute att :  obj.getAttributes()){
                        if(att.getName().toLowerCase().contains("size") && att.getValue().equals(size)){
                            realObjectMappingFig2.put(obj.getName(), entry.getKey().split("\\.")[0]);
                        }
                    }

                }
            }
        }
        
        Integer objNum = 0;
        ExtractAttributes(figure1, ret1, realObjectMappingFig1, objNum);
        ExtractAttributes(figure2, ret2, realObjectMappingFig2, objNum);
        
        Integer maxsize;
        Integer minsize;
        
        int cnt = figure2.getObjects().size() - figure1.getObjects().size();
        if (cnt == 0)
        {
            maxsize = figure1.getObjects().size();
            minsize = maxsize;
        }
        else if (cnt>0)
        {
            maxsize = figure2.getObjects().size();
            minsize = figure1.getObjects().size();
        }
        else
        {
            maxsize = figure1.getObjects().size();
            minsize = figure2.getObjects().size();
        }
        ret.put("maxsize",maxsize.toString());
        ret.put("minsize", minsize.toString());
               
        //Determine if shapes were added or removed
        if(figure2.getObjects().size() > figure1.getObjects().size())
            ret.put("tf-shpe_added", "1");
        else if(figure2.getObjects().size() < figure1.getObjects().size())
            ret.put("tf-shpe_deleted", "1");
        
        DidShapeChange(ret1, ret2, ret);
        AreAllShapesSame(ret1, ret2, ret);
        //WasShapeFilled(ret1, ret2, ret);
        WasShapeScaled(ret1, ret2, ret);
        Set<Entry<String, String>> transforms = transformations.entrySet();
        //Loop through all of the big picture transformations: location, shape,
        //fill, angle, etc.
        for(Entry<String,String> entry: transforms)
        {
            String prevEntry = null;
            String transform = null;
            String key = entry.getKey();
            String value = entry.getValue();
            String[] tags = entry.getValue().split(",");
            //Then loop through all of the possibilities for those transformations.
            for (String tag : tags) {
                Set<Entry<String,String>> ret1EntrySet = ret1.entrySet();
                for(Entry<String,String> retEntry : ret1EntrySet)
                {
                    //If the transformation that you're looking for is there, and the second figure has the same one...
                    Boolean test = retEntry.getKey().trim().toLowerCase().contains(tag.toLowerCase());
                    String test3 = retEntry.getKey();
                    Boolean test2 = ret2.containsKey( test3);
                    if(test && test2)
                    {
                        String ret1EntryValue = retEntry.getValue();
                        String ret1EntryKey = retEntry.getKey();
                        String ret2Test = ret2.get(ret1EntryKey); 
                        
                        if(!ret1EntryValue.equals(ret2Test))
                        {
                            transform = entry.getKey();
                            //transformValue = retEntry.getValue();
                            if(attributes.containsKey(transform))
                                ret.put(transform, ret2.get(retEntry.getKey()) );
                            else if (transform.equals("angle"))
                            {
                                Integer angle1 = Integer.parseInt(retEntry.getValue());
                                Integer angle2 = Integer.parseInt(ret2.get(ret1EntryKey));
                                Integer anglediff = angle1+angle2;
                                if (anglediff > 359)anglediff = 360 - anglediff;
                                ret.put("tf-"+transform, anglediff.toString());
                            }
                            else
                                ret.put("tf-"+transform, transform); //TODO: New frame needs to be created also
                            transform = null;
                        }
                    }
                }
                
            }
        }
        
        for(Entry<String,String> entry : ret1.entrySet())
        {
            ret.put(figure1.getName()+"."+entry.getKey(), entry.getValue());
        }
        for(Entry<String,String> entry : ret2.entrySet())
        {
            ret.put(figure2.getName()+"."+entry.getKey(), entry.getValue());
        }
        ret.put("count_changed", ""+cnt);
        
        int i = iFormat;
        for(Entry<String,String> entry : ret.entrySet())
        {
            println(entry.toString());
            i--;
        }
        
        println("**********");
        while (i>0)
        {
            println();
            i--;
        }
                

        return ret;
    }

    private void ExtractAttributes(RavensFigure figure, HashMap<String, String> ret, HashMap<String,String> realObjectMapping, Integer objNum) {
        //.getObjects returns an arrayList
        for(RavensObject obj:figure.getObjects())
        {
            
            //we will correlate the objects via 1, 2, 3, etc.
            String objName = objNum.toString(); // was obj.getName();
            String realName = "";
            for(RavensAttribute att:obj.getAttributes())
            {
                //ret.put(figure.getName()+"."+obj.getName()+"."+att.getName(), att.getValue());
                if (!realObjectMapping.isEmpty()&& realObjectMapping.containsKey(obj.getName()))
                {
                     realName = realObjectMapping.get(obj.getName());
                }
                else
                {
                     realName = objName; //obj.getName();
                }
                //String realName = (!realObjectMapping.isEmpty() && realObjectMapping.containsKey(obj.getName()))? realObjectMapping.get(obj.getName()):obj.getName();
                ret.put(realName+"."+att.getName(), att.getValue());
            }
            objNum++;
        }
    }
    
    private HashMap<String,String> VerifyCorrelation(RavensFigure figure1, RavensFigure figure2) {
        HashMap<String,String> corrFig1 = new HashMap<>();
        HashMap<String,String> corrFig2 = new HashMap<>();
        HashMap<String,String> retCorrelation = new HashMap<>();
        boolean correlated = true;
        
        Integer objNumber = 0;
        for(RavensObject obj:figure1.getObjects())
        {
            //we will do the correlations by shape and size and use 1, 2, 3, etc for shape correlation
            for(RavensAttribute att :  obj.getAttributes()){
                //first do correlation by shape and size
                if(att.getName().toLowerCase().contains("shape") || att.getName().toLowerCase().contains("size")){
                    corrFig1.put(objNumber.toString()+"."+att.getName(), att.getValue());  //was obj.getName() for first one.
                    objNumber++;
                //then do correlation by shape only
                
                //then do correlation by size only
                    
                }
            }
            
        }
        objNumber = 0;
        for(RavensObject obj:figure2.getObjects())
        {
            for(RavensAttribute att :  obj.getAttributes()){
                if(att.getName().toLowerCase().contains("shape") || att.getName().toLowerCase().contains("size")){
                    corrFig2.put(objNumber+"."+att.getName(), att.getValue());  //was obj.getName() for first one.
                    objNumber++;
                }
            }
            
        }
        
         
        //Test correlations
        for(Entry<String,String> entry : corrFig1.entrySet()){
            if(corrFig2.containsKey(entry.getKey()) && !entry.getValue().equals(corrFig2.get(entry.getKey())))
                correlated = false;
        }
        //HashMap<String, String> ab = BuildComparisonSheet( corrFig1, corrFig2);
        if(correlated){
            //ret.put("tf-correlated", "yes");
            for(Entry<String,String> entry : corrFig1.entrySet()){
                if(entry.getKey().toLowerCase().contains("size"))
                    retCorrelation.put(entry.getKey(), entry.getValue());
            }
        }
        int d=0;
        return retCorrelation;
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