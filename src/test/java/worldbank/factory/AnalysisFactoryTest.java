package worldbank.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.models.analyses.AirPollutionForestArea;
import worldbank.models.analyses.Analysis;
import worldbank.models.analyses.CO2EmissionsGDPperCapita;
import worldbank.models.analyses.CO2EnergyAirPollution;

class AnalysisFactoryTest {
	private AnalysisFactory analysisFactory = new AnalysisFactory();
	
	@Test
	void testCreateAnalysis() throws IndicatorNotFoundException {
		Analysis a = analysisFactory.createAnalysis("CO2 vs Energy Use vs PM2.5 air pollution Annual Change");
		assertEquals(CO2EnergyAirPollution.class, a.getClass());
	}
	
	@Test
	void testCreateAnalysis2() throws IndicatorNotFoundException {
		Analysis a = analysisFactory.createAnalysis("PM2.5 air pollution vs Forest area");
		assertEquals(AirPollutionForestArea.class, a.getClass());
	}
	
	@Test
	void testCreateAnalysis3() throws IndicatorNotFoundException {
		Analysis a = analysisFactory.createAnalysis("CO2 emissions vs GDP per capita ratio");
		assertEquals(CO2EmissionsGDPperCapita.class, a.getClass());
	}
	
	@Test
	void testCreateAnalysis4() throws IndicatorNotFoundException {
		assertThrows(IllegalArgumentException.class, () -> {analysisFactory.createAnalysis("WRONG");});
	}

}
