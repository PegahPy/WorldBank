package worldbank.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import worldbank.models.Analysis;
import worldbank.models.Indicator;
import worldbank.models.calculations.AnnualPercentageChange;
import worldbank.models.calculations.ICalculation;

@Data
public class AnalysisManager {
	private static AnalysisManager instance;
	private IndicatorManager indicatorManager;
	private List<Analysis> analeses;
	
	public static AnalysisManager getInstance() {
		if (instance == null)
			instance = new AnalysisManager();

		return instance;
	}
	
	private AnalysisManager() {		
		analeses = new ArrayList<>();
		indicatorManager = IndicatorManager.getInstance();
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
}
