package wang.relish.monopoly;

import android.app.Application;
import android.content.Context;

import wang.relish.monopoly.util.ToastUtil;

/**
 * @author Relish Wang
 * @since 2018/02/19
 */
public class App extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
        ToastUtil.init(this);
    }
}
