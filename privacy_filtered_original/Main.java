
import java.util.*;

class Order {
    String orderId;
    String customerName;
    String orderDetails;
    String status;
    boolean isPriority;
    Order next;

    public Order(String orderId, String customerName, String orderDetails, boolean isPriority) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDetails = orderDetails;
        this.status = "Pending";
        this.isPriority = isPriority;
        this.next = null;
    }
}

class OrderQueue {
    private Order priorityFront, priorityRear;
    private Order regularFront, regularRear;
    private Order completedFront, completedRear;

    private Map<String, Integer> inventory;

    public OrderQueue() {
        priorityFront = priorityRear = null;
        regularFront = regularRear = null;
        completedFront = completedRear = null;

        inventory = new HashMap<>();
        inventory.put("Phone", 5);
        inventory.put("Charger", 5);
        inventory.put("Laptop", 2);
    }

    private boolean isInStock(String orderDetails) {
        String[] items = orderDetails.split(",");

        for (String item : items) {
            item = item.trim();
            String[] parts = item.split("x");
            if (parts.length != 2) return false;

            try {
                int quantity = Integer.parseInt(parts[0].trim());
                String product = parts[1].trim();

                if (!inventory.containsKey(product) || inventory.get(product) < quantity)
                    return false;

            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private void updateInventory(String orderDetails) {
        String[] items = orderDetails.split(",");
        for (String item : items) {
            item = item.trim();
            String[] parts = item.split("x");

            int quantity = Integer.parseInt(parts[0].trim());
            String product = parts[1].trim();

            inventory.put(product, inventory.get(product) - quantity);
        }
    }

    public void addOrder(String orderId, String customerName, String orderDetails, boolean isPriority) {
        if (!isInStock(orderDetails)) {
            System.out.println("Cannot add order " + orderId + ". Not enough stock or invalid details.");
            return;
        }

        Order newOrder = new Order(orderId, customerName, orderDetails, isPriority);

        if (isPriority) {
            if (priorityRear == null) priorityFront = priorityRear = newOrder;
            else {
                priorityRear.next = newOrder;
                priorityRear = newOrder;
            }
        } else {
            if (regularRear == null) regularFront = regularRear = newOrder;
            else {
                regularRear.next = newOrder;
                regularRear = newOrder;
            }
        }

        updateInventory(orderDetails);
        System.out.println("Order added: " + orderId + (isPriority ? " (Priority)" : ""));
    }

    public void fulfillOrder() {
        Order fulfilled = null;

        if (priorityFront != null) {
            fulfilled = priorityFront;
            priorityFront = priorityFront.next;
            if (priorityFront == null) priorityRear = null;

            System.out.println("Fulfilled priority order: " + fulfilled.orderId);
        }
        else if (regularFront != null) {
            fulfilled = regularFront;
            regularFront = regularFront.next;
            if (regularFront == null) regularRear = null;

            System.out.println("Fulfilled regular order: " + fulfilled.orderId);
        }
        else {
            System.out.println("No orders to fulfill right now.");
            return;
        }

        fulfilled.status = "Fulfilled";
        fulfilled.next = null;

        if (completedRear == null) completedFront = completedRear = fulfilled;
        else {
            completedRear.next = fulfilled;
            completedRear = fulfilled;
        }
    }

    public void cancelOrder(String orderId) {
        Order prev = null, current = priorityFront;

        // Search priority queue
        while (current != null) {
            if (current.orderId.equals(orderId)) {
                if (prev == null) priorityFront = current.next;
                else prev.next = current.next;

                if (current == priorityRear) priorityRear = prev;

                System.out.println("Cancelled priority order: " + orderId);
                return;
            }
            prev = current;
            current = current.next;
        }

        // Search regular queue
        prev = null;
        current = regularFront;

        while (current != null) {
            if (current.orderId.equals(orderId)) {
                if (prev == null) regularFront = current.next;
                else prev.next = current.next;

                if (current == regularRear) regularRear = prev;

                System.out.println("Cancelled regular order: " + orderId);
                return;
            }
            prev = current;
            current = current.next;
        }

        System.out.println("Order not found: " + orderId);
    }

    public void displayOrders() {
        if (priorityFront == null && regularFront == null) {
            System.out.println("No orders in the queue.");
            return;
        }

        System.out.println("\nPriority Orders:");
        Order current = priorityFront;
        while (current != null) {
            System.out.println("Order ID: " + current.orderId + ", Name: " +
                    current.customerName + ", Status: " + current.status);
            current = current.next;
        }

        System.out.println("\nRegular Orders:");
        current = regularFront;
        while (current != null) {
            System.out.println("Order ID: " + current.orderId + ", Name: " +
                    current.customerName + ", Status: " + current.status);
            current = current.next;
        }
    }

    public void displayCompletedOrders() {
        if (completedFront == null) {
            System.out.println("No completed orders.");
            return;
        }

        System.out.println("\nCompleted Orders:");
        Order current = completedFront;
        while (current != null) {
            System.out.println("Order ID: " + current.orderId + ", Name: " +
                    current.customerName + ", Status: " + current.status);
            current = current.next;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        OrderQueue queue = new OrderQueue();

        queue.addOrder("001", "Customer A", "2x Phone, 1x Charger", true);
        queue.addOrder("002", "Customer B", "3x Laptop", false);
        queue.addOrder("003", "Customer A", "1x Laptop", true);
        queue.addOrder("004", "Customer C", "10x Phone", false);

        queue.displayOrders();

        queue.fulfillOrder();
        queue.fulfillOrder();
        queue.fulfillOrder();

        queue.cancelOrder("999");
        queue.cancelOrder("002");

        queue.displayOrders();
        queue.displayCompletedOrders();
    }
}
