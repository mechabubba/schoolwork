/**
 * An enhanced implementation of a (doubly) linked list class.
 * @param <T> A generic class.
 */
public class EnhancedLinkedList<T> {
    /**
     * A private Node class used as the linkages for our linked list.
     * @param <T> A generic class.
     */
    private static class Node<T> {
        public T data;
        public Node prev;
        public Node next;

        /**
         * Constructs a Node from data.
         * @param data The data to place inside the node.
         */
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node back;
    private int size;

    /**
     * Constructs an empty linked list.
     */
    public EnhancedLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    /**
     * Inserts a piece of data at the front (index 0) of the list.
     * @param data Data to be inserted.
     */
    public void insertAtFront(T data) {
        Node node = new Node(data);
        if(size == 0) {
            this.front = node;
            this.back = node;
        } else {
            this.front.prev = node;
            node.next = this.front;
            this.front = node;
        }
        size++;
    }

    /**
     * Inserts a piece of data at the back (index size - 1) of the list.
     * @param data Data to be inserted.
     */
    public void insertAtBack(T data) {
        Node node = new Node(data);
        if(size == 0) {
            this.front = node;
            this.back = node;
        } else {
            this.back.next = node;
            node.prev = this.back;
            this.back = node;
        }
        size++;
    }

    /**
     * Removes a piece of data from the front (index 0) of the list.
     */
    public void removeFromFront() throws ArrayIndexOutOfBoundsException {
        if(size == 0) throw new ArrayIndexOutOfBoundsException("Cannot remove element from empty list.");
        if((size - 1) == 0) {
            this.back = null;
        }
        this.front = this.front.next;
        size--;
    }

    /**
     * Removes a piece of data from the back (index size - 1) of the list.
     */
    public void removeFromBack() throws ArrayIndexOutOfBoundsException {
        if(size == 0) throw new ArrayIndexOutOfBoundsException("Cannot remove element from empty list.");
        if((size - 1) == 0) {
            this.front = null;
        }
        this.back = this.back.prev;
        size--;
    }

    /**
     * Gets a piece of data from the list.
     * @param index The index to get data from.
     * @return The data at that index.
     * @throws ArrayIndexOutOfBoundsException Thrown if you attempt to access an index out of bounds of the list.
     */
    public T get(int index) throws ArrayIndexOutOfBoundsException {
        if(index >= this.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds.");
        }
        int i = 0;
        Node node = front;
        while(index != i) {
            node = node.next;
            i++;
        }
        return (T)node.data;
    }

    /**
     * Gets the size of the list.
     * @return The size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Gets a string representation of the list and its contents.
     * @return A string representation of the list.
     */
    public String toString() {
        StringBuilder message = new StringBuilder("[");
        for(int i = 0; i < this.size; i++) {
            message.append(this.get(i));
            if(i < (this.size - 1)) {
                message.append(", ");
            }
        }
        message.append("]");
        return message.toString();
    }
}
