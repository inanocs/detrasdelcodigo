package com.detrasdelcodigo.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the posts database table.
 * 
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
@NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idpost;

	@Lob
	private String contenido;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	private String descripcion;

	@Lob
	private String portada;

	@Lob
	private String titulo;

	// bi-directional many-to-one association to Comentario
	@OneToMany(mappedBy = "post")
	private List<Comentario> comentarios;

	// uni-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name = "idcategoria")
	private Categoria categoria;

	// bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

	// uni-directional many-to-many association to Tag
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "posts_tags", joinColumns = { @JoinColumn(name = "idpost") }, inverseJoinColumns = {
			@JoinColumn(name = "idtag") })
	private List<Tag> tags;

	public Comentario addComentario(Comentario comentario) {
		getComentarios().add(comentario);
		comentario.setPost(this);

		return comentario;
	}

	public Comentario removeComentario(Comentario comentario) {
		getComentarios().remove(comentario);
		comentario.setPost(null);

		return comentario;
	}
}