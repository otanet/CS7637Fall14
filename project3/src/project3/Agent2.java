package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 *
 * You may also create and submit new files in addition to modifying this file.
 *
 * Make sure your file retains methods with the signatures: public Agent()
 * public char Solve(RavensProblem problem)
 *
 * These methods will be necessary for the project's main method to run.
 *
 */
public class Agent2
{

    private HashMap<String, Shape> Shapes = new HashMap<>();
    private final String DEBUGPROBLEM = "";
    HashMap<String,String> transformations = new HashMap<>();
    HashMap<String,String> attributes = new HashMap<>();
    HashMap<String,Double> transformationScore = new HashMap<>();

    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     *
     * Do not add any variables to this signature; they will not be used by
     * main().
     *
     */
    public Agent2()
    {
        transformations.put("scaled","size");
        transformations.put("angle","angle");
        transformations.put("fill","fill");
        transformations.put("location","above,inside,under,below,left,right,left-of,right-of");

        attributes.put("fill","fill");
    }

    /**
     * The primary method for solving incoming Raven's Progressive Matrices. For
     * each problem, your Agent's Solve() method will be called. At the
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
        String answer = "1";
        if ("".equals(DEBUGPROBLEM) || problem.getName().contains(DEBUGPROBLEM)) {
            println();
            println("---------------PROBLEM:" + problem.getName() + "----------------");
            println();
            RavensFigure objA = problem.getFigures().get("A");
            RavensFigure objB = problem.getFigures().get("B");
            RavensFigure objC = problem.getFigures().get("C");
            RavensFigure obj1 = problem.getFigures().get("1");
            RavensFigure obj2 = problem.getFigures().get("2");
            RavensFigure obj3 = problem.getFigures().get("3");
            RavensFigure obj4 = problem.getFigures().get("4");
            RavensFigure obj5 = problem.getFigures().get("5");
            RavensFigure obj6 = problem.getFigures().get("6");

            HashMap<String,String> ab1 = BuildTransformationFactSheet(objA, objB);
            HashMap<String,String> c11 = BuildTransformationFactSheet(objC, obj1);
            HashMap<String,String> c21 = BuildTransformationFactSheet(objC, obj2);
            HashMap<String,String> c31 = BuildTransformationFactSheet(objC, obj3);
            HashMap<String,String> c41 = BuildTransformationFactSheet(objC, obj4);
            HashMap<String,String> c51 = BuildTransformationFactSheet(objC, obj5);
            HashMap<String,String> c61 = BuildTransformationFactSheet(objC, obj6);
            PrintSheet(ab1);
            PrintSheet(c11);
            PrintSheet(c21);
            PrintSheet(c31);
            PrintSheet(c41);
            PrintSheet(c51);
            PrintSheet(c61);

            int[] score = {0,0,0,0,0,0,0};

            //--Stage 2 & 3
            //score[1]+=ScoreFactSheets(ab, c1, "1");
            int i = 1;
        }
        return answer;
    }

    //--Build transformation sheets
    private HashMap<String,String> BuildTransformationFactSheet(RavensFigure figure1, RavensFigure figure2)
    {
        HashMap<String,String> sheet = new HashMap<>();
        HashMap<String,String> ret1 = new HashMap<>();
        HashMap<String,String> ret2 = new HashMap<>();

        ret1 = extractAttributes(figure1);                                      /* Extract regular attributes */
        ret2 = extractAttributes(figure2);

        int objectsChangeCount = figure2.getObjects().size()                    /* Count how much objects changed by */
                                - figure1.getObjects().size();

        determineShapeChanges(ret1, ret2, sheet);
        determineAddsOrDels(ret1, ret2, objectsChangeCount, sheet);
        determineTransformations(ret1, ret2, sheet);

        for(Entry<String,String> entry : ret1.entrySet())
            sheet.put(figure1.getName()+"."+entry.getKey(), entry.getValue());
        for(Entry<String,String> entry : ret2.entrySet())
            sheet.put(figure2.getName()+"."+entry.getKey(), entry.getValue());

        return sheet;
    }

    /**
     * Extracts all attributes from the RavenFigure
     * @param RavenFigure
     * @return HashMap of extracted attributes renamed to their ordinal
     */
    private HashMap<String, String> extractAttributes(RavensFigure figure){
        HashMap<String, String> ret = new HashMap<>();
        HashMap<String,Integer> mapping = new HashMap<>();                      /* map objects to numbers*/

        int objCount = 0;
        for(RavensObject obj:figure.getObjects())
        {
            objCount++;                                                         /* Use object orginal instead of name */
            mapping.put(obj.getName(), objCount);
            for(RavensAttribute att:obj.getAttributes()){                       /* Add attributes */
                String newKey = String.valueOf(objCount)+"."+att.getName();
                String newValue = att.getValue();
                String tempNewValue = "";

                for(String i : newValue.split(","))                             /* Remap possiton attributes to remapped objects */
                {
                    tempNewValue += mapping.containsKey(i)?","+mapping.get(i).toString():newValue;
                }
                newValue = tempNewValue.startsWith(",")?tempNewValue.substring(1):newValue;
                ret.put(newKey, newValue);
            }
        }
        return ret;
    }

    /**
     * Determine if shapes were changed between objects
     * @param object1
     * @param object2
     * @param Updates result with change information
     */
    private void determineShapeChanges(HashMap<String,String> object1, HashMap<String,String> object2, HashMap<String,String> result){
        int cnt = 0;
        for(String entry : object1.keySet())
        {
            if (entry.toLowerCase().contains("shape"))
            {
                String shapeObject1 = object1.get(entry);
                String shapeObject2 = (object1.containsKey(entry)) ?object2.get(entry):null;
                if(shapeObject2 != null && !shapeObject1.equals(shapeObject2)){
                    result.put("tf-"+entry, "changed");
                    result.put("tf-shpe_changed", String.valueOf(++cnt));
                }
            }
        }
    }

    /**
     * Determine what objects were deleted or added
     * @param object1
     * @param object2
     * @param objectsChangeCount
     * @param Sheet updated with transformation attributes
     */
    private void determineAddsOrDels(HashMap<String, String> object1, HashMap<String, String> object2, Integer objectsChangeCount, HashMap<String, String> result){
        String t = "";
        if (objectsChangeCount > 0) t = "added";
        if (objectsChangeCount < 0) t = "deleted";

        if (t.equals("added")){                                                 /* if objects were added, switch objects around and test */
            HashMap<String, String> object3 = new HashMap<>(object1);
            object1 = new HashMap<>(object2);
            object2 = new HashMap<>(object3);
        }

        for(String entry : object1.keySet())                                    /* loop through all attributes */
        {
            if (entry.toLowerCase().contains("shape"))                          /* look for shape attribute */
            {
                String shapeObject1 = object1.get(entry);
                String shapeObject2 = (object1.containsKey(entry))              /* if shape not in object2 then null */
                                    ? object2.get(entry)
                                    : null;
                if(shapeObject2 == null && objectsChangeCount != 0)
                    result.put("tf-"+entry, t);                                 /* if shape missing tag as "added: or "deleted" */
            }
        }

        result.put("tf-obj_"+t,String.valueOf(Math.abs(objectsChangeCount)));
    }

    /**
     * Determine all transformations
     * @param object1
     * @param object2
     * @param result
     */
    private void determineTransformations(HashMap<String,String> object1,HashMap<String,String> object2,HashMap<String,String> result)
    {
        for(Entry<String,String> entry: transformations.entrySet())
        {
            String prevEntry = null;
            String transform = null;
            String key = entry.getKey();
            String value = entry.getValue();
            String[] tags = entry.getValue().split(",");

            for (String tag : tags) {
                for(Entry<String,String> obj1Entry : object1.entrySet())
                {
                    //If they both contain the same tag
                    if(obj1Entry.getKey().trim().toLowerCase().contains(tag.toLowerCase()) && object2.containsKey(obj1Entry.getKey()))
                    {
                        if(!obj1Entry.getValue().equals(object2.get(obj1Entry.getKey())))
                        {
                            transform = entry.getKey();
                            //transformValue = obj1Entry.getValue();
                            if(attributes.containsKey(transform))
                                result.put(transform, object2.get(obj1Entry.getKey()) );
                            else if(obj1Entry.getKey().contains("angle"))
                            {
                                double obj1Angle = Double.valueOf(obj1Entry.getValue());
                                double obj2Angle = Double.valueOf(object2.get(obj1Entry.getKey()));
                                result.put("tf-"+obj1Entry.getKey()+"-diff", String.valueOf(obj2Angle - obj1Angle));
                            }
                            else
                                result.put("tf-"+transform, transform); //TODO: New frame needs to be created also
                            transform = null;
                        }
                    }
                }
            }
        }
    }
    //--Build transformation sheets
    private HashMap<String,String> BuildComparisonSheet(RavensFigure figure1, RavensFigure figure2, HashMap<String,String> realObjectMapping)
    {
        HashMap<String,String> sheet = new HashMap<>(), ret1 = new HashMap<>(), ret2 = new HashMap<>(), ret3 = new HashMap<>();

        //--Extract regular attributes
        ret1 = extractAttributes(figure1);
        ret2 = extractAttributes(figure2);

        //--Re-write object with correct object mapping, save as ret3
        RewriteSheetWithMapping(ret2,realObjectMapping,ret3);
        determineShapeChanges(ret1, ret3, sheet);
        determineTransformations(ret1, ret3, sheet);
        //AreAllShapesSame(ret1, ret3, sheet);


        for(Entry<String,String> entry : ret1.entrySet())
            sheet.put(figure1.getName()+"."+entry.getKey(), entry.getValue());
        for(Entry<String,String> entry : ret3.entrySet())
            sheet.put(figure2.getName()+"."+entry.getKey(), entry.getValue());

        int cnt = figure2.getObjects().size() - figure1.getObjects().size();

        //Determine if objects were added or removed
        if(figure2.getObjects().size() > figure1.getObjects().size())
            sheet.put("tf-obj_added", String.valueOf(Math.abs(cnt)));
        else if(figure2.getObjects().size() < figure1.getObjects().size())
            sheet.put("tf-obj_deleted", String.valueOf(Math.abs(cnt)));

        return sheet;
    }

    //--Used to score the transformation knowledge sheets
    private double ScoreFactSheets(HashMap<String, String> c, HashMap<String, String> o, String num)
    {
        double score = 0;
        HashSet<String> ShapesInC = new HashSet<>();
        int shapesInCQty = 0;
        int countChanged = Integer.valueOf(c.containsKey("tf-obj_added")?c.get("tf-obj_added"):"0")
                            - Integer.valueOf(c.containsKey("tf-obj_deleted")?c.get("tf-obj_deleted"):"0");

        for(String key : c.keySet())
        {

            //match keys:values from both transformation sheets without matching shape
            String objKey = (key.split("\\.")[0].equals("A"))?"C":num;
            String exactKey = key.replaceFirst("\\w\\.", "");
            String cKey = exactKey.contains(".")?objKey+"."+exactKey:exactKey;
            String val1 = c.get(key);
            String val2 = (o.containsKey(cKey))?o.get(cKey):null;
            println(key+" : "+val1+" - "+cKey+" : "+val2);
            if(!key.contains("shape")){
                score+=(val1.equals(val2))?1:0;

            } else {
                if (cKey.contains("C")){
                    ShapesInC.add(val2);
                    shapesInCQty++;
                }
            }
        }

        //--tie brakers galore --------------------
        int shapesInAnswer=0;
        int expectedCountOfNewObjects = 0;
        if(c.containsKey("tf-shpe_added"))
            expectedCountOfNewObjects = shapesInCQty + Math.abs(countChanged);
        else if (c.containsKey("tf-shpe_deleted"))
            expectedCountOfNewObjects = shapesInCQty - Math.abs(countChanged);

        System.out.println("Expected Objects: "+expectedCountOfNewObjects);
        for(String shape:ShapesInC)
        {
            shapesInAnswer=0;
            for(String key : o.keySet())
            {
                //Most objects from C should be in answer except if shape changed from A -> B
                if(key.contains("shape") && key.contains(num) && o.get(key).equals(shape) && !c.containsKey("tf-shpe_changed") )
                    score++;

                if(key.contains("shape") && key.contains(num))
                    shapesInAnswer++;
            }
        }

        //Amount of objects in answer should match qty objects in C less deleted objects
        if(shapesInAnswer == expectedCountOfNewObjects)
            score++;

        System.out.println();

        return score;

    }



    //--Re-writes objects using a specified object mapping
    private void RewriteSheetWithMapping(HashMap<String,String> obj, HashMap<String,String> objectMapping, HashMap<String,String> ret3)
    {
        HashMap<String,String> temp = new HashMap<>(obj);
        //HashMap<String,String> ret3 = new HashMap<>();
        for(Entry<String,String> entry : objectMapping.entrySet())
        {
            for(Entry<String,String> ret2Entry : obj.entrySet())
            {
                String searchKey = ret2Entry.getKey().split("\\.")[0];
                if(entry.getValue().equals(searchKey)){
                    String newKey = ret2Entry.getKey().replace(entry.getValue()+".",entry.getKey()+".");
                    String newValue = ret2Entry.getValue();
                    //--Remap possiton attributes to remapped objects
                    for(String i : newValue.split(","))
                    {
                        if (objectMapping.containsValue(i))
                        {
                            String tempValue = "";
                            for(String keyEntry : newValue.split(",")){
                                    tempValue += ","+GetKeyByValue(objectMapping, keyEntry);
                            }
                            newValue = tempValue.substring(1);
                        }
                    }
                    ret3.put(newKey, newValue);
                    temp.remove(ret2Entry.getKey());
                }
            }
        }
        //--Fill in unmapped objects
        for(Entry<String,String> entry : temp.entrySet())
        {
            ret3.put(entry.getKey(), entry.getValue());
        }
    }

    private String GetKeyByValue(HashMap<String,String> obj, String Value)
    {
        String t = null;
        for(Entry<String,String> entry : obj.entrySet())
        {
            if (entry.getValue().equals(Value))
                return entry.getKey();
        }

        return t;
    }

    //--Print transformation sheet
    private void PrintSheet(HashMap<String, String> sheet){
        println();
        for (Entry<String, String> entry : sheet.entrySet())
            System.out.println(entry.getKey() + "\t" + entry.getValue());
    }

    //--Build the real object mapping
    private HashMap<String,String> GetRealObjectMapping(HashMap<String, HashMap<String, Double>> Obj)
    {
        HashMap<String,String> realObjectMapping = new HashMap<>();
        for(Entry<String, HashMap<String, Double>> entry : Obj.entrySet())
            for(Entry<String, Double> e : entry.getValue().entrySet())
                realObjectMapping.put(entry.getKey(), e.getKey());
        return realObjectMapping;
    }

    //Sum score of correlations
    private double GetCorrelationScore(HashMap<String, HashMap<String, Double>> Obj)
    {
        double score = 0d;
        for(Entry<String, HashMap<String, Double>> entry : Obj.entrySet())
            for(Entry<String, Double> e : entry.getValue().entrySet())
                score+=e.getValue();
        return score;
    }

    private HashMap<String, HashMap<String, Double>> GetCorrelatedObjects(RavensFigure figure1, RavensFigure figure2)
    {
        String obj1Shape, obj2Shape;

        HashMap<String, HashMap<String, Double>> correlations = new HashMap<>();
        List<Correlation> corrs = new ArrayList<>();
        for (RavensObject obj1 : figure1.getObjects()) {
            HashMap<String, Double> correlationScores = new HashMap<>();

            obj1Shape = GetAttribute(obj1, "shape");
            HashSet obj1Attributes = new HashSet();
            for (RavensAttribute att : obj1.getAttributes()) //Find all attributes in object1
            {
                //obj1Attributes.add(att.getName());
            }

            for (RavensObject obj2 : figure2.getObjects()) //Compare attributes rom object1 to object2
            {
                double i = 0, r = 0;

                obj2Shape = GetAttribute(obj2, "shape"); //compare only if they're the same shape
                if (obj1Shape.equals(obj2Shape)) {
                    for (RavensAttribute att : obj2.getAttributes()) {
                        if (obj1Attributes.contains(att.getName())) {
                            i++; //matching attributes
                        } else {
                            r++; //non-matching attributes
                        }
                    }
                }
                //calculate correlation score based on attribute names matching
                double obj1Size = obj1.getAttributes().size();
                double obj2Size = obj2.getAttributes().size();
                double objDiff = obj1Size - obj2Size;
                double correlationScore = (i / obj2Size) - (r / obj1Size) - ((objDiff / obj1Size)); //determine the correlation score
                if (correlationScores.size() > 0) { //only if the list is empty
                    for (Entry<String, Double> entry : correlationScores.entrySet()) //Only add higher scores
                    {
                        if (correlationScore > entry.getValue()) {
                            correlationScores.remove(entry.getKey());
                            correlationScores.put(obj2.getName(), correlationScore);
                        }
                    }
                } else {
                    correlationScores.put(obj2.getName(), correlationScore);
                }
            }

            //build result
            for (Entry<String, Double> entry : correlationScores.entrySet()) {
                correlations.put(obj1.getName(), correlationScores);
                corrs.add(new Correlation(obj1.getName(), entry.getKey(), entry.getValue()));
            }

        }
        return correlations;
    }

    private HashMap<String, String> GetAttributePair(RavensObject obj, String attribute)
    {
        HashMap<String, String> ret = new HashMap<>();
        for (RavensAttribute att : obj.getAttributes()) {
            if (att.getName().toLowerCase().equals(attribute)) {
                ret.put(att.getName(), att.getValue());
            }
        }
        return ret;
    }

    private String GetAttribute(RavensObject obj, String attribute)
    {
        for (RavensAttribute att : obj.getAttributes()) {
            if (att.getName().toLowerCase().equals(attribute)) {
                return att.getValue();
            }
        }
        return null;
    }

    private void println()
    {
        println("");
    }

    private void println(String text)
    {
        System.out.println(text);
    }

    private void BuildShapesKnowledge()
    {
        Shapes.put("circle", new Shape("circle", 360, true, true));
        Shapes.put("triangle", new Shape("triangle", 3, false, true));
        Shapes.put("Pac-Man", new Shape("Pac-Man", 1, true, false));
        Shapes.put("right-triangle", new Shape("right-triangle", 3, false, false));
        Shapes.put("square", new Shape("square", 4, true, true));
    }

    private class Correlation
    {

        public String _baseObject;
        public String _corrObject;
        public double _corrScore;

        public Correlation(String baseObject, String corrObject, double corrScore)
        {
            _baseObject = baseObject;
            _corrObject = corrObject;
            _corrScore = corrScore;
        }
    }

    private class Shape
    {

        public String _name;
        public int _order = 0;
        public boolean _horizontalSymmetric = false;
        public boolean _verticalSymmetric = false;

        public Shape(String name, int order, boolean hotizontalSymmetric, boolean verticalSymmetric)
        {
            _horizontalSymmetric = hotizontalSymmetric;
            _verticalSymmetric = verticalSymmetric;
            _name = name;
            _order = order;
        }

    }
}