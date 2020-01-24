import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<T> implements ALDAList<T> {

	private final Node<T> first = new Node<T>(null);
	private final Node<T> last = new Node<T>(null);
	private Node<T> lastElement = first;
	private int currentOperations = 0;
	private int size = 0;

	public MyALDAList() {
		first.nextNode = last;
	}

	/** Adds a specified element of a type, into the linked list of that type*/
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
				if(i == size-1)
					lastElement = newNode;
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
		for (int i = -1; i < size-1; i++, node = node.nextNode) {
			if (node.nextNode.data.equals(element)) {
				delete(node);
				return true;
			}
		}
		return false;
	}

	@Override
	public T remove(int index) {
		Node<T> node = first;
		for (int i = -1; i < size-1; i++, node = node.nextNode) {
			if (i == index-1) {
				Node<T> removedNode = node.nextNode;
				delete(node);
				return removedNode.data;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	private void delete(Node<T> previousNode) {
		if(previousNode.nextNode == lastElement) 
			lastElement = previousNode;
		
		Node<T> nextNode = previousNode.nextNode.nextNode;
		previousNode.nextNode = nextNode;
		size--;
		currentOperations++;
	}

	@Override
	public boolean contains(T element) {
		return indexOf(element) != -1;
	}

	@Override
	public int indexOf(T element) {
		Node<T> node = first;
		for (int i = 0; i < size; i++) {
			node = node.nextNode;
			if (node.data != null && node.data.equals(element))
				return i;
		}
		return -1;
	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(this);
	}

	@Override
	public T get(int index) {
		Node<T> node = first.nextNode;
		for (int i = 0; i < size; i++, node = node.nextNode) {
			if (i == index)
				return node.data;
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void clear() {
		first.nextNode = last;
		lastElement = first;
		size = 0;
		currentOperations++;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		String str = "[";
		Iterator<T> it = this.iterator();
		boolean firstElm = true;
		while(it.hasNext())
			if (firstElm) {
				str = str + it.next();
				firstElm = false;
			}
			else
				str = str + ", " + it.next();
		str = str + "]";
		return str;
	}

	// ____INNER CLASSES BELLOW_______

	private static class Node<T> {
		Node<T> nextNode = null;
		T data;

		public Node(T data) {
			this.data = data;
		}

	}

	private static class ListIterator<T> implements Iterator<T> {
		private MyALDAList<T> list;

		private int operations;
		private Node<T> currentNode, previousNode;
		private int index = 0;

		public ListIterator(MyALDAList<T> list) {
			this.list = list;
			operations = list.currentOperations;
			currentNode = list.first;
			previousNode = currentNode;
		}

		@Override
		public boolean hasNext() {
			checkMod();
			return currentNode.nextNode.data != null;
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

		@Override
		public void remove() {
			if (currentNode != previousNode) {
				checkMod();
				previousNode = currentNode;
				list.remove(currentNode.data);
				operations++;
			} else
				throw new IllegalStateException();

		}

		private void checkMod() {
			if (operations != list.currentOperations)
				throw new ConcurrentModificationException();
		}

	}
}
