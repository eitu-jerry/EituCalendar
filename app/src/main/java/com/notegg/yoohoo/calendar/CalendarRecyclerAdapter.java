package com.notegg.yoohoo.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notegg.yoohoo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.Holder> {

    private Context context;
    private final CalcDateDiff cdd = new CalcDateDiff();
    private List<CalendarCellItem> list = new ArrayList<>();

    private float cellHeight;

    public CalendarRecyclerAdapter(Context context) {
        this.context = context;
        cellHeight = context.getResources().getDimension(R.dimen.calendarCellHeight);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_calendar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CalendarCellItem item = list.get(position);
        Calendar day = item.getDay();
        holder.txtDay.setText(String.valueOf(day.get(Calendar.DATE)));

        //토,일,월 컬러 구분
        int dow = day.get(Calendar.DAY_OF_WEEK);
        if (dow == 1) {
            holder.txtDay.setTextColor(Color.RED);
        }
        else if (dow == 7) {
            holder.txtDay.setTextColor(Color.BLUE);
        }
        else {
            holder.txtDay.setTextColor(Color.BLACK);
        }

        //이 셀이 오늘 날짜인지 확인
        Calendar c = Calendar.getInstance();
        boolean eYear = c.get(Calendar.YEAR) == day.get(Calendar.YEAR);
        boolean eMonth = c.get(Calendar.MONTH) == day.get(Calendar.MONTH);
        boolean eDate = c.get(Calendar.DATE) == day.get(Calendar.DATE);
        holder.txtWorkerCount.setSelected(eYear && eMonth && eDate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(int year, int month) {
        list = cdd.getDayList(year, month);
        notifyDataSetChanged();
    }

    public void setCellHeight(float cellHeight) {
        this.cellHeight = cellHeight;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView txtDay, txtWorkerCount;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtWorkerCount = itemView.findViewById(R.id.txtWorkerCount);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.height = (int) cellHeight;
            itemView.setLayoutParams(params);
        }
    }
}
