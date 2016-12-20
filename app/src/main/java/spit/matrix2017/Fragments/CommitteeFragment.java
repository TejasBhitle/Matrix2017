package spit.matrix2017.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import spit.matrix2017.R;


public class CommitteeFragment
        extends Fragment{

    LinearLayout secretary, cp, vcp, exec, tech, pr, marketing, events, decor, admin, security;

    public CommitteeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_committee,container,false);

        secretary = (LinearLayout) view.findViewById(R.id.secretary_email);
        cp = (LinearLayout) view.findViewById(R.id.cp_email);
        vcp = (LinearLayout) view.findViewById(R.id.vcp_email);
        exec = (LinearLayout) view.findViewById(R.id.exec_email);
        tech = (LinearLayout) view.findViewById(R.id.tech_email);
        pr = (LinearLayout) view.findViewById(R.id.pr_email);
        marketing = (LinearLayout) view.findViewById(R.id.marketing_email);
        events = (LinearLayout) view.findViewById(R.id.events_email);
        decor = (LinearLayout) view.findViewById(R.id.decor_email);
        admin = (LinearLayout) view.findViewById(R.id.admin_email);
        security = (LinearLayout) view.findViewById(R.id.security_email);

        View.OnClickListener emailListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "";
                switch (v.getId()){
                    case R.id.secretary_email:
                        to = getResources().getString(R.string.secretary_email);
                        break;
                    case R.id.cp_email:
                    case R.id.vcp_email:
                        to = getResources().getString(R.string.core_email);
                        break;
                    case R.id.exec_email:
                        to = getResources().getString(R.string.exec_email);
                        break;
                    case R.id.tech_email:
                        to = getResources().getString(R.string.tech_email);
                        break;
                    case R.id.pr_email:
                        to = getResources().getString(R.string.pr_email);
                        break;
                    case R.id.marketing_email:
                        to = getResources().getString(R.string.marketing_email);
                        break;
                    case R.id.events_email:
                        to = getResources().getString(R.string.events_email);
                        break;
                    case R.id.decor_email:
                        to = getResources().getString(R.string.decor_email);
                        break;
                    case R.id.admin_email:
                        to = getResources().getString(R.string.admin_email);
                        break;
                    case R.id.security_email:
                        to = getResources().getString(R.string.security_email);
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:"+to));
                intent.putExtra(Intent.EXTRA_EMAIL,to);
                try{
                    startActivity(Intent.createChooser(intent,"Send Email"));
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        secretary.setOnClickListener(emailListener);
        cp.setOnClickListener(emailListener);
        vcp.setOnClickListener(emailListener);
        exec.setOnClickListener(emailListener);
        tech.setOnClickListener(emailListener);
        pr.setOnClickListener(emailListener);
        marketing.setOnClickListener(emailListener);
        events.setOnClickListener(emailListener);
        decor.setOnClickListener(emailListener);
        admin.setOnClickListener(emailListener);
        security.setOnClickListener(emailListener);

        return view;
    }
}