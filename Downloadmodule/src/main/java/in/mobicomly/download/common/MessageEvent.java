package in.mobicomly.download.common;

public class MessageEvent {
    Msg message;
    public MessageEvent(Msg message) {
        this.message = message;
    }
    public Msg getMessage(){
        return message;
    }
}
