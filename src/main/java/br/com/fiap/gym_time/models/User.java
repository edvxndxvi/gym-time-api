package br.com.fiap.gym_time.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 12, message = "O usuário deve ter no mínimo 4 caracteres e no máximo 12.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Use apenas letras, números e underline.")
    private String username;

    @NotNull
    @Size(min = 2, max = 15, message = "O nome deve ter no mínimo 2 caracteres e no máximo 15.")
    private String name;

    @Pattern(
        regexp = "^(https?|ftp)://.*\\.(jpg|jpeg|png|gif)$",
        message = "A imagem de perfil deve ser uma URL válida de imagem."
    )   
    private String profilePicURL;


    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
