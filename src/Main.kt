/**
 * ===============================================================
 * Kotlin GUI Basic Starter
 * ===============================================================
 *
 * This is a starter project for a simple Kotlin GUI application.
 * The Java Swing library is used, plus the FlatLAF look-and-feel
 * for a reasonably modern look.
 */

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import java.lang.Math.clamp
import javax.swing.*
import javax.swing.border.Border
import javax.swing.text.MaskFormatter


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    MainWindow()            // Create and show the UI
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow : JFrame(), ActionListener, KeyListener {

    // Fields to hold the UI elements
    private lateinit var redLabel: JLabel
    private lateinit var greenLabel: JLabel
    private lateinit var blueLabel: JLabel

    private lateinit var redUpButton: JButton
    private lateinit var redDownButton: JButton
    private lateinit var greenUpButton: JButton
    private lateinit var greenDownButton: JButton
    private lateinit var blueUpButton: JButton
    private lateinit var blueDownButton: JButton

    private lateinit var redText: JTextField
    private lateinit var greenText: JTextField
    private lateinit var blueText: JTextField

    private lateinit var colourPatch: JLabel

    private lateinit var colourHex: JLabel

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Colour Picker"
        contentPane.preferredSize = Dimension(520, 320)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val defaultFont = Font(Font.SANS_SERIF, Font.PLAIN, 24)
        val bigFont = Font(Font.SANS_SERIF, Font.PLAIN, 50)
        val hugeFont = Font(Font.SANS_SERIF, Font.PLAIN, 90)

        // LABELS -----------------------------------------

        redLabel = JLabel("R")
        redLabel.horizontalAlignment = SwingConstants.CENTER
        redLabel.bounds = Rectangle(20, 20, 70, 70)
        redLabel.font = hugeFont
        redLabel.foreground = Color.RED
        add(redLabel)

        greenLabel = JLabel("G")
        greenLabel.horizontalAlignment = SwingConstants.CENTER
        greenLabel.bounds = Rectangle(20, 120, 70, 70)
        greenLabel.font = hugeFont
        greenLabel.foreground = Color.GREEN
        add(greenLabel)

        blueLabel = JLabel("B")
        blueLabel.horizontalAlignment = SwingConstants.CENTER
        blueLabel.bounds = Rectangle(20, 220, 70, 70)
        blueLabel.font = hugeFont
        blueLabel.foreground = Color.BLUE
        add(blueLabel)

        // TEXT -----------------------------------------

        redText = JTextField("0")
        redText.horizontalAlignment = SwingConstants.CENTER
        redText.bounds = Rectangle(100, 20, 100, 70)
        redText.font = bigFont
        redText.background = Color.BLACK
        redText.foreground = Color.WHITE
        redText.addActionListener(this)
        redText.addKeyListener(this)
        add(redText)

        greenText = JTextField("0")
        greenText.horizontalAlignment = SwingConstants.CENTER
        greenText.bounds = Rectangle(100, 120, 100, 70)
        greenText.font = bigFont
        greenText.background = Color.BLACK
        greenText.foreground = Color.WHITE
        greenText.addActionListener(this)
        greenText.addKeyListener(this)
        add(greenText)

        blueText = JTextField("0")
        blueText.horizontalAlignment = SwingConstants.CENTER
        blueText.bounds = Rectangle(100, 220, 100, 70)
        blueText.font = bigFont
        blueText.background = Color.BLACK
        blueText.foreground = Color.WHITE
        blueText.addActionListener(this)
        blueText.addKeyListener(this)
        add(blueText)

        // BUTTONS -----------------------------------------

        redUpButton = JButton("▲")
        redUpButton.bounds = Rectangle(220, 20, 30, 30)
        redUpButton.font = defaultFont
        redUpButton.foreground = Color.RED
        redUpButton.addActionListener(this)     // Handle any clicks
        add(redUpButton)

        redDownButton = JButton("▼")
        redDownButton.bounds = Rectangle(220, 60, 30, 30)
        redDownButton.font = defaultFont
        redDownButton.foreground = Color.RED
        redDownButton.addActionListener(this)     // Handle any clicks
        add(redDownButton)

        greenUpButton = JButton("▲")
        greenUpButton.bounds = Rectangle(220, 120, 30, 30)
        greenUpButton.font = defaultFont
        greenUpButton.foreground = Color.GREEN
        greenUpButton.addActionListener(this)     // Handle any clicks
        add(greenUpButton)

        greenDownButton = JButton("▼")
        greenDownButton.bounds = Rectangle(220, 160, 30, 30)
        greenDownButton.font = defaultFont
        greenDownButton.foreground = Color.GREEN
        greenDownButton.addActionListener(this)     // Handle any clicks
        add(greenDownButton)

        blueUpButton = JButton("▲")
        blueUpButton.bounds = Rectangle(220, 220, 30, 30)
        blueUpButton.font = defaultFont
        blueUpButton.foreground = Color.BLUE
        blueUpButton.addActionListener(this)     // Handle any clicks
        add(blueUpButton)

        blueDownButton = JButton("▼")
        blueDownButton.bounds = Rectangle(220, 260, 30, 30)
        blueDownButton.font = defaultFont
        blueDownButton.foreground = Color.BLUE
        blueDownButton.addActionListener(this)     // Handle any clicks
        add(blueDownButton)

        // COLOUR -----------------------------------------

        colourPatch = JLabel()
        colourPatch.bounds = Rectangle(280, 20, 220, 170)
        colourPatch.isOpaque = true
        colourPatch.background = Color.BLACK
        colourPatch.border = BorderFactory.createLineBorder(Color.BLACK, 2)
        add(colourPatch)

        colourHex = JLabel("#000000")
        colourHex.bounds = Rectangle(280, 220, 220, 70)
        colourHex.horizontalAlignment = SwingConstants.CENTER
        colourHex.font = bigFont
        colourHex.foreground = Color.WHITE
        add(colourHex)
    }


    /**
     * Handle any UI events (e.g. button clicks)
     */
    override fun actionPerformed(e: ActionEvent?) {
        var redValue = redText.text.toIntOrNull() ?: 0
        var greenValue = greenText.text.toIntOrNull() ?: 0
        var blueValue = blueText.text.toIntOrNull() ?: 0

        when (e?.source) {
            redUpButton -> redValue++
            redDownButton -> redValue--
            greenUpButton -> greenValue++
            greenDownButton -> greenValue--
            blueUpButton -> blueValue++
            blueDownButton -> blueValue--
        }

        redValue = (redValue + 256) % 256
        greenValue = (greenValue + 256) % 256
        blueValue = (blueValue + 256) % 256

        redText.text = redValue.toString()
        greenText.text = greenValue.toString()
        blueText.text = blueValue.toString()

        updateUI()
    }

    fun updateUI() {
        val redValue = redText.text.toInt()
        val greenValue = greenText.text.toInt()
        val blueValue = blueText.text.toInt()

        redText.background = Color(redValue, 0, 0)
        greenText.background = Color(0, greenValue, 0)
        blueText.background = Color(0, 0, blueValue)

        colourPatch.background = Color(redValue, greenValue, blueValue)

        val redHex = "%02X".format(redValue)
        val greenHex = "%02X".format(greenValue)
        val blueHex = "%02X".format(blueValue)

        colourHex.text = "#$redHex$greenHex$blueHex"
    }

    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {

    }

    override fun keyReleased(e: KeyEvent?) {
        var redValue = redText.text.toIntOrNull() ?: 0
        var greenValue = greenText.text.toIntOrNull() ?: 0
        var blueValue = blueText.text.toIntOrNull() ?: 0

        redValue = clamp(redValue.toLong(),  0, 255)
        greenValue = clamp(greenValue.toLong(),  0, 255)
        blueValue = clamp(blueValue.toLong(),  0, 255)

        redText.text = redValue.toString()
        greenText.text = greenValue.toString()
        blueText.text = blueValue.toString()

        updateUI()
    }
}

