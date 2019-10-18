package com.ripper.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ripper.config.AppConfig;
import com.ripper.config.SecurityConfig;
import com.ripper.entity.Role;
import com.ripper.entity.User;
import com.ripper.service.UserService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class, SecurityConfig.class })
@WebAppConfiguration
public class UserServiceTest {

	@Resource
	private UserService userService;

	@Test
	public void getUserByLoginTest() {
		User testUser = userService.getUserByLogin("jsmith");

		assertNotNull(testUser);
		assertEquals("Smith", testUser.getLastName());
	}

	@Test
	public void getAllUsersTest() {
		List<User> testUsers = userService.getAllUsers();

		assertNotNull(testUsers);
		assertEquals(testUsers.size(), 3);
		assertEquals("jsmith", testUsers.get(0).getLogin());
		assertEquals("jdoe", testUsers.get(1).getLogin());
	}

	@Test
	public void createUserTest() {
		User newUser = new User("Vlad", "Dracula", "alucard", "qwerty", Role.ROLE_ADMIN, true);
		userService.saveUser(newUser);

		User testUser = userService.getUserByLogin("alucard");

		assertNotNull(testUser);
		assertEquals("Dracula", testUser.getLastName());
	}

	@Test
	public void updateUserTest() {
		User userToUpdate = userService.getUserByLogin("admin");
		userToUpdate.setLastName("Robertson");

		userService.saveUser(userToUpdate);

		User testUser = userService.getUserByLogin("admin");

		assertNotNull(testUser);
		assertEquals("Robertson", testUser.getLastName());
	}

	@Test
	public void deleteUserTest() {

		userService.deleteUser("admin");

		User testUser = userService.getUserByLogin("admin");

		assertNull(testUser);

	}

}
