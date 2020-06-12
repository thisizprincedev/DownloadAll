package in.mobicomly.download.mvp.e;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "AppSetting")
public class AppSettingEntity {
    @Column(name = "id", isId = true,autoGen = true)
    private int id;
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
