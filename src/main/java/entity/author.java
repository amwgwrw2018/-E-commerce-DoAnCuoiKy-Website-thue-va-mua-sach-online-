package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.DynamicUpdate;


@javax.persistence.Entity
@Table(name = "author")
@DynamicUpdate(value=true)
public class author {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String id;
	@Column(name = "username")
	String username;
	@Column(name = "authorName")
	String authorName;
	@Column(name = "authorInfo")
	String authorInfo;
	@Column(name = "avatar")
	String avatar;
	@Column(name = "authorDescription")
	String authorDescription;
	@OneToMany(mappedBy="authorID")
	List<bookandauthor> bookAndAuthor;
	public author() {
		// TODO Auto-generated constructor stub
	}
	public author(String id) {
		this.id=id;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorInfo() {
		return authorInfo;
	}

	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAuthorDescription() {
		return authorDescription;
	}

	public void setAuthorDescription(String authorDescription) {
		this.authorDescription = authorDescription;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
