package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MessageDao;
import com.example.demo.dto.Message;

@Service
public class MessageService {

	@Autowired
	MessageDao dao;

	@Cacheable("local")
	public Collection<Message> getMessages() throws InterruptedException {
		return dao.getMessages();
	}
}
