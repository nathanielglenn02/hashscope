import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hashscope.databinding.ItemCardBinding
import com.example.hashscope.model.PredictedTopic

class PredictedTopicAdapter(private val topics: List<PredictedTopic>) :
    RecyclerView.Adapter<PredictedTopicAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = topics[position]
        holder.binding.tvTitle.text = topic.top_topic
        holder.binding.tvSubtitle.text = "Predicted Frequency: ${topic.predicted_frequency}"
    }

    override fun getItemCount() = topics.size
}
