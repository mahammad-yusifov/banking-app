package az.azercell.bankingapp.service;

import az.azercell.bankingapp.exception.ErrorCode;
import az.azercell.bankingapp.exception.UserAlreadyExistsException;
import az.azercell.bankingapp.exception.UserBlockedException;
import az.azercell.bankingapp.model.request.AuthenticationRequest;
import az.azercell.bankingapp.model.request.UserRegisterRequest;
import az.azercell.bankingapp.model.response.AuthenticationResponse;
import az.azercell.bankingapp.repository.dao.UserDao;
import az.azercell.bankingapp.repository.UserRepository;
import az.azercell.bankingapp.util.PhoneNumberCheckerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BigDecimal defaultBalance = BigDecimal.valueOf(100.0);

    public AuthenticationResponse registerUser(UserRegisterRequest userRegisterRequest) {

        if (repository.findByGsmNumber(userRegisterRequest.getGsmNumber()).isPresent()) {
            throw new UserAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS, "Sistemdə artıq bu nömrə ilə" +
                    " istifadəçi var");
        }

        UserDao.UserDaoBuilder userDaoBuilder = UserDao.builder()
                .name(userRegisterRequest.getName())
                .surname(userRegisterRequest.getSurname())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .balance(defaultBalance)
                .gsmNumber(userRegisterRequest.getGsmNumber());

        LocalDate birthDate = userRegisterRequest.getBirthDate();
        if (birthDate != null) {
            userDaoBuilder.birthDate(Date.valueOf(birthDate));
        } else {
            userDaoBuilder.birthDate(null);
        }

        UserDao user = userDaoBuilder.build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .userId(user.getId())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest userRegisterRequest) {
        String login = userRegisterRequest.getLogin();

        UserDao user;
        if (PhoneNumberCheckerUtil.isAzerbaijaniPhoneNumber(login)) {
            user = repository.findByGsmNumber(login).orElseThrow(() -> new BadCredentialsException(null));
        } else {
            throw new BadCredentialsException(null);
        }

        if (user.isAccountBlocked()) {
            throw new UserBlockedException(ErrorCode.USER_BLOCK, "Hesabınız bloklanmışdır. " +
                    "Zəhmət olmasa bizimlə əlaqə saxlayın.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        userRegisterRequest.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .userId(user.getId())
                .token(jwtToken)
                .build();
    }
}
