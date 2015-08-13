package millstein.RunBitMan2.gameEntities;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GameEntity implements Shape {

	int xPos, yPos, xSize, ySize;

	public GameEntity(int startX, int startY, int sizeX, int sizeY) {
		xPos = startX;
		yPos = startY;
		xSize = sizeX;
		ySize = sizeY;

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, xPos + xSize, yPos + ySize);
	}

	@Override
	public Rectangle2D getBounds2D() {
		return getBounds2D();
	}

	@Override
	public boolean contains(double x, double y) {
		if ((xSize == 0) && (ySize == 0)) {
			return false;
		} else {
			return (inRange(x, xPos, xSize) && inRange(y, yPos, ySize));
		}
	}

	@Override
	public boolean contains(Point2D p) {
		return contains(p.getX(), p.getY());
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return intersects(new Rectangle(new Point((int) x, (int) y),
				new Dimension((int) w, (int) h)));
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		int tw = this.xSize;
		int th = this.ySize;
		double rw = r.getWidth();
		double rh = r.getHeight();

		if ((rw <= 0) || (rh <= 0) || (tw <= 0) || (th <= 0)) {
			return false;
		}

		int tx = this.xPos;
		int ty = this.yPos;
		double rx = r.getX();
		double ry = r.getY();

		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		// overflow || intersect
		return (((rw < rx) || (rw > tx)) && ((rh < ry) || (rh > ty))
				&& ((tw < tx) || (tw > rx)) && ((th < ty) || (th > ry)));
	}

	@Override
	public boolean contains(double X, double Y, double doubleW,
			double doubleH) {
		int W = (int) doubleW;
		int H = (int) doubleH;

		int w = this.xSize;
		int h = this.ySize;

		if ((w | h | W | H) < 0) {
			return false;
		}

		int x = this.xPos;
		int y = this.yPos;

		if ((X < x) || (Y < y)) {
			return false;
		}

		w += x;
		W += X;

		if (W <= X) {
			if ((w >= x) || (W > w)) {
				return false;
			}
		} else {
			if ((w >= x) && (W > w)) {
				return false;
			}
		}

		h += y;
		H += Y;

		if (H <= Y) {
			if ((h >= y) || (H > h)) {
				return false;
			}
		} else {
			if ((h >= y) && (H > h)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean inRange(double x, double min, double max) {
		return (x > min) && (x < max);
	}
}
