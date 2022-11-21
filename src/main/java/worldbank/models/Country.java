package worldbank.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Country {
	private String name;
	private String id;
	private boolean fetchable;
}
