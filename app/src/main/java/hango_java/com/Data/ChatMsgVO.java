package hango_java.com.Data;

public class ChatMsgVO {
    private String userId;
    private String crt_dt;
    private String content;

    public String getUserId() {
        return userId;
    }

    public String getCrt_dt() {
        return crt_dt;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCrt_dt(String crt_dt) {
        this.crt_dt = crt_dt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatMsgVO(){}

    public ChatMsgVO(String userId, String crt_dt, String content){
        this.userId = userId;
        this.crt_dt = crt_dt;
        this.content = content;
    }

}
