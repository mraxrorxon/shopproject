package com.example.ahror.shopproject.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexandrius.accordionswipelayout.library.SwipeLayout;
import com.example.ahror.shopproject.R;
import com.example.ahror.shopproject.activities.CompanyInfoActivity;
import com.example.ahror.shopproject.core.BaseFragment;
import com.example.ahror.shopproject.objects.Company;
import com.example.ahror.shopproject.utils.ShopDatabese;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ahror on 05.10.2017.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    private boolean modeCompany;
    private RecyclerView rvCompany;
    private ShopDatabese shopDatabese;
    private List<Company> companies;
    private Dialog addDialog;
    private AdapterCompany adapterCompany;
    private Uri companyIconUri;
    private EditText etSearchCompany;
    private ImageView ivCompanyIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initUI(rootView);
        initAddDialog();
        return rootView;
    }

    private void initUI(View v) {
        shopDatabese = ShopDatabese.getInstance(getContext());
        companies = shopDatabese.getCompanies(null);
        rvCompany = (RecyclerView) v.findViewById(R.id.rvCompanies);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rvCompany.setLayoutManager(mLayoutManager);
        adapterCompany = new AdapterCompany();
        rvCompany.setAdapter(adapterCompany);
        v.findViewById(R.id.fabAddCompany).setOnClickListener(this);
        v.findViewById(R.id.ivSearchIcon).setOnClickListener(this);
        etSearchCompany = (EditText) v.findViewById(R.id.etSearchCompany);
        etSearchCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                companies = shopDatabese.getCompanies(editable.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        etSearchCompany.clearFocus();
    }

    private void initAddDialog() {
        addDialog = new Dialog(getContext());
        int width = getResources().getDisplayMetrics().widthPixels;
        addDialog.getWindow().setLayout((int) (width * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        addDialog.setTitle("Yangi companya qo'shish");
        addDialog.setContentView(R.layout.company_add_dialog);
        ivCompanyIcon = (ImageView) addDialog.findViewById(R.id.ivCompanyIcon);
        final TextInputEditText etCompanyName = (TextInputEditText) addDialog.findViewById(R.id.etCompanyName);
        ivCompanyIcon = (ImageView) addDialog.findViewById(R.id.ivCompanyIcon);
        addDialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCompanyName.setText("");
                ivCompanyIcon.setImageResource(android.R.drawable.ic_menu_add);
                hideKeyboard(view);
                addDialog.dismiss();
            }
        });
        addDialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCompanyName.getText().toString().trim().isEmpty()) {
                    etCompanyName.setError("Companya nomini kiriting!!");
                    return;
                }
                etCompanyName.setError(null);
                Company company = new Company();
                company.setCompany_name(etCompanyName.getText().toString());
                company.setCompanyIcon(companyIconUri != null ? companyIconUri.toString() : null);
                if (!shopDatabese.addCompany(company)) {
                    etCompanyName.setError("Bunday nomli companiya mavjud");
                    return;
                }
                etCompanyName.setText("");
                ivCompanyIcon.setImageResource(android.R.drawable.ic_menu_add);
                hideKeyboard(view);
                addDialog.dismiss();
                companies.add(company);
                adapterCompany.notifyItemInserted(0);
            }
        });
        // set icon for new company
        addDialog.findViewById(R.id.ivCompanyIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 06.10.2017
                final CharSequence[] items = {"Cameradan olish", "Gallereyadan tanlash", "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Rasm qo'shish");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Cameradan olish")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[item].equals("Gallereyadan tanlash")) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, SELECT_FILE);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            ivCompanyIcon.setImageURI(data.getData());
            companyIconUri = data.getData();
        } else if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK) {
            ivCompanyIcon.setImageURI(data.getData());
            companyIconUri = data.getData();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivSearchIcon) {
            // TODO: 09.10.2017
            companies = shopDatabese.getCompanies(etSearchCompany.getText().toString());
        } else
            addDialog.show();
    }

    private class AdapterCompany extends RecyclerView.Adapter<CompanyHolder> {

        @Override
        public CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.root_company_layout, parent, false);
            return new CompanyHolder(view);
        }

        @Override
        public void onBindViewHolder(final CompanyHolder holder, final int position) {
            holder.tvCompanyName.setText("" + companies.get(position).getCompany_name());
            int borrow = companies.get(position).getProduct_count() * companies.get(position).getProduct_price();
            holder.tvCompanyBorrow.setText("Qarz: " + borrow);
            if (companies.get(position).getCompanyIcon() != null) {
//                holder.ivCompanyIcon.setImageURI(file.);
            }
            holder.swipeLayout.setOnSwipeItemClickListener(new SwipeLayout.OnSwipeItemClickListener() {
                @Override
                public void onSwipeItemClick(boolean b, int i) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                            .setNegativeButton("Bekor qilish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    holder.swipeLayout.closeItem();
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("O'chirish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    holder.swipeLayout.closeItem();
                                    shopDatabese.removeCompany(companies.get(position));
                                    companies.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, companies.size());
                                }
                            })
                            .setMessage(companies.get(position).getCompany_name() + "\n companiyani o'chirishni hohlaysizmi?");
                    alert.create().show();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CompanyInfoActivity.start(getContext(), companies.get(position).getId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return companies.size();
        }
    }

    private class CompanyHolder extends RecyclerView.ViewHolder {
        private TextView tvCompanyName;
        private TextView tvCompanyBorrow;
        private ImageView ivCompanyIcon;
        private SwipeLayout swipeLayout;

        public CompanyHolder(View itemView) {
            super(itemView);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvCompanyBorrow = (TextView) itemView.findViewById(R.id.tvCompanyBorrow);
            ivCompanyIcon = (ImageView) itemView.findViewById(R.id.ivCompanyIcon);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
        }
    }
}
