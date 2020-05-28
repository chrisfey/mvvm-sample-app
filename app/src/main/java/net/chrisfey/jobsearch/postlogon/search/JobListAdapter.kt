package net.chrisfey.jobsearch.postlogon.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_job_item.view.*
import net.chrisfey.jobsearch.R


class JobListAdapter internal constructor(context: Context, private val clickListener: (JobViewState) -> Unit) :
    RecyclerView.Adapter<JobListAdapter.JobViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mJobs: List<JobViewState>? = null // Cached copy of Jobs
    // getItemCount() is called many times, and when it is first called,
    // mJobs has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mJobs != null)
            mJobs!!.size
        else
            0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = mInflater.inflate(R.layout.view_job_item, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        if (mJobs != null) {
            val context = holder.itemView.context
            val current = mJobs!![position]
            holder.title.text = current.title
            holder.company.text = current.company
            holder.sourceImg.setImageDrawable(ContextCompat.getDrawable(context, current.source.icon))
            if (current.companyImg == null) {
                holder.companyLogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_image_black_24dp
                    )
                )
            } else {
                Glide
                    .with(context)
                    .load(current.companyImg)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(holder.companyLogo)
            }
            holder.itemView.setOnClickListener { clickListener.invoke(current) }

        } else {
            // Covers the case of data not being ready yet.
            holder.title.text = ""
            holder.company.text = ""
        }

    }

    internal fun setJobs(Jobs: List<JobViewState>) {
        mJobs = Jobs
        notifyDataSetChanged()
    }

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val title: TextView = itemView.title
        internal val company: TextView = itemView.company
        internal val sourceImg: ImageView = itemView.source
        internal val companyLogo: ImageView = itemView.companyLogo

    }
}


