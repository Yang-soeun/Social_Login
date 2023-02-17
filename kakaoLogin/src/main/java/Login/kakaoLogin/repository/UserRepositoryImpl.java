package Login.kakaoLogin.repository;

import Login.kakaoLogin.domain.QUser;
import Login.kakaoLogin.domain.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private EntityManager em;
    private JPAQueryFactory query;

    public UserRepositoryImpl(EntityManager em){
        query = new JPAQueryFactory(em);
    }

    @Override
    public User findByUseremail(String email) {
        User user = query.select(QUser.user)
                .from(QUser.user)
                .where(sameEmail(email))
                .fetchFirst();

        return user;
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        User user = query.select(QUser.user)
                .from(QUser.user)
                .where(sameId(userId))
                .fetchFirst();

        return Optional.ofNullable(user);
    }

    @Override
    public User findUserById(Long userId) {
        User user = query.select(QUser.user)
                .from(QUser.user)
                .where(sameId(userId))
                .fetchFirst();

        return user;
    }

    @Override
    public User findByNickname(String username) {
        User user = query.select(QUser.user)
                .from(QUser.user)
                .where(sameName(username))
                .fetchFirst();

        return user;
    }


    @Override
    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    private BooleanExpression sameEmail(String email){
        return (email == null) ? null : QUser.user.email.eq(email);
    }

    private BooleanExpression sameId(Long userId){
        return (userId == null) ? null : QUser.user.user_id.eq(userId);
    }

    private BooleanExpression sameName(String nickname){
        return (nickname == null) ? null : QUser.user.nickname.eq(nickname);
    }
}
