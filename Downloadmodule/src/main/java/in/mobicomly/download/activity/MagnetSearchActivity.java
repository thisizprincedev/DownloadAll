package in.mobicomly.download.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocosw.bottomsheet.BottomSheet;
import com.coorchice.library.SuperTextView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.adapter.MagnetSearchListAdapter;
import in.mobicomly.download.common.BaseActivity;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.common.RecyclerViewNoBugLinearLayoutManager;
import in.mobicomly.download.mvp.e.MagnetInfo;
import in.mobicomly.download.mvp.e.MagnetRule;
import in.mobicomly.download.mvp.p.MagnetSearchPresenter;
import in.mobicomly.download.mvp.p.MagnetSearchPresenterImp;
import in.mobicomly.download.mvp.p.UrlDownLoadPresenter;
import in.mobicomly.download.mvp.p.UrlDownLoadPresenterImp;
import in.mobicomly.download.mvp.v.MagnetSearchView;
import in.mobicomly.download.mvp.v.UrlDownLoadView;
import in.mobicomly.download.util.GsonUtil;
import in.mobicomly.download.util.StringUtil;
import in.mobicomly.download.util.Util;

public class MagnetSearchActivity extends BaseActivity implements MagnetSearchView,UrlDownLoadView {
    private EditText searchText;
    private SuperTextView searchBtn;
    private RecyclerView recyclerView;
    private TextView searchSourceText;
    private TextView searchSortText;
    private LinearLayout btsource;
    private LinearLayout btshort;
    private TwinklingRefreshLayout refreshLayout;

    private BottomSheet.Builder bottomSheet=null;
    private MagnetSearchPresenter magnetSearchPresenter;
    private UrlDownLoadPresenter urlDownLoadPresenter;
    private MagnetSearchListAdapter searchListAdapter;
    private LovelyChoiceDialog sourceDialog=null,sortDialog=null;
    private List<MagnetInfo> list=new ArrayList<>();
    private List<MagnetRule> rules;
    private MagnetRule rule;
    private  List<String> btSources=new ArrayList<>();
    private String searchKeyWord;
    private int searchPage=1;
    private String searchSort= Const.SEARCH_SORT_DATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnet_search);
        searchText = findViewById(R.id.input_search);
        searchBtn = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.recyclerview);
        searchSourceText = findViewById(R.id.search_source);
        searchSortText = findViewById(R.id.search_sort);
        refreshLayout = findViewById(R.id.refresh);
        btsource = findViewById(R.id.bt_source);
        btshort = findViewById(R.id.bt_sort);

        magnetSearchPresenter=new MagnetSearchPresenterImp(this);
        urlDownLoadPresenter=new UrlDownLoadPresenterImp(this);
        initData();
        initView();
    }
    private void initData(){
        rules= GsonUtil.getRule(this,"rule.json");
		if(rules==null){
            Util.alert(this,"获取种子来源网站失败，请重新打开本页面或者重启APP",Const.ERROR_ALERT);
            return;
        }
        for(MagnetRule rule:rules){
            btSources.add(rule.getSite());
        }
        rule=GsonUtil.getMagnetRule(rules).get(rules.get(0).getSite());
    }
    private void initView(){
        searchSourceText.setText(String.format(getString(R.string.search_source),rule.getSite()));
        searchSortText.setText(String.format(getString(R.string.search_sort), getString(R.string.date)));
        //recyclerView;
        recyclerView.setLayoutManager(new RecyclerViewNoBugLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        searchListAdapter=new MagnetSearchListAdapter(this,this,list);
        recyclerView.setAdapter(searchListAdapter);
        //refreshLayout
        ProgressLayout header = new ProgressLayout(this);
        header.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorMain));
        header.setColorSchemeResources(R.color.white);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                searchPage=1;
                list.clear();
                searchListAdapter.notifyDataSetChanged();
                search();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                searchPage+=1;
                search();
            }
        });
        //
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    refreshLayout.startRefresh();
                }
                return false;
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.startRefresh();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.startRefresh();
            }
        });
        btsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sourceDialog==null) {
                    sourceDialog=new LovelyChoiceDialog(getApplicationContext())
                            .setTopColorRes(R.color.colorMain)
                            .setIcon(R.drawable.ic_source)
                            .setItems(btSources, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                                @Override
                                public void onItemSelected(int position, String item) {
                                    rule = GsonUtil.getMagnetRule(rules).get(item);
                                    searchSourceText.setText(String.format(getString(R.string.search_source), item));
                                    refreshLayout.startRefresh();
                                }
                            });
                }
                sourceDialog.show();
            }
        });
        btshort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortDialog==null) {
                    sortDialog= new LovelyChoiceDialog(getApplicationContext())
                            .setTopColorRes(R.color.colorMain)
                            .setIcon(R.drawable.ic_sort)
                            .setItems(Arrays.asList(getString(R.string.date),getString(R.string.hot)), new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                                @Override
                                public void onItemSelected(int position, String item) {
                                    if(item.equals(getString(R.string.date))){
                                        searchSort=Const.SEARCH_SORT_DATE;
                                    }else{
                                        searchSort=Const.SEARCH_SORT_HOT;
                                    }
                                    searchSortText.setText(String.format(getString(R.string.search_sort), item));
                                    refreshLayout.startRefresh();
                                }
                            });
                }
                sortDialog.show();
            }
        });
    }

    @Override
    public void refreshData(List<MagnetInfo> info) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        if(null==info){
            Util.alert(this,"网络超时，请重试",Const.ERROR_ALERT);
        }else if(info.size()==0){
            Util.alert(this,"没有更多了",Const.ERROR_ALERT);
        }else {
            list.addAll(info);
            searchListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void moreOption(final MagnetInfo magnet) {
         new BottomSheet.Builder(this)
                    .title(R.string.slest_option)
                    .sheet(R.menu.magnet_option)
                    .listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == R.id.down) {
                                urlDownLoadPresenter.startTask(magnet.getMagnet());
                            } else if (which == R.id.copy) {
                                Util.putTextIntoClip(magnet.getMagnet());
                            } else if (which == R.id.xl) {
                                openXL(magnet);
                            } else if (which == R.id.sourcepage) {
                                Intent intent = new Intent(MagnetSearchActivity.this, BrowseActivity.class);
                                intent.putExtra("url", magnet.getDetailUrl());
                                startActivity(intent);
                            }
                        }
                    }).show();
    }

    private void search(){
        searchKeyWord=searchText.getText().toString().trim();
        if(!StringUtil.isEmpty(searchKeyWord)) {
            magnetSearchPresenter.searchMagnet(rule, searchKeyWord,searchSort, searchPage);
            hintKeyBoard();
        }else{
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }
    private void openXL(MagnetInfo magnet){
        if(Util.checkApkExist(getString(R.string.xl_package_name))){
            Util.putTextIntoClip(magnet.getMagnet());;
            Intent intent=new Intent();
            intent = getPackageManager().getLaunchIntentForPackage(getString(R.string.xl_package_name));
            startActivity(intent);

        }else{
            Util.alert(this,"未安装迅雷", Const.ERROR_ALERT);
        }
    }
    private void hintKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    @Override
    public void addTaskSuccess() {
        Util.alert(this,getString(R.string.add_task_success), Const.SUCCESS_ALERT);
    }

    @Override
    public void addTaskFail(String msg) {
        Util.alert(this,msg, Const.ERROR_ALERT);
    }
}
