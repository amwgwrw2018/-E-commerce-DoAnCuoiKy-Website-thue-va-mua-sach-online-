package thymeleaf.leaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class controller {

	 @RequestMapping("/")
	  public ModelAndView index( ModelAndView model) {
	    model.setViewName("index");
	    model.addObject("message","Welcome to our <b>fantastic</b> grocery store!");
	    return model;
	  }
}
