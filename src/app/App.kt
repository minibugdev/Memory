package app

import app.components.*
import app.external.AxiosConfigSettings
import app.external.ResourceResponse
import app.external.axios
import app.external.reactRenderResize
import kotlinext.js.jsObject
import react.*
import react.dom.div

interface AppState : RState {
    var preWeddingImagePublicIds: Array<String>
    var weddingImagePublicIds: Array<String>
}

interface AppProps : RProps {
    var windowWidth: Int
    var windowHeight: Int
}

class App : RComponent<AppProps, AppState>() {
    override fun AppState.init() {
        preWeddingImagePublicIds = emptyArray()
        weddingImagePublicIds = emptyArray()
    }

    override fun componentDidMount() {
        fetchGallery("https://res.cloudinary.com/ting-pop/image/list/15sep.json") { results ->
            setState {
                weddingImagePublicIds = results
            }
        }

        fetchGallery("https://res.cloudinary.com/ting-pop/image/list/2sep.json") { results ->
            setState {
                preWeddingImagePublicIds = results
            }
        }
    }

    override fun RBuilder.render() {
        background(props.windowWidth, props.windowHeight)

        div {
            header(props.windowWidth, props.windowHeight)

            welcome()

            story(props.windowWidth, props.windowHeight)

            gallery("Wedding", state.weddingImagePublicIds)

            gallery("Pre Wedding", state.preWeddingImagePublicIds)

            location()

            schedule()
        }
    }

    private fun fetchGallery(remoteUrl: String, callback: (Array<String>) -> Unit) {
        val config: AxiosConfigSettings = jsObject { url = remoteUrl }
        axios<ResourceResponse>(config).then { response ->
            response.data.resources
                    .map { it.publicId }
                    .sortedDescending()
                    .toTypedArray()
                    .run {
                        callback(this)
                    }
        }.catch { error ->
            console.log(error)
        }
    }
}

fun RBuilder.app() = reactRenderResize.component {
    attrs {
        render = { window ->
            child(App::class) {
                attrs {
                    windowWidth = window.width
                    windowHeight = window.height
                }
            }
        }
    }
}