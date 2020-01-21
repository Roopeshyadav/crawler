package com.roopesh.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.roopesh.crawler.constant.Constant;
import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.exception.RequestFailedException;
import com.roopesh.crawler.model.BasePage;
import com.roopesh.crawler.model.QueueData;
import com.roopesh.crawler.repository.CrawlerDataRepository;
import com.roopesh.crawler.repository.StatusRepository;
import com.roopesh.crawler.request.DataRequest;
import com.roopesh.crawler.request.StatusRequest;
import com.roopesh.crawler.request.SubmitRequest;
import com.roopesh.crawler.response.StatusResponse;
import com.roopesh.crawler.service.CrawlerService;
import com.roopesh.crawler.service.QueueService;
import com.roopesh.crawler.service.StatusService;
import com.roopesh.crawler.service.impl.StatusServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest(classes = CrawlerApplication.class)
@RunWith(SpringRunner.class)
class CrawlerApplicationTests {

	@Autowired
	private StatusService statusService;

	@Autowired
	private CrawlerService crawlerService;

	@MockBean
	private QueueService queueService;

	@MockBean
	private CrawlerDataRepository crawlerDataRepository;

	@MockBean
	private StatusRepository statusRepository;

	private QueueData queueData;
	private BasePage basePage;
	private Gson gson;

	public CrawlerApplicationTests() {
		gson = new Gson();
		String dataJson="{\n" +
				"    \"total_links\": 1,\n" +
				"    \"total_images\": 20,\n" +
				"    \"details\": [\n" +
				"        {\n" +
				"            \"pageLink\": \"https://en.wikipedia.org/wiki/Blog\",\n" +
				"            \"pageTitle\": \"Blog - Wikipedia\",\n" +
				"            \"image_count\": 20\n" +
				"        }\n" +
				"    ]\n" +
				"}";
		basePage = gson.fromJson(dataJson, BasePage.class);
	}

	@Before
	public void setup(){
//		MockitoAnnotations.initMocks(this); //without this you will get NPE

		queueData = new QueueData("16638793-5d82-4842-9533-9313c2ee6509","https://en.wikipedia.org/wiki/Blog", 2);

	}

	@PostConstruct
	public void init(){
		Mockito.when(statusRepository.get("16638793-5d82-4842-9533-9313c2ee6509")).thenReturn(Status.PROCESSED);
		Mockito.when(statusRepository.get("8a2e5fdc-c8db-47bf-9ebd-fdbaf52742f9")).thenReturn(Status.INPROGRESS);
		Mockito.when(statusRepository.get("7f931e3d-17c7-4350-a4e7-571b2450e610")).thenReturn(Status.INPROGRESS);
		Mockito.when(statusRepository.get("7f931e3d-17c7-4350-a4e7-571b2450e611")).thenReturn(Status.FAILED);
		Mockito.when(crawlerDataRepository.get("16638793-5d82-4842-9533-9313c2ee6509")).thenReturn(basePage);
		Mockito.when(queueService.get()).thenReturn(queueData);

	}

	@Test
	void statusCheck() {
		Assert.assertEquals(statusService.getStatus("8a2e5fdc-c8db-47bf-9ebd-fdbaf52742f9"), Status.INPROGRESS);
	}

	@Test
	void submitRequest(){
		MDC.put(Constant.MDC_REQ_ID, "16638793-5d82-4842-9533-9313c2ee6509");
		String requestJson = "{\n" +
				"\t\"url\":\"https://en.wikipedia.org/wiki/Blog\",\n" +
				"\t\"depth\":1\n" +
				"}";
		SubmitRequest submitRequest =gson.fromJson(requestJson,SubmitRequest.class);
		Assert.assertEquals(crawlerService.deepCrawl(submitRequest),"16638793-5d82-4842-9533-9313c2ee6509");

	}

	@Test
	void crawlRequestIdError(){
		Gson gson = new Gson();
		String requestJson = "{\n" +
				"\t\"id\":\"07bb920f-15dc-4e9b-88db-6311f7395d38\"\n" +
				"}";
		DataRequest dataRequest =gson.fromJson(requestJson,DataRequest.class);
		Assertions.assertThrows(NoSuchElementException.class,()->crawlerService.getCrawlData(dataRequest));

	}

	@Test
	void crawlRequestFailed(){
		Gson gson = new Gson();
		String requestJson = "{\n" +
				"\t\"id\":\"7f931e3d-17c7-4350-a4e7-571b2450e611\"\n" +
				"}";
		DataRequest dataRequest =gson.fromJson(requestJson,DataRequest.class);
		Assertions.assertThrows(RequestFailedException.class,()->crawlerService.getCrawlData(dataRequest));

	}

	@Test
	void crawlRequestSuccess(){
		Gson gson = new Gson();
		String requestJson = "{\n" +
				"\t\"id\":\"16638793-5d82-4842-9533-9313c2ee6509\"\n" +
				"}";
		DataRequest dataRequest =gson.fromJson(requestJson,DataRequest.class);
		;
		Assert.assertNotNull(crawlerService.getCrawlData(dataRequest));
	}

}
