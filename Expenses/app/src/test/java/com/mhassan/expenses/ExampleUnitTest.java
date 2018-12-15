package com.mhassan.expenses;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
	
	@Test
    public void submitValidData() {
		
		assertNotNull(solo.findViewById("com.mhassan.expenses.category"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.amount"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.date"));
		solo.sendKey(findViewById("com.mhassan.expenses.date"));
        
    }
	
	@Test
    public void verifyNewExpenceHaveData() {
		solo.sendKey(findViewById("com.mhassan.expenses.item"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.category"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.amount"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.date"));
    }
	
	@Test
    public void wrongTestCase() {
		assertNotNull(solo.findViewById("com.mhassan.expenses.category"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.amount"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.date"));
    }
	
	@Test
    public void wrongTestCase() {
		solo.sendKey(findViewById("com.mhassan.expenses.item"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.category"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.amount"));
		assertNotNull(solo.findViewById("com.mhassan.expenses.date"));
    }
}