package SpringPractice.user;

public interface UserService {
    void signUp(User user);
    User findUser(Long userId);
}
