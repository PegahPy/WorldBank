package worldbank.models.analyses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.ViewData;
import worldbank.models.calculations.Average;

public class GovernmentExpenditure extends Analysis {

	public GovernmentExpenditure() {
		super();
		this.name = "Government Expenditure on Education";
		Indicator i1 = indicatorManager.getIndicatorByName("Government expenditure");
		this.activeIndicators = Arrays.asList(i1);
		this.setCalculation(new Average());
	}

	@Override
	public List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) {
		List<List<ViewData>> lastResault = new ArrayList<>();
		List<Double> rawData = fetchData.fetchIndicatorResaults(country, this.getActiveIndicators().get(0), fromYear - 1 , toYear);
		List<ViewData> processedData = this.getCalculation().getCalculateMethod(rawData, toYear);
		lastResault.add(processedData);
		return lastResault;
	}

}
