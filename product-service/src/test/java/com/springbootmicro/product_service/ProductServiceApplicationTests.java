package com.springbootmicro.product_service;

import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootmicro.product_service.dto.ProductRequest;
import com.springbootmicro.product_service.repository.ProductRepository;



@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	// @Container
	// static MongoDBContainer mongoDBContainer = new MongoDBContainer("4.4.2");
	@Autowired
	private MockMvc mockmMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
	private static final DockerImageName MONGO_IMAGE = DockerImageName.parse("mongo:4.4.2").asCompatibleSubstituteFor("mongo");

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_IMAGE);

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

	// @DynamicPropertySource
	// static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
	// 	dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	// }

	@Test
	void shouldCreateProduct() throws Exception{
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockmMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
						.andExpect(status().isCreated());
		// Assertions.assertTrue(productRepository.findAll().size() == 1);
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
		.name("Iphone 13")
		.description("Iphone 13")
		.price(BigDecimal.valueOf(1200))
		.build();
	}

}
