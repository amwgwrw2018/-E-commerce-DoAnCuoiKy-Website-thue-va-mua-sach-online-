package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@javax.persistence.Entity
@Table(name = "language")
@DynamicUpdate(value=true)
public class language {
	@Id
	@Column(name = "languageId")
	String languageId;
	@Column(name = "language")
	String language;
	
	@OneToMany(mappedBy="languageId")
	List<book> bookByLanguage;
	
	public language() {
		// TODO Auto-generated constructor stub
	}
	
	public language(String languageId) {
		
		this.languageId = languageId;
	}

	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
}
