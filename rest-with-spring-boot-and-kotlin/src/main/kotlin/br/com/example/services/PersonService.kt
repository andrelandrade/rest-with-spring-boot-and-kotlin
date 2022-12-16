package br.com.example.services

import br.com.example.exceptions.ResourceNotFoundException
import br.com.example.model.Person
import br.com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Finding all people!")

        return repository.findAll()
    }

    fun findById(id: Long): Person {
        logger.info("Finding one person!")
        return repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }
    }

    fun create(person: Person): Person {
        logger.info("Creating one person with name ${person.firstName} ${person.lastName}")
        return repository.save(person)
    }

    fun update(person: Person) {
        logger.info("Updating one person with id ${person.id}")

        val entity = findById(person.id)

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with id ${id}")

        val entity = findById(id)

        repository.delete(entity)
    }

    private fun mockPerson(i: Int): Person {
        val person = Person()
        //person.id = counter.incrementAndGet()
        person.firstName = "Person Name $i"
        person.lastName = "Last Name $i"
        person.address = "far far away $i"

        return person
    }
}