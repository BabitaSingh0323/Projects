package com.incture.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/security")
public class SecurityController {
	
	@Autowired
	WebSecurity security;
	
	@RequestMapping(value="/token",method=RequestMethod.GET)
	public String getToken(){
		System.out.println("controller");
		return security.getAssertion(SecurityConstants.CLIENT_ID, SecurityConstants.USER_ID, SecurityConstants.TOKEN_URL, SecurityConstants.PRIVATE_KEY);
		
	}

}
