package app

import app.external.*
import app.models.Message
import app.models.Story
import kotlinext.js.jsObject
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import react.*
import react.dom.*
import styled.*

@JsModule("src/app/images/cover.jpg")
external val coverImage: dynamic
@JsModule("src/app/images/our_story.jpg")
external val ourStoryImage: dynamic
@JsModule("src/app/images/logo.png")
external val logo: dynamic
@JsModule("src/app/images/knot.png")
external val knot: dynamic
@JsModule("src/app/images/next.png")
external val iconNext: dynamic
@JsModule("src/app/images/prev.png")
external val iconPrev: dynamic
@JsModule("src/app/images/ring.png")
external val iconRing: dynamic

interface AppState : RState {
    var preWeddingImagePublicIds: Array<String>
    var weddingImagePublicIds: Array<String>
}

interface AppProps : RProps {
    var windowWidth: Int
    var windowHeight: Int
    var coverImageUrl: String
    var storyImageUrl: String
}

class App : RComponent<AppProps, AppState>() {
    override fun AppState.init() {
        preWeddingImagePublicIds = emptyArray()
        weddingImagePublicIds = emptyArray()
    }

    override fun componentDidMount() {
        val weddingConfig: AxiosConfigSettings = jsObject { url = "https://res.cloudinary.com/ting-pop/image/list/15sep.json" }
        axios<ResourceResponse>(weddingConfig).then { response ->
            setState {
                weddingImagePublicIds = response.data.resources
                        .map { it.publicId }
                        .sortedDescending()
                        .toTypedArray()
            }
        }.catch { error ->
            console.log(error)
        }

        val preWeddingConfig: AxiosConfigSettings = jsObject { url = "https://res.cloudinary.com/ting-pop/image/list/2sep.json" }
        axios<ResourceResponse>(preWeddingConfig).then { response ->
            setState {
                preWeddingImagePublicIds = response.data.resources
                        .map { it.publicId }
                        .sortedDescending()
                        .toTypedArray()
            }
        }.catch { error ->
            console.log(error)
        }
    }

    override fun RBuilder.render() {
        val componentStyles = ComponentStyles(props)
        val storyStyles = StoryStyles()

        styledDiv {
            css { +componentStyles.coverImage }
        }

        div {
            styledDiv {
                css { +componentStyles.cover }
                styledImg(src = logo as? String) {
                    css {
                        position = Position.absolute
                        left = 0.px
                        right = 0.px
                        top = 50.px
                        bottom = 0.px
                        margin(LinearDimension.auto)
                    }
                }
            }

            styledDiv {
                css {
                    backgroundColor = Color("#EEEEEE")
                    margin(0.px)
                    padding(80.px, 30.px)
                }
                div {
                    +"On the 15th of September, 2018"
                }
                styledH3 {
                    css { margin(10.px, 0.px) }
                    +"Unchalee Saechang (Ting) & Teeranai Buddee (Pop)"
                }
                div {
                    +"eloped in Kong Garden View Resort, Chiang Rai"
                }
                styledImg(src = knot as? String) {
                    css { width = 100.px }
                }
            }

            styledDiv {
                css { +storyStyles.layout }

                styledDiv {
                    css { +storyStyles.cover }

                    styledH1 {
                        css {
                            color = Color.white
                        }
                        +"OUR STORY"
                    }

                    OwlCarousel {
                        attrs {
                            className = "owl-theme"
                            items = props.windowWidth / 250
                            slideBy = 2
                            dots = false
                            nav = true
                            navText = arrayOf("<img src='$iconPrev'/>", "<img src='$iconNext'/>")
                        }

                        val stories = listOf(
                                Story("March 1990", "Pop was born in Chiang Rai"),
                                Story("October 1990", "Ting was born in Chiang Mai"),
                                Story("2008", "Pop arrive at Computer Science, Chiang Mai University"),
                                Story("2009", "Ting arrive at Computer Science, Chiang Mai University"),
                                Story("2009 - 2012", "Special care senior-junior relationship by lucky draw"),
                                Story("2012", "Pop graduate from Chiang Mai University"),
                                Story("2013", "Ting graduate from Chiang Mai University"),
                                Story("2013", "Pop moves from Chiang Mai to Bangkok, Thailand"),
                                Story("2014", "Ting moves to Muscut, Oman"),
                                Story("July 2017", "Meet again after 4 years"),
                                Story("September 2017", "First date :)"),
                                Story("October 2017", "In Relationship"),
                                Story("27 March 2018", "Will you marry me?"),
                                Story("15 September 2018", "Happy wedding day", iconRing as? String))

                        stories.forEachIndexed { index, story ->
                            val position = if (index % 2 == 0) StoryPosition.BOTTOM else StoryPosition.TOP
                            story(story, position, storyStyles)
                        }
                    }
                }
            }

            styledDiv {
                css {
                    padding(80.px, 0.px, 0.px, 0.px)
                    backgroundColor = Color("#ffffff")
                    overflow = Overflow.auto
                }

                if (state.weddingImagePublicIds.isNotEmpty()) {
                    h1 {
                        +"Wedding"
                    }

                    val cl = cloudinary.Core.new(jsObject {
                        cloudName = "ting-pop"
                    })
                    val galleryImages = state.weddingImagePublicIds.mapIndexed { index, publicId ->

                        val ratio = getImageRatio(index)
                        val ratioTransformation = jsObject<Transformation> {
                            aspectRatio = "${ratio.width}:${ratio.height}"
                            crop = "fill"
                        }
                        val widthTransformation = jsObject<Transformation> {
                            width = "400"
                            dpr = "auto"
                            crop = "scale"
                        }
                        GalleryImage(
                                src = cl.url(publicId, jsObject {
                                    transformation = arrayOf(jsObject {
                                        width = "2048"
                                        crop = "fit"
                                    })
                                }),
                                thumbnail = cl.url(publicId, jsObject {
                                    quality = 60
                                    transformation = arrayOf(ratioTransformation, widthTransformation)
                                }),
                                thumbnailWidth = ratio.width, thumbnailHeight = ratio.height)
                    }.toTypedArray()

                    reactGallery {
                        attrs {
                            id = "wedding"
                            enableImageSelection = false
                            margin = 1
                            images = galleryImages
                        }
                    }
                }
            }
            styledDiv {
                css {
                    padding(0.px, 0.px, 100.px, 0.px)
                    backgroundColor = Color("#ffffff")
                    overflow = Overflow.auto
                }

                if (state.preWeddingImagePublicIds.isNotEmpty()) {
                    styledH1 {
                        css {
                            paddingTop = 40.px
                        }
                        +"Pre Wedding"
                    }

                    val cl = cloudinary.Core.new(jsObject {
                        cloudName = "ting-pop"
                    })
                    val galleryImages = state.preWeddingImagePublicIds.mapIndexed { index, publicId ->

                        val ratio = getImageRatio(index)
                        val ratioTransformation = jsObject<Transformation> {
                            aspectRatio = "${ratio.width}:${ratio.height}"
                            crop = "fill"
                        }
                        val widthTransformation = jsObject<Transformation> {
                            width = "400"
                            dpr = "auto"
                            crop = "scale"
                        }
                        GalleryImage(
                                src = cl.url(publicId, jsObject {
                                    transformation = arrayOf(jsObject {
                                        width = "2048"
                                        crop = "fit"
                                    })
                                }),
                                thumbnail = cl.url(publicId, jsObject {
                                    quality = 60
                                    transformation = arrayOf(ratioTransformation, widthTransformation)
                                }),
                                thumbnailWidth = ratio.width, thumbnailHeight = ratio.height)
                    }.toTypedArray()

                    reactGallery {
                        attrs {
                            id = "pre-wedding"
                            enableImageSelection = false
                            margin = 1
                            images = galleryImages
                        }
                    }
                }
            }

            styledDiv {
                css {
                    backgroundColor = Color.white
                }
                styledIframe {
                    css {
                        border = "0"
                        margin(0.px)
                        padding(0.px)
                    }
                    attrs {
                        src = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3751.74940046674!2d99.84863131491393!3d19.892800986624636!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x30d7064f17cbc839%3A0x486f6aedfce304cc!2sKong+Garden+View+Resort+Chiang+Rai!5e0!3m2!1sen!2sth!4v1536967871345"
                        width = "100%"
                        height = "400"
                    }
                }
            }

            styledDiv {
                css {
                    backgroundColor = Color.white
                    margin(0.px)
                    padding(80.px, 0.px)
                }

                styledH1 {
                    +"SCHEDULE"
                }

                div {
                    +"On the 15th of September, 2018"
                }
                br {}

                styledTable {
                    css {
                        margin(LinearDimension.auto)
                    }
                    tbody {
                        tr {
                            styledTd {
                                css {
                                    width = 150.px
                                }
                                +"08.00 - 09.00"
                            }
                            styledTd {
                                css {
                                    textAlign = TextAlign.left
                                }
                                +"Welcome and Photos"
                            }
                        }
                        tr {

                            styledTd {
                                css {
                                    width = 100.px
                                }
                                +"09.00 - 09.30"
                            }
                            styledTd {
                                css {
                                    textAlign = TextAlign.left
                                }
                                +"Engagement"
                            }
                        }
                        tr {

                            styledTd {
                                css {
                                    width = 100.px
                                }
                                +"09.30 - 11.00"
                            }
                            styledTd {
                                css {
                                    textAlign = TextAlign.left
                                }
                                +"Traditional Wedding Ceremony"
                            }
                        }
                        tr {

                            styledTd {
                                css {
                                    width = 100.px
                                }
                                +"11.00 - 14.00"
                            }
                            styledTd {
                                css {
                                    textAlign = TextAlign.left
                                }
                                +"Launch and Photos"
                            }
                        }
                    }
                }

                styledImg(src = knot as? String) {
                    css { width = 100.px }
                }
            }
        }
    }

    private fun RBuilder.story(story: Story, position: StoryPosition, style: StoryStyles): ReactElement {
        return styledDiv {
            if (position == StoryPosition.TOP) {
                styledDiv {
                    css { +style.fixheight }
                    styledH4 {
                        css { +style.storytop }
                        story.icon?.let { icon ->
                            img(src = icon) {}
                        }
                        +story.title
                    }

                    styledP {
                        css { +style.story }
                        +story.details
                    }
                }

                styledH4 { css { +style.blankstory } }
                styledHr { css { +style.divider } }
                styledDiv { css { +style.storyemptyblock } }
            } else {
                styledDiv {

                    styledDiv { css { +style.storyemptyblock } }
                    styledHr { css { +style.divider } }
                    styledH4 { css { +style.blankstory } }
                    styledH4 {
                        css { +style.storybottom }
                        story.icon?.let { icon ->
                            img(src = icon) {}
                        }
                        +story.title
                    }
                    styledP {
                        css { +style.story }
                        +story.details
                    }
                }
            }
        }
    }

    private fun getImageRatio(index: Int): ImageRatio = when (index % 9) {
        2, 4, 6, 7 -> ImageRatio(4, 3)
        1, 5, 3 -> ImageRatio(1, 1)
        else -> ImageRatio(16, 9)
    }

    private data class ImageRatio(val width: Int, val height: Int)

    private enum class StoryPosition {
        TOP, BOTTOM
    }

    private inner class StoryStyles : StyleSheet("StoryStyles") {
        val layout by css {
            backgroundImage = Image("url('${props.storyImageUrl}')")
            backgroundRepeat = BackgroundRepeat.noRepeat
            backgroundPosition = "center"
            backgroundSize = "cover"
        }

        val cover by css {
            padding(40.px, 0.px)
            backgroundColor = Color.black.withAlpha(0.5)
        }

        val fixheight by css {
            height = 120.px
        }

        val storyemptyblock by css {
            height = 196.px
        }

        val storybottom by css {
            color = Color("#afc6d1")
            padding(10.px, 20.px, 0.px)
            margin(0.px)
        }

        val storytop by css {
            color = Color("#afc6d1")
            marginTop = 50.px
            marginBottom = 0.px
            padding(0.px, 20.px, 0.px)
        }

        val blankstory by css {
            marginBottom = 0.px
            marginTop = 0.px
            marginLeft = 50.pct
            width = 50.pct
            height = 26.px
            paddingTop = 0.px
            borderLeft = "1px solid #333"
        }

        val divider by css {
            width = 100.pct
            height = 0.px
            alignContent = Align.center
            alignItems = Align.center
            alignSelf = Align.center
            textAlign = TextAlign.center
            border = "0"
            borderTop = "1px solid #eee"
            borderColor = Color("#333")
            margin(0.px, LinearDimension.auto)
        }

        val story by css {
            color = Color("#fff");
            lineHeight = LineHeight("2em")
            padding(0.px, 20.px)
            margin(0.px)
        }
    }

    private inner class ComponentStyles(private val props: AppProps) : StyleSheet("ComponentStyles") {

        val coverImage by css {
            backgroundImage = Image("url('${props.coverImageUrl}')")
            backgroundRepeat = BackgroundRepeat.noRepeat
            backgroundPosition = "center"
            backgroundSize = "cover"

            width = props.windowWidth.px
            height = props.windowHeight.px

            position = Position.fixed
            left = LinearDimension.initial
            top = LinearDimension.initial
            zIndex = -999999
        }

        val cover by css {
            backgroundColor = Color.black.withAlpha(0.35)

            width = props.windowWidth.px
            height = props.windowHeight.px
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
                    coverImageUrl = coverImage
                    storyImageUrl = ourStoryImage
                }
            }
        }
    }
}