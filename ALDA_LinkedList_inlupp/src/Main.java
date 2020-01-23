import java.util.Iterator;

public class Main {
	
	
	public static void main(String[] args) {
		MyALDAList<Integer> list = new MyALDAList<Integer>();
		
		list.add(2);
		System.out.println("Size " + list.size);
		list.add(0, 3);
		System.out.println("Size " + list.size);
		list.add(1, 4);
		System.out.println("Size " + list.size);
		
		Iterator<MyALDAList<Integer>> it = list.iterator();
		for(int i = 0; i < list.size ; i++)
			if(it.hasNext())
				System.out.println(it.next());
		System.out.println();
		
		
		list.remove((Integer)4);
		
		Iterator<MyALDAList<Integer>> it2 = list.iterator();
		for(int i = 0; i < list.size ; i++)
			if(it2.hasNext())
				System.out.println(it2.next());
		
	}

}
