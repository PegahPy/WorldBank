package worldbank.models;

import lombok.Data;

@Data
public class Country {
	private String name;
	private String id;
	private boolean fetchable;
}
