package worldbank.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import worldbank.models.Country;
import worldbank.models.Indicator;

public class FetchData {
	private static FetchData instance;
	private static final String COUNTRIES_URL = "http://api.worldbank.org/v2/country?format=json&per_page=299";
	private static final String BASE_URL = "http://api.worldbank.org/v2/country/%s/indicator/%s?date=%d:%d&format=json";


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
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				for (int i = 0; i < sizeOfResults; i++) {
					Country country = Country.builder()
							.name(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString())
							.id(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString())
							.build();
					countries.add(country);
				}
				System.out.println("fetched all countries");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return countries;
	}
	
	public List<Double> fetchIndicatorResaults(Country country, Indicator indicator ,int fromYear, int toYear){
		List<Double> resaults = new ArrayList<>();
		try {
			URL url = new URL(String.format(BASE_URL, country.getId(), indicator.getCode(), fromYear, toYear));
			System.out.println(String.format("Fetching %s for country %s With %s", indicator.getCode(), country.getId(), url));
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
					resaults.add(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble());
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return resaults;
	}
}
