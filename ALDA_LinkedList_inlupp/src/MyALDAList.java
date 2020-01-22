import java.util.Iterator;

public class MyALDAList<T> implements ALDAList<T> {


	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int indexOf(T element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	private class Node{
		Node nextNode;
		T data;
		
	}

}
