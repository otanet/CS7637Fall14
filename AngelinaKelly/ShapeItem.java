package project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class ShapeItem {
	public String name;
	public String shape;
	public HashMap<String, String> positions = new HashMap<String, String>();
	public HashMap<String, String> modifiers = new HashMap<String, String>();
	private final ArrayList<String> symmetrical = new ArrayList<String>(
			Arrays.asList("square", "diamond", "cross", "plus", "rectangle"));
	private final ArrayList<String> noAngle = new ArrayList<String>(
			Arrays.asList("circle", "octagon"));

	public ShapeItem(ShapeItem aShapeItem){
		this.name = aShapeItem.name; 
		this.shape = aShapeItem.shape; 
		this.positions = aShapeItem.positions; 
		this.modifiers = aShapeItem.modifiers;
	}
	public ShapeItem() {
		this.name = "";
		this.shape = "";
	}
	public boolean shapeEquals(ShapeItem compShape, boolean quasi){
		boolean result = false;
		if (shape.equals(compShape.shape))
		{
			// Circles inherently have no angle, set circle angle to 0
			if (noAngle.contains(shape) & compShape.modifiers.containsKey("angle")){
				modifiers.remove("angle");
				compShape.modifiers.remove("angle");
				compShape.modifiers.put("angle", "0");
				modifiers.put("angle", "0");
			}
			if(sameModifiers(compShape.modifiers) | (modifiers.isEmpty() & compShape.modifiers.isEmpty())){
				if(positions.isEmpty() & compShape.positions.isEmpty()){
					result = true;
				}
				if (quasi){
					if(samePositions(compShape.positions, true)){
						return true;
					}
					else {return false;}
				}
				else if(positions.equals(compShape.positions)){
					result = true;
				}	
			}
		}
		return result;
	}
	
	public boolean isHalfShape(){
		if (shape.contains("half-")) { return true;}
		else { return false;}
	}
	
	public boolean isSymmetrical(){
		if (symmetrical.contains(shape)) { return true;}
		else { return false;}
	}

	public boolean sameName(String testName) {
		if (shape.equals(testName)) { return true; }
		else { return false;}
	}
	
	public boolean sameShape(String testShape) {
		if (shape.equals(testShape)) { return true; }
		else { return false;}
	}

	/* for some reason, two empty HashMaps may not be equal?  either way, we are adding a special case
	 * for when both HashMaps are empty
	 */
	public boolean samePositions(HashMap<String, String> testPositions, boolean quasi) {
		/* with quasi, we only want to see if the keys are the same, not the actual
		 * index
		 */
		boolean quasiResult = false;
		if (positions.isEmpty() & testPositions.isEmpty()){
			return true;
		}
		else if (quasi){
			for (Entry<String, String> pos: positions.entrySet()){
				if (testPositions.containsKey(pos.getKey())){
					quasiResult = true;
				}
				else { quasiResult = false; break;}
			}
			return quasiResult;
		}
		else if (positions.entrySet().equals(testPositions.entrySet())){
			return true;
		}
		else { return false;}
	}
	
	public void swapPositionID(HashMap<String, String> objectMap) {
		/* Building something to swap positions so that we can make positions correct
		 */
		String value = new String();
		ArrayList<String> values = new ArrayList<String>();
		HashMap<String, String> newPositions = new HashMap<String, String>();
		int count = 0;
		for (Entry<String, String> pos: positions.entrySet()){
			count = 0;
			StringBuilder posSb = new StringBuilder();
			value = pos.getValue();
			Collections.addAll(values, value.split(","));
			for (String v : values) {
				if (objectMap.containsKey(v)){
					if (count == 0) { posSb.append(objectMap.get(v)); }
					else { 
						posSb.append(",");
						posSb.append(objectMap.get(v));
					}
					count++;
				}
			}
			newPositions.put(pos.getKey(), posSb.toString());
		}
		positions = newPositions;
	}
	
	public String shapeSize() {
		if (modifiers.containsKey("size")) { return modifiers.get("size"); }
		else { return "null"; }
	}
	
	public boolean sameModifiers(HashMap<String, String> testModifiers) {
		if (modifiers.entrySet().equals(testModifiers.entrySet())){
			return true; }
		else if  (modifiers.isEmpty() & testModifiers.isEmpty()) { 
			return true; }
		else if (modifiers.size() != testModifiers.size()) { return false; }
		else {
			boolean match = false;
			for (Entry<String, String> entry : modifiers.entrySet()) {
				String mKey = entry.getKey();
				String mValue = entry.getValue();
				String testValue = new String();
				if (testModifiers.containsKey(mKey)) {
					testValue = testModifiers.get(mKey);
				}
				else { return false;}
				
				if (mKey.equals("fill")){
					ArrayList<String> fills = new ArrayList<String>();
					ArrayList<String> testFills = new ArrayList<String>();
					Collections.addAll(fills, mValue.split(","));
					Collections.addAll(testFills, testValue.split(","));
					if (fills.size() != testFills.size()) {
						return false;
					}
					int matchCount = 0;
					for (int i = 0; i < fills.size(); i++) {
						if (!testFills.contains(fills.get(i))){ return false; }
						else { matchCount++;}
					}
					if (matchCount == fills.size()) { match = true; }
				}
				else if (mKey.equals("angle")  & symmetrical.contains(shape)){
					int altAngle = 360 - Integer.parseInt(testValue);
					if (mValue.equals(testValue) | mValue.equals(Integer.toString(altAngle))) {
						match = true;
					}
				}
				else  {
					if (mValue.equals(testValue))  { match = true; }
					else { return false;}
				}
			}
			return match;
		}
	}
	
	/* Generalize shape will only change the name and the labels for positions, everything
	 * else will remain the same
	 */
	public ShapeItem generalizeShape(){
		ShapeItem tempShape = new ShapeItem();
		tempShape.name = "GENERIC";
		tempShape.shape = shape;
		tempShape.modifiers = modifiers;
		for (Entry<String, String> pos : positions.entrySet()) {
			String[] posSplit = pos.getValue().split(",");
			String posKey = pos.getKey();
			for (int i = 0; i < posSplit.length; i++) {
				posSplit[i] = "GENERIC";
			}
			
			StringBuilder posSb = new StringBuilder();
			for (String p : posSplit) { 
			    if (posSb.length() > 0) posSb.append(',');
			    posSb.append(p);
			}
			tempShape.positions.put(posKey, posSb.toString());
		}
		return tempShape;
	}
	
	public void printShape() {
		System.out.println("Name: " + name);
		System.out.println("Shape: " + shape);
		System.out.println("Positions: ");
		for (Entry<String, String> pos : positions.entrySet()) {
			System.out.println(pos.getKey() + ": " + pos.getValue()); }
		System.out.println("Modifiers:");
		for (Entry<String, String> mod : modifiers.entrySet()) {
			System.out.println(mod.getKey() + ": " + mod.getValue()); }
		
	}
}
