package com.akshay.oneexamadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.akshay.oneexamadmin.databinding.DialogSelectBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectDialog extends DialogFragment {

        List<String> items = new ArrayList<>();
        String title;
        OnDialogSelect listner;
        String tag = "";

    public SelectDialog(List<String> items, String title, OnDialogSelect listner, String tag) {
        this.items = items;
        this.title = title;
        this.listner = listner;
        this.tag = tag;
    }

    DialogSelectBinding binding;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = DialogSelectBinding.inflate(getLayoutInflater());
            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            binding.dialogTitle.setText(title);

            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

            binding.selectRecycler.setAdapter(itemsAdapter);

            binding.selectRecycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    listner.onSelectItem(items.get(i), tag);
                    dismiss();
                }
            });
        }
        

        public interface OnDialogSelect {
            void onSelectItem(String item,String tag);
        }
        
}