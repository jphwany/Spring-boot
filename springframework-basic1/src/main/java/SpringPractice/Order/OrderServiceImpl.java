package SpringPractice.Order;

import SpringPractice.discount.CurrentDiscountInfo; // 할인정책1
import SpringPractice.discount.RateDiscountInfo; // 할인정책2
import SpringPractice.discount.DiscountInfo;
import SpringPractice.user.User;
import SpringPractice.user.UserRepository;
import SpringPractice.user.UserRepositoryImpl; // new 연산자 사용안함


public class OrderServiceImpl implements OrderService {

//    private final UserRepository userRepository = new UserRepositoryImpl();

//  private final DiscountInfo discountInfo = new CurrentDiscountInfo();
//  -> 할인정책1
//    private final DiscountInfo discountInfo = new RateDiscountInfo();
//  -> 할인정책2
    private final UserRepository userRepository;
    private final DiscountInfo discountInfo;

    public OrderServiceImpl(UserRepository userRepository, DiscountInfo discountInfo) {
        this.userRepository = userRepository;
        this.discountInfo = discountInfo;
    }
    // 생성자 사용
    @Override
    public Order createOrder(Long userId, String itemName, int itemPrice) {
        User user = userRepository.findByUserId(userId);
        int discountPrice = discountInfo.discount(user, itemPrice);

        return new Order(userId, itemName, itemPrice, discountPrice);
    }
}
