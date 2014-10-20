package project3;

import java.util.*;

/**
 * This class holds all transform information between correlated nodes
 */
public class RavensTransform {
	
	/**
	 * Transform Bitmap
	 * This bitmap stores the transforms between correlated nodes in related figures.
	 * If a transform is present between the correlated nodes, the associated bit is set.
	 */
	public static final int UNCHANGED = 0;
   	public static final int DELETED = 1;
	public static final int ADDED = (1 << 1);
	public static final int SHRUNK = (1 << 2);
	public static final int EXPANDED = (1 << 3);
	public static final int FILL_CHANGED = (1 << 4);
	public static final int MOVED_INSIDE = (1 << 5);
	public static final int MOVED_OUTSIDE = (1 << 6);
	public static final int MOVED_ABOVE = (1 << 7);
	public static final int MOVED_BELOW = (1 << 8);
	public static final int MOVED_LEFT_OF = (1 << 9);
	public static final int MOVED_RIGHT_OF = (1 << 10);
	public static final int FLIPPED_VERTICALLY = (1 << 11);
	public static final int FLIPPED_HORIZONTALLY = (1 << 12);
	public static final int ROTATED = (1 << 13);
	
	/**
	 * Correlation Bitmap
	 * This bitmap stores the different correlations that can occur.
	 * They are ordered by importance, with the most important having the highest bit value.
	 */
	public static final int CORR_FILL = 1;
	public static final int CORR_SIZE = (1 << 1);
	public static final int CORR_SHAPE = (1 << 2);
	
	/**
	 * Transform properties stored by this class.
	 */
	public long transform;
	public String shape;
	public String size;
	public int rotation;
	public List<String> fill_transform;
	
	/**
	 * Constructor
	 */
	public RavensTransform() {transform=0; shape=""; size=""; rotation=0; fill_transform=new ArrayList<String>();}
	
	/**
	 * Print the string representation of the transform bitmap
	 */
	public void printTransform() {
		if((transform & RavensTransform.DELETED) > 0) System.out.print("DELETED ");
		if((transform & RavensTransform.ADDED) > 0) System.out.print("ADDED ");
		if((transform & RavensTransform.SHRUNK) > 0) System.out.print("SHRUNK ");
		if((transform & RavensTransform.EXPANDED) > 0) System.out.print("EXPANDED ");
		if((transform & RavensTransform.FILL_CHANGED) > 0) System.out.print("FILLED_CHANGE ");
		if((transform & RavensTransform.MOVED_INSIDE) > 0) System.out.print("MOVED_INSIDE ");
		if((transform & RavensTransform.MOVED_OUTSIDE) > 0) System.out.print("MOVED_OUTSIDE ");
		if((transform & RavensTransform.MOVED_ABOVE) > 0) System.out.print("MOVED_ABOVE ");
		if((transform & RavensTransform.MOVED_BELOW) > 0) System.out.print("MOVED_BELOW ");
		if((transform & RavensTransform.MOVED_LEFT_OF) > 0) System.out.print("MOVED_LEFT_OF ");
		if((transform & RavensTransform.MOVED_RIGHT_OF) > 0) System.out.print("MOVED_RIGHT_OF ");
		if((transform & RavensTransform.FLIPPED_VERTICALLY) > 0) System.out.print("FLIPPED_VERTICALLY ");
		if((transform & RavensTransform.FLIPPED_HORIZONTALLY) > 0) System.out.print("FLIPPED_HORIZONTALLY ");
		if((transform & RavensTransform.ROTATED) > 0) System.out.print("ROTATED ");
		System.out.println();
	}
        
        @Override
        public boolean equals(Object other) 
        {
        if (!(other instanceof RavensTransform)) {
            return false;
        }

        RavensTransform that = (RavensTransform) other;

    // Custom equality check .
    return (this.transform == that.transform)
            //&& this.shape.equals(that.shape) - the shape is not important for the general equality
            && this.size.equals(that.size)
            && this.rotation==that.rotation
            && this.fill_transform.equals(that.fill_transform);
    
        }
}


