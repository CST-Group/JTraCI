/**
 * 
 */
package br.unicamp.jtraci.entities;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

/**
 * @author andre
 *
 */
public class Junction extends Entity {
	
	private ReadQuery<Junction> junctionReadQuery;

	/**
	 * (0x42) 	position 	Returns the position of the named junction [m,m]
	 */
	private Point2D position; 
	
	/**
	 *  (0x4e) 	2D-polygon 	Returns the shape (list of 2D-positions) of the named junction
	 */
	private Path2D shape; 
	
	
	public Junction(){
		
		
		junctionReadQuery = new ReadQuery<Junction>(SumoSimulation.getInstance().getConnection(), Junction.class);
		
	}

	/**
	 * @return the position
	 */
	public Point2D getPosition() {
		
		position = (Point2D) junctionReadQuery.getAttributeValue(Constants.VAR_POSITION, ID, Point2D.class);
		
		return position;
	}

	/**
	 * @return the shape
	 */
	public Path2D getShape() {
		
		shape = (Path2D) (junctionReadQuery.getAttributeValue(Constants.VAR_LANE_SHAPE, ID, Path2D.class));
		
		return shape;
	}
}
