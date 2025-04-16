package br.com.fiap.gym_time.models;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    // Attributes
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String account;
    private String urlImage;

    @NotBlank(message = "O conteúdo não pode ser vazio")
    private String content;

    @PastOrPresent(message = "A data não pode ser futura")
    private LocalDate date;

    private Long likes;
    private Long comments;
    private Long shares;
}
