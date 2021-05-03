package com.detrasdelcodigo.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.dto.PostExtraData;
import com.detrasdelcodigo.api.dto.converters.PostDtoConverter;
import com.detrasdelcodigo.api.model.Post;
import com.detrasdelcodigo.api.model.Tag;
import com.detrasdelcodigo.api.services.CategoriaService;
import com.detrasdelcodigo.api.services.PostService;
import com.prueba.api.errors.PostNotFoundException;

@RequestMapping("/posts/")
@RestController
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private PostDtoConverter postConverter;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/")
	public ResponseEntity<?> getAllPosts(@RequestParam(name = "titulo", defaultValue = "%%") String titulo,
			@RequestParam(name = "username", defaultValue = "%%") String username,
			@RequestParam(name = "categoria", required = false) List<Long> idCategorias,
			@RequestParam(name = "tag", required = false) List<Long> tags,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		if (idCategorias == null || idCategorias.isEmpty()) {

			idCategorias = categoriaService.findAll().stream().map(c -> c.getIdcategoria())
					.collect(Collectors.toList());
		}
		Page<PostDto> posts = postService.findAllFilterDto("%" + titulo + "%", username, idCategorias, tags, pageable);

		return ResponseEntity.ok(posts);

	}

	@GetMapping("/id/{idpost}")
	public ResponseEntity<?> getPostById(@PathVariable Long idpost) {

		PostDto post = postConverter.convertToDto(postService.findById(idpost)
				.orElseThrow(() -> new PostNotFoundException("No se ha encontrado el post con id: " + idpost)), true);

		return ResponseEntity.ok(post);

	}

	@GetMapping("/{username}/{idpost}")
	public ResponseEntity<?> getPostByIdAndUsername(@PathVariable String username, @PathVariable Long idpost) {

		PostDto post = postConverter.convertToDto(postService.findByIdAndUsuario(idpost, username)
				.orElseThrow(() -> new PostNotFoundException("No se ha encontrado el post con id: " + idpost)), true);

		return ResponseEntity.ok(post);

	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getPostByUsername(@PathVariable String username,
			@RequestParam(name = "titulo", defaultValue = "%%") String titulo,
			@RequestParam(name = "categoria", required = false) List<Long> idCategorias,
			@RequestParam(name = "tag", required = false) List<Long> tags,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		if (idCategorias == null || idCategorias.isEmpty()) {

			idCategorias = categoriaService.findAll().stream().map(c -> c.getIdcategoria())
					.collect(Collectors.toList());
		}
		Page<Post> posts = postService.findAllFilter("%" + titulo + "%", username, idCategorias, pageable);

		Page<PostDto> postsFilter;
		if (tags == null || tags.isEmpty())
			postsFilter = posts.map(post -> postConverter.convertToDto(post, true));
		else {

			List<PostDto> postsList = posts.getContent().stream().filter(p -> {

				List<Tag> tagsPost = p.getTags();

				for (Tag tag : tagsPost) {

					if (tags.contains(tag.getIdtag())) {
						return true;
					}
				}

				return false;
			}).map(post -> postConverter.convertToDto(post, true)).collect(Collectors.toList());

			postsFilter = new PageImpl<PostDto>(postsList, pageable, postsList.size());

		}

		return ResponseEntity.ok(postsFilter);

	}
	
	@GetMapping("/{username}/extradata/")
	public ResponseEntity<?> getTotalComentarios(@PathVariable String username){
		
		int totalComments = postService.getTotalComments(username);
		int totalPostsCreated = postService.getTotalPostsCreated(username);
		
		return ResponseEntity.ok(new PostExtraData(totalComments,totalPostsCreated,username));
		
	}

}
