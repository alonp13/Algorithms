package calcCars;

public class LinkedListCycle {// Double Cycle Linked List
	private Node head, tail;
	// Constructor,  constructs an empty list
	public LinkedListCycle(){
		head = tail = null;
	} 
	// Appends the specified element to the end of this list. 
	public void add(char obj){
		if (head == null){
			head = new Node(obj, null, null);
			tail = head;
			head.next = head.prev = tail;
		}
		else{
			Node n = new Node(obj, tail, head);
			tail.next = n;
			tail = n;
			head.prev = tail;
		}
	}
	public Node getHead(){
		return head;
	}
	public Node getNext(Node n){
		return n.next;
	}
	public String toString(){
		String s = "[";
		if (head != null){
			s = s + head.toString() + ", ";
			for (Node n = head.next; n != head; n=n.next){
				s = s + n.toString() + ", ";
			}
			s = s.substring(0, s.length()-2);
		}
		return s+"]";
	}
}// end LinkedListCycle
