package index

import app.*
import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

fun main(args: Array<String>) {
    requireAll(require.context("src", true, js("/\\.css$/")))
    require("owl.carousel/dist/assets/owl.carousel.css")
    require("owl.carousel/dist/assets/owl.theme.default.css")

    render(document.getElementById("root")) {
        app()
    }
}
