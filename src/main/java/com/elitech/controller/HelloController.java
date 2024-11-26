package com.elitech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
	@GetMapping("welcome")
public String sayhello()
{
return "hello spring";	

}
}
