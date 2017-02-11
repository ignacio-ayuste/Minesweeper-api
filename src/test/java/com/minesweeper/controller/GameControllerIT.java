package com.minesweeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.model.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-context-test.xml")
@WebAppConfiguration
public class GameControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void startGame_whenMockMVC_thenVerifyResponse() throws Exception {
        createGame();
    }

    private Game createGame() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/game/create"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());

        ObjectMapper mapper = new ObjectMapper();

        return  mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Game.class);
    }

    @Test
    public void continueGame_whenMockMVC_thenVerifyResponse() throws Exception {
        Game game = createGame();

        MvcResult mvcResult = this.mockMvc.perform(get("/game/"+ game.getId() +"/select?row=1&col=1"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

}