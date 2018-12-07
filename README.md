# spring-retry-example
Example for spring retry
https://github.com/spring-projects/spring-retry

This project is a simple springboot application to demo the spring-retry feature. 
Run the DemoApplication to start the springboot server

Run http://localhost:8080/
to test the retryable annotation 

Run http://localhost:8080/retryTemplate
to test the retry template feature

A few lession learnt for spring-retry. 
 * Spring-retry is based on Spring AOP, thus retryable method will only work on public method, not private method. 
 * It is not possible to use spring cloud config to configure the maxAttempts annotation value for spring-retry. 
  The reason is that annotation value must be known at compile time.
  However spring cloud config won't retrieve the config value until run time. 
 * @Retryable and @Recover annotation can be applied to interface level, or method level inside interface. 
   They can also be applied for class level and method level inside class. 
 * To write unit test, you need to initialize the retryable bean by spring. 
  To do that, you can autowire the interface level of the retryable class. 
  Then create a retryConfig to enable retry for the implementation class. 

```
//For the junit test,make sure to run with spring.  
@RunWith(SpringRunner.class)
@SpringBootTest(classes = (RemoteServiceImplTest.RetryConfig.class})
public class REmoteServiceImplTest{

//This is the interface that implements the retryable annotation
@Autowired
private RemoteService remoteService;

//This will make remoteServiceImpl a proxy class since spring retry is based on spring AOP
@Configuration
@EnableRetry
@Import({remoteServiceImpl.class})
public static class RetryConfig{}

//Test Method

}
```
 However this is testing spring retry annotation. 
 You can write this for ease of testing so you don't need to test in browser.
 But not necessarily need to write this as required unit test. 
