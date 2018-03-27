package com.codingExercise.loan;

import com.codingExercise.loan.jpa.Loan;
import com.codingExercise.loan.jpa.LoanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class LoanControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Loan loan;

    private List<Loan> loanList = new ArrayList<>();

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.loanRepository.deleteAllInBatch();

        this.loan = new Loan();
        this.loan.setLoanAmount(new BigDecimal(1000));
        this.loan = loanRepository.save(this.loan);
    }

    @Test
    public void loanNotFound() throws Exception {
        mockMvc.perform(get("/api/loans/" + UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .content(this.json(new Loan()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createLoan() throws Exception {
        Loan testLoan = new Loan();
        testLoan.setLoanAmount(new BigDecimal(1000));
        String loanJson = json(testLoan);

        this.mockMvc.perform(post("/api/loans")
                .contentType(contentType)
                .content(loanJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void readSingleLoan() throws Exception {
        mockMvc.perform(get("/api/loans/" + this.loan.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.loan.getId().toString())))
                .andExpect(jsonPath("$.loanAmount", is(this.loan.getLoanAmount().doubleValue())));
    }

    @Test
    public void readLoans() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(this.loanList.get(0).getId().intValue())))
//                .andExpect(jsonPath("$[1].id", is(this.loanList.get(1).getId().intValue())))
        ;
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}