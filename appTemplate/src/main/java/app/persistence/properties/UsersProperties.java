package app.persistence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:users.properties")
@ConfigurationProperties
public class UsersProperties {

	private String userProperty1;
	
	private String userProperty2;
	
	public UsersProperties() {
		
	}

	public String getDummyproperty1() {
		return userProperty1;
	}

	public void setDummyproperty1(String userproperty1) {
		this.userProperty1 = userproperty1;
	}

	public String getDummyproperty2() {
		return userProperty2;
	}

	public void setDummyproperty2(String userproperty2) {
		this.userProperty2 = userproperty2;
	}

	@Override
	public String toString() {
		return "UsersProperties [userProperty1=" + userProperty1 + ", userProperty2=" + userProperty2 + "]";
	}

}
