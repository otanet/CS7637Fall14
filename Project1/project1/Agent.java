package project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	Integer index = 1;
	
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
        //Variables
    	ArrayList<ObjectTrans> transformations = new ArrayList<>();
    	ObjectTrans goal;
    	String answer="0";
    	ArrayList<String> options = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6"));
    	
    	
    	
    //Tell console what you're doing
    	System.out.println("Agent.java iteration = " + index + " Problem Name = " + problem.getName() + "");
    	
    //Get initial pieces and make the goal transformation difference array
    	HashMap<String, RavensFigure> figures = problem.getFigures();
    	RavensFigure A = figures.get("A");
    	RavensFigure B = figures.get("B");
    	
    	goal = new ObjectTrans(A.getObjects().get(0),B.getObjects().get(0));
    	System.out.println("-------GOAL-------");
    	for(int x=0; x<goal.getDiffArray().size(); x++)
    	{
    		System.out.println(goal.getDiffArray().get(x));
    	}
    	
    //Get figure C and prep for looping through the 6 guesses.
    	RavensFigure C = figures.get("C");
    	
    	for(int guesses = 0; guesses < 6; guesses++)
    	{
    	//RavensFigure oneFigure = figures.get("1");
    	ObjectTrans temp = new ObjectTrans(C.getObjects().get(0),figures.get(options.get(guesses)).getObjects().get(0));
    	transformations.add(temp);
    	System.out.println("-------First Guess-------");
    	for(int x=0; x<temp.getDiffArray().size(); x++)
    		{
    			System.out.println(temp.getDiffArray().get(x));
    		}
    	}
    	
    	for(int guesses = 0; guesses < 6; guesses++)
    	{
    		if(goal.getDiffArray().equals(transformations.get(guesses).getDiffArray())==true)
    		{
    			answer = options.get(guesses);
    		}
    	}
    	
        index = index + 1;
        
        String correctAnswer = problem.checkAnswer(answer);
        if (answer.equals(correctAnswer)==true)
        {
        	System.out.println("CORRECT!");
        }
        else
        {
	        System.out.println("DAMN!");
        }
        return answer;
        
    }

}
