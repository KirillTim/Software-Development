package ru.akirakozov.sd.refactoring.db

import java.sql.Connection
import java.sql.ResultSet

import ru.akirakozov.sd.refactoring.db.model.Product

/**
 * @author kirill
 */
class ProductDB(private val connection: Connection) {

    private val NAME_COLUMN = "name"
    private val PRICE_COLUMN = "price"
    private val TABLE_NAME = "product"

    fun addProduct(product: Product) {
        val INSERT_STATEMENT = "INSERT INTO $TABLE_NAME ($NAME_COLUMN, $PRICE_COLUMN) " +
                "VALUES (\"${product.name}\", ${product.price})"
        val stmt = connection.createStatement()
        stmt.executeUpdate(INSERT_STATEMENT)
        stmt.close()
    }

    fun getProducts(): List<Product> {
        val GET_STATEMENT = "SELECT * FROM $TABLE_NAME"
        val stmt = connection.createStatement()
        val resultSet = stmt.executeQuery(GET_STATEMENT)
        val products = mutableListOf<Product>()
        while (resultSet.next()) {
            products += resultSetToProduct(resultSet)
        }
        resultSet.close()
        stmt.close()
        return products
    }

    fun getMaxPrice() = getOneProduct("SELECT * FROM $TABLE_NAME ORDER BY $PRICE_COLUMN DESC LIMIT 1")

    fun getMinPrice() = getOneProduct("SELECT * FROM $TABLE_NAME ORDER BY $PRICE_COLUMN LIMIT 1")

    fun getPricesSum() = getOneInt("SELECT SUM($PRICE_COLUMN) FROM $TABLE_NAME")

    fun getProductsCount() = getOneInt("SELECT COUNT(*) FROM $TABLE_NAME")

    private fun getOneInt(query: String): Int? {
        val stmt = connection.createStatement()
        val resultSet = stmt.executeQuery(query)
        var result: Int? = null
        if (resultSet.next())
            result = resultSet.getInt(1)
        resultSet.close()
        stmt.close()
        return result
    }

    private fun getOneProduct(query: String): Product? {
        val stmt = connection.createStatement()
        val resultSet = stmt.executeQuery(query)
        var result: Product? = null
        if (resultSet.next())
            result = resultSetToProduct(resultSet)
        resultSet.close()
        stmt.close()
        return result
    }

    private fun resultSetToProduct(rs: ResultSet) = Product(rs.getString(NAME_COLUMN), rs.getInt(PRICE_COLUMN))
}
