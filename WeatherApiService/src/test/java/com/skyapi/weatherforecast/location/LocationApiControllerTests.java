package com.skyapi.weatherforecast.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;


@WebMvcTest(LocationApiController.class)
public class LocationApiControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    LocationService service;

    private static final String END_POINT_PATH = "/v1/locations";

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        Location location = new Location();

        String bodyContent = mapper.writeValueAsString(location);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testAddShouldReturn201Created() throws Exception {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(service.add(location)).thenReturn(location);

        String bodyContent = mapper.writeValueAsString(location);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code",  is("NYC_USA")))
                .andExpect(header().string("Location", "/v1/locations/NYC_USA"))
                .andDo(print());
    }
}
