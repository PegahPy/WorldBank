package worldbank.viewcontrollers;

import javax.swing.JFrame;

import worldbank.controllers.AnalysisManager;
import worldbank.models.Analysis;
import worldbank.models.Country;

public class GuiRunner {

	public static void main(String[] args) {
		
//		JFrame loginFrame = GuiLogin.getInstance();
//		loginFrame.setVisible(true);
		
//		JFrame mainFrame = GuiMain.getInstance();
//		mainFrame.pack();
//		mainFrame.setVisible(true);
		
//		FetchData f = FetchData.getInstance();
//		f.fetchAllCountries();
		
//		IndicatorManager i = IndicatorManager.getInstance();
//		i.getAllIndicators().forEach(indicator -> {
//			System.out.println(indicator);
//		});
		
//		FetchData f = FetchData.getInstance();
//		Country c = new Country();
//		c.setId("usa");
//		Indicator i = new Indicator();
//		i.setCode("EN.ATM.CO2E.PC");
//		f.fetchIndicatorResaults(c, i, 2000, 2001).forEach(result -> {
//			System.out.println(result);
//		});
		
		ViewController v = ViewController.getInstance();
		AnalysisManager am = AnalysisManager.getInstance();
		Country c = new Country();
		c.setId("usa");
		Analysis a = am.getAnaleses().get(0);
		v.ali(c, a, 2011, 2013).forEach(result -> {
			System.out.println(result);
		});
	}
	
	

}
