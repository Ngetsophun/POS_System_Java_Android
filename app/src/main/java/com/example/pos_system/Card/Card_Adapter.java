package com.example.pos_system.Card;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pos_system.Product.ProductData;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;

import java.util.List;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.MyViewHolder>{

   List<CardData> cardData;
   Context context;

   UserDAO userDAO;
   UserDatabase userDatabase;
   ProductData productData;

    public Card_Adapter(List<CardData> cardData, Context context) {
        this.cardData = cardData;
        this.context = context;
        this.productData = productData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_show_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        Glide.with(context).load(productData.getImage()).into(holder.imgcard);

        Glide.with(context).load(cardData.get(position).getPro_cardimg()).into(holder.imgcard);
        holder.cardNameKh.setText(String.valueOf(cardData.get(position).Pro_cardNameKH));
        holder.cardNameEng.setText(String.valueOf(cardData.get(position).pro_cardNameEng));
        holder.cardPrice.setText(String.valueOf(cardData.get(position).Pro_cardPrice));
        holder.UpdateQty.setText(String.valueOf(cardData.get(position).Pro_cardQty));



        holder.imgdeletecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = UserDatabase.getUserDatabase(view.getContext()).userDAO();

                userDAO.DeleteCardById(cardData.get(position).Cardid);
                cardData.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = cardData.get(position).getPro_cardQty();
                if(qty>1){
                    qty--;
                    cardData.get(position).setPro_cardQty(qty);
                    notifyDataSetChanged();
                    UpdatePrice();
                }



            }
        });

        holder.Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = cardData.get(position).getPro_cardQty();
                qty++;
                cardData.get(position).setPro_cardQty(qty);
                notifyDataSetChanged();
                UpdatePrice();

            }
        });



    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cardNameEng,cardNameKh,cardPrice;
        ImageView imgcard,imgdeletecard;
        EditText UpdateQty;
        Button Minus,Plus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameEng = itemView.findViewById(R.id.Card_nameEng);
            cardNameKh = itemView.findViewById(R.id.Card_nameKH);
            cardPrice = itemView.findViewById(R.id.Card_Price);
            imgcard = itemView.findViewById(R.id.CardImage);
            UpdateQty = itemView.findViewById(R.id.editQty);
            imgdeletecard = itemView.findViewById(R.id.imgdelete_Card);
            Minus = itemView.findViewById(R.id.btnMinusd);
            Plus = itemView.findViewById(R.id.btnPlus);
        }
    }
    public void UpdatePrice(){
        int sum= 0,i;
        for(i=0; i<cardData.size();i++){
            sum = (int) (sum+(cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardPrice()));
        }



    }
}
