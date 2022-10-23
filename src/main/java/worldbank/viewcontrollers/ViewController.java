package worldbank.viewcontrollers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldbank.controllers.AnalysisManager;
import worldbank.controllers.FetchData;
import worldbank.controllers.IndicatorManager;
import worldbank.models.Country;
import worldbank.models.Indicator;
import worldbank.models.analyses.Analysis;
import worldbank.models.calculations.AnnualPercentageChange;
import worldbank.models.calculations.ICalculation;
import worldbank.models.calculations.Ratio;

public class ViewController {
	private static ViewController instance;
	private AnalysisManager analysisManager;
	public static ViewController getInstance() {
		if (instance == null)
			instance = new ViewController();

		return instance;
	}
	
	private ViewController() {
		analysisManager = AnalysisManager.getInstance();
	}
}
