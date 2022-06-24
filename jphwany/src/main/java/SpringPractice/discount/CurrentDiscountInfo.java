package SpringPractice.discount;

import SpringPractice.user.User;
import SpringPractice.user.UserGrade;

public class CurrentDiscountInfo implements DiscountInfo {

    // 고정 할인 값
    private int grade_1_Amount = 3000;
    private int grade_2_Amount = 1500;

    @Override
    public int discount(User user, int price) {
        if(user.getUserGrade() == UserGrade.GRADE_1) {
            // enum 타입 비교는 == 사용한다
            return grade_1_Amount;
        } else if(user.getUserGrade() == UserGrade.GRADE_2) {
            return grade_2_Amount;
        }
        return 0;
    }
}
