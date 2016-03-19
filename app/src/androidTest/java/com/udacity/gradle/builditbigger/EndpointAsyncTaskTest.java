package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

/**
 * Created by ivan on 3/18/16.
 */
public class EndpointAsyncTaskTest extends AndroidTestCase {

    public void testJokeTask(){
        try {
            EndpointAsyncTask task = new EndpointAsyncTask();
            task.execute();
            String joke = task.get();

            assertNotNull(joke);
            assertTrue(joke.length() > 0);
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

}