package worldbank.models.calculations;

import java.util.ArrayList;
import java.util.List;

import worldbank.models.ViewData;

public class AnnualPercentageChange implements ICalculation<List<Double>> {

	@Override
	public List<ViewData> getCalculateMethod(List<Double> data, int toYear) {
		List<ViewData> resault = new ArrayList<>();
		int sizeOfResults = data.size();
		for (int i = 0; i < sizeOfResults - 1; i++) {
			Double r = (data.get(i) - data.get(i+1))/data.get(i+1)*100;
			resault.add(new ViewData(toYear - (i), r));
		}
		return resault;
	}

}
