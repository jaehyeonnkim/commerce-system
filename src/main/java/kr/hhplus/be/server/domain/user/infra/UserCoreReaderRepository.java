package kr.hhplus.be.server.domain.user.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreReaderRepository {

    @PersistenceContext
    private final EntityManager em;

    //Id로 단건 조회
    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
