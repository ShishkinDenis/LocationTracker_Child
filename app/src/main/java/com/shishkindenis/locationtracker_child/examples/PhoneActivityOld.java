package com.shishkindenis.locationtracker_child.examples;

/*public class PhoneActivityOld {
    public class PhoneAuthActivity extends MvpAppCompatActivity implements PhoneAuthView {

        @InjectPresenter
        PhoneAuthPresenter phoneAuthPresenter;

        private ActivityPhoneAuthBinding activityPhoneAuthBinding;
        private FirebaseAuth mAuth;
        private static final String TAG = "PhoneAuthActivity";
        private boolean mVerificationInProgress = false;
        private String mVerificationId;
        private PhoneAuthProvider.ForceResendingToken mResendToken;
        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            activityPhoneAuthBinding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
            View view = activityPhoneAuthBinding.getRoot();
            setContentView(view);

            mAuth = FirebaseAuth.getInstance();

            activityPhoneAuthBinding.btnRequestCode.setOnClickListener(v -> {
                if (!validatePhoneNumber()) {
                    return;
                }
                startPhoneNumberVerification(activityPhoneAuthBinding.etPhoneNumber.getText().toString());
            });

            activityPhoneAuthBinding.btnVerifyCode.setOnClickListener(v -> verifyPhoneNumberWithCode(
                    mVerificationId, activityPhoneAuthBinding.etVerificationCode.getText().toString()));

            activityPhoneAuthBinding.btnSignOut.setOnClickListener(v -> signOut());

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    Log.d(TAG, "onVerificationCompleted:" + credential);
                    mVerificationInProgress = false;
                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.w(TAG, "onVerificationFailed", e);
                    mVerificationInProgress = false;
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        activityPhoneAuthBinding.etPhoneNumber.setError("Invalid phone number.");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Log.d(TAG, "onCodeSent:" + verificationId);
                    mVerificationId = verificationId;
                    mResendToken = token;
                }
            };
        }

        @Override
        public void onStart() {
            super.onStart();
//        Почему onStart?
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (mVerificationInProgress && validatePhoneNumber()) {
                startPhoneNumberVerification(activityPhoneAuthBinding.etPhoneNumber.getText().toString());
            }
        }

        private void startPhoneNumberVerification(String phoneNumber) {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(mCallbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
            mVerificationInProgress = true;
        }

        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();

                            Toast.makeText(getApplicationContext(), "Authentication successful",
                                    Toast.LENGTH_LONG).show();

                            goToAnotherActivity(SendLocationActivity.class,"abc7","abc7");
                        } else {

                            Toast.makeText(getApplicationContext(), "Authentication failed",
                                    Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                activityPhoneAuthBinding.etVerificationCode.setError("Invalid code");
                            }
                        }
                    });
        }

        private boolean validatePhoneNumber() {
            if (activityPhoneAuthBinding.etPhoneNumber.getText().toString().isEmpty()) {
                activityPhoneAuthBinding.etPhoneNumber.setError("Invalid phone number.");
                return false;
            }
            return true;
        }

        private void verifyPhoneNumberWithCode(String verificationId, String code) {
            if (validatePhoneNumber()) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
            else {
                Toast.makeText(getApplicationContext(), "Verification failed",
                        Toast.LENGTH_LONG).show();
            }
        }

        private void signOut(){
//        если вход совершен
            mAuth.signOut();
            Toast.makeText(getApplicationContext(), "Sign out successful",
                    Toast.LENGTH_LONG).show();
//         Если вход не совершен
//        Toast.makeText(getApplicationContext(), "You haven't sign in yet" ,
//                Toast.LENGTH_LONG).show();
        }

        //    СДЕЛАТЬ ОДИН ДЛЯ ВСЕХ АКТИВИТИ?
        public void goToAnotherActivity(Class activity, String name, String value){
            Intent intent = new Intent(this, activity);
            intent.putExtra(name, value);
            startActivity(intent);
        }

        public void showToast(String toastMessage){
            Toast.makeText(getApplicationContext(), toastMessage,
                    Toast.LENGTH_LONG).show();
        }

//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
    }
}
*/
