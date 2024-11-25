package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        assertThatThrownBy(service::callThrow).isInstanceOf(MyUncheckedException.class);
    }

    /*
    RuntimeException 상속받은 예외 -> 언체크 예외
     */
    private static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /*
    unChecked 예외는 예외를 잡거나, 던지지 않아도 됨
    예외를 잡지 않으면 자동으로 밖으로 던짐
     */
    private static class Service {
        Repository repository = new Repository();

        /*
        필요한 경우 예외 잡아서 처리
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                //예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /*
        예외 잡지 않아도 됨. 자연스럽게 상위로 넘어감
        체크 예외와 다르게 throws 예외 선언을 하지 않아도 됨
         */
        public void callThrow() {
            repository.call();
        }
    }

    private static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }

}
