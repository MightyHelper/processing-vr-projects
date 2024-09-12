package ko.carbonel

import processing.core.PApplet

class MainScreen : PApplet() {
    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        background(0)
    }

    override fun draw() {
        fill(255)
        ellipse(mouseX.toFloat(), mouseY.toFloat(), 80f, 80f)
    }
}

fun main() {
    PApplet.main(Ex1::class.java)
}