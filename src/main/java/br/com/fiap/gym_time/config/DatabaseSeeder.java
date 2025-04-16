package br.com.fiap.gym_time.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.gym_time.models.Post;
import br.com.fiap.gym_time.repository.PostRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {
    
    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {
        var posts = List.of(
            Post.builder().account("anônimo").content("Hoje o treininho rendeu.").likes(0L).comments(0L).shares(0L).build(),
            Post.builder().account("anônimo2").content("Amanhã é costas. :/").likes(0L).comments(0L).shares(0L).build()
        );

        postRepository.saveAll(posts);
    }
}
