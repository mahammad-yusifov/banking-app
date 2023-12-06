package az.azercell.bankingapp.config;

import az.azercell.bankingapp.exception.ErrorCode;
import az.azercell.bankingapp.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SignatureException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Throwable cause = authException.getCause();
        if (cause instanceof ExpiredJwtException || cause instanceof SignatureException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            String responseMsg = mapper.writeValueAsString(new ErrorResponse(ErrorCode.INVALID_TOKEN,
                    "Token is expired or invalid."));
            response.getWriter().write(responseMsg);
        }else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }
}