package app.instrument.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:instruments.yml")
@ConfigurationProperties(prefix="prefix")
public class InstrumentsDefinitions {

	private List<InstrumentDefinition> instruments = new ArrayList<InstrumentDefinition>();
	
	public InstrumentsDefinitions() {
		
	}

	public List<InstrumentDefinition> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<InstrumentDefinition> instruments) {
		this.instruments = instruments;
	}

	@Override
	public String toString() {
		return "InstrumentsDefinitions [instruments=" + instruments + "]";
	}
	
}
