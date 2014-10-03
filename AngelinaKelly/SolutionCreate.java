package project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class SolutionCreate {
	static ArrayList<String> shapes = new ArrayList<String>( 
			Arrays.asList("circle", "triangle", "square", "pentagon", "hexagon", "heptagon", "octagon"));
	
	public static HashMap<String, ShapeItem> findMissing(HashMap<String, ShapeItem> hmA,
			HashMap<String, ShapeItem> hmB, HashMap<String, ShapeItem> hmC, boolean alternate) {
	
		HashMap<String, ShapeItem> hmD = new HashMap<String, ShapeItem>();
		String key = new String();
		ShapeItem valueA = new ShapeItem();
		ShapeItem valueB = new ShapeItem();
		boolean simpleCompare = false;
		String aShape = new String();
		String bShape = new String();
		HashMap<String, String> aMods = new HashMap<String, String>();
		HashMap<String, String> bMods = new HashMap<String, String>();
		ArrayList<String> diff = new ArrayList<String>();
		
	/* Need to figure out if all of A is the same and all of B is the same.  If they are, then the transformation from 
	 * A -> B should be simple and the transformation from C -> would just be the difference between A and B.  	
	 * This should handle some of the simple transformations where there is only 1 item in A and B as well as where
	 * there are more items in C than A/B
	 */
		
		if(hmA.size() == hmB.size()){
			// Setting simpleCompare to true here since we will have multiple breaks/setting to false
			simpleCompare = true;
			for (Entry<String, ShapeItem> entry : hmB.entrySet()){
				key = entry.getKey();
				valueB = entry.getValue();
				if (hmA.containsKey(key)){
					valueA = hmA.get(key);
					if (aShape.isEmpty()) {
						aShape = valueA.shape;
						bShape = valueB.shape;
						aMods = valueA.modifiers;
						bMods = valueB.modifiers;
					}
					else { 
						if (!(valueA.sameShape(aShape) & valueB.sameShape(bShape))) { simpleCompare = false; break; } 
						else if (!valueA.sameShape(bShape)) { diff.add("shape"); }
						if (!(valueA.sameModifiers(aMods) & valueB.sameModifiers(bMods))) { simpleCompare = false; break; } 
							
					// This may need to be loosened/removed but for now, the comparison is to see if the positions 
					// between A and B are the same
						if (!valueA.samePositions(valueB.positions, false)) { simpleCompare = false ; break;}}
						if (!valueA.sameModifiers(bMods)) { 
							for (Entry<String, String> mod: aMods.entrySet()) {
								String mKey = mod.getKey();
								String aValue = mod.getValue();
								String bValue = valueB.modifiers.get(mKey);
								if (!aValue.equals(bValue)) { diff.add(mKey); }
							}
						}
				} else { simpleCompare = false;  break;}
			}
		} 
		else { simpleCompare = false; }
		if (simpleCompare) { hmD = simpleComparison(hmC, valueB, aShape, bShape, aMods, bMods, diff);}
		else if ((hmA.size() > hmB.size()) & alternate){ removeFirstObject(hmA, hmB, hmC, hmD, key); }
		else{
			hmD = normalComparison(hmA, hmB, hmC, alternate);
		}
		return hmD;
	}

	private static HashMap<String, ShapeItem> normalComparison(HashMap<String, ShapeItem> hmA,
			HashMap<String, ShapeItem> hmB, HashMap<String, ShapeItem> hmC, boolean alternate) {
		String key;
		ShapeItem valueA;
		ShapeItem valueB;
		ShapeItem valueC;
		HashMap<String, ShapeItem> hmD = new HashMap<String, ShapeItem>();
		
		for (Entry<String, ShapeItem> entry : hmB.entrySet())
		{
			key = entry.getKey();
		valueB = entry.getValue();
		ShapeItem dShape = new ShapeItem();
		dShape.name = key;
		
		if (hmA.containsKey(key)){
			valueA = hmA.get(key);
			valueC = hmC.get(key);
			/* If the shapes are the same, then we are good, otherwise we might need to 
			 * perform other magic such as counting sides.  At least one challenge problem 
			 * switches shapes and uses number of sides as a comparison
			 * 
			 * The methodology below is if A and B are the same thing for a given field, then
			 * it follows that C and D will be the same thing for the same field.  So we will 
			 * check to see which fields are the same for A and B and set D to C for them if
			 * they are the same.  Otherwise, we will need to figure out the differences and
			 * set the differences.
			 */
			if (valueA.sameShape(valueB.shape)) { 
				dShape.shape = valueC.shape;	
				}
			else if (valueA.sameShape(valueC.shape)){
				dShape.shape = valueB.shape;
			}
			else { break; }
			dShape = getNormalMods(valueA, valueB, valueC, dShape, alternate); 
			
			if (valueA.samePositions(valueB.positions, false)) { 
				dShape.positions = valueC.positions; }
			else if (valueC.samePositions(valueA.positions, false)) { 
				dShape.positions = valueB.positions; }
			else if (valueA.samePositions(valueB.positions, true)){
				dShape.positions = valueC.positions; }
			else if (valueC.samePositions(valueA.positions, true))
			{ dShape.positions = valueB.positions; } 

			hmD.put(key, dShape);
		}
		/* In the case that we are adding something new that wasn't in A, we can add it directly
		 * as there is no point of comparison
		 */
		else { hmD.put(key, valueB); }
		}
		return hmD;
	}

	private static void removeFirstObject(HashMap<String, ShapeItem> hmA,
			HashMap<String, ShapeItem> hmB, HashMap<String, ShapeItem> hmC,
			HashMap<String, ShapeItem> hmD, String key) {
		ShapeItem valueA;
		ShapeItem valueB;
		ShapeItem valueC;
		boolean alternate = false;
		/* Since the first time, we probably got rid of the last item, this time,
		 * we will get rid of the first item
		 */

		int count = 0;
		String prevKey = new String();
		String currentKey = new String();
		for (Entry<String, ShapeItem> entry : hmA.entrySet()){
			if (count == 0){ 
				prevKey = entry.getKey(); 
				count++;
			}
			else {
				ShapeItem dShape = new ShapeItem();
				currentKey = entry.getKey();
				count++;
				dShape.name = prevKey;
				
				if (hmC.containsKey(currentKey)){
					valueA = entry.getValue();
					valueC = hmC.get(currentKey);
					
					if (hmB.containsKey(currentKey)){
						valueB = hmB.get(currentKey);
					}
					else{ valueB = hmB.get(prevKey); }
						
					if (valueA.sameShape(valueB.shape)) { 
						dShape.shape = valueC.shape;	
						}
					else if (valueA.sameShape(valueC.shape)){
						dShape.shape = valueB.shape;
					}
					dShape = getNormalMods(valueA, valueB, valueC, dShape, false); 
					
					if (valueA.samePositions(valueB.positions, false)) { 
						dShape.positions = valueC.positions; }
					else if (valueA.samePositions(valueC.positions, false)) { 
						dShape.positions = valueB.positions; }
					/* Need to build special case for inside out, may be other special cases later */
					hmD.put(key, dShape);
				}
				prevKey = currentKey;
			}
		}
	}
	
	private static HashMap<String, ShapeItem> simpleComparison(HashMap<String, ShapeItem> hmC, ShapeItem valueB, 
		String aShape, String bShape, HashMap<String, String> aMods, HashMap<String, String> bMods, 
		ArrayList<String> diff) {
		
		HashMap<String, ShapeItem> hmD = new HashMap<String, ShapeItem>();
		String sKey = new String();
		ShapeItem cValue = new ShapeItem();
		
		for (Entry<String, ShapeItem> cShapes : hmC.entrySet() ) {
			sKey = cShapes.getKey();
			cValue = cShapes.getValue();
			ShapeItem dShape = new ShapeItem();
			dShape.name = sKey;
			
			if (diff.contains("shape")) {
				if (cValue.shape.equals(aShape)) {
					dShape.shape = bShape;
				}
				else if (shapes.toString().contains(aShape) & shapes.toString().contains(bShape)) {
					int shapeDiff = Math.abs(shapes.indexOf(aShape) - shapes.indexOf(bShape)) ;
					if (shapes.indexOf(aShape) > shapes.indexOf(bShape)) { 
						dShape.shape = shapes.get(shapes.indexOf(cValue.shape) - shapeDiff); }
					else if (shapes.indexOf(bShape) > shapes.indexOf(aShape)) { 
						dShape.shape = shapes.get(shapes.indexOf(cValue.shape) + shapeDiff); }
				}
				else { System.out.println("Shape anomaly"); }
				diff.remove("shape");
			}
			else {dShape.shape = cValue.shape;}
			if (!cValue.positions.isEmpty()) { dShape.positions = cValue.positions; }
		
			dShape = getDiffMods(valueB, aMods, bMods, diff,  cValue, dShape);
			hmD.put(sKey, dShape);
		}
		return hmD;
	}
	
	private static ShapeItem getNormalMods(ShapeItem valueA, ShapeItem valueB, ShapeItem valueC, ShapeItem dShape, boolean alternate) {
		ArrayList<String> sizes = new ArrayList<String>(
				Arrays.asList("small", "medium", "large"));
		
		if (valueA.sameModifiers(valueB.modifiers)) { dShape.modifiers = valueC.modifiers; }
		else { 
			String aKey = new String();
			String aValue = new String();
			String bValue = new String();
			String cValue = new String();
			for(Entry<String, String> mods: valueA.modifiers.entrySet()){
				aKey = mods.getKey();
				aValue = mods.getValue();
				bValue = valueB.modifiers.get(aKey);
				cValue = valueC.modifiers.get(aKey);
					
				if (aValue.equals(bValue)){
					dShape.modifiers.put(aKey, cValue);
				}
				else if (aValue.equals(cValue)){
					dShape.modifiers.put(aKey, bValue);
				}
					
				/* we may need to do some magic here but this should help with the instances where some 
				 * modifiers are the same and some aren't
				 */
				else {
					if (aKey.equals("angle"))
					{
						int dAngle = getDAngle(aValue, bValue, cValue, dShape.isHalfShape(), alternate);
						dShape.modifiers.put(aKey, Integer.toString(dAngle));
					
					}
					else if (aKey.equals("fill")){ dShape = getFills(aValue, bValue, cValue, aKey, dShape); }
					else if (aKey.equals("size")) {
						int sizeDiff = Math.abs(sizes.indexOf(aValue) - sizes.indexOf(bValue));
						int dIndex;
						if (sizes.indexOf(bValue) > sizes.indexOf(aValue)) {
							dIndex = sizes.indexOf(cValue) + sizeDiff;
							if (dIndex >= sizes.size()){ 
								dShape.modifiers.put(aKey, sizes.get(dIndex - sizes.size())); 
								}
							else { 
								dShape.modifiers.put(aKey, sizes.get(dIndex)); }
						}
						else {
							dIndex = sizes.indexOf(cValue) - sizeDiff;
							if (dIndex < 0) { 
								dShape.modifiers.put(aKey, sizes.get(sizes.size() + dIndex)); }
								else { 
								dShape.modifiers.put(aKey, sizes.get(dIndex)); }
						}
					}
						
					else{ dShape.modifiers.put(aKey, valueB.modifiers.get(aKey)); }
				}
			}
			/* This is going to be a special case */
			if (dShape.modifiers.containsKey("vertical-flip") & dShape.modifiers.containsKey("angle") ){
				if (!dShape.modifiers.get("angle").equals(valueC.modifiers.get("angle"))){
					int angleDiff = Math.abs(Integer.valueOf(dShape.modifiers.get("angle")) - 
							Integer.valueOf(valueC.modifiers.get("angle")));
					String cVFlip = valueC.modifiers.get("vertical-flip");
					if (angleDiff > 135 & angleDiff < 225)
					{ 
						if (cVFlip.equals("no")){ dShape.modifiers.replace("vertical-flip", "yes");}
						else { dShape.modifiers.replace("vertical-flip", "no");}
					}
				}
			}
		}
		return dShape;
	}
		
	private static ShapeItem getDiffMods(ShapeItem valueB, HashMap<String, String> aMods, HashMap<String, String> bMods,
			ArrayList<String> diff, ShapeItem cValue, ShapeItem dShape) {
		String modKey = new String();
		String modValue = new String();
		boolean alternate = false;
			
		for (Entry<String, String> m : cValue.modifiers.entrySet()) {
			modKey = m.getKey();
			modValue = m.getValue();

			// Will need to do the angle modification
			if (!diff.contains(modKey)) { 
				dShape.modifiers.put(modKey, modValue); }
			else {
				if (modKey.equals("angle")) {
					int dAngle = getDAngle(aMods.get(modKey), bMods.get(modKey), modValue, dShape.isHalfShape(), false);
					dShape.modifiers.put(modKey, Integer.toString(dAngle));
				}
				else if (modKey.equals("fill")){
					dShape = getFills(aMods.get(modKey), bMods.get(modKey), modValue, modKey, dShape); }
				else{ dShape.modifiers.put(modKey, valueB.modifiers.get(modKey)); }
			}
		}
		/* We are accounting for the instance that there is a vertical flip */
		if (dShape.modifiers.containsKey("vertical-flip") & dShape.modifiers.containsKey("angle") ){
			if (!dShape.modifiers.get("angle").equals(cValue.modifiers.get("angle"))){
				String angle1 = dShape.modifiers.get("angle"); 
				String angle2 = cValue.modifiers.get("angle");
				int angleDiff = Math.abs(Integer.valueOf(angle1) - Integer.valueOf(angle2));
				String cVFlip = cValue.modifiers.get("vertical-flip");
				if (angleDiff > 135 & angleDiff < 225)
				{ 
					if (cVFlip.equals("no")){ dShape.modifiers.replace("vertical-flip", "yes");}
					else { dShape.modifiers.replace("vertical-flip", "no");}
				} 
			}
		}
		return dShape;
	}
		
	private static int getDAngle(String A, String B, String C, boolean halfShape, boolean alternate) {
		int aAngle = Integer.parseInt(A);
		int bAngle = Integer.parseInt(B);
		int cAngle = Integer.parseInt(C);
		int dAngle;
			
		int angleChange = Math.abs(bAngle - aAngle);
		/* This is where things can get tricky.  If the angleChange is 180, we 
		 * have to consider the preference between mirror and reflection. A special 
		 * case in finding the answer should be that if no matches are found, then
		 * we try a mirror.  The preference is if the shape is symmetrical, do reflection.
		 * otherwise, do rotation. 
		 */
		if (angleChange == 180 & !halfShape & !alternate) {
			dAngle = 360 - cAngle;
		}
		else {
			dAngle = cAngle + angleChange;
			if (dAngle > 360) { dAngle -= 360; }
		}
		if (dAngle == 360) { dAngle = 0;}
		return dAngle;
	}
		
	private static ShapeItem getFills(String aValue, String bValue, String cValue, String key, ShapeItem dShape){
		
	ArrayList<String> aSplit = new ArrayList<String>();
	ArrayList<String> bSplit = new ArrayList<String>();
	ArrayList<String> cSplit = new ArrayList<String>();
	Collections.addAll(aSplit, aValue.split(","));
	Collections.addAll(bSplit, bValue.split(","));
	Collections.addAll(cSplit, cValue.split(","));

	StringBuilder dFills = new StringBuilder();
			
	ArrayList<String> diffadd = new ArrayList<String>();
	ArrayList<String> diffrem = new ArrayList<String>();
	ArrayList<String> fillOptions = new ArrayList<String>();
		fillOptions.add("top-right");
		fillOptions.add("bottom-right");
		fillOptions.add("top-left");
		fillOptions.add("bottom-left");
			
	String change;
	if (aSplit.get(0).equals("no")){
		diffadd = bSplit;
		change = "add";
	}
	else if (bSplit.get(0).equals("no")) {
		diffrem = aSplit;
		change = "remove";
	}
	else
	{
		change = "none";
		for (String bMod : bSplit) {
			boolean found = false;
			for (String aMod: aSplit){
				if (bMod.equals(aMod)) {
					found = true;
					break;
				}
			}
			if (found == false){ 
				diffadd.add(bMod); 
				change = "add";
				}
		}
		for (String aMod : aSplit) {
			boolean found = false;
			for (String bMod: bSplit){
				if (aMod.equals(bMod)) {
					found = true;
					break;
				}
			}
			if (found == false){ 
				diffrem.add(aMod); 
				if (change.equals("add")){ change = "both"; }
				else if (change.equals("none")){ change = "remove"; }
			}
		}
	}
	if (cSplit.get(0).equals("no")) {
		dFills.append(diffadd.toString().replaceAll("[\\[\\]\\s]",""));
		dShape.modifiers.put(key, dFills.toString());
	}
	else if (cSplit.get(0).equals("yes")){
		fillOptions.removeAll(diffrem);
		dFills.append(fillOptions.toString().replaceAll("[\\[\\]\\s]",""));
		dShape.modifiers.put(key, dFills.toString());
	}
	else{
		if (change.equals("remove")){
			cSplit.removeAll(diffrem);
		}
		else if (change.equals("add")) {
			cSplit.addAll(diffadd);
		}
		else if (change.equals("both")) {
			cSplit.removeAll(diffrem);
			cSplit.addAll(diffadd);
		}
		else { 
			System.out.println("Modifiers anomaly");  
			dShape.modifiers.put(key, bValue);
		}
		for (int i = 0; i < cSplit.size(); i++) {
			if (cSplit.indexOf(cSplit.get(i)) != cSplit.lastIndexOf(cSplit.get(i)) ){
				cSplit.remove(cSplit.indexOf(cSplit.get(i)));
				//i++ was here?
			}
		}
		dFills.append(cSplit.toString().replaceAll("[\\[\\]\\s]", ""));
		dShape.modifiers.put(key, dFills.toString());
		}
		return dShape;
	}
	public static HashMap<String, ShapeItem> insideOutMapping(
			HashMap<String, ShapeItem> hmA, HashMap<String, ShapeItem> hmB,
			HashMap<String, ShapeItem> hmC) {
		
		HashMap<String, ShapeItem> hmD = new HashMap<String, ShapeItem>();
		HashMap<String, String> shapeMap = new HashMap<String, String>();
		String key = new String();
		ShapeItem valueC= new ShapeItem();
		ShapeItem valueA = new ShapeItem();
		ShapeItem valueB = new ShapeItem();
		boolean alternate = false;
		
		/* need to create the shapeMap first */
		for (Entry<String, ShapeItem> entryC: hmC.entrySet()){
			ShapeItem dShape = new ShapeItem();
			key = entryC.getKey();
			valueC = entryC.getValue();
			valueA = hmA.get(key);
			valueB = hmB.get(key);
				if (valueA.sameShape(valueB.shape)) { 
					dShape.shape = valueC.shape;	
					}
				else  { dShape.shape = valueB.shape; }
			dShape = getNormalMods(valueA, valueB, valueC, dShape, false); 
			if (dShape.modifiers.containsKey(("size"))) {
				shapeMap.put(dShape.modifiers.get("size"), key)	;
			}	
			else { return hmD;}
		}
		/* D can now be created*/
		for (Entry<String, ShapeItem> entryC: hmC.entrySet()){
			ShapeItem dShape = new ShapeItem();
			key = entryC.getKey();
			valueC = entryC.getValue();
			valueA = hmA.get(key);
			valueB = hmB.get(key);
				if (valueA.sameShape(valueB.shape)) { 
					dShape.shape = valueC.shape;	
					}
				else  { dShape.shape = valueB.shape; }
			shapeMap.put(key, dShape.shape)	;
	
			dShape = getNormalMods(valueA, valueB, valueC, dShape, alternate); 
				
				if (valueA.samePositions(valueB.positions, false)) { 
					dShape.positions = valueC.positions; }
				else if (valueC.samePositions(valueA.positions, false)) { 
					dShape.positions = valueB.positions; }
				else { 
					if (dShape.modifiers.get("size").equals("large")) {
						dShape.positions.clear();
					}
					else if (dShape.modifiers.get("size").equals("medium")) {
						dShape.positions.put("inside", shapeMap.get("large"));
					}
					else if (dShape.modifiers.get("size").equals("small")) {
						dShape.positions.put("inside", shapeMap.get("medium") + "," + shapeMap.get("large"));
					}
				}
				hmD.put(key, dShape);
			}
		return hmD;
	}
	/* For now this isn't used because I need to add some exception handling */
	public static boolean insideOutCandidate (HashMap<String, ShapeItem> hmA, HashMap<String, ShapeItem> hmB,
			HashMap<String, ShapeItem> hmC) {
		boolean candidate;
		boolean small = false;
		boolean medium = false;
		boolean large = false;
		boolean mediumPos = false;
		boolean largePos = false;
		boolean smallPos = false;
		String key = new String();
		ShapeItem value = new ShapeItem();
		int posCount;
		
		for (Entry<String, ShapeItem> ioA : hmA.entrySet() )
		{
			key = ioA.getKey();
			value = ioA.getValue();
			if (value.positions.containsKey("inside")) {
				if (value.modifiers.get("size").equals("medium")){
					medium = true;
					if (value.positions.values().size() == 1) { mediumPos = true; }
					else { return false; }
				}
				else if (value.modifiers.get("size").equals("small")){
					small = true;
					if (value.positions.get("inside").contains(",")) { smallPos = true; }
					else { return false; }
				}
				else { return false;}
			}
			else if (value.modifiers.get("size").equals("large")){
				large = true;
				largePos = true;
			}
			else { 
				return false; } 
		}
		
		for (Entry<String, ShapeItem> ioB : hmB.entrySet() )
		{
			key = ioB.getKey();
			value = ioB.getValue();
			if (value.positions.containsKey("inside")) {
				if (value.modifiers.get("size").equals("medium")){
					medium = true;
					if (!value.positions.get("inside").contains(",")) { mediumPos = true; }
					else { return false; }
				}
				else if (value.modifiers.get("size").equals("small")) {
					small = true;
					if (value.positions.get("inside").contains(",")) { smallPos = true; }
					else { return false; }
				}
				else { return false;}
			}
			else if (value.modifiers.get("size").equals("large")){
				large = true;
				largePos = true;
			}
			else { return false; }
		}
		
		for (Entry<String, ShapeItem> ioC : hmC.entrySet() )
		{
			key = ioC.getKey();
			value = ioC.getValue();
			if (value.positions.containsKey("inside")) {
				if (value.modifiers.get("size").equals("medium")){
					medium = true;
					if (!value.positions.get("inside").contains(",")) { mediumPos = true; }
					else { return false; }
				}
				else if (value.modifiers.get("size").equals("small")) {
					small = true;
					if (value.positions.get("inside").contains(",")) { smallPos = true; }
					else { return false; }
				}
				else { return false;}
			}
			else if (value.modifiers.get("size").equals("large")){
				large = true;
				largePos = true;
			}
			else { return false; }
		}
		return true;		
	}
}
