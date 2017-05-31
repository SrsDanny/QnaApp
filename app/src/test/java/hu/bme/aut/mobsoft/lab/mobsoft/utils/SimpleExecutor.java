package hu.bme.aut.mobsoft.lab.mobsoft.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class SimpleExecutor implements Executor {

	@Override
	public void execute(@NonNull Runnable command) {
		command.run();
	}
}
