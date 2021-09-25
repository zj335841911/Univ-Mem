package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bean.WordList;
import com.example.univ_mem.MyApplication;
import com.example.univ_mem.R;
import com.example.univ_mem.WordListRecyclerViewListener;

import java.util.List;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/6/24.
 * TIME : 18:48.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder>  {
    WordListRecyclerViewListener recycylerViewListener;
    private List<WordList> wordLists;
    private Context context= MyApplication.getContext();

    public WordListAdapter( WordListRecyclerViewListener recycylerViewListener, List<WordList> wordLists) {
        this.recycylerViewListener = recycylerViewListener;
        this.wordLists = wordLists;

    }

    private static final int[] colors = new int[]{R.color.color_0,R.color.color_1,
            R.color.color_2,R.color.color_3,R.color.color_4,
            R.color.color_5,R.color.color_6,R.color.color_7,
            R.color.color_8,R.color.color_9,R.color.color_10,};

    public WordListAdapter( List<WordList> wordLists) {
        this.wordLists = wordLists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView headWord;
        TextView phonetic;
        TextView quickDefinition;
        public ViewHolder(@NonNull View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.wordActivity_cardView);
            headWord = (TextView) view.findViewById(R.id.wordActivity_search_text_headWord);
            phonetic = (TextView) view.findViewById(R.id.wordActivity_search_text_phonetic);
            quickDefinition = (TextView) view.findViewById(R.id.wordActivity_search_text_quickDefinition);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        final View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.word_search_recyclerview_item, parent, false);
        //注册点击事件
        final ViewHolder viewHolder = new ViewHolder(view);
        //   int positon=viewHolder.getAdapterPosition();这两句放这里会出现ArrayList,outofbound,length=10.index=-1,所以要放在下面两个
        // final Note note = notes.get(positon);//点击事件里面，具体原因不详
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon=viewHolder.getAdapterPosition();
               WordList wordList = wordLists.get(positon);
                recycylerViewListener.onItemClick( viewHolder.cardView,  wordList);
            }
        });
        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int positon=viewHolder.getAdapterPosition();
                WordList wordList = wordLists.get(positon);
                recycylerViewListener.onItemLongClick(viewHolder.cardView,wordList);
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordList wordList=wordLists.get(position);
        int id=wordList.getId();
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(colors[id%11]));
        holder.headWord.setTextColor(Color.rgb(0, 0, 0));
        holder.headWord.setText(wordList.getHeadword());
        holder.phonetic.setText(wordList.getPhonetic());
        holder.quickDefinition.setText(wordList.getQuickdefinition());
    }

    @Override
    public int getItemCount() {
        if (wordLists==null) {
            return 0;
        }
        else return wordLists.size();
    }

    public void refreshShow(List<WordList> wordLists) {
        this.wordLists=wordLists;
        notifyDataSetChanged();
    }
}
