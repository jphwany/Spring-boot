package SpringPractice.discount;

import SpringPractice.user.User;
import SpringPractice.user.UserGrade;

public class RateDiscountInfo implements DiscountInfo {

    private int grade_1_Rate = 20;
    private int grade_2_Rate = 10;
    @Override
    public int discount(User user, int price) {
        if(user.getUserGrade() == UserGrade.GRADE_1) {
            return price * grade_1_Rate / 100;
        } else if(user.getUserGrade() == UserGrade.GRADE_2) {
            return price * grade_2_Rate / 100;
        }
        return 0;
    }
}