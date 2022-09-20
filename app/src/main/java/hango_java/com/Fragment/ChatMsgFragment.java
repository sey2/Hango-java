package hango_java.com.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hango_java.com.Adapter.ChatAdapter;
import hango_java.com.Data.ChatMsgVO;
import hango_java.com.R;
import hango_java.com.ViewModel.TravelViewModel;

public class ChatMsgFragment extends Fragment implements View.OnClickListener {

    // 로그용 TAG
    private final String TAG = getClass().getSimpleName();
    private TravelViewModel userViewModel;

    // 채팅을 입력할 입력창과 전송 버튼
    EditText content_et;
    ImageView send_iv;

    // 채팅 내용을 뿌려줄 RecyclerView 와 Adapter
    RecyclerView rv;
    ChatAdapter mAdapter;

    // 채팅 방 이름
    String chatroom = "";

    // 채팅 내용을 담을 배열
    List<ChatMsgVO> msgList = new ArrayList<>();

    // FirebaseDatabase 연결용 객체들
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public ChatMsgFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChatMsgFragment newInstance() {
        ChatMsgFragment fragment = new ChatMsgFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_msg, container, false);

        userViewModel = new ViewModelProvider(this.getActivity()).get(TravelViewModel.class);

        content_et = view.findViewById(R.id.content_et);
        send_iv = view.findViewById(R.id.send_iv);

        rv = view.findViewById(R.id.rv);
        send_iv.setOnClickListener(this);

        // ChatRoomFragment 에서 받는 채팅방 이름
        chatroom = getArguments().getString("chatroom");
        mAdapter = new ChatAdapter(msgList, userViewModel.getUserinfo().getValue().getUserID());

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(mAdapter);

        // Firebase Database 초기
        myRef = database.getReference(chatroom);

        // Firebase Database Listener 붙이기
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Firebase 의 해당 DB 에 값이 추가될 경우 호출, 생성 후 최초 1번은 실행됨
                Log.d(TAG, "onChild added");
                Log.d(TAG, "onChild = "+dataSnapshot.getValue(ChatMsgVO.class).toString());

            // Database 의 정보를 ChatMsgVO 객체에 담음
                ChatMsgVO chatMsgVO = dataSnapshot.getValue(ChatMsgVO.class);
                msgList.add(chatMsgVO);

            // 채팅 메시지 배열에 담고 RecyclerView 다시 그리기
                mAdapter = new ChatAdapter(msgList, userViewModel.getUserinfo().getValue().getUserID());
                rv.setAdapter(mAdapter);
                rv.scrollToPosition(msgList.size()-1);
                Log.d(TAG, msgList.size()+"");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        Log.d(TAG, "chatroom = "+chatroom);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send_iv:
                if(content_et.getText().toString().trim().length() >= 1){
                    Log.d(TAG, "입력처리");

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Database 에 저장할 객체 만들기
                    ChatMsgVO msgVO = new ChatMsgVO(userViewModel.getUserinfo().getValue().getUserID(), df.format(new Date()).toString(), content_et.getText().toString().trim());

                // 해당 DB 에 값 저장시키기
                    myRef.push().setValue(msgVO);

                    // 입력 필드 초기화
                    content_et.setText("");
                }else
                {
                    Toast.makeText(getActivity(), "메시지를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}