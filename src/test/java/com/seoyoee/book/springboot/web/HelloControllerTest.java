package com.seoyoee.book.springboot.web;

import org.junit.jupiter.api.Test; // Junit5에서 junit -> junit.jupiter.api
import org.junit.jupiter.api.extension.ExtendWith; // Junit5에서 RunWith -> ExtendWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;  // Junit5에서 SpringRunner -> SpringExtension
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) // 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 함
@WebMvcTest(controllers = HelloController.class) // Web에 집중할 수 있는 어노테이션
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈을 주입받음
    private MockMvc mvc; // 스프링 MVC의 시작점으로 웹 API를 테스트 할 때 사용

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 함
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증 (HTTP Header Status가 200인지 아닌지)
                .andExpect(content().string(hello)); // mvc.perform의 결과를 검증 (응답 본문의 내용)
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // API 테스트시 사용될 요청 파리미터 설정
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // json 응답값을 필드별로 검증할 수 있는 메소드
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
