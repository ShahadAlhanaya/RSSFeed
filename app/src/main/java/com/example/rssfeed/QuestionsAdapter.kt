import com.example.rssfeed.R

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.Question
import kotlinx.android.synthetic.main.student_item.view.*


class QuestionsAdapter(private val questionsList: List<Question>, private val context: Context) :
    RecyclerView.Adapter<QuestionsAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.tv_title
        val authorTextView: TextView = itemView.tv_author
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.student_item,
            parent,
            false
        )
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val title = questionsList[position].title
        val author = questionsList[position].author
        val link = questionsList[position].link
        holder.titleTextView.text = title
        holder.authorTextView.text = author
//        holder.userItemLinearLayout.setOnClickListener {
//            val intent = Intent(context, EditActivity::class.java)
//            intent.putExtra("userName", userName)
//            intent.putExtra("userLocation", userLocation)
//            intent.putExtra("pk", pk)
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount() = questionsList.size
}