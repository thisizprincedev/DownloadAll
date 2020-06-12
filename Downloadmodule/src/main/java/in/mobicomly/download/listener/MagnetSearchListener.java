package in.mobicomly.download.listener;

import java.util.List;

import in.mobicomly.download.mvp.e.MagnetInfo;

public interface MagnetSearchListener {
    void success(List<MagnetInfo> info);
    void fail(String error);
}
