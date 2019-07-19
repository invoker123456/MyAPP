package com.invoker.myapp.main;

import android.os.Bundle;
import android.view.View;

import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.util.vpnUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by invoker on 2019-06-16
 * Description:
 */
public class Vpn_TestActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpn_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_connect, R.id.bt_disconnect})
    void onClick(View view) {
        vpnUtils.init(this);
        if (view.getId() == R.id.bt_connect) {
            //查询检查是否已经存在VPN
            Object vpnProfile = vpnUtils.getVpnProfile();
            if (vpnProfile == null) {
                vpnProfile = vpnUtils.createVpnProfile("vpn1", "www.lfsoft.net", "user", "hello1214");
            } else {
                vpnUtils.setParams(vpnProfile, "vpn1", "www.lfsoft.net", "user", "hello1214");
            }
            //连接
            vpnUtils.connect(this, vpnProfile);
        } else if (view.getId() == R.id.bt_disconnect) {
            //断开连接
            vpnUtils.disconnect(this);
        }
    }
}
