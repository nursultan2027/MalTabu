package kz.maltabu.app.maltabukz.Redesign.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.add_post_redesign.*
import kotlinx.android.synthetic.main.tab_item.*
import kz.maltabu.app.maltabukz.R
import kz.maltabu.app.maltabukz.activities.AddPostActivity2
import kz.maltabu.app.maltabukz.activities.MainActivity2
import kz.maltabu.app.maltabukz.helpers.FileHelper
import kz.maltabu.app.maltabukz.helpers.LocaleHelper
import kz.maltabu.app.maltabukz.helpers.Maltabu
import kz.maltabu.app.maltabukz.models.Catalog
import kz.maltabu.app.maltabukz.models.Category
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor
import org.json.JSONException
import org.json.JSONObject

class AddPostRedesign : AppCompatActivity() {
    private lateinit var epicDialog: Dialog
    private val fileHelper = FileHelper(this)
    private lateinit var dict : JSONObject
    private var categories: ArrayList<Category> = ArrayList()
    private var buttons: ArrayList<Button> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.add_post_redesign)
        epicDialog = Dialog(this)
        buttons.add(spinner1 as Button)
        buttons.add(spinner2 as Button)
        buttons.add(spinner3 as Button)
        buttons.add(spinner4 as Button)
        buttons.add(spinner5 as Button)
        buttons.add(spinner6 as Button)
        buttons.add(spinner7 as Button)
        getCategories()
        initView()
    }

    private fun initView() {
        val lang = Paper.book().read<Any>("language") as String
        val context = LocaleHelper.setLocale(this, lang)
        Maltabu.lang = lang
        val resources = context.resources
        arrr.setOnClickListener{
            onBackPressed()
        }
        textView.text = resources.getString(R.string.addPost)
        chooseCatalogCategory.text = resources.getString(R.string.chooseCategoryCatalog)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }

    @Throws(JSONException::class)
    private fun getCategories() {
        dict = fileHelper.diction()
        for (i in 0 until fileHelper.categoriesFromFile.size) {
            val category = fileHelper.categoriesFromFile[i]
            categories.add(category)
            if (Maltabu.lang == "ru") run {
                buttons[i].text = category.name
            }
            else {
                var kazName = dict.getString(category.name)
                if (kazName.toLowerCase() == "жем") {
                    kazName = "Жем-шөп"
                }
                buttons[i].text = kazName
            }
            buttons[i].setOnClickListener{
                catalogsDialog(i)
            }
        }
    }

    protected fun sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog)
        epicDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        epicDialog.show()
    }

    protected fun catalogsDialog(position: Int) {
        epicDialog.setContentView(R.layout.choose_catalog_dialog)
        val lay = epicDialog.findViewById(R.id.layout) as LinearLayout
        val category = categories[position]
        for(i in 0 until category.catalogs.size){
            val text = TextView(this)
            text.textColor = Color.parseColor("#000000")
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            val llp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(10, 20, 0, 20)
            text.layoutParams = llp
            if (Maltabu.lang == "ru") run {
                text.text = category.catalogs[i].name
            }
            else {
                var kazName = dict.getString(category.catalogs[i].name)
                if (kazName.toLowerCase() == "бараны") {
                    kazName = "Овцы"
                }
                text.text = kazName
            }
            text.setOnClickListener {
                sDialog()
                val thread = SecondThread(position, i).start()
            }
            lay.addView(text)
        }
        epicDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        epicDialog.show()
    }

    fun Go(position: Int, finalI: Int) {
        val intent2 = Intent(this, AddPostActivity2::class.java)
        var catalog: Catalog? = null
        catalog = categories[finalI].catalogs[position]
        intent2.putExtra("catalog", catalog)
        startActivity(intent2)
    }

    inner class SecondThread internal constructor(internal var i: Int, internal var p: Int) : Thread() {
        override fun run() {
            Go(p, i)
        }
    }

}