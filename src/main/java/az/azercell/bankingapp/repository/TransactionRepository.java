package az.azercell.bankingapp.repository;

import az.azercell.bankingapp.model.constant.TransactionType;
import az.azercell.bankingapp.repository.dao.TransactionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<TransactionDao, Integer> {

//    @Transactional(propagation = Propagation.REQUIRED)
//    @Query(value = "INSERT INTO TRANSACTION (type, balance_before, balance_after, amount) " +
//            "VALUES (:type, :balanceBefore, :balanceAfter, :amount)", nativeQuery = true)
//    void saveTopUpTransaction(@Param("type") TransactionType type,
//                             @Param("balanceBefore") BigDecimal balanceBefore,
//                             @Param("balanceAfter") BigDecimal balanceAfter,
//                             @Param("amount") BigDecimal amount);

}
