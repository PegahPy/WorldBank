package worldbank.models.analyses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldbank.exceptions.DataNotAvailableException;
import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.ViewData;
import worldbank.models.calculations.AnnualPercentageChange;
import worldbank.models.calculations.Average;

public class GovernmentExpenditureOnEducationCurrentHealthExpenditure extends Analysis{
	public GovernmentExpenditureOnEducationCurrentHealthExpenditure(){
		super();
		this.name = "Government expenditure on education vs Current health expenditure";
		try {
			Indicator i1 = indicatorManager.getIndicatorByName("Government expenditure");
			Indicator i2 = indicatorManager.getIndicatorByName("Current health expenditure");
			this.activeIndicators = Arrays.asList(i1, i2);
			this.setCalculation(new AnnualPercentageChange());
		} catch (IndicatorNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) throws DataNotAvailableException {
		List<List<ViewData>> lastResault = new ArrayList<>();
		for(Indicator indicator : this.getActiveIndicators()) {
			List<Double> rawData = fetchData.fetchIndicatorResaults(country, indicator, fromYear - 1, toYear);
			List<ViewData> processedData = this.getCalculation().getCalculateMethod(rawData, toYear);
			lastResault.add(processedData);
		}
		return lastResault;
	}
	
	@Override
	public Analysis copy(){
		return new GovernmentExpenditureOnEducationCurrentHealthExpenditure();
	}

}
