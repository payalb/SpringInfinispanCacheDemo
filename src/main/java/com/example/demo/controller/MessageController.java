package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Message;
import com.example.demo.service.MessageService;

@Controller
public class MessageController {

	@Autowired MessageService service;
	
	@GetMapping(path="/messages", produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public Collection<Message> getMessages() throws InterruptedException{
		return service.getMessages();
	}
}
