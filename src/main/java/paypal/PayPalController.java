package paypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController

public class PayPalController {

    public PayPalClient payPalClient;
    @Autowired
    PayPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/make/payment/{price}")
    public Map<String, Object> makePayment(@PathVariable("price")String price){
    	System.out.println(price);
        return payPalClient.createPayment(price);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/complete/payment")
    public ModelAndView completePayment(@RequestParam("paymentId")String paymentId,@RequestParam("token")String token,@RequestParam("PayerID")String PayerID){
    	System.out.println(paymentId);
    	System.out.println(PayerID);
    	System.out.println(token);
     return payPalClient.completePayment(paymentId, PayerID);
    	
    }


}