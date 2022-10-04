package com.utn.primerparciallauria.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.entities.Character

class CharacterAdapter(private var characterList : MutableList <Character>,
                       private var onClick : (Int) -> Unit) : RecyclerView.Adapter<CharacterAdapter.CharacterHolder> (){

    class CharacterHolder (v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init {
            this.view = v
        }

        fun setName (name : String) {
            var txtName : TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }

        fun setImg (img : String) {
            var imgCircle: ImageView = view.findViewById((R.id.imgCircle))
            var imgUrl = img

            Glide.with(view)
                .load(imgUrl)
                .circleCrop()
                .into(imgCircle)
        }

        fun getCard () : CardView {
            return view.findViewById(R.id.cardInstrument)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_character,parent,false)
        return (CharacterHolder(view))
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.setName(characterList[position].name)
        holder.setImg(characterList[position].imgAvatar)

        holder.getCard().setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}
