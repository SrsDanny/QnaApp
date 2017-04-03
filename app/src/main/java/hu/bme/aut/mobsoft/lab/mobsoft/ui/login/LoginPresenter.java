package hu.bme.aut.mobsoft.lab.mobsoft.ui.login;

import hu.bme.aut.mobsoft.lab.mobsoft.model.user.UserDetails;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

public class LoginPresenter extends Presenter<LoginScreen> {

    @Override
    public void attachScreen(LoginScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void loginUser(UserDetails userDetails) {
        // Login the user (network operation)
        // Call showMessage or loginComplete depending on outcome
    };
}