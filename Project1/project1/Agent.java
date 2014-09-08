package project1;

import java.util.ArrayList;
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
        //ArrayList<ObjectTrans>

    	//Variables
    	String answer="0";
    	//Tell console what you're doing
    	System.out.println("Agent.java iteration = " + index + " Problem Name = " + problem.getName() + "");
    	
    	//Tell console what pieces you're working with
    	HashMap<String, RavensFigure> figures = problem.getFigures();
    	RavensFigure A = figures.get("A");
    	//System.out.println("Name = " + A.getName() + " number of objects inside frame = " + A.getObjects().size() );
    	RavensFigure B = figures.get("B");
    	
    	ObjectTrans goal = new ObjectTrans(A.getObjects().get(0),B.getObjects().get(0));
    	System.out.println("-------GOAL-------");
    	for(int x=0; x<goal.getDiffArray().size(); x++)
    	{
    		System.out.println(goal.getDiffArray().get(x));
    	}
    	
    	RavensFigure C = figures.get("C");
    	
    	RavensFigure oneFigure = figures.get("1");
    	ObjectTrans first = new ObjectTrans(C.getObjects().get(0),oneFigure.getObjects().get(0));
    	System.out.println("-------First Guess-------");
    	for(int x=0; x<first.getDiffArray().size(); x++)
    	{
    		System.out.println(first.getDiffArray().get(x));
    	}
    	
    	
    	RavensFigure twoFigure = figures.get("2");
    	ObjectTrans second = new ObjectTrans(C.getObjects().get(0),twoFigure.getObjects().get(0));
    	System.out.println("-------Second Guess-------");
    	for(int x=0; x<second.getDiffArray().size(); x++)
    	{
    		System.out.println(second.getDiffArray().get(x));
    	}
    	
    	RavensFigure threeFigure = figures.get("3");
    	ObjectTrans third = new ObjectTrans(C.getObjects().get(0),threeFigure.getObjects().get(0));
    	System.out.println("-------Third Guess-------");
    	for(int x=0; x<third.getDiffArray().size(); x++)
    	{
    		System.out.println(third.getDiffArray().get(x));
    	}
    	
    	RavensFigure fourFigure = figures.get("4");
    	ObjectTrans fourth = new ObjectTrans(C.getObjects().get(0),fourFigure.getObjects().get(0));
    	System.out.println("-------Fourth Guess-------");
    	for(int x=0; x<fourth.getDiffArray().size(); x++)
    	{
    		System.out.println(fourth.getDiffArray().get(x));
    	}
    	
    	RavensFigure fiveFigure = figures.get("5");
    	ObjectTrans fifth = new ObjectTrans(C.getObjects().get(0),fiveFigure.getObjects().get(0));
    	System.out.println("-------Fifth Guess-------");
    	for(int x=0; x<fifth.getDiffArray().size(); x++)
    	{
    		System.out.println(fifth.getDiffArray().get(x));
    	}
    	
    	RavensFigure sixFigure = figures.get("6");
    	ObjectTrans sixth = new ObjectTrans(C.getObjects().get(0),sixFigure.getObjects().get(0));
    	System.out.println("-------Sixth Guess-------");
    	for(int x=0; x<sixth.getDiffArray().size(); x++)
    	{
    		System.out.println(sixth.getDiffArray().get(x));
    	}
    	
    	if (goal.getDiffArray().equals(first.getDiffArray())==true)
    	{
    		answer = "1";
    	}
    	
    	else if (goal.getDiffArray().equals(second.getDiffArray())==true)
    	{
    		answer = "2";
    	}
    	
    	else if (goal.getDiffArray().equals(third.getDiffArray())==true)
    	{
    		answer = "3";
    	}
    	else if (goal.getDiffArray().equals(fourth.getDiffArray())==true)
    	{
    		answer = "4";
    	}
    	
    	else if (goal.getDiffArray().equals(fifth.getDiffArray())==true)
    	{
    		answer = "5";
    	}
    	
    	else if (goal.getDiffArray().equals(sixth.getDiffArray())==true)
    	{
    		answer = "6";
    	}
    	//goal.figureTransformation(figures.get("A"),figures.get("B"));

        
        
        /*
        HashMap<String, RavensFigure> test = problem.getFigures();
        RavensFigure A = test.get("A");
        ArrayList<RavensObject> object = A.getObjects();
        RavensObject thingy = object.get(0);
        String thingyName= thingy.getName();
        System.out.println(thingyName);
        
        System.out.println(name);
        */
    	
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
