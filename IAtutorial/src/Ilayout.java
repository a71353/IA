import java.util.*;

public interface Ilayout {
	/**
	 @return the children of the receiver.
	 */
	
	List<Ilayout>children();
	
	/**
	 @return true if the receiver equals the argument 1; return false otherwise.
	 */
	boolean isGoal(Ilayout I);
	
	/**
	 @return the cost for moving from the input config to the receiver.
	 */
	int getG();

}
