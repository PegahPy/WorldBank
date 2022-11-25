package worldbank.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;

import worldbank.exceptions.IndicatorNotFoundException;

class IndicatorManagerTest {
	
	private String correctIndicatorName = "PM2.5 air pollution";
	private String incorrectIndicatorName = "WRONG";
	
	private IndicatorManager indicatorManager = IndicatorManager.getInstance();

	@Test
	void testGetAllIndicators() {
		assertNotNull(indicatorManager.getAllIndicators());
	}

	@Test
	void testGetIndicatorByName_correctName() throws IndicatorNotFoundException {
		assertNotNull(indicatorManager.getIndicatorByName(correctIndicatorName));
	}
	
	@Test
	void testGetIndicatorByName_wrongName() throws IndicatorNotFoundException {
		assertThrows(IndicatorNotFoundException.class, () -> {indicatorManager.getIndicatorByName(incorrectIndicatorName);});
	}

}
