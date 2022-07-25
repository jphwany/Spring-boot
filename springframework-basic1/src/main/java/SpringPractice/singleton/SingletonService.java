package SpringPractice.singleton;

public class SingletonService {

//  static 영역에 객체 1개 생성
    private static final SingletonService instance = new SingletonService();


//  객체 인스턴스가 필요할 때 public static 메소드(getInstance())를 통해서만 조회할 수 있게 만든다
    public static SingletonService getInstance(){
        return instance;
    }


// 생성자를 private 제어자로 선언, 외부에서 new를 사용한 객체 생성을 제한
    private SingletonService(){
    }

    public void logic(){
        System.out.println("싱글톤 객체 호출");
    }
}
