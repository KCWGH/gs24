package com.gs24.website.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gs24.website.config.RootConfig;
<<<<<<< HEAD
import com.gs24.website.persistence.PreorderMapper;
import com.gs24.website.task.ImageCheckTask;
=======
>>>>>>> d2fd1ccc65244a963f2295f1ab0f15ac2afca41e
import com.gs24.website.task.PreorderCheckTask;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@WebAppConfiguration
@Log4j
public class imageTaskTest {
	
//	@Autowired
//	private ImageCheckTask imageCheckTask;
	
	@Autowired
	private PreorderCheckTask checkPreorderTask;
	
//	@Autowired
//	private PreorderMapper preorderMapper;
	
	@Test
	public void mapperTest() {
		a();
	}
	
	public void a() {
		checkPreorderTask.Task();
	}
}
