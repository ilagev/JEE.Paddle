package api;

import org.junit.After;
import org.junit.Test;

public class TrainingResourceFunctionalTesting {
    
    @Test
    public void testCreateValidTraining() {
        
    }
    
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
