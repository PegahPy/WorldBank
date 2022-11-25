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
import worldbank.models.calculations.NoProcess;
import worldbank.models.calculations.Ratio;

public class ProblemsInAccessingHealthCareMortalityRate extends Analysis {

	public ProblemsInAccessingHealthCareMortalityRate() {
		super();
		this.name = "Problems in Accessing Health Care vs Mortality rate";
		try {
			Indicator i1 = indicatorManager.getIndicatorByName("Problems in accessing health care");
			Indicator i2 = indicatorManager.getIndicatorByName("Mortality rate, infant");
			this.activeIndicators = Arrays.asList(i1, i2);
			this.setCalculation(new NoProcess());
		} catch (IndicatorNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) throws DataNotAvailableException {
		List<List<ViewData>> lastResault = new ArrayList<>();
		for(Indicator indicator : this.getActiveIndicators()) {
			List<Double> rawData = fetchData.fetchIndicatorResaults(country, indicator, fromYear, toYear);
			List<ViewData> processedData = this.getCalculation().getCalculateMethod(rawData, toYear);
			lastResault.add(processedData);
		}
		return lastResault;
	}
	
	@Override
	public Analysis copy(){
		return new ProblemsInAccessingHealthCareMortalityRate();
	}

}
