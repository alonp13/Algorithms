package calcCars;

public class CalcCarsLinkList {
	LinkedListCycle cars;
	final int nLetters = 23, size = 5;
	final char v='v',  w = 'w';
	
	public CalcCarsLinkList(){
// build parking
		cars = new LinkedListCycle();
		for (int i=0; i<size; i++){
			char c = (char)('a' + (int)(Math.random()*nLetters));
			cars.add(c);
		}
		System.out.println(cars.toString());
	}
	 
// cars calculation
	public int calcCars(){
		cars.getHead().setData(v);
		Node t = cars.getHead().getNext();
		boolean flag = true; //continue
		int count = 1;
		while(flag){
			if (t.getData()==v){
				t.setData(w);
				int i = count;
				while(i>0){
					t = t.getPrev();
					i--;
				}
				if (t.getData() == w) flag = false;
				else{
					count = 1;
					t = cars.getHead().getNext();
				}
			}
			else{
				t = t.getNext();
				count++;
			}
		}
		return count;
	}
	public static void main(String[] args) {
		CalcCarsLinkList parking = new CalcCarsLinkList();
		System.out.println("number of cars = "+parking.calcCars());
	}

}
