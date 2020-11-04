


  package bookmarket;

          import bookmarket.external.Payment;
          import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
          import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
          import org.springframework.beans.factory.annotation.Autowired;
          import org.springframework.web.bind.annotation.GetMapping;
          import org.springframework.web.bind.annotation.RequestParam;
          import org.springframework.web.bind.annotation.RestController;

          import java.util.Optional;

@RestController
public class OrderController {




 @GetMapping("/circuitBreaker")
 @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"), //5초만에 fallback 호출
         @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
         @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
         @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),//n번 개까지 정상 동작
         @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")//10초동안 circuitBreaker 동작
 })
 public String circuitBreakerTest(@RequestParam String isYn) throws InterruptedException {

  if (isYn.equals("Y")) {
   System.out.println("@@@ CircuitBreaker!!!");
   Thread.sleep(10000);
   //throw new RuntimeException("CircuitBreaker!!!");
  }

  System.out.println("$$$ SUCCESS!!!");
  return " SUCCESS!!!";
 }


 private String fallback(String isYn) {
  System.out.println("### fallback!!!");
  return "CircuitBreaker!!!";
 }

}