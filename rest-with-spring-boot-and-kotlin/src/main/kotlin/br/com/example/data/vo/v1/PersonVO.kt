package br.com.example.data.vo.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "address", "firstName", "lastName")
data class PersonVO (
    //@field:JsonIgnore
    var id: Long = 0,

    @field:JsonProperty("first_name")
    var firstName: String = "",

    @field:JsonProperty("last_name")
    var lastName: String = "",
    var address: String = "",
)