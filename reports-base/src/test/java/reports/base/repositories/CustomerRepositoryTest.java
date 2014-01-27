package reports.base.repositories;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import reports.base.entities.Address;
import reports.base.entities.Contact;
import reports.base.entities.Contact.ContactType;
import reports.base.entities.Customer;

@ContextConfiguration(locations="classpath:META-INF/jpa.spring.xml")
@TransactionConfiguration(defaultRollback=false)
@Test(groups="javaConfig")
public class CustomerRepositoryTest extends AbstractTransactionalTestNGSpringContextTests
{
    @Autowired
    private CustomerRepository target;
    
    /**
     * Check configuration.
     * @throws ParseException 
     */
    @Test
    public void saveRead() throws ParseException
    {
        Set<Address> addresses = new HashSet<Address>(Arrays.asList(new Address("street1", "zip1", "city1"),
                                                new Address("street2", "zip2", "city2")));
        Set<Contact> contacts = new HashSet<Contact>(Arrays.asList(new Contact(ContactType.MAIL, "setting@server.com")));
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Customer customer = new Customer("lastName2", "firstName", df.parse("1996.05.12"), addresses, contacts);
        customer = target.save(customer);
        customer = target.findOne(customer.getId());
        assertEquals(customer.getAddresses().size(), 2);
        assertEquals(customer.getContacts().size(), 1);
    }
    @Test
    public void saveOne() throws ParseException
    {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Customer customer = new Customer("lastName", "firstName", df.parse("1996.05.12"), Collections.<Address> emptySet(), Collections.<Contact> emptySet());
        customer = target.save(customer);
    }
}