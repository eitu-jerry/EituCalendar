package com.notegg.yoohoo.calendar;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.notegg.yoohoo.R;

public class CalendarPageAdapter extends RecyclerView.Adapter<CalendarPageAdapter.Holder> {

    private final Context context;

    public CalendarPageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager_calendar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int year = position / 12;
        int month = position - (12 * year) + 1;
        Log.d("target", "y:" + (1970 + year) + " m:" + month);

        holder.txtYD.setText((1970 + year) + "년" + month + "월");
        holder.adapter.setList(1970 + year, month);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView txtYD;
        RecyclerView recyclerView, dowView;
        CalendarRecyclerAdapter adapter;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtYD = itemView.findViewById(R.id.txtYD);
            dowView = itemView.findViewById(R.id.dowView);
            dowView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 7));
            dowView.setAdapter(new DateAdapter());

            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 7));
            adapter = new CalendarRecyclerAdapter(context);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);

                    int position = parent.getChildAdapterPosition(view);
                    float m_05 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, context.getResources().getDisplayMetrics());
                    float m_1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, context.getResources().getDisplayMetrics());

                    if (position % 7 == 0) {
                        outRect.right = (int) m_1;
                    }
                    else if (position % 7 == 6) {
                        outRect.left = (int) m_1;
                    }
                    else {
                        outRect.left = (int) m_05;
                        outRect.right = (int) m_05;
                    }
                    outRect.bottom = (int) (m_05 + m_1);
                }
            });
        }

        private class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder> {

            @NonNull
            @Override
            public DateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new DateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_calendar_dow, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull DateHolder holder, int position) {
                switch (position) {
                    case 0:
                        holder.txtDow.setText("일");
                        break;
                    case 1:
                        holder.txtDow.setText("월");
                        break;
                    case 2:
                        holder.txtDow.setText("화");
                        break;
                    case 3:
                        holder.txtDow.setText("수");
                        break;
                    case 4:
                        holder.txtDow.setText("목");
                        break;
                    case 5:
                        holder.txtDow.setText("금");
                        break;
                    case 6:
                        holder.txtDow.setText("토");
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return 7;
            }

            class DateHolder extends RecyclerView.ViewHolder {
                TextView txtDow;

                public DateHolder(@NonNull View itemView) {
                    super(itemView);
                    txtDow = itemView.findViewById(R.id.txtDow);
                }
            }
        }
    }
}
