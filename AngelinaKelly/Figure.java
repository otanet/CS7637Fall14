package project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class Figure {
	public static HashMap<String, ShapeItem> createHashFigure(String figure, HashMap<String, RavensFigure> prob,
			boolean alternate) {
		
		/** Positions that found in example file except right-of, there may be other positions as well
		 * but for the initial check, we will assume the following are our only positions
		 */
		ArrayList<String> positions = new ArrayList<String>(
				Arrays.asList("above", "below", "inside", "right-of", "left-of", "overlaps"));
    	HashMap<String, ShapeItem> hmShape = new HashMap<String, ShapeItem>();

    	RavensFigure fig = prob.get(figure);
    	ArrayList<RavensObject> objects = fig.getObjects();
    	
    	/* This is where we will make a permutation and use it for an alternate mapping
    	 * Can do something where we specify permutations but for now, this is intended
    	 * to flip the first two
    	 */
    	ArrayList<String> altNames = new ArrayList<String>();
    	for (RavensObject ravensObject : objects) {
    		altNames.add(ravensObject.getName());
    	}
    
    	int loopCount = 0;
    	if (altNames.size() < 2) { alternate = false;}
    	
    	
    	for (RavensObject ravensObject : objects) {
    		String RaName = ravensObject.getName();
    		ShapeItem RaShape = new ShapeItem();
    		HashMap<String, String> pos = new HashMap<String, String>();
    		HashMap<String, String> mod = new HashMap<String, String>();
    		ArrayList<RavensAttribute> ravensAttr = ravensObject.getAttributes();
    			
    		for (RavensAttribute ravensAttribute : ravensAttr) {
    			String attribute = ravensAttribute.getName();
    			String value = ravensAttribute.getValue();
    			if (attribute.equals("shape")) {
    				RaShape.shape = value;
    			}
    			else if (positions.contains(attribute)){
    				RaShape.positions.put(attribute, value);
   					pos.put(attribute, value);
   				}
   				else { 
   					RaShape.modifiers.put(attribute, value);
   					mod.put(attribute, value);
   				}
   			}
    		RaShape.modifiers = mod;
   			RaShape.positions = pos;
   			if (alternate & loopCount == 0)
   			{
   				hmShape.put(altNames.get(1), RaShape); 
   				RaShape.name = altNames.get(1);
   			}
   			else if (alternate & loopCount == 1)
   			{
   				hmShape.put(altNames.get(0), RaShape); 
   				RaShape.name = altNames.get(0);
   			}
   			else {
   				hmShape.put(RaName, RaShape); 
   				RaShape.name = RaName;
   			}
		}	
    	return hmShape;
	}
	
	
	
	/* This could be refactored if we have time, the loops are a bit repetitive */
public static HashMap<String, ShapeItem> altMapHM (HashMap<String, ShapeItem> hmA, HashMap<String, ShapeItem> hmB,
		HashMap<String, ShapeItem> hmC, String selection) {
	
	String[] labels = {"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R"};
	HashMap<String, ShapeItem> altA = new HashMap<String, ShapeItem>();
	HashMap<String, ShapeItem> altB = new HashMap<String, ShapeItem>();
	HashMap<String, ShapeItem> altC = new HashMap<String, ShapeItem>();
	ArrayList<String> commonShape = new ArrayList<String>();
	ArrayList<String> commonShapeAC = new ArrayList<String>();
	ArrayList<String> commonShapeAB = new ArrayList<String>();
	ArrayList<String> bShapes = new ArrayList<String>();
	ArrayList<String> sameKeys = new ArrayList<String>(); 
	HashMap<String, String> hmAKeys = new HashMap<String, String>();
	HashMap<String, String> hmBKeys = new HashMap<String, String>();
	HashMap<String, String> hmCKeys = new HashMap<String, String>();
	HashMap<String, String> objectMapA = new HashMap<String, String>();
	HashMap<String, String> objectMapB = new HashMap<String, String>();
	HashMap<String, String> objectMapC = new HashMap<String, String>();
	ArrayList<String> hmAShapes = new ArrayList<String>();
	ArrayList<String> hmBShapes = new ArrayList<String>();
	ArrayList<String> hmCShapes = new ArrayList<String>();
	
	for (Entry<String, ShapeItem> tempEntry: hmA.entrySet()){
		hmAKeys.put(tempEntry.getKey(), tempEntry.getValue().shape);
		if (!hmAShapes.contains(tempEntry.getValue().shape)){
			hmAShapes.add(tempEntry.getValue().shape);}
	}
	for (Entry<String, ShapeItem> tempEntry: hmB.entrySet()){
		hmBKeys.put(tempEntry.getKey(), tempEntry.getValue().shape);
		if (!hmBShapes.contains(tempEntry.getValue().shape)){
			hmBShapes.add(tempEntry.getValue().shape);}
	}
	for (Entry<String, ShapeItem> tempEntry: hmC.entrySet()){
		hmCKeys.put(tempEntry.getKey(), tempEntry.getValue().shape);
		if (!hmCShapes.contains(tempEntry.getValue().shape)){
			hmCShapes.add(tempEntry.getValue().shape);}
	}
	sameKeys.addAll(hmBShapes);
	sameKeys.retainAll(hmAShapes);
	sameKeys.retainAll(hmCShapes);
	commonShape.addAll(sameKeys);
	if (!hmAShapes.containsAll(commonShape) | !hmBShapes.containsAll(commonShape) | 
		!hmCShapes.containsAll(commonShape)) { commonShape.clear();}
/* if there is 1 common shape between all of the figures, we'll use that for our base figure */
	sameKeys.clear();
	sameKeys.addAll(hmCShapes);
	sameKeys.removeAll(commonShape);
	sameKeys.retainAll(hmAShapes);
	commonShapeAC.addAll(sameKeys);
	sameKeys.clear();
	sameKeys.addAll(hmBShapes);
	sameKeys.retainAll(hmAShapes);
	sameKeys.removeAll(commonShape);
	commonShapeAB.addAll(sameKeys);
	sameKeys.clear();
	sameKeys.addAll(hmBShapes);
	sameKeys.removeAll(commonShape);
	sameKeys.removeAll(commonShapeAC);
	bShapes.addAll(sameKeys);
/* One thing we will do is forget positions because since this is to find
 * an alternate answer.  Positions may need to be figured in later
 * The following loops through and adds a common shape as first label for all of the hashmaps.
 */
	for (int i = 0; i < commonShape.size(); i++) {
		ShapeItem swapShape = new ShapeItem();
		swapShape.name = labels[i];
		swapShape.shape = commonShape.get(i);
		for(Entry<String, ShapeItem> swap: hmA.entrySet()){
			if (swap.getValue().shape.equals(commonShape.get(i))){
				swapShape.modifiers = swap.getValue().modifiers;
				swapShape.positions = swap.getValue().positions;
				objectMapA.put(swap.getKey(), swapShape.name);
				break;
			}
		}
		altA.put(swapShape.name, swapShape);
	}	
	for (int i = 0; i < commonShape.size(); i++) {
		ShapeItem swapShape = new ShapeItem();
		swapShape.name = labels[i];
		swapShape.shape = commonShape.get(i);
		
		for(Entry<String, ShapeItem> swap: hmB.entrySet()){
		if (swap.getValue().shape.equals(commonShape.get(i))){
			swapShape.modifiers = swap.getValue().modifiers;
			swapShape.positions = swap.getValue().positions;
			objectMapB.put(swap.getKey(), swapShape.name);
			break;
			}
		}	
		altB.put(swapShape.name, swapShape);
	}
	for (int i = 0; i < commonShape.size(); i++) {
		ShapeItem swapShape = new ShapeItem();
		swapShape.name = labels[i];
		swapShape.shape = commonShape.get(i);
		for(Entry<String, ShapeItem> swap: hmC.entrySet()){
			if (swap.getValue().shape.equals(commonShape.get(i))){
				swapShape.modifiers = swap.getValue().modifiers;
				swapShape.positions = swap.getValue().positions;
				objectMapC.put(swap.getKey(), swapShape.name);
				break;
			}
		}
		altC.put(swapShape.name, swapShape);
	}

/* Next is to loop through common shapes for A/B to populate the rest of
 * A/B
 */
	int labelOffset = commonShape.size();
	for (int i = 0; i < commonShapeAC.size(); i++) {
		ShapeItem swapShape = new ShapeItem();
		swapShape.name = labels[i+labelOffset];
		swapShape.shape = commonShapeAC.get(i);
		for(Entry<String, ShapeItem> swap: hmA.entrySet()){
			if (swap.getValue().shape.equals(commonShapeAC.get(i))){
				swapShape.modifiers = swap.getValue().modifiers;
				swapShape.positions = swap.getValue().positions;
				objectMapA.put(swap.getKey(), swapShape.name);
				break;
		}
	}
	altA.put(swapShape.name, swapShape);
	}
	for (int i = 0; i < commonShapeAC.size(); i++) {
		ShapeItem swapShape = new ShapeItem();
		swapShape.name = labels[i+labelOffset];
		swapShape.shape = commonShapeAC.get(i);
		for(Entry<String, ShapeItem> swap: hmC.entrySet()){
			if (swap.getValue().shape.equals(commonShapeAC.get(i))){
				swapShape.modifiers = swap.getValue().modifiers;
				swapShape.positions = swap.getValue().positions;
				objectMapC.put(swap.getKey(), swapShape.name);
				break;
			}
		}
		altC.put(swapShape.name, swapShape);
	}
	for (int i = 0;i < bShapes.size(); i++) {
		ShapeItem swapShape = new ShapeItem();
		swapShape.name = labels[i+labelOffset];
		swapShape.shape = bShapes.get(i);
		for(Entry<String, ShapeItem> swap: hmB.entrySet()){
			if (swap.getValue().shape.equals(bShapes.get(i))){
				swapShape.modifiers = swap.getValue().modifiers;
				swapShape.positions = swap.getValue().positions;
				objectMapB.put(swap.getKey(), swapShape.name);
				break;
			}
		}
		altB.put(swapShape.name, swapShape);
	}


	if (selection.equals("A")) { 
	ShapeItem updatedShape = new ShapeItem();
		for (Entry<String, ShapeItem> alt: altA.entrySet()) {
			updatedShape = alt.getValue();
			if (!updatedShape.positions.isEmpty()){
				updatedShape.swapPositionID(objectMapA);
				altA.replace(alt.getKey(), updatedShape);
			}
		}
		return altA;}
	else if (selection.equals("B")) { 
	ShapeItem updatedShape = new ShapeItem();
		for (Entry<String, ShapeItem> alt: altB.entrySet()) {
			updatedShape = alt.getValue();
			if (!updatedShape.positions.isEmpty()){
				updatedShape.swapPositionID(objectMapB);
				altB.replace(alt.getKey(), updatedShape);
			}
		}
		return altB;}
	else { 
	ShapeItem updatedShape = new ShapeItem();
		for (Entry<String, ShapeItem> alt: altC.entrySet()) {
			updatedShape = alt.getValue();
			if (!updatedShape.positions.isEmpty()){
				updatedShape.swapPositionID(objectMapC);
				altC.replace(alt.getKey(), updatedShape);
			}
		}
		return altC;}
	}
}
