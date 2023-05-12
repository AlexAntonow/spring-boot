package com.springboot.demo;

import com.springboot.demo.controller.Controller;
import com.springboot.demo.controller.UserController;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTests {

	@Autowired
	Controller controller;

	@Autowired
	UserController userController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(controller);
		Assertions.assertNotNull(userController);
	}

	@Test
	public void applicationContextTest(){
		Application.main(new String[]{});
	}

	@Test
	public void controllerTest(){
		assertThat(controller.Hello()).isEqualTo("Hello World!");
	}
}
