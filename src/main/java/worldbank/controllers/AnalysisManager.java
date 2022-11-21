package worldbank.controllers;

import java.util.ArrayList;
import java.util.List;
import factory.AnalysisFactory;
import lombok.Data;
import worldbank.models.Country;
import worldbank.models.ViewData;
import worldbank.models.analyses.Analysis;

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
		AnalysisFactory analysisFacorty = new AnalysisFactory();
		Analysis a1 = analysisFacorty.createAnalysis("CO2 vs Energy Use vs PM2.5 air pollution Annual Change");
		Analysis a2 = analysisFacorty.createAnalysis("PM2.5 air pollution vs Forest area");
		Analysis a3 = analysisFacorty.createAnalysis("CO2 emissions vs GDP per capita");
		Analysis a4 = analysisFacorty.createAnalysis("Current Health Expenditure vs Hospital Beds");
		Analysis a5 = analysisFacorty.createAnalysis("Forest Area");
		Analysis a6 = analysisFacorty.createAnalysis("Government Expenditure on Education");
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
