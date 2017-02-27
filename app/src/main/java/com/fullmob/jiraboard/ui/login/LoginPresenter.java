package com.fullmob.jiraboard.ui.login;

import com.fullmob.jiraapi.responses.AuthResponse;
import com.fullmob.jiraboard.managers.user.AuthenticationManager;
import com.fullmob.jiraboard.schedulers.FullmobSchedulers;
import com.fullmob.jiraboard.ui.AbstractPresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class LoginPresenter extends AbstractPresenter<LoginView> {
    private final AuthenticationManager authManager;

    public LoginPresenter(LoginView view, AuthenticationManager authManager) {
        super(view);
        this.authManager = authManager;
    }

    void onViewCreated() {
        if (authManager.isAlreadyAuthorized()) {
            getView().proceedToHomeScreen();
        }
    }

    void onLoginRequested(String subDomain, String user, String password) {
        authManager.login(subDomain, user, password)
            .observeOn(FullmobSchedulers.getMainThread())
            .subscribe(new Observer<Response<AuthResponse>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    getView().showLoading();
                }

                @Override
                public void onNext(Response<AuthResponse> response) {
                    getView().hideLoading();
                    if (authManager.isSuccess(response.body()) && getView() != null) {
                        getView().proceedToHomeScreen();
                    } else {
                        getView().showInvalidCredentials();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    getView().hideLoading();
                }

                @Override
                public void onComplete() {
                    getView().hideLoading();
                }
            });
    }
}