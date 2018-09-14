package com.vibhor.shorten;

import com.vibhor.shorten.Model.ShortenUrl;
import com.vibhor.shorten.Service.StatisticsService;
import com.vibhor.shorten.Service.UrlConverterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ShortenApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UrlConverterService urlConverterService;

	@MockBean
	private StatisticsService statisticsService;


	@Test
	public void shortenUrl_tests() throws Exception{

		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/shorten")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc
				.perform(request)
				.andExpect(status().is(400))
				.andReturn();

		request = MockMvcRequestBuilders
				.post("/api/shorten")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createUrlinJson("https://www.google.com/abcd12345678/90"))
				.accept(MediaType.APPLICATION_JSON);
		result = mockMvc
				.perform(request)
				.andExpect(status().is(400))
				.andReturn();

		request = MockMvcRequestBuilders
				.post("/api/shorten")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createUrlinJson("123456789"))
				.accept(MediaType.APPLICATION_JSON);

		result = mockMvc
				.perform(request)
				.andExpect(status().is(400))
				.andExpect(content().json("Invalid Url"))
				.andReturn();
	}

	@Test
	public void convertUrl_tests() throws Exception{

		when(urlConverterService.getLongURLFromID("a")).thenReturn("https://www.google.com");

		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/a")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc
				.perform(request)
				.andExpect(status().is(302))
				.andReturn();
	}

	@Test
	public void statistics_tests() throws Exception{

		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/statistics")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc
				.perform(request)
				.andExpect(status().is(200))
				.andReturn();
	}

	private static String createUrlinJson(String url) {
		return "{\"url\": \""+ url + "\"}";
	}

}
