package gr.charos.christmas.day8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Instructions {
	private final List<Move> moves;
	private final List<Node> nodes;

	public Instructions(List<Move> moves, List<Node> nodes) {
		super();
		this.moves = moves;
		this.nodes = nodes;
	}

	public Node executeStartingAt(String nodeName) {
		Node current = this.nodes.stream().filter(p -> p.getName().equalsIgnoreCase(nodeName)).findFirst().get();

		return executeStartingAt(current);
	}
	
	public Node executeStartingAt(Node current) {

		for (int i = 0; i < moves.size(); i++) {
			current = current.move(moves.get(i));
		}

		return current;
	}
	
	

	public List<Move> getMoves() {
		return moves;
	}
	
	public List<Node> nodesEndingAt(String end) {
		return this.nodes.stream().filter(p->p.getName().endsWith(end)).collect(Collectors.toList());
	}

	public static Instructions from(List<String> lines) {
		String instr = lines.get(0);
		List<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < instr.length(); i++) {
			moves.add(Move.of(instr.substring(i, i + 1)));
		}

		Map<String, Node> map = new HashMap<String, Node>();

		for (int i = 2; i < lines.size(); i++) {
			String name = lines.get(i).substring(0, 3);
			map.put(name, Node.of(name));

		}

		// get left & right nodes
		for (int i = 2; i < lines.size(); i++) {
			String name = lines.get(i).substring(0, 3);
			String nameLeft = lines.get(i).substring(7, 10);
			String nameRight = lines.get(i).substring(12, 15);
			Node n = map.get(name);
			Node left = map.get(nameLeft);
			Node right = map.get(nameRight);

			n.setNodeLeft(left);
			n.setNodeRight(right);

		}
		List<Node> nodes = new ArrayList(map.values());

		return new Instructions(moves, nodes);
	}

	enum Move {
		LEFT("L"), RIGHT("R");

		private String name;

		Move(String name) {
			this.name = name;
		}

		public static Move of(String l) {
			return Arrays.asList(Move.values()).stream().filter(p -> p.name.equalsIgnoreCase(l)).findFirst().get();
		}
	}

	public static class Node {
		private Node nodeLeft;
		private Node nodeRight;
		private final String name;
		
		

		public Node move(Move m) {
			if (Move.LEFT.equals(m)) {
				return nodeLeft;
			}
			if (Move.RIGHT.equals(m)) {
				return nodeRight;
			}
			throw new IllegalArgumentException("What the heck a move is this?");
		}

		public Node(String name) {
			this.name = name;
		}

		public Node getNodeLeft() {
			return nodeLeft;
		}

		public void setNodeLeft(Node nodeLeft) {
			this.nodeLeft = nodeLeft;
		}

		public Node getNodeRight() {
			return nodeRight;
		}

		public void setNodeRight(Node nodeRight) {
			this.nodeRight = nodeRight;
		}

		public String getName() {
			return name;
		}
		
		
		

		@Override
		public String toString() {
			return "Node [name=" + name + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			return Objects.equals(name, other.name);
		}

		public static Node of(String name) {
			return new Node(name);
		}

	}
}