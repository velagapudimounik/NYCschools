package verizon.a20180302_mounikvelagapudi_nycschools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import verizon.a20180302_mounikvelagapudi_nycschools.ModelClasses.School;

/**
 * Created by mounikvelagapudi on 03/03/18.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {

    private final ArrayList<School> schools;
    private final Context context;
    private final String type;

    public CustomAdapter(Context context, ArrayList<School> schools, String type) {
        this.schools = schools;
        this.context = context;
        this.type = type;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type.equalsIgnoreCase("school")){
            View view = LayoutInflater.from(context).inflate(R.layout.listofschools, parent, false);
            return new myViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.listofmarks, parent, false);
            return new myViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(CustomAdapter.myViewHolder holder, final int position) {

        if (type.equalsIgnoreCase("school")){
            holder.textView.setText(schools.get(position).getSchoolName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(context, Scores.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }
            });
        } else {
            holder.tvWriting.setText(schools.get(position).getWritingScore());
            holder.tvReading.setText(schools.get(position).getReadingScore());
            holder.tvMath.setText(schools.get(position).getMathScore());
            holder.tvSchoolName.setText(schools.get(position).getSchoolName());
        }
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView tvWriting,tvReading,tvMath, tvSchoolName;

        public myViewHolder(View itemView) {
            super(itemView);

            if (type.equalsIgnoreCase("school")){
                textView = itemView.findViewById(R.id.textView);
            } else {
                tvMath = itemView.findViewById(R.id.mathId);
                tvReading = itemView.findViewById(R.id.readingId);
                tvWriting = itemView.findViewById(R.id.writingId);
                tvSchoolName = itemView.findViewById(R.id.schoolNameId);
            }
        }
    }
}
