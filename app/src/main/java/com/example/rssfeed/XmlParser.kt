package com.example.rssfeed

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.lang.IllegalStateException

class XmlParser {
    private val ns: String? = null

    fun parse(inputStream: InputStream): List<Question>{
        inputStream.use {
            inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readRssFeed(parser)
        }
    }

    private fun readRssFeed(parser: XmlPullParser): List<Question> {
        val questions = mutableListOf<Question>()

        parser.require(XmlPullParser.START_TAG, ns, "feed")
        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.eventType != XmlPullParser.START_TAG){
                continue
            }
            if(parser.name == "entry"){
                parser.require(XmlPullParser.START_TAG, ns, "entry")
                var title: String? = null
                var link: String? = ""
                var author: String? = ""
                while (parser.next() != XmlPullParser.END_TAG){
                    if(parser.eventType != XmlPullParser.START_TAG){
                        continue
                    }
                    when(parser.name){
                        "title" -> title = readTitle(parser)
                        "author" -> author = readAuthor(parser)
//                        "link" -> link = readLink(parser)
                        else -> skip(parser)
                    }
                }
                questions.add(Question(title, link,author))
            } else {
                skip(parser)
            }
        }
        return questions
    }

    private fun skip(parser: XmlPullParser) {
        if(parser.eventType != XmlPullParser.START_TAG){
            throw IllegalStateException()
        }
            var depth = 1
            while (depth != 0){
                when(parser.next()){
                    XmlPullParser.END_TAG -> depth--
                    XmlPullParser.START_TAG -> depth++
                }
            }
    }

    private fun readTitle(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    private fun readAuthor(parser: XmlPullParser): String? {
        var name : String? = ""
        parser.require(XmlPullParser.START_TAG, ns, "author")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name){
                "name" -> {name = readText(parser)}
                else -> skip(parser)
            }
        }
        return name
    }

    private fun readLink(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "link")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "link")
        return title
    }

    private fun readText(parser: XmlPullParser): String? {
        var result = ""
        if(parser.next() == XmlPullParser.TEXT){
            result = parser.text
            parser.nextTag()
        }
        return result
    }





}
