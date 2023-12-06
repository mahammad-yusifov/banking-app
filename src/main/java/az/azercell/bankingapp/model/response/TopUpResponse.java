package az.azercell.bankingapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopUpResponse {

    private Integer customerId;
    private BigDecimal balance;
}
