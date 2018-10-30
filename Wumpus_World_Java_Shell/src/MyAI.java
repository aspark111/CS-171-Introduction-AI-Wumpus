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
import java.lang.Object;
import java.awt.*;


public class MyAI extends Agent
{
	private ArrayList<Point> coordinates = new ArrayList<Point>();
	private enum Direction {NORTH, EAST, SOUTH, WEST}
	private Direction agent_direction;
	public MyAI ( )
	{
		coordinates.add(new Point(0,0));
		agent_direction = Direction.EAST;

	}
	
	public Action getAction
	(boolean stench,
	boolean breeze,
	boolean glitter,
	boolean bump,
	boolean scream)
	{
		Point origin = new Point(0,0);
		if((stench || breeze) && coordinates.get(coordinates.size()-1) ==  origin)
		{
			return Action.CLIMB;
		}
		updatePath();
		return Action.CLIMB;
		if(stench || breeze && coordinates.get(coordinates.size()-1) == origin)
		{
		    return Action.CLIMB;
		}

		//goBack functior
		//if(stench || breeze) go back
		//try different path than before
		//keep track of different direction function
		
		//updatePath();
		//return Action.FORWARD;

		if(coordinates.get(coordinates.size() - 1) == origin)
		{
		    //have to make getGold function
		    return Action.CLIMB;
		}


	}
	
	public void updatePath()
	{
	    Point current_position = coordinates.get(coordinates.size()-1);
	    int x = current_position.getX();
	    int y = current_position.getY();
	    if(agent_direction == Direction.NORTH && bump == false){

		x += 1;
	    }
	    else if(agent_direction == Direction.EAST && bump == false){
		y += 1;
	    }
	    else if(agent_direction == Direction.SOUTH && !inFrontOfWall(current_position)){
		x -= 1;
	    }
	    else if(agent_direction == Direction.WEST && !inFrontOfWall(current_position)){
		y -= 1;
	    }
	    coordinates.add(new Point(x,y));
	}

	public void goBack()
	{
	}

}
