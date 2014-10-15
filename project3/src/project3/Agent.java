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
    	String answer = "1";
         String debugProblem = "2x2 Basic Problem 06";  //**********************************************************************
         if (problem.getName().equals(debugProblem)){   //**********************************************************************
            if(DEBUG_LEVEL>=1)
    		System.out.println();
    	
            if(DEBUG_LEVEL>=3)
    		printProblem(problem);
    	

            String type = problem.getProblemType();
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
        } // comment out for single problem debugging //**********************************************************************
    		
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
	    problem_transforms.add(0,generateTransform(problem, false, "A", "B"));
	    int[] answerB2 = testTransforms2x1(problem_transforms);
	    
	    //Pick highest scoring answer
	    answerA2 = (answerA2[1] >= answerB2[1]) ? answerA2: answerB2;
            
            //********************
    	int finalAnswer = 0;
        if (answerA1[0] == answerB1[0] )
        {
        finalAnswer = answerA1[0];
        }
        else
        {
            System.out.println("here we are");
        }
        System.out.println("Robbie guessed: "+finalAnswer);
        System.out.println("Correct answer: "+problem.checkAnswer(Integer.toString(finalAnswer)));
    	return Integer.toString(finalAnswer);
    }
    
    public String solve3x3(RavensProblem problem) {
    	//TODO
    	return "1";
    }
    
    public int[] testTransforms2x1(List<List<RavensTransform>> problem_transforms) {
    	int most_similar=0, most_similar_similarity=0;
    	
    	List<RavensTransform> AB_transforms = problem_transforms.get(0);
		int AB_transform_count=0;
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
	    		if(obj2 != null) fig2_correlated.add(obj2);
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
			    		if (obj1.getAttributes().get(j).getName().equals("size") && obj2.getAttributes().get(k).getName().equals("size") &&
			    	    				obj1.getAttributes().get(j).getValue().equals(obj2.getAttributes().get(k).getValue())) correlation |= RavensTransform.CORR_SIZE;
			    		
			    		//Fills are the same
			    		if (obj1.getAttributes().get(j).getName().equals("fill") && obj2.getAttributes().get(k).getName().equals("fill") &&
			    	    				obj1.getAttributes().get(j).getValue().equals(obj2.getAttributes().get(k).getValue())) correlation |= RavensTransform.CORR_FILL;
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
    		return rt;
    	}
    	
    	rt.shape = obj1.getAttributes().get(0).getValue();
    	// Exists in figure 1, but not in figure 2
    	if(obj2 == null) { 
    		rt.transform = RavensTransform.DELETED;
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
    			if(obj1.getAttributes().get(i).getName().equals("size") && obj1.getAttributes().get(i).getName().equals(obj2.getAttributes().get(j).getName())) {
    				if((obj1.getAttributes().get(i).getValue().equals("large") && (obj2.getAttributes().get(j).getValue().equals("medium") ||
    						obj2.getAttributes().get(j).getValue().equals("small"))) || (obj1.getAttributes().get(i).getValue().equals("medium") &&
    						obj2.getAttributes().get(j).getValue().equals("small"))) rt.transform |= RavensTransform.SHRUNK;
    				if((obj1.getAttributes().get(i).getValue().equals("small") && (obj2.getAttributes().get(j).getValue().equals("medium") ||
    						obj2.getAttributes().get(j).getValue().equals("large"))) || (obj1.getAttributes().get(i).getValue().equals("medium") &&
    						obj2.getAttributes().get(j).getValue().equals("large"))) rt.transform |= RavensTransform.EXPANDED;
    				if(obj1.getAttributes().get(i).getValue().equals(obj2.getAttributes().get(j).getValue())) rt.size = obj1.getAttributes().get(i).getValue();
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
