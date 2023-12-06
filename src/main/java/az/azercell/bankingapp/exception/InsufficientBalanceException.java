package az.azercell.bankingapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InsufficientBalanceException extends RuntimeException {

    private final String code;

    private final String message;

    public InsufficientBalanceException(String code, String message) {
        super(code);
        this.code = code;
        this.message = message;
    }
}
