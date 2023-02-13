package Login.kakaoLogin.repository.refresh;

import Login.kakaoLogin.domain.QRefreshToken;
import Login.kakaoLogin.domain.QUser;
import Login.kakaoLogin.domain.RefreshToken;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{

    @Autowired
    private EntityManager em;
    private JPAQueryFactory query;

    public RefreshTokenRepositoryImpl(EntityManager em){
        query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<RefreshToken> find(Long userId) {
        RefreshToken refreshToken = query.select(QRefreshToken.refreshToken1)
                .from(QRefreshToken.refreshToken1)
                .where(sameId(userId))
                .fetchFirst();

        return Optional.ofNullable(refreshToken);//null을 가지고 있는 Optional을 생성가능능
    }
    @Override
    public void save(RefreshToken refreshToken) {
        em.persist(refreshToken);
    }

    @Override
    public void delete(Long userId) {
        query.delete(QRefreshToken.refreshToken1)
                .where(sameId(userId))
                .execute();
    }

    private BooleanExpression sameId(Long userId){
        return (userId == null)?null: QUser.user.user_id.eq(userId);
    }
}
