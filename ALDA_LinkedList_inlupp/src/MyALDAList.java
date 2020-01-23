import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<T> implements ALDAList<T> {

	final Node<T> first = new Node<T>(null);
	final Node<T> last = new Node<T>(null);
	Node<T> lastElement = first;
	int currentOperations = 0;
	int size = 0;

	public MyALDAList() {
		first.nextNode = last;
	}
	
	@Override
	public void add(T element) {
		Node<T> newNode = new Node<T>(element);
		newNode.nextNode = last;
		lastElement.nextNode = newNode;
		lastElement = newNode;
		size++;
		currentOperations++;
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean remove(T element) {
		Node<T> node = first;
		for(int i = 0; i < size; i++) {
			if(node.nextNode.data == element) {
				delete(node);
				return true;
			}
			node = node.nextNode;
		}
		
		return false;
		
	}
	
	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	private void delete(Node<T> previousNode) {
		Node<T> nextNode = previousNode.nextNode.nextNode;
		previousNode.nextNode = nextNode;
		size--;
		currentOperations++;
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
		return new ListIterator<T>();
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
		return size;
	}

	private static class Node<T> {
		Node<T> nextNode = null;
		T data;

		public Node(T data) {
			this.data = data;
		}

	}

	private static class ListIterator<T> implements Iterator<T> {
		MyALDAList<T> list = new MyALDAList<T>();

		int operations = list.currentOperations;
		Node<T> currentNode, previousNode;
		int index = 0;

		@Override
		public boolean hasNext() {
			return currentNode.nextNode != null;
		}

		@Override
		public T next() {
			if (hasNext())
				if (operations == list.currentOperations) {
					previousNode = currentNode;
					currentNode = currentNode.nextNode;
					index++;
					return currentNode.data;
				} else
					throw new ConcurrentModificationException();
			throw new NoSuchElementException();
		}

	}
}
