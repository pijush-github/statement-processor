package com.rabo.customer.statement.processor.rest;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.rabo.customer.statement.processor.config.StatementProcessorConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = StatementProcessorConfigurer.class)
@TestPropertySource("classpath:environment.properties")
public class StatementProcessingControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	
    private MockMvc mockMvc;
    
    private InputStream isCSV;
    
    private InputStream isXML;
        
    private InputStream isTXT;
    
    private InputStream isEmptyXML;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
        this.isCSV = this.getClass().getClassLoader().getResourceAsStream("records.csv");
        this.isXML = this.getClass().getClassLoader().getResourceAsStream("records.xml");
        this.isTXT = this.getClass().getClassLoader().getResourceAsStream("records.txt");
        this.isEmptyXML = this.getClass().getClassLoader().getResourceAsStream("empty.xml");
    }
    
    
	@Test
	public void testUploadMonthlyStatementsAsCSVFile() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("customerStatement", "records.csv", "multipart/form-data", isCSV); 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/monthly/statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA);
        MvcResult result = this.mockMvc.perform(builder).andExpect(ok).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());        
	}
	
	@Test
	public void testUploadMonthlyStatementsAsXMLFile() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("customerStatement", "records.xml", "multipart/form-data", isXML); 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/monthly/statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA);
        MvcResult result = this.mockMvc.perform(builder).andExpect(ok).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());        
	}
	
	@Test
	public void testUploadMonthlyStatementsAsNullFile() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("customerStatement", "empty.xml", "multipart/form-data", isEmptyXML); 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/monthly/statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA);
        MvcResult result = this.mockMvc.perform(builder).andExpect(badRequest).andReturn();
        Assert.assertEquals(400, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());        
	}
	
	@Test
	public void testUploadMonthlyStatementsAsUnknownType() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("customerStatement", "records.txt", "multipart/form-data", isTXT); 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/monthly/statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA);
        MvcResult result = this.mockMvc.perform(builder).andExpect(badRequest).andReturn();
        Assert.assertEquals(400, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());        
	}

}
