package worldbank.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.ViewData;
import worldbank.models.analyses.AirPollutionForestArea;
import worldbank.models.analyses.Analysis;
import worldbank.models.analyses.CO2EmissionsGDPperCapita;
import worldbank.models.analyses.CO2EnergyAirPollution;
import worldbank.models.analyses.CurrentHealthExpenditureHospitalBeds;
import worldbank.models.analyses.ForestArea;
import worldbank.models.analyses.GovernmentExpenditure;
import worldbank.models.calculations.AnnualPercentageChange;
import worldbank.models.calculations.ICalculation;
import worldbank.models.calculations.Ratio;

@Data
public class AnalysisManager {
	private static AnalysisManager instance;
	private List<Analysis> analeses;
	private CountryManager countryManager;
	
	public static AnalysisManager getInstance() {
		if (instance == null)
			instance = new AnalysisManager();

		return instance;
	}
	
	private AnalysisManager() {		
		countryManager = CountryManager.getInstance();
		analeses = new ArrayList<>();
		populateAnalyses();
	}
	
	private void populateAnalyses() {
		Analysis a1 = new CO2EnergyAirPollution();
		Analysis a2 = new AirPollutionForestArea();
		Analysis a3 = new CO2EmissionsGDPperCapita();
		Analysis a4 = new ForestArea();
		Analysis a5 = new GovernmentExpenditure();
		Analysis a6 = new CurrentHealthExpenditureHospitalBeds();
		analeses.add(a1);
		analeses.add(a2);
		analeses.add(a3);
		analeses.add(a4);
		analeses.add(a5);
		analeses.add(a6);
	}
	
	public List<List<ViewData>> calculateAnalysis(String countryName, String analysisName, int fromYear, int toYear) throws Exception {
		Country country = countryManager.getAllCountries()
				.stream()
				.filter(c -> c.getName().equals(countryName))
				.findFirst()
				.orElseThrow(Exception::new);
		return analeses.stream()
			.filter(a -> a.getName().equals(analysisName))
			.findFirst()
			.map(a -> a.Calculate(country, fromYear, toYear))
			.orElseThrow(Exception::new);
	}
}
