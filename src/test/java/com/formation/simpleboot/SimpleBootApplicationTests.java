package com.formation.simpleboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.formation.simpleboot.service.Calculator;

@SpringBootTest
class SimpleBootApplicationTests {
	
	private Calculator calculator = new Calculator();

	@Test
	void testSum() {
		assertEquals(6, calculator.sum(2, 4));
	}

}
