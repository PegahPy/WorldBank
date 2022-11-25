package worldbank.models.charts;

import lombok.Getter;
import lombok.Setter;
import worldbank.enums.ChartTypes;

@Getter
@Setter
public abstract class Chart {
	private ChartTypes type;
	private int index;
}
