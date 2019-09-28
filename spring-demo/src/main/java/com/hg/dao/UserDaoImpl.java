package com.hg.dao;

import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {
	public void query(){
		System.out.println("======query=========UserDaoImpl");
	}
}
