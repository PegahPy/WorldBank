package worldbank.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import lombok.Data;
import worldbank.exceptions.CalculationFailedException;
import worldbank.exceptions.DataNotAvailableException;
import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.factory.AnalysisFactory;
import worldbank.models.Country;
import worldbank.models.ViewData;
import worldbank.models.analyses.Analysis;
import worldbank.viewcontrollers.GuiLogin;

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
		try {
			AnalysisFactory analysisFacorty = new AnalysisFactory();
			Analysis a1 = analysisFacorty.createAnalysis("CO2 vs Energy Use vs PM2.5 air pollution Annual Change");
			Analysis a2 = analysisFacorty.createAnalysis("PM2.5 air pollution vs Forest area");
			Analysis a3 = analysisFacorty.createAnalysis("CO2 emissions vs GDP per capita ratio");
			Analysis a4 = analysisFacorty.createAnalysis("Current Health Expenditure vs Hospital Beds ratio");
	
			Analysis a5 = analysisFacorty.createAnalysis("Forest Area");
			Analysis a6 = analysisFacorty.createAnalysis("Government Expenditure on Education");
			Analysis a7 = analysisFacorty.createAnalysis("Government expenditure on education vs Current health expenditure");
			Analysis a8 = analysisFacorty.createAnalysis("Problems in Accessing Health Care vs Mortality rate");
			analeses.add(a1);
			analeses.add(a2);
			analeses.add(a3);
			analeses.add(a4);
			analeses.add(a5);
			analeses.add(a6);
			analeses.add(a7);
			analeses.add(a8);
		} catch (IndicatorNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<List<ViewData>> calculateAnalysis(String countryName, String analysisName, int fromYear, int toYear) throws DataNotAvailableException, CalculationFailedException {
		System.out.println(analysisName);
		Country country = countryManager.getAllCountries()
				.stream()
				.filter(c -> c.getName().equals(countryName))
				.findFirst()
				.orElseThrow(CalculationFailedException::new);
		return analeses.stream()
			.filter(a -> a.getName().equals(analysisName))
			.findFirst()
			.map(a -> {
				try {
					return a.Calculate(country, fromYear, toYear);
				} catch (DataNotAvailableException e) {
					e.printStackTrace();
				}
				return null;
			})
			.orElseThrow(DataNotAvailableException::new);
	}
	
	public Analysis getAnalysisByName(String name) {
		return analeses.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
	}
	
	public static void main(String[] args) {
		
		AnalysisManager am = AnalysisManager.getInstance();
		try {
			am.calculateAnalysis("United States", "Government expenditure on education vs Current health expenditure", 2016, 2018).forEach(result -> {
				System.out.println(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
