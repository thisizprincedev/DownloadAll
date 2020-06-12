package in.mobicomly.download.mvp.v;

import java.util.List;

import in.mobicomly.download.mvp.e.MagnetInfo;

public interface MagnetSearchView {
    void refreshData(List<MagnetInfo> info);
    void moreOption(MagnetInfo magnet);
}
