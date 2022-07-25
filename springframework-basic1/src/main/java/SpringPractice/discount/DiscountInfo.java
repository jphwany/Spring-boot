package SpringPractice.discount;

import SpringPractice.user.User;

public interface DiscountInfo {
    int discount(User user, int price);
}
