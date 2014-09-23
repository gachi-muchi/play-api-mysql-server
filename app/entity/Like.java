package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "t_like")
public class Like extends Model {

	@Id
	@Column(columnDefinition = "bigint(20)")
	private String id;

	@Column(columnDefinition = "bigint(20)")
	private String toId;

	@Column(columnDefinition = "bigint(20)")
	private String fromId;

	@Column(columnDefinition = "datetime")
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getUserId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
