package com.cuponstore.linkers.Activities.taxi.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.FirebaseStorage
import com.seguro.taxis.R
import com.seguro.taxis.models.TaxiModel

class TaxiAdapter(val activity: Activity, val context: Context, val listener: TaxiAdapter.TaxiAdapterDelegate)

    : RecyclerView.Adapter<TaxiAdapter.ServicesHolder>() {

    val taxiModel: ArrayList<TaxiModel> = arrayListOf()

    open interface TaxiAdapterDelegate {
        fun tapProfileTaxiButton(taxiModel: TaxiModel)
        fun tapCallButton(taxiModel: TaxiModel)
        fun tapWhatsAppCallButton(taxiModel: TaxiModel)
    }

    fun swap(datas: ArrayList<TaxiModel>?) {
        if (datas == null || datas.size == 0)
            return
        taxiModel.clear()
        taxiModel.addAll(datas)
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_STORE: Int = 0
        var delegate: TaxiAdapterDelegate? = null
    }

    override fun getItemCount() = taxiModel.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_taxi, parent, false)
        return ServicesHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_STORE
    }

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        delegate = listener

        holder.bindHolder(taxiModel[position], context)

        holder.containerTaxi.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val taxi = taxiModel[position]
                delegate?.tapProfileTaxiButton(taxi) } })

        holder.callTaxi.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val taxi = taxiModel[position]
                delegate?.tapCallButton(taxi) } })

        holder.whatsAppButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val taxi = taxiModel[position]
                delegate?.tapWhatsAppCallButton(taxi) } })
    }

    class ServicesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameSite: TextView = itemView.findViewById(R.id.nameSiteTextView)
        var containerTaxi: ConstraintLayout = itemView.findViewById(R.id.container_taxi)
        var schedule: TextView = itemView.findViewById(R.id.scheduleTextView)
        var nameTaxi: TextView = itemView.findViewById(R.id.nameTaxiTextView)
        var photoTaxi: ImageView = itemView.findViewById(R.id.photoTaxiImageView)
        var callTaxi: Button = itemView.findViewById(R.id.callTaxiButton)
        var whatsAppButton: Button = itemView.findViewById(R.id.whatsAppButton)
        var isAvailable: ImageView = itemView.findViewById(R.id.isAvailableImageView)


        fun bindHolder(taxiModel: TaxiModel, context: Context) {
            var storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            nameSite.text = taxiModel.nameSiteTaxi
            schedule.text = taxiModel.schedule
            nameTaxi.text = "¡Taxista! " + taxiModel.nameTaxi

            var isAvailableTaxi = taxiModel.isAvailableTaxi

            if (isAvailableTaxi == true) {
                isAvailable.setImageResource(R.drawable.btn_gradient_green_corner)
               // availableTitle.text = "Disponible"
            } else {
                isAvailable.setImageResource(R.drawable.btn_gradient_red_corner)
              //  availableTitle.text = "No disponible"
            }

            storageRef.child("taxis/${taxiModel.key}/profileTaxi.jpeg").downloadUrl.addOnSuccessListener {
                Glide.with(context)
                        .load(it)
                        .transform(CenterCrop(), RoundedCorners(15))
                        .placeholder(R.drawable.ic_profile_gray)
                        .into(photoTaxi)

            }.addOnFailureListener {
                print("error de carga de imagen ")
            }
        }
    }
}