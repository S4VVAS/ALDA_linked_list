//Savvas Giortsis (Sagi2536) savvasswe@gmail.com

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<T> implements ALDAList<T> {

	private final Node<T> first = new Node<T>(null); // Starting sentinel node.
	private final Node<T> last = new Node<T>(null); // Ending sentinel node.
	private Node<T> lastElement = first;
	private int currentOperations = 0;
	private int size = 0;

	public MyALDAList() {
		first.nextNode = last;
	}

	/** Adds an element into the end of list. */
	@Override
	public void add(T element) {
		Node<T> newNode = new Node<T>(element);
		newNode.nextNode = last;
		lastElement.nextNode = newNode;
		lastElement = newNode;
		size++;
		currentOperations++;
	}

	/**
	 * Adds an element into the specified index of the list, pushing elements above
	 * that index up. Can cast IndexOutOfBoundsException() if the index does not
	 * exist in list (bellow 0 or above max index)
	 */
	@Override
	public void add(int index, T element) {
		Node<T> node = first;
		for (int i = -1; i < size; i++, node = node.nextNode) {
			if (i == index - 1) {
				Node<T> newNode = new Node<T>(element);
				newNode.nextNode = node.nextNode;
				node.nextNode = newNode;
				if (i == size - 1)
					lastElement = newNode;
				currentOperations++;
				size++;
				return;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	/**
	 * Finds and removes the specified element in the list, on successful operation,
	 * true is returned. Else if that element does not exist, false is returned
	 */
	@Override
	public boolean remove(T element) {
		Node<T> node = first;
		for (int i = -1; i < size - 1; i++, node = node.nextNode) {
			if (node.nextNode.data.equals(element)) {
				deleteFromPrevious(node);
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the element on the specified index and returns that removed element
	 * to the caller. Can cast IndexOutOfBoundsException() if the index does not
	 * exist in list (bellow 0 or above max index)
	 */
	@Override
	public T remove(int index) {
		Node<T> node = first;
		for (int i = -1; i < size - 1; i++, node = node.nextNode) {
			if (i == index - 1) {
				Node<T> removedNode = node.nextNode;
				deleteFromPrevious(node);
				return removedNode.data;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	// Deletes node from previous, avoids reiteration of the list in order to find
	// the previous element.
	private void deleteFromPrevious(Node<T> previousNode) {
		if (previousNode.nextNode == lastElement)
			lastElement = previousNode;
		Node<T> nextNode = previousNode.nextNode.nextNode;
		previousNode.nextNode = nextNode;
		size--;
		currentOperations++;
	}

	/**
	 * Returns: true or false based on if the specified element exists in the list
	 * or not.
	 */
	@Override
	public boolean contains(T element) {
		return indexOf(element) != -1;
	}

	/**
	 * Returns: the index in the list where the specified element is. Returns: -1 if
	 * there is no instances of that element in list.
	 */
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

	/** Returns: a new iterator for this list. */
	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(this);
	}

	/**
	 * Returns: the value of a certain index in this list. Can cast
	 * IndexOutOfBoundsException() if the index does not exist in list (bellow 0 or
	 * above max index)
	 */
	@Override
	public T get(int index) {
		Node<T> node = first.nextNode;
		for (int i = 0; i < size; i++, node = node.nextNode) {
			if (i == index)
				return node.data;
		}
		throw new IndexOutOfBoundsException();
	}

	/** Clears all elements in list (Deletes everything). */
	@Override
	public void clear() {
		first.nextNode = last;
		lastElement = first;
		size = 0;
		currentOperations++;
	}

	/** Returns: the number of elements in the list */
	@Override
	public int size() {
		return size;
	}

	/** Returns: a string representation of the list. */
	@Override
	public String toString() {
		String str = "[";
		Iterator<T> it = this.iterator();
		boolean firstElm = true;
		while (it.hasNext())
			if (firstElm) {
				str = str + it.next();
				firstElm = false;
			} else
				str = str + ", " + it.next();
		str = str + "]";
		return str;
	}

	// ____INNER CLASSES BELLOW_______

	private static class Node<T> {
		protected Node<T> nextNode = null;
		protected T data;

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
