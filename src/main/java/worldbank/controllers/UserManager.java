package worldbank.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import worldbank.models.User;

public class UserManager {
	private static UserManager instance;
	private final static String FILEPATH = "src/main/resources/credentials.json";

	public static UserManager getInstance() {
		if (instance == null)
			instance = new UserManager();

		return instance;
	}
	
	private UserManager() {
	}
	
	public boolean login(String username, String password) {
		String userJson;
		try {
			userJson = new String(Files.readAllBytes(Paths.get(FILEPATH)));
		} catch (IOException e) {
			userJson="";
			System.out.println("Unable to read Credentials file");
			e.printStackTrace();
		}
		System.out.println(userJson);
		Gson gson = new Gson();
		Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
		ArrayList<User> userArray = gson.fromJson(userJson, userListType); 
		for(User user : userArray) {
			System.out.println(user);
		}
		if (userArray.contains(new User(username, password))) {
		    return true;
		} 
		return false;
	}
}
