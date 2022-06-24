package SpringPractice.user;

public class UserServiceImpl implements UserService {

//    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserRepository userRepository;
    // new 연산자 사용하지 않고 아래 코드 처럼 생성자 사용
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public void signUp(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findByUserId(userId);
    }
}