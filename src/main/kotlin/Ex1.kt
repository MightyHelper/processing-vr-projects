package ko.carbonel

import processing.core.PApplet
import processing.core.PImage
import processing.core.PVector

/**
 * Implementar, en Processing, un pequeño ambiente 3D donde se presenten 2 o más planos capaces de rotar sobre alguno de sus ejes, como se muestra en la siguiente imagen a modo de ejemplo, pero se debe proponer una figura original:
 *
 * El usuario debe poder aplicar (o cambiar) un sentido de rotación al conjunto de planos según 1 ejes de rotación (vertical u horizontal) al pulsar cualquiera de las 4 teclas de flecha, detener (al pulsar D) o bien reiniciar la animación (al pulsar R). Cuando se acciona ésta última, debe producirse un cambio en el diseño de cada superficie (ya sea mediante uso de imágenes diferentes o relleno con patrones).
 * Asuma un tipo de iluminación a su criterio, de modo de lograr un buen efecto/resalte.
 *
 */
class Ex1 : PApplet() {
    private var rotation = PVector(0f, 0f, 0f)
    private var rotationVelocity = PVector(0f, 0f, 0f)
    private var oldVelocity = PVector(0f, 0f, 0f)
    private var lastFrameTime = 0L
    private var planeColor = color(255, 0, 0)
    private var scale = 1f
    private var scaleV = 0f
    var img: PImage? = null


    override fun settings() {
        size(800, 600, P3D)
    }

    override fun setup() {
        background(0)
        textureMode(NORMAL)
        img = loadImage("src/main/resources/img.png")
    }

    override fun keyPressed() {
        when (keyCode) {
            LEFT -> rotationVelocity.y -= 1f
            RIGHT -> rotationVelocity.y += 1f
            UP -> rotationVelocity.x -= 1f
            DOWN -> rotationVelocity.x += 1f
            ' '.code -> rotationVelocity.z += 1f
            CONTROL -> rotationVelocity.z -= 1f
            'D'.code -> {
                oldVelocity = rotationVelocity.copy()
                rotationVelocity.mult(0f)
            }

            'R'.code -> {
                rotationVelocity = oldVelocity.copy()
                img?.filter(BLUR, 1f)
                planeColor = color(random(255f), random(255f), random(255f))

            }

            'K'.code -> scaleV += 0.5f
            'L'.code -> scaleV -= 0.5f
        }
    }

    override fun draw() {
        background(0f)
        ambientLight(50f, 50f, 50f)
        translate(width / 2f, height / 2f, 0f)
        scale(scale)
        fill(0f, 255f, 0f)
        stroke(0f, 255f, 0f)
        sphere(8f)
        computePhysics(deltaTime())
        rotateX(rotation.x)
        rotateY(rotation.y)
        rotateZ(rotation.z)
        addPointLight()
        addBox1()
        addBox2()
    }

    private fun computePhysics(delta: Float) {
        rotation.add(rotationVelocity.copy().mult(delta))
        rotationVelocity.mult(pow(0.2f, delta))
        scale += scaleV * delta
        scaleV *= pow(0.2f, delta)
    }

    private fun addBox2() {
        noStroke()
        noFill()

        beginShape()
        texture(img)
        vertex(-250f, -250f, 0f, 0f, 0f)
        vertex(250f, -250f, 0f, 1f, 0f)
        vertex(250f, 250f, 0f, 1f, 1f)
        vertex(-250f, 250f, 0f, 0f, 1f)
        endShape(CLOSE)
        noTexture()
    }

    private fun addBox1() {
        stroke(planeColor)
        fill(planeColor)
        box(500f, 1f, 500f)
    }

    private fun addPointLight() {
        pushMatrix()
        translate(mouseX - width / 2f, mouseY - height / 2f, 90f)
        fill(255)
        noStroke()
        pointLight(51f, 102f, 126f, 140f, 160f, 144f)
        sphere(5f)
        popMatrix()
    }

    private fun deltaTime(): Float {
        val currentTime = System.currentTimeMillis()
        val deltaTime = currentTime - lastFrameTime
        lastFrameTime = currentTime
        return deltaTime.toFloat() / 1000f
    }

}