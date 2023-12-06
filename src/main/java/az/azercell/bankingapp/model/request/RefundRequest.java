package az.azercell.bankingapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequest {

    private String gsmNumber;
    private Integer transactionId;
    private BigDecimal amount;
}
