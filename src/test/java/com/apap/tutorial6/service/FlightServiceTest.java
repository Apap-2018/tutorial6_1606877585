package com.apap.tutorial6.service;

import com.apap.tutorial6.model.FlightModel;
import com.apap.tutorial6.repository.FlightDb;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class FlightServiceTest {
    @Autowired
    private FlightService flightService;

    @MockBean
    private FlightDb flightDb;

    @TestConfiguration
    static class FlightServiceTestContextConfiguration{
        @Bean
        public FlightService flightService(){
            return new FlightServiceImpl();
        }
    }

    @Test
    public void whenValidFlightNumber_thenFlightShouldBeFound(){
        //given
        FlightModel flightModel = new FlightModel();
        flightModel.setFlightNumber("X550");
        flightModel.setOrigin("Depok");
        flightModel.setDestination("Bekasi");
        flightModel.setTime(new Date(new java.util.Date().getTime()));
        Optional<FlightModel> flight = Optional.of(flightModel);
        Mockito.when(flightDb.findByFlightNumber(flight.get().getFlightNumber())).thenReturn(flight);

        //When
        Optional<FlightModel> found = flightService.getFlightDetailByFlightNumber(flight.get().getFlightNumber());

        //Then
        Assert.assertThat(found,Matchers.notNullValue());
        Assert.assertThat(found.get().getFlightNumber(),Matchers.equalTo(flightModel.getFlightNumber()));

    }
}
