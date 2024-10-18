package ch.heig.sio.lab1.groupH;
import ch.heig.sio.lab1.tsp.Edge;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

  /**
   * A traversal of the edges of the canonical tour.
   */
  public class TraversalIterator implements Iterator<Edge> {
    /** Number of cities. */
    private final int n;

    /** Start of the next edge. */
    private int i = 0;
    private ArrayList<Integer> cities;

    TraversalIterator(ArrayList<Integer> cities) {
        this.n = cities.size();
        this.cities = cities;
    }

    @Override
    public boolean hasNext() {
      return i < n;
    }

    @Override
    public Edge next() {
      if (!hasNext())
        throw new NoSuchElementException();
      return new Edge(cities.get(i), cities.get(++i % n));
    }
  }
