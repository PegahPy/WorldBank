package worldbank.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import worldbank.models.User;
import worldbank.viewcontrollers.GuiLogin;

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
		if (userArray.contains(new User(username, password))) {
			System.out.println("Login Successful");
		    return true;
		} 
		System.out.println("Login failed");
		return false;
	}
	
	public static void main(String[] args) {
		UserManager u = UserManager.getInstance();
		u.login("Pegah", "Pegah123");
	}
}
