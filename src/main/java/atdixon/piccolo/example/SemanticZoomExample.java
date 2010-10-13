package atdixon.piccolo.example;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.PFrame;

import java.awt.geom.Point2D;
import java.util.ListIterator;
import java.util.Random;

public class SemanticZoomExample extends PFrame {

    private static final int NODES = 1000;
    private static final int DIAM = 40;

    public static void main(String[] args) {
        new SemanticZoomExample();
    }

    private final Random random = new Random();

    @Override
    public void initialize() {
        setSize(800, 600);
        PCanvas canvas = getCanvas();
        PLayer layer = canvas.getLayer();
        for (int i = 0; i < NODES; ++i) {
            ZoomAwareCircle c = new ZoomAwareCircle();
            layer.addChild(c);
            c.offset(random.nextInt(800), random.nextInt(600));
        }
    }

    private static final class ZoomAwareCircle extends PPath {

        ZoomAwareCircle() {
            setPathToEllipse(0, 0, DIAM, DIAM);
            Point2D m = getBounds().getCenter2D();

            PPath c1 = PPath.createEllipse(0, 0, DIAM / 2, DIAM / 2);
            PPath c2 = PPath.createEllipse(0, 0, DIAM / 2, DIAM / 2);
            addChild(c1);
            addChild(c2);

            c1.centerBoundsOnPoint(m.getX(), m.getY());
            c1.translate(DIAM / 4, 0);
            c2.centerBoundsOnPoint(m.getX(), m.getY());
            c2.translate(- DIAM / 4, 0);
        }

        @Override
        protected void paint(PPaintContext pc) {
            setChildrenVisibility(pc.getScale() > 1.5);
            super.paint(pc);
        }

        private void setChildrenVisibility(boolean v) {
            ListIterator children = getChildrenIterator();
            while (children.hasNext()) {
                ((PNode) children.next()).setVisible(v);
            }
        }

    }

}
