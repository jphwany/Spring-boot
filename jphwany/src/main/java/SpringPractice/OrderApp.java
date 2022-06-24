package SpringPractice;

import SpringPractice.discount.CurrentDiscountInfo; // 할인 정책1
import SpringPractice.discount.RateDiscountInfo; // 할인 정책2
import SpringPractice.user.User;
import SpringPractice.user.UserGrade;
import SpringPractice.user.UserService;
import SpringPractice.Order.Order;
import SpringPractice.Order.OrderService;

public class OrderApp {
    public static void main(String[] args) {

////      CurrentDiscountInfo discountInfo = new CurrentDiscountInfo();
////      -> 할인 정책 1
//        RateDiscountInfo discountInfo = new RateDiscountInfo();
////      -> 할인 정책 2
//        User user = new User(0L, "jphwany", UserGrade.GRADE_1);
//        int discountedPrice = discountInfo.discount(user, 10000);
//
////        if(discountedPrice == 3000) {
////            System.out.println("[Notice] 회원 등급에 따른 할인");
////            System.out.println("[Inform 1등급 회원] : " + discountedPrice + "원 할인 적용");
////        }
//
//        if(discountedPrice == 2000) { // 할인정책2 적용 10000 * 0.2 = 2000
//            System.out.println("[Notice] 회원 등급에 따른 할인");
//            System.out.println("[Inform 1등급 회원] : " + discountedPrice + "원 할인 적용");
//        }
//
//        System.out.println();
//
//        user = new User(0L, "jamesPark", UserGrade.GRADE_2);
//        discountedPrice = discountInfo.discount(user, 10000);
//
////        if(discountedPrice == 1500) {
////            System.out.println("[Notice] 회원 등급에 따른 할인 적용");
////            System.out.println("[Inform 2등급 회원] : " + discountedPrice + "원 할인 적용");
////        }
//
//        if(discountedPrice == 1000) { // 할인정책2 적용 10000 * 0.1 = 1000
//            System.out.println("[Notice] 회원 등급에 따른 할인 적용");
//            System.out.println("[Inform 2등급 회원] : " + discountedPrice + "원 할인 적용");
//        }
        AppConfig appConfig = new AppConfig();
        UserService userService = appConfig.userService();
        OrderService orderService = appConfig.orderService();

        Long userId = 0L;
        User user = new User(userId, "jphwany", UserGrade.GRADE_1);
        userService.signUp(user);

        Order order = orderService.createOrder(userId, "coldbrew", 5000);

        System.out.println("order = " + order);


    }
}
