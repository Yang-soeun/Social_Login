package google.Login.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import google.Login.domain.QUser;
import google.Login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    @Autowired
    private EntityManager em;
    private JPAQueryFactory query;

    public MemberRepositoryImpl(EntityManager em){
        query = new JPAQueryFactory(em);
    }

    @Override
    public User findByEmail(String email) {
        User user = query.select(QUser.user)
                .from(QUser.user)
                .where(sameEmail(email))
                .fetchFirst();

        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        em.persist(user);
    }

//    @Override
//    @Transactional
//    public void saveRole(UserRole userRole) {
//        em.persist(userRole);
//    }

    private BooleanExpression sameEmail(String email){
        return (email == null) ? null :QUser.user.email.eq(email);
    }
}
