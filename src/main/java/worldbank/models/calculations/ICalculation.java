package worldbank.models.calculations;

import java.util.List;

import worldbank.models.ViewData;

public interface ICalculation<T>{
	public List<ViewData> getCalculateMethod(T Objects, int toYear);
}
