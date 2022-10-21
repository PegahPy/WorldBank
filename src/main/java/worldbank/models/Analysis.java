package worldbank.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import worldbank.models.calculations.ICalculation;

@Data
public class Analysis {
	
	private String name;
	private List<Indicator> activeIndicators;
	private ICalculation calculation;
	
	
}
