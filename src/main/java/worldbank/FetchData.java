package worldbank;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class FetchData {
	private static FetchData instance;
	private static final String COUNTRIES_URL = "http://api.worldbank.org/v2/country?format=json&per_page=299";
	

	public static FetchData getInstance() {
		if (instance == null)
			instance = new FetchData();

		return instance;
	}
	
	private FetchData() {
		
	}
	
	public List<Country> fetchAllCountries() {
		List<Country> countries = new ArrayList<>();
		try {
			URL url = new URL(COUNTRIES_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				for (int i = 0; i < sizeOfResults; i++) {
					Country country = new Country();
					country.setName(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString());
					country.setId(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString());
					countries.add(country);
				}
				System.out.println("fetched all countries");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return countries;
	}
}
