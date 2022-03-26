package com.pyh.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pyh.exam.demo.interceptor.BeforeActionInterceptor;
import com.pyh.exam.demo.interceptor.NeedLoginInterceptor;

// 이 부분은 프로그램 실행 시 맨 처음 딱 한번만 실행
@Configuration // 설정 시, 스프링이 맨 처음 실행된 후 자동으로 여기를 가장 먼저 읽음
	public class MyWebMvcConfigurer implements WebMvcConfigurer {
		// beforeActionInterceptor 인터셉터 불러오기
		@Autowired // new하지 않고도 객체 생성
		BeforeActionInterceptor beforeActionInterceptor; // 이렇게만 선언하면 인터셉터만 있는 것

		// needLoginInterceptor 인터셉터 불러오기
		@Autowired
		NeedLoginInterceptor needLoginInterceptor;
				
		
		// 이 함수는 인터셉터를 적용하는 역할을 합니다.
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			// beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
			// 아래처럼 추가를 안하면 해당 인터셉터가 스프링부트에 추가되지 않음
			// 인터셉터한테 아래처럼 지령을 내림
	        registry.addInterceptor(beforeActionInterceptor) // beforeActionInterceptor라는 인터셉터 추가해라 
	                .addPathPatterns("/**") // (beforeActionInterceptor한테) 모든 요청에 대해서 인터셉터 실행해라
	                .excludePathPatterns("/resource/**") // 다만, resource/로 시작하는 모든 경로는 제외해라
	                .excludePathPatterns("/error"); // error 보여주는 것도 제외해라
	        
	     // needLoginInterceptor 인터셉터 적용
	        registry.addInterceptor(needLoginInterceptor)
            		.addPathPatterns("/usr/article/write") // 글 등록 페이지
            		.addPathPatterns("/usr/article/doWrite") // 글 등록을 처리해주는 페이지(눈에 안보임)
            		.addPathPatterns("/usr/article/modify") // 글 수정 페이지
            		.addPathPatterns("/usr/article/doModify") // 글 수정을 처리해주는 페이지(눈에 안보임)
            		.addPathPatterns("/usr/article/doDelete"); // 글 삭제를 처리해주는 페이지(눈에 안보임)
		}
	}

