package worldbank.enums;

public enum ChartTypes {
	  PIECHART("Pie Chart"),
	  LINECHART("Line Chart"),
	  BARCHART("Bar CHart"),
	  SCATTERCHART("Scatter Chart"),
	  REPORT("Report");

	public final String label;
	
	ChartTypes(String label) {
		this.label = label;
	}
}
