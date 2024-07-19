//package com.click.account.controller;
//
//import com.click.account.domain.dto.request.AccountRequest;
//import com.click.account.service.AccountService;
//import com.click.account.service.AccountServiceImpl;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@WebMvcTest(AccountController.class)
//class AccountControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @MockBean
//    AccountServiceImpl accountService;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    void 계좌_생성() throws Exception {
//        // given
//        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
//        AccountRequest request = new AccountRequest(
//                "account",
//                "0123",
//                "텅장"
//        );
//
//        String content = new ObjectMapper().writeValueAsString(request);
//
//        // when
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
//                .header("userId", userId.toString())
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andDo(print());
//
//        // then
//        ArgumentCaptor<UUID> userIdCaptor = ArgumentCaptor.forClass(UUID.class);
//        ArgumentCaptor<AccountRequest> requestCaptor = ArgumentCaptor.forClass(AccountRequest.class);
//
//        Mockito.verify(accountService).saveAccount(userIdCaptor.capture(), requestCaptor.capture());
//
//        UUID capturedUserId = userIdCaptor.getValue();
//        AccountRequest capturedRequest = requestCaptor.getValue();
//
//        Assertions.assertEquals(userId, capturedUserId);
//        Assertions.assertEquals(request.accountName(), capturedRequest.accountName());
//    }
//
//    @Test
//    void 계좌_생성_실패() throws Exception {
//        // given
//        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
//        AccountRequest request = new AccountRequest(
//                "account",
//                "0123",
//                "텅장"
//        );
//
//        String content = new ObjectMapper().writeValueAsString(request);
//
//        // when
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
//                .header("userId", userId.toString())
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andDo(print());
//
//        // then
//        ArgumentCaptor<UUID> userIdCaptor = ArgumentCaptor.forClass(UUID.class);
//        ArgumentCaptor<AccountRequest> requestCaptor = ArgumentCaptor.forClass(AccountRequest.class);
//
//        Mockito.verify(accountService).saveAccount(userIdCaptor.capture(), requestCaptor.capture());
//
//        UUID capturedUserId = userIdCaptor.getValue();
//        AccountRequest capturedRequest = requestCaptor.getValue();
//
//        Assertions.assertEquals(userId, capturedUserId);
//        Assertions.assertEquals(request.accountName(), capturedRequest.accountName());
//    }
//
//    @Test
//    void 계좌_삭제_성공() throws Exception {
//        //given
//        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
//        String account = "416032747714";
//        AccountRequest request = new AccountRequest(
//                "account",
//                "0123",
//                "텅장"
//        );
//
//        String content = new ObjectMapper().writeValueAsString(request);
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts")
//                .header("userId", userId.toString())
//                .param("account", account)
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andDo(print());
//        //then
//        ArgumentCaptor<UUID> userIdCaptor = ArgumentCaptor.forClass(UUID.class);
//        ArgumentCaptor<AccountRequest> requestCaptor = ArgumentCaptor.forClass(AccountRequest.class);
//        ArgumentCaptor<String> accountCaptor = ArgumentCaptor.forClass(String.class);
//        Mockito.verify(accountService).deleteAccount(userIdCaptor.capture(), accountCaptor.capture());
//
//    }
//    @Test
//    void 계좌_삭제_실패() throws Exception {
//        //given
//        UUID userId = UUID.randomUUID();
//        String account = "416032747714";
//
//        //when
//        //then
//    }
//}