package br.com.fiap.gym_time.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.gym_time.models.Account;
import br.com.fiap.gym_time.models.Post;
import br.com.fiap.gym_time.repository.AccountRepository;
import br.com.fiap.gym_time.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.experimental.var;

@Configuration
public class DatabaseSeeder {
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {

        var joaoAccount = Account.builder().name("Jota").username("jvtips").profilePicURL("https://github.com/joaosilvaz.png").build();
        var rafaAccount = Account.builder().name("Romanini").username("roma").profilePicURL("https://github.com/rafaelromanini.png").build();
        var edvanAccount = Account.builder().name("Ed").username("edvxndxvi").profilePicURL("https://github.com/edvxndxvi.png").build();

        accountRepository.saveAll(List.of(joaoAccount, rafaAccount, edvanAccount));


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

        List<Account> accounts = accountRepository.findAll();
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
                    .account(accounts.get(random.nextInt(accounts.size())))
                    .build()
            );
        }

        postRepository.saveAll(posts);
    }
}
