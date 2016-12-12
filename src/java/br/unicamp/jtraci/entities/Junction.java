package br.unicamp.jtraci.entities;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

public class Junction extends Entity {

    private Point2D position;
    private Path2D shape;
    private ReadQuery<Junction> junctionReadQuery;


    public Junction() {
        junctionReadQuery = new ReadQuery<Junction>(SumoSimulation.getInstance().getConnection(), Junction.class);
    }

    public Point2D getPosition() {

        position = (Point2D) junctionReadQuery.getAttributeValue(Constants.VAR_POSITION, ID, Point2D.class);

        return position;
    }

    public Path2D getShape() {

        shape = (Path2D) (junctionReadQuery.getAttributeValue(Constants.VAR_SHAPE, ID, Path2D.class));

        return shape;
    }




}
