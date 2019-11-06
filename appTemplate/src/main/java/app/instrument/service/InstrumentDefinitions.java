package app.instrument.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:instruments.yml")
@ConfigurationProperties(prefix="io")
public class InstrumentDefinitions {

    private List<InstrumentDefinition> instruments = new ArrayList<>();

    public static class InstrumentDefinition {
        private String name;
        private String model;
        private String address;
        private int timeout;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public int getTimeout() {
			return timeout;
		}
		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}
		@Override
		public String toString() {
			return "InstrumentDefinition [name=" + name + ", model=" + model + ", address=" + address + ", timeout="
					+ timeout + "]";
		}

    }

	public List<InstrumentDefinition> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<InstrumentDefinition> instruments) {
		this.instruments = instruments;
	}

	@Override
	public String toString() {
		return "InstrumentDefinitions [instruments=" + instruments + "]";
	}


  
}

