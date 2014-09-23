package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Entity
public class User extends Model {

	@Id
	@Column(columnDefinition = "bigint(20)")
	private String userId;

	@Column(columnDefinition = "varchar(64)")
	private String name;

	@Column(columnDefinition = "varchar(128)")
	private String mail;

	@Column(columnDefinition = "varchar(16)")
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
