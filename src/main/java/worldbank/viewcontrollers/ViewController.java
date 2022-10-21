package worldbank.viewcontrollers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldbank.controllers.AnalysisManager;
import worldbank.controllers.FetchData;
import worldbank.controllers.IndicatorManager;
import worldbank.models.Analysis;
import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.calculations.AnnualPercentageChange;
import worldbank.models.calculations.ICalculation;

public class ViewController {
	private static ViewController instance;
	private AnalysisManager analysisManager;
	private IndicatorManager indicatorManager;
	private FetchData fetchData;
	public static ViewController getInstance() {
		if (instance == null)
			instance = new ViewController();

		return instance;
	}
	
	private ViewController() {
		analysisManager = AnalysisManager.getInstance();
		fetchData = FetchData.getInstance();
		indicatorManager = IndicatorManager.getInstance();
	}
	
	private void loadAnalese() {
		List<Analysis> analeses = new ArrayList<>();
		
		//Add CO2 emissions vs Energy use vs PM2.5 air pollution
		Analysis a = new Analysis();
		Indicator i1 = indicatorManager.getIndicatorByName("CO2 emissions");
		Indicator i2 = indicatorManager.getIndicatorByName("Energy use");
		Indicator i3 = indicatorManager.getIndicatorByName("PM2.5 air pollution");
		
		ICalculation annualPercentage = new AnnualPercentageChange();
		a.setName("CO2 emissions vs Energy use vs PM2.5 air pollution");
		a.setActiveIndicators(Arrays.asList(i1, i2, i3));
		a.setCalculation(annualPercentage);
		analeses.add(a);
		
		//Add PM2.5 air pollution vs Forest area
		Analysis b = new Analysis();
		Indicator i4 = indicatorManager.getIndicatorByName("PM2.5 air pollution");
		Indicator i5 = indicatorManager.getIndicatorByName("Forest area");
		b.setName("PM2.5 air pollution vs Forest area");
		a.setActiveIndicators(Arrays.asList(i4, i5));
		a.setCalculation(annualPercentage);
		analeses.add(b);
		
		
	}
	
	
	public List<List<Double>> ali(Country country, Analysis analysis, int fromYear, int toYear) {
		List<List<Double>> lastResault = new ArrayList<>();
		for(Indicator indicator : analysis.getActiveIndicators()) {
			List<Double> rawData = fetchData.fetchIndicatorResaults(country, indicator, fromYear - 1 , toYear);
			List<Double> processedData = analysis.getCalculation().calculate(rawData);
			lastResault.add(processedData);
		}
		return lastResault;
	}
}
