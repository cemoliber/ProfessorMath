import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.math2.R
import com.example.math2.ReportCardItem

class ReportCardAdapter(context: Context, private val reportList: List<ReportCardItem>) : ArrayAdapter<ReportCardItem>(context, 0, reportList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val reportCard = reportList[position]

        // Eğer mevcut bir view yoksa yeni bir tane oluştur
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        // View öğelerini ayarlama
        val noteCard = view.findViewById<TextView>(R.id.noteCard)
        val trueCountCard = view.findViewById<TextView>(R.id.trueCountCard)
        val wrongCountCard = view.findViewById<TextView>(R.id.wrongCountCard)
        val levelCard = view.findViewById<TextView>(R.id.levelCard)
        val operationCard = view.findViewById<TextView>(R.id.operationCard)

        // Verileri yerleştir
        noteCard.text = reportCard.note.toString()
        trueCountCard.text = "Doğru Sayısı: ${reportCard.trueCount}"
        wrongCountCard.text = "Yanlış Sayısı: ${reportCard.wrongCount}"
        levelCard.text = "Seviye: ${reportCard.level}"
        operationCard.text = "İşlem: ${reportCard.operation}"

        return view
    }
}
