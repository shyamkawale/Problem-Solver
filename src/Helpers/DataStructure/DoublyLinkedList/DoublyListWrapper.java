package Helpers.DataStructure.DoublyLinkedList;

public class DoublyListWrapper {
	public static int[] stringToIntegerArray(String input) {
		input = input.trim();
		input = input.substring(1, input.length() - 1);
		if (input.isEmpty()) {
			return new int[0];
		}

		String[] parts = input.split(",");
		int[] output = new int[parts.length];
		for (int index = 0; index < parts.length; index++) {
			String part = parts[index].trim();
			output[index] = Integer.parseInt(part);
		}
		return output;
	}

	public static ListNode stringToListNode(String input) {
		// Generate array from the input
		int[] nodeValues = stringToIntegerArray(input);

		// Now convert that list into linked list
		ListNode head = null;
		ListNode ptr = head;
		for (int item : nodeValues) {
			ListNode newNode = new ListNode(item);
			if (head == null) {
				head = newNode;
				ptr = head;
			} else {
				ptr.next = newNode;
				newNode.prev = ptr;
				ptr = newNode;
			}
		}
		return head;
	}

	public static void prettyPrintLinkedList(ListNode node) {
		if (!isValidLinkedList(node)) {
			System.out.println("Not Valid LinkedList");
		}

		while (node != null && node.next != null) {
			System.out.print(node.val + "<->");
			node = node.next;
		}

		if (node != null) {
			System.out.println(node.val);
		} else {
			System.out.println("Empty LinkedList");
		}
	}

	private static boolean isValidLinkedList(ListNode head) {
		if (head == null) {
			return true;
		}

		ListNode currNode = head;
		ListNode prevNode = head.prev;

		while (currNode != null) {
			if (currNode.prev != prevNode) {
				return false;
			}

			prevNode = currNode;
			currNode = currNode.next;
		}

		return true;
	}
}
