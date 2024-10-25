package work.vietdefi.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spring_user") // Tên bảng trong cơ sở dữ liệu
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;
    private String password;
    private String token;
    private Long tokenExpired;
}
