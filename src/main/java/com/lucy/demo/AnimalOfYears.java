package com.lucy.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cchang3 on 11/14/18.
 */
@RestController
@EnableRetry
public class AnimalOfYears {

    @Autowired
    RetryTemplate retryTemplate;

    @GetMapping(path="/animal")
    public String getAnimal(){
        return "Ox";
    }

    @GetMapping(path="/")
    @Retryable(value={NullPointerException.class}, maxAttempts = 2)
    public String getStuff(){
        System.out.println("inside getstuff method");
        String s = null;
        s.charAt(0);
        return "Ox";
    }

    @Recover
    public String recoverFromNPE(NullPointerException e){
        System.out.println("inside recover from NPE method");
        return "inside recover method from NPE";
    }


    public String getStuffRetryTemplate(){
        System.out.println("%%%%%%%%%%inside getstuff method");
        String s = null;
        s.charAt(0);
        return "Ox";
    }

    @GetMapping(path="/retryTemplate")
    public String retryGetStuffRetryTemplate() throws Exception{
            retryTemplate.execute(new RetryCallback<String, Exception>() {
                public String doWithRetry(RetryContext context) {
                    getStuffRetryTemplate();
                    return "finished successfully in the doWithRetry Method";
                }
            }, new RecoveryCallback<String>() {
                    public String recover(RetryContext context){
                        System.out.println("inside recover method ");
                        context.getLastThrowable().printStackTrace();
                        return null;
                    }
                                  }
                    );
            return "complete the retryGetStuffRetryTemplate";
    }
}
