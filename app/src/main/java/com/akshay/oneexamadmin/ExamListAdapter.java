package com.akshay.oneexamadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshay.oneexamadmin.databinding.ExamLayoutItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ExamListVH> {


    Context context;
    List<Exam> exams = new ArrayList<>();
    List<ExamResult> examResults = new ArrayList<>();
    ExamListClick listner;
    String type = "EXAMS";

    public ExamListAdapter(Context context, ExamListClick listner) {
        this.context = context;
        this.listner = listner;
    }

    public ExamListAdapter(Context context, ExamListClick listner, String type) {
        this.context = context;
        this.type = type;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ExamListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExamLayoutItemBinding binding = ExamLayoutItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ExamListVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamListVH holder, int position) {

        if(type.equals("EXAMS")){
            Exam item = exams.get(position);
            holder.binding.examName.setText(item.getExamName());

            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onExamItemClick(item);
                }
            });
        } else {
            ExamResult examResult = examResults.get(position);
            holder.binding.examName.setText(examResult.getUserName());

            Date date = new Date();
            date.setTime(Long.parseLong(examResult.getTimestamp()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
            String dateSt = dateFormat.format(calendar.getTime());
            holder.binding.examAttemptTime.setText("Attempted on : " +dateSt);
            holder.binding.examAttemptTime.setVisibility(View.VISIBLE);

            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onResultItemClick(examResult);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return type.equals("EXAMS") ? exams.size() : examResults.size();
    }

    public void updateList(List<Exam> list){
        exams = list;
        notifyDataSetChanged();
    }

    public void updateResultList(List<ExamResult> list){
        examResults = list;
        notifyDataSetChanged();
    }

    class ExamListVH extends RecyclerView.ViewHolder{
        ExamLayoutItemBinding binding;
        public ExamListVH(@NonNull ExamLayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ExamListClick {
        void onExamItemClick(Exam exam);
        void onResultItemClick(ExamResult examResult);
    }
}
