package ru.akirakozov.sd.refactoring.html

/**
 * @author kirill
 */
open class HtmlPage(val body: String = "") {
    val head = "<html>\n<body>\n"
    val foot = "</body>\n</html>"
    open fun generate() = "$head\n$body\n$foot\n"
}

class ListHtmlPage(val title: String = "", val list: List<String> = listOf<String>()) : HtmlPage() {

    override fun generate() = "${super.head}\n$title\n${list.joinToString("</br>")}\n${super.foot}"
}