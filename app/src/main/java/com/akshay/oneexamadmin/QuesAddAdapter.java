package com.akshay.oneexamadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshay.oneexamadmin.databinding.QuestAddLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class QuesAddAdapter extends RecyclerView.Adapter<QuesAddAdapter.QuestAddVH> {


    Context context;
    List<Question> list = new ArrayList<>();
    QuesAction listner;

    public QuesAddAdapter(Context context,QuesAction listner) {
        this.context = context;
        this.listner = listner;
    }



    @NonNull
    @Override
    public QuestAddVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestAddLayoutBinding binding = QuestAddLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new QuestAddVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestAddVH holder, int position) {
        Question item = list.get(position);
        holder.binding.question.setText(item.getQuestionTitle());

        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.deleteQuest(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public void updateList(List<Question> list){
        this.list = list;
        notifyDataSetChanged();
    }

    class QuestAddVH extends RecyclerView.ViewHolder{

        QuestAddLayoutBinding binding;
        public QuestAddVH(@NonNull QuestAddLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface QuesAction {
        void deleteQuest(Question question);
    }
}
