package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import com.orm.SugarContext;

public class SugarOrmRepository implements Repository {

    @Override
    public void open(Context context) {
        SugarContext.init(context);
    }

    @Override
    public void close() {
        SugarContext.terminate();
    }
}
