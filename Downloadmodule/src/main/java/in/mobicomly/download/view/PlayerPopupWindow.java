package in.mobicomly.download.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.aplayer.aplayerandroid.APlayerAndroid;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.util.StringUtil;
import in.mobicomly.download.util.SystemConfig;
import in.mobicomly.download.util.Util;

public class PlayerPopupWindow extends PopupWindow implements View.OnClickListener{
    private View mRootView = null;
    private View parentView=null;
    private Activity parentActivity;
    private APlayerAndroid aPlayer=null;
    private TextView fullScreen,nativeScreen;
    private TextView playSpeedText,playAudioText;
    private ImageView playSpeedPlus,playSpeedCut,playAudioPlus,playAudioCut;
    private ImageView playBrightnessCut,playBrightnessPlus;
    private SeekBar playAudioSeek,playBrightnessSeek;
    private Switch playDecoder;
    public PlayerPopupWindow(Activity parentActivity,View parentView, APlayerAndroid aPlayer) {
        super(parentView.getHeight(), parentView.getHeight());
        this.parentActivity=parentActivity;
        this.parentView=parentView;
        this.aPlayer=aPlayer;
        mRootView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_video_more_setting, null);
        setContentView(mRootView);
        initView();
    }

    private void initView(){
        playSpeedText=(TextView)mRootView.findViewById(R.id.play_speed_text);
        fullScreen=(TextView)mRootView.findViewById(R.id.full_screen);
        nativeScreen=(TextView)mRootView.findViewById(R.id.native_screen);
        //playAudioText=(TextView)mRootView.findViewById(R.id.play_audio_text);
        playSpeedPlus=(ImageView)mRootView.findViewById(R.id.play_speed_plus);
        playSpeedCut=(ImageView)mRootView.findViewById(R.id.play_speed_cut);
        playAudioPlus=(ImageView)mRootView.findViewById(R.id.play_audio_plus);
        playAudioCut=(ImageView)mRootView.findViewById(R.id.play_audio_cut);
        playBrightnessCut=(ImageView)mRootView.findViewById(R.id.play_brightness_cut);
        playBrightnessPlus=(ImageView)mRootView.findViewById(R.id.play_brightness_plus);
        playAudioSeek=(SeekBar) mRootView.findViewById(R.id.play_audio_seek);
        playBrightnessSeek=(SeekBar) mRootView.findViewById(R.id.play_brightness_seek);
        playDecoder=(Switch) mRootView.findViewById(R.id.play_decoder);
        //playAudioText.setText(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)+"");
        playSpeedPlus.setOnClickListener(this);
        playSpeedCut.setOnClickListener(this);
        playAudioPlus.setOnClickListener(this);
        playAudioCut.setOnClickListener(this);
        playBrightnessPlus.setOnClickListener(this);
        playBrightnessCut.setOnClickListener(this);
        fullScreen.setOnClickListener(this);
        nativeScreen.setOnClickListener(this);

        SeekBar.OnSeekBarChangeListener barChangeListener=new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int id = seekBar.getId();
                if (id == R.id.play_audio_seek) {
                    SystemConfig.getInstance().setStreamVolume(i);
                } else if (id == R.id.play_brightness_seek) {
                    SystemConfig.getInstance().setWindowBrightness(parentActivity, i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        playDecoder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String decoder=aPlayer.getConfig(APlayerAndroid.CONFIGID.HW_DECODER_ENABLE);
                    if ("0".equals(decoder)) {
                        Util.alert(parentActivity, "当前手机不支持硬件解码", Const.ERROR_ALERT);
                        playDecoder.setChecked(false);
                    } else {
                        playDecoder.setChecked(b);
                        aPlayer.setConfig(APlayerAndroid.CONFIGID.HW_DECODER_USE,b?"1":"0");
                    }
            }
        });
        playAudioSeek.setOnSeekBarChangeListener(barChangeListener);
        playBrightnessSeek.setOnSeekBarChangeListener(barChangeListener);
        setSystemPlayAudio();
        setSystemBrightness();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.play_speed_plus) {
            Double speed = StringUtil.stringToDouble(playSpeedText.getText().toString());
            if (speed >= 2) return;
            speed += 0.1;
            setPlaySpeed(StringUtil.stringToDouble(speed + ""));
            playSpeedText.setText(StringUtil.stringToDouble(speed + "") + "");
        } else if (id == R.id.play_speed_cut) {
            Double speed2 = StringUtil.stringToDouble(playSpeedText.getText().toString());
            if (speed2 <= 0.5) return;
            speed2 -= 0.1;
            setPlaySpeed(StringUtil.stringToDouble(speed2 + ""));
            playSpeedText.setText(StringUtil.stringToDouble(speed2 + "") + "");
        } else if (id == R.id.play_audio_plus) {
            int currentVolume = SystemConfig.getInstance().getStreamVolume();
            if (currentVolume >= SystemConfig.getInstance().getStreamMaxVolume()) return;
            SystemConfig.getInstance().setStreamVolume(currentVolume + 2);
            setSystemPlayAudio();
        } else if (id == R.id.play_audio_cut) {
            int currentVolume2 = SystemConfig.getInstance().getStreamVolume();
            if (currentVolume2 <= 0) return;
            SystemConfig.getInstance().setStreamVolume(currentVolume2 - 2);
            setSystemPlayAudio();
        } else if (id == R.id.play_brightness_plus) {
            int currBrightness = playBrightnessSeek.getProgress();
            if (currBrightness >= 255) return;
            currBrightness = (currBrightness >= 240) ? 255 : (currBrightness + 15);
            playBrightnessSeek.setProgress(currBrightness);
            SystemConfig.getInstance().setWindowBrightness(parentActivity, currBrightness);
            //setSystemBrightness();
        } else if (id == R.id.play_brightness_cut) {
            int currBrightness2 = playBrightnessSeek.getProgress();
            if (currBrightness2 <= 0) return;
            currBrightness2 = (currBrightness2 < 15) ? 0 : (currBrightness2 - 15);
            playBrightnessSeek.setProgress(currBrightness2);
            SystemConfig.getInstance().setWindowBrightness(parentActivity, currBrightness2);
            //setSystemBrightness();
        } else if (id == R.id.full_screen) {
            String parm = parentView.getWidth() + ";" + parentView.getHeight();
            aPlayer.setConfig(APlayerAndroid.CONFIGID.ASPECT_RATIO_CUSTOM, parm);
            fullScreen.setTextColor(parentActivity.getResources().getColor(R.color.colorMain));
            nativeScreen.setTextColor(parentActivity.getResources().getColor(R.color.gray_8f));
        } else if (id == R.id.native_screen) {
            String parm2 = aPlayer.getConfig(APlayerAndroid.CONFIGID.ASPECT_RATIO_NATIVE);
            aPlayer.setConfig(APlayerAndroid.CONFIGID.ASPECT_RATIO_CUSTOM, parm2);
            nativeScreen.setTextColor(parentActivity.getResources().getColor(R.color.colorMain));
            fullScreen.setTextColor(parentActivity.getResources().getColor(R.color.gray_8f));
        }
    }

    private void setPlaySpeed(Double speed){
        aPlayer.setConfig(APlayerAndroid.CONFIGID.PLAY_SPEED,(speed*100)+"");
    }

    public void setSystemPlayAudio(){
        int maxVolume = SystemConfig.getInstance().getStreamMaxVolume();
        int currentVolume = SystemConfig.getInstance().getStreamVolume();
        playAudioSeek.setMax(maxVolume);
        playAudioSeek.setProgress(currentVolume);
    }

    public void setSystemBrightness(){
        int currentBrightness=SystemConfig.getInstance().getScreenBrightness();
        playBrightnessSeek.setMax(255);
        playBrightnessSeek.setProgress(currentBrightness);
    }
}
