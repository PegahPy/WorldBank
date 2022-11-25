package worldbank.viewcontrollers;

import javax.swing.JFrame;

import worldbank.controllers.AnalysisManager;
import worldbank.models.Country;
import worldbank.models.analyses.Analysis;

public class GuiRunner {

	public static void main(String[] args) {
		
		JFrame loginFrame = GuiLogin.getInstance();
		loginFrame.setVisible(true);

	}
	
	

}
