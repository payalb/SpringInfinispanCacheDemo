package com.example.demo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message implements Serializable {

	private static final long serialVersionUID = -7549459520732561554L;
	
	private int id;
	private String message;
}
