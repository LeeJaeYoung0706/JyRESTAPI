package rest.JyRESTAPI.utils.https;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Slf4j
public class CustomTransactionTemplate extends TransactionTemplate {

    @Autowired
    public CustomTransactionTemplate(PlatformTransactionManager transactionManager) {
        super(transactionManager);
        //트랜잭션의 기본타입은 PROPAGATION_REQUIRES_NEW 이나 다른방식을 사용하고 싶을때는 아래 propagation호출해서 사용.
        //this.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    //트랜잭션 타입을 외부에서도 설정할수 있도록 추가구성.
    public CustomTransactionTemplate propagation(int propagationBehavior) {
        this.setPropagationBehavior(propagationBehavior);
        return this;
    }

    //트랜잭션의 최대 타임아웃 설정. 데드락방지용.
    public CustomTransactionTemplate timeout(int timeoutInSeconds) {
        this.setTimeout(timeoutInSeconds);
        return this;
    }

    //읽기전용 트랜잭션 설정여부 flush 방지용
    public CustomTransactionTemplate readOnly(boolean readOnly) {
        this.setReadOnly(readOnly);
        return this;
    }

    @Override
    @Nullable
    public <T> T execute(TransactionCallback<T> action) throws TransactionException {
        try {
            return super.execute(action);
        } catch (Error | Exception ex) {
            log.error("[트랜잭션 처리중 에러발생] {}", safeMessage(ex), ex);
            throw new CustomResponseException("오류 내역 : " + safeMessage(ex), 500);
        }
    }

    //메세지 리턴시 널처리방지.
    private String safeMessage(Throwable e) {
        return (e.getMessage() != null) ? e.getMessage() : e.getClass().getSimpleName();
    }
}