package com.detrasdelcodigo.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idusuario;

	private String apellidos;

	private String email;
	
	@Lob
	private String avatar;

	private String nombre;

	private String password;

	private String username;

	//bi-directional many-to-one association to Comentario
	@OneToMany(mappedBy="usuario")
	private List<Comentario> comentarios;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="usuario")
	private List<Post> posts;

	//uni-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="rol")
	private Rol rol;

	public Comentario addComentario(Comentario comentario) {
		getComentarios().add(comentario);
		comentario.setUsuario(this);

		return comentario;
	}

	public Comentario removeComentario(Comentario comentario) {
		getComentarios().remove(comentario);
		comentario.setUsuario(null);

		return comentario;
	}


	public Post addPost(Post post) {
		getPosts().add(post);
		post.setUsuario(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setUsuario(null);

		return post;
	}

	

}