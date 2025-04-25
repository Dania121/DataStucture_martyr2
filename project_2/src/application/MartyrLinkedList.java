package application;

public class MartyrLinkedList {
	MartyrNode first, last;

	int count = 0;

	public MartyrLinkedList() {
		super();
	}

	public MartyrLinkedList(MartyrNode first) {
		super();
		this.first = first;
		this.count = 0;
	}

	// Find method to check if martyr exists
	public boolean find(String name) {
		MartyrNode current = first;
		while (current != null) {
			if (current.getMartyr().getName().equals(name)) {
				return true; // Martyr exists
			}
			current = current.next;
		}
		return false; // Martyr not found
	}

	public void addMartyr(Martyr martyr) {
		if (find(martyr.getName())) {
			// Martyr already exists.
			return;
		}

		MartyrNode newNode = new MartyrNode(martyr);

		// If the linked list is empty or the new martyr is younger or same age but with
		// the same or higher priority gender
		if (first == null) {
			first = last = newNode;
			count++;
			return;
		}
		if (martyr.getAge() < first.getMartyr().getAge() || (martyr.getAge() == first.getMartyr().getAge()
				&& martyr.getGender().compareTo(first.getMartyr().getGender()) <= 0)) {
			newNode.next = first;
			first = newNode;
			count++;
			return;
		}

		// Find the appropriate position to insert the new martyr
		MartyrNode current = first;
		while (current.next != null && (current.next.getMartyr().getAge() < martyr.getAge()
				|| (current.next.getMartyr().getAge() == martyr.getAge()
						&& current.next.getMartyr().getGender().compareTo(martyr.getGender()) <= 0))) {
			current = current.next;
		}

		// Insert the new martyr at the appropriate position
		if (current.next == null)
			last = newNode;
		newNode.next = current.next;
		current.next = newNode;
		count++;
	}

	public void print() {
		MartyrNode f = first;
		while (f != null) {
			System.out.println(f.getMartyr());
			System.out.println("___________________________________________________________________________");
			f = f.next;
		}
	}

	public int size() {
		int size = 0;
		MartyrNode current = first;
		while (current != null) {
			size++;
			current = current.next;
		}
		return size;
	}
	public void delete(Martyr martyr) {
	    MartyrNode current = first;
	    MartyrNode previous = null;

	    // Traverse the linked list to find the node containing the given martyr
	    while (current != null && !current.getMartyr().equals(martyr)) {
	        previous = current;
	        current = current.next;
	    }

	    // If the martyr is found, remove it from the linked list
	    if (current != null) {
	        // If the node to be deleted is the first node
	        if (current == first) {
	            first = first.next;
	            // If the deleted node was the only node in the list
	            if (first == null) {
	                last = null;
	            }
	        } else {
	            // If the node to be deleted is in the middle or end of the list
	            previous.next = current.next;
	            // If the deleted node was the last node
	            if (current == last) {
	                last = previous;
	            }
	        }
	        // Decrement the count of elements
	        count--;
	    }
	}
}
