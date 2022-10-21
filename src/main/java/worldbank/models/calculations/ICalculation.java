package worldbank.models.calculations;

import java.util.List;

public interface ICalculation<T>{
	public List<Double> calculate(T Objects);
}
