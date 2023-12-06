package az.azercell.bankingapp.model.request;

import az.azercell.bankingapp.util.SqlDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private String name;
    private String surname;
    @JsonDeserialize(using = SqlDateDeserializer.class)
    private LocalDate birthDate;
    private String password;
    private String gsmNumber;
}
