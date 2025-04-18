package br.com.fiap.gym_time.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.gym_time.models.Post;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post>{
    Page<Post> findByContentContainingIgnoringCase(String content, Pageable pageable);
}
