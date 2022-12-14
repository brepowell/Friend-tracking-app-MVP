package edu.uwb.meetme.usermanagement;

import edu.uwb.meetme.models.User;
import edu.uwb.meetme.models.ResponseMessage;
import edu.uwb.meetme.resources.UserController;
import edu.uwb.meetme.resources.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserManagementTests {

	@LocalServerPort
	private int port;

	@Autowired
	private UserController userController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

	@Test
	public void signupShouldSucceed() {
		String endpoint = "http://localhost:" + port + "/user-management/api/v1/member/signup";
		String memberEmail = "testuser@gymapp.com";

		// GIVEN
		User user = new User();
		user.setEmail(memberEmail);
		user.setPassword("SuperSecure");

		// WHEN
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> request = new HttpEntity<>(user, headers);
		ResponseEntity<ResponseMessage> response = this.restTemplate.postForEntity(endpoint, request, ResponseMessage.class);

		// THEN
		String responseString = response.getBody().getMessage();
		Assertions.assertEquals("Your profile was successfully created with set-slot-sweat.", responseString);

		// Clean up
		User addedUser = userService.getUser(memberEmail);
		userService.deleteUser(addedUser.getId());
	}

	@Test
	public void signupWithoutEmailShouldFail() {
		String endpoint = "http://localhost:" + port + "/user-management/api/v1/member/signup";

		// GIVEN
		User user = new User();
		user.setPassword("SuperSecure");

		// WHEN
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<User> response = this.restTemplate.postForEntity(endpoint, request, User.class);

		// THEN
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void signupWithoutPasswordShouldFail() {
		String endpoint = "http://localhost:" + port + "/user-management/api/v1/member/signup";

		// GIVEN
		User user = new User();
		user.setEmail("testuser@gymapp.com");

		// WHEN
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<User> response = this.restTemplate.postForEntity(endpoint, request, User.class);

		// THEN
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void signupTwiceWithSameEmailShouldFail() {
		String endpoint = "http://localhost:" + port + "/user-management/api/v1/member/signup";
		String memberEmail = "testuser@gymapp.com";

		// GIVEN
		User user = new User();
		user.setEmail(memberEmail);
		user.setPassword("SuperSecure");

		// WHEN
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> request = new HttpEntity<>(user, headers);
		ResponseEntity<ResponseMessage> goodResponse = this.restTemplate.postForEntity(endpoint, request, ResponseMessage.class);

		// AND: first succeeds
		String goodResponseString = goodResponse.getBody().getMessage();
		Assertions.assertEquals("Your profile was successfully created with set-slot-sweat.", goodResponseString);

		// THEN: Second attempt to signup with same email should fail
		User doubleUser = new User();
		user.setEmail("testuser@gymapp.com");
		user.setPassword("SuperSecure");
		request = new HttpEntity<>(doubleUser, headers);
		ResponseEntity<String> badResponse = this.restTemplate.postForEntity(endpoint, request, String.class);

		// Failure returns a BAD_REQUEST
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, badResponse.getStatusCode());

		// Clean up database
		User addedUser = userService.getUser(memberEmail);
		userService.deleteUser(addedUser.getId());
	}

}


