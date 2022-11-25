package worldbank.viewcontrollers;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javafx.scene.chart.Chart;
import worldbank.controllers.AnalysisManager;
import worldbank.controllers.CountryManager;
import worldbank.enums.ChartTypes;
import worldbank.exceptions.CalculationFailedException;
import worldbank.exceptions.DataNotAvailableException;
import worldbank.models.Country;
import worldbank.models.ViewData;
import worldbank.models.analyses.Analysis;
import java.util.stream.Collectors;

public class GuiMain extends JFrame{
	private static GuiMain instance;
	private static AnalysisManager analysisManager;
	private CountryManager countryManager;
	
	private List<List<ViewData>> viewData;
	private List<Chart> activeCharts;
	private String activeCountry;
	private Analysis activeAnalysis;
	private int fromYear;
	private int toYear;
	
    protected JPanel north;
    protected JPanel south;
    protected JPanel center;

    protected JComboBox<String> countriesList;
    protected JComboBox<String> fromList;
    protected JComboBox<String> toList;
    protected JComboBox<String> analysisList;
    protected JButton recalculateBtn;
    protected JButton removeChartBtn;
    protected JButton addChartBtn;

	public static GuiMain getInstance() {
		if (instance == null)
			instance = new GuiMain();

		return instance;
	}
	
	private GuiMain() {
		// Set window title
		super("Country Statistics");
		this.setup();
		this.addCountries();
		this.addYears();
		
		this.addViewSelect();
		this.addAnalysisSelect();
		this.addRecalculate();
		
		this.addCharts();
		this.initialize();

		getContentPane().add(this.north, BorderLayout.NORTH);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(center, BorderLayout.WEST);
	}
	
	private void setup() {
		analysisManager = AnalysisManager.getInstance();
		countryManager = CountryManager.getInstance();
		activeCharts = new ArrayList<>();
		
        this.north = new JPanel();
        this.south = new JPanel();
        this.center = new JPanel();        
	}
	
	private void addCountries() {
		JLabel chooseCountryLabel = new JLabel("Choose a country: ");
		Vector<String> countriesNames = this.loadCountries();
		countriesNames.sort(null);
		this.countriesList = new JComboBox<String>(countriesNames);
		
		north.add(chooseCountryLabel);
		north.add(countriesList);
	}
	
	private Vector<String> loadCountries() {
		Vector<String> countriesVector = new Vector<>();;
		List<Country> countriesList;
		try {
			countriesList = countryManager.getAllCountries();
			countriesList.stream().forEach(c -> {
				countriesVector.add(c.getName());
			});
		} catch (DataNotAvailableException e) {
			e.printStackTrace();
		}
		return countriesVector;
	}
	
	private void addYears() {
		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		Vector<String> years = new Vector<String>();
		for (int i = 2021; i >= 2010; i--) {
			years.add("" + i);
		}
		fromList = new JComboBox<String>(years);
		toList = new JComboBox<String>(years);

		this.north.add(from);
		this.north.add(fromList);
		this.north.add(to);
		this.north.add(toList);
	}
	
	private void addRecalculate() {
		this.recalculateBtn = new JButton("Recalculate");
		recalculateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performCalculation();
			}
		});
		south.add(recalculateBtn);

	}
	
	private void addViewSelect() {
		JLabel viewsLabel = new JLabel("Available Views: ");

		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("Pie Chart");
		viewsNames.add("Line Chart");
		viewsNames.add("Bar Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Report");
		JComboBox<String> viewsList = new JComboBox<String>(viewsNames);
		addChartBtn = new JButton("+");
		removeChartBtn= new JButton("-");
		south.add(viewsLabel);
		south.add(viewsList);
		addChartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addChart();
			}
		});
		removeChartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeChart();
			}
		});
		south.add(addChartBtn);
		south.add(removeChartBtn);
	}
	
	private void addAnalysisSelect() {
		JLabel methodLabel = new JLabel("        Choose analysis method: ");		
		Vector<String> methodsNames = new Vector<>();;
		List<Analysis> analyses;
		analyses = analysisManager.getAnaleses();
		analyses.stream().forEach(c -> {
			methodsNames.add(c.getName());
		});
		analysisList = new JComboBox<String>(methodsNames);
		south.add(methodLabel);
		south.add(analysisList);
	}
	
	private void addCharts() {
		center.setLayout(new GridLayout(2, 0));
	}

	private void performCalculation() {
		System.out.println("Performing Calculation");
		updateActiveFields();
		removeAllCharts();
		updateLineChart();
		updateTimeSeries();
		updateBar();
		updatePie();
		updateScatter();
		updateReport();
		refreshCharts();
	}
	
	private void removeAllCharts() {
		center.removeAll();
	}
	private void refreshCharts() {
		center.validate();
		center.repaint();
	}
	
	private void updateActiveFields() {
		this.activeCountry = String.valueOf(countriesList.getSelectedItem());
		this.activeAnalysis = analysisManager.getAnalysisByName(String.valueOf(analysisList.getSelectedItem()));
		this.fromYear = Integer.parseInt(String.valueOf(fromList.getSelectedItem()));
		this.toYear = Integer.parseInt(String.valueOf(toList.getSelectedItem()));
		try {
			this.viewData = analysisManager.calculateAnalysis(activeCountry, activeAnalysis.getName(), fromYear, toYear);
		} catch (DataNotAvailableException | CalculationFailedException e) {
			JOptionPane.showMessageDialog(null, "Sorry the data is not available for selected country and years");
			e.printStackTrace();
		}
	}
	
	private void updateLineChart() {
		int numberOfLines = this.viewData.size();
		XYSeriesCollection dataset = new XYSeriesCollection();
		for(int i = 0; i < numberOfLines; i++) {
			String indicatorName = this.activeAnalysis.getActiveIndicators().get(i).getName();
			XYSeries series = new XYSeries(indicatorName);
			this.viewData.get(i).stream().forEach(v -> series.add(v.getYear(), v.getValue()));
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYLineChart(activeAnalysis.getName(), "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(
				new TextTitle(activeAnalysis.getName(), new Font("Serif", java.awt.Font.BOLD, 18)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		center.add(chartPanel);
	}
	private void updateTimeSeries() {
		int numberOfLines = this.viewData.size();
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(int i = 0; i < numberOfLines; i++) {
			String indicatorName = this.activeAnalysis.getActiveIndicators().get(i).getName();
			TimeSeries series = new TimeSeries(indicatorName);
			this.viewData.get(i).stream().forEach(v -> series.add(new Year(v.getYear()), v.getValue()));
			dataset.addSeries(series);
		}

		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);

		JFreeChart chart = new JFreeChart(activeAnalysis.getName(),
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		center.add(chartPanel);

	}
	private void updateBar() {
		int numberOfLines = this.viewData.size();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < numberOfLines; i++) {
			String indicatorName = this.activeAnalysis.getActiveIndicators().get(i).getName();
			this.viewData.get(i).stream().forEach(v -> dataset.setValue(v.getValue(), indicatorName, String.valueOf(v.getYear())));
		}
		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();
		BarRenderer barrenderer2 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));


		plot.mapDatasetToRangeAxis(0, 0);

		JFreeChart barChart = new JFreeChart(activeAnalysis.getName(),
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		center.add(chartPanel);
	}
	private void updatePie() {
		int numberOfLines = this.viewData.size();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < numberOfLines; i++) {
			String indicatorName = this.activeAnalysis.getActiveIndicators().get(i).getName();
			this.viewData.get(i).stream().forEach(v -> dataset.addValue(v.getValue(), String.valueOf(v.getYear()), indicatorName));
		}
		
		JFreeChart pieChart = ChartFactory.createMultiplePieChart(activeAnalysis.getName(), dataset,
				TableOrder.BY_COLUMN, true, true, false);

		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		center.add(chartPanel);
	}
	private void updateScatter() {
		int numberOfLines = this.viewData.size();
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(int i = 0; i < numberOfLines; i++) {
			String indicatorName = this.activeAnalysis.getActiveIndicators().get(i).getName();
			TimeSeries series = new TimeSeries(indicatorName);
			this.viewData.get(i).stream().forEach(v -> series.add(new Year(v.getYear()), v.getValue()));
			dataset.addSeries(series);
		}

		XYPlot plot = new XYPlot();
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
		XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		plot.mapDatasetToRangeAxis(0, 0);
		JFreeChart scatterChart = new JFreeChart(activeAnalysis.getName(),
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		center.add(chartPanel);
	}
	private void updateReport() {
		JTextArea report = new JTextArea();
		report.setEditable(false);
		report.setPreferredSize(new Dimension(400, 300));
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		
		int numberOfLines = this.viewData.size();
		String reportMessage =activeAnalysis.getName() + "\n" + "==============================\n";
		for(int i = 0; i < numberOfLines; i++) {
			String indicatorName = this.activeAnalysis.getActiveIndicators().get(i).getName();
			reportMessage += this.viewData.get(i).stream().map(v -> String.format("Year %s: \n %s => %s \n", v.getYear(), indicatorName, v.getValue())).collect(Collectors.joining(", "));
		}

		report.setText(reportMessage);
		JScrollPane outputScrollPane = new JScrollPane(report);
		center.add(outputScrollPane);
	}
	private void initialize() {
		this.countriesList.setSelectedItem("United States");
		this.fromList.setSelectedItem("2014");
		this.toList.setSelectedItem("2017");
		this.analysisList.setSelectedItem("PM2.5 air pollution vs Forest area");
		this.performCalculation();
	}
	
	private void removeChart() {
	}
	private void addChart() {
	}
}
