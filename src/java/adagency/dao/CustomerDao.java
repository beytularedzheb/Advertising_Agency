package adagency.dao;

import adagency.entity.Customer;

public class CustomerDao extends AbstractDao<Customer> {

    public CustomerDao() {
        super(Customer.class);
    }
}
