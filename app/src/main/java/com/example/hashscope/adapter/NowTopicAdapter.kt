import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hashscope.databinding.ItemCardBinding
import com.example.hashscope.model.MainTopic

class NowTopicAdapter(private val topics: List<MainTopic>) :
    RecyclerView.Adapter<NowTopicAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = topics[position]
        holder.binding.tvTitle.text = topic.topics_name
        holder.binding.tvSubtitle.text = "Frequency: ${topic.frequency}"
    }

    override fun getItemCount() = topics.size
}
