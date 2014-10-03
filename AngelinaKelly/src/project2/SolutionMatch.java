package project1;

import java.util.HashMap;
import java.util.Map.Entry;

public class SolutionMatch {
   	final static String[] Solutions = {"1", "2", "3", "4", "5", "6"};
   	
	public static String solutionAnswer(RavensProblem problem) {

    	HashMap<String, ShapeItem> hmA = new HashMap<String, ShapeItem>();
    	HashMap<String, ShapeItem> hmB = new HashMap<String, ShapeItem>();
    	HashMap<String, ShapeItem> hmC = new HashMap<String, ShapeItem>();
    	HashMap<String, ShapeItem> hmD = new HashMap<String, ShapeItem>();
    	
    	
    	String name = problem.getName();
    	System.out.println("Raven's problem name is " + name);
    	HashMap<String, RavensFigure> prob = problem.getFigures();
    	
    	hmA = Figure.createHashFigure("A", prob, false);
    	hmB = Figure.createHashFigure("B", prob, false);
    	hmC = Figure.createHashFigure("C", prob, false);
    	String match;
		try {
			hmD = SolutionCreate.findMissing(hmA, hmB, hmC, false); 
			match = SolutionMatch.findMatch(hmD, Solutions, prob, false);
		}
		catch (Exception e) {
			e.printStackTrace();
			match = "0";
		}
			
			try {
				if (match.equals("0")){
					hmD = SolutionCreate.findMissing(hmA, hmB, hmC, true); 
					match = SolutionMatch.findMatch(hmD, Solutions, prob, true);
				}
			} catch (Exception e) {
				match = "0";
				e.printStackTrace();
			}
			if (match.equals("0")){
				HashMap<String, ShapeItem> tempHmA;
				HashMap<String, ShapeItem> tempHmB;
				HashMap<String, ShapeItem> tempHmC;
				try {
					tempHmA = new HashMap<String, ShapeItem>();
					tempHmB = new HashMap<String, ShapeItem>();
					tempHmC = new HashMap<String, ShapeItem>();
					tempHmA = Figure.altMapHM(hmA, hmB, hmC, "A");
					tempHmB = Figure.altMapHM(hmA, hmB, hmC, "B");
					tempHmC = Figure.altMapHM(hmA, hmB, hmC, "C");
					hmD = SolutionCreate.findMissing(tempHmA, tempHmB, tempHmC, false); 
					match = SolutionMatch.findMatch(hmD, Solutions, prob, false);
				
				if (match.equals("0")){
					hmD = SolutionCreate.insideOutMapping(tempHmA, tempHmB, tempHmC);
					match = SolutionMatch.findMatch(hmD, Solutions, prob, false);
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					match = "0";
				}
			}
    	/* Spin the roulette wheel */
    	if (match.equals("0"))
    	{
    		match = Integer.toString(1+ (int)(Math.random()*6));
    	}
    	return match;
	}
	
	public static String findMatch(HashMap<String, ShapeItem> nextFigure, String[] Solutions, HashMap<String, 
			RavensFigure> prob, boolean quasi) {
		
		HashMap<String, ShapeItem> hmComp = new HashMap<String, ShapeItem>();
		ShapeItem genShape = new ShapeItem();
		ShapeItem genFig = new ShapeItem();
		String solution = "0";
		String altSolution = "0";
		String tempAltSolution = "0";
		int matches = 0;
		int genMatches = 0;

    	for (String key : Solutions) {
    		matches = 0;
    		hmComp = Figure.createHashFigure(key, prob, false);
    		ShapeItem compValue = new ShapeItem();
    		ShapeItem figValue = new ShapeItem();
    		genMatches = 0;
    		if(hmComp.size() == nextFigure.size()) {
    			for (Entry<String, ShapeItem> entry: hmComp.entrySet()){
    				genShape = entry.getValue().generalizeShape();
    				compValue = entry.getValue();
    				
    				if (nextFigure.containsKey(entry.getKey())) {
    					figValue = nextFigure.get(entry.getKey());
    					if(figValue.shapeEquals(compValue, quasi)){
    						matches++;
    						solution = key;
    					}
    				}
    				if (solution.equals("0")) {
    					boolean foundAlt = false;
    					for (Entry<String, ShapeItem> fig : nextFigure.entrySet()) {
    						genFig = fig.getValue().generalizeShape();
    						if (genFig.shapeEquals(genShape, quasi) & !foundAlt){
    							genMatches++;
    							tempAltSolution = key;
    							foundAlt = true;
    						}
						}
    				}
    			}
    			if (!solution.equals("0") & matches == nextFigure.size()) { break;}
    			else if (!tempAltSolution.equals("0") & (genMatches == hmComp.size())){
    				altSolution = tempAltSolution;
    			}
    			else { solution = "0"; }
    		}
    	}
    	/* If there is no match then that is an issue.  A couple techniques to try are to 
    	 * assume that our answer is a valid answer but that the labels are mixed.  Another technique to try
    	 * is an 'educated' guess
    	 * 
    	 * In order to deal with mixed labels, we know we have a finite set of answers and ignore the individual 
    	 * labels or else we could create each permutation which could create a large problem for large number
    	 * of objects
    	 */
    	
    	if (solution.equals("0")){ return altSolution; }
    	else { return solution; }
	}
}