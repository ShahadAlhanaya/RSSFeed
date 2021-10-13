package com.example.rssfeed

import QuestionsAdapter
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var questionsList = mutableListOf<Question>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialize recyclerView
        recyclerView = findViewById(R.id.recycler_view)

        FetchQuestions().execute()
    }

    private inner class FetchQuestions : AsyncTask<Void, Void, MutableList<Question>>() {
        val parser = XmlParser()
        override fun doInBackground(vararg p0: Void?): MutableList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            questionsList = urlConnection.inputStream?.let {
                parser.parse(it)
            } as MutableList<Question>
            return questionsList
        }

        override fun onPostExecute(result: MutableList<Question>?) {
            super.onPostExecute(result)

            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = QuestionsAdapter(questionsList, this@MainActivity)
            recyclerView.adapter?.notifyDataSetChanged()
        }


    }


}