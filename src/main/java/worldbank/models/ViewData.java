package worldbank.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewData {
	private int year;
	private Double value;
}
