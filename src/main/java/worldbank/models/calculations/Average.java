package worldbank.models.calculations;

import java.util.ArrayList;
import java.util.List;

import worldbank.models.ViewData;

public class Average implements ICalculation<List<Double>> {

	@Override
	public List<ViewData> getCalculateMethod(List<Double> rawData, int toYear) {
		List<ViewData> result = new ArrayList<>();
		int sizeOfResults = rawData.size();
		double r = 0;
		for (int i = 0; i < sizeOfResults; i++) {
			r += (double) (rawData.get(i));
		}
		result.add(new ViewData(toYear, r/sizeOfResults));
		return result;
	}

}
