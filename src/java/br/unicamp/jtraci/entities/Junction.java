package br.unicamp.jtraci.entities;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

/**
 * Created by du on 07/12/16.
 */
public class Junction extends Entity {

    private Point2D position;
    private Path2D shape;
    private ReadQuery<Junction> junctionReadQuery;
    private List<Edge> incommingEdges;
    private List<Edge> outgoingLanes;
    private List<Edge> allEdges;


    private SumoSimulation sumoSimulation;


    public Junction() {
        junctionReadQuery = new ReadQuery<Junction>(SumoSimulation.getInstance().getConnection(), Junction.class);
        sumoSimulation = SumoSimulation.getInstance();

        incommingEdges = new ArrayList<Edge>();
        outgoingLanes = new ArrayList<Edge>();
        allEdges = new ArrayList<Edge>();
    }

    public Point2D getPosition() {

        position = (Point2D) junctionReadQuery.getAttributeValue(Constants.VAR_POSITION, ID, Point2D.class);

        return position;
    }

    public Path2D getShape() {

        shape = (Path2D) (junctionReadQuery.getAttributeValue(Constants.VAR_SHAPE, ID, Path2D.class));

        return shape;
    }

    public List<Edge> getIncommingEdges() {

        List<Edge> allEdges = getAllEdges();

        if (incommingEdges.size() == 0) {
            incommingEdges = new ArrayList<Edge>();

            for (Edge edge : allEdges) {

                String[] edgeSplited = edge.getID().split(":");

                if (edgeSplited[1].equals(getID())) {
                    incommingEdges.add(edge);
                }

            }
        }


        return incommingEdges;
    }

    public List<Edge> getOutgoingEdges() {

        List<Edge> allEdges = getAllEdges();

        if (outgoingLanes.size() == 0) {
            outgoingLanes = new ArrayList<Edge>();

            for (Edge edge : allEdges) {

                String[] edgeSplited = edge.getID().split(":");

                if (edgeSplited[0].equals(getID())) {
                    outgoingLanes.add(edge);
                }

            }
        }

        return outgoingLanes;
    }

    private List<Edge> getAllEdges() {
        if (allEdges.size() == 0) {
            allEdges = sumoSimulation.getAllEdges();
        }

        return allEdges;
    }
}
