package com.example.movieapp.activities


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.movieapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {

    private var featureGraphicImageView: ImageView? = null

    private var shareImageButton: ImageButton? = null
    private var rateUsImageButton: ImageButton? = null
    private var feedbackImageButton: ImageButton? = null

    private var sourceCodeOnGitHubCardView: CardView? = null

    private var openSourceLicensesFrameLayout: FrameLayout? = null
    private var versionNumberTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.movieapp.R.layout.activity_about)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.about)
        featureGraphicImageView =
            findViewById<View>(R.id.image_view_feature_graphic_about) as ImageView
        featureGraphicImageView!!.getLayoutParams().width = resources.displayMetrics.widthPixels
        featureGraphicImageView!!.getLayoutParams().height =
            (resources.displayMetrics.widthPixels.toDouble() * (500.0 / 1024.0)).toInt()
        shareImageButton = findViewById<View>(R.id.image_button_share_about) as ImageButton
        rateUsImageButton = findViewById<View>(R.id.image_button_rate_us_about) as ImageButton
        feedbackImageButton = findViewById<View>(R.id.image_button_feedback_about) as ImageButton
        sourceCodeOnGitHubCardView =
            findViewById<View>(R.id.card_view_source_code_on_github) as CardView
        openSourceLicensesFrameLayout =
            findViewById<View>(com.example.movieapp.R.id.frame_layout_open_source_licenses) as FrameLayout
        versionNumberTextView = findViewById<View>(com.example.movieapp.R.id.text_view_version_number) as TextView
        loadActivity()
    }

    private fun loadActivity() {
        shareImageButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                val packageName = applicationContext.packageName
                val appShareIntent = Intent(Intent.ACTION_SEND)
                appShareIntent.type = "text/plain"
                var extraText = "Hey! Check out this amazing app.\n"
                extraText += "https://play.google.com/store/apps/details?id=$packageName"
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText)
                startActivity(appShareIntent)
            }
        })
        rateUsImageButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                val packageName = applicationContext.packageName
                val playStoreLink =
                    "https://play.google.com/store/apps/details?id=$packageName"
                val appRateUsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink))
                startActivity(appRateUsIntent)
            }
        })
        feedbackImageButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                val feedbackIntent = Intent(Intent.ACTION_SENDTO)
                feedbackIntent.data = Uri.parse("mailto:")
                feedbackIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("sbl.gencturks@gmail.com"))
                feedbackIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Feedback: " + resources.getString(R.string.app_name)
                )
                startActivity(feedbackIntent)
            }
        })
        sourceCodeOnGitHubCardView!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                val githubLink =
                    "https://github.com/SibelG/" + resources.getString(com.example.movieapp.R.string.app_name)
                val githubIntent = Intent(Intent.ACTION_VIEW, Uri.parse(githubLink))
                startActivity(githubIntent)
            }
        })
        openSourceLicensesFrameLayout!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                val attributionsLink =
                    "https://github.com/SibelG/" + resources.getString(com.example.movieapp.R.string.app_name) + "/blob/master/ATTRIBUTIONS.md"
                val attributionsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(attributionsLink))
                startActivity(attributionsIntent)
            }
        })
        try {
            versionNumberTextView!!.text = packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}