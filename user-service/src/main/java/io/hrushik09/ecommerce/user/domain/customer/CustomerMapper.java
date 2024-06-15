package io.hrushik09.ecommerce.user.domain.customer;

import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerResponse;

class CustomerMapper {
    public static CreateCustomerResponse convertToCreateCustomerResponse(CustomerEntity customerEntity) {
        return new CreateCustomerResponse(customerEntity.getCode(), customerEntity.getUsername(), customerEntity.getEmail(), customerEntity.getFirstName(), customerEntity.getLastName(), customerEntity.getCountry(), customerEntity.getRegion());
    }
}
