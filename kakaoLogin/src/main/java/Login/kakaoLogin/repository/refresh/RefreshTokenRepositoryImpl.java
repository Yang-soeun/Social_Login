package Login.kakaoLogin.repository.refresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RefreshTokenRepositoryImpl extends RefreshTokenRepository{

    @Autowired
    private EntityManager em;

    public RefreshTokenRepositoryImpl(EntityManager em){

    }
}
