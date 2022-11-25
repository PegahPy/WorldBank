package worldbank.controllers;

import java.util.List;

import worldbank.exceptions.DataNotAvailableException;
import worldbank.models.Country;

public class CountryManager {
	private static CountryManager instance;
	
	private FetchData fetcher;

	public static CountryManager getInstance() {
		if (instance == null)
			instance = new CountryManager();

		return instance;
	}
	
	private CountryManager() {
		fetcher = FetchData.getInstance();
	}
	
	public List<Country> getAllCountries() throws DataNotAvailableException {
		return fetcher.fetchAllCountries();
	}
	
	public static void main(String[] args) {
		CountryManager c = CountryManager.getInstance();
		try {
			c.getAllCountries().forEach(result -> {
				System.out.println(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
