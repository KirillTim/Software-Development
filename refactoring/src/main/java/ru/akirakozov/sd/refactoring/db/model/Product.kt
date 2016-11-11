package ru.akirakozov.sd.refactoring.db.model

/**
 * @author Kirill
 */
data class Product(val name: String, val price: Int) {
    override fun toString(): String {
        return "$name\t$price"
    }
}