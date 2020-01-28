package calcCars;

public class Node {
	char data;
	Node prev, next;
	public Node(char data, Node prev, Node next){
		this.data = data;
		this.next = next;
		this.prev = prev;
	} 
	public String toString(){ return ""+this.data; }
	public void setData(char c){ this.data = c; }
	public char getData(){ return this.data; }
	public Node getNext(){ return this.next;}
	public Node getPrev(){ return this.prev; }
}
