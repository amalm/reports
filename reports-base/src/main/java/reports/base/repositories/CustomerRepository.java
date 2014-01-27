package reports.base.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import reports.base.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    @Query("from Customer c left outer join fetch c.contacts left outer join fetch c.addresses where c.lastName=:lastName")
    Customer findByLastName(@Param("lastName") String lastName);
}
