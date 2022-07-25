package SpringPractice;

import SpringPractice.user.User;
import SpringPractice.user.UserGrade;
import SpringPractice.user.UserService;
import SpringPractice.user.UserServiceImpl; // 비활성화

public class UserApp {

    public static void main(String[] args) {
//        UserService userService = new UserServiceImpl();
        AppConfig appConfig = new AppConfig();
        UserService userService = appConfig.userService();
        // new 연산자 버리고 AppConfig로 결정할 수 있게 코드 수정


        User user = new User(0L, "martin", UserGrade.GRADE_2);
        userService.signUp(user);

        User currentUser = userService.findUser(0L);
        System.out.println("signUp user : " + user.getName());
        System.out.println("current user : " + currentUser.getName());
        System.out.println();

        if(user.getName().equals(currentUser.getName())) {
            System.out.println("[System] 새로운 가입자가 현재 사용자와 같습니다.");
        }
    }
}