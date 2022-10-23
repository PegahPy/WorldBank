package worldbank.viewcontrollers;

import javax.swing.JFrame;

import worldbank.controllers.AnalysisManager;
import worldbank.models.Country;
import worldbank.models.analyses.Analysis;

public class GuiRunner {

	public static void main(String[] args) {
		
//		JFrame loginFrame = GuiLogin.getInstance();
//		loginFrame.setVisible(true);
		
//		JFrame mainFrame = GuiMain.getInstance();
//		mainFrame.pack();
//		mainFrame.setVisible(true);
		
//		});
		
		AnalysisManager am = AnalysisManager.getInstance();
		try {
			am.calculateAnalysis("United States", "Government Expenditure on Education", 2016, 2016).forEach(result -> {
				System.out.println(result);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
