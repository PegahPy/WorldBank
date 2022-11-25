package worldbank.controllers;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import worldbank.exceptions.CalculationFailedException;
import worldbank.exceptions.DataNotAvailableException;
import worldbank.models.ViewData;

class AnalysisManagerTest {
	
	private AnalysisManager analysisManager = AnalysisManager.getInstance();
	
	private String SampleCountry1 = "United States";
	private String SampleAnalysis1 = "Government expenditure on education vs Current health expenditure";
	
	private String WrongCountry = "United Statess";


	@Test
	void testCalculateManager_valid() throws Exception {
		List<List<ViewData>> result = analysisManager.calculateAnalysis(SampleCountry1, SampleAnalysis1, 2016, 2018);
		assertEquals(2, result.size());
		assertEquals(3, result.get(0).size());
	}
	
	@Test
	void testCalculateManager_unValid() throws Exception {
		assertThrows(CalculationFailedException.class,  ()->{analysisManager.calculateAnalysis(WrongCountry, SampleAnalysis1, 2016, 2018);});
	}

}
