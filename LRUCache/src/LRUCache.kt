interface Cache<in K, V> {
    operator fun get(key: K): V?

    fun put(key: K, value: V): V?

    fun size(): Int

    fun isEmpty() = size() == 0

    fun isNotEmpty() = !isEmpty()
}

class LRUCache<in K, V>(private val cacheSize: Int = 10) : Cache<K, V> {

    init {
        require(cacheSize > 0, { "The cache must not be empty" })
    }

    private data class Node<K, V>(val key: K, var value: V, var prev: Node<K, V>?, var next: Node<K, V>?)

    private var head: Node<K, V>? = null

    private var tail: Node<K, V>? = null

    private val map = mutableMapOf<K, Node<K, V>>()

    @Synchronized
    override fun get(key: K): V? {
        val node = map[key]
        if (node != null) {
            assert(head != null)
            assert(tail != null)
            update(node)
            assert(head == node)
            return node.value
        }
        return null
    }

    private fun update(node: Node<K, V>) {
        assert(isNotEmpty(), {"Can't update empty list"})
        if (node == head) {
            return
        }
        if (node == tail) {
            tail!!.prev?.next = null
            tail = tail!!.prev
            node.prev = null
            node.next = head
            head!!.prev = node
            head = node
            return
        }
        node.prev!!.next = node.next
        node.next!!.prev = node.prev
        node.prev = null
        node.next = head
        head!!.prev = node
        head = node
    }

    @Synchronized
    override fun put(key: K, value: V): V? {
        val oldNode = map[key]

        if (oldNode != null) {
            assert(head != null)
            assert(tail != null)
            update(oldNode)
            assert(head == oldNode)

            val oldValue = oldNode.value
            oldNode.value = value
            return oldValue
        }
        assert(key !in map)

        if (size() == cacheSize) {
            if (head == tail) {
                assert(cacheSize == 1, {"Broken list structure"})
                head = null
                tail = null
                map.clear()
            } else {
                tail!!.prev!!.next = null
                map.remove(tail!!.key)
                tail = tail!!.prev
            }
        }

        val newNode = Node<K, V>(key, value, null, null)

        if (isEmpty()) {
            assert(head == null)
            assert(tail == null)
            head = newNode
            tail = newNode
        } else {
            assert(head != null)
            assert(tail != null)
            newNode.next = head
            head!!.prev = newNode
            head = newNode
        }
        map[key] = newNode

        return null
    }

    @Synchronized override fun size(): Int = map.size
}

fun main(args: Array<String>) {
    val cache = LRUCache<String, Int>(3)
    cache.put("one", 1)
    println(cache.put("one", 11)) //1
    println(cache.size()) //1
    println(cache.put("two", 2)) //null
    println(cache.get("two")) //2
    println(cache.put("three", 3)) // null
    println(cache.put("four", 4))
    println(cache.size()) // 3
    println(cache.get("one")) //null
}