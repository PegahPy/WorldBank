package worldbank.models.analyses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldbank.exceptions.DataNotAvailableException;
import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.ViewData;
import worldbank.models.calculations.Average;
import worldbank.models.calculations.Ratio;

public class ForestArea extends Analysis {
	
	public ForestArea(){
		super();
		this.name = "Forest Area";
		try {
			Indicator i1 = indicatorManager.getIndicatorByName("Forest area");
			this.activeIndicators = Arrays.asList(i1);
			this.setCalculation(new Average());
		} catch (IndicatorNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) throws DataNotAvailableException {
		List<List<ViewData>> lastResault = new ArrayList<>();
		List<Double> rawData = fetchData.fetchIndicatorResaults(country, this.getActiveIndicators().get(0), fromYear , toYear);
		List<ViewData> processedData = this.getCalculation().getCalculateMethod(rawData, toYear);
		lastResault.add(processedData);
		return lastResault;
	}
	
	@Override
	public Analysis copy() {
		return new ForestArea();
	}

}
