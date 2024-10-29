package work.vietdefi.spring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String refreshToken;
    private long refreshTokenExpired;
    private String accessToken;
    private long accessTokenExpired;
}
