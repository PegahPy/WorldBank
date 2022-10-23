package worldbank.models.analyses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.ViewData;
import worldbank.models.calculations.AnnualPercentageChange;
import worldbank.models.calculations.Ratio;

public class CO2EnergyAirPollution extends Analysis {

	public CO2EnergyAirPollution() {
		super();
		this.name = "CO2 vs Energy Use vs PM2.5 air pollution Annual Change";
		Indicator i1 = indicatorManager.getIndicatorByName("CO2 emissions");
		Indicator i2 = indicatorManager.getIndicatorByName("Energy use");
		Indicator i3 = indicatorManager.getIndicatorByName("PM2.5 air pollution");
		this.activeIndicators = Arrays.asList(i1, i2, i3);
		this.setCalculation(new AnnualPercentageChange());
	}

	@Override
	public List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) {
		List<List<ViewData>> lastResault = new ArrayList<>();
		for(Indicator indicator : this.getActiveIndicators()) {
			List<Double> rawData = fetchData.fetchIndicatorResaults(country, indicator, fromYear - 1 , toYear);
			List<ViewData> processedData = this.getCalculation().getCalculateMethod(rawData, toYear);
			lastResault.add(processedData);
		}
		return lastResault;
	}

}
