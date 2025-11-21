package ${package}.person.domain;

import ch.martinelli.oss.jooqspring.JooqDAO;
import ${package}.db.tables.Person;
import ${package}.db.tables.records.PersonRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static ${package}.db.tables.Person.PERSON;

@Repository
public class PersonDAO extends JooqDAO<Person, PersonRecord, Long> {

	public PersonDAO(DSLContext dslContext) {
		super(dslContext, PERSON);
	}

}
