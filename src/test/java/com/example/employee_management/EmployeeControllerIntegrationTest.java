package com.example.employee_management;

import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

   
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private EmployeeRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void createAndFetchByEmail_specMethod_shouldWork() {
        // create via basic endpoint
        String urlCreate = "http://localhost:" + port + "/api/employees/create/basic?firstName=Ana&email=ana@example.com";
        ResponseEntity<String> createResp = rest.postForEntity(urlCreate, null, String.class);
        assertEquals(HttpStatus.CREATED, createResp.getStatusCode());

        // fetch by email using spec
        String urlGet = "http://localhost:" + port + "/api/employees/by-email?email=ana@example.com&method=spec";
        ResponseEntity<String> getResp = rest.getForEntity(urlGet, String.class);
        assertEquals(HttpStatus.OK, getResp.getStatusCode());
        assertTrue(getResp.getBody().contains("ana@example.com"));
    }

    @Test
    public void createWithPhone_andDeleteByEmail_shouldWork() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO("Bob", "Marley", "bob@example.com", "1234567", "addr");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeRequestDTO> req = new HttpEntity<>(dto, headers);

        String url = "http://localhost:" + port + "/api/employees/create/with-phone";
        ResponseEntity<String> resp = rest.postForEntity(url, req, String.class);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());

        // delete by email
        String urlDelete = "http://localhost:" + port + "/api/employees/by-email?email=bob@example.com";
        ResponseEntity<String> delResp = rest.exchange(urlDelete, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, delResp.getStatusCode());
    }
}
