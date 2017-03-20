package hu.bme.aut.mobsoft.lab.mobsoft.ui.main;

import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

public class MainPresenter extends Presenter<MainScreen> {

    private static MainPresenter instance = null;

    public MainPresenter() {
    }

    @Override
    public void attachScreen(MainScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }
}