package com.protosstechnology.train.bootexamine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protosstechnology.train.bootexamine.model.Document;
import com.protosstechnology.train.bootexamine.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BootexamineApplicationTests {
	private final Long FIRST_GEN_ID = 1L;
	private final Long WRONG_GEN_ID = 112L;
	private final String FIRST_NUMBER = "44";
	private final String FIRST_DESC = "First";
	private final String ROOT_URL = "/document";
	private final Document FIRST_DOC = new Document(FIRST_GEN_ID, FIRST_NUMBER, FIRST_DESC);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DocumentService documentService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		when(documentService.read(FIRST_GEN_ID)).thenReturn(FIRST_DOC);
		when(documentService.read(WRONG_GEN_ID)).thenReturn(null);
	}

	@Test
	public void addDocument_ok() throws Exception {
		Document mockedDoc = new Document("112", "TestDoc");
		String docJson = objectMapper.writeValueAsString(mockedDoc);
		MvcResult result = mockMvc.perform(post(ROOT_URL).contentType(MediaType.APPLICATION_JSON).content(docJson))
				.andExpect(status().isOk())
				.andReturn();

		String responseString = result.getResponse().getContentAsString();
		assertEquals(docJson, responseString);
		verify(documentService, times(1)).create(mockedDoc);
	}

	@Test
	public void getDocument_ok() throws Exception {
		MvcResult result = mockMvc.perform(get(ROOT_URL + "/" + FIRST_GEN_ID))
				.andExpect(status().isOk())
				.andReturn();

		String responseString = result.getResponse().getContentAsString();
		Document readDoc = objectMapper.readValue(responseString, Document.class);
		assertNotNull(readDoc);
		verify(documentService, times(1)).read(FIRST_GEN_ID);
	}

	@Test
	public void getDocument_badRequest() throws Exception {
		mockMvc.perform(get(ROOT_URL + "/ASDFJKL;")).andExpect(status().isBadRequest());
		verify(documentService, times(0)).read(FIRST_GEN_ID);
	}

	@Test
	public void getDocument_notFound() throws Exception {
		MvcResult result = mockMvc.perform(get(ROOT_URL + "/" + WRONG_GEN_ID))
				.andExpect(status().isNotFound())
				.andReturn();

		String responseString = result.getResponse().getContentAsString();
		assertTrue(StringUtils.isEmpty(responseString));
		verify(documentService, times(1)).read(WRONG_GEN_ID);
	}

	@Test
	public void putDocument_ok() throws Exception {
		Document mockedDoc = new Document("112", "TestDoc");
		String docJson = objectMapper.writeValueAsString(mockedDoc);
		MvcResult result = mockMvc.perform(put(ROOT_URL + "/" + FIRST_GEN_ID).contentType(MediaType.APPLICATION_JSON).content(docJson))
				.andExpect(status().isOk())
				.andReturn();

		String responseString = result.getResponse().getContentAsString();
		Document readDoc = objectMapper.readValue(responseString, Document.class);
		mockedDoc.setId(FIRST_GEN_ID);
		assertEquals(mockedDoc, readDoc);
		verify(documentService, times(1)).update(mockedDoc);
	}

	@Test
	public void deleteDocument_ok() throws Exception {
		MvcResult result = mockMvc.perform(delete(ROOT_URL + "/" + FIRST_GEN_ID))
				.andExpect(status().isOk())
				.andReturn();
		String responseString = result.getResponse().getContentAsString();
		assertEquals(String.format("Delete Document %s successfully", FIRST_GEN_ID), responseString);
		verify(documentService, times(1)).delete(FIRST_GEN_ID);
	}
}
