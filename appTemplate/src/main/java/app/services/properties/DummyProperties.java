package app.services.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dummy.properties")
@ConfigurationProperties(prefix="dummy")
public class DummyProperties {
	
	private String dummyproperty1;
	
	private String dummyproperty2;
	
	public DummyProperties() {
		
	}

	public String getDummyproperty1() {
		return dummyproperty1;
	}

	public void setDummyproperty1(String dummyproperty1) {
		this.dummyproperty1 = dummyproperty1;
	}

	public String getDummyproperty2() {
		return dummyproperty2;
	}

	public void setDummyproperty2(String dummyproperty2) {
		this.dummyproperty2 = dummyproperty2;
	}

}
