package az.azercell.bankingapp.repository;

import az.azercell.bankingapp.repository.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDao, Integer> {

    Optional<UserDao> findByGsmNumber(String gsmNumber);

//    @Transactional(propagation = Propagation.REQUIRED)
//    @Query(value = "UPDATE customer SET balance = balance + :amount WHERE id = :userId", nativeQuery = true)
//    void topUpBalance(@Param("userId") Integer userId, @Param("amount") BigDecimal amount);

}
