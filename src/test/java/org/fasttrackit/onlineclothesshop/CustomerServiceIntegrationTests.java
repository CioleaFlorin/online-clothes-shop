package org.fasttrackit.onlineclothesshop;


import org.fasttrackit.onlineclothesshop.domain.Customer;
import org.fasttrackit.onlineclothesshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineclothesshop.service.CustomerService;
import org.fasttrackit.onlineclothesshop.steps.CustomerSteps;
import org.fasttrackit.onlineclothesshop.transfer.SaveCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSteps customerSteps;

    @Test(expected = TransactionSystemException.class)
    public void testCreateCustomer_whenInvalidRequest_thenThrowException() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        //null to validate the negative flow
        customerService.createCustomer(request);
    }

    @Test
    public void testGetCustomer_whenExistingCustomer_thenReturnCustomer() {
        Customer createdCustomer = customerSteps.createCustomer();
        Customer customer = customerService.getCustomer(createdCustomer.getId());

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
        assertThat(customer.getFirstName(), is(createdCustomer.getFirstName()));
        assertThat(customer.getLastName(), is(createdCustomer.getLastName()));
    }

    @Test
    public void testCreateCustomer_whenValidRequest_thenCustomerIsSaved() {
        customerSteps.createCustomer();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCustomer_whenNonExistingCustomer_thenThrowResourceNotFoundException() {
        customerService.getCustomer(99999999999L);
    }

    @Test
    public void testUpdateCustomer_whenValidRequest_thenReturnUpdatedCustomer() {

        Customer createdCustomer = customerSteps.createCustomer();

        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName(createdCustomer.getFirstName() + "updated");
        request.setLastName(createdCustomer.getLastName() + "updated");

        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), request);

        assertThat(updatedCustomer.getId(), notNullValue());
        assertThat(updatedCustomer.getId(), is(createdCustomer.getId()));
        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));


    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteCustomer_whenExistingCustomer_thenCustomerIsDeleted() {
        Customer customer=customerSteps.createCustomer();

        customerService.deleteCustomer(customer.getId());

        customerService.getCustomer(customer.getId());
    }
}
