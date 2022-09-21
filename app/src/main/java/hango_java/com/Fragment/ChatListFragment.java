package hango_java.com.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import hango_java.com.R;

public class ChatListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ChatRoomFragment";
    EditText chatroom_et;
    Button enter_btn;

    public static ChatListFragment newInstance(String param1, String param2) {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);

        chatroom_et = rootView.findViewById(R.id.et_name);
        enter_btn = rootView.findViewById(R.id.enterBtn);
        enter_btn.setOnClickListener(this);

        return rootView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enterBtn:
                if(chatroom_et.getText().toString().trim().length() >= 0){
                    Log.d(TAG, "입장처리");

                    // 원하는 데이터를 담을 객체
                    Bundle argu = new Bundle();
                    argu.putString("chatroom", chatroom_et.getText().toString());

                    // 이동할 Fragment 선언
                    ChatMsgFragment chatMsgFragment = ChatMsgFragment.newInstance();

                    // 이동할 Fragment 에 데이터 객체 담기
                    chatMsgFragment.setArguments(argu);


                    Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.chatFragment,argu);


                }else
                {
                    Toast.makeText(getActivity(), "채팅방 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}