package worldbank.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Indicator {	
	private String name;
	private String description;
	private String code;
}
