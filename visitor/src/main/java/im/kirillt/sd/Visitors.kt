package im.kirillt.sd

import im.kirillt.sd.Operation.Type.*
import java.util.*

/**
 * @author Kirill
 */
interface TokenVisitor {
    fun visit(number: NumberToken)
    fun visit(operation: Operation)
    fun visit(brace: Brace)
}

class ParserVisitor() : TokenVisitor {

    private val polish = mutableListOf<Token>()

    private val operations = Stack<Token>()

    private val priorities = mapOf(
            ADD to 0,
            SUB to 0,
            MUL to 1,
            DIV to 1
    )

    fun getResult(): List<Token> {
        while (operations.isNotEmpty()) {
            polish.add(operations.pop())
        }
        return polish
    }

    override fun visit(number: NumberToken) {
        polish.add(number)
    }

    override fun visit(brace: Brace) {
        if (brace.isOpen) {
            operations.push(brace)
        } else {
            while (operations.isNotEmpty()) {
                val top = operations.pop()
                if (top is Brace && top.isOpen) {
                    return
                }
                polish.add(top)
            }
            throw IllegalStateException("Mismatched braces")
        }
    }

    override fun visit(operation: Operation) {
        while (operations.isNotEmpty()) {
            val top = operations.peek()
            if (top is Operation && priorities.containsKey(top.type)
                    && priorities[operation.type]!! <= priorities[top.type]!!) {
                operations.pop()
                polish.add(top)
            } else {
                break
            }
        }
        operations.add(operation)
    }
}

class CalcVisitor : TokenVisitor {

    private val stack: Stack<Double> = Stack()

    fun getResult(): Double {
        if (stack.size != 1) {
            throw IllegalStateException()
        }
        return stack.peek()
    }

    override fun visit(number: NumberToken) {
        stack.push(number.value.toDouble())
    }

    override fun visit(brace: Brace) {
        throw IllegalStateException("Polish notation must not contain braces")
    }

    override fun visit(operation: Operation) {
        val two = stack.pop()
        val one = stack.pop()
        stack.push(operation.getFunction().invoke(one, two))
    }
}

class PrintVisitor : TokenVisitor {

    private val sb = StringBuilder()

    fun getResult() = sb.toString()

    override fun visit(number: NumberToken) {
        sb.append("Number(${number.value}) ")
    }

    override fun visit(operation: Operation) {
        sb.append("${operation.type} ")
    }

    override fun visit(brace: Brace) {
        sb.append(if (brace.isOpen) "Open" else "Close")
    }

}
