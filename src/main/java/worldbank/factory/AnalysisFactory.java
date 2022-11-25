package worldbank.factory;

import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.models.analyses.AirPollutionForestArea;
import worldbank.models.analyses.Analysis;
import worldbank.models.analyses.CO2EmissionsGDPperCapita;
import worldbank.models.analyses.CO2EnergyAirPollution;
import worldbank.models.analyses.CurrentHealthExpenditureHospitalBeds;
import worldbank.models.analyses.ForestArea;
import worldbank.models.analyses.GovernmentExpenditure;
import worldbank.models.analyses.GovernmentExpenditureOnEducationCurrentHealthExpenditure;
import worldbank.models.analyses.ProblemsInAccessingHealthCareMortalityRate;

public class AnalysisFactory {
	public Analysis createAnalysis(String name) throws IndicatorNotFoundException {
	      switch (name) {
	        case "CO2 vs Energy Use vs PM2.5 air pollution Annual Change":
	            return new CO2EnergyAirPollution();
	        case "PM2.5 air pollution vs Forest area":
	            return new AirPollutionForestArea();
	        case "CO2 emissions vs GDP per capita ratio":
	            return new CO2EmissionsGDPperCapita();
	        case "Current Health Expenditure vs Hospital Beds ratio":
	            return new CurrentHealthExpenditureHospitalBeds();
	        case "Forest Area":
	            return new ForestArea();
	        case "Government Expenditure on Education":
	            return new GovernmentExpenditure();
	        case "Problems in Accessing Health Care vs Mortality rate":
	            return new ProblemsInAccessingHealthCareMortalityRate();
	        case "Government expenditure on education vs Current health expenditure":
	            return new GovernmentExpenditureOnEducationCurrentHealthExpenditure();
	        default:
	            throw new IllegalArgumentException("Unknown Anayisis "+name);
	        }
	}

}
	