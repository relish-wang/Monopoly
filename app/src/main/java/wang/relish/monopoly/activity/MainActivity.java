package wang.relish.monopoly.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import wang.relish.monopoly.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                // TODO 输入名字创建用户
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
