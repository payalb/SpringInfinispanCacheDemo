package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import com.example.demo.dto.Message;

@Repository
@ApplicationScope
public class MessageDao {
	
	Map<Integer, Message> db= new HashMap<>();
	
	{
		db.put(1, Message.builder().id(1).message("Message1").build());
		db.put(2, Message.builder().id(2).message("Message2").build());
	}
	
	public Collection<Message> getMessages() throws InterruptedException{
		Thread.sleep(5000);
		System.out.println("db");
		return new ArrayList<Message>(db.values());
	}

	public void addMessage(Message m) {
		db.put(m.getId(), m);
	}
}
