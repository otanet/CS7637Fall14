package project3;

import java.util.*;

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
	 * Levels of debug printed (0=off,1=lowest-3=highest) 
	 */
	public static int DEBUG_LEVEL=3;
	
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    public Agent() {
    	
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
        String type = problem.getProblemType();
    	String answer = "1";
        String name = problem.getName();
        String lastTwo = name.substring(name.length()-2);
        int foo = Integer.parseInt(lastTwo);
        
        String debugProblem = "3x3 Basic Problem 13";  //**********************************************************************
        //if(problem.getName().equals(debugProblem))
        //if (type.equals("3x3") && foo < 6)   //**********************************************************************
        //{    //**********************************************************************
            if(DEBUG_LEVEL>=1)
    		System.out.println();
    	
            if(DEBUG_LEVEL>=3)
    		printProblem(problem);
    	

            
            if (type.equals("2x1")) {
                    answer = solve2x1(problem);
            } else if (type.equals("2x2")) {
                    answer = solve2x2(problem);
            } else if (type.equals("3x3")) {
                    answer = solve3x3(problem);
            } else {
                    System.out.println("ERROR: Unknown problem type");
            }

            if(DEBUG_LEVEL>=1)
                    System.out.println();
        //} // comment out for single problem debugging //**********************************************************************
    	if(Integer.parseInt(answer) < 1)
        {
            Random randomgenerator = new Random();
            Integer random = randomgenerator.nextInt(5)+1;
            answer = random.toString();
        }
        System.out.println(problem.getName());
        System.out.println("Robbie guessed: "+ answer);
        System.out.println("Correct answer: "+problem.checkAnswer(answer));

        return answer;
    }
    
    public String solve2x1(RavensProblem problem) {
    	
    	//Top-down correlation
    	List<List<RavensTransform>> problem_transforms = new ArrayList<List<RavensTransform>>();
    	problem_transforms.add(generateTransform(problem, true, "A", "B"));
    	problem_transforms.add(generateTransform(problem, true, "C", "1"));
    	problem_transforms.add(generateTransform(problem, true, "C", "2"));
    	problem_transforms.add(generateTransform(problem, true, "C", "3"));
    	problem_transforms.add(generateTransform(problem, true, "C", "4"));
    	problem_transforms.add(generateTransform(problem, true, "C", "5"));
    	problem_transforms.add(generateTransform(problem, true, "C", "6"));
    	
        System.out.println("*****Transforms generated********");
        
    	//answer index: 0-highest scoring answer, 1-similarity score
    	int[] answer = testTransforms2x1(problem_transforms);
        
        System.out.println("*****Transforms scored top-down ********");
    	
    	//Bottom-up correlation
	    problem_transforms.remove(0);
	    problem_transforms.add(0,generateTransform(problem, false, "A", "B"));
	    int[] answer2 = testTransforms2x1(problem_transforms);
            
        System.out.println("*****Transforms scored top-down ********");
	    
	    //Pick highest scoring answer
	    answer = (answer[1] >= answer2[1]) ? answer: answer2;
    	
    	return Integer.toString(answer[0]);
    }
    
    public String solve2x2(RavensProblem problem) {
    	
        //Top-down correlation
    	List<List<RavensTransform>> problem_transforms = new ArrayList<List<RavensTransform>>();
    	problem_transforms.add(generateTransform(problem, true, "A", "B"));
    	problem_transforms.add(generateTransform(problem, true, "C", "1"));
    	problem_transforms.add(generateTransform(problem, true, "C", "2"));
    	problem_transforms.add(generateTransform(problem, true, "C", "3"));
    	problem_transforms.add(generateTransform(problem, true, "C", "4"));
    	problem_transforms.add(generateTransform(problem, true, "C", "5"));
    	problem_transforms.add(generateTransform(problem, true, "C", "6"));
    	
    	//answer index: 0-highest scoring answer, 1-similarity score
    	int[] answerA1 = testTransforms2x1(problem_transforms);
    	
    	//Bottom-up correlation
	    problem_transforms.remove(0);
	    problem_transforms.add(0,generateTransform(problem, false, "A", "B"));
	    int[] answerB1 = testTransforms2x1(problem_transforms);
	    
	    //Pick highest scoring answer
	    answerA1 = (answerA1[1] >= answerB1[1]) ? answerA1: answerB1;
            
            //********************
    	
    	 //Top-down correlation
    	List<List<RavensTransform>> problem_transforms2 = new ArrayList<List<RavensTransform>>();
    	problem_transforms2.add(generateTransform(problem, true, "A", "C"));
    	problem_transforms2.add(generateTransform(problem, true, "B", "1"));
    	problem_transforms2.add(generateTransform(problem, true, "B", "2"));
    	problem_transforms2.add(generateTransform(problem, true, "B", "3"));
    	problem_transforms2.add(generateTransform(problem, true, "B", "4"));
    	problem_transforms2.add(generateTransform(problem, true, "B", "5"));
    	problem_transforms2.add(generateTransform(problem, true, "B", "6"));
    	
    	//answer index: 0-highest scoring answer, 1-similarity score
    	int[] answerA2 = testTransforms2x1(problem_transforms2);
    	
    	//Bottom-up correlation
	    problem_transforms.remove(0);
	    problem_transforms.add(0,generateTransform(problem, false, "A", "C"));
	    int[] answerB2 = testTransforms2x1(problem_transforms);
	    
	    //Pick highest scoring answer
	    answerA2 = (answerA2[1] >= answerB2[1]) ? answerA2: answerB2;
            
            //********************
    	int finalAnswer = 0;
        if (answerA1[0] == answerB1[0] )
        {
        finalAnswer = answerA1[0];
        }
        else if (answerA1[1] >= answerA2[1])
        {
        finalAnswer = answerA1[0];
        }
        else
        {
        finalAnswer = answerA2[0];
        System.out.println("error: no code for this case -WLT");
        }
    	return Integer.toString(finalAnswer);
    }
    
    public String solve3x3(RavensProblem problem) {
    	//TODO
    	//Top-down correlation
    	List<List<RavensTransform>> setup_transforms_horz = new ArrayList<List<RavensTransform>>();
        List<List<RavensTransform>> setup_transforms_vert = new ArrayList<List<RavensTransform>>();
        List<List<RavensTransform>> soln_transforms_horz = new ArrayList<List<RavensTransform>>();
        List<List<RavensTransform>> soln_transforms_vert = new ArrayList<List<RavensTransform>>();
        
    	setup_transforms_horz.add(generateTransform(problem, true, "A", "B")); //A  B  C
    	setup_transforms_horz.add(generateTransform(problem, true, "B", "C")); //D  E  F
    	setup_transforms_horz.add(generateTransform(problem, true, "D", "E")); //G  H  123456
    	setup_transforms_horz.add(generateTransform(problem, true, "E", "F"));
    	setup_transforms_horz.add(generateTransform(problem, true, "G", "H"));
        
        setup_transforms_vert.add(generateTransform(problem, true, "A", "D"));
    	setup_transforms_vert.add(generateTransform(problem, true, "D", "G")); 
    	setup_transforms_vert.add(generateTransform(problem, true, "B", "E"));
        setup_transforms_vert.add(generateTransform(problem, true, "E", "H"));
        setup_transforms_vert.add(generateTransform(problem, true, "C", "F"));
        
        soln_transforms_horz.add(generateTransform(problem, true, "H", "1")); //A  B  C
    	soln_transforms_horz.add(generateTransform(problem, true, "H", "2")); //D  E  F
    	soln_transforms_horz.add(generateTransform(problem, true, "H", "3")); //G  H  123456
    	soln_transforms_horz.add(generateTransform(problem, true, "H", "4"));
    	soln_transforms_horz.add(generateTransform(problem, true, "H", "5"));
        soln_transforms_horz.add(generateTransform(problem, true, "H", "6"));
        
        soln_transforms_vert.add(generateTransform(problem, true, "F", "1")); //A  B  C
    	soln_transforms_vert.add(generateTransform(problem, true, "F", "2")); //D  E  F
    	soln_transforms_vert.add(generateTransform(problem, true, "F", "3")); //G  H  123456
    	soln_transforms_vert.add(generateTransform(problem, true, "F", "4"));
    	soln_transforms_vert.add(generateTransform(problem, true, "F", "5"));
        soln_transforms_vert.add(generateTransform(problem, true, "F", "6"));

        String classification = classify(setup_transforms_horz , setup_transforms_vert);
        
        String answer = "0";
        
        switch (classification)
        {
            case "exact_horz_vert":
                answer = exact(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
                
            case "shape_change":
                answer = shape_change(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
              
            case "single_shape_added": 
                answer = shape_added(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
                
            case "size_increase":
                answer = size_increase(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
                
            case "double_increase":
                answer = double_increase(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
                
            case "shape_removed":
                answer = shape_removed(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
                
            case "shape_added_changed":
                answer = shape_added_changed(setup_transforms_horz, setup_transforms_vert, soln_transforms_horz, soln_transforms_vert);
                break;
                
            default: 
                Random randomgenerator = new Random();
                Integer random = randomgenerator.nextInt(5)+1;
                answer = random.toString();
                break;
                
        }
        
    	return answer;

    }
    
    public String shape_added_changed(List<List<RavensTransform>>horzSetup, 
            List<List<RavensTransform>>vertSetup, 
            List<List<RavensTransform>>horzSoln, 
            List<List<RavensTransform>>vertSoln)
    {
        Integer answer = 3;
        for(int i=0; i < horzSoln.size(); i++)
        {
            if((horzSoln.get(i).size()-horzSetup.get(4).size())==6
                    && (vertSoln.get(i).size()-vertSetup.get(4).size())==3)
            {
                answer = i + 1;
            }
        }
        return answer.toString();
    }
    
    public String shape_removed(List<List<RavensTransform>>horzSetup, 
            List<List<RavensTransform>>vertSetup, 
            List<List<RavensTransform>>horzSoln, 
            List<List<RavensTransform>>vertSoln)
    {
        Integer answer = 0;
        for(int i=0; i<horzSoln.size();i++)
        {
            if((horzSoln.get(i).size()==1 && horzSoln.get(i).get(0).transform==1))
            {
                answer = i + 1;
            }
        }
        return answer.toString();
    }
    
    public String double_increase(List<List<RavensTransform>>horzSetup, 
            List<List<RavensTransform>>vertSetup, 
            List<List<RavensTransform>>horzSoln, 
            List<List<RavensTransform>>vertSoln)
    {
        ArrayList<Integer> horzList = new ArrayList();
        ArrayList<Integer> vertList = new ArrayList();
        ArrayList<Integer> removeList = new ArrayList();
        
        Integer answer = 0;
        for(int i=0; i<horzSoln.size();i++)
        {
            if(horzSoln.get(i).size() - horzSetup.get(4).size()==3)
            {
                answer = i;
                horzList.add(i);
            }
        }
        if (horzList.size() >1)
        {
            for(int i=0; i<horzList.size();i++)
            {
                int rotation1 = horzSetup.get(0).get(0).rotation;
                int rotation2 = horzSetup.get(1).get(0).rotation;
                
                if(horzSoln.get(horzList.get(i)).get(0).rotation != rotation2)
                {
                    removeList.add(i);
                }
            }
            for(int i=0; i<removeList.size();i++)
            {
                horzList.remove(removeList.get(i));
            }
        }
                
        answer = horzList.get(0)+1;
        return answer.toString();
    }
    
    public String size_increase(List<List<RavensTransform>>horzSetup, 
            List<List<RavensTransform>>vertSetup, 
            List<List<RavensTransform>>horzSoln, 
            List<List<RavensTransform>>vertSoln)
    {
        Integer answer = 0;
        for(int i=0; i<horzSoln.size();i++)
        {
            if(horzSoln.get(i).get(0).transform == 8)
            {
                answer = i + 1;
            }
        }
        return answer.toString();
    }
    
    public String shape_added(List<List<RavensTransform>>horzSetup, List<List<RavensTransform>>vertSetup, List<List<RavensTransform>>horzSoln, List<List<RavensTransform>>vertSoln)
    {
        Integer answer = 3;
        for(int i=0; i < horzSoln.size(); i++)
        {
            if((horzSoln.get(i).size()-horzSetup.get(4).size())==1
                    && (vertSoln.get(i).size()-vertSetup.get(4).size())==1)
            {
                answer = i + 1;
            }
        }
        return answer.toString();
    }
        public String shape_change(List<List<RavensTransform>>horzSetup, List<List<RavensTransform>>vertSetup, List<List<RavensTransform>>horzSoln, List<List<RavensTransform>>vertSoln)
    {
        Integer answer = -1;
        Boolean change = true;
        ArrayList<Integer> horzList = new ArrayList();
        ArrayList<Integer> vertList = new ArrayList();
        
        for(int i = 0; i<horzSoln.size();i++)
        {
            change = true;
            for (int j=0; j < (horzSoln.get(j).size()/2); j++)
            {
                if(horzSoln.get(i).size() % 2 ==1 )
                {
                    change = false;
                    continue;
                }
                {   //the first one needs to be Deleted.
                    if (horzSoln.get(i).get(j).transform != RavensTransform.DELETED)
                    {
                        change = change && false;
                    }
                    if (horzSoln.get(i).get(j).transform != RavensTransform.DELETED)
                    {
                        change = change && false;
                    }
                //the first one needs to be Added.

                } 
            }
            if (change == true)
            horzList.add(i);
        }
        
        for(int i = 0; i<vertSoln.size();i++)
        {
            change = true;
            for (int j=0; j < (vertSoln.get(j).size()/2); j++)
            {
                if(vertSoln.get(i).size() % 2 ==1 )
                {
                    change = false;
                    continue;
                }
                {   //the first one needs to be Deleted.
                    if (vertSoln.get(i).get(j).transform != RavensTransform.DELETED)
                    {
                        change = change && false;
                    }
                    if (vertSoln.get(i).get(j).transform != RavensTransform.DELETED)
                    {
                        change = change && false;
                    }
                //the first one needs to be Added.

                } 
            }
            if (change == true)
            vertList.add(i);
        }
        
        ArrayList<Integer> possibleList = new ArrayList();
        for(int i=0; i<horzList.size(); i++)
            for(int j=0; j<vertList.size(); j++)
                if (horzList.get(i)==vertList.get(j))
                        {
                            possibleList.add(horzList.get(i));
                        }
        String firstShape = horzSetup.get(4).get(1).shape;
        for (int i=0; i<possibleList.size();i++)
        {
            String testShape = horzSoln.get(possibleList.get(i)).get(3).shape;
            if (firstShape.equals(testShape))
            {
                possibleList.remove(i);
            }
            if (possibleList.size() == 1)
                break;
        }
        
        answer = possibleList.get(0) + 1;
        return answer.toString(); 
    }
        
    public String exact(List<List<RavensTransform>>horzSetup, List<List<RavensTransform>>vertSetup, List<List<RavensTransform>>horzSoln, List<List<RavensTransform>>vertSoln)
    {
        Integer answer = -1;
        ArrayList<Integer> HorzList = new ArrayList();
        ArrayList<Integer> VertList = new ArrayList(); 
        
        for(Integer i = 0; i< horzSoln.size();i++)
                {   Boolean test = true;
                    //HORZ
                    //setup.get(3) is the transform between E and F.
                    if (horzSoln.get(i).size()==horzSetup.get(3).size())
                    {
                        if (horzSoln.get(i).get(0).transform == horzSetup.get(3).get(0).transform
                            && vertSoln.get(i).get(0).transform == vertSetup.get(3).get(0).transform)
                        {
                            for (int j = 0; j < horzSetup.get(j).size(); j++)
                            {
                                if(horzSetup.size()<3)
                                    continue;
                                    if (horzSoln.get(i).get(j).fill.equals(horzSetup.get(3).get(j).fill)
                                            && vertSoln.get(i).get(j).fill.equals(vertSetup.get(3).get(j).fill))
                                    {
                                        test = test && true;
                                    }
                                    else 
                                    {
                                        test = test && false;
                                    }
                               
                            }
                                if (test)
                                {
                                    answer = i;
                                    HorzList.add(answer);
                                }
                        }
                    }
                }
        if (HorzList.size()>1)
        {
            for (int i=0; i<HorzList.size();i++)
            {
                List<RavensTransform> testSoln = vertSoln.get(HorzList.get(i));
                if(testSoln.size()!=vertSetup.get(1).size())
                {
                    HorzList.remove(i);
                }
                if(horzSetup.get(0).get(0).rotation == horzSetup.get(1).get(0).rotation
                        && horzSetup.get(2).get(0).rotation == horzSetup.get(3).get(0).rotation
                        && horzSoln.get(HorzList.get(i)).get(0).rotation == horzSetup.get(2).get(0).rotation
                        )
                {
                    HorzList.set(0,HorzList.get(i));
                }
            }
        }
        else if(HorzList.size()==0)
            HorzList.add(3);

        answer = HorzList.get(0)+1;
        return answer.toString();
    }

    public String classify(List<List<RavensTransform>> horz, List<List<RavensTransform>> vert)
    {
        //Classification for horizontal and vertical transformations
        String classification = "";
        // the sizes of the transforms need to be the same (
        if(horz.get(0).size()==horz.get(2).size()               //A 0 B 1 C
                //                                              //D 2 E 3 F
                && horz.get(2).size()==horz.get(4).size()       //G 4 H
                && vert.get(0).size()==vert.get(2).size()
                && vert.get(2).size()==vert.get(4).size()
                //but there can' be more than 3 transforms in each one.
                && horz.get(0).size() < 4
                && vert.get(0).size() <4 
                && horz.get(1).get(0).transform != 8
                && horz.get(2).get(0).transform != 8
                && horz.get(3).get(0).transform != 8)
        {
            //passing the first test for exact transforms.
            classification = "exact_horz_vert";
        }
                
        //check to see if this is a shape changing problem
        else if(horz.get(0).size()>1
                && horz.get(0).get(0).transform == horz.get(0).get(1).transform
                && horz.get(0).get(0).transform == RavensTransform.DELETED
                && horz.get(0).size() > 3)
        {
            classification = "shape_change";
        }
        else if(horz.get(2).size()-horz.get(0).size()==1
                && horz.get(3).size()-horz.get(1).size()==1)
        {
            classification = "single_shape_added";
        }
        
        else if(horz.get(1).get(0).transform == 8
                && horz.get(2).get(0).transform == 8
                && horz.get(3).get(0).transform == 8
                && horz.get(4).get(0).transform == 8)
        {
            classification = "size_increase";
        }
        
        else if((horz.get(1).size() - horz.get(0).size())==1
                && horz.get(3).size() - horz.get(2).size()==2)
        {
            classification = "double_increase";
        }
        
        else if((horz.get(1).size() - horz.get(0).size())==2
                && horz.get(3).size() - horz.get(2).size()==4)
        {
            classification = "shape_added_changed";
        }
        
        else if((horz.get(1).size() - horz.get(0).size())==1
                && horz.get(3).size() - horz.get(2).size()==2)
        {
            classification = "double_increase";
        }
        
        else if((horz.get(0).size() - horz.get(1).size())==1
                && horz.get(2).size() - horz.get(3).size()==1)
        {
            classification = "shape_removed";
        }
        
        
        //classification for general transformation
        return classification;
    }
    public int[] testTransforms2x1(List<List<RavensTransform>> problem_transforms) {
    	int most_similar=0, most_similar_similarity=0;
    	
    	List<RavensTransform> AB_transforms = problem_transforms.get(0);
		int AB_transform_count=0;
                //AB_transform_count = AB_transforms.size();
		for(int i=0; i < AB_transforms.size(); i++) {
			if(AB_transforms.get(i).transform != 0) AB_transform_count++;
		}
		
    	for(int i=1; i < problem_transforms.size(); i++) {
    		int similarity=0;
    		
        	/*** SMART TESTER ***/
    		/*** Apply similarity weights ***/
    		
    		//Same # of transforms (not counting UNCHANGED)
    		int problem_transform_count=0;
    		for(int k=0; k < problem_transforms.get(i).size(); k++) {
    			if(problem_transforms.get(i).get(k).transform != 0) problem_transform_count++;
    		}
    		if(AB_transform_count == problem_transform_count) similarity += 2;
    		
    		for(int j=0; j < AB_transforms.size(); j++) {
				List<String> checked = new ArrayList<String>();
    			for(int k=0; k < problem_transforms.get(i).size(); k++) {
    				if(DEBUG_LEVEL>=1)
    					System.out.println("AB_shape/transform: " + AB_transforms.get(j).shape + "/" + AB_transforms.get(j).transform +
    						", C shape/transform: " + problem_transforms.get(i).get(k).shape + "/" + problem_transforms.get(i).get(k).transform);
    				
    				//Transform matches
    				if(problem_transforms.get(i).get(k).transform == AB_transforms.get(j).transform) {
    					similarity++;
    					//Larger weight for non UNCHANGED/DELETED/ADDED
    					if(AB_transforms.get(j).transform > RavensTransform.ADDED) similarity++;
    				}
    				
    				//Shape AND transform matches
    				if(problem_transforms.get(i).get(k).shape.equals(AB_transforms.get(j).shape) &&
    				problem_transforms.get(i).get(k).transform == AB_transforms.get(j).transform) {
    					similarity++;
    					//Larger weight for non UNCHANGED/DELETED/ADDED
    					if(AB_transforms.get(j).transform > RavensTransform.ADDED) similarity++;
    				}
    				
    				//Size AND transform matches
    				if(problem_transforms.get(i).get(k).size.equals(AB_transforms.get(j).size) && !problem_transforms.get(i).get(k).size.equals("") &&
    				problem_transforms.get(i).get(k).transform == AB_transforms.get(j).transform) {
    					similarity++;
    					//Larger weight for non UNCHANGED/DELETED/ADDED
    					if(AB_transforms.get(j).transform > RavensTransform.ADDED) similarity++;
    				}
    				
    				//Rotation matches
    				if((problem_transforms.get(i).get(k).transform & RavensTransform.ROTATED) > 0 &&
    						(AB_transforms.get(j).transform & RavensTransform.ROTATED) > 0) {
        				if((problem_transforms.get(i).get(k).rotation == AB_transforms.get(j).rotation) && 
        						problem_transforms.get(i).get(k).rotation != 0) similarity++;
    				}
    				
    				//Fill
    				if((problem_transforms.get(i).get(k).transform & RavensTransform.FILL_CHANGED) > 0 &&
    						(AB_transforms.get(j).transform & RavensTransform.FILL_CHANGED) > 0) {
    					if(DEBUG_LEVEL>=2)
    						System.out.println("AB_shape/fill: " + AB_transforms.get(j).shape + "/" + AB_transforms.get(j).fill_transform +
        						", C shape/fill: " + problem_transforms.get(i).get(k).shape + "/" + problem_transforms.get(i).get(k).fill_transform);
        				for(int m=0; m<problem_transforms.get(i).get(k).fill_transform.size(); m++) {
        					if(!checked.contains(problem_transforms.get(i).get(k).fill_transform.get(m))) {
        						if(AB_transforms.get(j).fill_transform.contains(problem_transforms.get(i).get(k).fill_transform.get(m))) {
        							similarity++;
        							//Larger weight if size matches
        							if(AB_transforms.get(j).size.equals(problem_transforms.get(i).get(k).size)) similarity++;
        						} else similarity--;
        					} else similarity--;
        					checked.add(problem_transforms.get(i).get(k).fill_transform.get(m));
        				}
    				}
    				
    				if(DEBUG_LEVEL>=3) {
	    				System.out.print("AB: ");
	    				AB_transforms.get(j).printTransform();
	    				System.out.print("C: ");
	    				problem_transforms.get(i).get(k).printTransform();
                                        System.out.println("*******");
    				}
    			}
    		}
    		if(similarity > most_similar_similarity) {
    			most_similar_similarity = similarity;
    			most_similar = i;
    		}
        	if(DEBUG_LEVEL>=1)
        	System.out.println("C:" + i + " similarity = " + similarity);
    	}

    	int[] answer = new int[2];
    	answer[0] = most_similar;
    	answer[1] = most_similar_similarity;
    	return answer;
    }
    
    public List<RavensTransform> generateTransform(RavensProblem problem, boolean forward, String fig1, String fig2) {

    	if(DEBUG_LEVEL>=1)
    		System.out.println(fig1 + ":" + fig2);
    	
    	List<RavensTransform> transforms = new ArrayList<RavensTransform>();
    	List<RavensObject> fig2_correlated = new ArrayList<RavensObject>();

    	/*** SMART GENERATOR ***/
    	if(forward) {
	    	for (int i=0; i < problem.getFigures().get(fig1).getObjects().size(); i++) {
	    		RavensObject obj1 = problem.getFigures().get(fig1).getObjects().get(i);
	    		RavensObject obj2 = correlateObject(obj1, problem.getFigures().get(fig2), fig2_correlated);
	    		if(obj2 != null) 
                        {
                            fig2_correlated.add(obj2);
                        }
                        
	    		transforms.add(calculateTransform(obj1, obj2));
	    		
	    		if(DEBUG_LEVEL>=1) {
		    		String nm;
		    		if(obj2 == null) {nm = "null";} else {nm = obj2.getName();}
		    		System.out.println("Correlated objects: " + obj1.getName() + ":" + nm);
	    		}
	    		
	    	}
    	} else {
	        for (int i=problem.getFigures().get(fig1).getObjects().size()-1; i >=0; i--) {
	    		RavensObject obj1 = problem.getFigures().get(fig1).getObjects().get(i);
	    		RavensObject obj2 = correlateObject(obj1, problem.getFigures().get(fig2), fig2_correlated);
	    		if(obj2 != null) fig2_correlated.add(obj2);
	    		transforms.add(calculateTransform(obj1, obj2));
	    		
	    		if(DEBUG_LEVEL>=1) {
		    		String nm;
		    		if(obj2 == null) {nm = "null";} else {nm = obj2.getName();}
		    		System.out.println("Correlated objects: " + obj1.getName() + ":" + nm);
	    		}
	    		
	    	}
    	}
    	
    	for (int i=0; i < problem.getFigures().get(fig2).getObjects().size(); i++) {
    		RavensObject obj2 = problem.getFigures().get(fig2).getObjects().get(i);
    		if(!fig2_correlated.contains(obj2)) {
    			transforms.add(calculateTransform(null,obj2));
    			
        		if(DEBUG_LEVEL>=1)
        			System.out.println("Correlated objects: null:" + obj2.getName());
        		
    		}
    	}
    	if(DEBUG_LEVEL>=1)
    		System.out.println();
    	
    	return transforms;
    }
    
    public RavensObject correlateObject(RavensObject obj1, RavensFigure fig, List<RavensObject> already_correlated) {
    	
    	RavensObject correlated_obj = null;
    	long best_correlation = 0;
    	for (int i=0; i < fig.getObjects().size(); i++) {
    		RavensObject obj2 = fig.getObjects().get(i);
    		if(DEBUG_LEVEL>=2)
    			System.out.println("obj1: " + obj1.getName() + ", obj2: " + obj2.getName());
    		
    		if(already_correlated.contains(obj2)) continue;
    		
    		long correlation = 0;
			boolean shape_match = false;
    		for(int j=0; j < obj1.getAttributes().size(); j++) {
    			for(int k=0; k < obj2.getAttributes().size(); k++) {
    		
		    		/*** PRODUCTION RULES FOR CORRELATING OBJECTS ***/    		
		    		//Shapes are the same
		    		if (obj1.getAttributes().get(j).getName().equals("shape") && obj2.getAttributes().get(k).getName().equals("shape") &&
		    				obj1.getAttributes().get(j).getValue().equals(obj2.getAttributes().get(k).getValue())) {	
		    			correlation |= RavensTransform.CORR_SHAPE;
		    			shape_match = true;
		    		}
		    		
		    		//Check other attributes if shape matches
		    		if(shape_match) {
		    			
			    		//Sizes are the same
			    		if (obj1.getAttributes().get(j).getName().equals("size") 
                                                && obj2.getAttributes().get(k).getName().equals("size") 
                                                && obj1.getAttributes().get(j).getValue().equals(obj2.getAttributes().get(k).getValue()))
                                            correlation |= RavensTransform.CORR_SIZE;
			    		
			    		//Fills are the same
			    		if (obj1.getAttributes().get(j).getName().equals("fill") 
                                                && obj2.getAttributes().get(k).getName().equals("fill") 
                                                && obj1.getAttributes().get(j).getValue().equals(obj2.getAttributes().get(k).getValue())) 
                                            correlation |= RavensTransform.CORR_FILL;
		    		}
                                
    			}
    		}
    		
    		if(best_correlation < correlation) {
    			best_correlation = correlation;
    			correlated_obj = obj2;
    		}


    		if(DEBUG_LEVEL>=2)
    			if(correlated_obj != null) System.out.println("Corr: " + obj2.getName() + ", value = " + correlation);
    	}
    	
    	return correlated_obj;
    }
    
    public RavensTransform calculateTransform(RavensObject obj1, RavensObject obj2) {
    	
    	RavensTransform rt = new RavensTransform();
    	
    	/*** PRODUCTION RULES FOR SPECIFYING TRANSFORMS ***/
    	
        rt.transform = RavensTransform.UNCHANGED;
        
    	// Exists in figure 2, but not in figure 1
    	if(obj1 == null) {
    		rt.shape = obj2.getAttributes().get(0).getValue();
    		rt.transform = RavensTransform.ADDED;
                for(int j=0; j < obj2.getAttributes().size(); j++)
                {
                    if(obj2.getAttributes().get(j).getName().equals("fill"))
                    {
                        rt.fill = obj2.getAttributes().get(j).getValue();
                    }
                    else
                    {
                        rt.fill = "-1";
                    }
                }
    		return rt;
    	}
    	
    	rt.shape = obj1.getAttributes().get(0).getValue();
        
    	// Exists in figure 1, but not in figure 2
    	if(obj2 == null) { 
    		rt.transform = RavensTransform.DELETED;
                for(int j=0; j < obj1.getAttributes().size(); j++)
                {
                    if(obj1.getAttributes().get(j).getName().equals("fill"))
                    {
                        rt.fill = obj1.getAttributes().get(j).getValue();
                    }
                    else
                    {
                        rt.fill = "-1";
                    }
                }
    		return rt;
    	}
    	
    	String attr="";
    	List<String> obj1AttrNames = getAttrNames(obj1);
    	List<String> obj2AttrNames = getAttrNames(obj2);
    	
    	//Moved above or below
    	attr = "above";
    	if(!obj1AttrNames.contains(attr) && obj2AttrNames.contains(attr)) rt.transform |= RavensTransform.MOVED_ABOVE;
    	if(obj1AttrNames.contains(attr) && !obj2AttrNames.contains(attr)) rt.transform |= RavensTransform.MOVED_BELOW;
    	
    	//Moved left or right of
    	attr = "left-of";
    	if(!obj1AttrNames.contains(attr) && obj2AttrNames.contains(attr)) rt.transform |= RavensTransform.MOVED_LEFT_OF;
    	if(obj1AttrNames.contains(attr) && !obj2AttrNames.contains(attr)) rt.transform |= RavensTransform.MOVED_RIGHT_OF;
    	
    	//Moved inside or outside
    	attr = "inside";
    	if(!obj1AttrNames.contains(attr) && obj2AttrNames.contains(attr)) rt.transform |= RavensTransform.MOVED_INSIDE;
    	if(obj1AttrNames.contains(attr) && !obj2AttrNames.contains(attr)) rt.transform |= RavensTransform.MOVED_OUTSIDE;
    	
		String obj1Shape="", obj2Shape="";
    	for(int i=0; i < obj1.getAttributes().size(); i++) {
    		for(int j=0; j < obj2.getAttributes().size(); j++) {
    			if(obj1.getAttributes().get(i).getName().equals("shape")) obj1Shape = obj1.getAttributes().get(i).getValue();
    			if(obj2.getAttributes().get(j).getName().equals("shape")) obj2Shape = obj2.getAttributes().get(j).getValue();
    			
    			//Fill
    			if(obj1.getAttributes().get(i).getName().equals("fill") && obj1.getAttributes().get(i).getName().equals(obj2.getAttributes().get(j).getName())) {
    				if(obj1.getAttributes().get(i).getValue().equals(obj2.getAttributes().get(j).getValue())) {
    					rt.fill_transform.add("UNCHANGED");
    				} else {
        				rt.transform |= RavensTransform.FILL_CHANGED;
	    				String[] obj1Fill = obj1.getAttributes().get(i).getValue().split(",");
	    				String[] obj2Fill = obj2.getAttributes().get(j).getValue().split(",");
	    				
	    				for(int m=0; m < obj1Fill.length; m++) {
	    					if(!(Arrays.asList(obj2Fill).contains(obj1Fill[m]))) rt.fill_transform.add("DEL-" + obj1Fill[m]);
	    				}
	    				for(int m=0; m < obj2Fill.length; m++) {
	    					if(!(Arrays.asList(obj1Fill).contains(obj2Fill[m]))) rt.fill_transform.add("ADD-" + obj2Fill[m]);
	    				}
    				}
    				if(DEBUG_LEVEL>=2) {
	    				System.out.print("fill trans: ");
	    				for(int m=0; m<rt.fill_transform.size(); m++) System.out.print(rt.fill_transform.get(m) + " ");
	    				System.out.println();
    				}
    			}
    			
    			//Shrunk or expanded
    			if(obj1.getAttributes().get(i).getName().equals("size") && obj1.getAttributes().get(i).getName().equals(obj2.getAttributes().get(j).getName())) 
                        {
    				if((obj1.getAttributes().get(i).getValue().equals("large") 
                                        &&    (obj2.getAttributes().get(j).getValue().equals("medium") 
                                            || obj2.getAttributes().get(j).getValue().equals("small") 
                                            || obj2.getAttributes().get(j).getValue().equals("very-small") ) )
                                        
                                        
                                        || (obj1.getAttributes().get(i).getValue().equals("medium") 
                                            &&    ((obj2.getAttributes().get(j).getValue().equals("small")) 
                                                ||  obj2.getAttributes().get(j).getValue().equals("very-small")) ) 
                                        
                                        || (obj1.getAttributes().get(i).getValue().equals("very-large") 
                                            &&    ((obj2.getAttributes().get(j).getValue().equals("large")) 
                                                ||  obj2.getAttributes().get(j).getValue().equals("medium"))
                                                ||  obj2.getAttributes().get(j).getValue().equals("very-small"))) 
                                    
                                    rt.transform |= RavensTransform.SHRUNK;
    				
                                if(
                                        (obj1.getAttributes().get(i).getValue().equals("small") 
                                        && (obj2.getAttributes().get(j).getValue().equals("medium") 
                                            || obj2.getAttributes().get(j).getValue().equals("large"))
                                            || obj2.getAttributes().get(j).getValue().equals("very-large"))
                                        
                                     || (obj1.getAttributes().get(i).getValue().equals("medium") 
                                        && (obj2.getAttributes().get(j).getValue().equals("large")
                                            || obj2.getAttributes().get(j).getValue().equals("very-large")))
                                        
                                     || (obj1.getAttributes().get(i).getValue().equals("large") 
                                        && obj2.getAttributes().get(j).getValue().equals("very-large"))
                                        ) 
                                    
                                    rt.transform |= RavensTransform.EXPANDED;
    				if(obj1.getAttributes().get(i).getValue().equals(obj2.getAttributes().get(j).getValue())) 
                                    rt.size = obj1.getAttributes().get(i).getValue();
    			}
    			
    			//Rotated
    			attr = "angle";
    			if(obj1.getAttributes().get(i).getName().equals("angle") && obj1.getAttributes().get(i).getName().equals(obj2.getAttributes().get(j).getName())) {
    				if(!obj1.getAttributes().get(i).getValue().equals(obj2.getAttributes().get(j).getValue())) {
    					rt.transform |= RavensTransform.ROTATED;
    					rt.rotation = Math.abs(Integer.parseInt(obj2.getAttributes().get(j).getValue()) - Integer.parseInt(obj1.getAttributes().get(i).getValue()));
    				}
    			} else if(obj1.getAttributes().get(i).getName().equals("angle") && !obj2AttrNames.contains(attr)) {
    				if(Integer.parseInt(obj1.getAttributes().get(i).getValue()) != 0) {
    					rt.transform = rt.transform | RavensTransform.ROTATED;
    					rt.rotation = Integer.parseInt(obj1.getAttributes().get(i).getValue());
    				}
    			} else if(obj2.getAttributes().get(j).getName().equals("angle") && !obj1AttrNames.contains(attr)) {
    				if(Integer.parseInt(obj2.getAttributes().get(j).getValue()) != 0) {
    					rt.transform = rt.transform | RavensTransform.ROTATED;
    					rt.rotation = Integer.parseInt(obj2.getAttributes().get(j).getValue());
    				}
    			}
    			
    			//Flipped
    			attr = "angle";
    	    	if(obj1.getAttributes().get(i).getName().equals("angle") && obj1.getAttributes().get(i).getName().equals(obj2.getAttributes().get(j).getName())) {
    	   			if((Integer.parseInt(obj1.getAttributes().get(i).getValue()) == 0 && Integer.parseInt(obj2.getAttributes().get(j).getValue()) == 180) ||
    	   					(Integer.parseInt(obj1.getAttributes().get(i).getValue()) == 180 && Integer.parseInt(obj2.getAttributes().get(j).getValue()) == 0)) {
     					rt.transform |= RavensTransform.FLIPPED_VERTICALLY;
    	   			}
    	   			if((Integer.parseInt(obj1.getAttributes().get(i).getValue()) == 90 && Integer.parseInt(obj2.getAttributes().get(j).getValue()) == 270) ||
    	   					(Integer.parseInt(obj1.getAttributes().get(i).getValue()) == 270 && Integer.parseInt(obj2.getAttributes().get(j).getValue()) == 90)) {
     					rt.transform |= RavensTransform.FLIPPED_HORIZONTALLY;
    	   			}
    	   			//Special case for uniform shapes
    	   			if(obj1.getAttributes().get(i).getValue().equals(obj2.getAttributes().get(j).getValue())) {
    	   				if(obj1Shape.equals(obj2Shape)) {
    	   					if(obj1Shape.equals("circle") || obj1Shape.equals("square") || obj1Shape.equals("triangle")) {
    	     					rt.transform |= RavensTransform.FLIPPED_HORIZONTALLY;
    	     					rt.transform |= RavensTransform.ROTATED;
    	   					}
    	   					if(obj1Shape.equals("circle") || obj1Shape.equals("square")) {
    	     					rt.transform |= RavensTransform.FLIPPED_VERTICALLY;
    	     					rt.transform |= RavensTransform.ROTATED;
    	   					}
    	   				}
    	   			}
                                //Special case for non-uniform shapes
                                if(obj1Shape.equals(obj2Shape)) 
                                {
                                        if(obj1Shape.equals("right-triangle")) 
                                        {
                                            if(obj1.getAttributes().get(i).getValue().equals("180") && obj2.getAttributes().get(j).getValue().equals("90"))
                                            {
                                                rt.transform |= RavensTransform.FLIPPED_HORIZONTALLY;
                                                rt.transform ^= RavensTransform.ROTATED;
                                                rt.rotation = 0;
                                            }
                                            if(obj1.getAttributes().get(i).getValue().equals("270") && obj2.getAttributes().get(j).getValue().equals("0"))
                                            {
                                                rt.transform |= RavensTransform.FLIPPED_HORIZONTALLY;
                                                rt.transform ^= RavensTransform.ROTATED;
                                                rt.rotation = 0;
                                            }
                                        
                                        }
                                }
    	    	}

    		}
    	}
    	rt.fill = "-1";
    	return rt;
    }
    
    public List<String> getAttrNames(RavensObject obj) {
    	List<String> names = new ArrayList<String>();
    	
    	for(int i=0; i < obj.getAttributes().size(); i++) {
    		names.add(obj.getAttributes().get(i).getName());
    	}
    	
    	return names;
    }
    
    public void printProblem(RavensProblem problem) {
    	System.out.println("Type: " + problem.getProblemType() + "\n");
    	for (String key: problem.getFigures().keySet()) {
    		System.out.println("Figure: " + key);
	    	for (int i=0; i < problem.getFigures().get(key).getObjects().size(); i++) {
	    		System.out.println("  Node: " + problem.getFigures().get(key).getObjects().get(i).getName());
	    		for (int j=0; j < problem.getFigures().get(key).getObjects().get(i).getAttributes().size(); j++) {
	    			System.out.println("    " + problem.getFigures().get(key).getObjects().get(i).getAttributes().get(j).getName() +
	    					": " + problem.getFigures().get(key).getObjects().get(i).getAttributes().get(j).getValue());
	    		}
	    	}
	    	System.out.println();
    	}
    }
    
    
}
