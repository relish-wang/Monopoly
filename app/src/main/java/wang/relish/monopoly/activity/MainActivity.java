package wang.relish.monopoly.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wang.relish.monopoly.R;
import wang.relish.monopoly.entity.User;
import wang.relish.monopoly.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvUsers;
    private List<User> mUsers = new ArrayList<>();
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRvUsers = findViewById(R.id.rv_users);
        mAdapter = new UserAdapter();
        mRvUsers.setAdapter(mAdapter);
        mRvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        mUsers = User.getUsers();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_turn:
                //TODO 回合
                return true;
            case R.id.menu_reset:
                // TODO 重置
                return true;
            case R.id.menu_add_user:

                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_user, null);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("新建麻友")
                        .setView(view)
                        .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etUserName = view.findViewById(R.id.et_user_name);
                                String name = etUserName.getText().toString();
                                if (TextUtils.isEmpty(name)) {
                                    ToastUtil.show("名字不得为空!");
                                    return;
                                }
                                User.createUser(name);
                                loadData();
                            }
                        }).setNegativeButton("取消", null)
                        .show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    class UserAdapter extends RecyclerView.Adapter<UserAdapter.VHolder> {
        @Override
        public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_user, parent, false);
            return new VHolder(view);
        }

        @Override
        public void onBindViewHolder(VHolder holder, int position) {
            User user = mUsers.get(position);
            if (user == null) return;
            final long id = user.getId();
            holder.tv_id.setText(id + "");
            final String name = user.getName();
            holder.tv_name.setText(name);
            holder.tv_money.setText(user.getMoney() + "");
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("删除选手")
                            .setMessage("是否删除[" + name + "]?")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // TODO 删除选手
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class VHolder extends RecyclerView.ViewHolder {
            TextView tv_id;
            TextView tv_name;
            TextView tv_money;

            public VHolder(View view) {
                super(view);
                tv_id = view.findViewById(R.id.tv_id);
                tv_name = view.findViewById(R.id.tv_name);
                tv_money = view.findViewById(R.id.tv_money);
            }
        }
    }
}
