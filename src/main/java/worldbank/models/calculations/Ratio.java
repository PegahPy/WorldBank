package worldbank.models.calculations;

import java.util.ArrayList;
import java.util.List;

import worldbank.models.ViewData;

public class Ratio implements ICalculation<List<List<Double>>> {

	@Override
	public List<ViewData> getCalculateMethod(List<List<Double>> lists, int toYear) {
		List<Double> first = lists.get(0);
		List<Double> second = lists.get(1);
		List<ViewData> result = new ArrayList<>();
		int sizeOfResults = first.size();
		for (int i = 0; i < sizeOfResults; i++) {
			Double r = (double) (first.get(i) / second.get(i));
			result.add(new ViewData(toYear - i, r));
		}
		return result;
	}

}
