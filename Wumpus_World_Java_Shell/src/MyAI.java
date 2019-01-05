// ======================================================================
// FILE:        MyAI.java
//
// AUTHOR:      Abdullah Younis
//
// DESCRIPTION: This file contains your agent class, which you will
//              implement. You are responsible for implementing the
//              'getAction' function and any helper methods you feel you
//              need.
//
// NOTES:       - If you are having trouble understanding how the shell
//                works, look at the other parts of the code, as well as
//                the documentation.
//
//              - You are only allowed to make changes to this portion of
//                the code. Any changes to other portions of the code will
//                be lost when the tournament runs your code.
// ======================================================================
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;
import java.awt.Point;
import java.awt.*;


public class MyAI extends Agent
{
	private ArrayList<Point> coordinates = new ArrayList<Point>();
	private ArrayList<Point> visited = new ArrayList<Point>();
	private Map<String,Point> states = new HashMap<String, Point>();
	private enum Direction {NORTH, EAST, SOUTH, WEST}
	private Direction agent_direction;
	private double x;
	private double y;
	private double maxX;
	private double maxY;
	private boolean gold;
	private int backturns;
	public MyAI ()
	{
	//---------------YOUR CODE STARTS HERE-------------//
		maxX = 0.0; maxY = 0.0; x = 0.0; y = 0.0; backturns = 0; gold = false;
		coordinates.add(new Point(0,0));
		visited.add(new Point(0,0));
		agent_direction = Direction.EAST;
	//---------------YOUR CODE ENDS HERE--------------//
	}
	public Action getAction
	(boolean stench,
	boolean breeze,
	boolean glitter,
	boolean bump,
	boolean scream)
	{
	//-------------------------YOUR CODE STARTS HERE---------------//
		Point origin = new Point(0,0);
		Point current = coordinates.get(coordinates.size()-1);
		
		checkBumps(bump);
		if(glitter == true){
			gold = true;
			return Action.GRAB;
		}
		if(gold == true){
			if(x == 0 && y == 0)
				return Action.CLIMB;
			else
				return goBack();
		}
		if(!(breeze || stench))
			return visit();
		if((breeze || stench) && current.equals(origin))
			return Action.CLIMB;
		if(breeze || stench)
			return goBack();
		if(current.equals(origin))
			return Action.CLIMB;
		updatePath();
		return Action.CLIMB;
	//--------------------------YOUR CODE ENDS HERE-----------------//
	}
	

	//--------------------------YOUR CODE STARTS HERE---------------//
	public void updatePath()
	{
	    Point current_position = coordinates.get(coordinates.size()-1);
	    x = current_position.getX();
	    y = current_position.getY();
	    if(agent_direction == Direction.NORTH)
		y += 1;
	    if(agent_direction == Direction.EAST)
		x += 1;
	    if(agent_direction == Direction.SOUTH)
		y -= 1;
	    if(agent_direction == Direction.WEST)
		x -= 1;
	    coordinates.add(new Point((int)x,(int)y));
	    if(!visited.contains(new Point((int)x,(int)y)))
		visited.add(new Point((int)x,(int)y));
	}
	public void checkBumps(boolean bump){
	    Point current_position = coordinates.get(coordinates.size()-1);
	    if(agent_direction == Direction.EAST && bump == true){
	        coordinates.remove(current_position);
		x -= 1;
		maxX = x;
	    }
	    else if(agent_direction == Direction.NORTH && bump == true){
	        coordinates.remove(current_position); 
		y -= 1;
		maxY = y;
	    }
	    else if(agent_direction == Direction.SOUTH && bump == true){
	        coordinates.remove(current_position);
		y += 1;
	    }
	    else if(agent_direction == Direction.WEST && bump == true ){
		coordinates.remove(current_position);
		x += 1;
	    }
	}

	public Action goBack()
	{
	//backtracks movements that have been taken and input into the variable "coordinates"
	//removes most recent "cuurent" state and gets the previous "current" state
	//algorithm is modified to move to that tile without using updatePath()
	//only modifies the variable "coordinates" and does not modify "visited"
	//we don't use updatePath() because it messes up the current value when backtracking
	//used when gold is grabbed or when all adjacent tiles have been visited
	    
	    Point previouscurrent = coordinates.get(coordinates.size()-2);
	    if(previouscurrent.equals(new Point((int)x+1, (int)y))){
		if(agent_direction == Direction.WEST){
			agent_direction = Direction.SOUTH;
			return Action.TURN_LEFT;
		}
	        if(agent_direction == Direction.SOUTH){
			agent_direction = Direction.EAST;
			return Action.TURN_LEFT;
		}
	        if(agent_direction == Direction.NORTH){
			agent_direction = Direction.EAST;
			return Action.TURN_RIGHT;
		}
	        //if(agent_direction == Direction.EAST){
	        else {
			if(coordinates.size() > 1)
				coordinates.remove(coordinates.size()-1);
			x += 1;
			return Action.FORWARD;
	    	}
	    }
	    else if(previouscurrent.equals(new Point((int)x-1,(int)y))){
	        if(agent_direction == Direction.WEST){
			if(coordinates.size() > 1)
				coordinates.remove(coordinates.size()-1);
			x -= 1;
			return Action.FORWARD;
	    	}
	    	if(agent_direction == Direction.EAST){
			agent_direction = Direction.NORTH;
			return Action.TURN_LEFT;
		}
	    	if(agent_direction == Direction.NORTH){
			agent_direction = Direction.WEST;
			return Action.TURN_LEFT;
		}
	    	//if(agent_direction == Direction.SOUTH){
	    	else {
			agent_direction = Direction.WEST;
			return Action.TURN_RIGHT;
		}
	    }
	    else if(previouscurrent.equals(new Point((int)x, (int)y+1))){
	    	if(agent_direction == Direction.WEST){
			agent_direction = Direction.NORTH;
			return Action.TURN_RIGHT;
		}
	    	if(agent_direction == Direction.EAST){
			agent_direction = Direction.NORTH;
			return Action.TURN_LEFT;
		}
	    	if(agent_direction == Direction.NORTH){
			if(coordinates.size() > 1)
				coordinates.remove(coordinates.size()-1);
			y += 1;
			return Action.FORWARD;
	    	}
	    	//if(agent_direction == Direction.SOUTH){
	    	else {
			agent_direction = Direction.WEST;
			return Action.TURN_RIGHT;
		}
	    }
	    else {
	    	if(agent_direction == Direction.WEST){
			agent_direction = Direction.SOUTH;
			return Action.TURN_LEFT;
		}
	    	if(agent_direction == Direction.EAST){
			agent_direction = Direction.SOUTH;
			return Action.TURN_RIGHT;
		}
	    	if(agent_direction == Direction.NORTH){
			agent_direction = Direction.WEST;
			return Action.TURN_LEFT;
		}
		else {
	    	//if(agent_direction == Direction.SOUTH){
			if(coordinates.size() > 1)
				coordinates.remove(coordinates.size()-1);
			y -= 1;
			return Action.FORWARD;
	    	}
	    }
	}
	
	public Action visit(){
	//visits unvisited tiles. Used when agent is standing on a clean tile
	//it determines which of the adjacent tiles are not visited, and then visits them.
	//If all the adjacent tiles have been visited, then it backtracks and looks for other non-visited tiles.
	    if(!visited.contains(new Point((int)x+1, (int)y))){
		if(agent_direction == Direction.WEST){
			agent_direction = Direction.SOUTH;
			return Action.TURN_LEFT;
		}
	        if(agent_direction == Direction.SOUTH){
			agent_direction = Direction.EAST;
			return Action.TURN_LEFT;
		}
	        if(agent_direction == Direction.NORTH){
			agent_direction = Direction.EAST;
			return Action.TURN_RIGHT;
		}
		else {
	        //if(agent_direction == Direction.EAST){
			updatePath();
			return Action.FORWARD;
	    	}
	    }
	    else if(!visited.contains(new Point((int)x, (int)y+1))){
	    	if(agent_direction == Direction.WEST){
			agent_direction = Direction.NORTH;
			return Action.TURN_RIGHT;
		}
	    	if(agent_direction == Direction.EAST){
			agent_direction = Direction.NORTH;
			return Action.TURN_LEFT;
		}
	    	if(agent_direction == Direction.NORTH){
			updatePath();
			return Action.FORWARD;
	    	}
		else {
	    	//if(agent_direction == Direction.SOUTH){
			agent_direction = Direction.WEST;
			return Action.TURN_RIGHT;
		}
	    }
	    else if(!visited.contains(new Point((int)x, (int)y-1))){
	    	if(agent_direction == Direction.WEST){
			agent_direction = Direction.SOUTH;
			return Action.TURN_LEFT;
		}
	    	if(agent_direction == Direction.EAST){
			agent_direction = Direction.SOUTH;
			return Action.TURN_RIGHT;
		}
	    	if(agent_direction == Direction.NORTH){
			agent_direction = Direction.WEST;
			return Action.TURN_LEFT;
		}
		else {
	    	//if(agent_direction == Direction.SOUTH){
			updatePath();
			return Action.FORWARD;
	    	}
	    }
	    else if(!visited.contains(new Point((int)x-1,(int)y))){
	        if(agent_direction == Direction.WEST){
			updatePath();
			return Action.FORWARD;
	    	}
	    	if(agent_direction == Direction.EAST){
			agent_direction = Direction.NORTH;
			return Action.TURN_LEFT;
		}
	    	if(agent_direction == Direction.NORTH){
			agent_direction = Direction.WEST;
			return Action.TURN_LEFT;
		}
		else {
	    	//if(agent_direction == Direction.SOUTH){
			agent_direction = Direction.WEST;
			return Action.TURN_RIGHT;
		}
	    }
	    else if(visited.contains(new Point(0,0)) && x == 0 && y == 0){
		return Action.CLIMB;
	    }
	    else {
		return goBack();    
	    }

	}

	//---------------------------YOUR CODE ENDS HERE-----------------//

}





