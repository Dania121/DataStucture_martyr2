package application;

public class Queue {
	Node front;
	Node rear;
	LinkedList list;

	public Queue() {
		this.list = new LinkedList();
	}

	public Node getFront() {
		return front;
	}

	public void setFront(Node front) {
		this.front = front;
	}

	public Node getRear() {
		return rear;
	}

	public void setRear(Node rear) {
		this.rear = rear;
	}

	public LinkedList getList() {
		return list;
	}

	public void setList(LinkedList list) {
		this.list = list;
	}

	public void inQueue(LocationNode data) {
		list.addLast(data);
		front = list.head;
		rear = list.tail;
	}

	public Node deQueue() {
		if (isEmpty()) {
			System.out.println("Queue is empty");
			return null;
		} else {
			Node result = list.deleteFirst();
			front = list.head;
			return result;
		}
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void printQueue() {
		if (isEmpty()) {
			System.out.println("Queue is empty");
		} else {
			System.out.println("Queue contents:");
			Node current = front;
			while (current != null) {
				System.out.println(current.data);
				current = current.next;
			}
		}
	}
}
