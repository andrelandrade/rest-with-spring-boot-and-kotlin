package br.com.example

import br.com.example.exceptions.UnsupportedMathOperationException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MathController {

    @RequestMapping(value = ["/sum/{numberOne}/{numberTwo}"])
    fun sum(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?): Double {

        validMathOperation(numberOne, numberTwo)

        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    @RequestMapping(value = ["/subtract/{numberOne}/{numberTwo}"])
    fun subtract(@PathVariable(value = "numberOne") numberOne: String?,
                 @PathVariable(value = "numberTwo") numberTwo: String?): Double {

        validMathOperation(numberOne, numberTwo)

        return convertToDouble(numberOne) - convertToDouble(numberTwo)
    }

    @RequestMapping(value = ["/multiply/{numberOne}/{numberTwo}"])
    fun multiply(@PathVariable(value = "numberOne") numberOne: String?,
                 @PathVariable(value = "numberTwo") numberTwo: String?): Double {

        validMathOperation(numberOne, numberTwo)

        return convertToDouble(numberOne) * convertToDouble(numberTwo)
    }

    @RequestMapping(value = ["/divide/{numberOne}/{numberTwo}"])
    fun divide(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?): Double {

        validMathOperation(numberOne, numberTwo)

        if (numberTwo == "0") throw UnsupportedMathOperationException("Division by 0")

        return convertToDouble(numberOne) / convertToDouble(numberTwo)
    }

    @RequestMapping(value = ["/avg/{numberOne}/{numberTwo}"])
    fun avg(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?): Double {

        validMathOperation(numberOne, numberTwo)

        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2
    }

    @RequestMapping(value = ["/sqrt/{numberOne}"])
    fun sqrt(@PathVariable(value = "numberOne") numberOne: String?): Double {

        validMathOperation(numberOne,"0")

        return kotlin.math.sqrt(convertToDouble(numberOne))
    }

    private fun validMathOperation(numberOne: String?, numberTwo: String?) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please set a numeric value")
    }

    private fun convertToDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank()) return 0.0

        val number = strNumber.replace(",".toRegex(), ".")

        return if (isNumeric(number)) number.toDouble() else 0.0
    }

    private fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) return false

        val number = strNumber.replace(",".toRegex(), ".")

        return number.matches("""[-+]?[0-9]*\.?[0-9]+""".toRegex())
    }
}