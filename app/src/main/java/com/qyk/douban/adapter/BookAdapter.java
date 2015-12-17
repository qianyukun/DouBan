package com.qyk.douban.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qyk.douban.R;
import com.qyk.douban.model.Book;
import com.qyk.douban.utils.StringNotNull;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewHolder> {
    private List<Book> books;
    private Context context;

    public BookAdapter(List<Book> books, Context context) {
        this.context = context;
        this.books = books;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        String author="";
        if(book.getAuthor()!=null&&book.getAuthor().length>0){
            author=StringNotNull.NotNullString(book.getAuthor()[0]);
        }
        String desc = "作者: " + author
                + "\n副标题: " + StringNotNull.NotNullString(book.getSubtitle())
                + "\n出版年: " + StringNotNull.NotNullString(book.getPubdate())
                + "\n页数: "   + StringNotNull.NotNullString(book.getPages())
                + "\n定价:" + StringNotNull.NotNullString(book.getPrice());
        holder.info.setText(desc);
        Glide.with(holder.bookimage.getContext())
                .load(book.getImage())
                .fitCenter()
                .into(holder.bookimage);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView info;
        private ImageView bookimage;

        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_book_title);
            info = (TextView) itemView.findViewById(R.id.tv_book_short_info);
            bookimage = (ImageView) itemView.findViewById(R.id.image_book);
        }
    }
}
