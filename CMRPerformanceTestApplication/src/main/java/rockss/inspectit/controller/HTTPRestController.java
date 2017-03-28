package rockss.inspectit.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HTTPRestController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}