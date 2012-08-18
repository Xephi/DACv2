package fr.aumgn.dac2.arena.regions.shape;

import java.util.Iterator;

import org.apache.commons.lang.Validate;

import com.google.common.collect.AbstractLinkedIterator;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;

public class Column implements Iterable<Vector> {

    private final double minY;
    private final double maxY;
    private final Vector2D pt2D;

    public Column(FlatShape shape, Vector2D pt2D) {
        this.minY = shape.getMinY();
        this.maxY = shape.getMaxY();
        this.pt2D = pt2D;
    }

    public Vector get(double y) {
        Validate.isTrue(y >= minY && y <= maxY);
        return pt2D.to3D(y);
    }

    @Override
    public Iterator<Vector> iterator() {
        return new AbstractLinkedIterator<Vector>(pt2D.to3D(minY)) {
            @Override
            protected Vector computeNext(Vector pt) {
                return (pt.getY() >= maxY) ? null : pt.addY(1);
            }
        };
    }
}