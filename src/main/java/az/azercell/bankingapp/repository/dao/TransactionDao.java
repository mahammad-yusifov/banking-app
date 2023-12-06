package az.azercell.bankingapp.repository.dao;


import az.azercell.bankingapp.model.constant.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "balance_before")
    private BigDecimal balanceBefore;

    @Column(name = "balance_after")
    private BigDecimal balanceAfter;

    @Column(name = "amount")
    private BigDecimal amount;

}
