package com.example.to_dolist;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.Adapter.ToDoAdapter;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback{

    ToDoAdapter adapter;
    public RecyclerViewTouchHelper(ToDoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.delete(position); // assuming you meant adapter.delete()
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyItemChanged(position); // reset swipe state
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            adapter.editItems(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(adapter.getContext(), R.color.red))
                .addSwipeRightActionIcon(R.drawable.delete)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapter.getContext(), R.color.green))
                .addSwipeLeftActionIcon(R.drawable.edit)
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
