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
		Node<T> node = first;
		for (int i = -1; i < size; i++, node = node.nextNode) {
			if (i == index - 1) {
				Node<T> newNode = new Node<T>(element);
				newNode.nextNode = node.nextNode;
				node.nextNode = newNode;
				currentOperations++;
				size++;
				return;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public boolean remove(T element) {
		Node<T> node = first;
		for (int i = 0; i < size; i++) {
			if (node.nextNode.data.equals(element)) {
				delete(node);
				return true;
			}
			node = node.nextNode;
		}
		return false;
	}

	@Override
	public T remove(int index) {
		Node<T> node = first;
		for (int i = 0; i < size; i++,node = node.nextNode) {
			if (i == index){
				Node<T> removedNode = node.nextNode;
				delete(node);
				return removedNode.data;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	private void delete(Node<T> previousNode) {
		Node<T> nextNode = previousNode.nextNode.nextNode;
		previousNode.nextNode = nextNode;
		size--;
		currentOperations++;
	}

	@Override
	public boolean contains(T element) {
		Node<T> node = first;
		for (int i = 0; i < size; i++) {
			node = node.nextNode;
			if(node.data != null && node.data.equals(element))
				return true;
		}
		return false;
	}

	@Override
	public int indexOf(T element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator iterator() {
		return new ListIterator<T>(this);
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
		MyALDAList<T> list;

		int operations;
		Node<T> currentNode, previousNode;
		int index = 0;

		public ListIterator(MyALDAList<T> list) {
			this.list = list;
			operations = list.currentOperations;
			currentNode = list.first;
		}

		@Override
		public boolean hasNext() {
			checkMod();
			return currentNode.nextNode != null;
		}

		@Override
		public T next() {
			if (hasNext()) {
				checkMod();
				previousNode = currentNode;
				currentNode = currentNode.nextNode;
				index++;
				return currentNode.data;
			}
			throw new NoSuchElementException();
		}

		private void checkMod() {
			if (operations != list.currentOperations)
				throw new ConcurrentModificationException();
		}

	}
}
