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
import worldbank.models.calculations.Ratio;

public class CO2EmissionsGDPperCapita extends Analysis {

	public CO2EmissionsGDPperCapita() {
		super();
		this.name = "CO2 emissions vs GDP per capita ratio";
		try {
			Indicator i1 = indicatorManager.getIndicatorByName("CO2 emissions");
			Indicator i2 = indicatorManager.getIndicatorByName("GDP per capita");
			this.activeIndicators = Arrays.asList(i1, i2);
			this.setCalculation(new Ratio());
		} catch (IndicatorNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<List<ViewData>> Calculate(Country country, int fromYear, int toYear) throws DataNotAvailableException{
		List<List<ViewData>> lastResault = new ArrayList<>();
		List<Double> rawData1 = fetchData.fetchIndicatorResaults(country, this.getActiveIndicators().get(0), fromYear, toYear);
		List<Double> rawData2 = fetchData.fetchIndicatorResaults(country, this.getActiveIndicators().get(1), fromYear, toYear);
		List<ViewData> processedData = this.getCalculation().getCalculateMethod(Arrays.asList(rawData1, rawData2), toYear);
		lastResault.add(processedData);
		return lastResault;
	}
	
	@Override
	public Analysis copy() {
		return new CO2EmissionsGDPperCapita();
	}
}
