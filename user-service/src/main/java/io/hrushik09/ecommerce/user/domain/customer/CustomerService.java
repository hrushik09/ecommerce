package io.hrushik09.ecommerce.user.domain.customer;

import io.hrushik09.ecommerce.user.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerCommand;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerResponse;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityCodeGenerator generateCode;

    CustomerService(CustomerRepository customerRepository, EntityCodeGenerator generateCode) {
        this.customerRepository = customerRepository;
        this.generateCode = generateCode;
    }

    public CreateCustomerResponse createCustomer(CreateCustomerCommand cmd) {
        if (customerRepository.existsByUsername(cmd.username())) {
            throw new CustomerWithUsernameAlreadyExists(cmd.username());
        }
        if (customerRepository.existsByEmail(cmd.email())) {
            throw new CustomerWithEmailAlreadyExists(cmd.email());
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCode(generateCode.forEntityType("customer"));
        customerEntity.setUsername(cmd.username());
        customerEntity.setEmail(cmd.email());
        customerEntity.setFirstName(cmd.firstName());
        customerEntity.setLastName(cmd.lastName());
        customerEntity.setCountry(cmd.country());
        customerEntity.setRegion(cmd.region());
        CustomerEntity saved = customerRepository.save(customerEntity);
        return CustomerMapper.convertToCreateCustomerResponse(saved);
    }
}
