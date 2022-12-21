package br.com.example.services

import br.com.example.data.vo.v1.PersonVO
import br.com.example.data.vo.v2.PersonVO as PersonVOV2
import br.com.example.exceptions.ResourceNotFoundException
import br.com.example.mapper.DozerMapper
import br.com.example.mapper.custom.PersonMapper
import br.com.example.model.Person
import br.com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")

        val people = repository.findAll()

        return DozerMapper.parseListObjects(people, PersonVO::class.java)
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person!")

        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun create(person: PersonVO): PersonVO {
        logger.info("Creating one person with name ${person.firstName} ${person.lastName}")

        val entity: Person = DozerMapper.parseObject(person, Person::class.java)

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }
    fun createV2(person: PersonVOV2): PersonVOV2 {
        logger.info("Creating one person with name ${person.firstName} ${person.lastName}")

        val entity: Person = mapper.mapVOToEntity(person)

        return mapper.mapEntityToVO(repository.save(entity))
    }

    fun update(person: PersonVO) : PersonVO{
        logger.info("Updating one person with id ${person.id}")

        val entity = DozerMapper.parseObject(findById(person.id), Person::class.java)

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with id ${id}")

        val entity = DozerMapper.parseObject(findById(id), Person::class.java)

        repository.delete(entity)
    }

    private fun mockPerson(i: Int): PersonVO {
        val person = PersonVO()
        //person.id = counter.incrementAndGet()
        person.firstName = "Person Name $i"
        person.lastName = "Last Name $i"
        person.address = "far far away $i"

        return person
    }
}