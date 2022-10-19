package worldbank;

import javax.swing.JFrame;

public class GuiRunner {

	public static void main(String[] args) {
		
//		JFrame loginFrame = GuiLogin.getInstance();
//		loginFrame.setVisible(true);
		
//		JFrame mainFrame = GuiMain.getInstance();
//		mainFrame.pack();
//		mainFrame.setVisible(true);
		
//		FetchData f = FetchData.getInstance();
//		f.fetchAllCountries();
		
		IndicatorManager i = IndicatorManager.getInstance();
		i.getAllIndicators().forEach(indicator -> {
			System.out.println(indicator);
		});
	}
	
	

}
