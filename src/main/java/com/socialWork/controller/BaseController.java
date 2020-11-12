package com.socialWork.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialWork.auth.repository.UserRepository;

public class BaseController {
	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected ObjectMapper objectMapper;
}
