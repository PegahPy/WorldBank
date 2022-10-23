package worldbank.models.calculations;

import java.util.ArrayList;
import java.util.List;

import worldbank.models.ViewData;

public class NoProcess implements ICalculation<List<Double>> {

	@Override
	public List<ViewData> getCalculateMethod(List<Double> rawData, int toYear) {
		List<ViewData> result = new ArrayList<>();
		int sizeOfResults = rawData.size();
		for (int i = 0; i < sizeOfResults; i++) {
			double r = (double) (rawData.get(i));
			result.add(new ViewData(toYear, r));
		}
		return result;
	}


}
