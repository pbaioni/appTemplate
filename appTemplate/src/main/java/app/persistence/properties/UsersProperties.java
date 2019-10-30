package app.persistence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:users.properties")
@ConfigurationProperties(prefix="users")
public class UsersProperties {
	
	private String userProperty1;
	
	private String userProperty2;
	
	public UsersProperties() {
		
	}

	public String getDummyproperty1() {
		return userProperty1;
	}

	public void setDummyproperty1(String dummyproperty1) {
		this.userProperty1 = dummyproperty1;
	}

	public String getDummyproperty2() {
		return userProperty2;
	}

	public void setDummyproperty2(String dummyproperty2) {
		this.userProperty2 = dummyproperty2;
	}

}
