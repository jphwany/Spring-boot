package SpringPractice.Order;

public interface OrderService {
    Order createOrder(Long userId, String itemName, int itemPrice);
}
