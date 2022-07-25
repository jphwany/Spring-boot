package SpringPractice;

import SpringPractice.Order.OrderService;
import SpringPractice.Order.OrderServiceImpl;
import SpringPractice.discount.DiscountInfo;
import SpringPractice.discount.CurrentDiscountInfo;
import SpringPractice.discount.RateDiscountInfo;
import SpringPractice.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AppConfig {
//    public UserService userService(){
//        return new UserServiceImpl(new UserRepositoryImpl());
//    }
//    public OrderService orderService() {
//        return new OrderServiceImpl(new UserRepositoryImpl(), new CurrentDiscountInfo());
//    }
//}
    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
}
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(userRepository(), discountInfo());
    }
    @Bean
    public DiscountInfo discountInfo() {
//      return new CurrentDiscountInfo();
        return new RateDiscountInfo();
    }
}

