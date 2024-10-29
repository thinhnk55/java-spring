package work.vietdefi.spring.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spring_user") // Table name in database
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(unique = true, nullable = false, length = 64)
    private String username;
    @Column(length = 512)
    private String password;
    @Column(length = 512)
    private String refreshToken;
    private Long refreshTokenExpired;
}
