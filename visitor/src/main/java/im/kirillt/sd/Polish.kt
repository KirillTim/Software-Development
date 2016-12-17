package im.kirillt.sd

import im.kirillt.sd.Operation.Type.*
import java.io.InputStream

/**
 * @author Kirill
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("Usage: <expression>\ne.g.: 10+(4*2)/(2-8)")
        System.exit(1)
    }
    val expression = args[0]

    val tokens = Tokenizer(expression.byteInputStream()).getResult()
    val parserVisitor = ParserVisitor()
    tokens.forEach { it.accept(parserVisitor) }
    val polish = parserVisitor.getResult()

    val printVisitor = PrintVisitor()
    polish.forEach { it.accept(printVisitor) }
    println(printVisitor.getResult())

    val calcVisitor = CalcVisitor()
    polish.forEach { it.accept(calcVisitor) }
    println(calcVisitor.getResult())
}

class Tokenizer(private val inputStream: InputStream) {

    private val tokens = mutableListOf<Token>()

    private var state: TokenizerState = StartState()

    var curChar: Char? = null
        private set

    fun getResult(): List<Token> {
        nextChar()
        while (curChar != null) {
            state.parseChar(this)
        }
        return tokens
    }

    fun nextChar() {
        try {
            val c = inputStream.read()
            if (c == -1) {
                curChar = null
            } else {
                curChar = c.toChar()
            }
        } catch(e: Exception) {
            throw IllegalArgumentException("Unable to parse an expression", e)
        }
    }

    fun addToken(token: Token) = tokens.add(token)

    fun setState(newState: TokenizerState) {
        state = newState
    }
}

interface TokenizerState {
    fun parseChar(context: Tokenizer)
}

class StartState : TokenizerState {
    override fun parseChar(context: Tokenizer) {
        var move: Boolean = true
        when (context.curChar) {
            ' ', '\n' -> Unit
            '(' -> context.addToken(Brace(true))
            ')' -> context.addToken(Brace(false))
            '+' -> context.addToken(Operation(ADD))
            '-' -> context.addToken(Operation(SUB))
            '*' -> context.addToken(Operation(MUL))
            '/' -> context.addToken(Operation(DIV))
            else -> {
                move = false
            }
        }
        if (move) {
            context.nextChar()
        } else {
            if (context.curChar in '0'..'9') {
                context.setState(NumberState())
            } else {
                throw IllegalArgumentException("Unable to parse ${context.curChar}")
            }
        }
    }

}

class NumberState() : TokenizerState {

    private var value: Int = 0

    override fun parseChar(context: Tokenizer) {
        if (context.curChar in '0'..'9') {
            value = value * 10 + (context.curChar!! - '0')
            context.nextChar()
        } else {
            context.addToken(NumberToken(value))
            context.setState(StartState())
        }
    }
}