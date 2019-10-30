package app.commands.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:commands.properties")
@ConfigurationProperties(prefix="commands")
public class CommandsProperties {
	
	private boolean start;
	
	public CommandsProperties() {
		
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

}
