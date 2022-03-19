package com.devconnor.crud.controller;

import com.devconnor.crud.domain.Post;
import com.devconnor.crud.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // 글목록
    @GetMapping
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    // 글상세
    @GetMapping("/{id}")
    public EntityModel<Post> getPost(@PathVariable int id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {

            // HATEOAS
            EntityModel<Post> model = new EntityModel<>(post.get());
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getPosts());
            model.add(linkTo.withRel("all-posts"));
            return model;
        }
        return null;
    }

    // 글삭제
    @DeleteMapping("/{id}")
    private void deletePost(@PathVariable int id) {
        postRepository.deleteById(id);
    }

    // 글작성
    @PostMapping
    public ResponseEntity<Post> writePost(@Valid @RequestBody Post post) {
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
