package br.com.fiap.gym_time.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.gym_time.models.Post;
import br.com.fiap.gym_time.models.User;
import br.com.fiap.gym_time.models.UserRole;
import br.com.fiap.gym_time.repository.PostRepository;
import br.com.fiap.gym_time.repository.UserRepository;
import jakarta.annotation.PostConstruct;


@Configuration
public class DatabaseSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {

        var joaoAccount = User.builder().name("Jota").username("jvtips").profilePicURL("https://github.com/joaosilvaz.png").password("12443").role(UserRole.USER).build();
        var rafaAccount = User.builder().name("Romanini").username("roma").profilePicURL("https://github.com/rafaelromanini.png").password("12443").role(UserRole.USER).build();
        var edvanAccount = User.builder().name("Ed").username("edvxndxvi").profilePicURL("https://github.com/edvxndxvi.png").password("12443").role(UserRole.USER).build();

        userRepository.saveAll(List.of(joaoAccount, rafaAccount, edvanAccount));


        List<String> contents = List.of(
            "Hoje o treino foi insano!",
            "Perna hoje, bora crescer!",
            "Descanso merecido 😴",
            "Cardio pesado agora cedo.",
            "Back day 💪",
            "O pump veio!",
            "Sem dor, sem ganho!",
            "Tô morto, mas feliz!",
            "Novo PR no supino!",
            "Foco, força e fé no treino!"
        );

        List<User> users = userRepository.findAll();
        var random = new Random();
        var posts = new ArrayList<Post>();

        for (int i = 0; i < 50; i++) {
            posts.add(
                Post.builder()
                    .content(contents.get(random.nextInt(contents.size())))
                    .date(LocalDate.now().minusDays(random.nextInt(30)))
                    .likes((long) random.nextInt(100))
                    .comments((long) random.nextInt(50))
                    .shares((long) random.nextInt(30))
                    .user(users.get(random.nextInt(users.size())))
                    .build()
            );
        }

        postRepository.saveAll(posts);
    }
}
