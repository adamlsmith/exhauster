package com.pg.exhauster;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.*;

/**
 * Unit test for Exhauster App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    @Test
    public void testGetMemoryUsage(){
	    App a = new App();
	    String t = a.getMemoryUsage(0.5f);
	    Assert.assertNotNull(t);
	    Assert.assertTrue(t.startsWith("Heap"));
    }

    @Test
    public void testGetUsed(){
	App a = new App();
	float f = a.getUsed();
	Assert.assertTrue(f > 0);
	Assert.assertNotNull(f);
    }

    @Test
    public void testGetCommitted(){
	App a = new App();
	float f = a.getCommitted();
	Assert.assertTrue(f > 0);
    }


}
