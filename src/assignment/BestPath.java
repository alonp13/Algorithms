package assignment;

import java.util.ArrayList;

public class BestPath {

	private Node[][] mat;
	private int teta;

	private Node dest;
	private int width, height;

	private int min_turns = Integer.MAX_VALUE;
	private ArrayList<String> cheapest_paths;
	private ArrayList<String> optimal_paths;

	private int sec_min_turns = Integer.MAX_VALUE;
	private ArrayList<String> sec_cheapest_paths;
	private ArrayList<String> sec_optimal_paths;

	public BestPath(Node[][] mat, int teta) {
		this.mat = mat;
		this.width = mat[0].length;
		this.height = mat.length;
		this.dest = this.mat[height - 1][width - 1];

		this.teta = teta;
		this.cheapest_paths = new ArrayList<String>();
		this.sec_cheapest_paths = new ArrayList<String>();
		this.optimal_paths = new ArrayList<String>();
		this.sec_optimal_paths = new ArrayList<String>();

		initMatrix();
		computeAllCheapestPaths();
		computeAllSecCheapestPaths();
	}

/*
 * Auxiliary methods:
 */
	private void initMatrix() {
		mat[0][0].setPrice(0);
		for (int j = 1; j < mat[0].length; j++) {
			Node curr_node = mat[0][j];
			Node from_node = mat[0][j - 1];
			double total_price = from_node.getX() + from_node.getPrice();
			curr_node.setPrice(total_price);
			curr_node.setSecPrice(total_price);
			curr_node.setSecPrev(0, from_node);

			curr_node.setNumOfPaths(1);
			curr_node.setPrev(0, from_node);
		}

		for (int i = 1; i < mat.length; i++) {
			Node curr_node = mat[i][0];
			Node from_node = mat[i - 1][0];
			double total_price = from_node.getY() + from_node.getPrice();
			curr_node.setPrice(total_price);
			curr_node.setSecPrice(total_price);

			curr_node.setNumOfPaths(1);
			curr_node.setPrev(1, from_node);
			curr_node.setSecPrev(1, from_node);
		}

		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[i].length; j++) {
				Node curr_node = mat[i][j];
				Node left_node = mat[i][j - 1];
				Node down_node = mat[i - 1][j];
				double left_price = left_node.getX() + left_node.getPrice();
				double down_price = down_node.getY() + down_node.getPrice();

				double sec_left_price = left_node.getX() + left_node.getSecPrice();
				double sec_down_price = down_node.getY() + down_node.getSecPrice();

				if (left_price < down_price) {
					curr_node.setPrice(left_price);
					curr_node.setNumOfPaths(left_node.getNumOfPaths());
					curr_node.setPrev(0, left_node);

					if (down_price < sec_left_price || left_price == sec_left_price) {
						curr_node.setSecPrice(down_price);
						if (left_price == sec_left_price) {
							curr_node.setSecPrev(0, left_node);
						} else {
							curr_node.setSecPrev(1, down_node);
						}
					} else {
						curr_node.setSecPrice(sec_left_price);
						curr_node.setSecPrev(0, left_node);
					}

				} else if (down_price < left_price) {
					curr_node.setPrice(down_price);
					curr_node.setNumOfPaths(down_node.getNumOfPaths());
					curr_node.setPrev(1, down_node);

					if (left_price < sec_down_price || down_price == sec_down_price) {
						curr_node.setSecPrice(left_price);
						if (down_price == sec_down_price) {
							curr_node.setSecPrev(1, down_node);
						} else {
							curr_node.setSecPrev(0, left_node);
						}
					} else {
						curr_node.setSecPrice(sec_down_price);
						curr_node.setSecPrev(1, down_node);
					}

				} else {
					curr_node.setPrice(left_price);
					curr_node.setNumOfPaths(left_node.getNumOfPaths() + down_node.getNumOfPaths());
					curr_node.setPrev(0, left_node);
					curr_node.setPrev(1, down_node);

					if (sec_down_price < sec_left_price) {
						curr_node.setSecPrice(sec_down_price);
						curr_node.setSecPrev(1, down_node);
					} else {
						curr_node.setSecPrice(sec_left_price);
						curr_node.setSecPrev(0, left_node);
					}

				}

			}
		}

	}

	private void computeAllCheapestPaths() {
		char[] str = new char[width + height - 2];
		computeAllCheapestPaths(dest, str, width + height - 3);
	}

	private void computeAllCheapestPaths(Node n, char[] str, int level) {
		if (cheapest_paths.size() >= teta)
			return;

		if (n.getID() == 0) {
			String path = new String(str);
			cheapest_paths.add(path);

			int turns = checkTurns(str);

			if (turns == min_turns) {
				optimal_paths.add(path);
			}

			if (turns < min_turns) {
				min_turns = turns;
				optimal_paths.clear();
				optimal_paths.add(path);
			}
		}

		if (n.getPrevs().get(1) != null) {
			str[level] = '1';
			computeAllCheapestPaths(n.getPrevs().get(1), str, level - 1);
		}

		if (n.getPrevs().get(0) != null) {
			str[level] = '0';
			computeAllCheapestPaths(n.getPrevs().get(0), str, level - 1);
		}

	}

	private void computeAllSecCheapestPaths() {
		char[] str = new char[width + height - 2];
		computeAllSecCheapestPaths(dest, str, width + height - 3);
	}

	private void computeAllSecCheapestPaths(Node n, char[] str, int level) {
		if (sec_cheapest_paths.size() >= teta)
			return;

		if (n.getID() == 0) {
			String path = new String(str);
			sec_cheapest_paths.add(path);

			int turns = checkTurns(str);

			if (turns == sec_min_turns) {
				sec_optimal_paths.add(path);
			}

			if (turns < sec_min_turns) {
				sec_min_turns = turns;
				sec_optimal_paths.clear();
				sec_optimal_paths.add(path);
			}
		}

		if (n.getSecPrevs().get(1) != null) {
			str[level] = '1';
			computeAllSecCheapestPaths(n.getSecPrevs().get(1), str, level - 1);
		}

		if (n.getSecPrevs().get(0) != null) {
			str[level] = '0';
			computeAllSecCheapestPaths(n.getSecPrevs().get(0), str, level - 1);
		}
	}

	private int checkTurns(char[] str) {
		int turns = 0;
		for (int i = 1; i < str.length; i++) {
			if (str[i] != str[i - 1]) {
				turns++;
			}
		}
		return turns;
	}

/*
 * Assignment methods:
 */
	
	// Q2:	
	public int getNumOfCheapestPaths() {
		return cheapest_paths.size();
	}

	public int getNumOfOptimalPaths() {
		return optimal_paths.size();
	}

	public double getCheapestPrice() {
		return dest.getPrice();
	}

	public int printNumOfTurns() {
		return min_turns;
	}

	public ArrayList<String> getAllCheapestPaths() {
		return cheapest_paths;
	}

	public ArrayList<String> getAllOptimalPaths() {
		return optimal_paths;
	}

	// Q3: 	
	public int getNumOfCheapestPaths2() {
		return sec_cheapest_paths.size();
	}

	public int getNumOfOptimalPaths2() {
		return sec_optimal_paths.size();
	}

	public double getCheapestPrice2() {
		return dest.getSecPrice();
	}

	public int printNumOfTurns2() {
		return sec_min_turns;
	}

	public ArrayList<String> getAllCheapestPaths2() {
		return sec_cheapest_paths;
	}

	public ArrayList<String> getAllOptimalPaths2() {
		return sec_optimal_paths;
	}

	public static void main(String[] args) {
		Node[][] mat = new Node[4][4];

		mat[0][0] = new Node(1, 3);
		mat[0][1] = new Node(8, 4);
		mat[0][2] = new Node(3, 8);
		mat[0][3] = new Node(-1, 4);
		mat[1][0] = new Node(2, 5);
		mat[1][1] = new Node(5, 11);
		mat[1][2] = new Node(3, 1);
		mat[1][3] = new Node(-1, 2);
		mat[2][0] = new Node(4, 10);
		mat[2][1] = new Node(3, 1);
		mat[2][2] = new Node(1, 4);
		mat[2][3] = new Node(-1, 8);
		mat[3][0] = new Node(2, -1);
		mat[3][1] = new Node(3, -1);
		mat[3][2] = new Node(5, -1);
		mat[3][3] = new Node(-1, -1);

		BestPath bp = new BestPath(mat, 4);

		System.out.println(mat[2][1].getSecPrevs());

		System.out.println("num of cheapest paths: " + bp.getNumOfCheapestPaths());
		System.out.println("the cheapets price is: " + bp.getCheapestPrice());
		System.out.println("the paths: " + bp.getAllCheapestPaths());
		System.out.println("num of optimal paths: " + bp.getNumOfOptimalPaths());
		System.out.println("the optimal paths: " + bp.getAllOptimalPaths());
		System.out.println("num of minimum turns: " + bp.printNumOfTurns());
		System.out.println();
		System.out.println("num of sec cheapest paths: " + bp.getNumOfCheapestPaths2());
		System.out.println("the sec cheapets price is: " + bp.getCheapestPrice2());
		System.out.println("the sec paths: " + bp.getAllCheapestPaths2());
		System.out.println("num of sec optimal paths: " + bp.getNumOfOptimalPaths2());
		System.out.println("the sec optimal paths: " + bp.getAllOptimalPaths2());
		System.out.println("num of sec minimum turns: " + bp.printNumOfTurns2());

//		System.out.println(bp.getCheapestPrice());
//		System.out.println(bp.getNumOfCheapestPaths());
//		System.out.println(bp.getNumOfOptimalPaths());
//		System.out.println(bp.getAllCheapestPaths());
//		System.out.println(bp.min_turns);
//		System.out.println(bp.getAllOptimalPaths());

	}

}
