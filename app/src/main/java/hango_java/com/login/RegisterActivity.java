package hango_java.com.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hango_java.com.R;
import hango_java.com.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean validate = false;
    private Button validateButton;
    private Button registerButton;
    private EditText et_id;
    private EditText et_name;
    private EditText et_pass;
    private EditText et_passCk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_passCk = findViewById(R.id.et_passck);

        validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener((v) -> {
            EditText et_id = findViewById(R.id.et_id);
            String userID = et_id.getText().toString();

            if (validate) return;

            if (userID.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("아이디는 빈칸일 수 없습니다.").setPositiveButton("확인", null)
                        .create();
                dialog.show();
                return;
            }
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                        dialog.show();
                        et_id.setEnabled(false); //아이디값 고정
                        validate = true; //검증 완료
                        validateButton.setText("확인");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

            Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR", "서버 Response 가져오기 실패: $error");
                    return;
                }
            };

            ValidateRequest validateRequest = new ValidateRequest(userID, response);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(validateRequest);
        });


        registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener((v) -> {
            final String userID = et_id.getText().toString();
            final String userPassword = et_pass.getText().toString();
            final String userName = et_name.getText().toString();
            final String PassCk = et_pass.getText().toString();


            //아이디 중복체크 했는지 확인
            if (!validate) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                dialog.show();
                return;
            } else if (checkPass()) Log.e("ERROR", "Password 입력 오류");
            else {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //회원가입 성공시
                            if (userPassword.equals(PassCk)) {
                                if (success) {
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", userName), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                    //회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }

    private boolean checkPass() {
        String pass = et_pass.getText().toString();
        String passCk = et_passCk.getText().toString();

        if (pass.length() >= 8 && passCk.length() >= 8 && pass.equals(passCk)) return false;
        else if (pass.length() < 8)
            Toast.makeText(getApplicationContext(), "비밀번호는 8자 이상이여야 합니다.", Toast.LENGTH_LONG).show();
        else if (passCk.length() < 8)
            Toast.makeText(getApplicationContext(), "비밀번호 확인칸을 다시 확인해주세요.", Toast.LENGTH_LONG).show();
        else if (pass.equals(passCk))
            Toast.makeText(getApplicationContext(), "입력하신 비밀번호와 비밀번호 확인칸이 일치 하지 않습니다.", Toast.LENGTH_LONG).show();

        return true;
    }
}
