package com.oscar.androidubertwin.presentation.presenter.MainActivityPresenter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscar.androidubertwin.R;
import com.oscar.androidubertwin.domain.model.User;
import com.oscar.androidubertwin.presentation.ui.MainActivity;
import com.oscar.androidubertwin.utils.Validator;

/**
 * Created by oscar on 11/10/2017.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    private final MainActivity mainActivity;
    private final Context context;

    private EditText txtEmailRegister;
    private EditText txtPasswordRegister;
    private EditText txtNameRegister;
    private EditText txtPhoneRegister;
    private TextInputLayout txtInputPassword;

    private EditText txtEmailLogin;
    private EditText txtPasswordLogin;
    private TextInputLayout txtInputPasswordLogin;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseUsers;

    public MainActivityPresenter(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
    }

    @Override
    public void onCreate() {
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseUsers = firebaseDatabase.getReference("Users");
    }

    @Override
    public void showRegisterDialog() {
        final Button buttonPositive;
        final AlertDialog.Builder dialogRegister = new AlertDialog.Builder(this.context);
        dialogRegister.setTitle(R.string.title_register);
        dialogRegister.setMessage(R.string.message_register);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View registerLayout = inflater.inflate(R.layout.layout_register, null);

        txtEmailRegister = (EditText) registerLayout.findViewById(R.id.txtEmail_register);
        txtPasswordRegister = (EditText) registerLayout.findViewById(R.id.txtPassword_register);
        txtNameRegister = (EditText) registerLayout.findViewById(R.id.txtName_register);
        txtPhoneRegister = (EditText) registerLayout.findViewById(R.id.txtPhone_register);
        txtInputPassword = (TextInputLayout) registerLayout.findViewById(R.id.txtInputpassword);
        dialogRegister.setView(registerLayout);


        dialogRegister.setPositiveButton(R.string.login_register, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivity.showProgress();
                mainActivity.enableButtons(false);
                createNewUser();
            }
        });

        dialogRegister.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = dialogRegister.create();

        //dialogRegister.show();
        dialog.show();
        buttonPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPositive.setEnabled(false);

        checkValidationFieldsRegister(buttonPositive);

    }

    @Override
    public void showLoginDialog() {
        final Button buttonPositive;
        final AlertDialog.Builder dialogLogin = new AlertDialog.Builder(this.context);
        dialogLogin.setTitle(R.string.title_login);
        dialogLogin.setMessage(R.string.message_register);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View loginLayout = inflater.inflate(R.layout.layout_login, null);

        txtEmailLogin = (EditText) loginLayout.findViewById(R.id.txtEmail_login);
        txtPasswordLogin = (EditText) loginLayout.findViewById(R.id.txtPassword_login);

        txtInputPasswordLogin = (TextInputLayout) loginLayout.findViewById(R.id.txtInputpassword_Login);
        dialogLogin.setView(loginLayout);


        dialogLogin.setPositiveButton(R.string.login_sing_in, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivity.showProgress();
                mainActivity.enableButtons(false);
                loginUser();
            }
        });

        dialogLogin.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = dialogLogin.create();

        //dialogLogin.show();
        dialog.show();
        buttonPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonPositive.setEnabled(false);

        checkValidationFieldsLogin(buttonPositive);
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPasswordLogin.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mainActivity.dismissProgress();
                        mainActivity.navigateToWelcome();
                        mainActivity.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mainActivity.dismissProgress();
                mainActivity.enableButtons(true);
                mainActivity.setMessageSnackBar("login failure " + e.getMessage());
            }
        });
    }

    private void createNewUser() {
        auth.createUserWithEmailAndPassword(txtEmailRegister.getText().toString(), txtPasswordRegister.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(txtEmailRegister.getText().toString(),
                                                txtPasswordRegister.getText().toString(),
                                                txtNameRegister.getText().toString(),
                                                txtPhoneRegister.getText().toString());
                        databaseUsers.child(auth.getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mainActivity.enableButtons(true);
                                        mainActivity.dismissProgress();
                                        mainActivity.setMessageSnackBar("succes register");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mainActivity.enableButtons(true);
                                        mainActivity.dismissProgress();
                                        mainActivity.setMessageSnackBar("base datos "+e.getMessage());
                                    }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mainActivity.enableButtons(true);
                mainActivity.dismissProgress();
                mainActivity.setMessageSnackBar("no create user "+e.getMessage());
            }
        });
    }

    private void checkValidationFieldsRegister(final Button buttonPositive) {

        final boolean[] a = new boolean[4];
        a[0] = false;
        a[1] = false;
        a[2] = false;
        a[3] = false;
        txtEmailRegister.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtEmailRegister.getText().toString().isEmpty()) {
                    txtEmailRegister.setError(mainActivity.getString(R.string.email_empty));
                }
            }
        });

        txtEmailRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtEmailRegister.getText().toString().isEmpty()) {
                    txtEmailRegister.setError(mainActivity.getString(R.string.email_empty));
                }else if (Validator.isValidEmail(txtEmailRegister.getText().toString())){
                    txtEmailRegister.setError(mainActivity.getString(R.string.email_valid));
                }
                else {
                    a[0] = true;
                    if (a[0] && a[1] && a[2] && a[3]) {
                        buttonPositive.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPasswordRegister.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtPasswordRegister.getText().toString().isEmpty()) {
                    txtPasswordRegister.setError(mainActivity.getString(R.string.pass_empty));
                }
            }
        });

        txtPasswordRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPasswordRegister.getText().toString().isEmpty()) {
                    txtPasswordRegister.setError(mainActivity.getString(R.string.pass_empty));
                } else {
                    a[1] = true;
                    if (a[0] && a[1] && a[2] && a[3]) {
                        buttonPositive.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtNameRegister.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtNameRegister.getText().toString().isEmpty()) {
                    txtNameRegister.setError(mainActivity.getString(R.string.name_empty));
                }
            }
        });

        txtNameRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtNameRegister.getText().toString().isEmpty()) {
                    txtNameRegister.setError(mainActivity.getString(R.string.name_empty));
                } else {
                    a[2] = true;
                    if (a[0] && a[1] && a[2] && a[3]) {
                        buttonPositive.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPhoneRegister.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtPhoneRegister.getText().toString().isEmpty()) {
                    txtPhoneRegister.setError(mainActivity.getString(R.string.phone_empty));
                }
            }
        });

        txtPhoneRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPhoneRegister.getText().toString().isEmpty()) {
                    txtPhoneRegister.setError(mainActivity.getString(R.string.phone_empty));
                } else {
                    a[3] = true;
                    if (a[0] && a[1] && a[2] && a[3]) {
                        buttonPositive.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtInputPassword.setCounterEnabled(true);
        txtInputPassword.setCounterMaxLength(8);
    }
    private void checkValidationFieldsLogin(final Button buttonPositive) {

        final boolean[] a = new boolean[2];
        a[0] = false;
        a[1] = false;

        txtEmailLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtEmailLogin.getText().toString().isEmpty()) {
                    txtEmailLogin.setError(mainActivity.getString(R.string.email_empty));
                }
            }
        });

        txtEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtEmailLogin.getText().toString().isEmpty()) {
                    txtEmailLogin.setError(mainActivity.getString(R.string.email_empty));
                }else if (Validator.isValidEmail(txtEmailLogin.getText().toString())){
                    txtEmailLogin.setError(mainActivity.getString(R.string.email_valid));
                }
                else {
                    a[0] = true;
                    if (a[0] && a[1]) {
                        buttonPositive.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPasswordLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (txtPasswordLogin.getText().toString().isEmpty()) {
                    txtPasswordLogin.setError(mainActivity.getString(R.string.pass_empty));
                }
            }
        });

        txtPasswordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPasswordLogin.getText().toString().isEmpty()) {
                    txtPasswordLogin.setError(mainActivity.getString(R.string.pass_empty));
                } else {
                    a[1] = true;
                    if (a[0] && a[1]) {
                        buttonPositive.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtInputPasswordLogin.setCounterEnabled(true);
        txtInputPasswordLogin.setCounterMaxLength(8);
    }
}
