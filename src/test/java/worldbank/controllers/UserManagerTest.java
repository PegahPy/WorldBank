package worldbank.controllers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserManagerTest {
	
	private String USERNAME_VALID = "Pegah";
	private String PASSWORD_VALID = "Pegah123";
	
	private String USERNAME_UNVALID = "Pegahh";
	private String PASSWORD_UNVALID = "Pegah1234";

	private String USERNAME_UNVALID_ECTRASPACE = "Pegah  ";

	private UserManager userManager = UserManager.getInstance();;
	
	@Test
	void testLogin_ValidUserValidPassword_Success() {
		assertEquals(true, userManager.login(USERNAME_VALID, PASSWORD_VALID));
	}
	
	@Test
	void testLogin_UnValidUserValidPassword_Fail() {
		assertEquals(false, userManager.login(USERNAME_UNVALID, PASSWORD_VALID));
	}
	
	@Test
	void testLogin_ValidUserUnValidPassword_Fail() {
		assertEquals(false, userManager.login(USERNAME_VALID, PASSWORD_UNVALID));
	}
	
	@Test
	void testLogin_ExtraSpaceUsernameValidPassword_Fail() {
		assertEquals(false, userManager.login(USERNAME_UNVALID_ECTRASPACE, PASSWORD_VALID));
	}

}
