package com.detrasdelcodigo.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.detrasdelcodigo.api.dto.CrearPostDto;
import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.dto.UpdatePostDto;
import com.detrasdelcodigo.api.dto.converters.PostDtoConverter;
import com.detrasdelcodigo.api.model.Post;
import com.detrasdelcodigo.api.model.Tag;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.repositories.PostRepository;
import com.detrasdelcodigo.api.services.base.BaseService;
import com.prueba.api.errors.PostNotFoundException;

@Service
public class PostService extends BaseService<Post, Long, PostRepository> {

	@Autowired
	private PostDtoConverter postConverter;

	public List<Post> findPostsByUsername(String username) {

		return repositorio.findPostsByUsername(username);
	}

	public List<Post> findAllFilter(String titulo, String username, List<Long> idcategoria) {

		return repositorio.findAllFilter(titulo, username, idcategoria);
	}

	public Page<Post> findAllFilter(String titulo, String username, List<Long> idcategoria, Pageable page) {

		return repositorio.findAllFilter(titulo, username, idcategoria, page);
	}

	public Page<PostDto> findAllFilterDto(String titulo, String username, List<Long> idcategoria, List<Long> tags,
			Pageable page) {

		Page<Post> posts = this.findAllFilter("%" + titulo + "%", username, idcategoria, page);

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

			postsFilter = new PageImpl<PostDto>(postsList, page, postsList.size());

		}

		return postsFilter;

	}
	
	public Optional<Post> findByIdAndUsuario(Long idpost, String username) {

		return repositorio.findByIdAndUsuario(idpost, username);
	}

	public Post createPost(CrearPostDto postDto, Usuario user) {

		Post postACrear = postConverter.convertDtoToPost(postDto);
		postACrear.setUsuario(user);
		postACrear.setCreatedAt(LocalDateTime.now());
		Post postCreado = repositorio.save(postACrear);

		return repositorio.findByIdAndUsuario(postCreado.getIdpost(), user.getUsername())
				.orElseThrow(() -> new PostNotFoundException("Post no encontrado"));
	}
	
	public Post updatePost(UpdatePostDto postDto, Usuario user) {
		
		Post postAEditar = postConverter.convertDtoToPost(postDto);
		
		Post postExistente = repositorio.findByIdAndUsuario(postDto.getIdpost(), user.getUsername()).get();
		
		if(postExistente == null) {
			
			throw new PostNotFoundException("El post no existe");
		}
		
		postAEditar.setCreatedAt(postExistente.getCreatedAt());
		postAEditar.setPortada(postAEditar.getPortada() == null ? postExistente.getPortada() : postAEditar.getPortada());
		postAEditar.setUsuario(user);
		
		
		return repositorio.save(postAEditar);
	}

	
}
