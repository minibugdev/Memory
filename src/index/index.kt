package index

import app.*
import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

fun main(args: Array<String>) {
    requireAll(require.context("src", true, js("/\\.css$/")))
//    require ("semantic-ui-css/semantic.min.css")
    require ("react-owl-carousel2/lib/styles.css")

    render(document.getElementById("root")) {
        app()
    }
}
