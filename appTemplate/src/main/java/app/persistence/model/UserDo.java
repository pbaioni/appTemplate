package app.persistence.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/** 
 * 	This is a data structure, so
 *  fields can be public. (Clean-Code)
 */
@Entity
@Table(name="users")
public class UserDo {
	
    @Id    
    @Column
    public String userName;
    @Column
    public String password;
    @Column
    public Date lastLogin;
	
	public UserDo() {
		//Default constructor needed for JPA.
	}

	public UserDo(String userName, String password, Date lastLogin) {
		super();
		this.userName = userName;
		this.password = password;
		this.lastLogin = lastLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public String toString() {
		return "UserDO [userName=" + userName + ", password=" + password + ", lastLogin=" + lastLogin + "]";
	}
	
}

