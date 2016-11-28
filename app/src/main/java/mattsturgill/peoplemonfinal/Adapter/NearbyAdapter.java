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

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyHolder> {
    private Context context;
    public ArrayList<User> nearUsers;

    public NearbyAdapter(ArrayList<User> nearUsers, Context context) {
        this.nearUsers = nearUsers;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(NearbyHolder holder, int position) {
        User user = nearUsers.get(position);
        holder.bindUser(user);
    }

    @Override
    public int getItemCount() {return nearUsers == null ? 0 : nearUsers.size();
    }

    @Override
    public NearbyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.near_item, parent, false);
        return new NearbyHolder(inflatedView);
    }

    class NearbyHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.nearby_user_name)
        TextView nearbyNameField;

        public NearbyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {
            nearbyNameField.setText(user.getUserName());
        }
    }
}
