package worldbank;

import java.util.List;

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
	
	public List<Country> getAllCountries() {
		return fetcher.fetchAllCountries();
	}
}
