package mattsturgill.peoplemonfinal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mattsturgill.peoplemonfinal.Model.User;
import mattsturgill.peoplemonfinal.R;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class CaughtListAdapter extends RecyclerView.Adapter<CaughtListAdapter.CaughtListHolder>{
    private Context context;
    public ArrayList<User> caughtUsers;

    public CaughtListAdapter(ArrayList<User> caughtUsers, Context context) {
        this.caughtUsers = caughtUsers;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(CaughtListHolder holder, int position) {
        User user = caughtUsers.get(position);
        holder.bindUser(user);
    }

    @Override
    public int getItemCount() {return caughtUsers == null ? 0 : caughtUsers.size();}

    @Override
    public CaughtListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.caught_list_item, parent, false);
        return new CaughtListHolder(inflatedView);
    }

    class CaughtListHolder extends RecyclerView.ViewHolder { //populates view

        @Bind(R.id.user_name_field)
        TextView nameField;

        public CaughtListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {

            nameField.setText(user.getUserName());
        }
    }
}
