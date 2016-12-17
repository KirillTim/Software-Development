package im.kirillt.sd

import im.kirillt.sd.Operation.Type.*

/**
 * @author Kirill
 */
interface Token {
    fun accept(tokenVisitor: TokenVisitor)
}

class Brace(val isOpen: Boolean) : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}

class Operation(val type: Type) : Token {

    enum class Type { ADD, SUB, MUL, DIV }

    fun getFunction(): ((Double, Double) -> Double) = when (type) {
        ADD -> { a, b -> a + b }
        SUB -> { a, b -> a - b }
        MUL -> { a, b -> a * b }
        DIV -> { a, b -> a / b }
    }

    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}

class NumberToken(val value: Int) : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}