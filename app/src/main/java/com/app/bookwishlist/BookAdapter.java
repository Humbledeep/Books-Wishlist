package com.app.bookwishlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> mData;
    private LayoutInflater mInflater;
    private WishListDBHelper bookDBHelper;

    BookAdapter(Context context, List<Book> data, WishListDBHelper dbHelper) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.bookDBHelper = dbHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mData.get(position);
        holder.bookText.setText(book.getTitle());
        holder.textViewGenre.setText(book.getGenre());
        holder.textViewAuthor.setText("By: "+book.getAuthor());
        holder.bookText.setPaintFlags(book.isRead() ? Paint.STRIKE_THRU_TEXT_FLAG : 0);
        holder.bookCheckBox.setChecked(book.isRead());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookText,textViewAuthor,textViewGenre;
        CheckBox bookCheckBox;
ImageButton deleteButton;
ImageButton editButton;

LinearLayout linearLayout;
        ViewHolder(View itemView) {
            super(itemView);
            bookText = itemView.findViewById(R.id.todo_text);
            textViewAuthor = itemView.findViewById(R.id.author);
            textViewGenre = itemView.findViewById(R.id.genre);
            bookCheckBox = itemView.findViewById(R.id.todo_checkbox);
			deleteButton = itemView.findViewById(R.id.delete_button);
			editButton = itemView.findViewById(R.id.edit_button);
			linearLayout = itemView.findViewById(R.id.parents);


            // Set an OnClickListener for the checkbox
            bookCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the corresponding book
                        Book book = mData.get(position);

                        // Toggle the Read status
                        book.setRead(!book.isRead());

                        // Update the checkbox state
                        notifyItemChanged(position);
                        bookDBHelper.updateItemStatus(book.getId(), book.isRead());


                        view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                        ((Activity)view.getContext()).overridePendingTransition(0, 0);
                        ((Activity)view.getContext()).finish();

                    }
                }
            });
			
			// Set an OnClickListener for the Delete button
			deleteButton.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View view){
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION){
							Book book = mData.get(position);
							
							bookDBHelper.deleteBook(book.getId());
							mData.remove(position);
							
							notifyItemRemoved(position);
                            Toast.makeText(view.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                            view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                            ((Activity)view.getContext()).overridePendingTransition(0, 0);
                            ((Activity)view.getContext()).finish();
						}
					}
				});

            editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Book book = mData.get(position);

                       // bookDBHelper.updateBook(book.getId(),book);
                        Intent intent=new Intent(view.getContext(),EditBookActivity.class);
                        intent.putExtra("name",book.getTitle());
                        intent.putExtra("author",book.getAuthor());
                        intent.putExtra("date",String.valueOf(book.getPublicationYear()));
                        intent.putExtra("id",String.valueOf(book.getId()));
                        view.getContext().startActivity(intent);

                    }
                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Book book = mData.get(position);

                        // bookDBHelper.updateBook(book.getId(),book);
                        Intent intent=new Intent(view.getContext(),DetailActivity.class);
                        intent.putExtra("name",book.getTitle());
                        intent.putExtra("author",book.getAuthor());
                        intent.putExtra("genre",book.getGenre());
                        intent.putExtra("publication",String.valueOf(book.getPublicationYear()));
                        intent.putExtra("id",String.valueOf(book.getId()));
                        view.getContext().startActivity(intent);

                    }
                }
            });


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Handling item click here

        }
    }
	
	public void addItem(Book book) {
		mData.add(book);
	}
}
