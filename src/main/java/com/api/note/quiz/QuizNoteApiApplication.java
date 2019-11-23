package com.api.note.quiz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * クイズ手帳API
 */
@SpringBootApplication
@MapperScan(basePackages = "com.api.note.quiz.repository")
public class QuizNoteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizNoteApiApplication.class, args);
	}
}
