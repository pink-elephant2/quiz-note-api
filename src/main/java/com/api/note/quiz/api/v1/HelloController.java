package com.api.note.quiz.api.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld
 * TODO 後で消す
 */
@RestController
public class HelloController {

	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Hello, World!";
	}
}
