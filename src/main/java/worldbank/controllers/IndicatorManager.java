package worldbank.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Data;
import worldbank.exceptions.IndicatorNotFoundException;
import worldbank.models.Indicator;

@Data
public class IndicatorManager {
	private static IndicatorManager instance;
	private final static String FILEPATH = "src/main/resources/indicators.json";
	private List<Indicator> indicators;

	public static IndicatorManager getInstance() {
		if (instance == null)
			instance = new IndicatorManager();

		return instance;
	}
	
	private IndicatorManager() {
		indicators = new ArrayList<>();
		String indicatorsJson;
		try {
			indicatorsJson = new String(Files.readAllBytes(Paths.get(FILEPATH)));
		} catch (IOException e) {
			indicatorsJson="";
			System.out.println("Unable to read Indicator files");
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Type indicatorsListType = new TypeToken<ArrayList<Indicator>>(){}.getType();
		ArrayList<Indicator> indicatorsArray = gson.fromJson(indicatorsJson, indicatorsListType); 
		for(Indicator indicator : indicatorsArray) {
			indicators.add(indicator);
		}
		System.out.println("Indicators Loaded");
	}
	
	public List<Indicator> getAllIndicators() {
		return indicators;
	}
	
	public Indicator getIndicatorByName(String name) throws IndicatorNotFoundException {
		return indicators.stream().filter(i -> i.getName().equals(name)).findFirst().orElseThrow(IndicatorNotFoundException::new);
	}
	public static void main(String[] args) {
		
		IndicatorManager am = IndicatorManager.getInstance();
		try {
			am.getAllIndicators().forEach(result -> {
				System.out.println(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
