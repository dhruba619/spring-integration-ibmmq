package com.samplejmsapp.activemq.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldAmqService {
	public void processMsg(String msg) {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("*************"+ msg + " as of "+sdf.format(date)+" ***********  " );
	}

}
