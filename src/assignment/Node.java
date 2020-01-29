package assignment;

import java.util.LinkedHashMap;

public class Node {

	// Q1:
	
	private static int ID = 0; // for seriallize nodes

	private int id;
	private double x, y;
	private double cheapest_price, sec_cheapest_price;
	private int num_of_cheapest_paths;

	private LinkedHashMap<Integer, Node> prevs;
	private LinkedHashMap<Integer, Node> sec_prevs;

	public Node(double x, double y) {
		id = ID++;

		this.x = x;
		this.y = y;
		prevs = new LinkedHashMap<Integer, Node>();
		sec_prevs = new LinkedHashMap<Integer, Node>();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getPrice() {
		return cheapest_price;
	}

	public double getSecPrice() {
		return sec_cheapest_price;
	}

	public void setPrice(double price) {
		this.cheapest_price = price;
	}

	public void setSecPrice(double sec_price) {
		this.sec_cheapest_price = sec_price;
	}

	public int getNumOfPaths() {
		return num_of_cheapest_paths;
	}

	public void setNumOfPaths(int num_of_paths) {
		this.num_of_cheapest_paths = num_of_paths;
	}

	public void setPrev(int from, Node prev) {
		prevs.put(from, prev);
	}

	public void setSecPrev(int from, Node prev) {
		sec_prevs.put(from, prev);
	}

	public int getID() {
		return id;
	}

	public LinkedHashMap<Integer, Node> getPrevs() {
		return prevs;
	}

	public LinkedHashMap<Integer, Node> getSecPrevs() {
		return sec_prevs;
	}

	@Override
	public String toString() {
		// return "("+price+")|r:"+x+"|u:"+y+" ";
		return "(" + id + ")";
	}
}
