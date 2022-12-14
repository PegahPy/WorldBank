package worldbank.models.analyses;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import worldbank.controllers.FetchData;
import worldbank.controllers.IndicatorManager;
import worldbank.exceptions.DataNotAvailableException;
import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.ViewData;
import worldbank.models.calculations.ICalculation;

@Getter
@Setter
public abstract class Analysis {
	
	protected String name;
	protected List<Indicator> activeIndicators;
	protected ICalculation calculation;
	protected IndicatorManager indicatorManager;
	protected FetchData fetchData;
	
	public Analysis() {
		indicatorManager = IndicatorManager.getInstance();
		fetchData = FetchData.getInstance();
	}
	
	public abstract List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) throws DataNotAvailableException;
	public abstract Analysis copy() throws IndicatorNotFoundException;
}
