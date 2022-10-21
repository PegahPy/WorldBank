package worldbank.models.calculations;

import java.util.ArrayList;
import java.util.List;

public class AnnualPercentageChange implements ICalculation<List<Double>> {

	@Override
	public List<Double> calculate(List<Double> data) {
		List<Double> resault = new ArrayList<>();
		int sizeOfResults = data.size();
		for (int i = 0; i < sizeOfResults - 1; i++) {
			Double r = (data.get(i) - data.get(i+1))/data.get(i+1)*100;
			resault.add(r);
		}
		return resault;
	}

}
