package app.components

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.br
import react.dom.div
import react.dom.tbody
import react.dom.tr
import styled.*

@JsModule("src/app/images/knot.png")
external val scheduleKnot: dynamic

class ScheduleComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
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

            styledImg(src = scheduleKnot as? String) {
                css { width = 100.px }
            }
        }
    }
}

fun RBuilder.schedule() = child(ScheduleComponent::class) {
}