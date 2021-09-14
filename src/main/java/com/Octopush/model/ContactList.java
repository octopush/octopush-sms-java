package com.Octopush.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactList {
	private List<Contact> contacts;
	private String list_name;
	private List<String> phone_numbers;
}
