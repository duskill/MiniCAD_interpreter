package is.shapes.view;

import is.shapes.model.GraphicEvent;
import is.shapes.model.GraphicObject;
import is.shapes.model.GraphicObjectListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import java.io.Serial;
import java.util.*;


import javax.swing.JComponent;

public class GraphicObjectPanel extends JComponent implements GraphicObjectListener {

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 8993548105090978185L;

	/**
	 * @directed true
	 */


	private final Map<Integer, GraphicObject> objects = new HashMap<>();


	public GraphicObjectPanel() {
		setBackground(Color.WHITE);
	}

	@Override
	public void graphicChanged(GraphicEvent e) {
		repaint();
		revalidate();

	}


	public GraphicObject getGraphicObjectAt(Point2D p) {
		for (GraphicObject g : objects.values()) {
			if (g.contains(p))
				return g;
		}
		return null;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension ps = super.getPreferredSize();
		double x = ps.getWidth();
		double y = ps.getHeight();
		for (GraphicObject go : objects.values()) {
			double nx = go.getPosition().getX() + go.getDimension().getWidth() / 2;
			double ny = go.getPosition().getY() + go.getDimension().getHeight() / 2;
			if (nx > x)
				x = nx;
			if (ny > y)
				y = ny;
		}
		return new Dimension((int) x, (int) y);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		for (GraphicObject go : objects.values()) {
			GraphicObjectView view = GraphicObjectViewFactory.FACTORY.createView(go);
			view.drawGraphicObject(go, g2);
		}

	}

	public void add(GraphicObject go) {
		objects.put(go.getId(), go);
		go.addGraphicObjectListener(this);
		repaint();
	}

	public void remove(GraphicObject go) {
		if (objects.remove(go.getId()) != null) {
			repaint();
			go.removeGraphicObjectListener(this);
		}

	}


	public List<GraphicObject> getObjects() {
		return  new ArrayList<>(objects.values());
	}

	public Set<Integer> getObjectsID() {return objects.keySet();}

	public GraphicObject getObjectById(int id) {
		return objects.get(id);
	}
}
